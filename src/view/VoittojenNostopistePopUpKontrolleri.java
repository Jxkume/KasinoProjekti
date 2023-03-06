package view;

import java.util.HashMap;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class VoittojenNostopistePopUpKontrolleri {
	
	private static HashMap<String, String> voittojenNostopisteenTulosteet;
	
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
    
    public VoittojenNostopistePopUpKontrolleri() {
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
    
	public void setVoittojenNostopisteenTulosteet(HashMap<String, String> tulosteet) {
		voittojenNostopisteenTulosteet = tulosteet;
	}
	
	public void naytaTulosteet() {
		palvelupisteNimi.setText("Yhteenveto voittojen nostopisteestä");
		palvellutAsiakkaat.setText(voittojenNostopisteenTulosteet.get("Palveltuja asiakkaita yhteensä"));
		keskimaarainenPalveluaika.setText(voittojenNostopisteenTulosteet.get("Asiakkaiden keskimääräinen palveluaika"));
		keskimaarainenJononpituus.setText(voittojenNostopisteenTulosteet.get("Keskimääräinen jononpituus"));
		keskimaarainenLapimenoaika.setText(voittojenNostopisteenTulosteet.get("Asiakkaiden keskimääräinen läpimenoaika"));
		suoritusteho.setText(voittojenNostopisteenTulosteet.get("Suoritusteho"));
		aktiiviaika.setText(voittojenNostopisteenTulosteet.get("Aktiiviaika"));
		kayttoaste.setText(voittojenNostopisteenTulosteet.get("Käyttöaste"));
		kokonaisoleskeluaika.setText(voittojenNostopisteenTulosteet.get("Asiakkaiden kokonaisoleskeluaika"));
	}

}