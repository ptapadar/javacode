package wilkie;

import java.util.Random;
import java.lang.Math;

public class WilkieModels {
	
	//Parameter sets: 0 for 1984 reduced, 1 for 1984 full, 2 for 1995 and 3 for 2010
	private static final double[] qmu = {0.0500,0.0500,0.0470,0.0430};
	private static final double[] qa  = {0.6000,0.6000,0.5800,0.5800};
	private static final double[] qsd = {0.0500,0.0500,0.0425,0.0400};
//	private static final double[] qsd = {0,0,0,0};

	private static final double[] yw  = {1.3500,1.3500,1.8000,1.5500};
	private static final double[] ymu = {0.0400,0.0400,0.0375,0.0375};
	private static final double[] ya  = {0.6000,0.6000,0.5500,0.6300};
	private static final double[] ysd = {0.1750,0.1750,0.1550,0.1550};
//	private static final double[] ysd = {0,0,0,0};

	private static final double[] dd  = {0.2000,0.2000,0.1300,0.1600};
	private static final double[] dw  = {0.8000,0.8000,0.5800,0.4300};
	private static final double[] dmu = {0.0000,0.0000,0.0160,0.0110};
	private static final double[] dy  = {-0.3000,-0.2000,-0.1750,-0.2200};
	private static final double[] db  = {0.0000,0.3750,0.5700,0.4300};
	private static final double[] dsd = {0.1000,0.0750,0.0700,0.0700};
//	private static final double[] dsd = {0,0,0,0};

	private static final double[] cd  = {0.0500,0.0450,0.0450,0.0450};
	private static final double[] cmu = {0.0350,0.0350,0.0305,0.0223};
	private static final double[] ca1 = {0.9100,1.2000,0.9000,0.9200};
	private static final double[] ca2 = {0.0000,-0.4800,0.0000,0.0000};
	private static final double[] ca3 = {0.0000,0.2000,0.0000,0.0000};
	private static final double[] cy  = {0.0000,0.0600,0.3400,0.3700};
	private static final double[] csd = {0.1650,0.1400,0.1850,0.2550};
//	private static final double[] csd = {0,0,0,0};

	
	private static final double cmin  = 0.005;

