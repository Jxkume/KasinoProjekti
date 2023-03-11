package main;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import simu.framework.Trace;
import simu.framework.Trace.Level;

/**
 * Luokka SimulaattoriMainille, jossa kaynnistetaan projektin kayttoliittyma
 * 
 * @author Tapio Humaljoki, Valtteri Kuitula, Jhon Rastrojo
 */
public class SimulaattoriMain extends Application {

    /** Kayttoliittyman paaikkuna */
    private Stage primaryStage;
    
    /** Kayttoliittyman pohja */
    private BorderPane rootLayout;

    /**
     * Luo kayttoliittyma-saikeen
     *
     * @param Stage-olio
     */
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("KasinoSimulaattori");
        alustaSimulaattorinPaaikkunanPohja();
        naytaSimulaattorinPaaikkuna();
    }
    
    /**
     * Alustaa kayttoliittyman paaikkunan pohja
     */
    public void alustaSimulaattorinPaaikkunanPohja() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SimulaattoriMain.class.getResource("/view/SimulaattorinPaaikkunaPohja.fxml"));
            rootLayout = (BorderPane) loader.load();
            
            Scene scene = new Scene(rootLayout);
            primaryStage.getIcons().add(new Image("/images/chip.png"));
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Lataa kayttoliittyman paaikkunan kayttajan nahtaville
     */
    public void naytaSimulaattorinPaaikkuna() {
    try {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(SimulaattoriMain.class.getResource("/view/SimulaattorinPaaikkuna.fxml"));
        AnchorPane SimulaattorinPaaikkuna = (AnchorPane) loader.load();
        rootLayout.setCenter(SimulaattorinPaaikkuna);
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
    
	/**
	 * Palauttaa kayttoliittyman paaikkunan
	 *
	 * @return Stage-olio
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

    /**
     * Kaynnistaa simulaattorin
     *
     * @param merkkijonolista
     */
    public static void main(String[] args) {
    	// Asetetaan Trace-level
    	Trace.setTraceLevel(Level.INFO);
    	// Kaynnistetaan simulaattori
        launch(args);
    }
    
}