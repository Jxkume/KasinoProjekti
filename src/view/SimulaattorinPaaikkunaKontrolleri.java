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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import main.SimulaattoriMain;
import simu.framework.IMoottori;
import simu.model.OmaMoottori;
import simu.model.Palvelupiste;

public class SimulaattorinPaaikkunaKontrolleri {

	private SimulaattoriMain simulaattoriMain;
    private IMoottori moottori;
    private ArrayList<ImageView> palvelutiskiJono, rulettiJono, blackjackJono, krapsJono, voittojenNostoPisteJono;
    
    @FXML
    private ImageView PJ1, PJ2, PJ3, PJ4, PJ5, PJ6, PJ7, PJ8, PJ9, PJ10;			// Palvelutiskin jonon kuvat
    @FXML
    private ImageView RJ1, RJ2, RJ3, RJ4, RJ5, RJ6, RJ7, RJ8, RJ9, RJ10;			// Ruletin jonon kuvat
    @FXML
    private ImageView BJ1, BJ2, BJ3, BJ4, BJ5, BJ6, BJ7, BJ8, BJ9, BJ10;			// Blackjackin jonon kuvat
    @FXML
    private ImageView KJ1, KJ2, KJ3, KJ4, KJ5, KJ6, KJ7, KJ8, KJ9, KJ10; 			// Krapsin jonon kuvat
    @FXML
    private ImageView VNJ1, VNJ2, VNJ3, VNJ4, VNJ5, VNJ6, VNJ7, VNJ8, VNJ9, VNJ10; 	// Voittojen nostopisteen jonon kuvat
    @FXML
	private Button kaynnistaButton, hidastaButton, nopeutaButton;
    @FXML
    private Button palvelutiskiButton, rulettiButton, blackjackButton, krapsButton, voittojenNostopisteButton;
    @FXML
    private TextField aikaTextField, viiveTextField;
    @FXML
    private GridPane tulokset, tekijat;
    @FXML
    private ChoiceBox<String> pelienKestoChoiceBox;
    @FXML
	private Label kokonaisaikatulosLabel, asiakasLkmLabel, keskimaarainenVietettyAikaLabel, kasinonTulosLabel;
    
    // Konstruktori
    public SimulaattorinPaaikkunaKontrolleri() {
    	
    	// Alustetaan tarvittavat JavaFX-komponentit ja muuttujat
    	hidastaButton = new Button();
    	nopeutaButton = new Button();
    	pelienKestoChoiceBox = new ChoiceBox<String>();
    	palvelutiskiJono = new ArrayList<>();
    	rulettiJono = new ArrayList<>();
    	blackjackJono = new ArrayList<>();
    	krapsJono = new ArrayList<>();
    	voittojenNostoPisteJono = new ArrayList<>();
    }
    
    // Tätä metodia kutsutaan konstruktorin jälkeen
	public void initialize() {
		
		// Asetetaan pelien kestot ChoiceBoxiin
		String[] kestot = {"Normaali", "Nopea", "Hidas"};
		pelienKestoChoiceBox.getItems().addAll(kestot);
		pelienKestoChoiceBox.setValue("Normaali");
		
		// Alustetaan napit
		kaynnistaButton.setDisable(false);
		hidastaButton.setDisable(true);
    	nopeutaButton.setDisable(true);
		
		// Asetetaan palvelutiskin jonon kuvat ArrayListiin
		palvelutiskiJono.add(PJ1);
		palvelutiskiJono.add(PJ2);
		palvelutiskiJono.add(PJ3);
		palvelutiskiJono.add(PJ4);
		palvelutiskiJono.add(PJ5);
		palvelutiskiJono.add(PJ6);
		palvelutiskiJono.add(PJ7);
		palvelutiskiJono.add(PJ8);
		palvelutiskiJono.add(PJ9);
		palvelutiskiJono.add(PJ10);
		
		// Asetetaan ruletin jonon kuvat ArrayListiin
		rulettiJono.add(RJ1);
		rulettiJono.add(RJ2);
		rulettiJono.add(RJ3);
		rulettiJono.add(RJ4);
		rulettiJono.add(RJ5);
		rulettiJono.add(RJ6);
		rulettiJono.add(RJ7);
		rulettiJono.add(RJ8);
		rulettiJono.add(RJ9);
		rulettiJono.add(RJ10);
		
		// Asetetaan Blackjackin jonon kuvat ArrayListiin
		blackjackJono.add(BJ1);
		blackjackJono.add(BJ2);
		blackjackJono.add(BJ3);
		blackjackJono.add(BJ4);
		blackjackJono.add(BJ5);
		blackjackJono.add(BJ6);
		blackjackJono.add(BJ7);
		blackjackJono.add(BJ8);
		blackjackJono.add(BJ9);
		blackjackJono.add(BJ10);
		
		// Asetetaan Krapsin jonon kuvat ArrayListiin
		krapsJono.add(KJ1);
		krapsJono.add(KJ2);
		krapsJono.add(KJ3);
		krapsJono.add(KJ4);
		krapsJono.add(KJ5);
		krapsJono.add(KJ6);
		krapsJono.add(KJ7);
		krapsJono.add(KJ8);
		krapsJono.add(KJ9);
		krapsJono.add(KJ10);
		
		// Asetetaan voittojen nostopisteen jonon kuvat ArrayListiin
		voittojenNostoPisteJono.add(VNJ1);
		voittojenNostoPisteJono.add(VNJ2);
		voittojenNostoPisteJono.add(VNJ3);
		voittojenNostoPisteJono.add(VNJ4);
		voittojenNostoPisteJono.add(VNJ5);
		voittojenNostoPisteJono.add(VNJ6);
		voittojenNostoPisteJono.add(VNJ7);
		voittojenNostoPisteJono.add(VNJ8);
		voittojenNostoPisteJono.add(VNJ9);
		voittojenNostoPisteJono.add(VNJ10);
		
		for (int i = 0; i < rulettiJono.size(); i++) {
			rulettiJono.get(i).setOpacity(0.25);
		}
		
		for (int i = 0; i < blackjackJono.size(); i++) {
			blackjackJono.get(i).setOpacity(0.25);
		}
		
		for (int i = 0; i < palvelutiskiJono.size(); i++) {
			palvelutiskiJono.get(i).setOpacity(0.25);
		}
		
		for (int i = 0; i < krapsJono.size(); i++) {
			krapsJono.get(i).setOpacity(0.25);
		}
		
		for (int i = 0; i < voittojenNostoPisteJono.size(); i++) {
			voittojenNostoPisteJono.get(i).setOpacity(0.25);
		}
		
	}
    
