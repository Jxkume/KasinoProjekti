package view;

import javafx.fxml.FXML;
import main.SimulatorMain;

public class SimulatorOverviewController {


    // Reference to the main application.
    private SimulatorMain simulatorMain;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public SimulatorOverviewController() {
    	
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {

    }

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setSimulatorMain(SimulatorMain simulatorMain) {
        this.simulatorMain = simulatorMain;
    }
}