package sample;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class Controller<ColCount> implements Initializable {

    @FXML
    Pane pane;
    @FXML
    Pane discroot;
    @FXML
    Rectangle rect1;
    @FXML
    Rectangle rect2;
    @FXML
    Rectangle rect3;
    @FXML
    Rectangle rect4;
    @FXML
    Rectangle rect5;
    @FXML
    Rectangle rect6;
    @FXML
    Rectangle rect7;
    @FXML
    Polygon pol1;
    @FXML
    Polygon pol2;
    @FXML
    Polygon pol3;
    @FXML
    Polygon pol4;
    @FXML
    Polygon pol5;
    @FXML
    Polygon pol6;
    @FXML
    Polygon pol7;



    public int colCount[] = {0,0,0,0,0,0,0};
    private boolean redTurn = true;
    private int rows = 6;
    private int cols = 7;



    private static class Disc extends Circle{
        boolean red;
        public Disc(boolean red){
            super(40, red ? Color.RED : Color.YELLOW);
            this.red = red;
            setCenterX(40);
            setCenterY(40);
            setLayoutX(0);
            setLayoutY(0);
        }
    }

    public void showRect(){
        rect1.setFill(Color.rgb(255,110,31, 0.3));
        pol1.setFill(Color.DODGERBLUE);
        pol1.setStroke(Color.BLACK);
    }
    public void hideRect(){
        rect1.setFill(Color.TRANSPARENT);
        pol1.setFill(Color.TRANSPARENT);
        pol1.setStroke(Color.TRANSPARENT);
    }

    public void addDisc(){
        if(colCount[0] < 6){
            Disc disc = new Disc(redTurn);
            disc.setTranslateX((0 * 85) + 20);
            discroot.getChildren().add(disc);
            TranslateTransition animation = new TranslateTransition(Duration.seconds(0.5),disc);
            animation.setToY(((5 - colCount[0]) * 85) + 20);
            colCount[0]++;
            redTurn = !redTurn;
            animation.play();
        }
    }
    public void showRect2(){
        rect2.setFill(Color.rgb(255,110,31, 0.3));
        pol2.setFill(Color.DODGERBLUE);
        pol2.setStroke(Color.BLACK);
    }
    public void hideRect2(){
        rect2.setFill(Color.TRANSPARENT);
        pol2.setFill(Color.TRANSPARENT);
        pol2.setStroke(Color.TRANSPARENT);
    }
    public void addDisc2(){
        if(colCount[1] < 6){
            Disc disc = new Disc(redTurn);
            disc.setTranslateX((1 * 85) + 20);
            discroot.getChildren().add(disc);
            TranslateTransition animation = new TranslateTransition(Duration.seconds(0.5),disc);
            animation.setToY(((5 - colCount[1]) * 85) + 20);
            colCount[1]++;
            redTurn = !redTurn;
            animation.play();
        }
    }
    public void showRect3(){
        rect3.setFill(Color.rgb(255,110,31, 0.3));
        pol3.setFill(Color.DODGERBLUE);
        pol3.setStroke(Color.BLACK);
    }
    public void hideRect3(){
        rect3.setFill(Color.TRANSPARENT);
        pol3.setFill(Color.TRANSPARENT);
        pol3.setStroke(Color.TRANSPARENT);
    }
    public void addDisc3(){
        if(colCount[2] < 6){
            Disc disc = new Disc(redTurn);
            disc.setTranslateX((2 * 85) + 20);
            discroot.getChildren().add(disc);
            TranslateTransition animation = new TranslateTransition(Duration.seconds(0.5),disc);
            animation.setToY(((5 - colCount[2]) * 85) + 20);
            colCount[2]++;
            redTurn = !redTurn;
            animation.play();
        }
    }
    public void showRect4(){
        rect4.setFill(Color.rgb(255,110,31, 0.3));
        pol4.setFill(Color.DODGERBLUE);
        pol4.setStroke(Color.BLACK);
    }
    
    public void hideRect4(){
        rect4.setFill(Color.TRANSPARENT);
        pol4.setFill(Color.TRANSPARENT);
        pol4.setStroke(Color.TRANSPARENT);
    }
    public void addDisc4(){
        if(colCount[3] < 6){
            Disc disc = new Disc(redTurn);
            disc.setTranslateX((3 * 85) + 20);
            discroot.getChildren().add(disc);
            TranslateTransition animation = new TranslateTransition(Duration.seconds(0.5),disc);
            animation.setToY(((5 - colCount[3]) * 85) + 20);
            colCount[3]++;
            redTurn = !redTurn;
            animation.play();
        }
    }
    public void showRect5(){
        rect5.setFill(Color.rgb(255,110,31, 0.3));
        pol5.setFill(Color.DODGERBLUE);
        pol5.setStroke(Color.BLACK);
    }
    public void hideRect5(){
        rect5.setFill(Color.TRANSPARENT);
        pol5.setFill(Color.TRANSPARENT);
        pol5.setStroke(Color.TRANSPARENT);
    }
    public void addDisc5(){
        if(colCount[4] < 6){
            Disc disc = new Disc(redTurn);
            disc.setTranslateX((4 * 85) + 20);
            discroot.getChildren().add(disc);
            TranslateTransition animation = new TranslateTransition(Duration.seconds(0.5),disc);
            animation.setToY(((5 - colCount[4]) * 85) + 20);
            colCount[4]++;
            redTurn = !redTurn;
            animation.play();
        }
    }
    public void showRect6(){
        rect6.setFill(Color.rgb(255,110,31, 0.3));
        pol6.setFill(Color.DODGERBLUE);
        pol6.setStroke(Color.BLACK);
    }
    public void hideRect6(){
        rect6.setFill(Color.TRANSPARENT);
        pol6.setFill(Color.TRANSPARENT);
        pol6.setStroke(Color.TRANSPARENT);
    }
    public void addDisc6(){
        if(colCount[5] < 6){
            Disc disc = new Disc(redTurn);
            disc.setTranslateX((5 * 85) + 20);
            discroot.getChildren().add(disc);
            TranslateTransition animation = new TranslateTransition(Duration.seconds(0.5),disc);
            animation.setToY(((5 - colCount[5]) * 85) + 20);
            colCount[5]++;
            redTurn = !redTurn;
            animation.play();
        }
    }
    public void showRect7(){
        rect7.setFill(Color.rgb(255,110,31, 0.3));
        pol7.setFill(Color.DODGERBLUE);
        pol7.setStroke(Color.BLACK);
    }
    public void hideRect7(){
        rect7.setFill(Color.TRANSPARENT);
        pol7.setFill(Color.TRANSPARENT);
        pol7.setStroke(Color.TRANSPARENT);
    }
    public void addDisc7(){
        if(colCount[6] < 6){
            Disc disc = new Disc(redTurn);
            disc.setTranslateX((6 * 85) + 20);
            discroot.getChildren().add(disc);
            TranslateTransition animation = new TranslateTransition(Duration.seconds(0.5),disc);
            animation.setToY(((5 - colCount[6]) * 85) + 20);
            colCount[6]++;
            redTurn = !redTurn;
            animation.play();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        List<Rectangle> list = new ArrayList<>();
//        for (int x = 0; x < cols; x++) {
//            Rectangle rect = new Rectangle(0,80,80,560);
//            rect.setTranslateX((x*85) + 20);
//            rect.setFill(Color.TRANSPARENT);
//            rect.setOnMouseEntered(e -> {
//                rect.setFill(Color.rgb(255,110,31, 0.3));
//
//            });
//            rect.setOnMouseExited(e -> {
//                rect.setFill(Color.TRANSPARENT);
//
//            });
//
//            list.add(rect);
//        }
//        pane.getChildren().addAll(list);
        Shape shape = new Rectangle(0,80,640,560);
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                Circle circle = new Circle(40);
                circle.setCenterX(40);
                circle.setCenterY(40);
                circle.setTranslateX((x * 85) + 20);
                circle.setTranslateY((y * 85) + 100);
                circle.setFill(Color.WHITE);
                shape = Shape.subtract(shape,circle);
            }
        }
        shape.setFill(Color.BLUE);
        pane.getChildren().add(shape);
    }
}