	//Date from 1982 to 2010:
	public static final double[] dataQ  = {7.967126908,8.148197975,9.741623356,7.206628431,6.51855838,6.40991574,6.554772593,6.40991574,6.482344166,6.120202034,6.083987821,6.047773608,5.613203049,5.359703557,5.178632491,5.178632491,5.214846704,5.323489343,5.468346196,5.794274115,5.649417262,6.265058887,7.061771578,7.279056857,7.242842644,7.206628431,7.279056857,7.351485284,7.387699497,7.645544695,8.013118959,8.307178371,8.527722929,9.556930869,10.14504969,10.2920794,10.65965366,11.32128734,11.63019675,12.17008983,12.39504528,12.39504528,12.62000073,13.17114158,13.52010372,13.77121024,14.43201687,15.07960738,15.63468495,16.01795279,16.96951435,17.76248231,19.16339237,20.89470575,22.4938578,24.87276168,29.63244613,37.00887199,42.58555133,47.75665399,51.7617237,60.68441065,69.86058302,78.27629911,82.5095057,86.89480355,90.87452471,96.04562738,99.61977186,103.3,110.3,118.8,129.9,135.7,139.2,141.9,146,150.7,154.4,160,164.4,167.3,172.2,173.4,178.5,183.5,189.9,194.1,202.7,210.9,212.9,218,228.4};
	public static final double[] dataI  = {0.173271721,0.022472856,0.178610977,-0.301406555,-0.100347974,-0.016807118,0.022347299,-0.022347299,0.011236073,-0.057487091,-0.005934736,-0.005970167,-0.074568695,-0.046212843,-0.034367644,0,0.006968669,0.020619287,0.02684725,0.057893978,-0.025317808,0.103435587,0.119707964,0.030305349,-0.004987542,-0.005012542,0.010000083,0.009901071,0.004914015,0.034306698,0.046956983,0.036039936,0.026202372,0.113944259,0.059719235,0.014388737,0.03509132,0.06021886,0.026920095,0.045376404,0.01831553,0,0.017986096,0.042745278,0.02614955,0.018402456,0.046868934,0.043894194,0.036148514,0.024218303,0.057708318,0.045670037,0.075913314,0.086494002,0.073746472,0.100531011,0.175096618,0.222287753,0.140357358,0.114603383,0.080532544,0.159035887,0.140814746,0.113743279,0.052668643,0.051784725,0.044781473,0.055343658,0.036537294,0.036276719,0.06556655,0.074237481,0.089323517,0.043681643,0.025465181,0.019210836,0.028484038,0.031684484,0.024255532,0.035627178,0.027128667,0.017486125,0.028867984,0.006944472,0.028987537,0.027626066,0.03428295,0.021875872,0.043353563,0.039657034,0.009438484,0.023672491,0.046603415};
	public static final double[] dataY  = {0.048,0.048,0.095,0.0885,0.0601,0.0643,0.043146,0.044625,0.048348,0.042993,0.042075,0.0681,0.0721,0.0694,0.0438,0.0347,0.0367,0.0368,0.0368,0.0491,0.0613,0.0616,0.0647,0.05,0.0447,0.0443,0.0416,0.0431,0.0415,0.0468,0.0474,0.0558,0.0536,0.059,0.064,0.0576,0.0524,0.0564,0.0648,0.0702,0.0525,0.0398,0.0499,0.0563,0.0435,0.0408,0.0518,0.0522,0.0578,0.0438,0.0319,0.0385,0.0439,0.0325,0.0315,0.0477,0.1171,0.0547,0.0642,0.0528,0.0579,0.0687,0.061,0.0589,0.0526,0.0462,0.0442,0.0433,0.0404,0.0432,0.0471,0.0424,0.0547,0.0502,0.0435,0.0337,0.0402,0.038,0.0374,0.0323,0.0292,0.023555556,0.024777778,0.029222222,0.039444444,0.034444444,0.033888889,0.032777778,0.031777778,0.033555556,0.049888889,0.035555556,0.032111111};
	public static final double[] dataD  = {0.714600246,0.968283333,1.325211268,1.11595994,1.071897391,1.205198623,1.144150735,1.365042073,1.531122957,1.463071595,1.655170868,1.828388483,1.519790414,1.121615114,0.814131639,0.791728316,0.889094587,0.974757629,1.095395949,1.187887513,1.23587075,1.138930647,1.0498976,0.994938322,1.055060789,1.129849991,1.174178017,1.209451613,1.375233603,1.508415139,1.466605425,1.486935807,1.519066114,1.712541361,1.780268986,1.858880022,2.274190142,2.486416387,2.599720137,2.722217619,2.828598486,3.07485199,3.673497172,4.039892293,4.11249,4.428432,5.028226,5.415228,5.434934,5.305494,5.525399,5.67259,5.981814,6.285175,6.87267,7.143552,7.832819,8.646976,9.755832,11.327184,12.750738,15.786573,17.81139,18.442768,20.104772,21.7371,26.207948,29.571302,33.753392,37.593504,43.642389,51.07928,56.464075,59.62254,59.324865,56.689129,61.161888,68.51742,75.310884,77.8753,78.078464,76.36852444,73.93218111,73.75338222,74.69712778,76.03197778,81.69763889,93.31898889,102.3695689,110.2860378,110.2190233,98.16177778,98.35312222};
	public static final double[] dataC  = {0.0422,0.049,0.0559,0.0498,0.045,0.045,0.0435,0.0454,0.0463,0.045,0.0459,0.049,0.0431,0.0485,0.0367,0.0345,0.0313,0.0289,0.0295,0.0336,0.0356,0.0366,0.0326,0.0305,0.0306,0.0316,0.0308,0.0276,0.0255,0.0307,0.0314,0.0356,0.0353,0.0409,0.043,0.0393,0.038,0.0445,0.0488,0.0541,0.0484,0.0513,0.0572,0.0663,0.0565,0.0581,0.0631,0.0648,0.0666,0.0706,0.0799,0.0876,0.0979,0.0846,0.0984,0.1225,0.172,0.1467,0.1447,0.1046,0.1193,0.1223,0.1161,0.1343,0.1025,0.0971,0.099,0.098,0.1006,0.0921,0.0899,0.0966,0.1048,0.0971,0.0883,0.0652,0.0853,0.0778,0.0774,0.0639,0.0455,0.0489,0.0462,0.0504,0.0456,0.0472,0.0445,0.0403,0.0426,0.0436,0.039,0.0471,0.0451};
	
