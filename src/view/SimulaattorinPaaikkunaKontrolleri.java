package view;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import eduni.distributions.Negexp;
import eduni.distributions.Normal;
import eduni.distributions.Uniform;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import main.SimulaattoriMain;
import simu.framework.IMoottori;
import simu.model.OmaMoottori;
import simu.model.Palvelupiste;

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
	private Label asiakasLkmLabel;
    @FXML
	private Label keskimaarainenVietettyAikaLabel;
    @FXML
	private Button kaynnistaButton;
    @FXML
	private Button hidastaButton;
    @FXML
	private Button nopeutaButton;
    @FXML
    private Label kasinonTulos;
    @FXML
    private GridPane tulokset;
    @FXML
    private Polygon PTJ1;
    @FXML
    private Label PTJID1;
    @FXML
    private Polygon PTJ2;
    @FXML
    private Label PTJID2;
    @FXML
    private Polygon PTJ3;
    @FXML
    private Label PTJID3;
    @FXML
    private Polygon PTJ4;
    @FXML
    private Label PTJID4;
    
    // Nämä liittyvät palvelupisteen pop-up-ikkunaan - Valdo
    @FXML
    protected Button palvelutiskiButton;
    @FXML
    protected Button rulettiButton;
    @FXML
    protected Button blackjackButton;
    @FXML
    protected Button krapsButton;
    @FXML
    protected Button voittojenNostopisteButton;
    @FXML
    private ChoiceBox<String> jakaumatChoiceBox;
    
    private HashMap<String, String> palvelutiskinTulosteet;
    
    public String getJakauma() {
    	return jakaumatChoiceBox.getValue();
    }
    
    public SimulaattorinPaaikkunaKontrolleri() {
    	// Alustetaan tarvittavat JavaFX-komponentit ja muut muuttujat
    	hidastaButton = new Button();
    	nopeutaButton = new Button();
    	jakaumatChoiceBox = new ChoiceBox<String>();
    }
    
    // Tätä metodia kutsutaan konstruktorin jälkeen
	public void initialize() {
		String[] jakaumat = {"Normaali jakauma", "Eksponenttijakauma", "Tasainen jakauma"};
		jakaumatChoiceBox.getItems().addAll(jakaumat);
		jakaumatChoiceBox.setValue("Normaali jakauma");
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
	
	public void naytaKeskimaarainenVietettyAika(double aika) {
		Platform.runLater(()-> keskimaarainenVietettyAikaLabel.setText((String.format("%.02f", aika))));
	}
	
	public void naytaGridPane() {
		Platform.runLater(()-> tulokset.setVisible(true));
	}
	
	public void paivitaTulos(int tulos) {
		Platform.runLater(()-> kasinonTulos.setText(tulos + " €"));
	}
	
	public void naytaTiedot() {
		// Asetetaan hidasta- ja nopeuta-napit käyttökelvottomaksi, kun saadaan simulaation tulokset
		Platform.runLater(()-> hidastaButton.setDisable(true));
		Platform.runLater(()-> nopeutaButton.setDisable(true));
		// Asetetaan jokaisen palvelupisteen nappi käytettäväksi
		Platform.runLater(()-> palvelutiskiButton.setDisable(false));
		Platform.runLater(()-> rulettiButton.setDisable(false));
		Platform.runLater(()-> blackjackButton.setDisable(false));
		Platform.runLater(()-> krapsButton.setDisable(false));
		Platform.runLater(()-> voittojenNostopisteButton.setDisable(false));
	}
	
	public synchronized void visualisoiJono(Palvelupiste palvelupiste) {
		if (palvelupiste.getJono().size() >= 1) {
			Platform.runLater(()->PTJ1.setOpacity(1));
			Platform.runLater(()->PTJID1.setText(Integer.toString(palvelupiste.getJono().get(0).getId())));
		} else {
			Platform.runLater(()->PTJ1.setOpacity(0.25));
			Platform.runLater(()->PTJID1.setText(null));
		}
		
		if (palvelupiste.getJono().size() >= 2) {
			Platform.runLater(()->PTJ2.setOpacity(1));
			Platform.runLater(()->PTJID2.setText(Integer.toString(palvelupiste.getJono().get(1).getId())));
		} else {
			Platform.runLater(()->PTJ2.setOpacity(0.25));
			Platform.runLater(()->PTJID2.setText(null));
		}
		
		if (palvelupiste.getJono().size() >= 3) {
			Platform.runLater(()->PTJ3.setOpacity(1));
			Platform.runLater(()->PTJID3.setText(Integer.toString(palvelupiste.getJono().get(2).getId())));
		} else {
			Platform.runLater(()->PTJ3.setOpacity(0.25));
			Platform.runLater(()->PTJID3.setText(null));
		}
		
		if (palvelupiste.getJono().size() >= 4) {
			Platform.runLater(()->PTJ4.setOpacity(1));
			Platform.runLater(()->PTJID4.setText(Integer.toString(palvelupiste.getJono().get(3).getId())));
		} else {
			Platform.runLater(()->PTJ4.setOpacity(0.25));
			Platform.runLater(()->PTJID4.setText(null));
		}
	}
	
	public void naytaPalvelutiskiPopUp(ActionEvent event) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("/view/PalvelutiskiPopUp.fxml"));
			Scene scene = new Scene(fxmlLoader.load());
			Stage stage = new Stage();
			stage.setTitle("Palvelupiste");
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setPalvelutiskinTulosteet(HashMap<String, String> palvelutiskinTulosteet) {
		this.palvelutiskinTulosteet = palvelutiskinTulosteet;
	}
	
	public HashMap<String, String> getPalvelutiskinTulosteet() {
		return palvelutiskinTulosteet;
	}

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setSimulaattoriMain(SimulaattoriMain simulaattoriMain) {
        this.simulaattoriMain = simulaattoriMain;
    }
    
	public double getAika(){
		return Double.parseDouble(aika.getText());
	}
	
	public long getViive(){
		return Long.parseLong(viive.getText());
	}
	
}