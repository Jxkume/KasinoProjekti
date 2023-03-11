package simu.model;

/**
 * Luokka Krapsille, jossa toteutetaan Kraps-pelin logiikka eli miten Kraps toimii reaalimaailmassa
 * 
 * @author Tapio Humaljoki, Valtteri Kuitula, Jhon Rastrojo
 */
public class Kraps {
	
	/**
	 * Arpoo satunnaisluvun
	 *
	 * @param Integer-luku, joka maarittaa arvotun luvun maksimiarvon (-1)
	 * @return arvottu Integer-luku
	 */
	public static int randomLuku(int n) {
		return (int) (Math.random() * n);
	}
	
	/**
	 * Palauttaa kahden arvotun luvun summan
	 * Arpoo kaksi lukua valilta 1-6
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
	 * Tarkistaa milloin asiakas voittaa
	 *
	 * @return true, jos asiakas maaritetaan voittajaksi
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
