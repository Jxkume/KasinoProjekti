package view;

import java.util.HashMap;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import simu.model.Palvelupiste;

public class PalvelutiskiPopUpKontrolleri extends SimulaattorinPaaikkunaKontrolleri {
	
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
    private Label asiakkaidenKokonaisoleskeluaika;
    
    private HashMap<String, String> palvelutiskinTulosteet;
    
    public PalvelutiskiPopUpKontrolleri() {
    	palvellutAsiakkaat = new Label();
    }
    
    public void initialize() {
    	setPalvelutiskinTulosteet();
    	naytaPalveltujaAsiakkaitaYhteensa();
    }
    
    public void setPalvelutiskinTulosteet() {
    	this.palvelutiskinTulosteet = getPalvelutiskinTulosteet();
    }

    public void naytaPalveltujaAsiakkaitaYhteensa() {
    	String tuloste = palvelutiskinTulosteet.get("Palveltuja asiakkaita yhteensä");
    	palvellutAsiakkaat.setText(tuloste);
    }
    
}
