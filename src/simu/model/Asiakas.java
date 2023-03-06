package simu.model;

import simu.framework.Kello;
import simu.framework.Trace;


// TODO:
// Asiakas koodataan simulointimallin edellyttämällä tavalla (data!)
public class Asiakas {
	
	private double saapumisaika; // saapuminen Kasinoon
	private double poistumisaika; // poistuminen Kasinosta
	private double saapumisaikaJonoon;
	private double poistumisaikaJonosta;
	private int id;
	private static int saapuneetAsiakkaat = 1;
	private static int lahteneetAsiakkaat = 0;
	private static double vietettyAika = 0;
	private double keskimaarainenVietettyAika;
	private int alkuperainenPolettimaara = 0;
	private int nykyinenPolettimaara = 0;
	
	public Asiakas() {
	    id = saapuneetAsiakkaat++;
		saapumisaika = Kello.getInstance().getAika();
		Trace.out(Trace.Level.INFO, "Uusi asiakas: " + id + ": " + String.format("%.02f", saapumisaika));
	}

	public int getId() {
		return id;
	}
	
	public int getSaapuneetAsiakkaat() {
		return saapuneetAsiakkaat;
	}
	
	public double getPoistumisaika() {
		return poistumisaika;
	}

	public void setPoistumisaika(double poistumisaika) {
		this.poistumisaika = poistumisaika;
	}

	public double getSaapumisaika() {
		return saapumisaika;
	}

	public void setSaapumisaika(double saapumisaika) {
		this.saapumisaika = saapumisaika;
	}
	
	public double getSaapumisaikaJonoon() {
		return saapumisaikaJonoon;
	}
	
	public void setSaapumisaikaJonoon(double saapumisaikaJonoon) {
		this.saapumisaikaJonoon = saapumisaikaJonoon;
	}
	
	public double getPoistumisaikaJonosta() {
		return poistumisaikaJonosta;
	}
	
	public void setPoistumisaikaJonosta(double poistumisaikaJonosta) {
		this.poistumisaikaJonosta = poistumisaikaJonosta;
	}
	
	public void raportti() {
		Trace.out(Trace.Level.INFO, "Asiakas " + id + " saapui: " + String.format("%.02f", saapumisaika));
		Trace.out(Trace.Level.INFO, "Asiakas " + id + " poistui: " + String.format("%.02f", poistumisaika));
		Trace.out(Trace.Level.INFO, "Asiakas " + id + " viipyi: " + String.format("%.02f", (poistumisaika-saapumisaika)));
		lahteneetAsiakkaat++;
		vietettyAika += (poistumisaika-saapumisaika);
	}
	
	public double getKeskimaarainenVietettyAika() {
		keskimaarainenVietettyAika = vietettyAika / lahteneetAsiakkaat;
		return keskimaarainenVietettyAika;
	}
	
	public void annaPolettejaPalvelutiskilla() {
		// Asiakkalle annetaan poletteja 100-1000
		int polettimaara;
		polettimaara = (int) Math.floor(Math.random() * (10 - 1 + 1) + 1) * 100;
		nykyinenPolettimaara += polettimaara;
		alkuperainenPolettimaara += polettimaara;
	}
	
	public int getAlkuperainenPolettimaara() {
		return alkuperainenPolettimaara;
	}
	
	public int getNykyinenPolettimaara() {
		return nykyinenPolettimaara;
	}

	public void lisaaPoletteja(int polettimaara) {
		nykyinenPolettimaara += polettimaara;
	}
	
	public void vahennaPoletteja(int polettimaara) {
		nykyinenPolettimaara -= polettimaara;
	}
}