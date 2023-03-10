package simu.model;

import simu.framework.Kello;
import simu.framework.Trace;


/**
 * Asiakas-luokka. Sisältää Asiakas-olioille yhteisiä ominaisuuksia
 * 
 * @author Tapio Humaljoki, Valtteri Kuitula ja Jhon Rastrojo
 */
public class Asiakas {
	
	/** Asiakkaan saapumisaika */
	private double saapumisaika;
	
	/** Asiakkaan poistumisaika */
	private double poistumisaika;
	
	/** Aika jolloin asiakas saapuu jonoon */
	private double saapumisaikaJonoon;
	
	/** Aika jolloin asiakas poistuu jonosta */
	private double poistumisaikaJonosta;
	
	/** Asiakkaan id */
	private int id;
	
	/** Kasinolle saapuneiden asiakkaiden määrä */
	private static int saapuneetAsiakkaat = 1;
	
	/** Kasinolta lähteneiden asiakkaiden määrä */
	private static int lahteneetAsiakkaat = 0;
	
	/** Asiakkaan kasinolla viettämä aika */
	private static double vietettyAika = 0;
	
	/** Kaikkien asiakkaiden kasinolla keskimäärin viettämä aika */
	private double keskimaarainenVietettyAika;
	
	/** Polettimäärä jonka asiakas on nostanut palvelutiskiltä */
	private int alkuperainenPolettimaara = 0;
	
	/** Asiakkaan tämän hetkinen polettimäärä */
	private int nykyinenPolettimaara = 0;
	
	/**
	 * Asiakkaan kostruktori
	 * Tulostaa konsoliin 
	 */
	public Asiakas() {
	    id = saapuneetAsiakkaat++;
		saapumisaika = Kello.getInstance().getAika();
		Trace.out(Trace.Level.INFO, "Uusi asiakas: " + id + ": " + String.format("%.02f", saapumisaika));
	}

	/**
	 * Palauttaa asiakkaan id:n
	 *
	 * @return asiakkaa id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Palauttaa kasinolle saapuneiden asiakkaiden määrän
	 *
	 * @return saapuneetAsiakkaat
	 */
	public int getSaapuneetAsiakkaat() {
		return saapuneetAsiakkaat;
	}
	
	/**
	 * Palauttaa asiakkaan poistumisajan
	 *
	 * @return poistumisaika
	 */
	public double getPoistumisaika() {
		return poistumisaika;
	}

	/**
	 * Asettaa asiakkaan poitumisajan
	 *
	 * @param asiakkaan palvelupisteeltäpoistumisen aika
	 */
	public void setPoistumisaika(double poistumisaika) {
		this.poistumisaika = poistumisaika;
	}

	/**
	 * Palauttaa asiakkaan saapumisajan
	 *
	 * @return saapumisaika
	 */
	public double getSaapumisaika() {
		return saapumisaika;
	}

	/**
	 * Asettaa asiakkaan saapumisajan
	 *
	 *
	 * @param asiakkaan palvelupisteelle saapumisen aika
	 */
	public void setSaapumisaika(double saapumisaika) {
		this.saapumisaika = saapumisaika;
	}
	
	/**
	 * Palauttaa asiakkaan jonoon saapumisajan
	 *
	 * @return saapumisaika jonoon
	 */
	public double getSaapumisaikaJonoon() {
		return saapumisaikaJonoon;
	}
	
	/**
	 * Asettaa asiakkaan jonoon saapumisajan
	 *
	 * @param asiakkaan palvelupisteen jonoon saapumisen aika
	 */
	public void setSaapumisaikaJonoon(double saapumisaikaJonoon) {
		this.saapumisaikaJonoon = saapumisaikaJonoon;
	}
	
	/**
	 * Palauttaa asiakkaan jonosta poistumisajan
	 *
	 * @return poistumisaikaJonosta
	 */
	public double getPoistumisaikaJonosta() {
		return poistumisaikaJonosta;
	}
	
	/**
	 * Asettaa asiakkaan jonosta poistumisajan
	 *
	 * @param asiakkaan poistumisaika palvelupisteen jonosta
	 */
	public void setPoistumisaikaJonosta(double poistumisaikaJonosta) {
		this.poistumisaikaJonosta = poistumisaikaJonosta;
	}
	
	/**
	 * Asiakkaan simulaatiosta poistumisraportti
	 * Tulostaa konsoliin asiakkaan saapumis-, poistumis- ja viipymisajan
	 * Kasvattaa kasinolta lähteneiden asiakkaiden määrää, sekä kaikkien asiakkaiden yhteensä kasinolla viettämää aikaa
	 */
	public void raportti() {
		Trace.out(Trace.Level.INFO, "Asiakas " + id + " saapui: " + String.format("%.02f", saapumisaika));
		Trace.out(Trace.Level.INFO, "Asiakas " + id + " poistui: " + String.format("%.02f", poistumisaika));
		Trace.out(Trace.Level.INFO, "Asiakas " + id + " viipyi: " + String.format("%.02f", (poistumisaika-saapumisaika)));
		lahteneetAsiakkaat++;
		vietettyAika += (poistumisaika-saapumisaika);
	}
	
	/**
	 * Palauttaa asiakkaiden keskimäärin kasinolla viettämän ajan
	 *
	 * @return keskimaarainenVietettyAika
	 */
	public double getKeskimaarainenVietettyAika() {
		keskimaarainenVietettyAika = vietettyAika / lahteneetAsiakkaat;
		return keskimaarainenVietettyAika;
	}
	
	/**
	 * Antaa asiakkaalle poletteja
	 */
	public void annaPolettejaPalvelutiskilla() {
		// Asiakkalle annetaan poletteja 100-1000
		int polettimaara;
		polettimaara = (int) Math.floor(Math.random() * (10 - 1 + 1) + 1) * 100;
		nykyinenPolettimaara += polettimaara;
		alkuperainenPolettimaara += polettimaara;
	}
	
	/**
	 * Palauttaa Asiakkaan palvelutiskiltä nostamien polettejen määrän
	 *
	 * @return alkuperainenPoletimaara
	 */
	public int getAlkuperainenPolettimaara() {
		return alkuperainenPolettimaara;
	}
	
	/**
	 * Palauttaa asiakkaalla olevien polettien määrän
	 *
	 * @return nykyinenPolettimaara
	 */
	public int getNykyinenPolettimaara() {
		return nykyinenPolettimaara;
	}

	/**
	 * Lisää asiakkaalle poletteja
	 *
	 * @param lisättävien polettien määrä
	 */
	public void lisaaPoletteja(int polettimaara) {
		nykyinenPolettimaara += polettimaara;
	}
	
	/**
	 * vähentää asiakkaalla olevia poletteja
	 *
	 * @param vähennettävien polettien määrä
	 */
	public void vahennaPoletteja(int polettimaara) {
		nykyinenPolettimaara -= polettimaara;
	}
}