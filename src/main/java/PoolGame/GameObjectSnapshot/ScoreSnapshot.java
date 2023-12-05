package PoolGame.GameObjectSnapshot;

/**
 * @projectName: Design_Pattern_Assignment_2
 * @package: PoolGame.GameObjectSnapshot
 * @className: ScoreSnapshot
 * @author: pi
 * @description: 保存当前的得分
 * @version: 1.0
 */
public class ScoreSnapshot extends GameObjectSnapshot{
    private int score;
    public ScoreSnapshot(int score){
        this.score = score;
    }
    public int getScore(){
        return score;
    }
}
