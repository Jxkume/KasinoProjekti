package simu.model;

import java.util.HashMap;

import dao.KasinoDao;
import dao.PalvelupisteDao;
import eduni.distributions.Negexp;
import eduni.distributions.Normal;
import simu.framework.Kello;
import simu.framework.Moottori;
import simu.framework.Saapumisprosessi;
import simu.framework.Tapahtuma;
import simu.framework.Trace;
import view.PalvelutiskiPopUpKontrolleri;
import view.SimulaattorinPaaikkunaKontrolleri;
import view.BlackjackPopUpKontrolleri;
import view.KrapsPopUpKontrolleri;
import view.RulettiPopUpKontrolleri;
import view.VoittojenNostopistePopUpKontrolleri;

/**
 * Luokka OmaMoottorille
 */
public class OmaMoottori extends Moottori {
	
	/** Saapumisprosessi-olio */
	private Saapumisprosessi saapumisprosessi;
	
	/** Kasinoon saapuvien asiakkaiden lukumäärä */
	private static int asiakasLkm = 0;
	
	/** Kasinossa kaikkien asiakkaiden keskimäärin viettämä aika */
	private double keskimaarainenVietettyAika;
	
	/** PalvelupisteDao-olio */
	private PalvelupisteDao palvDao = new PalvelupisteDao();
	
	/** Kasino-olio */
	private Kasino kasino = new Kasino();
	
	/** KasinoDao-olio */
	private KasinoDao kasinoDao = new KasinoDao();
	
	/** Palvelutiskin pop-up-ikkunan kontrolleri */
	private PalvelutiskiPopUpKontrolleri palvelupistePopUpKontrolleri = new PalvelutiskiPopUpKontrolleri();
	
	/** Ruletin pop-up-ikkunan kontrolleri */
	private RulettiPopUpKontrolleri rulettiPopUpKontrolleri = new RulettiPopUpKontrolleri();
	
	/** Blackjackin pop-up-ikkunan kontrolleri */
	private BlackjackPopUpKontrolleri blackjackPopUpKontrolleri = new BlackjackPopUpKontrolleri();
	
	/** Krapsin pop-up-ikkunan kontrolleri */
	private KrapsPopUpKontrolleri krapsPopUpKontrolleri = new KrapsPopUpKontrolleri();
	
	/** Voittojen nostopisteen pop-up-ikkunan kontrolleri */
	private VoittojenNostopistePopUpKontrolleri voittojenNostopistePopUpKontrolleri = new VoittojenNostopistePopUpKontrolleri();
	
	/**
	 * OmaMoottorin konstruktori, jossa luodaan simulaation saapumisprosessi sekä 5 palvelupistettä
	 *
	 * @param Käyttöliittymän pääikkunan kontrolleri
	 */
	public OmaMoottori(SimulaattorinPaaikkunaKontrolleri kontrolleri) {

		super(kontrolleri);
		
		saapumisprosessi = new Saapumisprosessi(new Negexp(15,5), tapahtumalista, TapahtumanTyyppi.ARR1);
	
		palvelupisteet = new Palvelupiste[5];
		palvelupisteet[0] = new Palvelupiste(new Normal(4,1), tapahtumalista, TapahtumanTyyppi.DEP1, "Palvelutiski");
		palvelupisteet[1] = new Palvelupiste(new Normal(4,1), tapahtumalista, TapahtumanTyyppi.DEP2, "Ruletti");
		palvelupisteet[2] = new Palvelupiste(new Normal(4,1), tapahtumalista, TapahtumanTyyppi.DEP3, "Blackjack");
		palvelupisteet[3] = new Palvelupiste(new Normal(4,1), tapahtumalista, TapahtumanTyyppi.DEP4, "Kraps");
		palvelupisteet[4] = new Palvelupiste(new Normal(4,1), tapahtumalista, TapahtumanTyyppi.DEP5, "Voittojen nostopiste");
		
	}
	
	/**
	 * Alustaa simulaation ennen käynnistystä
	 */
	@Override
	protected void alustukset() {
		
		// Asetetaan käyttäjän käyttöliittymässä valitsema saapumisnopeus asiakkaille
		if (kontrolleri.getSaapumisenKesto().equals("Normaali")) {
			saapumisprosessi.setGenerator(new Negexp(15,5));
		} else if (kontrolleri.getSaapumisenKesto().equals("Nopea")) {
			saapumisprosessi.setGenerator(new Negexp(10,5));
		} else if (kontrolleri.getSaapumisenKesto().equals("Hidas")) {
			saapumisprosessi.setGenerator(new Negexp(20,5));
		}
		
		// Asetetaan käyttäjän käyttöliittymässä valitsema pelien kesto kaikille peleille
		if (kontrolleri.getPelienKesto().equals("Normaali")) {
			// Pelit kestävät 3-5 aikayksikköä
			getRuletti().setGenerator(new Normal(4,1));
			getBlackjack().setGenerator(new Normal(4,1));
			getKraps().setGenerator(new Normal(4,1));
		} else if (kontrolleri.getPelienKesto().equals("Nopea")) {
			// Pelit kestävät 1-2 aikayksikköä
			getRuletti().setGenerator(new Normal(1.5,0.5));
			getBlackjack().setGenerator(new Normal(1.5,0.5));
			getKraps().setGenerator(new Normal(1.5,0.5));
		} else if (kontrolleri.getPelienKesto().equals("Hidas")) {
			// Pelit kestävät 6-8 aikayksikköä
			getRuletti().setGenerator(new Normal(7,1));
			getBlackjack().setGenerator(new Normal(7,1));
			getKraps().setGenerator(new Normal(7,1));
		}
		
		// Generoidaan ensimmäinen asiakas järjestelmään
		saapumisprosessi.generoiSeuraava();
		
	}
	        
