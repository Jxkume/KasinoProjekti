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
	
	/** Kasinon tekema tulos */
	@SuppressWarnings("unused")
	private int talonVoittoEuroina;
	
	/** Kasinoon saapuvien asiakkaiden lukumaara */
	private int asiakasLKM;
	
	/** Kasinon asiakkaiden keskimaarin vietetty aika */
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
	 * Palauttaa kasinon tekeman tuloksen palvelupisteen kautta
	 *
	 * @return kasinon tekema tulos
	 */
	public int getTalonVoittoEuroina() {
		return palvelupiste.getTalonVoittoEuroina();
	}
	
	/**
	 * Asettaa kasinon tekeman voiton
	 *
	 * @param kasinon voittosumma
	 */
	public void setTalonVoittoEuroina(int talonVoittoEuroina) {
		this.talonVoittoEuroina = talonVoittoEuroina;
	}
	
	/**
	 * Asettaa kasinoon saapuneiden asiakkaiden lukumaaran
	 *
	 * @param kasinoon saapuvien asiakkaiden lukumaara
	 */
	public void setAsiakasLKM(int asiakasLKM) {
		this.asiakasLKM = asiakasLKM;
	}
	
	/**
	 * Palauttaa kasinoon saapuneiden asiakkaiden lukumaaran
	 *
	 * @return kasinoon saapuvien asiakkaiden lukumaara
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
	 * Asettaa asiakkaiden keskimaarin vietetyn ajan kasinolla
	 *
	 * @param asiakkaiden keskimaarin vietetty aika kasinolla
	 */
	public void setkeskimaarainenVietettyAika(double keskimaarainenVietettyAika) {
		this.keskimaarainenVietettyAika = keskimaarainenVietettyAika;
	}
	
	/**
	 * Palauttaa asiakkaiden keskimaarin vietetyn ajan kasinolla
	 *
	 * @return asiakkaiden keskimaarin vietetty aika kasinolla
	 */
	public double getkeskimaarainenVietettyAika() {
		return keskimaarainenVietettyAika;
	}
	
}