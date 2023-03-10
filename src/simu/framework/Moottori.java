package simu.framework;

import simu.model.Palvelupiste;
import view.SimulaattorinPaaikkunaKontrolleri;


public abstract class Moottori extends Thread implements IMoottori {  // UUDET MÄÄRITYKSET
	
  private double simulointiaika = 0;
	
	private long viive = 0;
	
	private Kello kello;
	
	protected Tapahtumalista tapahtumalista;
	
	protected Palvelupiste[] palvelupisteet;
	
	//protected IKontrolleri kontrolleri; // UUSI
	protected SimulaattorinPaaikkunaKontrolleri kontrolleri;
	
	//public Moottori(IKontrolleri kontrolleri) {  // UUSITTU
	public Moottori(SimulaattorinPaaikkunaKontrolleri kontrolleri) {	
		this.kontrolleri = kontrolleri;  //UUSI

		kello = Kello.getInstance(); // Otetaan kello muuttujaan yksinkertaistamaan koodia
		
		tapahtumalista = new Tapahtumalista();
		
		// Palvelupisteet luodaan simu.model-pakkauksessa Moottorin aliluokassa 
		
		
	}
	
	
	@Override
	public void setSimulointiaika(double aika) {
		simulointiaika = aika;
	}
	
	public double getSimulointiaika() {
		return simulointiaika;
	}
	
	@Override // UUSI
	public void setViive(long viive) {
		this.viive = viive;
	}
	
	
	@Override // UUSI 
	public long getViive() {
		return viive;
	}
	
	@Override
	public void run() { // Entinen aja()
		alustukset(); // luodaan mm. ensimmäinen tapahtuma
		while (simuloidaan()) {
			kello.setAika(nykyaika());
			Trace.out(Trace.Level.INFO, "\nKello on: " + String.format("%.02f", kello.getAika()));
			viive(); 
			suoritaBTapahtumat();
			yritaCTapahtumat();
		}
		tulokset();
		paivitaTietokanta();
	}
	
	
	private void suoritaBTapahtumat() {
		while (tapahtumalista.getSeuraavanAika() == kello.getAika()){
			suoritaTapahtuma(tapahtumalista.poista());
		}
	}

	
	private void yritaCTapahtumat() {    // määrittele protectediksi, josa haluat ylikirjoittaa
		for (Palvelupiste p: palvelupisteet){
			if (!p.onVarattu() && p.onJonossa()){
				p.aloitaPalvelu();
			}
		}
	}

	
	private double nykyaika() {
		return tapahtumalista.getSeuraavanAika();
	}
	
	
	private boolean simuloidaan() {
		return kello.getAika() < simulointiaika;
	}
		
	
	private void viive() { // UUSI
		Trace.out(Trace.Level.INFO, "Viive " + viive);
		try {
			sleep(viive);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	
	protected abstract void alustukset(); // Määritellään simu.model-pakkauksessa Moottorin aliluokassa
	
	protected abstract void suoritaTapahtuma(Tapahtuma t);  // Määritellään simu.model-pakkauksessa Moottorin aliluokassa
	
	protected abstract void tulokset(); // Määritellään simu.model-pakkauksessa Moottorin aliluokassa
	
	protected abstract void paivitaTietokanta(); // Määritellään simu.model-pakkauksessa Moottorin aliluokassa
	
}