	/**
	 * Suorittaa tapahtuman (eli asiakkaan palvelemisen) tapahtumalistasta
	 *
	 * @param Tapahtuma-olio, joka saadaan tapahtumalistasta
	 */
	@Override
	protected void suoritaTapahtuma(Tapahtuma t) {
		
		Asiakas asiakas;
		// Arvotaan todennäköisyys jatkaako asiakas pelaamista pelissä
		int todennakoisyys = (int) Math.floor(Math.random() * (4 - 1 + 1) + 1);
		// Arvotaan minne kolmesta palvelupisteestä asiakas siirtyy
		int randomPalvelupiste = (int) Math.floor(Math.random() * (3 - 1 + 1) + 1);
		// Arvotaan siirtyykö asiakas hakemaan lisää poletteja vai voittojen nostopisteelle
		int lisaaPolettejaTaiVoitot = (int) Math.floor(Math.random() * (2 - 1 + 1) + 1);
		
		switch (t.getTyyppi()) {
			
			case ARR1: // Asiakas saapuu järjestelmään ja palvelutiskin jonoon
				getPalvelutiski().lisaaJonoon(new Asiakas());
				asiakasLkm++;
				Trace.out(Trace.Level.INFO, getPalvelutiski().toString());
		    	saapumisprosessi.generoiSeuraava();
				break;
				
			case DEP1: // Asiakas lähtee kasinon palvelutiskiltä
				asiakas = getPalvelutiski().getJononEnsimmainen();
				// Asiakkaalle annetaan poletteja palvelutiskillä
				asiakas.annaPolettejaPalvelutiskilla();
				if (randomPalvelupiste == 1) {
					getPalvelutiski().otaJonosta(asiakas);
					getRuletti().lisaaJonoon(asiakas);
					Trace.out(Trace.Level.INFO, getRuletti().toString());
				} else if (randomPalvelupiste == 2) {
					getPalvelutiski().otaJonosta(asiakas);
					getBlackjack().lisaaJonoon(asiakas);
					Trace.out(Trace.Level.INFO, getBlackjack().toString());
				} else {
					getPalvelutiski().otaJonosta(asiakas);
					getKraps().lisaaJonoon(asiakas);
					Trace.out(Trace.Level.INFO, getKraps().toString());
				}
				break;
				
			case DEP2: // Asiakas lähtee kasinon ruletista
				asiakas = getRuletti().getJononEnsimmainen();
				// Asiakkaalla pitää olla vähintään 50 polettia pelatakseen
				if (asiakas.getNykyinenPolettimaara() >= 50) {
					// Asiakas pelaa ja tarkistetaan voittiko vai hävisikö asiakas
					if (getRuletti().voittikoAsiakas(asiakas)) {
						// Asiakkaan voittaessa todennäköisyys jatkaa pelaamista on suurempi
						todennakoisyys += 1;
					} else {
						// Asiakkaan hävitessä todennäköisyys jatkaa pelaamista on pienempi
						todennakoisyys -= 1;
					}
				} else { // Asiakas hakee lisää poletteja
					getRuletti().otaJonosta(asiakas);
					getPalvelutiski().lisaaJonoon(asiakas);
					Trace.out(Trace.Level.INFO, getPalvelutiski().toString());
					break;
				}
				// Jos todennäköisyys on 2 tai alle, asiakas poistuu uuteen peliin tai voittojen nostopisteelle
				if (todennakoisyys <= 2 && asiakas.getNykyinenPolettimaara() >= 50) {
					if (randomPalvelupiste == 1) {
						getRuletti().otaJonosta(asiakas);
						getBlackjack().lisaaJonoon(asiakas);
						Trace.out(Trace.Level.INFO, getBlackjack().toString());
					} else if (randomPalvelupiste == 2) {
						getRuletti().otaJonosta(asiakas);
						getKraps().lisaaJonoon(asiakas);
						Trace.out(Trace.Level.INFO, getKraps().toString());
					} else {
						getRuletti().otaJonosta(asiakas);
						getVoittojenNostopiste().lisaaJonoon(asiakas);
						Trace.out(Trace.Level.INFO, getVoittojenNostopiste().toString());
					}
				} else if (todennakoisyys >= 3 && asiakas.getNykyinenPolettimaara() >= 50) { // Asiakas voi jatkaa pelaamista vain, jos hänellä on vähintään 50 polettia
					getRuletti().jatkaPelaamista(asiakas);
				} else { // Asiakas hakee lisää poletteja tai menee voittojen nostopisteelle
					if (lisaaPolettejaTaiVoitot == 1) {
						getRuletti().otaJonosta(asiakas);
						getPalvelutiski().lisaaJonoon(asiakas);
						Trace.out(Trace.Level.INFO, getPalvelutiski().toString());
					} else {
						getRuletti().otaJonosta(asiakas);
						getVoittojenNostopiste().lisaaJonoon(asiakas);
						Trace.out(Trace.Level.INFO, getVoittojenNostopiste().toString());
					}
				}
		   	   	break;
		   	   
			case DEP3: // Asiakas lähtee kasinon Blackjackistä
				asiakas = getBlackjack().getJononEnsimmainen();
				// Asiakkaalla pitää olla vähintään 50 polettia pelatakseen
				if (asiakas.getNykyinenPolettimaara() >= 50) {
					// Asiakas pelaa ja tarkistetaan voittiko vai hävisikö asiakas
					if (getBlackjack().voittikoAsiakas(asiakas)) {
						// Asiakkaan voittaessa todennäköisyys jatkaa pelaamista on suurempi
						todennakoisyys += 1;
					} else {
						// Asiakkaan hävitessä todennäköisyys jatkaa pelaamista on pienempi
						todennakoisyys -= 1;
					}
				} else { // Asiakas hakee lisää poletteja
					getBlackjack().otaJonosta(asiakas);
					getPalvelutiski().lisaaJonoon(asiakas);
					Trace.out(Trace.Level.INFO, getPalvelutiski().toString());
					break;
				}
				// Jos todennäköisyys on 2 tai alle, asiakas poistuu uuteen peliin, hakemaan lisää poletteja tai voittojen nostopisteelle
				if (todennakoisyys <= 2 && asiakas.getNykyinenPolettimaara() >= 50) {
					if (randomPalvelupiste == 1) {
						getBlackjack().otaJonosta(asiakas);
						getRuletti().lisaaJonoon(asiakas);
						Trace.out(Trace.Level.INFO, getRuletti().toString());
					} else if (randomPalvelupiste == 2) {
						getBlackjack().otaJonosta(asiakas);
						getKraps().lisaaJonoon(asiakas);
						Trace.out(Trace.Level.INFO, getKraps().toString());
					} else {
						getBlackjack().otaJonosta(asiakas);
						getVoittojenNostopiste().lisaaJonoon(asiakas);
						Trace.out(Trace.Level.INFO, getVoittojenNostopiste().toString());
					}
				} else if (todennakoisyys >= 3 && asiakas.getNykyinenPolettimaara() >= 50) { // Asiakas voi jatkaa pelaamista vain, jos hänellä on vähintään 50 polettia
					getBlackjack().jatkaPelaamista(asiakas);
				} else { // Asiakas hakee lisää poletteja tai menee voittojen nostopisteelle
					if (lisaaPolettejaTaiVoitot == 1) {
						getBlackjack().otaJonosta(asiakas);
						getPalvelutiski().lisaaJonoon(asiakas);
						Trace.out(Trace.Level.INFO, getPalvelutiski().toString());
					} else {
						getBlackjack().otaJonosta(asiakas);
						getVoittojenNostopiste().lisaaJonoon(asiakas);
						Trace.out(Trace.Level.INFO, getVoittojenNostopiste().toString());
					}
				}
		   	   	break;
		   	   	
			case DEP4: // Asiakas lähtee kasinon Krapsistä
				asiakas = getKraps().getJononEnsimmainen();
				// Asiakkaalla pitää olla vähintään 50 polettia pelatakseen
				if (asiakas.getNykyinenPolettimaara() >= 50) {
					// Asiakas pelaa ja tarkistetaan voittiko vai hävisikö asiakas
					if (getKraps().voittikoAsiakas(asiakas)) {
						// Asiakkaan voittaessa todennäköisyys jatkaa pelaamista on suurempi
						todennakoisyys += 1;
					} else {
						// Asiakkaan hävitessä todennäköisyys jatkaa pelaamista on pienempi
						todennakoisyys -= 1;
					}
				} else { // Asiakas hakee lisää poletteja
					getKraps().otaJonosta(asiakas);
					getPalvelutiski().lisaaJonoon(asiakas);
					Trace.out(Trace.Level.INFO, getPalvelutiski().toString());
					break;
				}
				// Jos todennäköisyys on 2 tai alle, asiakas poistuu uuteen peliin, hakemaan lisää poletteja tai voittojen nostopisteelle
				if (todennakoisyys <= 2 && asiakas.getNykyinenPolettimaara() >= 50) {
					if (randomPalvelupiste == 1) {
						getKraps().otaJonosta(asiakas);
						getRuletti().lisaaJonoon(asiakas);
						Trace.out(Trace.Level.INFO, getRuletti().toString());
					} else if (randomPalvelupiste == 2) {
						getKraps().otaJonosta(asiakas);
						getBlackjack().lisaaJonoon(asiakas);
						Trace.out(Trace.Level.INFO, getBlackjack().toString());
					} else {
						getKraps().otaJonosta(asiakas);
						getVoittojenNostopiste().lisaaJonoon(asiakas);
						Trace.out(Trace.Level.INFO, getVoittojenNostopiste().toString());
					}
				} else if (todennakoisyys >= 3 && asiakas.getNykyinenPolettimaara() >= 50) { // Asiakas voi jatkaa pelaamista vain, jos hänellä on vähintään 50 polettia
					getKraps().jatkaPelaamista(asiakas);
				} else { // Asiakas hakee lisää poletteja tai menee voittojen nostopisteelle
					if (lisaaPolettejaTaiVoitot == 1) {
						getKraps().otaJonosta(asiakas);
						getPalvelutiski().lisaaJonoon(asiakas);
						Trace.out(Trace.Level.INFO, getPalvelutiski().toString());
					} else {
						getKraps().otaJonosta(asiakas);
						getVoittojenNostopiste().lisaaJonoon(asiakas);
						Trace.out(Trace.Level.INFO, getVoittojenNostopiste().toString());
					}
				}
		   	   	break;
		   	   	
			case DEP5: // Asiakas lähtee kasinon voittojen nostopisteeltä
				asiakas = getVoittojenNostopiste().getJononEnsimmainen(); 
				int kaynnit[] = new int[4];
		    	palvelupisteet[4].otaJonosta(asiakas);
		    	Trace.out(Trace.Level.INFO, "Asiakas " + asiakas.getId() + " poistuu kasinolta.");
				asiakas.setPoistumisaika(Kello.getInstance().getAika());
				for(int i = 0; i < (palvelupisteet.length - 1); i++) {
					for(int j = 0; j < palvelupisteet[i].getKaynteja().size(); j++) {
						if(palvelupisteet[i].getKaynteja().get(j).getId() == asiakas.getId()) {
							kaynnit[i]++;
						}
					}
				}
				// Tulostetaan asiakkaan raportti
	        	asiakas.raportti();
	        	// Tallennetaan keskimääräinen vietetty aika kasinolla
	        	keskimaarainenVietettyAika = asiakas.getKeskimaarainenVietettyAika();
	        	// Tulostetaan asiakkaan käynnit jokaiselta palvelupisteeltä paitsi voittojen nostopisteeltä
	        	Trace.out(Trace.Level.INFO, "Asiakas " + asiakas.getId() + " kävi palvelutiskillä " + kaynnit[0] + " kertaa.");
	        	Trace.out(Trace.Level.INFO, "Asiakas " + asiakas.getId() + " kävi ruletissa " + kaynnit[1] + " kertaa.");
	        	Trace.out(Trace.Level.INFO, "Asiakas " + asiakas.getId() + " kävi Blackjackissä " + kaynnit[2] + " kertaa.");
	        	Trace.out(Trace.Level.INFO, "Asiakas " + asiakas.getId() + " kävi Krapsissä " + kaynnit[3] + " kertaa.");
	        	break;
	        	
		}
		// Päivitetään kasinon tekemä tulos käyttöliittymään
		kontrolleri.paivitaTulos(getPalvelutiski().getTalonVoittoEuroina());
		
		// Visualisoidaan jonot käyttöliittymään
		kontrolleri.visualisoiJono(getPalvelutiski());
		kontrolleri.visualisoiJono(getRuletti());
		kontrolleri.visualisoiJono(getBlackjack());
		kontrolleri.visualisoiJono(getKraps());
		kontrolleri.visualisoiJono(getVoittojenNostopiste());
		
	}
	
