package annuity;

public class TestAnnuity {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		double propInBonds = 1; //0=All asset in equities; 1=All assets in bonds.
		
		Annuity ann = new Annuity(250000,18000,65,120,propInBonds,propInBonds); //Premium, Annual pension, Start age, End age, Proportion in bonds (Actual), Proportion in bonds (assumption for reserve calculations). 
		double[] ec = ann.economicCapital(1000, 0.995); //Simulation, quantile
		for(int i=0; i<=120-65; i++) System.out.println(ec[i]);
		

	}

}
