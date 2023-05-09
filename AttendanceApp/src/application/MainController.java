package application;


import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainController {
    @FXML
    private Button quitBtn;

    @FXML
    private Button startBtn;
    
	Parent newScene;
	public void initialize() {	
		quitBtn.setOnAction(Event -> {
			Platform.exit();
		});
	}
	
	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException {
		   FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Menu.fxml"));
		    Parent newScene = loader.load();
		    Scene scene = new Scene(newScene);
		    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		    stage.setScene(scene);
		    stage.show();
	}

	
}
