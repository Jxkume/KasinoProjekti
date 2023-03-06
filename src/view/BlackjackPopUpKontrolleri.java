package view;

import java.util.HashMap;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class BlackjackPopUpKontrolleri {
	
	private static HashMap<String, String> blackjackinTulosteet;
	
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
    
    public BlackjackPopUpKontrolleri() {
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
    
	public void initialize() {
		Platform.runLater(()-> naytaTulosteet());
	}
    
	public void setBlackjackinTulosteet(HashMap<String, String> tulosteet) {
		blackjackinTulosteet = tulosteet;
	}
	
	public void naytaTulosteet() {
		palvelupisteNimi.setText("Yhteenveto Blackjackistä");
		palvellutAsiakkaat.setText(blackjackinTulosteet.get("Palveltuja asiakkaita yhteensä"));
		keskimaarainenPalveluaika.setText(blackjackinTulosteet.get("Asiakkaiden keskimääräinen palveluaika"));
		keskimaarainenJononpituus.setText(blackjackinTulosteet.get("Keskimääräinen jononpituus"));
		keskimaarainenLapimenoaika.setText(blackjackinTulosteet.get("Asiakkaiden keskimääräinen läpimenoaika"));
		suoritusteho.setText(blackjackinTulosteet.get("Suoritusteho"));
		aktiiviaika.setText(blackjackinTulosteet.get("Aktiiviaika"));
		kayttoaste.setText(blackjackinTulosteet.get("Käyttöaste"));
		kokonaisoleskeluaika.setText(blackjackinTulosteet.get("Asiakkaiden kokonaisoleskeluaika"));
	}

}