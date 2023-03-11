package view;

import java.util.HashMap;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Luokka PalvelutiskiPopUp.fxml-tiedoston kontrollerille
 * 
 * @author Tapio Humaljoki, Valtteri Kuitula, Jhon Rastrojo
 */
public class PalvelutiskiPopUpKontrolleri {
	
	/** HashMap-lista Palvelutiski-palvelupisteen tulosteista */
	private static HashMap<String, String> palvelutiskinTulosteet;
	
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
    public PalvelutiskiPopUpKontrolleri() {
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
	 * @param HashMap-lista, jossa on Palvelutiski-palvelupisteen tulosteet
	 */
	public void setPalvelutiskinTulosteet(HashMap<String, String> tulosteet) {
		palvelutiskinTulosteet = tulosteet;
	}
	
	/**
	 * Asettaa kaikki tulosteet pop-up-ikkunan JavaFX-komponentteihin
	 */
	public void naytaTulosteet() {
		palvelupisteNimi.setText("Yhteenveto palvelutiskista");
		palvellutAsiakkaat.setText(palvelutiskinTulosteet.get("Palveltuja asiakkaita yhteensa"));
		keskimaarainenPalveluaika.setText(palvelutiskinTulosteet.get("Asiakkaiden keskimaarainen palveluaika"));
		keskimaarainenJononpituus.setText(palvelutiskinTulosteet.get("Keskimaarainen jononpituus"));
		keskimaarainenLapimenoaika.setText(palvelutiskinTulosteet.get("Asiakkaiden keskimaarainen lapimenoaika"));
		suoritusteho.setText(palvelutiskinTulosteet.get("Suoritusteho"));
		aktiiviaika.setText(palvelutiskinTulosteet.get("Aktiiviaika"));
		kayttoaste.setText(palvelutiskinTulosteet.get("Kayttoaste"));
		kokonaisoleskeluaika.setText(palvelutiskinTulosteet.get("Asiakkaiden kokonaisoleskeluaika"));
	}

}
