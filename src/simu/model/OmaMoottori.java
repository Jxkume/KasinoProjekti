package simu.model;

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
	public Palvelupiste getPalvelutiski() {
		return palvelupisteet[0];
	}
	
	// Kasinon ruletin getteri
	public Palvelupiste getRuletti() {
		return palvelupisteet[1];
	}
	
	// Kasinon Blackjackin getteri
	public Palvelupiste getBlackjack() {
		return palvelupisteet[2];
	}
	
	// Kasinon Krapsin getteri
	public Palvelupiste getKraps() {
		return palvelupisteet[3];
	}
	
	// Kasinon voittojen nostopisteen getteri
	public Palvelupiste getVoittojenNostopiste() {
		return palvelupisteet[4];
	}

	@Override
	protected void alustukset() {
		saapumisprosessi.generoiSeuraava(); // Ensimmäinen saapuminen järjestelmään
	}
	
	// Simulaation logiikka miten asiakkaat liikkuvat palvelupisteillä
	// TO-DO: Loppuraportti - Tapio
	// TO-DO: Asiakaskohtainen raportti - Valdo
	// TO-DO: Pitää miettiä peleille järkevät voittotodennäköisyydet ja voittosummat (voittikoAsiakas-metodi Palvelupiste-luokassa) - Valdo
	        
	@Override
	protected void suoritaTapahtuma(Tapahtuma t){  // B-vaiheen tapahtumat

		// Tarkistetaan minkä jakauman käyttäjä on valinnut käyttöliittymässä
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
		
		Asiakas asiakas;
		// Arvotaan luku väliltä 1-4
		int todennakoisyys = (int) Math.floor(Math.random() * (4 - 1 + 1) + 1);
		// Arvotaan luku väliltä 1-3
		int randomPalvelupiste = (int) Math.floor(Math.random() * (3 - 1 + 1) + 1);
		// Arvotaan luku väliltä 1-2
		int lisaaPolettejaTaiVoitot = (int) Math.floor(Math.random() * (2 - 1 + 1) + 1);
		
		switch (t.getTyyppi()) {
			
			case ARR1: // Asiakas saapuu kasinoon
				palvelupisteet[0].lisaaJonoon(new Asiakas());
				asiakasLkm++;
		    	//kontrolleri.visualisoiAsiakas(); // UUSI
				Trace.out(Trace.Level.INFO, palvelupisteet[0].toString());
		    	saapumisprosessi.generoiSeuraava();
				break;
				
			case DEP1: // Asiakas lähtee kasinon palvelutiskiltä
				asiakas = palvelupisteet[0].getJononEnsimmainen();
				// Asiakkaalle annetaan poletteja palvelutiskillä
				asiakas.annaPolettejaPalvelutiskilla();
				if (randomPalvelupiste == 1) {
					palvelupisteet[0].otaJonosta(asiakas);
					palvelupisteet[1].lisaaJonoon(asiakas);
					Trace.out(Trace.Level.INFO, palvelupisteet[1].toString());
				} else if (randomPalvelupiste == 2) {
					palvelupisteet[0].otaJonosta(asiakas);
					palvelupisteet[2].lisaaJonoon(asiakas);
					Trace.out(Trace.Level.INFO, palvelupisteet[2].toString());
				} else {
					palvelupisteet[0].otaJonosta(asiakas);
					palvelupisteet[3].lisaaJonoon(asiakas);
					Trace.out(Trace.Level.INFO, palvelupisteet[3].toString());
				}
				break;
				
			case DEP2: // Asiakas lähtee kasinon ruletista
				asiakas = palvelupisteet[1].getJononEnsimmainen();
				// Asiakkaalla pitää olla vähintään 10 polettia pelatakseen
				if (asiakas.getNykyinenPolettimaara() >= 10) {
					// Asiakas pelaa ja tarkistetaan voittiko vai hävisikö asiakas
					if (palvelupisteet[1].voittikoAsiakas(asiakas)) {
						// Lisätään lukuun 1
						todennakoisyys += 1;
					} else {
						// Vähennetään luvusta 1
						todennakoisyys -= 1;
					}
				} else { // Asiakas hakee lisää poletteja
					palvelupisteet[1].otaJonosta(asiakas);
					palvelupisteet[0].lisaaJonoon(asiakas);
					Trace.out(Trace.Level.INFO, palvelupisteet[0].toString());
					break;
				}
				// Jos todennäköisyys on 2 tai alle, asiakas poistuu uuteen peliin tai voittojen nostopisteelle
				if (todennakoisyys <= 2 && asiakas.getNykyinenPolettimaara() >= 10) {
					if (randomPalvelupiste == 1) {
						palvelupisteet[1].otaJonosta(asiakas);
						palvelupisteet[2].lisaaJonoon(asiakas);
						Trace.out(Trace.Level.INFO, palvelupisteet[2].toString());
					} else if (randomPalvelupiste == 2) {
						palvelupisteet[1].otaJonosta(asiakas);
						palvelupisteet[3].lisaaJonoon(asiakas);
						Trace.out(Trace.Level.INFO, palvelupisteet[3].toString());
					} else {
						palvelupisteet[1].otaJonosta(asiakas);
						palvelupisteet[4].lisaaJonoon(asiakas);
						Trace.out(Trace.Level.INFO, palvelupisteet[4].toString());
					}
				} else if (todennakoisyys >= 3 && asiakas.getNykyinenPolettimaara() >= 10) { // Asiakas voi jatkaa pelaamista vain, jos hänellä on vähintään 10 polettia
					palvelupisteet[1].jatkaPelaamista(asiakas);
				} else { // Asiakas hakee lisää poletteja tai menee voittojen nostopisteelle
					if (lisaaPolettejaTaiVoitot == 1) {
						palvelupisteet[1].otaJonosta(asiakas);
						palvelupisteet[0].lisaaJonoon(asiakas);
						Trace.out(Trace.Level.INFO, palvelupisteet[0].toString());
					} else {
						palvelupisteet[1].otaJonosta(asiakas);
						palvelupisteet[4].lisaaJonoon(asiakas);
						Trace.out(Trace.Level.INFO, palvelupisteet[4].toString());
					}
				}
		   	   	break;
		   	   
			case DEP3: // Asiakas lähtee kasinon Blackjackistä
				asiakas = palvelupisteet[2].getJononEnsimmainen();
				// Asiakkaalla pitää olla vähintään 10 polettia pelatakseen
				if (asiakas.getNykyinenPolettimaara() >= 10) {
					// Asiakas pelaa ja tarkistetaan voittiko vai hävisikö asiakas
					if (palvelupisteet[2].voittikoAsiakas(asiakas)) {
						// Lisätään lukuun 1
						todennakoisyys += 1;
					} else {
						// Vähennetään luvusta 1
						todennakoisyys -= 1;
					}
				} else { // Asiakas hakee lisää poletteja
					palvelupisteet[2].otaJonosta(asiakas);
					palvelupisteet[0].lisaaJonoon(asiakas);
					Trace.out(Trace.Level.INFO, palvelupisteet[0].toString());
					break;
				}
				// Jos todennäköisyys on 2 tai alle, asiakas poistuu uuteen peliin, hakemaan lisää poletteja tai voittojen nostopisteelle
				if (todennakoisyys <= 2 && asiakas.getNykyinenPolettimaara() >= 10) {
					if (randomPalvelupiste == 1) {
						palvelupisteet[2].otaJonosta(asiakas);
						palvelupisteet[1].lisaaJonoon(asiakas);
						Trace.out(Trace.Level.INFO, palvelupisteet[1].toString());
					} else if (randomPalvelupiste == 2) {
						palvelupisteet[2].otaJonosta(asiakas);
						palvelupisteet[3].lisaaJonoon(asiakas);
						Trace.out(Trace.Level.INFO, palvelupisteet[3].toString());
					} else {
						palvelupisteet[2].otaJonosta(asiakas);
						palvelupisteet[4].lisaaJonoon(asiakas);
						Trace.out(Trace.Level.INFO, palvelupisteet[4].toString());
					}
				} else if (todennakoisyys >= 3 && asiakas.getNykyinenPolettimaara() >= 10) { // Asiakas voi jatkaa pelaamista vain, jos hänellä on vähintään 10 polettia
					palvelupisteet[2].jatkaPelaamista(asiakas);
				} else { // Asiakas hakee lisää poletteja tai menee voittojen nostopisteelle
					if (lisaaPolettejaTaiVoitot == 1) {
						palvelupisteet[2].otaJonosta(asiakas);
						palvelupisteet[0].lisaaJonoon(asiakas);
						Trace.out(Trace.Level.INFO, palvelupisteet[0].toString());
					} else {
						palvelupisteet[2].otaJonosta(asiakas);
						palvelupisteet[4].lisaaJonoon(asiakas);
						Trace.out(Trace.Level.INFO, palvelupisteet[4].toString());
					}
				}
		   	   	break;
		   	   	
			case DEP4: // Asiakas lähtee kasinon Krapsistä
				asiakas = palvelupisteet[3].getJononEnsimmainen();
				// Asiakkaalla pitää olla vähintään 10 polettia pelatakseen
				if (asiakas.getNykyinenPolettimaara() >= 10) {
					// Asiakas pelaa ja tarkistetaan voittiko vai hävisikö asiakas
					if (palvelupisteet[3].voittikoAsiakas(asiakas)) {
						// Lisätään lukuun 1
						todennakoisyys += 1;
					} else {
						// Vähennetään luvusta 1
						todennakoisyys -= 1;
					}
				} else { // Asiakas hakee lisää poletteja
					palvelupisteet[3].otaJonosta(asiakas);
					palvelupisteet[0].lisaaJonoon(asiakas);
					Trace.out(Trace.Level.INFO, palvelupisteet[0].toString());
					break;
				}
				// Jos todennäköisyys on 2 tai alle, asiakas poistuu uuteen peliin, hakemaan lisää poletteja tai voittojen nostopisteelle
				if (todennakoisyys <= 2 && asiakas.getNykyinenPolettimaara() >= 10) {
					if (randomPalvelupiste == 1) {
						palvelupisteet[3].otaJonosta(asiakas);
						palvelupisteet[1].lisaaJonoon(asiakas);
						Trace.out(Trace.Level.INFO, palvelupisteet[1].toString());
					} else if (randomPalvelupiste == 2) {
						palvelupisteet[3].otaJonosta(asiakas);
						palvelupisteet[2].lisaaJonoon(asiakas);
						Trace.out(Trace.Level.INFO, palvelupisteet[2].toString());
					} else {
						palvelupisteet[3].otaJonosta(asiakas);
						palvelupisteet[4].lisaaJonoon(asiakas);
						Trace.out(Trace.Level.INFO, palvelupisteet[4].toString());
					}
				} else if (todennakoisyys >= 3 && asiakas.getNykyinenPolettimaara() >= 10) { // Asiakas voi jatkaa pelaamista vain, jos hänellä on vähintään 10 polettia
					palvelupisteet[3].jatkaPelaamista(asiakas);
				} else { // Asiakas hakee lisää poletteja tai menee voittojen nostopisteelle
					if (lisaaPolettejaTaiVoitot == 1) {
						palvelupisteet[3].otaJonosta(asiakas);
						palvelupisteet[0].lisaaJonoon(asiakas);
						Trace.out(Trace.Level.INFO, palvelupisteet[0].toString());
					} else {
						palvelupisteet[3].otaJonosta(asiakas);
						palvelupisteet[4].lisaaJonoon(asiakas);
						Trace.out(Trace.Level.INFO, palvelupisteet[4].toString());
					}
				}
		   	   	break;
		   	   	
			case DEP5: // Asiakas lähtee kasinon voittojen nostopisteeltä
				asiakas = palvelupisteet[4].getJononEnsimmainen(); 
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
		//PalvelupisteDao palvDao = new PalvelupisteDao();
		//palvDao.poistaTiski(palvelupisteet[0].getNimi());
		
		Trace.out(Trace.Level.INFO, "\n**SIMULOINTI PÄÄTTYY**");
		if (palvelupisteet[4].getTalonVoittoEuroina() > 0) {
			Trace.out(Trace.Level.INFO, "\nKasino teki voittoa ajassa " + String.format("%.02f", Kello.getInstance().getAika()) + " yhteensä " + palvelupisteet[4].getTalonVoittoEuroina() + " euroa.");
		} else {
			Trace.out(Trace.Level.INFO, "\nKasino teki liiketappiota ajassa " + String.format("%.02f", Kello.getInstance().getAika()) + " yhteensä " + (palvelupisteet[4].getTalonVoittoEuroina() * -1) + " euroa.");
		}
		Trace.out(Trace.Level.INFO, "\nPalvelutiski palveli asiakkaita yhteensä " + palvelupisteet[0].getKaynteja().size() + " kertaa.");
		Trace.out(Trace.Level.INFO, "Rulettia pelattiin yhteensä " + palvelupisteet[1].getKaynteja().size() + " kertaa.");
		Trace.out(Trace.Level.INFO, "Blackjackiä pelattiin yhteensä " + palvelupisteet[2].getKaynteja().size() + " kertaa.");
		Trace.out(Trace.Level.INFO, "Krapsiä pelattiin yhteensä " + palvelupisteet[3].getKaynteja().size() + " kertaa.");
		Trace.out(Trace.Level.INFO, "Asiakkaat nosti voittoja voittojen nostopisteellä yhteensä " + palvelupisteet[4].getKaynteja().size() + " kertaa.");;

		Trace.out(Trace.Level.INFO, "\nKasinolle saapui yhteensä " + asiakasLkm + " asiakasta.");
        
        Trace.out(Trace.Level.INFO, "Palvelutiskin suoritusteho oli " + (palvelupisteet[0].getSuoritusteho(getSimulointiaika()) * 100) + " prosenttia.");
        Trace.out(Trace.Level.INFO, "Palvelutiskin käyttöaste oli " + (palvelupisteet[0].getKayttoaste(getSimulointiaika()) * 100) + " prosenttia.");
        Trace.out(Trace.Level.INFO, "Palvelutiskin keskimääräinen palveluaika oli " + String.format("%.02f", palvelupisteet[0].getKeskimaarainenPalveluaika()) + ".");
        Trace.out(Trace.Level.INFO, "Palvelutiskin kokonaisoleskeluaika oli " + String.format("%.02f", palvelupisteet[0].getKokonaisoleskeluaika()) + ".");
        Trace.out(Trace.Level.INFO, "Palvelutiskin keskimääräinen läpimenoaika oli " + String.format("%.02f", palvelupisteet[0].getKeskimaarainenLapimenoaika()) + ".");
        Trace.out(Trace.Level.INFO, "Palvelutiskin keskimääräinen jononpituus oli " + String.format("%.02f", palvelupisteet[0].getKeskimaarainenJononpituus(getSimulointiaika())) + " asiakasta.");
	}
	
}