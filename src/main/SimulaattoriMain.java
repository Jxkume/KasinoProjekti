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
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SimulaattoriMain.class.getResource("/view/SimulaattorinPaaikkunaPohja.fxml"));
            rootLayout = (BorderPane) loader.load();
            
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
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
        // Load person overview.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(SimulaattoriMain.class.getResource("/view/SimulaattorinPaaikkuna.fxml"));
        AnchorPane SimulaattorinPaaikkuna = (AnchorPane) loader.load();
        
        // Set person overview into the center of root layout.
        rootLayout.setCenter(SimulaattorinPaaikkuna);

        // Give the controller access to the main app.
        //SimulatorOverviewController controller = loader.getController();
        //controller.setSimulatorMain(this);

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