package simu.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import eduni.distributions.ContinuousGenerator;
import simu.framework.Kello;
import simu.framework.Tapahtuma;
import simu.framework.Tapahtumalista;
import simu.framework.Trace;

// TODO:
// Palvelupistekohtaiset toiminnallisuudet, laskutoimitukset (+ tarvittavat muuttujat) ja raportointi koodattava
public class Palvelupiste {

	private LinkedList<Asiakas> jono = new LinkedList<Asiakas>(); // Tietorakennetoteutus
	
	private ContinuousGenerator generator;
	private Tapahtumalista tapahtumalista;
	private TapahtumanTyyppi skeduloitavanTapahtumanTyyppi;
	private String nimi;
	private int palvellutAsiakkaat = 0;
	private static int talonPoletit = 0;
	private ArrayList<Asiakas> kaynteja;
	
	
	
	//JonoStrategia strategia; //optio: asiakkaiden j채rjestys
	
	private boolean varattu = false;

	public Palvelupiste(ContinuousGenerator generator, Tapahtumalista tapahtumalista, TapahtumanTyyppi tyyppi, String nimi) {
		this.tapahtumalista = tapahtumalista;
		this.generator = generator;
		this.skeduloitavanTapahtumanTyyppi = tyyppi;
		this.nimi = nimi;
		kaynteja = new ArrayList<>();
	}

	public ArrayList<Asiakas> getKaynteja() {
		return kaynteja;
	}

	public void setKaynteja(ArrayList<Asiakas> kaynteja) {
		this.kaynteja = kaynteja;
	}

	public ContinuousGenerator getGenerator() {
		return generator;
	}

	public Tapahtumalista getTapahtumalista() {
		return tapahtumalista;
	}

	public TapahtumanTyyppi getSkeduloitavanTapahtumanTyyppi() {
		return skeduloitavanTapahtumanTyyppi;
	}

	public String getNimi() {
		return nimi;
	}

	public int getPalvellutAsiakkaat() {
		return palvellutAsiakkaat;
	}

	public boolean isVarattu() {
		return varattu;
	}

	public void lisaaJonoon(Asiakas asiakas) {   // Jonon 1. asiakas aina palvelussa
		
		jono.add(asiakas);
		if (nimi.equals("Palvelutiski")) {
			System.out.println("Asiakas " + asiakas.getId() + " saapuu palvelutiskin jonoon.");
		} else if (nimi.equals("Ruletti")) {
			System.out.println("Asiakas " + asiakas.getId() + " saapuu ruletin jonoon.");
		} else if (nimi.equals("Blackjack")) {
			System.out.println("Asiakas " + asiakas.getId() + " saapuu Blackjackin jonoon.");
		} else if (nimi.equals("Kraps")) {
			System.out.println("Asiakas " + asiakas.getId() + " saapuu Krapsin jonoon.");
		} else if (nimi.equals("Voittojen nostopiste")) {
			System.out.println("Asiakas " + asiakas.getId() + " saapuu voittojen nostopisteen jonoon.");
		}
	}

	public Asiakas otaJonosta(Asiakas asiakas) {  // Poistetaan palvelussa ollut
		
		palvellutAsiakkaat++;
		kaynteja.add(asiakas);
		
		if (nimi.equals("Palvelutiski")) {
			System.out.println("Asiakas " + asiakas.getId() + " poistuu palvelutiskin jonosta.");
			System.out.println("Asiakkaalla " + asiakas.getId() + " poletteja yhteensä " + asiakas.getNykyinenPolettimaara() + ".");
			System.out.println(nimi + " palvellut " + palvellutAsiakkaat + " asiakasta.");
		} else if (nimi.equals("Ruletti")) {
			System.out.println("Asiakas " + asiakas.getId() + " poistuu ruletin jonosta.");
			System.out.println(nimi + " palvellut " + palvellutAsiakkaat + " asiakasta.");
		} else if (nimi.equals("Blackjack")) {
			System.out.println("Asiakas " + asiakas.getId() + " poistuu Blackjackin jonosta.");
			System.out.println(nimi + " palvellut " + palvellutAsiakkaat + " asiakasta.");
		} else if (nimi.equals("Kraps")) {
			System.out.println("Asiakas " + asiakas.getId() + " poistuu Krapsin jonosta.");
			System.out.println(nimi + " palvellut " + palvellutAsiakkaat + " asiakasta.");
		} else if (nimi.equals("Voittojen nostopiste")) {
			System.out.println("Asiakas " + asiakas.getId() + " poistuu voittojen nostopisteen jonosta.");
			System.out.println(nimi + " palvellut " + palvellutAsiakkaat + " asiakasta.");
			if (asiakas.getNykyinenPolettimaara() > asiakas.getAlkuperainenPolettimaara()) {
				System.out.println("Asiakas " + asiakas.getId() + " voitti poletteja kasinolla yhteensä " + (asiakas.getNykyinenPolettimaara() - asiakas.getAlkuperainenPolettimaara()) + ".");
			} else if (asiakas.getNykyinenPolettimaara() < asiakas.getAlkuperainenPolettimaara()) {
				System.out.println("Asiakas " + asiakas.getId() + " hävisi poletteja kasinolla yhteensä " + (asiakas.getAlkuperainenPolettimaara() - asiakas.getNykyinenPolettimaara()) + ".");
			} else {
				System.out.println("Asiakas " + asiakas.getId() + " ei voittanut tai hävinnyt poletteja kasinolla.");
			}
		}
		varattu = false;
		
		return jono.poll(); 
	}

