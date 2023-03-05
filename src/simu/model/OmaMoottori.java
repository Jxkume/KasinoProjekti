package simu.model;

import java.util.HashMap;

import eduni.distributions.Negexp;
import eduni.distributions.Normal;
import eduni.distributions.Uniform;
import simu.framework.Kello;
import simu.framework.Moottori;
import simu.framework.Saapumisprosessi;
import simu.framework.Tapahtuma;
import simu.framework.Trace;
import view.SimulaattorinPaaikkunaKontrolleri;


public class OmaMoottori extends Moottori {
	
	private Saapumisprosessi saapumisprosessi;
	private static int asiakasLkm = 0;
	private double keskimaarainenVietettyAika;
	
	//public OmaMoottori(IKontrolleri kontrolleri) { // UUSI
	public OmaMoottori(SimulaattorinPaaikkunaKontrolleri kontrolleri) {

		super(kontrolleri); //UUSI
		
		// TO-DO: muutetaan jakaumat oikeiksi, kun ollaan päätetty ne. - Valdo
		saapumisprosessi = new Saapumisprosessi(new Negexp(15,5), tapahtumalista, TapahtumanTyyppi.ARR1);
	
		palvelupisteet = new Palvelupiste[5];
		palvelupisteet[0] = new Palvelupiste(new Normal(5,3), tapahtumalista, TapahtumanTyyppi.DEP1, "Palvelutiski");
		palvelupisteet[1] = new Palvelupiste(new Normal(6,3), tapahtumalista, TapahtumanTyyppi.DEP2, "Ruletti");
		palvelupisteet[2] = new Palvelupiste(new Normal(7,3), tapahtumalista, TapahtumanTyyppi.DEP3, "Blackjack");
		palvelupisteet[3] = new Palvelupiste(new Normal(4,3), tapahtumalista, TapahtumanTyyppi.DEP4, "Kraps");
		palvelupisteet[4] = new Palvelupiste(new Normal(5,3), tapahtumalista, TapahtumanTyyppi.DEP5, "Voittojen nostopiste");
		
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
		// Asetetaan käyttäjän käyttöliittymässä valitsema jakauma palvelupisteille
		if (kontrolleri.getJakauma().equals("Normaali jakauma")) {
			getPalvelutiski().setGenerator(new Normal(5,3));
			getRuletti().setGenerator(new Normal(5,3));
			getBlackjack().setGenerator(new Normal(5,3));
			getKraps().setGenerator(new Normal(5,3));
			getVoittojenNostopiste().setGenerator(new Normal(5,3));
		} else if (kontrolleri.getJakauma().equals("Eksponenttijakauma")) {
			getPalvelutiski().setGenerator(new Negexp(5,3));
			getRuletti().setGenerator(new Negexp(5,3));
			getBlackjack().setGenerator(new Negexp(5,3));
			getKraps().setGenerator(new Negexp(5,3));
			getVoittojenNostopiste().setGenerator(new Negexp(5,3));
		} else if (kontrolleri.getJakauma().equals("Tasainen jakauma")) {
			getPalvelutiski().setGenerator(new Uniform(5,10));
			getRuletti().setGenerator(new Uniform(5,10));
			getBlackjack().setGenerator(new Uniform(5,10));
			getKraps().setGenerator(new Uniform(5,10));
			getVoittojenNostopiste().setGenerator(new Uniform(5,10));
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
				// Asiakkaalla pitää olla vähintään 10 polettia pelatakseen
				if (asiakas.getNykyinenPolettimaara() >= 10) {
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
				// Asiakkaalla pitää olla vähintään 10 polettia pelatakseen
				if (asiakas.getNykyinenPolettimaara() >= 10) {
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
				// Asiakkaalla pitää olla vähintään 10 polettia pelatakseen
				if (asiakas.getNykyinenPolettimaara() >= 10) {
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
		kontrolleri.paivitaTulos(palvelupisteet[0].getTalonVoittoEuroina());
		//kontrolleri.visualisoiJono(palvelupisteet[0]);
	}

	@Override
	protected void tulokset() {
		
		kontrolleri.naytaLoppuaika(Kello.getInstance().getAika());
		kontrolleri.naytaAsiakasLkm(asiakasLkm);
		kontrolleri.naytaKeskimaarainenVietettyAika(keskimaarainenVietettyAika);
		kontrolleri.naytaGridPane();
		kontrolleri.naytaTiedot();
		kontrolleri.setPalvelutiskinTulosteet(getPalvelutiskinTulosteet());
		//PalvelupisteDao palvDao = new PalvelupisteDao();
		//palvDao.poistaTiski(palvelupisteet[0].getNimi());
		
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
        
        Trace.out(Trace.Level.INFO, "Palvelutiskin suoritusteho oli " + (getPalvelutiski().getSuoritusteho(getSimulointiaika()) * 100) + " prosenttia.");
        Trace.out(Trace.Level.INFO, "Palvelutiskin käyttöaste oli " + (getPalvelutiski().getKayttoaste(getSimulointiaika()) * 100) + " prosenttia.");
        Trace.out(Trace.Level.INFO, "Palvelutiskin keskimääräinen palveluaika oli " + String.format("%.02f", getPalvelutiski().getKeskimaarainenPalveluaika()) + ".");
        Trace.out(Trace.Level.INFO, "Palvelutiskin kokonaisoleskeluaika oli " + String.format("%.02f", getPalvelutiski().getKokonaisoleskeluaika()) + ".");
        Trace.out(Trace.Level.INFO, "Palvelutiskin keskimääräinen läpimenoaika oli " + String.format("%.02f", getPalvelutiski().getKeskimaarainenLapimenoaika()) + ".");
        Trace.out(Trace.Level.INFO, "Palvelutiskin keskimääräinen jononpituus oli " + String.format("%.02f", getPalvelutiski().getKeskimaarainenJononpituus(getSimulointiaika())) + " asiakasta.");
	}
	
	public HashMap<String, String> getPalvelutiskinTulosteet() {
		HashMap<String, String> palvelutiskinTulosteet = new HashMap<>();
		palvelutiskinTulosteet.put("Palveltuja asiakkaita yhteensä", Integer.toString(getPalvelutiski().getPalvellutAsiakkaat()));
		palvelutiskinTulosteet.put("Asiakkaiden keskimääräinen palveluaika", Double.toString(getPalvelutiski().getKeskimaarainenPalveluaika()));
		palvelutiskinTulosteet.put("Keskimääräinen jononpituus", Double.toString(getPalvelutiski().getKeskimaarainenJononpituus(getSimulointiaika())));
		palvelutiskinTulosteet.put("Asiakkaiden keskimääräinen läpimenoaika", Double.toString(getPalvelutiski().getKeskimaarainenLapimenoaika()));
		palvelutiskinTulosteet.put("Suoritusteho", Double.toString(getPalvelutiski().getSuoritusteho(getSimulointiaika())));
		palvelutiskinTulosteet.put("Aktiiviaika", Double.toString(getPalvelutiski().getAktiiviaika()));
		palvelutiskinTulosteet.put("Käyttöaste", Double.toString(getPalvelutiski().getKayttoaste(getSimulointiaika())));
		palvelutiskinTulosteet.put("Asiakkaiden kokonaisoleskeluaika", Double.toString(getPalvelutiski().getKokonaisoleskeluaika()));
		return palvelutiskinTulosteet;
	}
	
}