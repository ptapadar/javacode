package statistics;

import com.github.rcaller.rstuff.RCaller;
import com.github.rcaller.rstuff.RCode;

public class RStatistics {
	
	public static final String rskew = "skew=function(x){n=length(x);m=mean(x);s=sd(x);return((n/((n-1)*(n-2)))*n*mean((x-m)^3)/(s^3))}";
	public static final String rkurt = "kurt=function(x){n=length(x);m=mean(x);s=sd(x);return((n*(n+1)/((n-1)*(n-2)*(n-3)))*n*mean((x-m)^4)/(s^4)-(3*(n-1)*(n-1)/((n-2)*(n-3))))}";

	public static double stat(double[] data, String s) {// s can be "mean", "sd" etc

		RCaller caller = RCaller.create();
		RCode code = RCode.create();
		
		if(s=="skew") {code.addRCode(rskew); caller.setRCode(code);}
		if(s=="kurt") {code.addRCode(rkurt); caller.setRCode(code);}
		
		code.addDoubleArray("data", data);
		
		code.addRCode("res="+s+"(data)");
		caller.setRCode(code);
		caller.runAndReturnResult("res");
		
		return caller.getParser().getAsDoubleArray("res")[0];
	}


	public static double quantile(double[] data, double q) {

		RCaller caller = RCaller.create();
		RCode code = RCode.create();
		
		code.addDoubleArray("data", data);
		code.addDouble("qq", q);
		
		code.addRCode("res=quantile(data,qq)");
		caller.setRCode(code);
		caller.runAndReturnResult("res");
		
		return caller.getParser().getAsDoubleArray("res")[0];
	}

	public static double correlation(double[] data1, double[] data2) {

		RCaller caller = RCaller.create();
		RCode code = RCode.create();
		
		code.addDoubleArray("x", data1);
		code.addDoubleArray("y", data2);
		
		code.addRCode("res=cor(x,y)");
		caller.setRCode(code);
		caller.runAndReturnResult("res");
		
		return caller.getParser().getAsDoubleArray("res")[0];
	}

}
