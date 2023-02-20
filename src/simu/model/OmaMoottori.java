package simu.model;

import controller.IKontrolleri;
import eduni.distributions.Negexp;
import eduni.distributions.Normal;
import simu.framework.Kello;
import simu.framework.Moottori;
import simu.framework.Saapumisprosessi;
import simu.framework.Tapahtuma;


public class OmaMoottori extends Moottori {
	
	private Saapumisprosessi saapumisprosessi;
	
	public OmaMoottori(IKontrolleri kontrolleri) { // UUSI

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
	// TO-DO: Asiakkaalle pitää antaa voitot ja pitää laskea paljon kasino teki voittoa/tappiota. - Valdo
	// TO-DO: Loppuraportti - Tapio
	// TO-DO: Tulosteet kuntoon - Tapio
	// TO-DO: Opettele Trace - Tapio
	@Override
	protected void suoritaTapahtuma(Tapahtuma t){  // B-vaiheen tapahtumat

		Asiakas asiakas;
		// Arvotaan luku välillä 1-4
		int todennakoisyys = (int) Math.floor(Math.random() * (4 - 1 + 1) + 1);
		// Arvotaan luku välillä 1-3
		int randomPalvelupiste = (int) Math.floor(Math.random() * (3 - 1 + 1) + 1);
		
		int kotiinko = (int) Math.floor(Math.random() * (2 - 1 + 1) + 1);
		
		switch (t.getTyyppi()) {
			
			case ARR1: // Asiakas saapuu kasinoon
				palvelupisteet[0].lisaaJonoon(new Asiakas());	
		    	kontrolleri.visualisoiAsiakas(); // UUSI
		    	palvelupisteet[0].tulostaJononAsiakkaat();
		    	saapumisprosessi.generoiSeuraava();
				break;
				
			case DEP1: // Asiakas lähtee kasinon palvelutiskiltä
				asiakas = palvelupisteet[0].getJono().getFirst();
				// Asiakkaalle annetaan poletteja palvelutiskillä
				asiakas.annaPoletteja();
				if (randomPalvelupiste == 1) {
					palvelupisteet[0].otaJonosta(asiakas);
					palvelupisteet[1].lisaaJonoon(asiakas);
					palvelupisteet[1].tulostaJononAsiakkaat();
				} else if (randomPalvelupiste == 2) {
					palvelupisteet[0].otaJonosta(asiakas);
					palvelupisteet[2].lisaaJonoon(asiakas);
					palvelupisteet[2].tulostaJononAsiakkaat();
				} else {
					palvelupisteet[0].otaJonosta(asiakas);
					palvelupisteet[3].lisaaJonoon(asiakas);
					palvelupisteet[3].tulostaJononAsiakkaat();
				}
				break;
				
			case DEP2: // Asiakas lähtee kasinon ruletista
				asiakas = palvelupisteet[1].getJono().getFirst();
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
					palvelupisteet[0].tulostaJononAsiakkaat();
					break;
				}
				// Jos todennäköisyys on 2 tai alle, asiakas poistuu uuteen peliin tai voittojen nostopisteelle
				if (todennakoisyys <= 2 && asiakas.getNykyinenPolettimaara() >= 10) {
					if (randomPalvelupiste == 1) {
						palvelupisteet[1].otaJonosta(asiakas);
						palvelupisteet[2].lisaaJonoon(asiakas);
						palvelupisteet[2].tulostaJononAsiakkaat();
					} else if (randomPalvelupiste == 2) {
						palvelupisteet[1].otaJonosta(asiakas);
						palvelupisteet[3].lisaaJonoon(asiakas);
						palvelupisteet[3].tulostaJononAsiakkaat();
					} else {
						palvelupisteet[1].otaJonosta(asiakas);
						palvelupisteet[4].lisaaJonoon(asiakas);
						palvelupisteet[4].tulostaJononAsiakkaat();
					}
				} else if (todennakoisyys >= 3 && asiakas.getNykyinenPolettimaara() >= 10) { // Asiakas voi jatkaa pelaamista vain, jos hänellä on vähintään 10 polettia
					palvelupisteet[1].jatkaPelaamista(asiakas);
				} else { // Asiakas hakee lisää poletteja tai menee voittojen nostopisteelle
					if (kotiinko == 1) {
						palvelupisteet[1].otaJonosta(asiakas);
						palvelupisteet[0].lisaaJonoon(asiakas);
						palvelupisteet[0].tulostaJononAsiakkaat();
					} else {
						palvelupisteet[1].otaJonosta(asiakas);
						palvelupisteet[4].lisaaJonoon(asiakas);
						palvelupisteet[4].tulostaJononAsiakkaat();
					}
				}
		   	   	break;
		   	   
			case DEP3: // Asiakas lähtee kasinon Blackjackistä
				asiakas = palvelupisteet[2].getJono().getFirst();
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
					palvelupisteet[0].tulostaJononAsiakkaat();
					break;
				}
				// Jos todennäköisyys on 2 tai alle, asiakas poistuu uuteen peliin, hakemaan lisää poletteja tai voittojen nostopisteelle
				if (todennakoisyys <= 2 && asiakas.getNykyinenPolettimaara() >= 10) {
					if (randomPalvelupiste == 1) {
						palvelupisteet[2].otaJonosta(asiakas);
						palvelupisteet[1].lisaaJonoon(asiakas);
						palvelupisteet[1].tulostaJononAsiakkaat();
					} else if (randomPalvelupiste == 2) {
						palvelupisteet[2].otaJonosta(asiakas);
						palvelupisteet[3].lisaaJonoon(asiakas);
						palvelupisteet[3].tulostaJononAsiakkaat();
					} else {
						palvelupisteet[2].otaJonosta(asiakas);
						palvelupisteet[4].lisaaJonoon(asiakas);
						palvelupisteet[4].tulostaJononAsiakkaat();
					}
				} else if (todennakoisyys >= 3 && asiakas.getNykyinenPolettimaara() >= 10) { // Asiakas voi jatkaa pelaamista vain, jos hänellä on vähintään 10 polettia
					palvelupisteet[2].jatkaPelaamista(asiakas);
				} else { // Asiakas hakee lisää poletteja tai menee voittojen nostopisteelle
					if (kotiinko == 1) {
						palvelupisteet[2].otaJonosta(asiakas);
						palvelupisteet[0].lisaaJonoon(asiakas);
						palvelupisteet[0].tulostaJononAsiakkaat();
					} else {
						palvelupisteet[2].otaJonosta(asiakas);
						palvelupisteet[4].lisaaJonoon(asiakas);
						palvelupisteet[4].tulostaJononAsiakkaat();
					}
				}
		   	   	break;
		   	   	
			case DEP4: // Asiakas lähtee kasinon Krapsistä
				asiakas = palvelupisteet[3].getJono().getFirst();
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
					palvelupisteet[0].tulostaJononAsiakkaat();
					break;
				}
				// Jos todennäköisyys on 2 tai alle, asiakas poistuu uuteen peliin, hakemaan lisää poletteja tai voittojen nostopisteelle
				if (todennakoisyys <= 2 && asiakas.getNykyinenPolettimaara() >= 10) {
					if (randomPalvelupiste == 1) {
						palvelupisteet[3].otaJonosta(asiakas);
						palvelupisteet[1].lisaaJonoon(asiakas);
						palvelupisteet[1].tulostaJononAsiakkaat();
					} else if (randomPalvelupiste == 2) {
						palvelupisteet[3].otaJonosta(asiakas);
						palvelupisteet[2].lisaaJonoon(asiakas);
						palvelupisteet[2].tulostaJononAsiakkaat();
					} else {
						palvelupisteet[3].otaJonosta(asiakas);
						palvelupisteet[4].lisaaJonoon(asiakas);
						palvelupisteet[4].tulostaJononAsiakkaat();
					}
				} else if (todennakoisyys >= 3 && asiakas.getNykyinenPolettimaara() >= 10) { // Asiakas voi jatkaa pelaamista vain, jos hänellä on vähintään 10 polettia
					palvelupisteet[3].jatkaPelaamista(asiakas);
				} else { // Asiakas hakee lisää poletteja tai menee voittojen nostopisteelle
					if (kotiinko == 1) {
						palvelupisteet[3].otaJonosta(asiakas);
						palvelupisteet[0].lisaaJonoon(asiakas);
						palvelupisteet[0].tulostaJononAsiakkaat();
					} else {
						palvelupisteet[3].otaJonosta(asiakas);
						palvelupisteet[4].lisaaJonoon(asiakas);
						palvelupisteet[4].tulostaJononAsiakkaat();
					}
				}
		   	   	break;
		   	   	
			case DEP5: // Asiakas lähtee kasinon voittojen nostopisteeltä
				asiakas = palvelupisteet[4].getJono().getFirst(); 
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
		
		// UUTTA graafista
		kontrolleri.naytaLoppuaika(Kello.getInstance().getAika());
		
	}

	
}