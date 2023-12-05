package PoolGame.GameObjectSnapshot;

/**
 * @projectName: Design_Pattern_Assignment_2
 * @package: PoolGame.GameObjectSnapshot
 * @className: TimerSnapshot
 * @author: pi
 * @description: 保存时间 ticks
 * @version: 1.0
 */
public class TimerSnapshot extends GameObjectSnapshot{
    private long ticks;
    public TimerSnapshot(long ticks){
        this.ticks = ticks;
    }
    public long getTicks() {
        return ticks;
    }
}
