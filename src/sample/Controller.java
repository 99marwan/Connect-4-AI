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


    public int[] colCount = new int[7];
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


    public void addDisc(int x){
        if(colCount[x] < 6){
            Disc disc = new Disc(redTurn);
            disc.setTranslateX((x * 85) + 20);
            discroot.getChildren().add(disc);
            TranslateTransition animation = new TranslateTransition(Duration.seconds(0.5),disc);
            animation.setToY(((5 - colCount[x]) * 85) + 20);
            colCount[x]++;
            redTurn = !redTurn;
            animation.play();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Arrays.fill(colCount,0);
        List<Rectangle> list = new ArrayList<>();
        List<Polygon> listPol = new ArrayList<>();
        Double[] points = {-16.20001220703125, 40.0, 25.4000244140625, 40.0, 4.5999755859375, 76.20001220703125};
        for (int x = 0; x < cols; x++) {
            Rectangle rect = new Rectangle(0,80,80,560);
            rect.setTranslateX((x*85) + 20);
            rect.setFill(Color.TRANSPARENT);
            Polygon pol = new Polygon();
            pol.getPoints().addAll(points);
            pol.setLayoutX(55 + (x * 85));
            pol.setLayoutY(3);
            pol.setFill(Color.TRANSPARENT);
            rect.setOnMouseEntered(e -> {
                rect.setFill(Color.rgb(255,110,31, 0.3));
                pol.setFill(Color.DODGERBLUE);
                pol.setStroke(Color.BLACK);
            });
            rect.setOnMouseExited(e -> {
                rect.setFill(Color.TRANSPARENT);
                pol.setFill(Color.TRANSPARENT);
                pol.setStroke(Color.TRANSPARENT);
            });

            int finalX = x;
            rect.setOnMouseClicked(e -> {
                if(finalX == 0)
                    addDisc(finalX);
                else if(finalX == 1)
                    addDisc(finalX);
                else if(finalX == 2)
                    addDisc(finalX);
                else if(finalX == 3)
                    addDisc(finalX);
                else if(finalX == 4)
                    addDisc(finalX);
                else if(finalX == 5)
                    addDisc(finalX);
                else if(finalX == 6)
                    addDisc(finalX);

            });
            list.add(rect);
            listPol.add(pol);
        }
        pane.getChildren().addAll(listPol);
        pane.getChildren().addAll(list);
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
