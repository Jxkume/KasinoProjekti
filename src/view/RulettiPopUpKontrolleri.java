package view;

import java.util.HashMap;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Luokka RulettiPopUp.fxml-tiedoston kontrollerille
 * 
 * @author Tapio Humaljoki, Valtteri Kuitula, Jhon Rastrojo
 */
public class RulettiPopUpKontrolleri {
	
	/** HashMap-lista Ruletti-palvelupisteen tulosteista */
	private static HashMap<String, String> ruletinTulosteet;
	
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
    public RulettiPopUpKontrolleri() {
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
	 * @param HashMap-lista, jossa on Ruletti-palvelupisteen tulosteet
	 */
	public void setRuletinTulosteet(HashMap<String, String> tulosteet) {
		ruletinTulosteet = tulosteet;
	}
	
	/**
	 * Asettaa kaikki tulosteet pop-up-ikkunan JavaFX-komponentteihin
	 */
	public void naytaTulosteet() {
		palvelupisteNimi.setText("Yhteenveto ruletista");
		palvellutAsiakkaat.setText(ruletinTulosteet.get("Palveltuja asiakkaita yhteensä"));
		keskimaarainenPalveluaika.setText(ruletinTulosteet.get("Asiakkaiden keskimääräinen palveluaika"));
		keskimaarainenJononpituus.setText(ruletinTulosteet.get("Keskimääräinen jononpituus"));
		keskimaarainenLapimenoaika.setText(ruletinTulosteet.get("Asiakkaiden keskimääräinen läpimenoaika"));
		suoritusteho.setText(ruletinTulosteet.get("Suoritusteho"));
		aktiiviaika.setText(ruletinTulosteet.get("Aktiiviaika"));
		kayttoaste.setText(ruletinTulosteet.get("Käyttöaste"));
		kokonaisoleskeluaika.setText(ruletinTulosteet.get("Asiakkaiden kokonaisoleskeluaika"));
	}

}