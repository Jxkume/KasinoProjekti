package simu.model;

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
	
	//JonoStrategia strategia; //optio: asiakkaiden järjestys
	
	private boolean varattu = false;

	public Palvelupiste(ContinuousGenerator generator, Tapahtumalista tapahtumalista, TapahtumanTyyppi tyyppi) {
		this.tapahtumalista = tapahtumalista;
		this.generator = generator;
		this.skeduloitavanTapahtumanTyyppi = tyyppi;		
	}

	public void lisaaJonoon(Asiakas a) {   // Jonon 1. asiakas aina palvelussa
		jono.add(a);
	}

	public Asiakas otaJonosta() {  // Poistetaan palvelussa ollut
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
			System.out.println("Asiakkaalla " + asiakas.getId() + " poletteja yhteensä " + asiakas.getPolettimaara());
			return true;
		} else {
			System.out.println("Asiakas " + asiakas.getId() + " hävisi pelin..");
			asiakas.vahennaPoletteja(10);
			System.out.println("Asiakkaalla " + asiakas.getId() + " poletteja yhteensä " + asiakas.getPolettimaara());
			return false;
		}
	}
	
	public void jatkaPelaamista(Asiakas a) {
		jono.addFirst(a);
	}
	
	public String toString() {
		String string = "";
		for (int i = 0; i < jono.size(); i++) {
			string += jono.get(i).getId() + " ";
		}
		return string;
	}
}
