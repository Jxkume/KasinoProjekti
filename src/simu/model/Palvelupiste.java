package simu.model;

import java.util.Arrays; 
import java.util.LinkedList;
import eduni.distributions.ContinuousGenerator;
import simu.framework.Kello;
import simu.framework.Tapahtuma;
import simu.framework.Tapahtumalista;

// TODO:
// Palvelupistekohtaiset toiminnallisuudet, laskutoimitukset (+ tarvittavat muuttujat) ja raportointi koodattava
public class Palvelupiste {

	private LinkedList<Asiakas> jono = new LinkedList<Asiakas>(); // Tietorakennetoteutus
	
	private ContinuousGenerator generator;
	private Tapahtumalista tapahtumalista;
	private TapahtumanTyyppi skeduloitavanTapahtumanTyyppi;
	private String nimi;
	
	//JonoStrategia strategia; //optio: asiakkaiden järjestys
	
	private boolean varattu = false;

	public Palvelupiste(ContinuousGenerator generator, Tapahtumalista tapahtumalista, TapahtumanTyyppi tyyppi, String nimi) {
		this.tapahtumalista = tapahtumalista;
		this.generator = generator;
		this.skeduloitavanTapahtumanTyyppi = tyyppi;	
		this.nimi = nimi;
	}

	public void lisaaJonoon(Asiakas asiakas) {   // Jonon 1. asiakas aina palvelussa
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
		jono.add(asiakas);
	}

	public Asiakas otaJonosta(Asiakas asiakas) {  // Poistetaan palvelussa ollut
		if (nimi.equals("Palvelutiski")) {
			System.out.println("Asiakas " + asiakas.getId() + " poistuu palvelutiskin jonosta.");
		} else if (nimi.equals("Ruletti")) {
			System.out.println("Asiakas " + asiakas.getId() + " poistuu ruletin jonosta.");
		} else if (nimi.equals("Blackjack")) {
			System.out.println("Asiakas " + asiakas.getId() + " poistuu Blackjackin jonosta.");
		} else if (nimi.equals("Kraps")) {
			System.out.println("Asiakas " + asiakas.getId() + " poistuu Krapsin jonosta.");
		} else if (nimi.equals("Voittojen nostopiste")) {
			System.out.println("Asiakas " + asiakas.getId() + " poistuu voittojen nostopisteen jonosta.");
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
	
	public boolean pelaa(Asiakas asiakas) {
		int random = (int) Math.floor(Math.random() * (2 - 1 + 1) + 1);
		if (random == 1) {
			System.out.println("Asiakas " + asiakas.getId() + " voitti pelin!");
			asiakas.lisaaPoletteja(10);
			System.out.println("Asiakkaalla " + asiakas.getId() + " poletteja yhteensä " + asiakas.getPolettimaara() + ".");
			return true;
		} else {
			System.out.println("Asiakas " + asiakas.getId() + " hävisi pelin.");
			asiakas.vahennaPoletteja(10);
			System.out.println("Asiakkaalla " + asiakas.getId() + " poletteja jäljellä yhteensä " + asiakas.getPolettimaara() + ".");
			return false;
		}
	}
	
	public void jatkaPelaamista(Asiakas asiakas) {
		// Asiakas poistetaan jonosta ja lisätään takaisin samalle paikalle
		jono.poll();
		jono.addFirst(asiakas);
		System.out.println("Asiakas " + asiakas.getId() + " jatkaa pelaamista.");
	}
	
	public boolean siirtyykoVoittojenNostopisteelle(Asiakas asiakas) {
        int erotus = asiakas.getAlkuperainenPolettimaara() - asiakas.getNykyinenPolettimaara();
        if (erotus < 0) {
            erotus = erotus * (-1);
        }
        int arpa = (int) Math.floor(Math.random() * ((asiakas.getAlkuperainenPolettimaara() - 1) - 0) + 1) + erotus;
        if (arpa >= asiakas.getAlkuperainenPolettimaara()) {
            return true;
        } else {
            return false;
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