	/**
	 * Päivittää simulaation tulosteet paikalliseen tietokantaan
	 */
	@Override
	protected void paivitaTietokanta() {
		
		// Tallennetaan simulointiajat palvelupisteihin tietokantaa varten
		getPalvelutiski().setSimulointiaika(getSimulointiaika());
		getRuletti().setSimulointiaika(getSimulointiaika());
		getBlackjack().setSimulointiaika(getSimulointiaika());
		getKraps().setSimulointiaika(getSimulointiaika());
		getVoittojenNostopiste().setSimulointiaika(getSimulointiaika());
		
		// Päivitetään tietokannassa olevia kokonaisoleskeluaikoja;
		palvDao.updateKokonaisoleskeluaika(getPalvelutiski());
		palvDao.updateKokonaisoleskeluaika(getRuletti());
		palvDao.updateKokonaisoleskeluaika(getBlackjack());
		palvDao.updateKokonaisoleskeluaika(getKraps());
		palvDao.updateKokonaisoleskeluaika(getVoittojenNostopiste());
		
		// Päivitetään tietokannassa olevia suoritustehoja
		palvDao.updateSuoritusteho(getPalvelutiski());
		palvDao.updateSuoritusteho(getRuletti());
		palvDao.updateSuoritusteho(getBlackjack());
		palvDao.updateSuoritusteho(getKraps());
		palvDao.updateSuoritusteho(getVoittojenNostopiste());
		
		// Päivitetään tietokannassa olevia käyttöasteita
		palvDao.updateKayttoaste(getPalvelutiski());
		palvDao.updateKayttoaste(getRuletti());
		palvDao.updateKayttoaste(getBlackjack());
		palvDao.updateKayttoaste(getKraps());
		palvDao.updateKayttoaste(getVoittojenNostopiste());
		
		// Päivitetään tietokannassa olevia palveltujen asiakkaiden määriä
		palvDao.updatePalveltujaasiakkaita(getPalvelutiski());
		palvDao.updatePalveltujaasiakkaita(getRuletti());
		palvDao.updatePalveltujaasiakkaita(getBlackjack());
		palvDao.updatePalveltujaasiakkaita(getKraps());
		palvDao.updatePalveltujaasiakkaita(getVoittojenNostopiste());
		
		// Päivitetään tietokannassa olevia keskimääräisiä palveluaikoja
		palvDao.updateKeskimaarainenPalveluaika(getPalvelutiski());
		palvDao.updateKeskimaarainenPalveluaika(getRuletti());
		palvDao.updateKeskimaarainenPalveluaika(getBlackjack());
		palvDao.updateKeskimaarainenPalveluaika(getKraps());
		palvDao.updateKeskimaarainenPalveluaika(getVoittojenNostopiste());
		
		// Päivitetään tietokannassa olevia keskimääräisiä jononpituuksia
		palvDao.updateKeskimaarainenJononpituus(getPalvelutiski());
		palvDao.updateKeskimaarainenJononpituus(getRuletti());
		palvDao.updateKeskimaarainenJononpituus(getBlackjack());
		palvDao.updateKeskimaarainenJononpituus(getKraps());
		palvDao.updateKeskimaarainenJononpituus(getVoittojenNostopiste());
		
		// Päivitetään tietokannassa olevia keskimääräisiä läpimenoaikoja
		palvDao.updateKeskimaarainenLapimenoaika(getPalvelutiski());
		palvDao.updateKeskimaarainenLapimenoaika(getRuletti());
		palvDao.updateKeskimaarainenLapimenoaika(getBlackjack());
		palvDao.updateKeskimaarainenLapimenoaika(getKraps());
		palvDao.updateKeskimaarainenLapimenoaika(getVoittojenNostopiste());
		
		// Päivitetään tietokannassa olevia aktiiviaikoja
		palvDao.updateAktiiviajat(getPalvelutiski());
		palvDao.updateAktiiviajat(getRuletti());
		palvDao.updateAktiiviajat(getBlackjack());
		palvDao.updateAktiiviajat(getKraps());
		palvDao.updateAktiiviajat(getVoittojenNostopiste());
		
		// Päivitetään tietokannassa oleva kasinon tekemä tulos
		kasinoDao.updatetalonVoittoEuroina(kasino);
		
		// Päivitetään tietokannassa oleva kasinoon saapuvien asiakkaiden määrä
		kasino.setAsiakasLKM(asiakasLkm);
		kasinoDao.updateAsiakasLKM(kasino);
		
		// Päivitetään tietokannassa oleva simulointiaika (löytyy tietokannasta nimellä kello)
		kasinoDao.updateKello(kasino);
		
		// Päivitetään tietokannassa oleva asiakkaiden keskimäärin vietetty aika kasinossa
		kasino.setkeskimaarainenVietettyAika(keskimaarainenVietettyAika);
		kasinoDao.updatekeskimaarainenVietettyAika(kasino);
		
	}
	
