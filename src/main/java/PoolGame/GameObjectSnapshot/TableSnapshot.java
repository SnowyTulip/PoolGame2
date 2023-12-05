package PoolGame.GameObjectSnapshot;

import PoolGame.Items.Ball;

import java.util.ArrayList;
import java.util.List;

/**
 * @projectName: Design_Pattern_Assignment_2
 * @package: PoolGame.GameObjectSnapshot
 * @className: TableSnapshot
 * @author: pi
 * @description: 保存table的快照，实际上table只需要维护balls的信息<br/>
 * 在恢复的时候也仅仅将table所持有的球的快照恢复即可
 * @version: 1.0
 */
public class TableSnapshot extends GameObjectSnapshot{
    private List<BallSnapshot> ballsSnapshots;
    public TableSnapshot(List<Ball> balls) {
        this.ballsSnapshots = new ArrayList<>();
        for (Ball ball : balls) {
            this.ballsSnapshots.add(ball.genSnapshot());
        }
    }
    public List<BallSnapshot> getBallsSnapshots() {
        return this.ballsSnapshots;
    }
}
