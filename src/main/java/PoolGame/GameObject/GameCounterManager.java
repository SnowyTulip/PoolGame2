package PoolGame.GameObject;

import PoolGame.GameObjectSnapshot.CounterSnapshot;
import PoolGame.GameObjectSnapshot.GameObjectSnapshot;
import PoolGame.GameObjectSnapshot.GameSnapshot;
import PoolGame.Items.Ball;

import java.util.ArrayList;
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
public class GameCounterManager implements IGenGameSnapshot{


    enum CounterState {
        counterRunning,counterEnd,
    }
    private CounterState counterState;
    private int counterIndex = 0;
    private Game game;
    List<GameSnapshot> gameSnapshots;

    @Override
    public CounterSnapshot genSnapshot() {
        return new CounterSnapshot(this.counterIndex);
    }

    @Override
    public void setSnapshot(GameObjectSnapshot snapshot) {
        if(snapshot instanceof CounterSnapshot) {
            this.counterIndex = ((CounterSnapshot) snapshot).getCounterIndex();
        }
    }
    public  GameCounterManager(Game game){
        this.game = game;
        this.gameSnapshots = new ArrayList<GameSnapshot>();
        // 游戏开始视作第0个回合结束,需要保存游戏开始时的状态，也就是第0个回合
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
        SaveGameSnapshot();
    }
    //新加回合必须注意,当前有可能是已经回滚后的状态,如果发现当前回合i 需要插入的gameSnapshots[i]上已经有快照了
    //那么直接丢弃i后面的回合快照,包括i,然后继续将i回合快照保存到正确的位置
    private void SaveGameSnapshot(){
        if(this.gameSnapshots.size() > counterIndex){
            //当前回合i 需要插入的gameSnapshots[i]上已经有快照了
            //删除i后所有快照,包括i
            this.gameSnapshots.subList(this.counterIndex,gameSnapshots.size()).clear();
        }
        this.gameSnapshots.add(game.genSnapshot());
    }
    public void GameGoBack(){
        if(this.counterIndex > 0){
            this.counterIndex --;
            GameSnapshot gameSnapshot = this.gameSnapshots.get(this.counterIndex);
            this.game.setSnapshot(gameSnapshot);
        }
    }
    public void GameGoNext(){
        if(this.counterIndex < this.gameSnapshots.size() - 1){
            this.counterIndex++;
            GameSnapshot gameSnapshot = this.gameSnapshots.get(this.counterIndex);
            this.game.setSnapshot(gameSnapshot);
        }
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