	/**
	 * Päivittää simulaation tulosteet käyttöliittymään ja konsoliin
	 */
	@Override
	protected void tulokset() {
		
		// Viedään kasinon tulosteet käyttöliittymän pääikkunaan
		kontrolleri.naytaLoppuaika(Kello.getInstance().getAika());
		kontrolleri.naytaAsiakasLkm(asiakasLkm);
		kontrolleri.naytaKeskimaarainenVietettyAika(keskimaarainenVietettyAika);
		kontrolleri.naytaGridPane();
		kontrolleri.naytaTiedot();
		
		// Viedään palvelupisteiden tulosteet omiin pop-up-ikkunoihin
		palvelupistePopUpKontrolleri.setPalvelutiskinTulosteet(getPalvelutiskinTulosteet());
		rulettiPopUpKontrolleri.setRuletinTulosteet(getRuletinTulosteet());
		blackjackPopUpKontrolleri.setBlackjackinTulosteet(getBlackjackinTulosteet());
		krapsPopUpKontrolleri.setKrapsinTulosteet(getKrapsinTulosteet());
		voittojenNostopistePopUpKontrolleri.setVoittojenNostopisteenTulosteet(getVoittojenNostopisteenTulosteet());
		
		Trace.out(Trace.Level.INFO, "\n**SIMULOINTI PÄÄTTYY**");
		
		if (palvelupisteet[4].getTalonVoittoEuroina() > 0) {
			Trace.out(Trace.Level.INFO, "\nKasino teki voittoa ajassa " + String.format("%.02f", Kello.getInstance().getAika()) + " yhteensä " + getVoittojenNostopiste().getTalonVoittoEuroina() + " euroa.");
		} else {
			Trace.out(Trace.Level.INFO, "\nKasino teki liiketappiota ajassa " + String.format("%.02f", Kello.getInstance().getAika()) + " yhteensä " + (getVoittojenNostopiste().getTalonVoittoEuroina() * -1) + " euroa.");
		}
		Trace.out(Trace.Level.INFO, "\nPalvelutiski palveli asiakkaita yhteensä " + getPalvelutiski().getKaynteja().size() + " kertaa.");
		Trace.out(Trace.Level.INFO, "Rulettia pelattiin yhteensä " + getRuletti().getKaynteja().size() + " kertaa.");
		Trace.out(Trace.Level.INFO, "Blackjackiä pelattiin yhteensä " + getBlackjack().getKaynteja().size() + " kertaa.");
		Trace.out(Trace.Level.INFO, "Krapsiä pelattiin yhteensä " + getKraps().getKaynteja().size() + " kertaa.");
		Trace.out(Trace.Level.INFO, "Asiakkaat nosti voittoja voittojen nostopisteellä yhteensä " + getVoittojenNostopiste().getKaynteja().size() + " kertaa.");;
		Trace.out(Trace.Level.INFO, "\nKasinolle saapui yhteensä " + asiakasLkm + " asiakasta.");
        
	}
	
