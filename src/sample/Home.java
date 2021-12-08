package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class Home {
    @FXML
    public Button game;
    @FXML
    public ComboBox algo;
    @FXML
    public TextField input_k;

    @FXML
    public void HandleGame(ActionEvent actionEvent) throws IOException {
        if(input_k.getLength() ==0){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Check");
            alert.setHeaderText("Type the number of Diff. & choose your algorithm");
            alert.showAndWait();
        }else {

            game.setDisable(true);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
            Parent root = loader.load();

            Stage stage2 = (Stage) game.getScene().getWindow();
            stage2.close();

            Controller sceneCont = loader.getController();
            boolean x=false;
            if(algo.getValue().equals("Minmax with pruning")){
                x=true;
            }
            sceneCont.construct(Integer.getInteger(input_k.getText()),x,true);
            sceneCont.initialize();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Connect 4");
            stage.show();
        }

    }
}
