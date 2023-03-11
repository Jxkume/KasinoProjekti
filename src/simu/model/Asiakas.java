package simu.model;

import simu.framework.Kello;
import simu.framework.Trace;

/**
 * Luokka Asiakkaalle
 * 
 * @author Tapio Humaljoki, Valtteri Kuitula ja Jhon Rastrojo
 */
public class Asiakas {
	
	/** Asiakkaan saapumisaika */
	private double saapumisaika;
	
	/** Asiakkaan poistumisaika */
	private double poistumisaika;
	
	/** Aika, jolloin asiakas saapuu jonoon */
	private double saapumisaikaJonoon;
	
	/** Aika, jolloin asiakas poistuu jonosta */
	private double poistumisaikaJonosta;
	
	/** Asiakkaan id */
	private int id;
	
	/** Kasinolle saapuneiden asiakkaiden maara */
	private static int saapuneetAsiakkaat = 1;
	
	/** Kasinolta lahteneiden asiakkaiden maara */
	private static int lahteneetAsiakkaat = 0;
	
	/** Asiakkaan kasinolla viettama aika */
	private static double vietettyAika = 0;
	
	/** Kaikkien asiakkaiden kasinolla keskimaarin viettama aika */
	private double keskimaarainenVietettyAika;
	
	/** Polettimaara, jonka asiakas on nostanut palvelutiskilta */
	private int alkuperainenPolettimaara = 0;
	
	/** Asiakkaan tamanhetkinen polettimaara */
	private int nykyinenPolettimaara = 0;
	
	/**
	 * Asiakkaan kostruktori
	 * Tulostaa asiakkaan saapumisen konsoliin 
	 */
	public Asiakas() {
	    id = saapuneetAsiakkaat++;
		saapumisaika = Kello.getInstance().getAika();
		Trace.out(Trace.Level.INFO, "Uusi asiakas: " + id + ": " + String.format("%.02f", saapumisaika));
	}
	
	/**
	 * Lisaa asiakkaalle poletteja
	 */
	public void annaPolettejaPalvelutiskilla() {
		// Asiakkalle annetaan poletteja 100-1000
		int polettimaara;
		polettimaara = (int) Math.floor(Math.random() * (10 - 1 + 1) + 1) * 100;
		nykyinenPolettimaara += polettimaara;
		alkuperainenPolettimaara += polettimaara;
	}
	
	/**
	 * Lisaa asiakkaalle poletteja
	 *
	 * @param lisattavien polettien maara
	 */
	public void lisaaPoletteja(int polettimaara) {
		nykyinenPolettimaara += polettimaara;
	}
	
	/**
	 * Vahentaa asiakkaalla olevien polettien maaraa
	 *
	 * @param vahennettavien polettien maara
	 */
	public void vahennaPoletteja(int polettimaara) {
		nykyinenPolettimaara -= polettimaara;
	}
	
	/**
	 * Tulostaa raportin asiakkaan poistuttua kasinolta
	 */
	public void raportti() {
		Trace.out(Trace.Level.INFO, "Asiakas " + id + " saapui: " + String.format("%.02f", saapumisaika));
		Trace.out(Trace.Level.INFO, "Asiakas " + id + " poistui: " + String.format("%.02f", poistumisaika));
		Trace.out(Trace.Level.INFO, "Asiakas " + id + " viipyi: " + String.format("%.02f", (poistumisaika-saapumisaika)));
		// Lisataan lahteneiden asiakkaiden maaraa
		lahteneetAsiakkaat++;
		// Lisataan asiakkaiden kasinolla viettamaa kokonaisaikaa
		vietettyAika += (poistumisaika-saapumisaika);
	}

	/**
	 * Palauttaa asiakkaan id:n
	 *
	 * @return asiakkaan id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Palauttaa kasinolle saapuneiden asiakkaiden maaran
	 *
	 * @return kasinolle saapuneet asiakkaat
	 */
	public int getSaapuneetAsiakkaat() {
		return saapuneetAsiakkaat;
	}
	
	/**
	 * Palauttaa asiakkaan poistumisajan
	 *
	 * @return asiakkaan poistumisaika
	 */
	public double getPoistumisaika() {
		return poistumisaika;
	}

	/**
	 * Asettaa asiakkaan poistumisajan
	 *
	 * @param asiakkaan palvelupisteelta poistumisen aika
	 */
	public void setPoistumisaika(double poistumisaika) {
		this.poistumisaika = poistumisaika;
	}

	/**
	 * Palauttaa asiakkaan saapumisajan
	 *
	 * @return asiakkaan saapumisaika
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
	 * Palauttaa asiakkaan saapumisajan jonoon
	 *
	 * @return asiakkaan saapumisaika jonoon
	 */
	public double getSaapumisaikaJonoon() {
		return saapumisaikaJonoon;
	}
	
	/**
	 * Asettaa asiakkaan saapumisajan jonoon
	 *
	 * @param asiakkaan saapumisaika palvelupisteen jonoon
	 */
	public void setSaapumisaikaJonoon(double saapumisaikaJonoon) {
		this.saapumisaikaJonoon = saapumisaikaJonoon;
	}
	
	/**
	 * Palauttaa asiakkaan poistumisajan jonosta
	 *
	 * @return asiakkaan poistumisaika palvelupisteen jonosta
	 */
	public double getPoistumisaikaJonosta() {
		return poistumisaikaJonosta;
	}
	
	/**
	 * Asettaa asiakkaan poistumisajan jonosta
	 *
	 * @param asiakkaan poistumisaika palvelupisteen jonosta
	 */
	public void setPoistumisaikaJonosta(double poistumisaikaJonosta) {
		this.poistumisaikaJonosta = poistumisaikaJonosta;
	}
	
	/**
	 * Palauttaa asiakkaiden keskimaarin kasinolla viettaman ajan
	 *
	 * @return asiakkaiden keskimaarin viettama aika kasinolla
	 */
	public double getKeskimaarainenVietettyAika() {
		keskimaarainenVietettyAika = vietettyAika / lahteneetAsiakkaat;
		return keskimaarainenVietettyAika;
	}
	
	/**
	 * Palauttaa asiakkaan kasinon palvelutiskilta nostamien polettien maaran
	 *
	 * @return asiakkaan palvelutiskilta nostamien polettien maara
	 */
	public int getAlkuperainenPolettimaara() {
		return alkuperainenPolettimaara;
	}
	
	/**
	 * Palauttaa asiakkaan tamanhetkisen polettimaaran
	 *
	 * @return asiakkaan tamanhetkinen polettimaara
	 */
	public int getNykyinenPolettimaara() {
		return nykyinenPolettimaara;
	}

}