package simu.model;

import controller.IKontrolleri;
import eduni.distributions.Negexp;
import eduni.distributions.Normal;
import simu.framework.Kello;
import simu.framework.Moottori;
import simu.framework.Saapumisprosessi;
import simu.framework.Tapahtuma;
import view.SimulaattorinPaaikkunaKontrolleri;


public class OmaMoottori extends Moottori {
	
	private Saapumisprosessi saapumisprosessi;
	private static int asiakasLkm = 0;
	
	//public OmaMoottori(IKontrolleri kontrolleri) { // UUSI
	public OmaMoottori(SimulaattorinPaaikkunaKontrolleri kontrolleri) {

		super(kontrolleri); //UUSI
		
		// TO-DO: muutetaan jakaumat oikeiksi, kun ollaan päätetty ne. - Valdo
		saapumisprosessi = new Saapumisprosessi(new Negexp(15,5), tapahtumalista, TapahtumanTyyppi.ARR1);
	
		// MUISTUTUS: Jakaumat on pakko olla samat palvelupisteille, ettei jonot kasaannu!!!!!
		palvelupisteet = new Palvelupiste[5];
		palvelupisteet[0] = new Palvelupiste(new Normal(5,3), tapahtumalista, TapahtumanTyyppi.DEP1, "Palvelutiski");
		palvelupisteet[1] = new Palvelupiste(new Normal(6,3), tapahtumalista, TapahtumanTyyppi.DEP2, "Ruletti");
		palvelupisteet[2] = new Palvelupiste(new Normal(7,3), tapahtumalista, TapahtumanTyyppi.DEP3, "Blackjack");
		palvelupisteet[3] = new Palvelupiste(new Normal(4,3), tapahtumalista, TapahtumanTyyppi.DEP4, "Kraps");
		palvelupisteet[4] = new Palvelupiste(new Normal(5,3), tapahtumalista, TapahtumanTyyppi.DEP5, "Voittojen nostopiste");
	}

	@Override
	protected void alustukset() {
		saapumisprosessi.generoiSeuraava(); // Ensimmäinen saapuminen järjestelmään
	}
	
	// Simulaation logiikka miten asiakkaat liikkuvat palvelupisteillä
	// TO-DO: Loppuraportti - Tapio
	// TO-DO: Asiakaskohtainen raportti - Valdo
	// TO-DO: Tulosteet kuntoon - Tapio
	// TO-DO: Opettele Trace - Tapio
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
				palvelupisteet[0].lisaaJonoon(new Asiakas());
				asiakasLkm++;
		    	//kontrolleri.visualisoiAsiakas(); // UUSI
		    	System.out.println(palvelupisteet[0]);
		    	saapumisprosessi.generoiSeuraava();
				break;
				