	private static final int baseYear = 1918; //This is the year for which initial values are set.
	//Initial values as at 31st December 1982:
	private static final double[] yni  = {-0.051595267,-0.051595267,-0.06502902,-0.02171109};
	private static final double[] yei  = {0,0,0,0};
	private static final double[] dmi  = {0.113416592,0.113416592,0.087210709,0.099686519};
	private static final double[] dei  = {0,0,0,0};
	private static final double[] cmi  = {0.041553618,0.03793759,0.03793759,0.03793759};
	private static final double[] cni  = {0.000646382,0.00426241,0.00426241,0.00426241};
	
	private int startYear; //Start year cannot be before 1985.
	private int horizon; //in years.
	private int modelNumber; //indicates which model (1984 reduced, 1984 full, 1995 and 2010) to use for simulation.
	
    private Random ran;
  	private double[] yn, ye, dm, de, cm, cn, ce;
    
    public double[] inflationRate, dividendYield, dividendIndex, consolYield;

	public WilkieModels(int modelNumber, int startYear, int horizon){
		this.modelNumber=modelNumber; //0,1,2,3
		this.startYear=startYear; //>=1985
		this.horizon=horizon; //>0
		ran = new Random(12345);        
		
		//Note that the first element is the 0-th year.
		inflationRate = new double[horizon+1]; inflationRate[0]=dataI[startYear-baseYear];
		dividendYield = new double[horizon+1]; dividendYield[0]=dataY[startYear-baseYear];
        dividendIndex = new double[horizon+1]; dividendIndex[0]=dataD[startYear-baseYear];
        consolYield = new double[horizon+1];   consolYield[0]=dataC[startYear-baseYear];
        
        //Note that for the following we need the some extra years' of initial values.
        yn = new double[horizon+(startYear-baseYear)+1]; yn[0]=yni[modelNumber];
        ye = new double[horizon+(startYear-baseYear)+1]; ye[0]=yei[modelNumber];
        dm = new double[horizon+(startYear-baseYear)+1]; dm[0]=dmi[modelNumber];
        de = new double[horizon+(startYear-baseYear)+1]; de[0]=dei[modelNumber];
        cm = new double[horizon+(startYear-baseYear)+1]; cm[0]=cmi[modelNumber];
        cn = new double[horizon+(startYear-baseYear)+1]; cn[0]=cni[modelNumber];
        ce = new double[horizon+(startYear-baseYear)+1]; ce[0]=0.0;
        
        //Setting the initial values for the startYear
        for(int i=baseYear+1; i<=startYear; i++){
        	yn[i-baseYear]=Math.log(dataY[i-baseYear])-yw[modelNumber]*dataI[i-baseYear]-Math.log(ymu[modelNumber]);
        	ye[i-baseYear]=yn[i-baseYear]-ya[modelNumber]*yn[i-baseYear-1];
        	dm[i-baseYear]=dd[modelNumber]*dataI[i-baseYear]+(1-dd[modelNumber])*dm[i-baseYear-1];
        	de[i-baseYear]=Math.log(dataD[i-baseYear]/dataD[i-baseYear-1])-dw[modelNumber]*dm[i-baseYear]
	        	-(1-dw[modelNumber])*dataI[i-baseYear]-dmu[modelNumber]-dy[modelNumber]*ye[i-baseYear-1]
	        	-db[modelNumber]*de[i-baseYear-1];
        	cm[i-baseYear]=cd[modelNumber]*dataI[i-baseYear]+(1-cd[modelNumber])*cm[i-baseYear-1];
        	cn[i-baseYear]=dataC[i-baseYear]-cm[i-baseYear];
        }
    }
	
