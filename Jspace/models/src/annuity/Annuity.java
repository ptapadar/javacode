package annuity;

import java.util.Random;
import mortality.*;
import graphical.*;
import statistics.*;

public class Annuity {
	
	private double premium; //single premium paid upfront
	private double pension; //paid annually in arrears
	private int retirementAge;
	private int lastAge; //end of mortality table
	private double expPropInBonds; //Experience: Proportion of pension fund assets invested in bonds 
	private double valPropInBonds; //Valuation: Proportion of pension fund assets invested in bonds 
	private boolean futureKnown; //This will usually be set to false, as a valuation actuary will not know the future
	private int yHor; //Time horizon in years
	
	private Random ran;
	
	public Annuity(double premium, double pension, int retirementAge, int lastAge, 
			double expPropInBonds, double valPropInBonds){
		this.premium=premium;
		this.pension=pension;
		this.retirementAge=retirementAge;
		this.lastAge=lastAge;
		this.expPropInBonds=expPropInBonds;
		this.valPropInBonds=valPropInBonds;
		
		this.futureKnown=false; //Hard-coded - never a good idea!

		this.yHor=lastAge-retirementAge;
		ran = new Random(12345);
				
	}
	
	private class EconVar {
		int bondTermY = 15; //Hard-coding - never a good idea!
		
		private Economic inflation;
		private Economic divGrowth;
		private Economic divYield;
		private Economic cashYield;
		private Economic mediumTermBondYield;
		private Economic longTermBondYield;
		
		private double[] bondReturn;
		private double[] equityReturn;
		
		public EconVar(){
			
			inflation = new Economic(yHor*12, 0.0275, 0.975, 0.0075*Math.sqrt(1-0.975*0.975), ran, 0.0275,true);
			divGrowth = new Economic(yHor*12, 0.0425, 0.95, 0.02*Math.sqrt(1-0.95*0.95), ran, 0.0425, true, inflation, 0.1);
			divYield = new Economic(yHor*12, 0.0325, 0.975, 0.0075*Math.sqrt(1-0.975*0.975), ran, 0.0325, false, inflation, 0.3);
			cashYield = new Economic(yHor*12, 0.0475, 0.975, 0.0075*Math.sqrt(1-0.975*0.975), ran, 0.0475, false, inflation, 0.6);
			mediumTermBondYield = new Economic(yHor*12, 0.0500, 0.975, 0.01875*Math.sqrt(1-0.975*0.975), ran, 0.0500, false, cashYield, 0.6);
			longTermBondYield = new Economic(yHor*12, 0.0525, 0.975, 0.01875*Math.sqrt(1-0.975*0.975), ran, 0.0525, false, mediumTermBondYield, 0.6);

			double[] equityTRI = Economic.equityIndex(divYield, divGrowth);
			double[] bondTRI = Economic.bondIndex(longTermBondYield, bondTermY);
			double[] rpi = Economic.indexFromReturn(inflation.values);
			
			double[] unity = new double[rpi.length]; 
			for(int i=0; i<unity.length; i++) unity[i]=1;
					
			bondReturn = Economic.returnOnIndex(bondTRI,unity);//unity variable gives nominal returns
			equityReturn = Economic.returnOnIndex(equityTRI,unity);//rpi would have given real return
			
		}
		
		public double[] getReturn(double propInBonds){

			double[] assetReturn = new double[bondReturn.length];
			for(int i=0; i<assetReturn.length; i++) 
				assetReturn[i]=propInBonds*bondReturn[i]+(1-propInBonds)*equityReturn[i];

			return assetReturn;
			
		}
		
		public double[] getYield(double propInBonds){
			
			double[] bondYield = new double[yHor];
			double[] equityYield = new double[yHor];
			double[] assetYield = new double[yHor];
			for(int i=0; i<yHor; i++){
				bondYield[i]=longTermBondYield.values[i*12];
				equityYield[i]=(1+divYield.values[i*12])*(1+divGrowth.values[i*12])-1;
				assetYield[i]=propInBonds*bondYield[i]+(1-propInBonds)*equityYield[i];
			}
			return assetYield;
		}
		
	}
		
