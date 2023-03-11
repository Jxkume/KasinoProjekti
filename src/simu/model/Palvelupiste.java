package simu.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

import eduni.distributions.ContinuousGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import simu.framework.Kello;
import simu.framework.Tapahtuma;
import simu.framework.Tapahtumalista;
import simu.framework.Trace;
import view.SimulaattorinPaaikkunaKontrolleri;

/**
 * Luokka palvelupisteelle (palvelutiski, ruletti, Blackjack, Kraps, voittojen nostopiste)
 * 
 * @author Tapio Humaljoki, Valtteri Kuitula, Jhon Rastrojo
 */
@Entity
@Table(name="palvelupiste")
public class Palvelupiste {
	
	/** Kayttoliittyman paaikkunan kontrolleri */
	@Transient
    SimulaattorinPaaikkunaKontrolleri kontrolleri = new SimulaattorinPaaikkunaKontrolleri();
	
	/** Lista palvelupisteen asiakkaiden jonosta */
	@Transient
	private LinkedList<Asiakas> jono = new LinkedList<Asiakas>();
	
	/** Lista, johon tallennetaan palvelupisteen asiakkaiden kayntimaara */
	@Transient
	private ArrayList<Asiakas> kaynteja = new ArrayList<>();
	
	/** Simulaattorin kello */
	@Transient
	private Kello kello = Kello.getInstance();
	
	/** Palvelupisteen jakauma */
	@Transient
	private ContinuousGenerator generator;
	
	/** Tapahtumalista, johon palvelupisteen tapahtumat lisataan */
	@Transient
	private Tapahtumalista tapahtumalista;
	
	/** Palvelupisteen tapahtumatyyppi */
	@Transient
	private TapahtumanTyyppi skeduloitavanTapahtumanTyyppi;
	
	/** Asiakkaan lapimenoaika palvelupisteella */
	@Transient
	private double lapimenoaika;
	
	/** Simulaattorin simulointiaika */
	@Transient
	private double simulointiAika;
	
	/** Muuttuja, joka tarkistaa onko palvelupiste varattu */
	@Transient
	private boolean varattu = false;
	
	/** Palvelupisteen nimi */
	@Id
    @Column(name="nimi")
	private String nimi;
	
	/** Kasinon tekema voitto euroina
	 * 	1 euro = 1 poletti
	 */
	private static int talonVoittoEuroina = 0;
	
	/** Palvelupisteen keskimaarainen jononpituus */
	private double keskimaarainenJononpituus;
	
	/** Palvelupisteen keskimaarainen jononpituus */
	private double keskimaarainenLapimenoaika;
	
	/** Palvelupisteen suoritusteho */
	private double suoritusteho;
	
	/** Palvelupisteen aktiiviaika */
	private double aktiiviaika;
	
	/** Palvelupisteen kayttoaste */
	private double kayttoaste;
	
	/** Palvelupisteen keskimaarainen palveluaika */
	private double keskimaarainenPalveluaika;
	
	/** Asiakkaiden kokonaisoleskeluaika palvelupisteella */
	private double kokonaisoleskeluaika;
	
	/** Palvelupisteen palveltujen asiakkaiden maara */
	private int palvellutAsiakkaat;

	/**
	 * Konstruktori palvelupisteelle
	 *
	 * @param satunnaislukugeneraattori
	 * @param tapahtumalista
	 * @param tapahtuman tyyppi
	 * @param palvelupisteen nimi
	 */
	public Palvelupiste(ContinuousGenerator generator, Tapahtumalista tapahtumalista, TapahtumanTyyppi tyyppi, String nimi) {
		this.tapahtumalista = tapahtumalista;
		this.generator = generator;
		this.skeduloitavanTapahtumanTyyppi = tyyppi;
		this.nimi = nimi;
	}
	
	/**
	 * Oletuskonstruktori
	 */
	public Palvelupiste() {}
	
