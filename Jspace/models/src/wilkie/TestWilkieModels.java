package wilkie;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import statistics.*;

public class TestWilkieModels {

	public static void main(String[] argv) {


		int modelNumber=2;
		int startYear=2010;
		int horizon=100;
		int simulations=2500;
		double[][] statStore = new double[4][simulations];

		WilkieModels w = new WilkieModels(modelNumber,startYear,horizon);
		for(int sim=0; sim<simulations; sim++){
			w.simulate();

			double[] equityTRI = w.getSharePriceRolledIndex();
			double[] bondTRI = w.getConsolPriceRolledIndex();
			double[] rpi = w.getInflationIndex();

			double[] ret = WilkieModels.returnOnIndex(equityTRI,rpi);
			//double[] ret = WilkieModels.returnOnIndex(bondTRI,rpi);

			statStore[0][sim]=Statistics.stat(ret, "mean");
			statStore[1][sim]=Statistics.stat(ret, "sd");
			statStore[2][sim]=Statistics.stat(ret, "skew");
			statStore[3][sim]=Statistics.stat(ret, "kurt");

		}

		System.out.println(Statistics.stat(statStore[0], "mean"));
		System.out.println(Statistics.stat(statStore[1], "mean"));
		System.out.println(Statistics.stat(statStore[2], "mean"));
		System.out.println(Statistics.stat(statStore[3], "mean"));
	}

}