	private double[] cashflow(GompertzMakeham mort){
		double[] cf = new double[yHor+1];
		cf[0]=premium;
		for(int i=1; i<=yHor; i++) 
			cf[i]=-pension*mort.nPx(retirementAge, retirementAge+i);
		return cf;
	}
	
	private double[] reserve(double[] cf, double[] valRate){//After cashflows
		double[] res = new double[yHor+1];
		
		if(futureKnown == true){//This is only for checking purpose, i.e. assuming actuaries are GOD and know future
			res[yHor]=0;
			for(int i=yHor-1; i>=0; i--) res[i]=(res[i+1]-cf[i+1])/(1+valRate[i]);
		}
		else{
			res[yHor]=0;
			for(int j=0; j<yHor; j++){
				res[j]=0;
				for(int i=yHor-1; i>=j; i--) res[j]=(res[j]-cf[i+1])/(1+valRate[j]);				
			}
		}
		return res;
	}
	
	private double[] profit(double[] res, double[] cf, double[] returns){
		double[] provec = new double[yHor+1];
		provec[0] = cf[0] - res[0];
		for(int i=1; i<=yHor; i++) provec[i]=res[i-1]*(1+returns[i-1])-res[i]+cf[i];
		return provec;		
	}
	
	private double[] pvfp(double[] provec, double[] returns){
		double[] fp = new double[yHor+1];
		fp[yHor]=0;
		for(int i=yHor-1; i>=0; i--)
			fp[i]=(fp[i+1]+provec[i+1])/(1+returns[i]);
		
		return fp;
	}
	
	private double[] capReq(double[] fp, double[] returns){
		double[] cap = new double[yHor+1];
		double[] accum = new double[yHor+1];
		accum[0] = 1;
		for(int i=1; i<=yHor; i++) accum[i]=accum[i-1]*(1+returns[i-1]);
		for(int i=0; i<=yHor; i++){
			cap[i]=Math.max(-fp[i],0);
			for(int j=i; j<=yHor; j++) 
				if(cap[i]<-fp[j]/(accum[j]/accum[i])) cap[i]=-fp[j]/(accum[j]/accum[i]);
		}
		return cap;
	}
	
	private double[] simulateCapReq(){
		
		GompertzMakeham mortExp = new GompertzMakeham(retirementAge,lastAge,0.0,0.000002,1.13);
		GompertzMakeham mortVal = new GompertzMakeham(retirementAge,lastAge,0.0,0.000002,1.13);
		double[] cfExp = cashflow(mortExp);
		double[] cfVal = cashflow(mortVal);

		EconVar econ = new EconVar();
		double[] valRate = econ.getYield(valPropInBonds);
		double[] assetReturn = econ.getReturn(expPropInBonds);
		
		double[] res = reserve(cfVal,valRate);
		double[] provec = profit(res,cfExp,assetReturn);
		double[] fp = pvfp(provec,assetReturn);
		double[] cap = capReq(fp,assetReturn);
		//for(int i=0; i<50; i++) System.out.println(assetReturn[i]);
		return cap;
		
	}
	
	public double[] economicCapital(int simulations, double prob){
		
		double[][] store = new double[simulations][];
		
		for(int i=0; i<simulations; i++) store[i]=simulateCapReq();
		
		double[][] storeTranspose = new double[yHor+1][simulations];
		for(int i=0; i<=yHor; i++){
			for(int j=0; j<simulations; j++){
				storeTranspose[i][j]=store[j][i];
			}
		}
		
		double[] ec = new double[yHor+1];
		for(int i=0; i<=yHor; i++) ec[i]=Statistics.quantile(storeTranspose[i], prob);
		return ec;
	}

}
