package view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Alert.AlertType;
import main.SimulaattoriMain;
import simu.framework.IMoottori;
import simu.framework.Kello;
import simu.model.OmaMoottori;

public class SimulaattorinPaaikkunaKontrolleri {

	private SimulaattoriMain simulaattoriMain;
    private IMoottori moottori;
    private OmaMoottori omaMoottori;
    
    @FXML
    private TextField aika;
    @FXML
	private TextField viive;
    @FXML
	private Label kokonaisaika;
    @FXML
	private Label aikaLabel;
    @FXML
	private Label viiveLabel;
    @FXML
	private Label kokonaisaikatulosLabel;
    @FXML
	private Label asiakasLkmLabel;
    @FXML
	private Label voittoLabel;
    @FXML
	private Button kaynnistaButton;
    @FXML
	private Button hidastaButton;
    @FXML
	private Button nopeutaButton;
    @FXML
    private GridPane tulokset;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public SimulaattorinPaaikkunaKontrolleri() {
    	
    }
    
    @FXML
    private void initialize() {
    	// Asetetaan hidasta- ja nopeuta-napit käyttökelvottomaksi ennen kuin simulaatio ajetaan
    	hidastaButton.setDisable(true);
    	nopeutaButton.setDisable(true);
    }
    
	public void kaynnistaSimulointi() {
		Alert alert = new Alert(AlertType.ERROR);
		// Tarkistetaan, että käyttäjä on syöttänyt simulointiajan ja viiveen
		if (aika.getText().isEmpty() && viive.getText().isEmpty()) {
			alert.setTitle("Simulointiaika ja viive puuttuvat!");
	        alert.setHeaderText("Ole hyvä ja aseta simulointiaika ja viive käynnistääksesi simulaattorin.");
	        alert.showAndWait();
		} else if (aika.getText().isEmpty()) {
	        alert.setTitle("Simulointiaika puuttuu!");
	        alert.setHeaderText("Ole hyvä ja aseta simulointiaika käynnistääksesi simulaattorin.");
	        alert.showAndWait();
		} else if (viive.getText().isEmpty()) {
	        alert.setTitle("Viive puuttuu!");
	        alert.setHeaderText("Ole hyvä ja aseta viive käynnistääksesi simulaattorin.");
	        alert.showAndWait();
		} else {
			// Tarkistetaan, että arvot eivät ole negatiivisia
			if (Integer.parseInt(aika.getText()) < 0) {
		        alert.setTitle("Negatiivinen simulointiaika!");
		        alert.setHeaderText("Simulointiaika ei voi olla negatiivinen.");
		        alert.showAndWait();
			} else if (Integer.parseInt(viive.getText()) < 0) {
		        alert.setTitle("Negatiivinen viive!");
		        alert.setHeaderText("Viive ei voi olla negatiivinen.");
		        alert.showAndWait();
			} else {
				// Simulaatio voidaan käynnistää
				moottori = new OmaMoottori(this); // säie
				moottori.setSimulointiaika(getAika());
				moottori.setViive(getViive());
				//ui.getVisualisointi().tyhjennaNaytto();
				((Thread)moottori).start();
				// Nappia voi painaa vain kerran
				kaynnistaButton.setDisable(true);
				// Käyttäjä voi nyt hidastaa tai nopeuttaa simulaatiota
		    	hidastaButton.setDisable(false);
		    	nopeutaButton.setDisable(false);
			}
		}
	}
	
	public void hidasta() { // hidastetaan moottorisäiettä
		// Lisätään viiveeseen 10
		moottori.setViive((long)(moottori.getViive() + 10));
	}
	
	public void nopeuta() { // nopeutetaan moottorisäiettä
		// Varmistetaan ettei viive voi mennä negatiiviseksi
		if (moottori.getViive() < 10) {
			moottori.setViive(0);
		} else {
			// Vähennetään viiveestä 10
			moottori.setViive((long)(moottori.getViive() - 10));
		}
	}
	
	public void naytaLoppuaika(double aika) {
		Platform.runLater(()-> kokonaisaikatulosLabel.setText(String.format("%.02f", aika))); 
	}
	
	public void naytaAsiakasLkm(int asiakasLkm) {
		Platform.runLater(()-> asiakasLkmLabel.setText(Integer.toString(asiakasLkm) + " asiakasta"));
	}
	
	public void naytaKasinonTekemaVoitto(int voitto) {
		Platform.runLater(()-> voittoLabel.setText(voitto + " €"));
	}
	
	public void naytaGridPane() {
		Platform.runLater(()-> tulokset.setVisible(true));
	}

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setSimulatorMain(SimulaattoriMain simulaattoriMain) {
        this.simulaattoriMain = simulaattoriMain;
    }
    
	public double getAika(){
		return Double.parseDouble(aika.getText());
	}
	
	public long getViive(){
		return Long.parseLong(viive.getText());
	}
	
}