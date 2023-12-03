package PoolGame.Strategy;

import PoolGame.Game;
import PoolGame.Items.Ball;

/**
 * @projectName: Design_Pattern_Assignment_2
 * @package: PoolGame.Strategy
 * @className: PocketThird
 * @author: pi
 * @description: TODO
 * @version: 1.0
 */
public class PocketThird implements BallPocketStrategy{
    private static final int FALL_COUNTER_THRESHOLD = 3;

    /**
     * Action to execute when a ball fell into a pocket
     *
     * @param game The instance of the game
     * @param ball The ball that this instance is associated to
     */
    @Override
    public void fallIntoPocket(Game game, Ball ball) {
        ball.incrementFallCounter();
        if (ball.getFallCounter() >= FALL_COUNTER_THRESHOLD) {
            ball.disable();
        } else {
            ball.resetPosition();
            for (Ball ballB: game.getPoolTable().getBalls()) {
                if (ball.isColliding(ballB)) {
                    ball.disable();
                }
            }
        }
    }
}
