package PoolGame.Strategy;

import PoolGame.GameObject.Game;
import PoolGame.Items.Ball;

/**
 * @projectName: Design_Pattern_Assignment_2
 * @package: PoolGame.Strategy
 * @className: GameOver
 * @author: pi
 * @description:
 * @version: 1.0
 */
public class GameOver implements BallPocketStrategy {
    public void fallIntoPocket(Game game, Ball ball) {
        ball.disable();
        game.gameOver();
    }
}