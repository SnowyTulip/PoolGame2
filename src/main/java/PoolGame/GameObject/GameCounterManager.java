package PoolGame.GameObject;

import PoolGame.Items.Ball;

import java.util.List;

/**
 * @projectName: Design_Pattern_Assignment_2
 * @package: PoolGame.GameObject
 * @className: GameCounterManager
 * @author: pi
 * @description:     用来标记当前回合是否已经结束
 *     //从一次击球开始,到所有球都停下来记录为一个回合
 * @version: 1.0
 */
public class GameCounterManager {
    enum CounterState {
        counterRunning,counterEnd,
    }
    private CounterState counterState;
    private int counterIndex = 0;
    private Game game;
    List<GameSnapshot> gameSnapShots;
    public  GameCounterManager(Game game){
        this.game = game;
        // 游戏开始视作第0个回合结束,需要保存游戏开始时的状态，也就是第0个回合
        this.CounterEnd();
    }

    //切换config时需要重置GameCounter
//    public void CounterReset(){
//
//    }
    public void CounterStart(){
        this.counterState = CounterState.counterRunning;
        counterIndex += 1;
        System.out.println("回合" + this.counterIndex + "开始");
    }
    public void CounterEnd(){
        this.counterState = CounterState.counterEnd;
        System.out.println("回合" + this.counterIndex + "结束");
        //保存快照
    }
    public boolean isCounterRunning(){
        return counterState == CounterState.counterRunning;
    }
    public boolean isCounterEnd(){
        //如果当前回合为正在运行, 当所有的球都停下时 -> 回合结束
        //并且如果球被禁用,则跳过对他的判断
        double V = 0;
        for (Ball ball : this.game.table.getBalls()) {
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

}
