package simu.model;

import java.util.HashMap;

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

public class OmaMoottori extends Moottori {
	
	private Saapumisprosessi saapumisprosessi;
	private static int asiakasLkm = 0;
	private double keskimaarainenVietettyAika;
	private PalvelupisteDao palvDao = new PalvelupisteDao();
	
	// Käyttöliittymän pop-up-ikkunoiden kontrollerit
	private PalvelutiskiPopUpKontrolleri palvelupistePopUpKontrolleri = new PalvelutiskiPopUpKontrolleri();
	private RulettiPopUpKontrolleri rulettiPopUpKontrolleri = new RulettiPopUpKontrolleri();
	private BlackjackPopUpKontrolleri blackjackPopUpKontrolleri = new BlackjackPopUpKontrolleri();
	private KrapsPopUpKontrolleri krapsPopUpKontrolleri = new KrapsPopUpKontrolleri();
	private VoittojenNostopistePopUpKontrolleri voittojenNostopistePopUpKontrolleri = new VoittojenNostopistePopUpKontrolleri();
	
	//public OmaMoottori(IKontrolleri kontrolleri) { // UUSI
	public OmaMoottori(SimulaattorinPaaikkunaKontrolleri kontrolleri) {

		super(kontrolleri); //UUSI
		
		// TO-DO: muutetaan jakaumat oikeiksi, kun ollaan päätetty ne. - Valdo
		saapumisprosessi = new Saapumisprosessi(new Negexp(15,5), tapahtumalista, TapahtumanTyyppi.ARR1);
	
		palvelupisteet = new Palvelupiste[5];
		palvelupisteet[0] = new Palvelupiste(new Normal(4,1), tapahtumalista, TapahtumanTyyppi.DEP1, "Palvelutiski");
		palvelupisteet[1] = new Palvelupiste(new Normal(6,3), tapahtumalista, TapahtumanTyyppi.DEP2, "Ruletti");
		palvelupisteet[2] = new Palvelupiste(new Normal(7,3), tapahtumalista, TapahtumanTyyppi.DEP3, "Blackjack");
		palvelupisteet[3] = new Palvelupiste(new Normal(4,3), tapahtumalista, TapahtumanTyyppi.DEP4, "Kraps");
		palvelupisteet[4] = new Palvelupiste(new Normal(4,1), tapahtumalista, TapahtumanTyyppi.DEP5, "Voittojen nostopiste");
		
	}

	// Kasinon palvelutiskin getteri
	protected Palvelupiste getPalvelutiski() {
		return palvelupisteet[0];
	}
	
	// Kasinon ruletin getteri
	protected Palvelupiste getRuletti() {
		return palvelupisteet[1];
	}
	
	// Kasinon Blackjackin getteri
	protected Palvelupiste getBlackjack() {
		return palvelupisteet[2];
	}
	
	// Kasinon Krapsin getteri
	protected Palvelupiste getKraps() {
		return palvelupisteet[3];
	}
	
	// Kasinon voittojen nostopisteen getteri
	protected Palvelupiste getVoittojenNostopiste() {
		return palvelupisteet[4];
	}
	
	@Override
	protected void alustukset() {
		// Generoidaan ensimmäinen asiakas järjestelmään
		saapumisprosessi.generoiSeuraava();
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
	}
	
	// Simulaation logiikka miten asiakkaat liikkuvat palvelupisteillä
	// TO-DO: Loppuraportti - Tapio
	// TO-DO: Asiakaskohtainen raportti - Valdo
	// TO-DO: Pitää miettiä peleille järkevät voittotodennäköisyydet ja voittosummat (voittikoAsiakas-metodi Palvelupiste-luokassa) - Valdo
	        
