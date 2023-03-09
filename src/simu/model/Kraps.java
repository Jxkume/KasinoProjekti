package simu.model;

public class Kraps {
	
	// random int luku 0-n
	public static int randomLuku(int n) {
		return (int) (Math.random() * n);
	}
	
	public static int kahdenNopanSumma() {
		int ekaNoppa = 1 + randomLuku(6);
		int tokaNoppa = 1 + randomLuku(6);
		int summa = ekaNoppa + tokaNoppa;
		return summa;
	}
	
	public static boolean voittaako() {
		int ekaHeitto = kahdenNopanSumma();
		if (ekaHeitto == 7 || ekaHeitto == 11) {
			return true;
		}
		if (ekaHeitto == 2 || ekaHeitto == 3 || ekaHeitto == 12) {
			return false;
		}
		
		while (true) {
			int seuraavatHeitot = kahdenNopanSumma();
			if (seuraavatHeitot == 7) {
				return false;
			} 
			if (seuraavatHeitot == ekaHeitto) {
				return true;
			}
		}
		
	}
	
}
