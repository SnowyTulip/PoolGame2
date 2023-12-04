package PoolGame.Strategy;

import PoolGame.GameObject.Game;
import PoolGame.Items.Ball;

/** Resets game when the method of this instance is called */
public class GameReset implements BallPocketStrategy {
    public void fallIntoPocket(Game game, Ball ball) {
        game.reset();
    }
}