	/**
	 * Tallentaa palvelutiskin tulosteet HashMap-listaan ja palauttaa koko listan
	 *
	 * @return HashMap-lista, joka koostuu String-avaimista ja String-arvoista
	 */
	public HashMap<String, String> getPalvelutiskinTulosteet() {

		HashMap<String, String> palvelutiskinTulosteet = new HashMap<>();
		palvelutiskinTulosteet.put("Palveltuja asiakkaita yhteensä", Integer.toString(getPalvelutiski().getPalvellutAsiakkaat()) + " asiakasta");
		// Tarkistetaan onko keskimääräinen palveluaika kelvollinen
		if (Double.isInfinite(getPalvelutiski().getKeskimaarainenPalveluaika())) {
			palvelutiskinTulosteet.put("Asiakkaiden keskimääräinen palveluaika", "Ei tiedossa");
		} else {
			palvelutiskinTulosteet.put("Asiakkaiden keskimääräinen palveluaika", String.format("%.02f", getPalvelutiski().getKeskimaarainenPalveluaika()));
		}
		palvelutiskinTulosteet.put("Keskimääräinen jononpituus", String.format("%.02f", getPalvelutiski().getKeskimaarainenJononpituus(getSimulointiaika())) + " asiakasta");
		// Tarkistetaan onko keskimääräinen läpimenoaika kelvollinen
		if (Double.isNaN(getPalvelutiski().getKeskimaarainenLapimenoaika())) {
			palvelutiskinTulosteet.put("Asiakkaiden keskimääräinen läpimenoaika", "Ei tiedossa");
		} else {
			palvelutiskinTulosteet.put("Asiakkaiden keskimääräinen läpimenoaika", String.format("%.02f", getPalvelutiski().getKeskimaarainenLapimenoaika()));
		}
		palvelutiskinTulosteet.put("Suoritusteho", String.format("%.02f", getPalvelutiski().getSuoritusteho(getSimulointiaika()) * 100) + " %");
		palvelutiskinTulosteet.put("Aktiiviaika", String.format("%.02f", getPalvelutiski().getAktiiviaika()));
		palvelutiskinTulosteet.put("Käyttöaste", String.format("%.02f", getPalvelutiski().getKayttoaste(getSimulointiaika()) * 100) + " %");
		palvelutiskinTulosteet.put("Asiakkaiden kokonaisoleskeluaika", String.format("%.02f", getPalvelutiski().getKokonaisoleskeluaika()));

		return palvelutiskinTulosteet;
	}

