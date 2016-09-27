package mortality;

public class TestMortality {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GompertzMakeham gm = new GompertzMakeham(60,110,0.0,0.000002,1.12);
		for(int i=0; i<=10; i++) 
			System.out.println(gm.nPx(60, 60+i));
	}

}
