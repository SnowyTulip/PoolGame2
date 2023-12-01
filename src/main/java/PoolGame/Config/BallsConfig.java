package PoolGame.Config;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/** A config class that will contain all the ball configuration */
public class BallsConfig implements Configurable {
    private List<BallConfig> ball;

    /**
     * Initialise the instance with the provided JSONArray of ball
     * @param obj A JSONArray containing all the ball configuration
     */
    // Bug: 这里似乎是助教提供代码处遗留的一个bug,因为读取完balls后还需要再读取ball才可以
    // FIX :
    public BallsConfig(Object obj) {
        obj = ((JSONObject)obj).get("ball");
        this.parseJSON(obj);
    }

    /**
     * Initialise the instance with the provided ball configs
     * @param ballList A list of ball configs
     */
    public BallsConfig(List<BallConfig> ballList) {
        this.init(ballList);
    }
    
    private void init(List<BallConfig> ballList) {
        this.ball = ballList;
    }

    public Configurable parseJSON(Object obj) {
        List<BallConfig> list = new ArrayList<>();
        JSONArray json = (JSONArray) obj;

        for (Object b : json) {
            list.add(new BallConfig(b));
        }
        this.init(list);
        return this;
    }

    /**
     * Get the ball list as defined in the config.
     * @return A list of balls
     */
    public List<BallConfig> getBallConfigs() {
        return this.ball;
    }
}
