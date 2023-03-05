package view;

import java.io.IOException;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
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
    protected PalvelutiskiPopUpKontrolleri kontrolleri = new PalvelutiskiPopUpKontrolleri();
    
  //Jonojen visualisointiin liittyviä asioita
    @FXML
    private Polygon RJ1, RJ2, RJ3, RJ4, BJ1, BJ2, BJ3, BJ4, KJ1, KJ2, KJ3, KJ4, VNPJ1, VNPJ2, VNPJ3, VNPJ4;
    @FXML
    private ImageView PTJ1, PTJ2, PTJ3, PTJ4;
    
    private ArrayList<ImageView> palvelutiskiJono = new ArrayList<>();
    private ArrayList<Polygon> rulettiJono = new ArrayList<>();
    private ArrayList<Polygon> blackjackJono = new ArrayList<>();
    private ArrayList<Polygon> krapsJono = new ArrayList<>();
    private ArrayList<Polygon> voittojenNostoPisteJono = new ArrayList<>();
    
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
    private Button palvelutiskiButton;
    @FXML
    private Button rulettiButton;
    @FXML
    private Button blackjackButton;
    @FXML
    private Button krapsButton;
    @FXML
    private Button voittojenNostopisteButton;
    
    @FXML
    private ChoiceBox<String> jakaumatChoiceBox;
    
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
		
		// Jonojen visualisoinnin komponentteja
		palvelutiskiJono.add(PTJ1);
		palvelutiskiJono.add(PTJ2);
		palvelutiskiJono.add(PTJ3);
		palvelutiskiJono.add(PTJ4);
		
		rulettiJono.add(RJ1);
		rulettiJono.add(RJ2);
		rulettiJono.add(RJ3);
		rulettiJono.add(RJ4);
		
		blackjackJono.add(BJ1);
		blackjackJono.add(BJ2);
		blackjackJono.add(BJ3);
		blackjackJono.add(BJ4);
		
		krapsJono.add(KJ1);
		krapsJono.add(KJ2);
		krapsJono.add(KJ3);
		krapsJono.add(KJ4);
		
		voittojenNostoPisteJono.add(VNPJ1);
		voittojenNostoPisteJono.add(VNPJ2);
		voittojenNostoPisteJono.add(VNPJ3);
		voittojenNostoPisteJono.add(VNPJ4);
	}
	
	public void visualisoiJono(Palvelupiste palvelupiste) {
		
		int max;
		int loput;
		if (palvelupiste.getJono().size() > 4) {
			max = 4;
			loput = max;
		} else {
			max = palvelupiste.getJono().size();
			loput = 4 - max;
		}
		
		if(palvelupiste.getNimi().equals("Palvelutiski")) {
			for(int i = 0; i < max; i++) {
				palvelutiskiJono.get(i).setOpacity(1);
			}
			if(max < 4) {
				for(int i = max; i < (max + loput); i++) {
					palvelutiskiJono.get(i).setOpacity(0.25);
				}
			}
		} else if(palvelupiste.getNimi().equals("Ruletti")) {
			for(int i = 0; i < max; i++) {
				rulettiJono.get(i).setOpacity(1);
			}
			if(max < 4) {
				for(int i = max; i < (max + loput); i++) {
					rulettiJono.get(i).setOpacity(0.25);
				}
			}
		} else if(palvelupiste.getNimi().equals("Blackjack")) {
				for(int i = 0; i < max; i++) {
					blackjackJono.get(i).setOpacity(1);
				}
				if(max < 4) {
					for(int i = max; i < (max + loput); i++) {
						blackjackJono.get(i).setOpacity(0.25);
				}
			}
		} else if(palvelupiste.getNimi().equals("Kraps")) {
			for(int i = 0; i < max; i++) {
				krapsJono.get(i).setOpacity(1);
			}
			if(max < 4) {
				for(int i = max; i < (max + loput); i++) {
					krapsJono.get(i).setOpacity(0.25);
				}
			}
		} else if(palvelupiste.getNimi().equals("Voittojen nostopiste")) {
			for(int i = 0; i < max; i++) {
				voittojenNostoPisteJono.get(i).setOpacity(1);
			}
			if(max < 4) {
				for(int i = max; i < (max + loput); i++) {
					voittojenNostoPisteJono.get(i).setOpacity(0.25);
				}
			}
		}
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
	
	public void naytaPalvelutiskiPopUp(ActionEvent event) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("/view/PalvelutiskiPopUp.fxml"));
			Parent scene = fxmlLoader.load();
			Stage stage = new Stage();
			stage.setTitle("Palvelupiste");
			stage.setScene(new Scene(scene));
			stage.setResizable(false);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
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