	/**
	 * Tallentaa ruletin tulosteet HashMap-listaan ja palauttaa koko listan
	 *
	 * @return HashMap-lista, joka koostuu String-avaimista ja String-arvoista
	 */
	public HashMap<String, String> getRuletinTulosteet() {

		HashMap<String, String> ruletinTulosteet = new HashMap<>();
		ruletinTulosteet.put("Palveltuja asiakkaita yhteensä", Integer.toString(getRuletti().getPalvellutAsiakkaat()) + " asiakasta");
		// Tarkistetaan onko keskimääräinen palveluaika kelvollinen
		if (Double.isInfinite(getRuletti().getKeskimaarainenPalveluaika())) {
			ruletinTulosteet.put("Asiakkaiden keskimääräinen palveluaika", "Ei tiedossa");
		} else {
			ruletinTulosteet.put("Asiakkaiden keskimääräinen palveluaika", String.format("%.02f", getRuletti().getKeskimaarainenPalveluaika()));
		}
		ruletinTulosteet.put("Keskimääräinen jononpituus", String.format("%.02f", getRuletti().getKeskimaarainenJononpituus(getSimulointiaika())) + " asiakasta");
		// Tarkistetaan onko keskimääräinen läpimenoaika kelvollinen
		if (Double.isNaN(getRuletti().getKeskimaarainenLapimenoaika())) {
			ruletinTulosteet.put("Asiakkaiden keskimääräinen läpimenoaika", "Ei tiedossa");
		} else {
			ruletinTulosteet.put("Asiakkaiden keskimääräinen läpimenoaika", String.format("%.02f", getRuletti().getKeskimaarainenLapimenoaika()));
		}
		ruletinTulosteet.put("Suoritusteho", String.format("%.02f", getRuletti().getSuoritusteho(getSimulointiaika()) * 100) + " %");
		ruletinTulosteet.put("Aktiiviaika", String.format("%.02f", getRuletti().getAktiiviaika()));
		ruletinTulosteet.put("Käyttöaste", String.format("%.02f", getRuletti().getKayttoaste(getSimulointiaika()) * 100) + " %");
		ruletinTulosteet.put("Asiakkaiden kokonaisoleskeluaika", String.format("%.02f", getRuletti().getKokonaisoleskeluaika()));

		return ruletinTulosteet;
	}

