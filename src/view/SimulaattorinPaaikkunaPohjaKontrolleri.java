package view;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class SimulaattorinPaaikkunaPohjaKontrolleri {

	public void naytaProsessikaavio(ActionEvent event) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("/view/Prosessikaavio.fxml"));
			Parent scene = fxmlLoader.load();
			Stage stage = new Stage();
			stage.getIcons().add(new Image("/images/Prosessikaavio.png"));
			stage.setTitle("Prosessikaavio");
			stage.setScene(new Scene(scene));
			stage.setResizable(false);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