	@Override
	protected void suoritaTapahtuma(Tapahtuma t){  // B-vaiheen tapahtumat
		
		Asiakas asiakas;
		// Arvotaan luku väliltä 1-4
		int todennakoisyys = (int) Math.floor(Math.random() * (4 - 1 + 1) + 1);
		// Arvotaan luku väliltä 1-3
		int randomPalvelupiste = (int) Math.floor(Math.random() * (3 - 1 + 1) + 1);
		// Arvotaan luku väliltä 1-2
		int lisaaPolettejaTaiVoitot = (int) Math.floor(Math.random() * (2 - 1 + 1) + 1);
		
		switch (t.getTyyppi()) {
			
			case ARR1: // Asiakas saapuu kasinoon
				getPalvelutiski().lisaaJonoon(new Asiakas());
				asiakasLkm++;
		    	//kontrolleri.visualisoiAsiakas(); // UUSI
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
						// Lisätään lukuun 1
						todennakoisyys += 1;
					} else {
						// Vähennetään luvusta 1
						todennakoisyys -= 1;
					}
				} else { // Asiakas hakee lisää poletteja
					getRuletti().otaJonosta(asiakas);
					getPalvelutiski().lisaaJonoon(asiakas);
					Trace.out(Trace.Level.INFO, getPalvelutiski().toString());
					break;
				}
				// Jos todennäköisyys on 2 tai alle, asiakas poistuu uuteen peliin tai voittojen nostopisteelle
				if (todennakoisyys <= 2 && asiakas.getNykyinenPolettimaara() >= 10) {
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
				} else if (todennakoisyys >= 3 && asiakas.getNykyinenPolettimaara() >= 10) { // Asiakas voi jatkaa pelaamista vain, jos hänellä on vähintään 10 polettia
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
						// Lisätään lukuun 1
						todennakoisyys += 1;
					} else {
						// Vähennetään luvusta 1
						todennakoisyys -= 1;
					}
				} else { // Asiakas hakee lisää poletteja
					getBlackjack().otaJonosta(asiakas);
					getPalvelutiski().lisaaJonoon(asiakas);
					Trace.out(Trace.Level.INFO, getPalvelutiski().toString());
					break;
				}
				// Jos todennäköisyys on 2 tai alle, asiakas poistuu uuteen peliin, hakemaan lisää poletteja tai voittojen nostopisteelle
				if (todennakoisyys <= 2 && asiakas.getNykyinenPolettimaara() >= 10) {
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
				} else if (todennakoisyys >= 3 && asiakas.getNykyinenPolettimaara() >= 10) { // Asiakas voi jatkaa pelaamista vain, jos hänellä on vähintään 10 polettia
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
						// Lisätään lukuun 1
						todennakoisyys += 1;
					} else {
						// Vähennetään luvusta 1
						todennakoisyys -= 1;
					}
				} else { // Asiakas hakee lisää poletteja
					getKraps().otaJonosta(asiakas);
					getPalvelutiski().lisaaJonoon(asiakas);
					Trace.out(Trace.Level.INFO, getPalvelutiski().toString());
					break;
				}
				// Jos todennäköisyys on 2 tai alle, asiakas poistuu uuteen peliin, hakemaan lisää poletteja tai voittojen nostopisteelle
				if (todennakoisyys <= 2 && asiakas.getNykyinenPolettimaara() >= 10) {
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
				} else if (todennakoisyys >= 3 && asiakas.getNykyinenPolettimaara() >= 10) { // Asiakas voi jatkaa pelaamista vain, jos hänellä on vähintään 10 polettia
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
	        	asiakas.raportti();
	        	// Tallennetaan keskimääräinen vietetty aika kasinolla
	        	keskimaarainenVietettyAika = asiakas.getKeskimaarainenVietettyAika();
	        	Trace.out(Trace.Level.INFO, "Asiakas " + asiakas.getId() + " kävi palvelutiskillä " + kaynnit[0] + " kertaa.");
	        	Trace.out(Trace.Level.INFO, "Asiakas " + asiakas.getId() + " kävi ruletissa " + kaynnit[1] + " kertaa.");
	        	Trace.out(Trace.Level.INFO, "Asiakas " + asiakas.getId() + " kävi Blackjackissä " + kaynnit[2] + " kertaa.");
	        	Trace.out(Trace.Level.INFO, "Asiakas " + asiakas.getId() + " kävi Krapsissä " + kaynnit[3] + " kertaa.");
	        	break;
		}
		kontrolleri.paivitaTulos(getPalvelutiski().getTalonVoittoEuroina());
		// Visualisoidaan jonot käyttöliittymään
		kontrolleri.visualisoiJono(getPalvelutiski());
		kontrolleri.visualisoiJono(getRuletti());
		kontrolleri.visualisoiJono(getBlackjack());
		kontrolleri.visualisoiJono(getKraps());
		kontrolleri.visualisoiJono(getVoittojenNostopiste());
	}
	
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
		
		// Päivitetään tietokannassa olevia kayttoasteita
		palvDao.updateKayttoaste(getPalvelutiski());
		palvDao.updateKayttoaste(getRuletti());
		palvDao.updateKayttoaste(getBlackjack());
		palvDao.updateKayttoaste(getKraps());
		palvDao.updateKayttoaste(getVoittojenNostopiste());
		
		// Päivitetään tietokannassa olevia palvellutasiakkaiden määrä
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
	}
	
	@Override
	protected void tulokset() {
		
		kontrolleri.naytaLoppuaika(Kello.getInstance().getAika());
		kontrolleri.naytaAsiakasLkm(asiakasLkm);
		kontrolleri.naytaKeskimaarainenVietettyAika(keskimaarainenVietettyAika);
		kontrolleri.naytaGridPane();
		kontrolleri.naytaTiedot();
		
		// Viedään palvelupisteiden tulosteen omiin pop-up-ikkunoihin
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
	
	// Palvelutiskin tulosteet käyttöliittymään
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
			palvelutiskinTulosteet.put("Suoritusteho", String.format("%.02f", getPalvelutiski().getSuoritusteho(getSimulointiaika()) * 100) + " prosenttia");
			palvelutiskinTulosteet.put("Aktiiviaika", String.format("%.02f", getPalvelutiski().getAktiiviaika()));
			palvelutiskinTulosteet.put("Käyttöaste", String.format("%.02f", getPalvelutiski().getKayttoaste(getSimulointiaika()) * 100) + " prosenttia");
			palvelutiskinTulosteet.put("Asiakkaiden kokonaisoleskeluaika", String.format("%.02f", getPalvelutiski().getKokonaisoleskeluaika()));
			
			return palvelutiskinTulosteet;
		}
		
		// Ruletin tulosteet käyttöliittymään
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
			ruletinTulosteet.put("Suoritusteho", String.format("%.02f", getRuletti().getSuoritusteho(getSimulointiaika()) * 100) + " prosenttia");
			ruletinTulosteet.put("Aktiiviaika", String.format("%.02f", getRuletti().getAktiiviaika()));
			ruletinTulosteet.put("Käyttöaste", String.format("%.02f", getRuletti().getKayttoaste(getSimulointiaika()) * 100) + " prosenttia");
			ruletinTulosteet.put("Asiakkaiden kokonaisoleskeluaika", String.format("%.02f", getRuletti().getKokonaisoleskeluaika()));
			
			return ruletinTulosteet;
		}
		
		// Blackjackin tulosteet käyttöliittymään
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
			blackjackinTulosteet.put("Suoritusteho", String.format("%.02f", getBlackjack().getSuoritusteho(getSimulointiaika()) * 100) + " prosenttia");
			blackjackinTulosteet.put("Aktiiviaika", String.format("%.02f", getBlackjack().getAktiiviaika()));
			blackjackinTulosteet.put("Käyttöaste", String.format("%.02f", getBlackjack().getKayttoaste(getSimulointiaika()) * 100) + " prosenttia");
			blackjackinTulosteet.put("Asiakkaiden kokonaisoleskeluaika", String.format("%.02f", getBlackjack().getKokonaisoleskeluaika()));
			
			return blackjackinTulosteet;
		}
		
		// Krapsin tulosteet käyttöliittymään
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
			krapsinTulosteet.put("Suoritusteho", String.format("%.02f", getKraps().getSuoritusteho(getSimulointiaika()) * 100) + " prosenttia");
			krapsinTulosteet.put("Aktiiviaika", String.format("%.02f", getKraps().getAktiiviaika()));
			krapsinTulosteet.put("Käyttöaste", String.format("%.02f", getKraps().getKayttoaste(getSimulointiaika()) * 100) + " prosenttia");
			krapsinTulosteet.put("Asiakkaiden kokonaisoleskeluaika", String.format("%.02f", getKraps().getKokonaisoleskeluaika()));
			
			return krapsinTulosteet;
		}
		
		// Voittojen nostopisteen tulosteet käyttöliittymään
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
			voittojenNostopisteenTulosteet.put("Suoritusteho", String.format("%.02f", getVoittojenNostopiste().getSuoritusteho(getSimulointiaika()) * 100) + " prosenttia");
			voittojenNostopisteenTulosteet.put("Aktiiviaika", String.format("%.02f", getVoittojenNostopiste().getAktiiviaika()));
			voittojenNostopisteenTulosteet.put("Käyttöaste", String.format("%.02f", getVoittojenNostopiste().getKayttoaste(getSimulointiaika()) * 100) + " prosenttia");
			voittojenNostopisteenTulosteet.put("Asiakkaiden kokonaisoleskeluaika", String.format("%.02f", getVoittojenNostopiste().getKokonaisoleskeluaika()));
			
			return voittojenNostopisteenTulosteet;
		}
}