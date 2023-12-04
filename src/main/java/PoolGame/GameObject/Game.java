package PoolGame.GameObject;

import java.util.ArrayList;
import java.util.List;

import PoolGame.Builder.BallBuilderDirector;
import PoolGame.Config.BallConfig;
import PoolGame.Config.GameConfig;
import PoolGame.Items.Ball;
import PoolGame.Items.PoolTable;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/** The game class that runs the game */
public class Game {
    PoolTable table;
    private boolean shownWonText = false;
    private GameCounterManager counterManager;
    private final Text winText = new Text(50, 50, "Win and Bye");
    enum GameState {
        pause,running,
    }
    private GameTimer gameTimer;
    private GameState state;



    /**
     * Initialise the game with the provided config
     * @param GameConfig The config parser to load the config from
     */
    public Game(GameConfig GameConfig) {
        this.setup(GameConfig);
    }

    private void setup(GameConfig GameConfig) {
        this.table = new PoolTable(GameConfig.getTableConfig());
        List<BallConfig> ballsConf = GameConfig.getBallsConfig().getBallConfigs();
        List<Ball> balls = new ArrayList<>();
        BallBuilderDirector builder = new BallBuilderDirector();
        builder.registerDefault();
        for (BallConfig ballConf: ballsConf) {
            Ball ball = builder.construct(ballConf);
            if (ball == null) {
                System.err.println("WARNING: Unknown ball, skipping...");
            } else {
                balls.add(ball);
            }
        }
        this.table.setupBalls(balls);
        this.winText.setVisible(false);
        this.winText.setX(table.getDimX() / 2.);
        this.winText.setY(table.getDimY() / 2.);
        this.table.getWhiteBall().addGameListener(this);
        this.gameTimer = new GameTimer();
        GameStart();
        this.counterManager = new GameCounterManager(this);
    }
    public GameCounterManager getGameCounterManager() {
        return counterManager;
    }

    /**
     * Get the window dimension in the x-axis
     * @return The x-axis size of the window dimension
     */
    public double getWindowDimX() {
        return this.table.getDimX();
    }

    /**
     * Get the window dimension in the y-axis
     * @return The y-axis size of the window dimension
     */
    public double getWindowDimY() {
        return this.table.getDimY();
    }

    /**
     * Get the pool table associated with the game
     * @return The pool table instance of the game
     */
    public PoolTable getPoolTable() {
        return this.table;
    }

    /** Add all drawable object to the JavaFX group
     * @param root The JavaFX `Group` instance
    */
    //显示Label 不可以遮挡球，因此添加Text 需要在球之前、球桌之后
    public void addDrawables(Group root) {
        ObservableList<Node> groupChildren = root.getChildren();
        table.addToGroupTable(groupChildren);
        table.addToGroupPockets(groupChildren);
        groupChildren.add(this.winText);
        groupChildren.add(this.gameTimer.getNode());
        table.addToGroupBalls(groupChildren);

    }
    public void setConfig(GameConfig config) {
        GamePause();
        if (config != null){
            this.setup(config);
        }
        reset();
        GameStart();
    }
    public void GameStart(){
        this.state = GameState.running;
        this.gameTimer.TimerStart();
    }
    public void GamePause(){
        this.state = GameState.pause;
        this.gameTimer.TimerStop();
    }

    /** Reset the game */
    public void reset() {
        this.winText.setVisible(false);
        this.shownWonText = false;
        this.table.reset();
        this.gameTimer.reset();
        GameStart();
    }
    public boolean isAllowHitBall(){
        boolean canHit = false;
        if(!this.counterManager.isCounterRunning())
            canHit = true;
        return canHit;
    }

    /** Code to execute every tick. */
    public void tick() {

        if (this.state == GameState.running) {
            if (table.hasWon() && !this.shownWonText) {
                //System.out.println(this.winText.getText());
                this.winText.setVisible(true);
                this.shownWonText = true;
            }
            table.checkPocket(this);
            table.handleCollision();
            this.table.applyFrictionToBalls();
            for (Ball ball : this.table.getBalls()) {
                ball.move();
            }
            if(this.counterManager.isCounterEnd()){
                this.counterManager.CounterEnd();
            }

        }
    }
}
