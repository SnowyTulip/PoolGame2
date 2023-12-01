package PoolGame.Config;

import org.json.simple.JSONObject;

/**
 * @projectName: Design_Pattern_Assignment_2
 * @package: PoolGame.Config
 * @className: PocketConfig
 * @author: pi
 * @description: 球洞的配置文件
 * @version: 1.0
 */
public class PocketConfig implements Configurable{
    private SizeConfig pocketSize;
    private double radius;

    public PocketConfig (Object obj) {this.parseJSON(obj);}

    public PocketConfig(SizeConfig pocketSizeConfig,double radius){
        this.init(pocketSizeConfig,radius);
    }
    private void init(SizeConfig pocketSizeConfig,double radius) {
     if (radius <= 0) {
            throw new IllegalArgumentException("radius of ball must be greater than 0");
     }
     this.radius = radius;
     this.pocketSize = pocketSizeConfig;

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
        SizeConfig sizecfg = new SizeConfig(json.get("position"));
        double radius = (double) json.get("radius");
        this.init(sizecfg,radius);
        return null;
    }
}
