package view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import main.SimulaattoriMain;
import simu.framework.IMoottori;
import simu.framework.Kello;
import simu.model.OmaMoottori;

public class SimulaattorinPaaikkunaKontrolleri {

	private SimulaattoriMain simulaattoriMain;
    private IMoottori moottori;
    
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
	private Button kaynnistaButton;
    @FXML
	private Button hidastaButton;
    @FXML
	private Button nopeutaButton;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public SimulaattorinPaaikkunaKontrolleri() {
    	
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
				moottori = new OmaMoottori(this);
				moottori.setSimulointiaika(getAika());
				moottori.setViive(getViive());
				//ui.getVisualisointi().tyhjennaNaytto();
				((Thread)moottori).start();
				// Nappia voi painaa vain kerran
				kaynnistaButton.setDisable(true);
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