	public void simulate(){
		for(int j=1; j<=horizon; j++){

			inflationRate[j]=qmu[modelNumber]+qa[modelNumber]*(inflationRate[j-1]-qmu[modelNumber])
					+qsd[modelNumber]*ran.nextGaussian();

			ye[j+(startYear-baseYear)]=ysd[modelNumber]*ran.nextGaussian();
			yn[j+(startYear-baseYear)]=ya[modelNumber]*yn[j+(startYear-baseYear)-1]
			        +ye[j+(startYear-baseYear)];
			dividendYield[j]=Math.exp(yw[modelNumber]*inflationRate[j]
			        +yn[j+(startYear-baseYear)]+Math.log(ymu[modelNumber]));

			dm[j+(startYear-baseYear)]=dd[modelNumber]*inflationRate[j]
			        +(1-dd[modelNumber])*dm[j+(startYear-baseYear)-1];			
			de[j+(startYear-baseYear)]=dsd[modelNumber]*ran.nextGaussian();
			dividendIndex[j]=dividendIndex[j-1]
					*Math.exp(dw[modelNumber]*dm[j+(startYear-baseYear)]
					+(1-dw[modelNumber])*inflationRate[j]
					+dmu[modelNumber]
					+dy[modelNumber]*ye[j+(startYear-baseYear)-1]
					+db[modelNumber]*de[j+(startYear-baseYear)-1]
					+de[j+(startYear-baseYear)]);


			cm[j+(startYear-baseYear)]=cd[modelNumber]*inflationRate[j]
			        +(1-cd[modelNumber])*cm[j+(startYear-baseYear)-1];
			ce[j+(startYear-baseYear)]=csd[modelNumber]*ran.nextGaussian();
			cn[j+(startYear-baseYear)]=cmu[modelNumber]
			        *Math.exp(ca1[modelNumber]*cn[j+(startYear-baseYear)-1]
			        +ca2[modelNumber]*cn[j+(startYear-baseYear)-2]
			        +ca3[modelNumber]*cn[j+(startYear-baseYear)-3]
			        +cy[modelNumber]*ye[j+(startYear-baseYear)]+ce[j+(startYear-baseYear)]);
			consolYield[j]=cm[j+(startYear-baseYear)]+cn[j+(startYear-baseYear)];
			if(consolYield[j]<cmin) consolYield[j]=cmin;
		}
	}	
	
	public double[] getInflationIndex(){
		double[] inflationIndex = new double[horizon+1]; inflationIndex[0]=100;
		for(int j=1; j<=horizon; j++)
			inflationIndex[j]=inflationIndex[j-1]*Math.exp(inflationRate[j]);
		return inflationIndex;
	}

	public double[] getSharePriceIndex(){
		double[] sharePriceIndex = new double[horizon+1];
		sharePriceIndex[0]=dividendIndex[0]/dividendYield[0];
		for(int j=1; j<=horizon; j++)
			sharePriceIndex[j]=dividendIndex[j]/dividendYield[j];
		return sharePriceIndex;
	}
	
	public double[] getSharePriceRolledIndex(){
		double[] sharePriceRolledIndex = new double[horizon+1]; sharePriceRolledIndex[0]=100;
		double[] sharePriceIndex = getSharePriceIndex();
		for(int j=1; j<=horizon; j++)
			sharePriceRolledIndex[j]=sharePriceRolledIndex[j-1]
					*((sharePriceIndex[j]+dividendIndex[j])/sharePriceIndex[j-1]);
		return sharePriceRolledIndex;
	}

	public double[] getConsolPriceRolledIndex(){
		double[] consolPriceRolledIndex = new double[horizon+1]; consolPriceRolledIndex[0]=100;
		for(int j=1; j<=horizon; j++)
			consolPriceRolledIndex[j]=consolPriceRolledIndex[j-1]
					*(1/consolYield[j]+1)*consolYield[j-1];
		return consolPriceRolledIndex;
	}

	public static double[] returnOnIndex(double[] index, double[] rpi){//SPECIALISED
		
		int hor = index.length-1;
		int unit = 1; //Wilkie is an annual model 
		double[] ret = new double[hor/unit];
		for(int i=0; i<hor/unit; i++) 
			ret[i]=(index[unit*(i+1)]/index[unit*i])/(rpi[unit*(i+1)]/rpi[unit*i])-1;
		return ret;
		
	}

	
}
