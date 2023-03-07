package simu.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

import eduni.distributions.ContinuousGenerator;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import simu.framework.Kello;
import simu.framework.Tapahtuma;
import simu.framework.Tapahtumalista;
import simu.framework.Trace;
import view.SimulaattorinPaaikkunaKontrolleri;

// TODO:
// Palvelupistekohtaiset toiminnallisuudet, laskutoimitukset (+ tarvittavat muuttujat) ja raportointi koodattava

@Entity
@Table(name="palvelupiste")
public class Palvelupiste {
	
	@Transient
	private LinkedList<Asiakas> jono = new LinkedList<Asiakas>(); // Tietorakennetoteutus
	@Transient
	private ArrayList<Asiakas> kaynteja = new ArrayList<>();
	@Transient
	private Kello kello = Kello.getInstance();
	@Transient
	private ContinuousGenerator generator;
	@Transient
	private Tapahtumalista tapahtumalista;
	@Transient
	private TapahtumanTyyppi skeduloitavanTapahtumanTyyppi;

	private static int talonVoittoEuroina = 0;
	
	@Transient
    SimulaattorinPaaikkunaKontrolleri kontrolleri = new SimulaattorinPaaikkunaKontrolleri();
	
	@Id
    @Column(name="nimi")
	private String nimi;

	private double keskimaarainenJononpituus;
	@Transient
	private double lapimenoaika;
	
	private double keskimaarainenLapimenoaika;
	
	private double suoritusteho;
	
	private double aktiiviaika;
	
	private double kayttoaste;
	
	private int palvellutAsiakkaat;
	
	private double keskimaarainenPalveluaika;
	
	private double kokonaisoleskeluaika;
	
	@Transient
	private double simulointiAika;
	
	//JonoStrategia strategia; //optio: asiakkaiden j채rjestys
	@Transient
	private boolean varattu = false;

	public Palvelupiste(ContinuousGenerator generator, Tapahtumalista tapahtumalista, TapahtumanTyyppi tyyppi, String nimi) {
		this.tapahtumalista = tapahtumalista;
		this.generator = generator;
		this.skeduloitavanTapahtumanTyyppi = tyyppi;
		this.nimi = nimi;
	}
	public Palvelupiste() {
		
	}
	
	public int getPalvellutAsiakkaat() {
		return palvellutAsiakkaat;
	}
	
	public void setPalvellutAsiakkaat(int palvellutAsiakkaat) {
		this.palvellutAsiakkaat = palvellutAsiakkaat;
	}
	
	public ArrayList<Asiakas> getKaynteja() {
		return kaynteja;
	}
	
	public void setKayttoaste(double kayttoaste) {
		this.kayttoaste = kayttoaste;
	}
	
	public double getKayttoaste(double simulointiaika) {
		kayttoaste = aktiiviaika / simulointiaika;
		return kayttoaste;
	}
	
	public double getSuoritusteho(double simulointiaika) {
		suoritusteho = palvellutAsiakkaat / simulointiaika;
		return suoritusteho;
	}
	public void setSuoritusteho(double suoritusteho) {
		this.suoritusteho = suoritusteho;
	}
	public void setKeskimaarainenPalveluaika(double keskimaarainenPalveluaika) {
		this.keskimaarainenPalveluaika = keskimaarainenPalveluaika;
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
	
	public void setSimulointiaika(double simulointiAika) {
		this.simulointiAika = simulointiAika;
	}
	
	public double getSimulointiaika() {
		return simulointiAika;
	}
	public void setKokonaisoleskeluaika(double kokonaisoleskeluaika) {
		this.kokonaisoleskeluaika = kokonaisoleskeluaika;
	}
	public void setKaynteja(ArrayList<Asiakas> kaynteja) {
		this.kaynteja = kaynteja;
	}

	public ContinuousGenerator getGenerator() {
		return generator;
	}
	
	public void setGenerator(ContinuousGenerator generator) {
		this.generator = generator;
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

	public boolean isVarattu() {
		return varattu;
	}
	
	public double getAktiiviaika() {
		return aktiiviaika;
	}
	
	public void setAktiiviaika(double aktiiviaika) {
		this.aktiiviaika = aktiiviaika;
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
	public void setKeskimaarainenLapimenoaika(double keskimaarainenLapimenoaika) {
		this.keskimaarainenLapimenoaika = keskimaarainenLapimenoaika;
	}
	
	public double getKeskimaarainenJononpituus(double simulointiaika) {
		keskimaarainenJononpituus = kokonaisoleskeluaika / simulointiaika;
		return keskimaarainenJononpituus;
	}
	public void setKeskimaarainenJononpituus(double keskimaarainenJononpituus) {
		this.keskimaarainenJononpituus = keskimaarainenJononpituus;
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

	public int arvotaanPelimaksu(Asiakas asiakas) {
		// Arvotaan pelin maksu 50-100 polettia ottaen huomioon asiakkaan polettimäärä
		int maksimi;
		if (asiakas.getNykyinenPolettimaara() < 100) {
			maksimi = asiakas.getNykyinenPolettimaara() / 10;
		} else {
			maksimi = 10;
		}
		int poletteja = (int) Math.floor(Math.random() * (maksimi - 5 + 1) + 5) * 10;
		return poletteja;
	}
	
public boolean voittikoAsiakas(Asiakas asiakas) {
		
		// Tässä jotain todennäköisyyksiä ja voittosummia peleihin, näitä voi vapaasti muutella - Valdo
		int todennakoisyysVoittoon;
		int polettimaara = arvotaanPelimaksu(asiakas);
		
		switch (nimi) {
			
			case "Ruletti":
				todennakoisyysVoittoon = new Random().nextInt(100) + 1;
				if (todennakoisyysVoittoon <= kontrolleri.getRuletinVoittotodennakoisyys()) {
					// Voittosumma on 50-100 polettia.
					Trace.out(Trace.Level.INFO, "Asiakas " + asiakas.getId() + " voitti ruletissa " + polettimaara + " polettia!");
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
				
			case "Blackjack":
				todennakoisyysVoittoon = new Random().nextInt(100) + 1;
				if (todennakoisyysVoittoon <= kontrolleri.getBlackjackinVoittotodennakoisyys()) {
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
				// Krapsissa on noin 50% mahdollisuus voittoon ja noin 50% mahdollisuus häviöön (pyöreästi)
				Kraps.kahdenNopanSumma();
				if (Kraps.voittaako()) {
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