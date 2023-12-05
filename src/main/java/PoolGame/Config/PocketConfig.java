package PoolGame.Config;

import org.json.simple.JSONObject;

/**
 * @projectName: Design_Pattern_Assignment_2
 * @package: PoolGame.Config
 * @className: PocketConfig
 * @author: pi
 * @description: 球洞的配置文件,这里是模仿ball的构建方法,它的上层构建类为PocketsConfig
 * @version: 1.0
 */
public class PocketConfig implements Configurable{
    private PositionConfig pocketPosition;
    private double radius;

    public double getRadius() {
        return radius;
    }
    public PositionConfig getPocketPosition(){
        return pocketPosition;
    }

    public PocketConfig (Object obj) {this.parseJSON(obj);}

    public PocketConfig(PositionConfig pocketPosConfig,double radius){
        this.init(pocketPosConfig,radius);
    }
    private void init(PositionConfig pocketPosConfig,double radius) {
     if (radius <= 0) {
            throw new IllegalArgumentException("radius of ball must be greater than 0");
     }
     this.radius = radius;
     this.pocketPosition = pocketPosConfig;

    }

    /**
     * Parse a JSONObject or JSONArray
     *
     * @param obj Either a JSONObject or JSONArray to be parsed and initialised
     *            into the appropriate class.
     * @return The configuration instance derived from the JSON
     */
    @Override
    public Configurable parseJSON(Object obj) {
        JSONObject json = (JSONObject) obj;
        PositionConfig positionConfig = new PositionConfig(json.get("position"));
        double radius = (double) json.get("radius");
        this.init(positionConfig,radius);
        return null;
    }
}
