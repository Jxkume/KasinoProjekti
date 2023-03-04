package main;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import simu.framework.Trace;
import simu.framework.Trace.Level;

public class SimulaattoriMain extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("KasinoSimulaattori");
        alustaSimulaattorinPaaikkunanPohja();
        naytaSimulaattorinPaaikkuna();
    }
    
    /**
     * Alustetaan simulaattorin pääikkunan pohja.
     */
    public void alustaSimulaattorinPaaikkunanPohja() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SimulaattoriMain.class.getResource("/view/SimulaattorinPaaikkunaPohja.fxml"));
            rootLayout = (BorderPane) loader.load();
            
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Näytetään simulaattorin pääikkuna käyttäjälle.
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
     * Näytetään palvelupisteen pop-up käyttäjälle.
     */
    public void naytaPalvelupisteenPopUp() {
    try {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/view/PalvelupistePopUp.fxml"));
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
    
	/**
	 * Returns the main stage.
	 * @return
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

    public static void main(String[] args) {
    	// Asetetaan Trace-level
    	Trace.setTraceLevel(Level.INFO);
    	// Käynnistetään simulaattori
        launch(args);
    }
    
}