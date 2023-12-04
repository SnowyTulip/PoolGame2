package PoolGame.GameObject;

import javafx.animation.PauseTransition;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * @projectName: Design_Pattern_Assignment_2
 * @package: PoolGame.GameObject
 * @className: GameScore
 * @author: pi
 * @description: TODO
 * @version: 1.0
 */
public class GameScore {
    private Label gameScoreLabel;
    private Pane gameScorePane;
    private int currentScore = 0;
    public GameScore(){
        this.gameScoreLabel = new Label("得分:" + this.currentScore);
        this.gameScoreLabel.setStyle("-fx-font-size: 20px;-fx-mouse-transparent: true");
        gameScorePane = new Pane(this.gameScoreLabel);
        gameScorePane.setStyle("-fx-background-color: transparent;-fx-mouse-transparent: true");
        BorderPane.setAlignment(gameScorePane, Pos.TOP_RIGHT);
        setPosition(100,100);
    }
    public void reset(){
        this.currentScore = 0;
    }
    public void addScore(int Score){
        this.gameScoreLabel.setText("得分:" + this.currentScore + "+" + Score);
        this.currentScore += Score;
        updateScoreLabelWithDelay();
    }
    public void updateScoreLabelWithDelay() {
        PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
        pause.setOnFinished(event -> updateScoreLabel());
        pause.play();
    }

    public void updateScoreLabel() {
        this.gameScoreLabel.setText("得分: " + this.currentScore);
    }
    public void setPosition(double x,double y){
        this.gameScorePane.setLayoutX(x);
        this.gameScorePane.setLayoutX(y);
    }
    public void addToGroupTimer(ObservableList<Node> groupChildren){
        groupChildren.add(gameScorePane);
    }
    public Node getNode(){
        return this.gameScorePane;
    }

}