	/**
	 * Tallentaa Blackjackin tulosteet HashMap-listaan ja palauttaa koko listan
	 *
	 * @return HashMap-lista, joka koostuu String-avaimista ja String-arvoista
	 */
	public HashMap<String, String> getBlackjackinTulosteet() {

		HashMap<String, String> blackjackinTulosteet = new HashMap<>();
		blackjackinTulosteet.put("Palveltuja asiakkaita yhteensä", Integer.toString(getBlackjack().getPalvellutAsiakkaat()) + " asiakasta");
		// Tarkistetaan onko keskimääräinen palveluaika kelvollinen
		if (Double.isInfinite(getBlackjack().getKeskimaarainenPalveluaika())) {
			blackjackinTulosteet.put("Asiakkaiden keskimääräinen palveluaika", "Ei tiedossa");
		} else {
			blackjackinTulosteet.put("Asiakkaiden keskimääräinen palveluaika", String.format("%.02f", getBlackjack().getKeskimaarainenPalveluaika()));
		}
		blackjackinTulosteet.put("Keskimääräinen jononpituus", String.format("%.02f", getBlackjack().getKeskimaarainenJononpituus(getSimulointiaika())) + " asiakasta");
		// Tarkistetaan onko keskimääräinen läpimenoaika kelvollinen
		if (Double.isNaN(getBlackjack().getKeskimaarainenLapimenoaika())) {
			blackjackinTulosteet.put("Asiakkaiden keskimääräinen läpimenoaika", "Ei tiedossa");
		} else {
			blackjackinTulosteet.put("Asiakkaiden keskimääräinen läpimenoaika", String.format("%.02f", getBlackjack().getKeskimaarainenLapimenoaika()));
		}
		blackjackinTulosteet.put("Suoritusteho", String.format("%.02f", getBlackjack().getSuoritusteho(getSimulointiaika()) * 100) + " %");
		blackjackinTulosteet.put("Aktiiviaika", String.format("%.02f", getBlackjack().getAktiiviaika()));
		blackjackinTulosteet.put("Käyttöaste", String.format("%.02f", getBlackjack().getKayttoaste(getSimulointiaika()) * 100) + " %");
		blackjackinTulosteet.put("Asiakkaiden kokonaisoleskeluaika", String.format("%.02f", getBlackjack().getKokonaisoleskeluaika()));

		return blackjackinTulosteet;
	}