	/**
	 * Lisaa asiakkaan palvelupisteen jonoon
	 *
	 * @param Asiakas-olio
	 */
	public void lisaaJonoon(Asiakas asiakas) {
		
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
	
	/**
	 * Poistaa asiakkaan palvelupisteen jonosta
	 *
	 * @param Asiakas-olio
	 * @return jonon ensimmainen Asiakas-olio
	 */
	public Asiakas otaJonosta(Asiakas asiakas) {
		
		// Asetetaan asiakkaalle poistumisaika
		asiakas.setPoistumisaikaJonosta(kello.getAika());
		// Lasketaan kyseisen asiakkaan lapimenoaika
		laskeLapimenoaika(asiakas);
		// Paivitetaan palvelupisteen kokonaisoleskeluaikaa
		laskeKokonaisoleskeluaika();
		// Lisataan palveltujen asiakkaiden maaraa
		palvellutAsiakkaat++;
		// Lisataan asiakkaan kaynti listaan
		kaynteja.add(asiakas);
		
		if (nimi.equals("Palvelutiski")) {
			Trace.out(Trace.Level.INFO, "Asiakas " + asiakas.getId() + " poistuu palvelutiskin jonosta.");
			Trace.out(Trace.Level.INFO, "Asiakkaalla " + asiakas.getId() + " poletteja yhteensa " + asiakas.getNykyinenPolettimaara() + ".");
			Trace.out(Trace.Level.INFO, nimi + " palvellut yhteensa " + palvellutAsiakkaat + " asiakasta.");
		} else if (nimi.equals("Ruletti")) {
			Trace.out(Trace.Level.INFO, "Asiakas " + asiakas.getId() + " poistuu ruletin jonosta.");
			Trace.out(Trace.Level.INFO, nimi + " palvellut yhteensa " + palvellutAsiakkaat + " asiakasta.");
		} else if (nimi.equals("Blackjack")) {
			Trace.out(Trace.Level.INFO, "Asiakas " + asiakas.getId() + " poistuu Blackjackin jonosta.");
			Trace.out(Trace.Level.INFO, nimi + " palvellut yhteensa " + palvellutAsiakkaat + " asiakasta.");
		} else if (nimi.equals("Kraps")) {
			Trace.out(Trace.Level.INFO, "Asiakas " + asiakas.getId() + " poistuu Krapsin jonosta.");
			Trace.out(Trace.Level.INFO, nimi + " palvellut yhteensa " + palvellutAsiakkaat + " asiakasta.");
		} else if (nimi.equals("Voittojen nostopiste")) {
			Trace.out(Trace.Level.INFO, "Asiakas " + asiakas.getId() + " poistuu voittojen nostopisteen jonosta.");
			Trace.out(Trace.Level.INFO, nimi + " palvellut yhteensa " + palvellutAsiakkaat + " asiakasta.");
			if (asiakas.getNykyinenPolettimaara() > asiakas.getAlkuperainenPolettimaara()) {
				// Vahennetaan asiakkaan voittama polettimaara kasinon tuloksesta
				talonVoittoEuroina -= (asiakas.getNykyinenPolettimaara() - asiakas.getAlkuperainenPolettimaara());
				Trace.out(Trace.Level.INFO, "Asiakas " + asiakas.getId() + " voitti poletteja kasinolla yhteensa " + (asiakas.getNykyinenPolettimaara() - asiakas.getAlkuperainenPolettimaara()) + ".");
			} else if (asiakas.getNykyinenPolettimaara() < asiakas.getAlkuperainenPolettimaara()) {
				// Lisataan asiakkaan haviama polettimaara kasinon tulokseen
				talonVoittoEuroina += (asiakas.getAlkuperainenPolettimaara() - asiakas.getNykyinenPolettimaara());
				Trace.out(Trace.Level.INFO, "Asiakas " + asiakas.getId() + " havisi poletteja kasinolla yhteensa " + (asiakas.getAlkuperainenPolettimaara() - asiakas.getNykyinenPolettimaara()) + ".");
			} else {
				Trace.out(Trace.Level.INFO, "Asiakas " + asiakas.getId() + " ei voittanut tai havinnyt poletteja kasinolla.");
			}
			// Tulostetaan kasinon voitot
			if (talonVoittoEuroina > 0) {
				Trace.out(Trace.Level.INFO, "Kasino on tehnyt voittoa tahan mennessa yhteensa " + talonVoittoEuroina + " euroa.");
			} else {
				Trace.out(Trace.Level.INFO, "Kasino on tehnyt liiketappiota tahan mennessa yhteensa " + (talonVoittoEuroina * -1) + " euroa.");
			}
		}
		varattu = false;
		return jono.poll(); 
	}
	
	/**
	 * Aloittaa palvelun palvelupisteen jonon ensimmaiselle asiakkaalle
	 */
	public void aloitaPalvelu() {
		varattu = true;
		double palveluaika = generator.sample();
		// Lisataan palvelupisteen aktiiviaikaan arvottu palveluaika
		aktiiviaika += palveluaika;
		// Lisataan tapahtumalistaan uusi tapahtuma
		tapahtumalista.lisaa(new Tapahtuma(skeduloitavanTapahtumanTyyppi, Kello.getInstance().getAika()+palveluaika));
	}
	
	/**
	 * Arpoo asiakkaalle palvelupisteen pelimaksun
	 *
	 * @param Asiakas-olio
	 * @return pelimaksu
	 */
	public int arvotaanPelimaksu(Asiakas asiakas) {
		// Arvotaan pelin maksu 50-100 polettia ottaen huomioon asiakkaan polettimaara
		int maksimi;
		if (asiakas.getNykyinenPolettimaara() < 100) {
			maksimi = asiakas.getNykyinenPolettimaara() / 10;
		} else {
			maksimi = 10;
		}
		int poletteja = (int) Math.floor(Math.random() * (maksimi - 5 + 1) + 5) * 10;
		return poletteja;
	}
	
	/**
	 * Tarkistaa voittiko asiakas palvelupisteella (jos kyseessa on jokin kasinon peleista)
	 *
	 * @param Asiakas-olio
	 * @return true, jos asiakas voittaa
	 */
	public boolean voittikoAsiakas(Asiakas asiakas) {
			
			// Arvotaan luku valilta 1-100
			int todennakoisyysVoittoon = new Random().nextInt(100) + 1;
			// Arvotaan pelimaksu asiakkaalle
			int polettimaara = arvotaanPelimaksu(asiakas);
			
			switch (nimi) {
				
				case "Ruletti":
					if (todennakoisyysVoittoon <= kontrolleri.getRuletinVoittotodennakoisyys()) {
						// Voittosumma on 50-100 polettia.
						Trace.out(Trace.Level.INFO, "Asiakas " + asiakas.getId() + " voitti ruletissa " + polettimaara + " polettia!");
						asiakas.lisaaPoletteja(polettimaara);
						Trace.out(Trace.Level.INFO, "Asiakkaalla " + asiakas.getId() + " poletteja yhteensa " + asiakas.getNykyinenPolettimaara() + ".");
						return true;
					} else { 
						Trace.out(Trace.Level.INFO, "Asiakas " + asiakas.getId() + " havisi pelin.");
						// Asiakas haviaa 50-100 polettia.
						asiakas.vahennaPoletteja(polettimaara);
						Trace.out(Trace.Level.INFO, "Asiakkaalla " + asiakas.getId() + " poletteja yhteensa " + asiakas.getNykyinenPolettimaara() + ".");
						return false;
					}
					
				case "Blackjack":
					if (todennakoisyysVoittoon <= kontrolleri.getBlackjackinVoittotodennakoisyys()) {
						// Voittosumma on 50-100 polettia.
						Trace.out(Trace.Level.INFO, "Asiakas " + asiakas.getId() + " voitti Blackjackissa " + polettimaara + " polettia!");
						asiakas.lisaaPoletteja(polettimaara);
						Trace.out(Trace.Level.INFO, "Asiakkaalla " + asiakas.getId() + " poletteja yhteensa " + asiakas.getNykyinenPolettimaara() + ".");
						return true;
					} else {
						Trace.out(Trace.Level.INFO, "Asiakas " + asiakas.getId() + " havisi pelin.");
						// Asiakas haviaa 50-100 polettia.
						asiakas.vahennaPoletteja(polettimaara);
						Trace.out(Trace.Level.INFO, "Asiakkaalla " + asiakas.getId() + " poletteja yhteensa " + asiakas.getNykyinenPolettimaara() + ".");
						return false;
					}
					
				case "Kraps":
					// Krapsissa on noin 50% mahdollisuus voittoon ja noin 50% mahdollisuus havioon (pyoreasti)
					Kraps.kahdenNopanSumma();
					if (Kraps.voittaako()) {
						// Voittosumma on 50-100 polettia.
						Trace.out(Trace.Level.INFO, "Asiakas " + asiakas.getId() + " voitti Krapsissa " + polettimaara + " polettia!");
						asiakas.lisaaPoletteja(polettimaara);
						Trace.out(Trace.Level.INFO, "Asiakkaalla " + asiakas.getId() + " poletteja yhteensa " + asiakas.getNykyinenPolettimaara() + ".");
						return true;
					} else {
						Trace.out(Trace.Level.INFO, "Asiakas " + asiakas.getId() + " havisi pelin.");
						// Asiakas haviaa 50-100 polettia.
						asiakas.vahennaPoletteja(polettimaara);
						Trace.out(Trace.Level.INFO, "Asiakkaalla " + asiakas.getId() + " poletteja yhteensa " + asiakas.getNykyinenPolettimaara() + ".");
						return false;
					}
			}
			return false;
		}
	
	/**
	 * Pitaa asiakkaan palvelupisteen jonon ensimmaisena, jotta asiakas voi pelata uudestaan
	 *
	 * @param Asiakas-olio
	 */
	public void jatkaPelaamista(Asiakas asiakas) {
		Trace.out(Trace.Level.INFO, "Asiakas " + asiakas.getId() + " jatkaa pelaamista.");
		varattu = false;
	}
	
	/**
	 * Palauttaa merkkijonoesityksen palvelupisteen jonon asiakkaiden id:sta ottaen huomioon palvelupisteen nimen
	 *
	 * @return merkkijonoesitys palvelupisteen jonon asiakkaiden id:sta
	 */
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
	
	/**
	 * Palauttaa palvellut asiakkaat
	 *
	 * @return palvellut asiakkaat
	 */
	public int getPalvellutAsiakkaat() {
		return palvellutAsiakkaat;
	}
	
	/**
	 * Asettaa palvellut asiakkaat
	 *
	 * @param palvellut asiakkaat
	 */
	public void setPalvellutAsiakkaat(int palvellutAsiakkaat) {
		this.palvellutAsiakkaat = palvellutAsiakkaat;
	}
	
	/**
	 * Palauttaa ArrayListin Asiakas-olioita asiakkaiden kayntikertojen selvittamiseksi
	 *
	 * @return ArrayList Asiakas-olioita
	 */
	public ArrayList<Asiakas> getKaynteja() {
		return kaynteja;
	}
	
	/**
	 * Asettaa palvelupisteen kayttoasteen
	 *
	 * @param palvelupisteen kayttoaste
	 */
	public void setKayttoaste(double kayttoaste) {
		this.kayttoaste = kayttoaste;
	}
	
	/**
	 * Laskee palvelupisteen kayttoasteen ja palauttaa sen
	 *
	 * @param simulaattorin simulointiaika
	 * @return palvelupisteen kayttoaste
	 */
	public double getKayttoaste(double simulointiaika) {
		kayttoaste = aktiiviaika / simulointiaika;
		return kayttoaste;
	}
	
	/**
	 * Laskee palvelupisteen suoritustehon ja palauttaa sen
	 *
	 * @param simulaattorin simulointiaika
	 * @return palvelupisteen suoritusteho
	 */
	public double getSuoritusteho(double simulointiaika) {
		suoritusteho = palvellutAsiakkaat / simulointiaika;
		return suoritusteho;
	}
	
	/**
	 * Asettaa palvelupisteen suoritustehon
	 *
	 * @param palvelupisteen suoritusteho
	 */
	public void setSuoritusteho(double suoritusteho) {
		this.suoritusteho = suoritusteho;
	}
	
	/**
	 * Asettaa palvelupisteen keskimaaraisen palveluajan
	 *
	 * @param palvelupisteen keskimaarainen palveluaika
	 */
	public void setKeskimaarainenPalveluaika(double keskimaarainenPalveluaika) {
		this.keskimaarainenPalveluaika = keskimaarainenPalveluaika;
	}
	
	/**
	 * Laskee palvelupisteen keskimaaraisen palveluajan ja palauttaa sen
	 *
	 * @return palvelupisteen keskimaarainen palveluaika
	 */
	public double getKeskimaarainenPalveluaika() {
		keskimaarainenPalveluaika = aktiiviaika / palvellutAsiakkaat;
		return keskimaarainenPalveluaika;
	}
	
	/**
	 * Laskee palvelupisteen kokonaisoleskeluajan
	 */
	public void laskeKokonaisoleskeluaika() {
		kokonaisoleskeluaika += lapimenoaika;
	}
	
	/**
	 * Palauttaa palvelupisteen kokonaisoleskeluajan
	 *
	 * @return palvelupisteen kokonaisoleskeluaika
	 */
	public double getKokonaisoleskeluaika() {
		return kokonaisoleskeluaika;
	}
	
	/**
	 * Asettaa simulaattorin simulointiajan, jotta palvelupisteella on se tiedossa
	 *
	 * @param simulaattorin simulointiaika
	 */
	public void setSimulointiaika(double simulointiAika) {
		this.simulointiAika = simulointiAika;
	}
	
	/**
	 * Palauttaa simulaattorin simulointiajan
	 *
	 * @return simulaattorin simulointiaika
	 */
	public double getSimulointiaika() {
		return simulointiAika;
	}
	
	/**
	 * Asettaa palvelupisteen kokonaisoleskeluajan
	 *
	 * @param palvelupisteen kokonaisoleskeluaika
	 */
	public void setKokonaisoleskeluaika(double kokonaisoleskeluaika) {
		this.kokonaisoleskeluaika = kokonaisoleskeluaika;
	}
	
	/**
	 * Asettaa satunnaislukugeneraattorin eli palvelupisteen jakauman
	 *
	 * @param palvelupisteen jakauma
	 */
	public void setGenerator(ContinuousGenerator generator) {
		this.generator = generator;
	}

	/**
	 * Palauttaa palvelupisteen nimen
	 *
	 * @return palvelupisteen nimi
	 */
	public String getNimi() {
		return nimi;
	}

	/**
	 * Tarkistaa onko palvelupiste varattu
	 *
	 * @return true tai false
	 */
	public boolean isVarattu() {
		return varattu;
	}
	
	/**
	 * Palauttaa palvelupisteen aktiiviajan
	 *
	 * @return palvelupisteen aktiiviaika
	 */
	public double getAktiiviaika() {
		return aktiiviaika;
	}
	
	/**
	 * Asettaa palvelupisteen aktiiviajan
	 *
	 * @param palvelupisteen aktiiviaika
	 */
	public void setAktiiviaika(double aktiiviaika) {
		this.aktiiviaika = aktiiviaika;
	}
	
	/**
	 * Laskee palvelupisteen lapimenoajan asiakkaan saapumis- ja poistumisajan perusteella
	 *
	 * @param Asiakas-olio
	 */
	public void laskeLapimenoaika(Asiakas asiakas) {
		lapimenoaika = asiakas.getPoistumisaikaJonosta() - asiakas.getSaapumisaikaJonoon();
	}
	
	/**
	 * Palauttaa asiakkaan lapimenoajan
	 *
	 * @param Asiakas-olio
	 * @return asiakkaan lapimenoaika
	 */
	public double getLapimenoaika(Asiakas asiakas) {
		return lapimenoaika;
	}
	
	/**
	 * Laskee palvelupisteen keskimaaraisen
	 *
	 * @return the keskimaarainen lapimenoaika
	 */
	public double getKeskimaarainenLapimenoaika() {
		keskimaarainenLapimenoaika = kokonaisoleskeluaika / palvellutAsiakkaat;
		return keskimaarainenLapimenoaika;
	}
	
	/**
	 * Asettaa palvelupisteen asiakkaiden keskimaaraisen lapimenoajan
	 *
	 * @param palvelupisteen asiakkaiden keskimaarainen lapimenoaika
	 */
	public void setKeskimaarainenLapimenoaika(double keskimaarainenLapimenoaika) {
		this.keskimaarainenLapimenoaika = keskimaarainenLapimenoaika;
	}
	
	/**
	 * Laskee ja palauttaa palvelupisteen keskimaaraisen jononpituuden
	 *
	 * @param simulaattorin simulointiaika
	 * @return palvelupisteen keskimaarainen jononpituus
	 */
	public double getKeskimaarainenJononpituus(double simulointiaika) {
		keskimaarainenJononpituus = kokonaisoleskeluaika / simulointiaika;
		return keskimaarainenJononpituus;
	}
	
	/**
	 * Asettaa palvelupisteen keskimaaraisen jononpituuden
	 *
	 * @param palvelupisteen keskimaarainen jononpituus
	 */
	public void setKeskimaarainenJononpituus(double keskimaarainenJononpituus) {
		this.keskimaarainenJononpituus = keskimaarainenJononpituus;
	}

	/**
	 * Palauttaa true, jos palvelupiste on varattu
	 *
	 * @return true tai false
	 */
	public boolean onVarattu() {
		return varattu;
	}

	/**
	 * Palauttaa true, kun palvelupisteen jonossa on asiakas
	 *
	 * @return true tai false
	 */
	public boolean onJonossa() {
		return jono.size() != 0;
	}
	
	/**
	 * Palauttaa palvelupisteen jonon
	 *
	 * @return palvelupisteen jono
	 */
	public LinkedList<Asiakas> getJono() {
		return jono;
	}
	
	/**
	 * Palauttaa palvelupisteen jonon ensimmaisen asiakkaan
	 *
	 * @return palvelupisteen jonon ensimmainen asiakas
	 */
	public Asiakas getJononEnsimmainen() {
		return jono.getFirst();
	}

	/**
	 * Palauttaa kasinon tekeman voiton
	 *
	 * @return kasinon tekema voitto
	 */
	public int getTalonVoittoEuroina() {
		return talonVoittoEuroina;
	}

}