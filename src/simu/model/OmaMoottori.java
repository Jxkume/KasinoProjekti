package simu.model;

import java.util.Random;

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

		super(kontrolleri); //UUSI mo : Jhon
		
		// TO-DO: muutetaan jakaumat oikeiksi, kun ollaan päätetty ne. - Valdo
		saapumisprosessi = new Saapumisprosessi(new Negexp(15,5), tapahtumalista, TapahtumanTyyppi.ARR1);
	
		palvelupisteet = new Palvelupiste[5];
		palvelupisteet[0] = new Palvelupiste(new Normal(10,6), tapahtumalista, TapahtumanTyyppi.DEP1, "Palvelutiski"); 	// Lähtö palvelutiskiltä
		palvelupisteet[1] = new Palvelupiste(new Normal(10,10), tapahtumalista, TapahtumanTyyppi.DEP2, "Ruletti"); // Lähtö ruletista
		palvelupisteet[2] = new Palvelupiste(new Normal(5,3), tapahtumalista, TapahtumanTyyppi.DEP3, "Blackjack"); 	// Lähtö Blackjackistä
		palvelupisteet[3] = new Palvelupiste(new Normal(5,3), tapahtumalista, TapahtumanTyyppi.DEP4, "Kraps");	// Lähtö Krapsistä
		palvelupisteet[4] = new Palvelupiste(new Normal(5,3), tapahtumalista, TapahtumanTyyppi.DEP5, "Voittojen nostopiste");	// Lähtö voittojen nostopisteeltä
	}

	@Override
	protected void alustukset() {
		saapumisprosessi.generoiSeuraava(); // Ensimmäinen saapuminen järjestelmään
	}
	
	// Simulaation logiikka miten asiakkaat liikkuvat palvelupisteillä
	// TO-DO: Luodaanko asiakas oikeassa paikassa? Pitää tarkistaa - Valdo
	// TO-DO: Miksi kaikki asiakkaat kasaantuvat jonottamaan rulettia? Pitää selvittää - Valdo
	// TO-DO: Asiakas voi hakea lisää poletteja palvelutiskiltä - Valdo
	// TO-DO: Jos asiakkaalla on 0 polettia, hänen pitää lähteä pois tai hakea lisää poletteja - Valdo
	// TO-DO: Asiakkaalle pitää antaa voitot ja pitää laskea paljon kasino teki voittoa/tappiota. - Valdo
	@Override
	protected void suoritaTapahtuma(Tapahtuma t){  // B-vaiheen tapahtumat

		Asiakas asiakas;
		int todennakoisyys;
		int randomPeli;
		
		switch (t.getTyyppi()) {
			
			case ARR1: // Asiakkaan saapuessa kasinoon hänet ohjataan kasinon palvelutiskin jonoon
				asiakas = new Asiakas(); // Luodaan uusi asiakas saapumisprosessin aikana
				palvelupisteet[0].lisaaJonoon(asiakas);	
		    	kontrolleri.visualisoiAsiakas(); // UUSI
		    	palvelupisteet[0].tulostaJononAsiakkaat();
		    	saapumisprosessi.generoiSeuraava();   
				break;
			case DEP1: // Lähtö palvelutiskiltä
				asiakas = palvelupisteet[0].getJono().getFirst();
				asiakas.annaPoletteja(); // Asiakkaalle annetaan poletteja palvelutiskillä ennen lähtöä
				palvelupisteet[0].otaJonosta(asiakas);
				// Asiakas arvotaan yhteen peliin.
				randomPeli = (int) Math.floor(Math.random() * (3 - 1 + 1) + 1);
				if (randomPeli == 1) {
					palvelupisteet[1].lisaaJonoon(asiakas);
					palvelupisteet[1].tulostaJononAsiakkaat();
				} else if (randomPeli == 2) {
					palvelupisteet[2].lisaaJonoon(asiakas);
					palvelupisteet[2].tulostaJononAsiakkaat();
				} else {
					palvelupisteet[3].lisaaJonoon(asiakas);
					palvelupisteet[3].tulostaJononAsiakkaat();
				}
				break;
			case DEP2: // Lähtö ruletista
				asiakas = palvelupisteet[1].getJono().getFirst();
				if (palvelupisteet[1].pelaa(asiakas)) {
					// Arvotaan luku väliltä 1-4 ja lisätään siihen 1
					todennakoisyys = (int) Math.floor(Math.random() * (4 - 1 + 1) + 1) + 1;
				} else {
					// Arvotaan luku väliltä 1-4 ja vähennetään siitä 1
					todennakoisyys = (int) Math.floor(Math.random() * (4 - 1 + 1) + 1) - 1;
				}
				// Jos todennäköisyys on 2 tai alle, asiakas poistuu pelistä
				if (todennakoisyys <= 2) {
					// Tarkistetaan siirtyykö asiakas voittojen nostopisteelle
					if (palvelupisteet[1].siirtyykoVoittojenNostopisteelle(asiakas)) {
						palvelupisteet[1].otaJonosta(asiakas);
						palvelupisteet[4].lisaaJonoon(asiakas);
						palvelupisteet[4].tulostaJononAsiakkaat();
					} else {
						palvelupisteet[1].otaJonosta(asiakas);
						// Asiakas arvotaan uuteen peliin
						randomPeli = (int) Math.floor(Math.random() * (2 - 1 + 1) + 1);
						if (randomPeli == 1) {
							palvelupisteet[2].lisaaJonoon(asiakas);
							palvelupisteet[2].tulostaJononAsiakkaat();
						} else {
							palvelupisteet[3].lisaaJonoon(asiakas);
							palvelupisteet[3].tulostaJononAsiakkaat();
						}
					}
				} else {
					palvelupisteet[1].jatkaPelaamista(asiakas);
				}
				break;
			case DEP3: // Lähtö Blackjackistä
				asiakas = palvelupisteet[2].getJono().getFirst();
				if (palvelupisteet[2].pelaa(asiakas)) {
					// Arvotaan luku väliltä 1-4 ja lisätään siihen 1
					todennakoisyys = (int) Math.floor(Math.random() * (4 - 1 + 1) + 1) + 1;
				} else {
					// Arvotaan luku väliltä 1-4 ja vähennetään siitä 1
					todennakoisyys = (int) Math.floor(Math.random() * (4 - 1 + 1) + 1) - 1;
				}
				// Jos todennäköisyys on 2 tai alle, asiakas poistuu pelistä kokonaan
				if (todennakoisyys <= 2) {
					// Tarkistetaan siirtyykö asiakas voittojen nostopisteelle
					if (palvelupisteet[2].siirtyykoVoittojenNostopisteelle(asiakas)) {
						palvelupisteet[2].otaJonosta(asiakas);
						palvelupisteet[4].lisaaJonoon(asiakas);
						palvelupisteet[4].tulostaJononAsiakkaat();
					} else {
						palvelupisteet[2].otaJonosta(asiakas);
						// Asiakas arvotaan uuteen peliin
						randomPeli = (int) Math.floor(Math.random() * (2 - 1 + 1) + 1);
						if (randomPeli == 1) {
							palvelupisteet[1].lisaaJonoon(asiakas);
							palvelupisteet[1].tulostaJononAsiakkaat();
						} else {
							palvelupisteet[3].lisaaJonoon(asiakas);
							palvelupisteet[3].tulostaJononAsiakkaat();
						}
					}
				} else {
					palvelupisteet[2].jatkaPelaamista(asiakas);
				}
		   	   	break;  
			case DEP4: // Lähtö Krapsistä
				asiakas = palvelupisteet[3].getJono().getFirst();
				if (palvelupisteet[3].pelaa(asiakas)) {
					// Arvotaan luku väliltä 1-4 ja lisätään siihen 1
					todennakoisyys = (int) Math.floor(Math.random() * (4 - 1 + 1) + 1) + 1;
				} else {
					// Arvotaan luku väliltä 1-4 ja vähennetään siitä 1
					todennakoisyys = (int) Math.floor(Math.random() * (4 - 1 + 1) + 1) - 1;
				}
				// Jos todennäköisyys on 2 tai alle, asiakas poistuu pelistä kokonaan
				if (todennakoisyys <= 2) {
					// Tarkistetaan siirtyykö asiakas voittojen nostopisteelle
					if (palvelupisteet[3].siirtyykoVoittojenNostopisteelle(asiakas)) {
						palvelupisteet[3].otaJonosta(asiakas);
						palvelupisteet[4].lisaaJonoon(asiakas);
						palvelupisteet[4].tulostaJononAsiakkaat();
					} else {
						palvelupisteet[3].otaJonosta(asiakas);
						// Asiakas arvotaan uuteen peliin
						randomPeli = (int) Math.floor(Math.random() * (2 - 1 + 1) + 1);
						if (randomPeli == 1) {
							palvelupisteet[1].lisaaJonoon(asiakas);
							palvelupisteet[1].tulostaJononAsiakkaat();
						} else {
							palvelupisteet[2].lisaaJonoon(asiakas);
							palvelupisteet[2].tulostaJononAsiakkaat();
						}
					}
				} else {
					palvelupisteet[3].jatkaPelaamista(asiakas);
				}
		   	   	break;  
			case DEP5: // Lähtö voittojen nostopisteeltä
		    	asiakas = palvelupisteet[4].getJono().getFirst();
		    	palvelupisteet[4].otaJonosta(asiakas);
		    	System.out.println("Asiakas " + asiakas.getId() + " poistuu kasinolta.");
				asiakas.setPoistumisaika(Kello.getInstance().getAika());
	        	asiakas.raportti();
	        	break;
		}	
	}


	// Simulaation logiikka miten asiakkaat liikkuvat palvelupisteillä
	// TO-DO: Luodaanko asiakas oikeassa paikassa? Pitää tarkistaa - Valdo
	// TO-DO: Asiakas voi hakea lisää poletteja palvelutiskiltä - Valdo
	// TO-DO: Jos asiakkaalla on 0 polettia, hänen pitää lähteä pois tai hakea lisää poletteja - Valdo
	// TO-DO: Asiakkaalle pitää antaa voitot ja pitää laskea paljon kasino teki voittoa/tappiota. - Valdo
	/*@Override
	protected void suoritaTapahtuma(Tapahtuma t) {  // B-vaiheen tapahtumat
		
		Asiakas asiakas;
		int todennakoisyys;
		
		if (t.getTyyppi().equals(TapahtumanTyyppi.ARR1)) { // Asiakkaan saapuessa kasinoon hänet ohjataan kasinon palvelutiskin jonoon.
			asiakas = new Asiakas(); // Luodaan uusi asiakas saapumisprosessin aikana
			palvelupisteet[0].lisaaJonoon(asiakas);	
		    kontrolleri.visualisoiAsiakas(); // UUSI
		    palvelupisteet[0].tulostaJononAsiakkaat();
		    saapumisprosessi.generoiSeuraava();    
		} else if (t.getTyyppi().equals(TapahtumanTyyppi.DEP1)) { // Lähtö palvelutiskiltä
			asiakas = palvelupisteet[0].getJono().getFirst();
			asiakas.annaPoletteja(); // Asiakkaalle annetaan poletteja palvelutiskillä ennen lähtöä
			palvelupisteet[0].otaJonosta();
			// Asiakas arvotaan yhteen peliin.
			int randomPeli = (int) Math.floor(Math.random() * (3 - 1 + 1) + 1);
			if (randomPeli == 1) {
				palvelupisteet[1].lisaaJonoon(asiakas);
				palvelupisteet[1].tulostaJononAsiakkaat();
			} else if (randomPeli == 2) {
				palvelupisteet[2].lisaaJonoon(asiakas);
				palvelupisteet[2].tulostaJononAsiakkaat();
			} else {
				palvelupisteet[3].lisaaJonoon(asiakas);
				palvelupisteet[3].tulostaJononAsiakkaat();
			}
		} else if (t.getTyyppi().equals(TapahtumanTyyppi.DEP2)) { // Lähtö ruletista 
			asiakas = palvelupisteet[1].getJono().getFirst();		
			if (palvelupisteet[1].pelaa(asiakas)) {
				// Arvotaan luku väliltä 1-4 ja lisätään siihen 1
				todennakoisyys = (int) Math.floor(Math.random() * (4 - 1 + 1) + 1) + 1;
			} else {
				// Arvotaan luku väliltä 1-4 ja vähennetään siitä 1
				todennakoisyys = (int) Math.floor(Math.random() * (4 - 1 + 1) + 1) - 1;
			}
			
			// Jos todennäköisyys on 2 tai alle, asiakas poistuu pelistä
			if (todennakoisyys <= 2) {
				// Tarkistetaan siirtyykö asiakas voittojen nostopisteelle
				if (palvelupisteet[1].siirtyykoVoittojenNostopisteelle(asiakas)) {
					palvelupisteet[1].otaJonosta();
					palvelupisteet[4].lisaaJonoon(asiakas);
				} else {
					palvelupisteet[1].otaJonosta();
					// Asiakas arvotaan uuteen peliin
					int randomPeli = (int) Math.floor(Math.random() * (2 - 1 + 1) + 1);
					if (randomPeli == 1) {
						palvelupisteet[2].lisaaJonoon(asiakas);
						palvelupisteet[2].tulostaJononAsiakkaat();
					} else {
						palvelupisteet[3].lisaaJonoon(asiakas);
						palvelupisteet[3].tulostaJononAsiakkaat();
					}
				}
			} else {
				palvelupisteet[1].jatkaPelaamista(asiakas);
			}
		} else if (t.getTyyppi().equals(TapahtumanTyyppi.DEP3)) { // Lähtö Blackjackistä
			asiakas = palvelupisteet[2].getJono().getFirst();		
			if (palvelupisteet[2].pelaa(asiakas)) {
				// Arvotaan luku väliltä 1-4 ja lisätään siihen 1
				todennakoisyys = (int) Math.floor(Math.random() * (4 - 1 + 1) + 1) + 1;
			} else {
				// Arvotaan luku väliltä 1-4 ja vähennetään siitä 1
				todennakoisyys = (int) Math.floor(Math.random() * (4 - 1 + 1) + 1) - 1;
			}
			
			// Jos todennäköisyys on 2 tai alle, asiakas poistuu pelistä kokonaan
			if (todennakoisyys <= 2) {
				// Tarkistetaan siirtyykö asiakas voittojen nostopisteelle
				if (palvelupisteet[2].siirtyykoVoittojenNostopisteelle(asiakas)) {
					palvelupisteet[2].otaJonosta();
					palvelupisteet[4].lisaaJonoon(asiakas);
				} else {
					palvelupisteet[2].otaJonosta();
					// Asiakas arvotaan uuteen peliin
					int randomPeli = (int) Math.floor(Math.random() * (2 - 1 + 1) + 1);
					if (randomPeli == 1) {
						palvelupisteet[1].lisaaJonoon(asiakas);
						palvelupisteet[1].tulostaJononAsiakkaat();
					} else {
						palvelupisteet[3].lisaaJonoon(asiakas);
						palvelupisteet[3].tulostaJononAsiakkaat();
					}
				}
			} else {
				palvelupisteet[2].jatkaPelaamista(asiakas);
			}
		} else if (t.getTyyppi().equals(TapahtumanTyyppi.DEP4)) { // Lähtö Krapsistä
			asiakas = palvelupisteet[3].getJono().getFirst();	
			if (palvelupisteet[3].pelaa(asiakas)) {
				// Arvotaan luku väliltä 1-4 ja lisätään siihen 1
				todennakoisyys = (int) Math.floor(Math.random() * (4 - 1 + 1) + 1) + 1;
			} else {
				// Arvotaan luku väliltä 1-4 ja vähennetään siitä 1
				todennakoisyys = (int) Math.floor(Math.random() * (4 - 1 + 1) + 1) - 1;
			}
			
			// Jos todennäköisyys on 2 tai alle, asiakas poistuu pelistä kokonaan
			if (todennakoisyys <= 2) {
				// Tarkistetaan siirtyykö asiakas voittojen nostopisteelle
				if (palvelupisteet[3].siirtyykoVoittojenNostopisteelle(asiakas)) {
					palvelupisteet[3].otaJonosta();
					palvelupisteet[4].lisaaJonoon(asiakas);
				} else {
					palvelupisteet[3].otaJonosta();
					// Asiakas arvotaan uuteen peliin
					int randomPeli = (int) Math.floor(Math.random() * (2 - 1 + 1) + 1);
					if (randomPeli == 1) {
						palvelupisteet[1].lisaaJonoon(asiakas);
						palvelupisteet[1].tulostaJononAsiakkaat();
					} else {
						palvelupisteet[2].lisaaJonoon(asiakas);
						palvelupisteet[2].tulostaJononAsiakkaat();
					}
				}
			} else {
				palvelupisteet[3].jatkaPelaamista(asiakas);
			}
		} else if (t.getTyyppi().equals(TapahtumanTyyppi.DEP5)) { // Lähtö voittojen nostopisteeltä
		    asiakas = palvelupisteet[4].otaJonosta();
		    System.out.println("Asiakas " + asiakas.getId() + " poistuu kasinolta.");
			asiakas.setPoistumisaika(Kello.getInstance().getAika());
	        asiakas.raportti(); 
		}
	}*/
	
	@Override
	protected void tulokset() {
		
		// VANHAA tekstipohjaista
		// System.out.println("Simulointi päättyi kello " + Kello.getInstance().getAika());
		// System.out.println("Tulokset ... puuttuvat vielä");
		
		// UUTTA graafista
		kontrolleri.naytaLoppuaika(Kello.getInstance().getAika());
		
	}

}
