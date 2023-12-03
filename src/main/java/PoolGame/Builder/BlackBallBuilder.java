package PoolGame.Builder;

import PoolGame.Items.Ball.BallType;
import PoolGame.Items.Ball;
import PoolGame.Strategy.BallPocketStrategy;

/**
 * @projectName: Design_Pattern_Assignment_2
 * @package: PoolGame.Builder
 * @className: BlackBallBuilder
 * @author: pi
 * @description: TODO
 * @version: 1.0
 */
public class BlackBallBuilder implements BallBuilder{
    private Ball ball;

    private BallType ballType = null;
    private BallPocketStrategy action = null;
    public BlackBallBuilder(){
        this.reset();
    }
    public BlackBallBuilder(BallType type, BallPocketStrategy action) {
        this.ballType = type;
        this.action = action;
        this.reset();
    }
    /**
     * Reset the builder
     */
    @Override
    public void reset() {
        this.ball = new Ball();
        this.ball.setColour("black");
        if (ballType != null) {
            this.ball.setBallType(this.ballType);
        }
        if (this.action != null) {
            this.ball.setPocketAction(this.action);
        }
    }

    /**
     * Set the x position of the ball.
     *
     * @param xPos The x position of the ball to be constructed.
     */
    public void setXPos(double xPos) {
        this.ball.setInitialXPos(xPos);
    }

    public void setYPos(double yPos) {
        this.ball.setInitialYPos(yPos);
    }

    public void setXVel(double xVel) {
        this.ball.setInitialXVel(xVel);
    }

    public void setYVel(double yVel) {
        this.ball.setInitialYVel(yVel);
    }

    public void setMass(double mass) {
        this.ball.setMass(mass);
    }

    /**
     * Set the ball type of the new ball.
     *
     * @param type The ball type of the new ball to be constructed.
     */
    @Override
    public void setBallType(Ball.BallType type) {
        this.ballType = type;
        this.ball.setBallType(type);
    }

    /**
     * Set the action to be carried out when the new ball falls into a pocket.
     *
     * @param action The action for the new ball to carry out.
     */
    @Override
    public void setPocketAction(BallPocketStrategy action) {
        this.action = action;
        this.ball.setPocketAction(action);
    }

    /**
     * Get the ball that has been configured and start a new build.
     *
     * @return The instance of the ball that has been configured by using the
     * setters
     */
    @Override
    public Ball finaliseBuild() {
        Ball ball = this.ball;
        this.reset();
        return ball;
    }
}
