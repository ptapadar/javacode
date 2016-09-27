package statistics;

public class TestStatistics {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		double[] data = new double[]{10,11,3,4,5,6,7,8,9};
		System.out.println(Statistics.stat(data, "mean"));
		System.out.println(Statistics.quantile(data, 0.9));
	}

}
