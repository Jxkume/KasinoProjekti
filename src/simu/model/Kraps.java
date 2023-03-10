package simu.model;

// 
/**
 * Luokka jossa toteutetaan krapsin logiikka, eli miten krapsin peli toimii oikeassa elämässä
 * 
 * @author Tapio Humaljoki, Valtteri Kuitula, Jhon Rastrojo
 */
public class Kraps {
	
	/**
	 * Arvotaan random luvun 0-n.
	 *
	 * @param luku n joka määrittää arvotun luvun välin päättymispiste
	 * @return arvotun luvun
	 */
	public static int randomLuku(int n) {
		return (int) (Math.random() * n);
	}
	
	/**
	 * Arpoo kaksi lukua jonka jälkeen summataan ne
	 *
	 * @return arvottujen lukujen summa
	 */
	public static int kahdenNopanSumma() {
		int ekaNoppa = 1 + randomLuku(6);
		int tokaNoppa = 1 + randomLuku(6);
		int summa = ekaNoppa + tokaNoppa;
		return summa;
	}
	
	/**
	 * Metodi jolla tarkistetaan ja määritetään milloin asiakas voittaa
	 *
	 * @return true silloin kun asiakas määritetään voittajaksi
	 */
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