	/**
	 * Tallentaa Krapsin tulosteet HashMap-listaan ja palauttaa koko listan
	 *
	 * @return HashMap-lista, joka koostuu String-avaimista ja String-arvoista
	 */
	public HashMap<String, String> getKrapsinTulosteet() {

		HashMap<String, String> krapsinTulosteet = new HashMap<>();
		krapsinTulosteet.put("Palveltuja asiakkaita yhteensä", Integer.toString(getKraps().getPalvellutAsiakkaat()) + " asiakasta");
		// Tarkistetaan onko keskimääräinen palveluaika kelvollinen
		if (Double.isInfinite(getKraps().getKeskimaarainenPalveluaika())) {
			krapsinTulosteet.put("Asiakkaiden keskimääräinen palveluaika", "Ei tiedossa");
		} else {
			krapsinTulosteet.put("Asiakkaiden keskimääräinen palveluaika", String.format("%.02f", getKraps().getKeskimaarainenPalveluaika()));
		}
		krapsinTulosteet.put("Keskimääräinen jononpituus", String.format("%.02f", getKraps().getKeskimaarainenJononpituus(getSimulointiaika())) + " asiakasta");
		// Tarkistetaan onko keskimääräinen läpimenoaika kelvollinen
		if (Double.isNaN(getKraps().getKeskimaarainenLapimenoaika())) {
			krapsinTulosteet.put("Asiakkaiden keskimääräinen läpimenoaika", "Ei tiedossa");
		} else {
			krapsinTulosteet.put("Asiakkaiden keskimääräinen läpimenoaika", String.format("%.02f", getKraps().getKeskimaarainenLapimenoaika()));
		}
		krapsinTulosteet.put("Suoritusteho", String.format("%.02f", getKraps().getSuoritusteho(getSimulointiaika()) * 100) + " %");
		krapsinTulosteet.put("Aktiiviaika", String.format("%.02f", getKraps().getAktiiviaika()));
		krapsinTulosteet.put("Käyttöaste", String.format("%.02f", getKraps().getKayttoaste(getSimulointiaika()) * 100) + " %");
		krapsinTulosteet.put("Asiakkaiden kokonaisoleskeluaika", String.format("%.02f", getKraps().getKokonaisoleskeluaika()));

		return krapsinTulosteet;
	}

	/**
	 * Tallentaa voittojen nostopisteen tulosteet HashMap-listaan ja palauttaa koko listan
	 *
	 * @return HashMap-lista, joka koostuu String-avaimista ja String-arvoista
	 */
	public HashMap<String, String> getVoittojenNostopisteenTulosteet() {

		HashMap<String, String> voittojenNostopisteenTulosteet = new HashMap<>();
		voittojenNostopisteenTulosteet.put("Palveltuja asiakkaita yhteensä", Integer.toString(getVoittojenNostopiste().getPalvellutAsiakkaat()) + " asiakasta");
		// Tarkistetaan onko keskimääräinen palveluaika kelvollinen
		if (Double.isInfinite(getVoittojenNostopiste().getKeskimaarainenPalveluaika())) {
			voittojenNostopisteenTulosteet.put("Asiakkaiden keskimääräinen palveluaika", "Ei tiedossa");
		} else {
			voittojenNostopisteenTulosteet.put("Asiakkaiden keskimääräinen palveluaika", String.format("%.02f", getVoittojenNostopiste().getKeskimaarainenPalveluaika()));
		}
		voittojenNostopisteenTulosteet.put("Keskimääräinen jononpituus", String.format("%.02f", getVoittojenNostopiste().getKeskimaarainenJononpituus(getSimulointiaika())) + " asiakasta");
		// Tarkistetaan onko keskimääräinen läpimenoaika kelvollinen
		if (Double.isNaN(getKraps().getKeskimaarainenLapimenoaika())) {
			voittojenNostopisteenTulosteet.put("Asiakkaiden keskimääräinen läpimenoaika", "Ei tiedossa");
		} else {
			voittojenNostopisteenTulosteet.put("Asiakkaiden keskimääräinen läpimenoaika", String.format("%.02f", getVoittojenNostopiste().getKeskimaarainenLapimenoaika()));
		}
		voittojenNostopisteenTulosteet.put("Suoritusteho", String.format("%.02f", getVoittojenNostopiste().getSuoritusteho(getSimulointiaika()) * 100) + " %");
		voittojenNostopisteenTulosteet.put("Aktiiviaika", String.format("%.02f", getVoittojenNostopiste().getAktiiviaika()));
		voittojenNostopisteenTulosteet.put("Käyttöaste", String.format("%.02f", getVoittojenNostopiste().getKayttoaste(getSimulointiaika()) * 100) + " %");
		voittojenNostopisteenTulosteet.put("Asiakkaiden kokonaisoleskeluaika", String.format("%.02f", getVoittojenNostopiste().getKokonaisoleskeluaika()));

		return voittojenNostopisteenTulosteet;
	}

	/**
	 * Palauttaa Palvelutiski-listasta palvelutiskin
	 *
	 * @return Palvelutiski-olio
	 */
	public Palvelupiste getPalvelutiski() {
		return palvelupisteet[0];
	}

	/**
	 * Palauttaa Palvelutiski-listasta ruletin
	 *
	 * @return Palvelutiski-olio
	 */
	public Palvelupiste getRuletti() {
		return palvelupisteet[1];
	}

	/**
	 * Palauttaa Palvelutiski-listasta Blackjackin
	 *
	 * @return Palvelutiski-olio
	 */
	public Palvelupiste getBlackjack() {
		return palvelupisteet[2];
	}

	/**
	 * Palauttaa Palvelutiski-listasta Krapsin
	 *
	 * @return Palvelutiski-olio
	 */
	public Palvelupiste getKraps() {
		return palvelupisteet[3];
	}

	/**
	 * Palauttaa Palvelutiski-listasta voittojen nostopisteen
	 *
	 * @return Palvelutiski-olio
	 */
	public Palvelupiste getVoittojenNostopiste() {
		return palvelupisteet[4];
	}

}