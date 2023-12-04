package PoolGame.GameObjectSnapshot;

/**
 * @projectName: Design_Pattern_Assignment_2
 * @package: PoolGame.GameObjectSnapshot
 * @className: BallSnapshot
 * @author: pi
 * @description: TODO
 * @version: 1.0
 */
//对于球,需要存储位置、速度、入袋次数、是否被禁用
public class BallSnapshot extends GameObjectSnapshot{
    private final double ballPosX;
    private final double ballPosY;
    private final int fallCounter;
    private final boolean disabled;
    public BallSnapshot(double posX,double posY, int fallCounter,boolean disabled) {
        this.ballPosX = posX;
        this.ballPosY = posY;
        this.fallCounter = fallCounter;
        this.disabled = disabled;
    }
    public double getBallPosX() {
        return ballPosX;
    }
    public double getBallPosY(){
        return ballPosY;
    }
    public int getFallCounter(){
        return fallCounter;
    }
    public boolean getDisabled() {
        return disabled;
    }
}
