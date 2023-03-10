package simu.model;

import jakarta.persistence.Column; 
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import simu.framework.Kello;

/**
 * Luokka kasinon tulosteille
 * 
 * @author Tapio Humaljoki, Valtteri Kuitula, Jhon Rastrojo
 * 
 */
@Entity
@Table(name="kasino")
public class Kasino {
	
	/** Kasinon nimi (tarvitaan tietokannassa) */
	@Id
    @Column(name="nimi")
	private String nimi = "Kasino";
	
	/** Palvelupiste-olio */
	@Transient
	Palvelupiste palvelupiste = new Palvelupiste();
	
	/** Kasinon tekemä tulos */
	@SuppressWarnings("unused")
	private int talonVoittoEuroina;
	
	/** Kasinoon saapuvien asiakkaiden lukumäärä */
	private int asiakasLKM;
	
	/** Kasinon asiakkaiden keskimäärin vietetty aika */
	private double keskimaarainenVietettyAika;
	
	/** Simulaattorin kello */
	@SuppressWarnings("unused")
	private double kello;
	
	/**
	 * Oletuskonstruktori
	 */
	public Kasino() {}
	
	/**
	 * Palauttaa kasinon nimen
	 *
	 * @return nimi
	 */
	public String getNimi() {
		return nimi;
	}
	
	/**
	 * Palauttaa kasinon tekemän tuloksen palvelupisteen kautta
	 *
	 * @return kasinon tekemä tulos
	 */
	public int getTalonVoittoEuroina() {
		return palvelupiste.getTalonVoittoEuroina();
	}
	
	/**
	 * Asettaa kasinon tekemän voiton
	 *
	 * @param kasinon voittosumma
	 */
	public void setTalonVoittoEuroina(int talonVoittoEuroina) {
		this.talonVoittoEuroina = talonVoittoEuroina;
	}
	
	/**
	 * Asettaa kasinoon saapuneiden asiakkaiden lukumäärän
	 *
	 * @param kasinoon saapuvien asiakkaiden lukumäärä
	 */
	public void setAsiakasLKM(int asiakasLKM) {
		this.asiakasLKM = asiakasLKM;
	}
	
	/**
	 * Palauttaa kasinoon saapuneiden asiakkaiden lukumäärän
	 *
	 * @return kasinoon saapuvien asiakkaiden lukumäärä
	 */
	public int getAsiakasLKM() {
		return asiakasLKM;
	}
	
	/**
	 * Asettaa kasinon kellonajan
	 *
	 * @param kasinon kellonaika
	 */
	public void setKello(double kello) {
		this.kello = kello;
	}
	
	/**
	 * Palauttaa simulaattorin kellonajan
	 *
	 * @return simulaattorin kellonaika
	 */
	public double getKello() {
		return Kello.getInstance().getAika();
	}
	
	/**
	 * Asettaa asiakkaiden keskimäärin vietetyn ajan kasinolla
	 *
	 * @param asiakkaiden keskimäärin vietetty aika kasinolla
	 */
	public void setkeskimaarainenVietettyAika(double keskimaarainenVietettyAika) {
		this.keskimaarainenVietettyAika = keskimaarainenVietettyAika;
	}
	
	/**
	 * Palauttaa asiakkaiden keskimäärin vietetyn ajan kasinolla
	 *
	 * @return asiakkaiden keskimäärin vietetty aika kasinolla
	 */
	public double getkeskimaarainenVietettyAika() {
		return keskimaarainenVietettyAika;
	}
	
}