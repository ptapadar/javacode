package graphical;

import java.util.Random;

public class Economic {

	public int horizon;
	private double mean;
	private double beta; //autoregressive parameter
	private double sigma; //standard deviation of error terms
	
	public double[] errors;
	public double[] values;
	
	private Random ran;
	
	public Economic(int setHorizon, double setMean, double setBeta, 
			double setSigma, Random setRandom, double initial, boolean negAllowed){
		
		horizon = setHorizon;
		mean = setMean;
		beta = setBeta;
		sigma = setSigma;
		ran = setRandom;

		errors = new double[horizon+1];
		values = new double[horizon+1];
		
		simulateErrors();
		simulateValues(initial,negAllowed);
		
	}
	
	public Economic(int setHorizon, double setMean, double setBeta, 
			double setSigma, Random setRandom, double initial, boolean negAllowed, 
			Economic connect, double rho){
		
		horizon = setHorizon;
		mean = setMean;
		beta = setBeta;
		sigma = setSigma;
		ran = setRandom;

		errors = new double[horizon+1];
		values = new double[horizon+1];
		
		simulateErrors(connect, rho);
		simulateValues(initial,negAllowed);
		
	}

	private void simulateErrors(){
		
		for(int i=0; i<=horizon; i++) errors[i] = ran.nextGaussian()*sigma;
	
	}
	
	private void simulateErrors(Economic connect, double rho){

		for(int i=0; i<=horizon; i++){
			double conditionalMu = rho * sigma *connect.errors[i]/connect.sigma;
			double conditionalSigma = Math.sqrt(Math.pow(sigma,2) - Math.pow(rho*sigma, 2));
			errors[i]=ran.nextGaussian()*conditionalSigma+conditionalMu;
		}
		
	}
	
	private void simulateValues(double initial, boolean negAllowed){
		
		values[0] = initial - mean; //mean would be added back later
		for(int i=1; i<=horizon; i++) values[i]=beta*values[i-1]+errors[i];
		for(int i=0; i<=horizon; i++) {
			values[i]=values[i]+mean; 
			if(values[i]<0 && negAllowed == false) values[i]=0.00005;
		}

	}
	
	public static double[] equityIndex(Economic divYield, Economic divGrowth){//Total return index
		
		int hor = divYield.horizon;
		double[] index = new double[hor+1];
		index[0] = 1;
		for(int i=1; i<=hor; i++) 
			index[i]=index[i-1]*(Math.pow(1+divGrowth.values[i],1.0/12.0)*(divYield.values[i-1]/divYield.values[i])+divYield.values[i]/12.0); //Monthly
		return index;

	}
	
	public static double[] bondIndex(Economic bondYield, int bondTermY){
		
		int hor = bondYield.horizon;
		double[] index = new double[hor+1];
		index[0] = 1;
		for(int i=1; i<=hor; i++) {
            double startYieldM=Math.pow((1+bondYield.values[i-1]),1.0/12.0)-1.0;
            double endYieldM=Math.pow((1+bondYield.values[i]),1.0/12.0)-1.0;
            double couponM=Math.pow((1+bondYield.values[i-1]),1.0/12.0)-1.0;
            double startBondPrice=couponM*(1-Math.pow(1.0/(1+startYieldM),bondTermY*12))/startYieldM
                    +Math.pow(1.0/(1+startYieldM),bondTermY*12);
            double endBondPrice=couponM*(1-Math.pow(1.0/(1+endYieldM),bondTermY*12-1))/endYieldM
                    +Math.pow(1.0/(1+endYieldM),bondTermY*12-1);
            double capitalGain=endBondPrice/startBondPrice-1;
            index[i]=index[i-1]*(1+capitalGain+couponM);
			
		}
		return index;
		
	}
	
	public static double[] indexFromReturn(double[] ret){//Index from annualised returns
		
		int hor = ret.length; 
		double[] index = new double[hor+1]; //One more dimension length
		index[0]=1;
		for(int i=1; i<hor; i++) 
			index[i]=index[i-1]*Math.pow(1+ret[i],1.0/12.0); //Monthly
		return index;
		
	}
	
	public static double[] returnOnIndex(double[] index, double[] rpi){//SPECIALISED for monthly model
		
		int hor = index.length-1;
		int unit = 12;//Year on year return
		double[] ret = new double[hor/unit];
		for(int i=0; i<hor/unit; i++) 
			ret[i]=(index[unit*(i+1)]/index[unit*i])/(rpi[unit*(i+1)]/rpi[unit*i])-1;
		return ret;
		
	}
}
