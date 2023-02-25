package view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.SimulaattoriMain;
import simu.framework.IMoottori;
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
		moottori = new OmaMoottori(this); // luodaan uusi moottorisäie jokaista simulointia varten
		moottori.setSimulointiaika(getAika());
		moottori.setViive(getViive());
		//ui.getVisualisointi().tyhjennaNaytto();
		((Thread)moottori).start();
	}
	
	public void hidasta() { // hidastetaan moottorisäiettä
		moottori.setViive((long)(moottori.getViive() + 10));
	}
	
	public void nopeuta() { // nopeutetaan moottorisäiettä
		moottori.setViive((long)(moottori.getViive() - 10));
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