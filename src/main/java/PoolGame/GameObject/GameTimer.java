package PoolGame.GameObject;

import PoolGame.GameObjectSnapshot.GameObjectSnapshot;
import PoolGame.GameObjectSnapshot.TimerSnapshot;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.time.LocalTime;
//import java.time.Duration;

/**
 * @projectName: Design_Pattern_Assignment_2
 * @package: PoolGame.GameObject
 * @className: GameTimer
 * @author: pi
 * @description: Game的计时器，包含时间显示的功能
 * @version: 1.0
 */
public class GameTimer implements IGenGameSnapshot{
    private Label timerLabel;
    private Pane timerPane;
    private Timeline timeLine;
    private long ticks;
    public GameTimer(){
        this.timerLabel = new Label("00:00:00");
        this.timerLabel.setStyle("-fx-font-size: 20px;-fx-mouse-transparent: true");
        timerPane = new Pane(this.timerLabel);
        timerPane.setStyle("-fx-background-color: transparent;-fx-mouse-transparent: true");
        BorderPane.setAlignment(timerPane, Pos.TOP_RIGHT);
        this.ticks = 0;
        this.timeLine = new Timeline(new KeyFrame(Duration.seconds(1),event ->  UpdateTimerForaTick()));
        this.timeLine.setCycleCount(Timeline.INDEFINITE);
    }
    public void UpdateTimerForaTick(){
        this.ticks += 1;
        java.time.Duration duration = java.time.Duration.ofSeconds(this.ticks);
        LocalTime time = LocalTime.MIDNIGHT.plus(duration);
        this.timerLabel.setText(time.toString());
//        System.out.println("timer:" + this.ticks);
    }
    public void addToGroupTimer(ObservableList<Node> groupChildren){
        groupChildren.add(timerPane);
    }
    public void reset(){
        this.ticks = 0;
    }
    public Node getNode(){
        return this.timerPane;
    }
    public void TimerStart(){
        this.timeLine.play();
    }
    public void TimerStop(){
        this.timeLine.stop();
    }

    @Override
    public TimerSnapshot genSnapshot() {
        return new TimerSnapshot(this.ticks);
    }

    @Override
    public void setSnapshot(GameObjectSnapshot snapshot) {
        if(snapshot instanceof TimerSnapshot){
            this.ticks = ((TimerSnapshot) snapshot).getTicks();
        }
    }
}
