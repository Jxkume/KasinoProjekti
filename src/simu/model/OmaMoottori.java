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
		
		saapumisprosessi = new Saapumisprosessi(new Negexp(15,5), tapahtumalista, TapahtumanTyyppi.ARR1);
	
		// TO-DO: muutetaan jakaumat oikeiksi, kun ollaan päätetty ne - Valdo
		palvelupisteet = new Palvelupiste[5];
		palvelupisteet[0]=new Palvelupiste(new Normal(10,6), tapahtumalista, TapahtumanTyyppi.DEP1); 	// Palvelutiski
		palvelupisteet[1]=new Palvelupiste(new Normal(10,10), tapahtumalista, TapahtumanTyyppi.DEP2); 	// Ruletti
		palvelupisteet[2]=new Palvelupiste(new Normal(5,3), tapahtumalista, TapahtumanTyyppi.DEP3); 	// Blackjack
		palvelupisteet[3]=new Palvelupiste(new Normal(5,3), tapahtumalista, TapahtumanTyyppi.DEP4);		// Kraps
		palvelupisteet[4]=new Palvelupiste(new Normal(5,3), tapahtumalista, TapahtumanTyyppi.DEP5);		// Voittojen nostopiste
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

	// Tähän tulee simulaation logiikka miten asiakkaat liikkuvat palvelupisteille
	@Override
	protected void suoritaTapahtuma(Tapahtuma t) {  // B-vaiheen tapahtumat
		if (t.getTyyppi().equals(TapahtumanTyyppi.ARR1)) {
			palvelupisteet[0].lisaaJonoon(new Asiakas());
		    saapumisprosessi.generoiSeuraava();	
		    kontrolleri.visualisoiAsiakas(); // UUSI
		    System.out.println("Asiakas saapuu kasinolle.");
		    System.out.println("Asiakas saapuu palvelutiskin jonoon.");
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
