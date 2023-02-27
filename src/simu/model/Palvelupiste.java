package simu.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

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
	private ArrayList<Asiakas> kaynteja = new ArrayList<>();
	private Kello kello = Kello.getInstance();
	private ContinuousGenerator generator;
	private Tapahtumalista tapahtumalista;
	private TapahtumanTyyppi skeduloitavanTapahtumanTyyppi;
	private static int talonVoittoEuroina;
	
	private String nimi;
	private int palvellutAsiakkaat;
	private double keskimaarainenPalveluaika;
	private double keskimaarainenJononpituus;
	private double lapimenoaika;
	private double keskimaarainenLapimenoaika;
	private double suoritusteho;
	private double aktiiviaika;
	private double kayttoaste;
	private double kokonaisoleskeluaika;
	
	//JonoStrategia strategia; //optio: asiakkaiden j채rjestys
	private boolean varattu = false;

	public Palvelupiste(ContinuousGenerator generator, Tapahtumalista tapahtumalista, TapahtumanTyyppi tyyppi, String nimi) {
		this.tapahtumalista = tapahtumalista;
		this.generator = generator;
		this.skeduloitavanTapahtumanTyyppi = tyyppi;
		this.nimi = nimi;
	}

	public ArrayList<Asiakas> getKaynteja() {
		return kaynteja;
	}
	
	public double getSuoritusteho(double simulointiaika) {
		suoritusteho = palvellutAsiakkaat / simulointiaika;
		return suoritusteho;
	}
	
	public double getKayttoaste(double simulointiaika) {
		kayttoaste = aktiiviaika / simulointiaika;
		return kayttoaste;
	}
	
	public double getKeskimaarainenPalveluaika() {
		keskimaarainenPalveluaika = aktiiviaika / palvellutAsiakkaat;
		return keskimaarainenPalveluaika;
	}
	
	public void laskeKokonaisoleskeluaika() {
		kokonaisoleskeluaika += lapimenoaika;
	}
	
	public double getKokonaisoleskeluaika() {
		return kokonaisoleskeluaika;
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
	
	public double getAktiiviaika() {
		return aktiiviaika;
	}
	
	public void laskeLapimenoaika(Asiakas asiakas) {
		lapimenoaika = asiakas.getPoistumisaikaJonosta() - asiakas.getSaapumisaikaJonoon();
	}
	
	public double getLapimenoaika(Asiakas asiakas) {
		return lapimenoaika;
	}
	
	public double getKeskimaarainenLapimenoaika() {
		keskimaarainenLapimenoaika = kokonaisoleskeluaika / palvellutAsiakkaat;
		return keskimaarainenLapimenoaika;
	}
	
	public double getKeskimaarainenJononpituus(double simulointiaika) {
		keskimaarainenJononpituus = kokonaisoleskeluaika / simulointiaika;
		return keskimaarainenJononpituus;
	}

	public void lisaaJonoon(Asiakas asiakas) {   // Jonon 1. asiakas aina palvelussa
		
		// Asetetaan asiakkaalle saapumisaika
		asiakas.setSaapumisaikaJonoon(kello.getAika());
		jono.add(asiakas);
		
		if (nimi.equals("Palvelutiski")) {
			Trace.out(Trace.Level.INFO, "Asiakas " + asiakas.getId() + " saapuu palvelutiskin jonoon.");
		} else if (nimi.equals("Ruletti")) {
			Trace.out(Trace.Level.INFO, "Asiakas " + asiakas.getId() + " saapuu ruletin jonoon.");
		} else if (nimi.equals("Blackjack")) {
			Trace.out(Trace.Level.INFO, "Asiakas " + asiakas.getId() + " saapuu Blackjackin jonoon.");
		} else if (nimi.equals("Kraps")) {
			Trace.out(Trace.Level.INFO, "Asiakas " + asiakas.getId() + " saapuu Krapsin jonoon.");
		} else if (nimi.equals("Voittojen nostopiste")) {
			Trace.out(Trace.Level.INFO, "Asiakas " + asiakas.getId() + " saapuu voittojen nostopisteen jonoon.");
		}
	}

	public Asiakas otaJonosta(Asiakas asiakas) {  // Poistetaan palvelussa ollut
		
		// Asetetaan asiakkaalle poistumisaika
		asiakas.setPoistumisaikaJonosta(kello.getAika());
		// Lasketaan kyseisen asiakkaan läpimenoaika
		laskeLapimenoaika(asiakas);
		// Päivitetään kokonaisoleskeluaikaa
		laskeKokonaisoleskeluaika();
		palvellutAsiakkaat++;
		kaynteja.add(asiakas);
		
		if (nimi.equals("Palvelutiski")) {
			Trace.out(Trace.Level.INFO, "Asiakas " + asiakas.getId() + " poistuu palvelutiskin jonosta.");
			Trace.out(Trace.Level.INFO, "Asiakkaalla " + asiakas.getId() + " poletteja yhteensä " + asiakas.getNykyinenPolettimaara() + ".");
			Trace.out(Trace.Level.INFO, nimi + " palvellut yhteensä " + palvellutAsiakkaat + " asiakasta.");
		} else if (nimi.equals("Ruletti")) {
			Trace.out(Trace.Level.INFO, "Asiakas " + asiakas.getId() + " poistuu ruletin jonosta.");
			Trace.out(Trace.Level.INFO, nimi + " palvellut yhteensä " + palvellutAsiakkaat + " asiakasta.");
		} else if (nimi.equals("Blackjack")) {
			Trace.out(Trace.Level.INFO, "Asiakas " + asiakas.getId() + " poistuu Blackjackin jonosta.");
			Trace.out(Trace.Level.INFO, nimi + " palvellut yhteensä " + palvellutAsiakkaat + " asiakasta.");
		} else if (nimi.equals("Kraps")) {
			Trace.out(Trace.Level.INFO, "Asiakas " + asiakas.getId() + " poistuu Krapsin jonosta.");
			Trace.out(Trace.Level.INFO, nimi + " palvellut yhteensä " + palvellutAsiakkaat + " asiakasta.");
		} else if (nimi.equals("Voittojen nostopiste")) {
			Trace.out(Trace.Level.INFO, "Asiakas " + asiakas.getId() + " poistuu voittojen nostopisteen jonosta.");
			Trace.out(Trace.Level.INFO, nimi + " palvellut yhteensä " + palvellutAsiakkaat + " asiakasta.");
			if (asiakas.getNykyinenPolettimaara() > asiakas.getAlkuperainenPolettimaara()) {
				Trace.out(Trace.Level.INFO, "Asiakas " + asiakas.getId() + " voitti poletteja kasinolla yhteensä " + (asiakas.getNykyinenPolettimaara() - asiakas.getAlkuperainenPolettimaara()) + ".");
				talonVoittoEuroina -= (asiakas.getNykyinenPolettimaara() - asiakas.getAlkuperainenPolettimaara());
			} else if (asiakas.getNykyinenPolettimaara() < asiakas.getAlkuperainenPolettimaara()) {
				Trace.out(Trace.Level.INFO, "Asiakas " + asiakas.getId() + " hävisi poletteja kasinolla yhteensä " + (asiakas.getAlkuperainenPolettimaara() - asiakas.getNykyinenPolettimaara()) + ".");
				talonVoittoEuroina += (asiakas.getAlkuperainenPolettimaara() - asiakas.getNykyinenPolettimaara());
			} else {
				Trace.out(Trace.Level.INFO, "Asiakas " + asiakas.getId() + " ei voittanut tai hävinnyt poletteja kasinolla.");
			}
			// Tulostetaan kasinon voitot
			if (talonVoittoEuroina > 0) {
				Trace.out(Trace.Level.INFO, "Kasino on tehnyt voittoa tähän mennessä yhteensä " + talonVoittoEuroina + " euroa.");
			} else {
				Trace.out(Trace.Level.INFO, "Kasino on tehnyt liiketappiota tähän mennessä yhteensä " + (talonVoittoEuroina * -1) + " euroa.");
			}
		}
		varattu = false;
		return jono.poll(); 
	}

	public void aloitaPalvelu() {  //Aloitetaan uusi palvelu, asiakas on jonossa palvelun aikana
		varattu = true;
		double palveluaika = generator.sample();
		aktiiviaika += palveluaika;
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
	
	public Asiakas getJononEnsimmainen() {
		return jono.getFirst();
	}
	
	public int getTalonVoittoEuroina() {
		return talonVoittoEuroina;
	}
	
	public boolean voittikoAsiakas(Asiakas asiakas) {
		
		// Tässä jotain todennäköisyyksiä ja voittosummia peleihin, näitä voi vapaasti muutella - Valdo
		int todennakoisyysVoittoon;
		int polettimaara;
		
		switch (nimi) {
			
			case "Ruletti":
				// Ruletissa 50% mahdollisuus voittoon ja 50% häviöön
				todennakoisyysVoittoon = new Random().nextInt(10);
				polettimaara = (int) Math.floor(Math.random() * (10 - 5 + 1) + 5) * 10;
				if (todennakoisyysVoittoon < 5) {
					// Voittosumma on 50-100 polettia.
					Trace.out(Trace.Level.INFO, "Asiakas " + asiakas.getId() + " voitti ruletissa " + polettimaara + " polettia!");
					asiakas.lisaaPoletteja(polettimaara);
					Trace.out(Trace.Level.INFO, "Asiakkaalla " + asiakas.getId() + " poletteja yhteensä " + asiakas.getNykyinenPolettimaara() + ".");
					return true;
				} else { //lol
					Trace.out(Trace.Level.INFO, "Asiakas " + asiakas.getId() + " hävisi pelin.");
					// Asiakas häviää 50-100 polettia.
					asiakas.vahennaPoletteja(polettimaara);
					Trace.out(Trace.Level.INFO, "Asiakkaalla " + asiakas.getId() + " poletteja yhteensä " + asiakas.getNykyinenPolettimaara() + ".");
					return false;
				}
				
			case "Blackjack":
				// Blackjackissa 40% mahdollisuus voittoon ja 60% häviöön
				todennakoisyysVoittoon = new Random().nextInt(10);
				polettimaara = (int) Math.floor(Math.random() * (10 - 5 + 1) + 5) * 10;
				if (todennakoisyysVoittoon < 4) {
					// Voittosumma on 50-100 polettia.
					Trace.out(Trace.Level.INFO, "Asiakas " + asiakas.getId() + " voitti Blackjackissä " + polettimaara + " polettia!");
					asiakas.lisaaPoletteja(polettimaara);
					Trace.out(Trace.Level.INFO, "Asiakkaalla " + asiakas.getId() + " poletteja yhteensä " + asiakas.getNykyinenPolettimaara() + ".");
					return true;
				} else {
					Trace.out(Trace.Level.INFO, "Asiakas " + asiakas.getId() + " hävisi pelin.");
					// Asiakas häviää 50-100 polettia.
					asiakas.vahennaPoletteja(polettimaara);
					Trace.out(Trace.Level.INFO, "Asiakkaalla " + asiakas.getId() + " poletteja yhteensä " + asiakas.getNykyinenPolettimaara() + ".");
					return false;
				}
				
			case "Kraps":
				// Krapsissa on 50% mahdollisuus voittoon ja 50% häviöön (pyöreästi)
				Kraps.kahdenNopanSumma();
				polettimaara = (int) Math.floor(Math.random() * (10 - 5 + 1) + 5) * 10;
				if (Kraps.voittaako() == true) {
					// Voittosumma on 50-100 polettia.
					Trace.out(Trace.Level.INFO, "Asiakas " + asiakas.getId() + " voitti Krapsissä " + polettimaara + " polettia!");
					asiakas.lisaaPoletteja(polettimaara);
					Trace.out(Trace.Level.INFO, "Asiakkaalla " + asiakas.getId() + " poletteja yhteensä " + asiakas.getNykyinenPolettimaara() + ".");
					return true;
				} else {
					Trace.out(Trace.Level.INFO, "Asiakas " + asiakas.getId() + " hävisi pelin.");
					// Asiakas häviää 50-100 polettia.
					asiakas.vahennaPoletteja(polettimaara);
					Trace.out(Trace.Level.INFO, "Asiakkaalla " + asiakas.getId() + " poletteja yhteensä " + asiakas.getNykyinenPolettimaara() + ".");
					return false;
				}
		}
		return false;
	}
	
	public void jatkaPelaamista(Asiakas asiakas) {
		Trace.out(Trace.Level.INFO, "Asiakas " + asiakas.getId() + " jatkaa pelaamista.");
		varattu = false;
	}

	public String toString() {

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

		return asiakkaat + Arrays.toString(asiakkaatJonossa).replace("[", "").replace("]", "") + ".";
	}
	
}