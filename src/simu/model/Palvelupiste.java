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
	private ArrayList<Asiakas> kaynteja = new ArrayList<>();
	private Kello kello = Kello.getInstance();
	private ContinuousGenerator generator;
	private Tapahtumalista tapahtumalista;
	private TapahtumanTyyppi skeduloitavanTapahtumanTyyppi;
	private String nimi;
	private static int talonVoittoEuroina;
	private int palvellutAsiakkaat;
	private double suoritusteho;
	private double aktiiviaika;
	private double kayttoaste;
	private double keskimaarainenPalveluaika;
	private double lapimenoaika;
	private double kokonaisoleskeluaika;
	private double keskimaarainenLapimenoaika;
	private double keskimaarainenJononpituus;
	
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
		
		// Asetetaan asiakkaalle poistumisaika
		asiakas.setPoistumisaikaJonosta(kello.getAika());
		// Lasketaan kyseisen asiakkaan läpimenoaika
		laskeLapimenoaika(asiakas);
		// Päivitetään kokonaisoleskeluaikaa
		laskeKokonaisoleskeluaika();
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
	
	public boolean voittikoAsiakas(Asiakas asiakas) {
		
		// Tässä jotain todennäköisyyksiä ja voittosummia peleihin, näitä voi vapaasti muutella - Valdo
		int todennakoisyysVoittoon;
		int polettimaara;
		
		switch (nimi) {
			
			case "Ruletti":
				// Ruletissa 50% mahdollisuus voittoon
				todennakoisyysVoittoon = (int) Math.floor(Math.random() * (2 - 1 + 1) + 1);
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