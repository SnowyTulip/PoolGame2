package PoolGame;

import java.util.ArrayList;
import java.util.List;

import PoolGame.Builder.BallBuilderDirector;
import PoolGame.Config.BallConfig;
import PoolGame.Config.GameConfig;
import PoolGame.Items.Ball;
import PoolGame.Items.PoolTable;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.text.Text;

/** The game class that runs the game */
public class Game {
    private PoolTable table;
    private boolean shownWonText = false;
    private final Text winText = new Text(50, 50, "Win and Bye");
    enum GameState {
        pause,running,
    }
    //用来标记当前回合是否已经结束
    //从一次击球开始,到所有球都停下来记录为一个回合
    enum CounterState {
        counterRunning,counterEnd,
    }
    private CounterState counterState;
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
        this.winText.setX(table.getDimX() / 2);
        this.winText.setY(table.getDimY() / 2);
        this.table.getWhiteBall().addGameListener(this);
        GameStart();
        // 游戏开始视作第0个回合结束
        CounterEnd();
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
    public void addDrawables(Group root) {
        ObservableList<Node> groupChildren = root.getChildren();
        table.addToGroup(groupChildren);
        groupChildren.add(this.winText);
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
    }
    public void GamePause(){
        this.state = GameState.pause;
    }

    /** Reset the game */
    public void reset() {
        this.winText.setVisible(false);
        this.shownWonText = false;
        this.table.reset();
        GameStart();
    }
    public boolean isAllowHitBall(){
        boolean hit = false;
        if(this.counterState == CounterState.counterEnd)
            hit = true;
        return hit;
    }
    public void CounterStart(){
        this.counterState = CounterState.counterRunning;
        System.out.println("回合开始");
    }
    public void CounterEnd(){
        this.counterState = CounterState.counterEnd;
        System.out.println("回合结束");
        //保存快照
    }
    private boolean isCounterEnd(){
        //如果当前回合为正在运行, 当所有的球都停下时 -> 回合结束
        //并且如果球被禁用,则跳过对他的判断
        double V = 0;
        for (Ball ball : this.table.getBalls()) {
            if(!ball.isDisabled()) {
                V += Math.abs(ball.getXVel()) + Math.abs(ball.getYVel());
            }
        }
        boolean CounterEnd = false;
        if (this.counterState == CounterState.counterRunning && V == 0){
            CounterEnd = true;
        }
        return CounterEnd;
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
            if(this.isCounterEnd()){
                this.CounterEnd();
            }

        }
    }
}
