package PoolGame.GameObjectSnapshot;

import PoolGame.Items.Ball;

import java.util.ArrayList;
import java.util.List;

/**
 * @projectName: Design_Pattern_Assignment_2
 * @package: PoolGame.GameObjectSnapshot
 * @className: TableSnapshot
 * @author: pi
 * @description: TODO
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
