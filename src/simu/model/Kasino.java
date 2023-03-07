package simu.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import simu.framework.Kello;



@Entity
@Table(name="kasino")
public class Kasino {
	
	@Transient
	Palvelupiste palvelupiste = new Palvelupiste();
	
	@Transient
	private Kello kellor = Kello.getInstance();
	
	@SuppressWarnings("unused")
	private int talonVoittoEuroina;
	
	private int asiakasLKM;
	
	private double keskimaarainenVietettyAika;
	
	@Id
    @Column(name="nimi")
	private String nimi = "Kasino";

	@SuppressWarnings("unused")
	private double kello;
	
	public Kasino() {
		
	}
	
	public String getNimi() {
		return nimi;
	}
	
	public int getTalonVoittoEuroina() {
		return palvelupiste.getTalonVoittoEuroina();
	}
	public void setTalonVoittoEuroina(int talonVoittoEuroina) {
		this.talonVoittoEuroina = talonVoittoEuroina;
	}
	
	public void setAsiakasLKM(int asiakasLKM) {
		this.asiakasLKM = asiakasLKM;
	}
	
	public int getAsiakasLKM() {
		return asiakasLKM;
	}
	
	public void setKello(double kello) {
		this.kello = kello;
	}
	
	public double getKello() {
		return Kello.getInstance().getAika();
	}
	
	public void setkeskimaarainenVietettyAika(double keskimaarainenVietettyAika) {
		this.keskimaarainenVietettyAika = keskimaarainenVietettyAika;
	}
	
	public double getkeskimaarainenVietettyAika() {
		return keskimaarainenVietettyAika;
	}
	
}