	public void kaynnistaSimulointi() {
		
		Alert alert = new Alert(AlertType.ERROR);
		// Tarkistetaan, että käyttäjä on syöttänyt simulointiajan ja viiveen
		if (aikaTextField.getText().isEmpty() && viiveTextField.getText().isEmpty()) {
			alert.setTitle("Simulointiaika ja viive puuttuvat!");
	        alert.setHeaderText("Ole hyvä ja aseta simulointiaika ja viive käynnistääksesi simulaattorin.");
	        alert.showAndWait();
		} else if (aikaTextField.getText().isEmpty()) {
	        alert.setTitle("Simulointiaika puuttuu!");
	        alert.setHeaderText("Ole hyvä ja aseta simulointiaika käynnistääksesi simulaattorin.");
	        alert.showAndWait();
		} else if (viiveTextField.getText().isEmpty()) {
	        alert.setTitle("Viive puuttuu!");
	        alert.setHeaderText("Ole hyvä ja aseta viive käynnistääksesi simulaattorin.");
	        alert.showAndWait();
		} else {
			// Tarkistetaan, että arvot eivät ole negatiivisia
			if (Integer.parseInt(aikaTextField.getText()) < 0) {
		        alert.setTitle("Negatiivinen simulointiaika!");
		        alert.setHeaderText("Simulointiaika ei voi olla negatiivinen.");
		        alert.showAndWait();
			} else if (Integer.parseInt(viiveTextField.getText()) < 0) {
		        alert.setTitle("Negatiivinen viive!");
		        alert.setHeaderText("Viive ei voi olla negatiivinen.");
		        alert.showAndWait();
			} else if (Integer.parseInt(viiveTextField.getText()) == 0) {
		        alert.setTitle("Ei-kelvollinen arvo!");
		        alert.setHeaderText("Viive ei voi olla arvoltaan nolla.");
		        alert.showAndWait();
			} else {
				// Simulaatio voidaan käynnistää
				moottori = new OmaMoottori(this); // säie
				moottori.setSimulointiaika(getAika());
				moottori.setViive(getViive());
				((Thread)moottori).start();
				// Nappia voi painaa vain kerran
				kaynnistaButton.setDisable(true);
				// Käyttäjä voi nyt hidastaa tai nopeuttaa simulaatiota
		    	hidastaButton.setDisable(false);
		    	nopeutaButton.setDisable(false);
		    	// Tulokset-GridPane asetetaan pois näkyvistä
		    	tulokset.setVisible(false);
			}
		}
		
	}

