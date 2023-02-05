package simu.model;

import eduni.distributions.Negexp;
import eduni.distributions.Normal;
import simu.framework.Kello;
import simu.framework.Moottori;
import simu.framework.Saapumisprosessi;
import simu.framework.Tapahtuma;

public class OmaMoottori extends Moottori{
	
	private Saapumisprosessi saapumisprosessi;
	private static int asiakasLKM = 0;
	private int talonALKpolettimaara = 0;
	private static int talonLOPpolettimaara = 0;
	private int polettiNostoMAX = 0;
	private int polettiNostoMIN = 0;


	public OmaMoottori(){
			
		palvelupisteet = new Palvelupiste[5];
	
		palvelupisteet[0]=new Palvelupiste(new Normal(10,6), tapahtumalista, TapahtumanTyyppi.DEP1,"Palvelutiski", 1);	
		palvelupisteet[1]=new Palvelupiste(new Normal(10,10), tapahtumalista, TapahtumanTyyppi.DEP2, "Ruletti", 1);
		palvelupisteet[2]=new Palvelupiste(new Normal(5,3), tapahtumalista, TapahtumanTyyppi.DEP3, "Blackjack", 2);
		palvelupisteet[3]=new Palvelupiste(new Normal(5,3), tapahtumalista, TapahtumanTyyppi.DEP4, "Kraps",1 );
		palvelupisteet[4]=new Palvelupiste(new Normal(5,3), tapahtumalista, TapahtumanTyyppi.DEP5, "Voittojen nostopiste",1 );
		
		saapumisprosessi = new Saapumisprosessi(new Negexp(15,5), tapahtumalista, TapahtumanTyyppi.ARR1);

	}

	
	@Override
	protected void alustukset() {
		saapumisprosessi.generoiSeuraava(); // Ensimmäinen saapuminen järjestelmään
	}
	
	@Override
	protected void suoritaTapahtuma(Tapahtuma t){  // B-vaiheen tapahtumat

		Asiakas a = new Asiakas();
		switch (t.getTyyppi()){
			
			case ARR1: palvelupisteet[0].lisaaJonoon(a);                                                                                                                                              
					   asiakasLKM++;
				       saapumisprosessi.generoiSeuraava();
				       System.out.println("Asiakas " + a.getId() + " palvelutiskille");
				       palvelupisteet[0].polettienNosto(50);
				break;
			case DEP1: a = palvelupisteet[0].otaJonosta();
				   	   palvelupisteet[1].lisaaJonoon(a);
				   	   System.out.println("Asiakas " + a.getId() + " saapuu Ruletille");
				   	   palvelupisteet[1].pelaa();
				   	   
				break;
			case DEP2: a = palvelupisteet[1].otaJonosta();
				   	   palvelupisteet[2].lisaaJonoon(a);
				   	   System.out.println("Asiakas " + a.getId() + " saapuu Blackjackille");
				   	   palvelupisteet[2].pelaa();
				break;
			case DEP3: a = palvelupisteet[2].otaJonosta();
		   	   			palvelupisteet[3].lisaaJonoon(a);
		   	   			System.out.println("Asiakas " + a.getId() + " saapuu Krapsille");
		   	   			palvelupisteet[3].pelaa();
		   	   break;
			case DEP4: a = palvelupisteet[3].otaJonosta();
		   	   			palvelupisteet[4].lisaaJonoon(a);
		   	   			System.out.println("Asiakas " + a.getId() + " saapuu voittojen nostopisteelle");
		   	   			palvelupisteet[4].nostaVoitot();
		   	   break;  
			case DEP5: 
					   a = palvelupisteet[4].otaJonosta();
					   a.setPoistumisaika(Kello.getInstance().getAika());
			           a.raportti(); 
		}	
	}
	
		
	@Override
	protected void tulokset() {	
		System.out.println("Simulointi päättyi kello " + Kello.getInstance().getAika());
		System.out.println("Asiakkaita saapui yhteensä " + asiakasLKM);
		System.out.println("Tulokset ... puuttuvat vielä");
	}

	
}