			case DEP1: // Asiakas lähtee kasinon palvelutiskiltä
				asiakas = palvelupisteet[0].getJononEnsimmainen();
				// Asiakkaalle annetaan poletteja palvelutiskillä
				asiakas.annaPolettejaPalvelutiskilla();
				if (randomPalvelupiste == 1) {
					palvelupisteet[0].otaJonosta(asiakas);
					palvelupisteet[1].lisaaJonoon(asiakas);
					System.out.println(palvelupisteet[1]);
				} else if (randomPalvelupiste == 2) {
					palvelupisteet[0].otaJonosta(asiakas);
					palvelupisteet[2].lisaaJonoon(asiakas);
					System.out.println(palvelupisteet[2]);
				} else {
					palvelupisteet[0].otaJonosta(asiakas);
					palvelupisteet[3].lisaaJonoon(asiakas);
					System.out.println(palvelupisteet[3]);
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
					System.out.println(palvelupisteet[0]);
					break;
				}
				// Jos todennäköisyys on 2 tai alle, asiakas poistuu uuteen peliin tai voittojen nostopisteelle
				if (todennakoisyys <= 2 && asiakas.getNykyinenPolettimaara() >= 10) {
					if (randomPalvelupiste == 1) {
						palvelupisteet[1].otaJonosta(asiakas);
						palvelupisteet[2].lisaaJonoon(asiakas);
						System.out.println(palvelupisteet[2]);
					} else if (randomPalvelupiste == 2) {
						palvelupisteet[1].otaJonosta(asiakas);
						palvelupisteet[3].lisaaJonoon(asiakas);
						System.out.println(palvelupisteet[3]);
					} else {
						palvelupisteet[1].otaJonosta(asiakas);
						palvelupisteet[4].lisaaJonoon(asiakas);
						System.out.println(palvelupisteet[4]);
					}
				} else if (todennakoisyys >= 3 && asiakas.getNykyinenPolettimaara() >= 10) { // Asiakas voi jatkaa pelaamista vain, jos hänellä on vähintään 10 polettia
					palvelupisteet[1].jatkaPelaamista(asiakas);
				} else { // Asiakas hakee lisää poletteja tai menee voittojen nostopisteelle
					if (lisaaPolettejaTaiVoitot == 1) {
						palvelupisteet[1].otaJonosta(asiakas);
						palvelupisteet[0].lisaaJonoon(asiakas);
						System.out.println(palvelupisteet[0]);
					} else {
						palvelupisteet[1].otaJonosta(asiakas);
						palvelupisteet[4].lisaaJonoon(asiakas);
						System.out.println(palvelupisteet[4]);
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
					System.out.println(palvelupisteet[0]);
					break;
				}
				// Jos todennäköisyys on 2 tai alle, asiakas poistuu uuteen peliin, hakemaan lisää poletteja tai voittojen nostopisteelle
				if (todennakoisyys <= 2 && asiakas.getNykyinenPolettimaara() >= 10) {
					if (randomPalvelupiste == 1) {
						palvelupisteet[2].otaJonosta(asiakas);
						palvelupisteet[1].lisaaJonoon(asiakas);
						System.out.println(palvelupisteet[1]);
					} else if (randomPalvelupiste == 2) {
						palvelupisteet[2].otaJonosta(asiakas);
						palvelupisteet[3].lisaaJonoon(asiakas);
						System.out.println(palvelupisteet[3]);
					} else {
						palvelupisteet[2].otaJonosta(asiakas);
						palvelupisteet[4].lisaaJonoon(asiakas);
						System.out.println(palvelupisteet[4]);
					}
				} else if (todennakoisyys >= 3 && asiakas.getNykyinenPolettimaara() >= 10) { // Asiakas voi jatkaa pelaamista vain, jos hänellä on vähintään 10 polettia
					palvelupisteet[2].jatkaPelaamista(asiakas);
				} else { // Asiakas hakee lisää poletteja tai menee voittojen nostopisteelle
					if (lisaaPolettejaTaiVoitot == 1) {
						palvelupisteet[2].otaJonosta(asiakas);
						palvelupisteet[0].lisaaJonoon(asiakas);
						System.out.println(palvelupisteet[0]);
					} else {
						palvelupisteet[2].otaJonosta(asiakas);
						palvelupisteet[4].lisaaJonoon(asiakas);
						System.out.println(palvelupisteet[4]);
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
					System.out.println(palvelupisteet[0]);
					break;
				}
				// Jos todennäköisyys on 2 tai alle, asiakas poistuu uuteen peliin, hakemaan lisää poletteja tai voittojen nostopisteelle
				if (todennakoisyys <= 2 && asiakas.getNykyinenPolettimaara() >= 10) {
					if (randomPalvelupiste == 1) {
						palvelupisteet[3].otaJonosta(asiakas);
						palvelupisteet[1].lisaaJonoon(asiakas);
						System.out.println(palvelupisteet[1]);
					} else if (randomPalvelupiste == 2) {
						palvelupisteet[3].otaJonosta(asiakas);
						palvelupisteet[2].lisaaJonoon(asiakas);
						System.out.println(palvelupisteet[2]);
					} else {
						palvelupisteet[3].otaJonosta(asiakas);
						palvelupisteet[4].lisaaJonoon(asiakas);
						System.out.println(palvelupisteet[4]);
					}
				} else if (todennakoisyys >= 3 && asiakas.getNykyinenPolettimaara() >= 10) { // Asiakas voi jatkaa pelaamista vain, jos hänellä on vähintään 10 polettia
					palvelupisteet[3].jatkaPelaamista(asiakas);
				} else { // Asiakas hakee lisää poletteja tai menee voittojen nostopisteelle
					if (lisaaPolettejaTaiVoitot == 1) {
						palvelupisteet[3].otaJonosta(asiakas);
						palvelupisteet[0].lisaaJonoon(asiakas);
						System.out.println(palvelupisteet[0]);
					} else {
						palvelupisteet[3].otaJonosta(asiakas);
						palvelupisteet[4].lisaaJonoon(asiakas);
						System.out.println(palvelupisteet[4]);
					}
				}
		   	   	break;
		   	   	
			case DEP5: // Asiakas lähtee kasinon voittojen nostopisteeltä
				asiakas = palvelupisteet[4].getJononEnsimmainen(); 
				int kaynnit[] = new int[4];
		    	palvelupisteet[4].otaJonosta(asiakas);
		    	System.out.println("Asiakas " + asiakas.getId() + " poistuu kasinolta.");
				asiakas.setPoistumisaika(Kello.getInstance().getAika());
				for(int i = 0; i < (palvelupisteet.length - 1); i++) {
					for(int j = 0; j < palvelupisteet[i].getKaynteja().size(); j++) {
						if(palvelupisteet[i].getKaynteja().get(j).getId() == asiakas.getId()) {
							kaynnit[i]++;
						}
					}
				}
	        	asiakas.raportti();
	        	System.out.println("Asiakas " + asiakas.getId() + " kävi palvelutiskillä " + kaynnit[0] + " kertaa.");
	        	System.out.println("Asiakas " + asiakas.getId() + " kävi ruletissa " + kaynnit[1] + " kertaa.");
	        	System.out.println("Asiakas " + asiakas.getId() + " kävi Blackjackissä " + kaynnit[2] + " kertaa.");
	        	System.out.println("Asiakas " + asiakas.getId() + " kävi Krapsissä " + kaynnit[3] + " kertaa.");
	        	break;
		}	
	}

