package PoolGame.GameObjectSnapshot;

/**
 * @projectName: Design_Pattern_Assignment_2
 * @package: PoolGame.GameObjectSnapshot
 * @className: CounterSnapshot
 * @author: pi
 * @description: counter需要保存 就counter数
 * @version: 1.0
 */
public class CounterSnapshot extends GameObjectSnapshot{
    private int counterIndex;
    public CounterSnapshot(int counterIndex){
        this.counterIndex = counterIndex;
    }
    public int getCounterIndex(){
        return this.counterIndex;
    }
}
