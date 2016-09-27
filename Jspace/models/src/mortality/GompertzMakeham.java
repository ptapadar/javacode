package mortality;

public class GompertzMakeham {

	private int startAge;
	private int endAge;
		
	public double[] survival;
	
	public GompertzMakeham(){
		this(60,110,0.0,0.000002,1.13); //Standard inputs
	}
	
	public GompertzMakeham(int startAge, int endAge, double paramA, double paramB, double paramC){
		
		this.startAge = startAge;
		this.endAge = endAge;
		survival = new double[endAge-startAge+1];
		
		for(int i=0; i<endAge-startAge+1; i++) 
			survival[i]=Math.exp(-paramA*i-paramB*Math.pow(paramC,startAge)*(Math.pow(paramC,i)-1)/Math.log(paramC));
	}
	
	public double nPx(int from, int to){
		if(from<startAge || to>endAge) return -1; //Throw exception;
		return(survival[to-startAge]/survival[from-startAge]);
	}

}
