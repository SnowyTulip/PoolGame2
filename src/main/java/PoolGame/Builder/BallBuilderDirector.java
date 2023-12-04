package PoolGame.Builder;

import java.util.HashMap;
import java.util.Map;

import PoolGame.Config.BallConfig;
import PoolGame.Items.Ball;
import PoolGame.Items.Ball.BallType;
import PoolGame.Strategy.GameReset;
import PoolGame.Strategy.PocketOnce;
import PoolGame.Strategy.PocketThird;
import PoolGame.Strategy.PocketTwice;

/** The class that builds all the different balls */
public class BallBuilderDirector {
    private Map<String, BallBuilder> builders;
    private Map<String,Integer> ballsScoreValues;

    /** Initialise a ball builder director with no knowledge of how to build balls */
    public BallBuilderDirector() {
        this.builders = new HashMap<>();
        this.ballsScoreValues = new HashMap<>();
        this.ballsScoreValues.put("white",-99999);
        this.ballsScoreValues.put("red",2);
        this.ballsScoreValues.put("blue",1);
        this.ballsScoreValues.put("yellow",2);
        this.ballsScoreValues.put("orange",1);
        this.ballsScoreValues.put("green",1);
        this.ballsScoreValues.put("purple",1);
        this.ballsScoreValues.put("black",1);
        this.ballsScoreValues.put("brown",2);

    }

    /**
     * Associate a key with a ball builder.
     * @param key A string to associate with a builder
     * @param builder The ball builder to associate with the provided key
     */
    public void register(String key, BallBuilder builder) {
        this.builders.put(key, builder);
    }

    /** Register the defaults for the ball builder director. */
    public void registerDefault() {
        this.register("white", new WhiteBallBuilder(BallType.CUEBALL, new GameReset(),ballsScoreValues.get("white") ));
        this.register("red", new RedBallBuilder(BallType.NORMALBALL, new PocketOnce() ,ballsScoreValues.get("red")));
        this.register("blue", new BlueBallBuilder(BallType.NORMALBALL, new PocketTwice(),ballsScoreValues.get("blue")));

        this.register("yellow", new YellowBallBuilder(BallType.NORMALBALL, new PocketOnce(),ballsScoreValues.get("yellow")));
        this.register("orange", new OrangeBallBuilder(BallType.NORMALBALL, new PocketOnce(),ballsScoreValues.get("orange")));

        this.register("green", new GreenBallBuilder(BallType.NORMALBALL, new PocketTwice(),ballsScoreValues.get("green")));
        this.register("purple", new PurpleBallBuilder(BallType.NORMALBALL, new PocketTwice(),ballsScoreValues.get("purple")));

        this.register("black", new BlackBallBuilder(BallType.NORMALBALL, new PocketThird(),ballsScoreValues.get("black")));
        this.register("brown", new BrownBallBuilder(BallType.NORMALBALL, new PocketThird(),ballsScoreValues.get("brown")));
    }

    /**
     * Construct the ball based on the config
     * @param config The ball configuration
     * @return The ball instance configured with the config provided
     */
    public Ball construct(BallConfig config) {
        String key = config.getColour();
        BallBuilder builder = this.builders.get(key);
        if (builder == null) {
            return null;
        }
        builder.setXPos(config.getPositionConfig().getX());
        builder.setYPos(config.getPositionConfig().getY());
        builder.setXVel(config.getVelocityConfig().getX());
        builder.setYVel(config.getVelocityConfig().getY());
        builder.setMass(config.getMass());
        return builder.finaliseBuild();
    }
}
