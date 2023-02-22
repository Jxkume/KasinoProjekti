package simu.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import eduni.distributions.ContinuousGenerator;
import simu.framework.Kello;
import simu.framework.Moottori;
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
	private static int talonVoittoEuroina = 0;
	private ArrayList<Asiakas> kaynteja;
	private double suoritusteho;

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
	
	public void laskeSuoritusteho(double simulointiaika) {
		suoritusteho = palvellutAsiakkaat / simulointiaika;
		System.out.println("Palvelupisteen suoritusteho on " + suoritusteho);
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
				talonVoittoEuroina -= (asiakas.getNykyinenPolettimaara() - asiakas.getAlkuperainenPolettimaara());
			} else if (asiakas.getNykyinenPolettimaara() < asiakas.getAlkuperainenPolettimaara()) {
				System.out.println("Asiakas " + asiakas.getId() + " hävisi poletteja kasinolla yhteensä " + (asiakas.getAlkuperainenPolettimaara() - asiakas.getNykyinenPolettimaara()) + ".");
				talonVoittoEuroina += (asiakas.getAlkuperainenPolettimaara() - asiakas.getNykyinenPolettimaara());
			} else {
				System.out.println("Asiakas " + asiakas.getId() + " ei voittanut tai hävinnyt poletteja kasinolla.");
			}
			// Tulostetaan kasinon voitot
			if (talonVoittoEuroina > 0) {
				System.out.println("Kasino on tehnyt voittoa tähän mennessä yhteensä " + talonVoittoEuroina + " euroa.");
			} else {
				System.out.println("Kasino on tehnyt liiketappiota tähän mennessä yhteensä " + (talonVoittoEuroina * -1) + " euroa.");
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
		
		// Tässä jotain todennäköisyyksiä ja voittosummia peleihin, näitä voi vapaasti muutella - Valdo
		int todennakoisyysVoittoon;
		int polettimaara;
		
		switch (nimi) {
			
			case "Ruletti":
				// Ruletissa 5% mahdollisuus voittoon
				todennakoisyysVoittoon = (int) Math.floor(Math.random() * (50 - 1 + 1) + 1);
				if (todennakoisyysVoittoon == 1) {
					// Voittosumma on 10-50 polettia.
					polettimaara = (int) Math.floor(Math.random() * (5 - 1 + 1) + 1) * 10;
					System.out.println("Asiakas " + asiakas.getId() + " voitti ruletissa " + polettimaara + " polettia!");
					asiakas.lisaaPoletteja(polettimaara);
					System.out.println("Asiakkaalla " + asiakas.getId() + " poletteja yhteensä " + asiakas.getNykyinenPolettimaara() + ".");
					return true;
				} else {
					System.out.println("Asiakas " + asiakas.getId() + " hävisi pelin.");
					// Asiakas häviää 10 polettia.
					asiakas.vahennaPoletteja(10);
					System.out.println("Asiakkaalla " + asiakas.getId() + " poletteja yhteensä " + asiakas.getNykyinenPolettimaara() + ".");
					return false;
				}
				
			case "Blackjack":
				// Blackjackissa 20% mahdollisuus voittoon
				todennakoisyysVoittoon = (int) Math.floor(Math.random() * (5 - 1 + 1) + 1);
				if (todennakoisyysVoittoon == 1) {
					// Voittosumma on 10-50 polettia.
					polettimaara = (int) Math.floor(Math.random() * (5 - 1 + 1) + 1) * 10;
					System.out.println("Asiakas " + asiakas.getId() + " voitti Blackjackissä " + polettimaara + " polettia!");
					asiakas.lisaaPoletteja(polettimaara);
					System.out.println("Asiakkaalla " + asiakas.getId() + " poletteja yhteensä " + asiakas.getNykyinenPolettimaara() + ".");
					return true;
				} else {
					System.out.println("Asiakas " + asiakas.getId() + " hävisi pelin.");
					// Asiakas häviää 10 polettia.
					asiakas.vahennaPoletteja(10);
					System.out.println("Asiakkaalla " + asiakas.getId() + " poletteja yhteensä " + asiakas.getNykyinenPolettimaara() + ".");
					return false;
				}
				
			case "Kraps":
				// Krapsissä 10% mahdollisuus voittoon
				todennakoisyysVoittoon = (int) Math.floor(Math.random() * (10 - 1 + 1) + 1);
				if (todennakoisyysVoittoon == 1) {
					// Voittosumma on 10-50 polettia.
					polettimaara = (int) Math.floor(Math.random() * (5 - 1 + 1) + 1) * 10;
					System.out.println("Asiakas " + asiakas.getId() + " voitti Krapsissä " + polettimaara + " polettia!");
					asiakas.lisaaPoletteja(polettimaara);
					System.out.println("Asiakkaalla " + asiakas.getId() + " poletteja yhteensä " + asiakas.getNykyinenPolettimaara() + ".");
					return true;
				} else {
					System.out.println("Asiakas " + asiakas.getId() + " hävisi pelin.");
					// Asiakas häviää 10 polettia.
					asiakas.vahennaPoletteja(10);
					System.out.println("Asiakkaalla " + asiakas.getId() + " poletteja yhteensä " + asiakas.getNykyinenPolettimaara() + ".");
					return false;
				}
		}
		
		return false;
	}
	
	public void jatkaPelaamista(Asiakas asiakas) {
		System.out.println("Asiakas " + asiakas.getId() + " jatkaa pelaamista.");
		varattu = false;
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
	
	public int getTalonVoittoEuroina() {
		return talonVoittoEuroina;
	}
	
}