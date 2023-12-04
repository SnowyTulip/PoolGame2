package PoolGame.GameObjectSnapshot;

/**
 * @projectName: Design_Pattern_Assignment_2
 * @package: PoolGame.Items
 * @className: GameSnapshot
 * @author: pi
 * @description: 需要保存table的快照、游戏分数、计时器时间
 * @version: 1.0
 */
public class GameSnapshot extends GameObjectSnapshot{
    private TableSnapshot tableSnapshot;
    private CounterSnapshot counterSnapshot;
    private ScoreSnapshot scoreSnapshot;
    private TimerSnapshot timeSnapshot;
    public GameSnapshot(TableSnapshot tableSnapshot, CounterSnapshot counter, ScoreSnapshot score, TimerSnapshot timeSnapshot){
        this.tableSnapshot = tableSnapshot;
        this.counterSnapshot = counter;
        this.scoreSnapshot = score;
        this.timeSnapshot = timeSnapshot;
    }

    public TimerSnapshot getTimeSnapshot() {
        return timeSnapshot;
    }

    public ScoreSnapshot getScoreSnapshot() {
        return scoreSnapshot;
    }

    public CounterSnapshot getCounterSnapshot() {
        return counterSnapshot;
    }

    public TableSnapshot getTableSnapshot() {
        return tableSnapshot;
    }
}
