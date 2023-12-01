package PoolGame.Config;

import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * @projectName: Design_Pattern_Assignment_2
 * @package: PoolGame.Config
 * @className: PocketsConfig
 * @author: pi
 * @description: 存放球洞的集合,如同BallsConfig
 * @version: 1.0
 */
public class PocketsConfig implements Configurable{
    private List<PocketConfig> pockets;
    public PocketsConfig(Object obj){
        this.parseJSON(obj);
    }

    public PocketsConfig(List<PocketConfig> pocketsList){
        this.init(pocketsList);
    }

    private void init(List<PocketConfig> pocketsList) {
        this.pockets = pocketsList;
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
        List<PocketConfig> list = new ArrayList<>();
        JSONArray json = (JSONArray) obj;

        for (Object b : json) {
            list.add(new PocketConfig(b));
        }
        this.init(list);
        return null;
    }
}
