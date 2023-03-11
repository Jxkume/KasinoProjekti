package view;

import java.util.HashMap;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Luokka KrapsPopUp.fxml-tiedoston kontrollerille
 * 
 * @author Tapio Humaljoki, Valtteri Kuitula, Jhon Rastrojo
 */
public class KrapsPopUpKontrolleri {
	
	/** HashMap-lista Kraps-palvelupisteen tulosteista */
	private static HashMap<String, String> krapsinTulosteet;
	
	// Pop-up-ikkunan JavaFX-komponentit
	@FXML
    private Label palvelupisteNimi;
    @FXML
    private Label palvellutAsiakkaat;
    @FXML
    private Label keskimaarainenPalveluaika;
    @FXML
    private Label keskimaarainenJononpituus;
    @FXML
    private Label keskimaarainenLapimenoaika;
    @FXML
    private Label suoritusteho;
    @FXML
    private Label aktiiviaika;
    @FXML
    private Label kayttoaste;
    @FXML
    private Label kokonaisoleskeluaika;
    
    /**
     * Kontrollerin konstruktori
     */
    public KrapsPopUpKontrolleri() {
    	palvelupisteNimi = new Label();
    	palvellutAsiakkaat = new Label();
    	keskimaarainenPalveluaika = new Label();
    	keskimaarainenJononpituus = new Label();
    	keskimaarainenLapimenoaika = new Label();
    	suoritusteho = new Label();
    	aktiiviaika = new Label();
    	kayttoaste = new Label();
    	kokonaisoleskeluaika = new Label();
    }
    
	/**
	 * Alustaa pop-up-ikkunan
	 */
	public void initialize() {
		Platform.runLater(()-> naytaTulosteet());
	}
    
	/**
	 * Asettaa pop-up-ikkunan tulosteet
	 *
	 * @param HashMap-lista, jossa on Kraps-palvelupisteen tulosteet
	 */
	public void setKrapsinTulosteet(HashMap<String, String> tulosteet) {
		krapsinTulosteet = tulosteet;
	}
	
	/**
	 * Asettaa kaikki tulosteet pop-up-ikkunan JavaFX-komponentteihin
	 */
	public void naytaTulosteet() {
		palvelupisteNimi.setText("Yhteenveto Krapsistä");
		palvellutAsiakkaat.setText(krapsinTulosteet.get("Palveltuja asiakkaita yhteensä"));
		keskimaarainenPalveluaika.setText(krapsinTulosteet.get("Asiakkaiden keskimääräinen palveluaika"));
		keskimaarainenJononpituus.setText(krapsinTulosteet.get("Keskimääräinen jononpituus"));
		keskimaarainenLapimenoaika.setText(krapsinTulosteet.get("Asiakkaiden keskimääräinen läpimenoaika"));
		suoritusteho.setText(krapsinTulosteet.get("Suoritusteho"));
		aktiiviaika.setText(krapsinTulosteet.get("Aktiiviaika"));
		kayttoaste.setText(krapsinTulosteet.get("Käyttöaste"));
		kokonaisoleskeluaika.setText(krapsinTulosteet.get("Asiakkaiden kokonaisoleskeluaika"));
	}

}