	public void aloitaPalvelu() {  //Aloitetaan uusi palvelu, asiakas on jonossa palvelun aikana
		varattu = true;
		double palveluaika = generator.sample();
		tapahtumalista.lisaa(new Tapahtuma(skeduloitavanTapahtumanTyyppi, Kello.getInstance().getAika()+palveluaika));
	}

	public boolean onVarattu() {
		return varattu;
	}

	public boolean onJonossa() {
		return jono.size() != 0;
	}
	
	public LinkedList<Asiakas> getJono() {
		return jono;
	}
	
	public boolean voittikoAsiakas(Asiakas asiakas) {
		int random = (int) Math.floor(Math.random() * (2 - 1 + 1) + 1);
		if (random == 1) {
			System.out.println("Asiakas " + asiakas.getId() + " voitti pelin!");
			asiakas.lisaaPoletteja(10);
			// Uusi metodi - Jhon
			laskeTalonHaviot(10);
			System.out.println("Asiakkaalla " + asiakas.getId() + " poletteja yhteensä " + asiakas.getNykyinenPolettimaara() + ".");
			return true;
		} else {
			System.out.println("Asiakas " + asiakas.getId() + " hävisi pelin.");
			asiakas.vahennaPoletteja(10);
			// Uusi metodi - Jhon
			laskeTalonVoitot(10);
			System.out.println("Asiakkaalla " + asiakas.getId() + " poletteja yhteensä " + asiakas.getNykyinenPolettimaara() + ".");
			return false;
		}
	}
	
	public void jatkaPelaamista(Asiakas asiakas) {
		// Asiakas poistetaan jonosta ja lisätään takaisin samalle paikalle
		System.out.println("Asiakas " + asiakas.getId() + " jatkaa pelaamista.");
		varattu = false;
	}
	// Uusi metodi - Jhon
	public void laskeTalonVoitot(int polettimaara) {
		
		talonPoletit += polettimaara;
	}
	// Uusi metodi - Jhon
	public void laskeTalonHaviot(int polettimaara) {
		
		talonPoletit -= polettimaara;
	}
	// Uusi metodi - Jhon (Parametriks laitoin asiakas että saadan sieltä annaPolettissa olevaa talonTappioMaara)
	public void talonRaportti(Asiakas asiakas) {
		int talonLopullinenPolettiMaara = talonPoletit += asiakas.getTalonTappioMaara();
		if(talonLopullinenPolettiMaara < 0) {
			Trace.out(Trace.Level.INFO,"Talo päättyi päivän " + talonLopullinenPolettiMaara + " polettien tappiolla");
		} else {
			Trace.out(Trace.Level.INFO,"Talo päättyi päivän " + talonLopullinenPolettiMaara + " polettien voitolla");
		}
	}
	public void tulostaJononAsiakkaat() {

		String asiakkaat = null;
		int[] asiakkaatJonossa = new int[jono.size()];

		if (nimi.equals("Palvelutiski")) {
			asiakkaat = "Asiakkaat palvelutiskin jonossa: ";
		} else if (nimi.equals("Ruletti")) {
			asiakkaat = "Asiakkaat ruletin jonossa: ";
		} else if (nimi.equals("Blackjack")) {
			asiakkaat = "Asiakkaat Blackjackin jonossa: ";
		} else if (nimi.equals("Kraps")) {
			asiakkaat = "Asiakkaat Krapsin jonossa: ";
		} else if (nimi.equals("Voittojen nostopiste")) {
			asiakkaat = "Asiakkaat voittojen nostopisteen jonossa: ";
		}

		for (int i = 0; i < jono.size(); i++) {
			asiakkaatJonossa[i] = jono.get(i).getId();
		}

		System.out.println(asiakkaat + Arrays.toString(asiakkaatJonossa).replace("[", "").replace("]", "") + ".");
	}
	
}