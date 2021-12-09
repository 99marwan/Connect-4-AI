package sample;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class Controller<ColCount> {

    @FXML
    Pane pane;
    @FXML
    Pane discroot;
    @FXML
    Label think;

    GameLogic logic = new GameLogic();
    Algorithm algorithm = new Algorithm();
    public int[] colCount = new int[7];
    private boolean redTurn = true;
    private int rows = 6;
    private int cols = 7;
    private int k;
    GameState initial;
    boolean notFull =true;
    private boolean alphaBeta_Pruning;

    private boolean human_isPlayer1;

    //the disc of the game
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

    //construction of variables
    public void construct(int k, boolean alphaBeta_Pruning, boolean human_isPlayer1){
        if (k > 42) {
            k = 42;
        }
        this.k = k;
        this.alphaBeta_Pruning = alphaBeta_Pruning;
        this.human_isPlayer1 = human_isPlayer1;
    }

    //Function to put disc in corresponding place
    public void addDisc(int x,boolean turn) throws IOException {
        int sum=0;
        if(turn) {
            System.out.println("player game");
            if (colCount[x]  < 6) {
                Disc disc = new Disc(redTurn);
                disc.setTranslateX((x * 85) + 20);
                discroot.getChildren().add(disc);
                TranslateTransition animation = new TranslateTransition(Duration.seconds(0.05), disc);
                animation.setToY(((5 - colCount[x]) * 85) + 20);
                colCount[x]++;
                initial.setColumnNumber(colCount);
                initial.setGameMoves(initial.getGameMoves() + String.valueOf(x));
                for (int i = 0; i < 7; i++) {
                    System.out.print(initial.getColumnNumber()[i] + " ");
                }
                System.out.println();
                System.out.println(initial.getGameMoves());
                redTurn = !redTurn;
                animation.play();
                notFull=true;
                think.setText("Computer is Thinking ^_^");
                think.setTextFill(Color.RED);
                System.out.println("=================================================================================================");
            }else {
                think.setText("Try Another Column, This one is full :(");
                think.setTextFill(Color.RED);
                notFull=false;
            }
        }else{
            think.setText("");
            System.out.println("computer game");
            if(colCount[x]  <= 6){
                Disc disc = new Disc(redTurn);
                disc.setTranslateX((x * 85) + 20);
                discroot.getChildren().add(disc);
                TranslateTransition animation = new TranslateTransition(Duration.seconds(0.2),disc);
                animation.setToY(((5 - colCount[x] + 1) * 85) + 20);
                for(int i=0;i<7;i++){
                    System.out.print(initial.getColumnNumber()[i]+" ");
                }
                System.out.println();
                System.out.println(initial.getGameMoves());
                System.out.println("=================================================================================================");
                redTurn = !redTurn;
                animation.play();

                sum=0;
                for(int i=0;i<7;i++){
                    sum+=colCount[i];
                }
                if(sum==42){
                    int[] result = logic.calculateScore(initial);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Result");
                    alert.setHeaderText("Game is Over => player1 is "+result[0]+" / player 2 is "+result[1]);
                    alert.showAndWait();

                    Stage stage = (Stage) discroot.getScene().getWindow();
                    stage.close();


                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
                    Parent root = loader.load();
                    Stage stage2 = new Stage();
                    stage2.setScene(new Scene(root,640, 640));
                    stage2.setTitle("Welcome to the Game");
                    stage2.show();
                    return;
                }
            }
        }
    }

    //Function of the computer thinking and put its disk
    public void aiDisc() throws IOException {
        if(alphaBeta_Pruning){
            initial = algorithm.decidePruning(initial,k);
            colCount=initial.getColumnNumber();
            String temp= initial.getGameMoves();
            System.out.println("computer will play in col   :  " + Character.getNumericValue(temp.charAt(temp.length()-1)));
            addDisc(Character.getNumericValue(temp.charAt(temp.length()-1)),redTurn);
        }else{
            initial = algorithm.decide(initial,k);
            colCount=initial.getColumnNumber();
            String temp= initial.getGameMoves();
            System.out.println("computer will play in col   :  " + Character.getNumericValue(temp.charAt(temp.length()-1)));
            addDisc(Character.getNumericValue(temp.charAt(temp.length()-1)),redTurn);
        }
    }

    //Initialization of the board, circles, polygon arrow
    public void initialize() {
        initial = logic.Initialize_Game(rows,cols,k,alphaBeta_Pruning,human_isPlayer1);
        colCount=initial.getColumnNumber();
        List<Rectangle> list = new ArrayList<>();
        List<Polygon> listPol = new ArrayList<>();
        Double[] points = {-16.20001220703125, 40.0, 25.4000244140625, 40.0, 4.5999755859375, 76.20001220703125};

        //creation of Rectangles that show which column your cursor on
        //creation of arrow above the rectangle
        for (int x = 0; x < cols; x++) {
            Rectangle rect = new Rectangle(0,148,80,560);
            rect.setTranslateX((x*85) + 20);
            rect.setFill(Color.TRANSPARENT);
            Polygon pol = new Polygon();
            pol.getPoints().addAll(points);
            pol.setLayoutX(55 + (x * 85));
            pol.setLayoutY(70);
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
            rect.setOnMousePressed(e -> {
                try {
                    addDisc(finalX,redTurn);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            });
            rect.setOnMouseReleased(e -> {
                try {
                    if(notFull) {
                        aiDisc();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            list.add(rect);
            listPol.add(pol);
        }
        pane.getChildren().addAll(listPol);
        pane.getChildren().addAll(list);

        //creation of board of blue rectangle and white circles
        Shape shape = new Rectangle(0,148,640,560);
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                Circle circle = new Circle(40);
                circle.setCenterX(40);
                circle.setCenterY(40);
                circle.setTranslateX((x * 85) + 20);
                circle.setTranslateY((y * 85) + 168);
                circle.setFill(Color.WHITE);
                shape = Shape.subtract(shape,circle);
            }
        }
        shape.setFill(Color.BLUE);
        pane.getChildren().add(shape);
    }
}
