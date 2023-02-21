package simu.model;

import simu.framework.Kello;
import simu.framework.Trace;


// TODO:
// Asiakas koodataan simulointimallin edellyttämällä tavalla (data!)
public class Asiakas {
	
	private double saapumisaika;
	private double poistumisaika;
	private int id;
	private static int i = 1;
	private static long sum = 0;
	private int alkuperainenPolettimaara = 0;
	private int nykyinenPolettimaara = 0;
	
	public Asiakas() {
	    id = i++;
		saapumisaika = Kello.getInstance().getAika();
		Trace.out(Trace.Level.INFO, "Uusi asiakas: " + id + ": " + String.format("%.02f", saapumisaika));
	}

	public int getId() {
		return id;
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
	
	public void raportti() {
		Trace.out(Trace.Level.INFO, "Asiakas " + id + " saapui: " + String.format("%.02f", saapumisaika));
		Trace.out(Trace.Level.INFO, "Asiakas " + id + " poistui: " + String.format("%.02f", poistumisaika));
		Trace.out(Trace.Level.INFO, "Asiakas " + id + " viipyi: " + String.format("%.02f", (poistumisaika-saapumisaika)));
		sum += (poistumisaika-saapumisaika);
		double keskiarvo = sum/id;
		System.out.println("Asiakkaiden läpimenoaikojen keskiarvo tähän mennessä: " + keskiarvo);
	}
	
	public void annaPoletteja() {
		int polettimaara;
		polettimaara = (int) Math.floor(Math.random() * (10 - 1 + 1) + 1) * 10;
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