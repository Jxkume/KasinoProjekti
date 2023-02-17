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
		palvelupisteet[0] = new Palvelupiste(new Normal(10,6), tapahtumalista, TapahtumanTyyppi.DEP1); 	// Lähtö palvelutiskiltä
		palvelupisteet[1] = new Palvelupiste(new Normal(10,10), tapahtumalista, TapahtumanTyyppi.DEP2); // Lähtö ruletista
		palvelupisteet[2] = new Palvelupiste(new Normal(5,3), tapahtumalista, TapahtumanTyyppi.DEP3); 	// Lähtö Blackjackistä
		palvelupisteet[3] = new Palvelupiste(new Normal(5,3), tapahtumalista, TapahtumanTyyppi.DEP4);	// Lähtö Krapsistä
		palvelupisteet[4] = new Palvelupiste(new Normal(5,3), tapahtumalista, TapahtumanTyyppi.DEP5);	// Lähtö voittojen nostopisteeltä
	}

	@Override
	protected void alustukset() {
		saapumisprosessi.generoiSeuraava(); // Ensimmäinen saapuminen järjestelmään
	}
	
	/*
	@Override
	protected void suoritaTapahtuma(Tapahtuma t){  // B-vaiheen tapahtumat

		Asiakas a;
		switch (t.getTyyppi()){
			
			case ARR1: palvelupisteet[0].lisaaJonoon(new Asiakas());	
				       saapumisprosessi.generoiSeuraava();	
				       kontrolleri.visualisoiAsiakas(); // UUSI
				       System.out.println("Asiakas saapuu kasinolle.");
				       System.out.println("Asiakas saapuu palvelutiskin jonoon.");
				break;
			case DEP1: a = palvelupisteet[0].otaJonosta();
				   	   palvelupisteet[1].lisaaJonoon(a);
				   	   System.out.println("Asiakas saapuu ruletin jonoon.");
				break;
			case DEP2: a = palvelupisteet[1].otaJonosta();
				   	   palvelupisteet[2].lisaaJonoon(a);
				   	   System.out.println("Asiakas saapuu Blackjackin jonoon.");
				break;
			case DEP3: a = palvelupisteet[2].otaJonosta();
		   	   		   palvelupisteet[3].lisaaJonoon(a);
		   	   		   System.out.println("Asiakas saapuu Krapsin jonoon.");
		   	   break;  
			case DEP4: a = palvelupisteet[3].otaJonosta();
		   	   		   palvelupisteet[4].lisaaJonoon(a);
		   	   		   System.out.println("Asiakas saapuu voittojen nostopisteen jonoon.");
		   	   break;  
			case DEP5: 
				       a = palvelupisteet[4].otaJonosta();
				       System.out.println("Asiakas poistuu kasinolta.");
					   a.setPoistumisaika(Kello.getInstance().getAika());
			           a.raportti(); 
		}	
	}
	*/

	// Simulaation logiikka miten asiakkaat liikkuvat palvelupisteillä
	@Override
	protected void suoritaTapahtuma(Tapahtuma t) {  // B-vaiheen tapahtumat
		Asiakas asiakas;
		// Asiakkaan saapuessa kasinoon hänet ohjataan kasinon palvelutiskin jonoon.
		if (t.getTyyppi().equals(TapahtumanTyyppi.ARR1)) {
			palvelupisteet[0].lisaaJonoon(new Asiakas());	
		    kontrolleri.visualisoiAsiakas(); // UUSI
		    //System.out.println("Asiakas " + palvelupisteet[0].getJono().getFirst().getId() + " saapuu palvelutiskin jonoon.");
		    System.out.println("Asiakkaat palvelutiskin jonossa: " + palvelupisteet[0]);
		    saapumisprosessi.generoiSeuraava();
		    
		} else if (t.getTyyppi().equals(TapahtumanTyyppi.DEP1)) {
			asiakas = palvelupisteet[0].getJono().getFirst();
			// Asiakkaalle annetaan poletteja palvelutiskillä
			asiakas.setPolettimaara();
			palvelupisteet[0].otaJonosta();

			// Arvotaan luku väliltä 1-3
			int random = (int) Math.floor(Math.random() * (3 - 1 + 1) + 1);
			if (random == 1) {
				palvelupisteet[1].lisaaJonoon(asiakas);
				System.out.println("Asiakas " + asiakas.getId() + " saapuu ruletin jonoon.");
				System.out.println("Asiakkaalla " + asiakas.getId() + " poletteja yhteensä " + asiakas.getPolettimaara());
				System.out.println("Asiakkaat ruletin jonossa: " + palvelupisteet[1]);
			} else if (random == 2) {
				palvelupisteet[2].lisaaJonoon(asiakas);
				System.out.println("Asiakas " + asiakas.getId() + " saapuu Blackjackin jonoon.");
				System.out.println("Asiakkaalla " + asiakas.getId() + " poletteja yhteensä " + asiakas.getPolettimaara());
				System.out.println("Asiakkaat Blackjackin jonossa: " + palvelupisteet[2]);
			} else {
				palvelupisteet[3].lisaaJonoon(asiakas);
				System.out.println("Asiakas " + asiakas.getId() + " saapuu Krapsin jonoon.");
				System.out.println("Asiakkaalla " + asiakas.getId() + " poletteja yhteensä " + asiakas.getPolettimaara());
				System.out.println("Asiakkaat Krapsin jonossa: " + palvelupisteet[3]);
			}
			
		} else if (t.getTyyppi().equals(TapahtumanTyyppi.DEP2)) {
			asiakas = palvelupisteet[1].getJono().getFirst();
			int todennakoisyys;			
			if (palvelupisteet[1].pelaa(asiakas) == true) {
				// Arvotaan luku väliltä 1-4 ja lisätään siihen 1
				todennakoisyys = (int) Math.floor(Math.random() * (4 - 1 + 1) + 1) + 1;
			} else {
				// Arvotaan luku väliltä 1-4 ja vähennetään siitä 1
				todennakoisyys = (int) Math.floor(Math.random() * (4 - 1 + 1) + 1) - 1;
			}
			
			// Jos todennäköisyys on 2 tai alle, asiakas poistuu pelistä kokonaan
			if (todennakoisyys <= 2) {
				palvelupisteet[1].otaJonosta();
				System.out.println("Asiakas " + asiakas.getId() + " poistuu ruletin jonosta.");
				palvelupisteet[4].lisaaJonoon(asiakas);
			} else {
				palvelupisteet[1].otaJonosta();
				palvelupisteet[1].jatkaPelaamista(asiakas);
			}
			
		} else if (t.getTyyppi().equals(TapahtumanTyyppi.DEP3)) {
			asiakas = palvelupisteet[2].getJono().getFirst();
			int todennakoisyys;			
			if (palvelupisteet[2].pelaa(asiakas) == true) {
				// Arvotaan luku väliltä 1-4 ja lisätään siihen 1
				todennakoisyys = (int) Math.floor(Math.random() * (4 - 1 + 1) + 1) + 1;
			} else {
				// Arvotaan luku väliltä 1-4 ja vähennetään siitä 1
				todennakoisyys = (int) Math.floor(Math.random() * (4 - 1 + 1) + 1) - 1;
			}
			
			// Jos todennäköisyys on 2 tai alle, asiakas poistuu pelistä kokonaan
			if (todennakoisyys <= 2) {
				palvelupisteet[2].otaJonosta();
				System.out.println("Asiakas " + asiakas.getId() + " poistuu Krapsin jonosta.");
				palvelupisteet[4].lisaaJonoon(asiakas);
			} else {
				palvelupisteet[2].otaJonosta();
				palvelupisteet[2].jatkaPelaamista(asiakas);
			}
			
		} else if (t.getTyyppi().equals(TapahtumanTyyppi.DEP4)) {
			asiakas = palvelupisteet[3].getJono().getFirst();
			int todennakoisyys;			
			if (palvelupisteet[3].pelaa(asiakas) == true) {
				// Arvotaan luku väliltä 1-4 ja lisätään siihen 1
				todennakoisyys = (int) Math.floor(Math.random() * (4 - 1 + 1) + 1) + 1;
			} else {
				// Arvotaan luku väliltä 1-4 ja vähennetään siitä 1
				todennakoisyys = (int) Math.floor(Math.random() * (4 - 1 + 1) + 1) - 1;
			}
			
			// Jos todennäköisyys on 2 tai alle, asiakas poistuu pelistä kokonaan
			if (todennakoisyys <= 2) {
				palvelupisteet[3].otaJonosta();
				System.out.println("Asiakas " + asiakas.getId() + " poistuu ruletin jonosta.");
				palvelupisteet[4].lisaaJonoon(asiakas);
			} else {
				palvelupisteet[3].otaJonosta();
				palvelupisteet[3].jatkaPelaamista(asiakas);
			}
			
		} else if (t.getTyyppi().equals(TapahtumanTyyppi.DEP5)) {
		    asiakas = palvelupisteet[4].otaJonosta();
		    System.out.println("Asiakas " + asiakas.getId() + " poistuu kasinolta.");
			asiakas.setPoistumisaika(Kello.getInstance().getAika());
	        asiakas.raportti(); 
		}
		
		// Asiakkaan lunastettua mahdolliset voitot voittojen nostopisteeltä, hän poistuu kasinosta
		// TO-DO: Asiakkaalle pitää antaa voitot ja pitää laskea paljon kasino teki voittoa/tappiota, en oo varma tehäänkö se tässä kohtaa. - Valdo
		
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