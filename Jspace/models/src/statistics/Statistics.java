package statistics;

public class Statistics {
	
	public static double stat(double[] data, String s) {// s can be "mean", "sd" etc
		
		if(s=="mean") return mean(data);
		else if(s=="sd") return sd(data);
		else if(s=="skew") return skew(data);
		else if(s=="kurt") return kurt(data);
		else return 0;
		
	}
	
	private static double mean(double[] data){
		
		double n= (double) data.length;
		double val=0;
		for(int i=0; i<n; i++) val=val+data[i];
		return (val/n);
		
	}

	private static double sd(double[] data){
		
		double n= (double) data.length;
		double m=mean(data);
		double val=0;
		for(int i=0; i<n; i++) val=val+Math.pow(data[i]-m,2);
		return Math.sqrt(val/(n-1));
		
	}

	private static double skew(double[] data){
		
		double n= (double) data.length;
		double m=mean(data);
		double s=sd(data);
		double val=0;
		for(int i=0; i<n; i++) val=val+Math.pow(data[i]-m,3);
		return (val*n/((n-1)*(n-2)))/Math.pow(s,3);
		
	}

	private static double kurt(double[] data){
		
		double n= (double) data.length;
		double m=mean(data);
		double s=sd(data);
		double val=0;
		for(int i=0; i<n; i++) val=val+Math.pow(data[i]-m,4);
		return (val*n*(n+1)/((n-1)*(n-2)*(n-3)))/Math.pow(s, 4)-(3.0*(n-1)*(n-1)/((n-2)*(n-3)));
		
	}
	
	public static double quantile(double[] data, double prob){
		int size = data.length;
		if(size == 1) return(data[0]);
		int below = (int)(Math.floor(size*prob));
		int above = (int)(Math.ceil(size*prob));
		double weight = above-size*prob;
		
		for(int i=size; i>=below; i--){ //Just sort down to the required quantile
			for(int j=1; j<i; j++){
				if(data[i-1]<data[j-1]){
					double temp=data[i-1];
					data[i-1]=data[j-1];
					data[j-1]=temp;
				}
			}
		}
		
		return data[below-1]*weight+data[above-1]*(1-weight);

	}
	
}