	@Override
	protected void tulokset() {
		// VANHAA tekstipohjaista
		// System.out.println("Simulointi päättyi kello " + Kello.getInstance().getAika());
		// System.out.println("Tulokset ... puuttuvat vielä");
		// UUTTA graafisa
		kontrolleri.naytaLoppuaika(Kello.getInstance().getAika());
		System.out.println("\n**SIMULOINTI PÄÄTTYY**");
		if (palvelupisteet[4].getTalonVoittoEuroina() > 0) {
			System.out.println("\nKasino teki voittoa ajassa " + String.format("%.02f", Kello.getInstance().getAika()) + " yhteensä " + palvelupisteet[4].getTalonVoittoEuroina() + " euroa.");
		} else {
			System.out.println("\nKasino teki liiketappiota ajassa " + String.format("%.02f", Kello.getInstance().getAika()) + " yhteensä " + (palvelupisteet[4].getTalonVoittoEuroina() * -1) + " euroa.");
		}
		System.out.println("\nPalvelutiski palveli asiakkaita yhteensä " + palvelupisteet[0].getKaynteja().size() + " kertaa." );
        System.out.println("Rulettia pelattiin yhteensä " + palvelupisteet[1].getKaynteja().size() + " kertaa." );
        System.out.println("Blackjackiä pelattiin yhteensä " + palvelupisteet[2].getKaynteja().size() + " kertaa." );
        System.out.println("Krapsiä pelattiin yhteensä " + palvelupisteet[3].getKaynteja().size() + " kertaa." );
        System.out.println("Asiakkaat nosti voittoja voittojen nostopisteellä yhteensä " + palvelupisteet[4].getKaynteja().size() + " kertaa." );

        System.out.println("\nKasinolle saapui yhteensä " + asiakasLkm + " asiakasta.");
        
        System.out.println("Palvelutiskin suoritusteho oli " + (palvelupisteet[0].getSuoritusteho(getSimulointiaika()) * 100) + " prosenttia.");
        System.out.println("Palvelutiskin käyttöaste oli " + (palvelupisteet[0].getKayttoaste(getSimulointiaika()) * 100) + " prosenttia.");
        System.out.println("Palvelutiskin keskimääräinen palveluaika oli " + String.format("%.02f", palvelupisteet[0].getKeskimaarainenPalveluaika()) + ".");
        System.out.println("Palvelutiskin kokonaisoleskeluaika oli " + String.format("%.02f", palvelupisteet[0].getKokonaisoleskeluaika()) + ".");
        System.out.println("Palvelutiskin keskimääräinen läpimenoaika oli " + String.format("%.02f", palvelupisteet[0].getKeskimaarainenLapimenoaika()) + ".");
        System.out.println("Palvelutiskin keskimääräinen jononpituus oli " + String.format("%.02f", palvelupisteet[0].getKeskimaarainenJononpituus(getSimulointiaika())) + " asiakasta.");
	}
	
}