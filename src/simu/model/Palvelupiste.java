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
	
	/** Käyttöliittymän pääikkunan kontrolleri */
	@Transient
    SimulaattorinPaaikkunaKontrolleri kontrolleri = new SimulaattorinPaaikkunaKontrolleri();
	
	/** Lista palvelupisteen asiakkaiden jonosta */
	@Transient
	private LinkedList<Asiakas> jono = new LinkedList<Asiakas>();
	
	/** Lista, johon tallennetaan palvelupisteen asiakkaiden käyntimäärä */
	@Transient
	private ArrayList<Asiakas> kaynteja = new ArrayList<>();
	
	/** Simulaattorin kello */
	@Transient
	private Kello kello = Kello.getInstance();
	
	/** Palvelupisteen jakauma */
	@Transient
	private ContinuousGenerator generator;
	
	/** Tapahtumalista, johon palvelupisteen tapahtumat lisätään */
	@Transient
	private Tapahtumalista tapahtumalista;
	
	/** Palvelupisteen tapahtumatyyppi */
	@Transient
	private TapahtumanTyyppi skeduloitavanTapahtumanTyyppi;
	
	/** Asiakkaan läpimenoaika palvelupisteellä */
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
	
	/** Kasinon tekemä voitto euroina
	 * 	1 euro = 1 poletti
	 */
	private static int talonVoittoEuroina = 0;
	
	/** Palvelupisteen keskimääräinen jononpituus */
	private double keskimaarainenJononpituus;
	
	/** Palvelupisteen keskimääräinen jononpituus */
	private double keskimaarainenLapimenoaika;
	
	/** Palvelupisteen suoritusteho */
	private double suoritusteho;
	
	/** Palvelupisteen aktiiviaika */
	private double aktiiviaika;
	
	/** Palvelupisteen käyttöaste */
	private double kayttoaste;
	
	/** Palvelupisteen keskimääräinen palveluaika */
	private double keskimaarainenPalveluaika;
	
	/** Asiakkaiden kokonaisoleskeluaika palvelupisteellä */
	private double kokonaisoleskeluaika;
	
	/** Palvelupisteen palveltujen asiakkaiden määrä */
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
	 * Lisää asiakkaan palvelupisteen jonoon
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
	 * @return jonon ensimmäinen Asiakas-olio
	 */
	public Asiakas otaJonosta(Asiakas asiakas) {
		
		// Asetetaan asiakkaalle poistumisaika
		asiakas.setPoistumisaikaJonosta(kello.getAika());
		// Lasketaan kyseisen asiakkaan läpimenoaika
		laskeLapimenoaika(asiakas);
		// Päivitetään palvelupisteen kokonaisoleskeluaikaa
		laskeKokonaisoleskeluaika();
		// Lisätään palveltujen asiakkaiden määrää
		palvellutAsiakkaat++;
		// Lisätään asiakkaan käynti listaan
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
				// Vähennetään asiakkaan voittama polettimäärä kasinon tuloksesta
				talonVoittoEuroina -= (asiakas.getNykyinenPolettimaara() - asiakas.getAlkuperainenPolettimaara());
				Trace.out(Trace.Level.INFO, "Asiakas " + asiakas.getId() + " voitti poletteja kasinolla yhteensä " + (asiakas.getNykyinenPolettimaara() - asiakas.getAlkuperainenPolettimaara()) + ".");
			} else if (asiakas.getNykyinenPolettimaara() < asiakas.getAlkuperainenPolettimaara()) {
				// Lisätään asiakkaan häviämä polettimäärä kasinon tulokseen
				talonVoittoEuroina += (asiakas.getAlkuperainenPolettimaara() - asiakas.getNykyinenPolettimaara());
				Trace.out(Trace.Level.INFO, "Asiakas " + asiakas.getId() + " hävisi poletteja kasinolla yhteensä " + (asiakas.getAlkuperainenPolettimaara() - asiakas.getNykyinenPolettimaara()) + ".");
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
	
	/**
	 * Aloittaa palvelun palvelupisteen jonon ensimmäiselle asiakkaalle
	 */
	public void aloitaPalvelu() {
		varattu = true;
		double palveluaika = generator.sample();
		// Lisätään palvelupisteen aktiiviaikaan arvottu palveluaika
		aktiiviaika += palveluaika;
		// Lisätään tapahtumalistaan uusi tapahtuma
		tapahtumalista.lisaa(new Tapahtuma(skeduloitavanTapahtumanTyyppi, Kello.getInstance().getAika()+palveluaika));
	}
	
	/**
	 * Arpoo asiakkaalle palvelupisteen pelimaksun
	 *
	 * @param Asiakas-olio
	 * @return pelimaksu
	 */
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
	
	/**
	 * Tarkistaa voittiko asiakas palvelupisteellä (jos kyseessä on jokin kasinon peleistä)
	 *
	 * @param Asiakas-olio
	 * @return true, jos asiakas voittaa
	 */
	public boolean voittikoAsiakas(Asiakas asiakas) {
			
			// Arvotaan luku väliltä 1-100
			int todennakoisyysVoittoon = new Random().nextInt(100) + 1;
			// Arvotaan pelimaksu asiakkaalle
			int polettimaara = arvotaanPelimaksu(asiakas);
			
			switch (nimi) {
				
				case "Ruletti":
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
	
	/**
	 * Pitää asiakkaan palvelupisteen jonon ensimmäisenä, jotta asiakas voi pelata uudestaan
	 *
	 * @param Asiakas-olio
	 */
	public void jatkaPelaamista(Asiakas asiakas) {
		Trace.out(Trace.Level.INFO, "Asiakas " + asiakas.getId() + " jatkaa pelaamista.");
		varattu = false;
	}
	
	/**
	 * Palauttaa merkkijonoesityksen palvelupisteen jonon asiakkaiden id:stä ottaen huomioon palvelupisteen nimen
	 *
	 * @return merkkijonoesitys palvelupisteen jonon asiakkaiden id:stä
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
	 * Palauttaa ArrayListin Asiakas-olioita asiakkaiden käyntikertojen selvittämiseksi
	 *
	 * @return ArrayList Asiakas-olioita
	 */
	public ArrayList<Asiakas> getKaynteja() {
		return kaynteja;
	}
	
	/**
	 * Asettaa palvelupisteen käyttöasteen
	 *
	 * @param palvelupisteen käyttöaste
	 */
	public void setKayttoaste(double kayttoaste) {
		this.kayttoaste = kayttoaste;
	}
	
	/**
	 * Laskee palvelupisteen käyttöasteen ja palauttaa sen
	 *
	 * @param simulaattorin simulointiaika
	 * @return palvelupisteen käyttöaste
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
	 * Asettaa palvelupisteen keskimääräisen palveluajan
	 *
	 * @param palvelupisteen keskimääräinen palveluaika
	 */
	public void setKeskimaarainenPalveluaika(double keskimaarainenPalveluaika) {
		this.keskimaarainenPalveluaika = keskimaarainenPalveluaika;
	}
	
	/**
	 * Laskee palvelupisteen keskimääräisen palveluajan ja palauttaa sen
	 *
	 * @return palvelupisteen keskimääräinen palveluaika
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
	 * Asettaa simulaattorin simulointiajan, jotta palvelupisteellä on se tiedossa
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
	 * Palauttaa asiakkaan läpimenoajan
	 *
	 * @param Asiakas-olio
	 * @return asiakkaan läpimenoaika
	 */
	public double getLapimenoaika(Asiakas asiakas) {
		return lapimenoaika;
	}
	
	/**
	 * Laskee palvelupisteen keskimääräisen
	 *
	 * @return the keskimaarainen lapimenoaika
	 */
	public double getKeskimaarainenLapimenoaika() {
		keskimaarainenLapimenoaika = kokonaisoleskeluaika / palvellutAsiakkaat;
		return keskimaarainenLapimenoaika;
	}
	
	/**
	 * Asettaa palvelupisteen asiakkaiden keskimääräisen läpimenoajan
	 *
	 * @param palvelupisteen asiakkaiden keskimääräinen läpimenoaika
	 */
	public void setKeskimaarainenLapimenoaika(double keskimaarainenLapimenoaika) {
		this.keskimaarainenLapimenoaika = keskimaarainenLapimenoaika;
	}
	
	/**
	 * Laskee ja palauttaa palvelupisteen keskimääräisen jononpituuden
	 *
	 * @param simulaattorin simulointiaika
	 * @return palvelupisteen keskimääräinen jononpituus
	 */
	public double getKeskimaarainenJononpituus(double simulointiaika) {
		keskimaarainenJononpituus = kokonaisoleskeluaika / simulointiaika;
		return keskimaarainenJononpituus;
	}
	
	/**
	 * Asettaa palvelupisteen keskimääräisen jononpituuden
	 *
	 * @param palvelupisteen keskimääräinen jononpituus
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
	 * Palauttaa palvelupisteen jonon ensimmäisen asiakkaan
	 *
	 * @return palvelupisteen jonon ensimmäinen asiakas
	 */
	public Asiakas getJononEnsimmainen() {
		return jono.getFirst();
	}

	/**
	 * Palauttaa kasinon tekemän voiton
	 *
	 * @return kasinon tekemä voitto
	 */
	public int getTalonVoittoEuroina() {
		return talonVoittoEuroina;
	}

}