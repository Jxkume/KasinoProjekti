package view;

import java.util.HashMap;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class RulettiPopUpKontrolleri {
	
	private static HashMap<String, String> ruletinTulosteet;
	
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
    
	public void initialize() {
		Platform.runLater(()-> naytaTulosteet());
	}
    
	public void setRuletinTulosteet(HashMap<String, String> tulosteet) {
		ruletinTulosteet = tulosteet;
	}
	
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