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
	
		palvelupisteet = new Palvelupiste[5];
		palvelupisteet[0] = new Palvelupiste(new Normal(10,6), tapahtumalista, TapahtumanTyyppi.DEP1, "Palvelutiski");
		palvelupisteet[1] = new Palvelupiste(new Normal(10,10), tapahtumalista, TapahtumanTyyppi.DEP2, "Ruletti");
		palvelupisteet[2] = new Palvelupiste(new Normal(5,3), tapahtumalista, TapahtumanTyyppi.DEP3, "Blackjack");
		palvelupisteet[3] = new Palvelupiste(new Normal(5,3), tapahtumalista, TapahtumanTyyppi.DEP4, "Kraps");
		palvelupisteet[4] = new Palvelupiste(new Normal(5,3), tapahtumalista, TapahtumanTyyppi.DEP5, "Voittojen nostopiste");
	}

	@Override
	protected void alustukset() {
		saapumisprosessi.generoiSeuraava(); // Ensimmäinen saapuminen järjestelmään
	}
	
	// Simulaation logiikka miten asiakkaat liikkuvat palvelupisteillä
	// TO-DO: Luodaanko asiakas oikeassa paikassa? Pitää tarkistaa - Valdo
	// TO-DO: Asiakas voi mennä uuteen peliin eikä suoraan voittojen nostopisteelle - Valdo
	// TO-DO: Asiakas voi hakea lisää poletteja palvelutiskiltä - Valdo
	// TO-DO: Jos asiakkaalla on 0 polettia, hänen pitää lähteä pois tai hakea lisää poletteja - Valdo
	// TO-DO: Asiakkaalle pitää antaa voitot ja pitää laskea paljon kasino teki voittoa/tappiota. - Valdo
	@Override
	protected void suoritaTapahtuma(Tapahtuma t){  // B-vaiheen tapahtumat

		Asiakas asiakas;
		int todennakoisyys;
		int randomPeli;
		
		switch (t.getTyyppi()) {
			
			case ARR1:
				palvelupisteet[0].lisaaJonoon(new Asiakas());	
		    	kontrolleri.visualisoiAsiakas(); // UUSI
		    	palvelupisteet[0].tulostaJononAsiakkaat();
		    	saapumisprosessi.generoiSeuraava();
				break;
			case DEP1:
				asiakas = palvelupisteet[0].getJono().getFirst();
				// Asiakkaalle annetaan poletteja palvelutiskillä
				asiakas.annaPoletteja();
				// Arvotaan luku väliltä 1-3
				randomPeli = (int) Math.floor(Math.random() * (3 - 1 + 1) + 1);
				if (randomPeli == 1) {
					palvelupisteet[0].otaJonosta(asiakas);
					palvelupisteet[1].lisaaJonoon(asiakas);
					palvelupisteet[1].tulostaJononAsiakkaat();
				} else if (randomPeli == 2) {
					palvelupisteet[0].otaJonosta(asiakas);
					palvelupisteet[2].lisaaJonoon(asiakas);
					palvelupisteet[2].tulostaJononAsiakkaat();
				} else {
					palvelupisteet[0].otaJonosta(asiakas);
					palvelupisteet[3].lisaaJonoon(asiakas);
					palvelupisteet[3].tulostaJononAsiakkaat();
				}
				break;
			case DEP2:
				asiakas = palvelupisteet[1].getJono().getFirst();
				// Arvotaan luku väliltä 1-4
				todennakoisyys = (int) Math.floor(Math.random() * (4 - 1 + 1) + 1);
				// Asiakas pelaa ja tarkistetaan voittiko vai hävisikö asiakas
				if (palvelupisteet[1].voittikoAsiakas(asiakas)) {
					// Lisätään lukuun 1
					todennakoisyys += 1;
				} else {
					// Vähennetään luvusta 1
					todennakoisyys -= 1;
				}
				// Jos todennäköisyys on 2 tai alle, asiakas poistuu hakemaan lisää poletteja tai voittojen nostopisteelle
				if (todennakoisyys <= 2) {
					palvelupisteet[1].otaJonosta(asiakas);
					palvelupisteet[4].lisaaJonoon(asiakas);
					palvelupisteet[4].tulostaJononAsiakkaat();
				} else {
					palvelupisteet[1].jatkaPelaamista(asiakas);
				}
		   	   	break;
			case DEP3:
				asiakas = palvelupisteet[2].getJono().getFirst();
				// Arvotaan luku väliltä 1-4
				todennakoisyys = (int) Math.floor(Math.random() * (4 - 1 + 1) + 1);
				// Asiakas pelaa ja tarkistetaan voittiko vai hävisikö asiakas
				if (palvelupisteet[2].voittikoAsiakas(asiakas)) {
					// Lisätään lukuun 1
					todennakoisyys += 1;
				} else {
					// Vähennetään luvusta 1
					todennakoisyys -= 1;
				}
				// Jos todennäköisyys on 2 tai alle, asiakas poistuu voittojen nostopisteelle
				if (todennakoisyys <= 2) {
					palvelupisteet[2].otaJonosta(asiakas);
					palvelupisteet[4].lisaaJonoon(asiakas);
					palvelupisteet[4].tulostaJononAsiakkaat();
				} else {
					palvelupisteet[2].jatkaPelaamista(asiakas);
				}
		   	   	break;
			case DEP4:
				asiakas = palvelupisteet[3].getJono().getFirst();
				// Arvotaan luku väliltä 1-4
				todennakoisyys = (int) Math.floor(Math.random() * (4 - 1 + 1) + 1);
				// Asiakas pelaa ja tarkistetaan voittiko vai hävisikö asiakas
				if (palvelupisteet[3].voittikoAsiakas(asiakas)) {
					// Lisätään lukuun 1
					todennakoisyys += 1;
				} else {
					// Vähennetään luvusta 1
					todennakoisyys -= 1;
				}
				// Jos todennäköisyys on 2 tai alle, asiakas poistuu hakemaan lisää poletteja tai voittojen nostopisteelle
				if (todennakoisyys <= 2) {
					palvelupisteet[3].otaJonosta(asiakas);
					palvelupisteet[4].lisaaJonoon(asiakas);
					palvelupisteet[4].tulostaJononAsiakkaat();
				} else {
					palvelupisteet[3].jatkaPelaamista(asiakas);
				}
		   	   	break;
			case DEP5: 
				asiakas = palvelupisteet[4].getJono().getFirst(); 
		    	palvelupisteet[4].otaJonosta(asiakas);
		    	System.out.println("Asiakas " + asiakas.getId() + " poistuu kasinolta.");
				asiakas.setPoistumisaika(Kello.getInstance().getAika());
	        	asiakas.raportti();
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