	public void visualisoiJono(Palvelupiste palvelupiste) {
		
		int max;
		int loput;
		if (palvelupiste.getJono().size() > 10) {
			max = 10;
			loput = max;
		} else {
			max = palvelupiste.getJono().size();
			loput = 10 - max;
		}
		
		if (palvelupiste.getNimi().equals("Palvelutiski")) {
			for (int i = 0; i < max; i++) {
				palvelutiskiJono.get(i).setOpacity(1);
			}
			if (max < 10) {
				for (int i = max; i < (max + loput); i++) {
					palvelutiskiJono.get(i).setOpacity(0.25);
				}
			}
		} else if (palvelupiste.getNimi().equals("Ruletti")) {
			for (int i = 0; i < max; i++) {
				rulettiJono.get(i).setOpacity(1);
			}
			if (max < 10) {
				for (int i = max; i < (max + loput); i++) {
					rulettiJono.get(i).setOpacity(0.25);
				}
			}
		} else if (palvelupiste.getNimi().equals("Blackjack")) {
				for (int i = 0; i < max; i++) {
					blackjackJono.get(i).setOpacity(1);
				}
				if (max < 10) {
					for(int i = max; i < (max + loput); i++) {
						blackjackJono.get(i).setOpacity(0.25);
				}
			}
		} else if (palvelupiste.getNimi().equals("Kraps")) {
			for (int i = 0; i < max; i++) {
				krapsJono.get(i).setOpacity(1);
			}
			if (max < 10) {
				for(int i = max; i < (max + loput); i++) {
					krapsJono.get(i).setOpacity(0.25);
				}
			}
		} else if (palvelupiste.getNimi().equals("Voittojen nostopiste")) {
			for (int i = 0; i < max; i++) {
				voittojenNostoPisteJono.get(i).setOpacity(1);
			}
			if (max < 10) {
				for (int i = max; i < (max + loput); i++) {
					voittojenNostoPisteJono.get(i).setOpacity(0.25);
				}
			}
		}
		
	}
	
	// Hidastetaan moottorisäiettä
	public void hidasta() {
		// Lisätään viiveeseen 10
		moottori.setViive((long)(moottori.getViive() + 10));
		viiveTextField.setText(Long.toString(moottori.getViive()));
	}
	
	// nopeutetaan moottorisäiettä
	public void nopeuta() {
		// Varmistetaan ettei viive voi mennä negatiiviseksi
		if (moottori.getViive() < 10) {
			moottori.setViive(1);
			viiveTextField.setText("1");
		} else {
			// Vähennetään viiveestä 10
			moottori.setViive((long)(moottori.getViive() - 10));
			viiveTextField.setText(Long.toString(moottori.getViive()));
		}
	}
	
	public void naytaGridPane() {
		Platform.runLater(()-> tekijat.setVisible(false));
		Platform.runLater(()-> tulokset.setVisible(true));
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
	
	public void paivitaTulos(int tulos) {
		Platform.runLater(()-> kasinonTulosLabel.setText(tulos + " €"));
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
			stage.getIcons().add(new Image("/images/Palvelutiski.png"));
			stage.setTitle("Palvelutiski");
			stage.setScene(new Scene(scene));
			stage.setResizable(false);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void naytaRulettiPopUp(ActionEvent event) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("/view/RulettiPopUp.fxml"));
			Parent scene = fxmlLoader.load();
			Stage stage = new Stage();
			stage.getIcons().add(new Image("/images/RulettiPoyta.png"));
			stage.setTitle("Ruletti");
			stage.setScene(new Scene(scene));
			stage.setResizable(false);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void naytaBlackjackPopUp(ActionEvent event) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("/view/BlackjackPopUp.fxml"));
			Parent scene = fxmlLoader.load();
			Stage stage = new Stage();
			stage.getIcons().add(new Image("/images/Blackjack.png"));
			stage.setTitle("Blackjack");
			stage.setScene(new Scene(scene));
			stage.setResizable(false);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void naytaKrapsPopUp(ActionEvent event) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("/view/KrapsPopUp.fxml"));
			Parent scene = fxmlLoader.load();
			Stage stage = new Stage();
			stage.getIcons().add(new Image("/images/KrapsPoyta.png"));
			stage.setTitle("Kraps");
			stage.setScene(new Scene(scene));
			stage.setResizable(false);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void naytaVoittojenNostopistePopUp(ActionEvent event) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("/view/VoittojenNostopistePopUp.fxml"));
			Parent scene = fxmlLoader.load();
			Stage stage = new Stage();
			stage.getIcons().add(new Image("/images/Voittotiski.png"));
			stage.setTitle("Voittojen nostopiste");
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
     * @param simulaattoriMain
     */
    public void setSimulaattoriMain(SimulaattoriMain simulaattoriMain) {
        this.simulaattoriMain = simulaattoriMain;
    }
    
	public double getAika(){
		return Double.parseDouble(aikaTextField.getText());
	}
	
	public long getViive(){
		return Long.parseLong(viiveTextField.getText());
	}
	
    public String getPelienKesto() {
    	return pelienKestoChoiceBox.getValue();
    }
	
}