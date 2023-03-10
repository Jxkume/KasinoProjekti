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

//
/**
 * Luokka SimulaattoriMain käynnistää käyttöliittymän
 * 
 * @author Tapio Humaljoki, Valtteri Kuitula, Jhon Rastrojo
 */
public class SimulaattoriMain extends Application {

    /** Käyttöliittymän pääikkuna */
    private Stage primaryStage;
    
    /** Käyttöliittymän pohjan */
    private BorderPane rootLayout;

    /**
     * Luo käyttöliittymä säikeen
     *
     * @param Stage, primaryStage eli käyttöliittymän pääikkunan
     */
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("KasinoSimulaattori");
        alustaSimulaattorinPaaikkunanPohja();
        naytaSimulaattorinPaaikkuna();
    }
    
    /**
     * Alustaa simulaattorin pääikkunan pohja
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
     * Metodi jolla saadaan käyttöliittymän pääikkunan näkyville
     * 
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
	 * Palauttaa käyttöliittymän pääikkunan
	 *
	 * @return primaryStage eli käyttöliittymän pääikkunan
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

    /**
     * Käynnistää simulaattori
     *
     * @param String[] args, eli komentorivin argumentteja
     */
    public static void main(String[] args) {
    	// Asetetaan Trace-level
    	Trace.setTraceLevel(Level.INFO);
    	// Käynnistetään simulaattori
        launch(args);
    }
    
}