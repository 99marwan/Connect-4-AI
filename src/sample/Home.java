package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Home {
    @FXML
    public Button game;

    @FXML
    public void HandleGame(ActionEvent actionEvent) throws IOException {
        game.setDisable(true);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();

        Stage stage2 = (Stage) game.getScene().getWindow();
        stage2.close();

        Controller sceneCont = loader.getController();
        sceneCont.initialize();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Connect 4");
        stage.show();


    }
}
