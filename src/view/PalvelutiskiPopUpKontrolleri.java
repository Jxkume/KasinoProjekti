package view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import simu.model.Palvelupiste;

public class PalvelutiskiPopUpKontrolleri {
	
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
    
    public PalvelutiskiPopUpKontrolleri() {
    }
    
	public void initialize() {
		palvelupisteNimi.setText("Palvelutiski");
	}

	public void setPalvellutAsiakkaat(int asiakkaat) {
		Platform.runLater(()-> palvellutAsiakkaat.setText(asiakkaat + " hello"));
	}
}
