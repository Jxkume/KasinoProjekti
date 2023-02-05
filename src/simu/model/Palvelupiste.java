package simu.model;

import java.util.ArrayList;
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
	private String palvelupisteNimi;
	private static int palveltujenAsiakkaidenLKM = 0;
	private static int aktiiviAika = 0;
	private int keskiMaarainenJononPituus = 0;
	private double palvelunKesto;
	private double vasteAika = 0;
	private double suoritusTeho = 0;
	private double palvelupisteenKayttoaste = 0;
	private double palvelupisteenKeskiMaarainenPalveluAika = 0;
	private double keskiMaarainenVasteAika = 0;
	private static double kokonaisOleskeluAika = 0;
	private ArrayList<Integer> idList =  new ArrayList<>();
	private TapahtumanTyyppi skeduloitavanTapahtumanTyyppi; 
	
	//JonoStartegia strategia; //optio: asiakkaiden järjestys
	
	private boolean varattu = false;


	public Palvelupiste(ContinuousGenerator generator, Tapahtumalista tapahtumalista, TapahtumanTyyppi tyyppi, String palvelupisteNimi, double palvelunKesto){
		this.tapahtumalista = tapahtumalista;
		this.generator = generator;
		this.skeduloitavanTapahtumanTyyppi = tyyppi;
		this.palvelupisteNimi = palvelupisteNimi;
		this.palvelunKesto = palvelunKesto;
				
	}

	public String getPalvelupisteNimi() {
		return palvelupisteNimi;
	}
	
	public void lisaaJonoon(Asiakas a){   // Jonon 1. asiakas aina palvelussa
		jono.add(a);
		
	}

	public Asiakas otaJonosta(){  // Poistetaan palvelussa ollut
		varattu = false;
		return jono.poll();
	}

	public void aloitaPalvelu(){  //Aloitetaan uusi palvelu, asiakas on jonossa palvelun aikana
		Trace.out(Trace.Level.INFO, "Aloitetaan uusi palvelu asiakkaalle " + jono.peek().getId());
		
		varattu = true;
		double palveluaika = generator.sample();
		tapahtumalista.lisaa(new Tapahtuma(skeduloitavanTapahtumanTyyppi,Kello.getInstance().getAika()+palveluaika));
		
		if(!idList.contains(jono.peek().getId())) {
			idList.add(jono.peek().getId());
			palveltujenAsiakkaidenLKM++;
		}
		
		
	}
	
	public void polettienNosto(int poletit) {
		jono.peek().muutaPolettiMaara(poletit);
	}
	
	public void pelaa() { // Asiakas pelaa pelin
		System.out.println("Asiakas " + jono.peek().getId() + " pelaa yhden pelin");
		
		int randomLuku = (int) Math.floor(Math.random() * 2);
		if (randomLuku == 1) {
			System.out.println("Asiakas " + jono.peek().getId() + " voitti 10 polettia");
			jono.peek().muutaPolettiMaara(10);
		} else {
			System.out.println("Asiakas " + jono.peek().getId() + " hävisi 10 polettia");
			jono.peek().muutaPolettiMaara(-10);

		}
	}
	
	public void nostaVoitot() { // HUOM: TARKISTAA TULOSTEET
		if(jono.peek().getPoletit() < 50) {
			System.out.println("Asiakas " + jono.peek().getId() + " hävisi yhteensä " + (50 - jono.peek().getPoletit()) + " polettia");
		} else if (jono.peek().getPoletit() > 50)  {
			System.out.println("Asiakas " + jono.peek().getId() +" voitti yhteensä " + (jono.peek().getPoletit() - 50) + " polettia");
		} else {
			System.out.println("Asiakas " + jono.peek().getId() + " ei voittanut yhtään polettia");
		}
	}
	public boolean onVarattu(){
		return varattu;
	}


	public boolean onJonossa(){
		return jono.size() != 0;
	}

}
