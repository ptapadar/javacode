package graphical;

import java.util.Random;

import java.io.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import statistics.*;

public class TestEco {

	public static void main(String[] args) {

		int nSim = 2500;
		int yHor = 25;
		int bondTermY = 15;

		Random ran = new Random();
		double[][] statStore = new double[4][nSim];
		
		for(int sim=0; sim<nSim; sim++){
			Economic inflation = new Economic(yHor*12, 0.0275, 0.975, 0.0075*Math.sqrt(1-0.975*0.975), ran, 0.0275,true);
			Economic divGrowth = new Economic(yHor*12, 0.0425, 0.95, 0.02*Math.sqrt(1-0.95*0.95), ran, 0.0425, true, inflation, 0.1);
			Economic divYield = new Economic(yHor*12, 0.0325, 0.975, 0.0075*Math.sqrt(1-0.975*0.975), ran, 0.0325, false, inflation, 0.3);
			Economic cashYield = new Economic(yHor*12, 0.0475, 0.975, 0.0075*Math.sqrt(1-0.975*0.975), ran, 0.0475, false, inflation, 0.6);
			Economic mediumTermBondYield = new Economic(yHor*12, 0.0500, 0.975, 0.01875*Math.sqrt(1-0.975*0.975), ran, 0.0500, false, cashYield, 0.6);
			Economic longTermBondYield = new Economic(yHor*12, 0.0525, 0.975, 0.01875*Math.sqrt(1-0.975*0.975), ran, 0.0525, false, mediumTermBondYield, 0.6);

			double[] equityTRI = Economic.equityIndex(divYield, divGrowth);
			double[] bondTRI = Economic.bondIndex(longTermBondYield, bondTermY);
			double[] rpi = Economic.indexFromReturn(inflation.values);
			
			double[] ret = Economic.returnOnIndex(equityTRI,rpi);//Use this to test Equity statistics
			//double[] ret = Economic.returnOnIndex(bondTRI,rpi);//Use this to test Bond statistics
			
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
	
	/* 
    public static double[][] scan() throws FileNotFoundException {// Use of a scanner function to read txt files.
    	double[][] val = new double[21][320];
        Scanner scanner = new Scanner(new File("data/full.txt"));
        scanner.useDelimiter(",|\\n");        
        int counter=0;
        while(scanner.hasNextDouble()){
            val[counter%21][counter/21]=scanner.nextDouble();
        	counter++;
        }
        scanner.close();
        return val;
    }
	*/

}
