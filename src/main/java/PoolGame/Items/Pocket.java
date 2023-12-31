package PoolGame.Items;

import PoolGame.Config.PocketConfig;
import PoolGame.Config.PositionConfig;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/** The pocket of a pool table */
public class Pocket implements Drawable {
    
    /** The radius of the pocket */
    public static final double RADIUS = Ball.RADIUS + 5;
    /** The colour of the pocket */
    protected Color colour = Color.BLACK;
    private double radius;
    /** The JavaFX shape of the pocket */
    protected Circle shape;

    /**
     * Initialise the pool table pocket with the provided value
     * @param posX The x coordinate position of the pocket
     * @param posY The y coordinate position of the pocket
     */
    public Pocket(double posX, double posY) {
        this.shape = new Circle(posX, posY, Pocket.RADIUS, this.colour);
    }
    public  Pocket(PocketConfig pocketConfig){
        PositionConfig posConfig =  pocketConfig.getPocketPosition();
        this.radius = pocketConfig.getRadius();
        this.shape =  new Circle(posConfig.getX(), posConfig.getY(), Pocket.RADIUS, this.colour);
    }
    public  Pocket(PocketConfig pocketConfig,double diffx,double diffy){
        PositionConfig posConfig =  pocketConfig.getPocketPosition();
        this.radius = pocketConfig.getRadius();
        this.shape =  new Circle(posConfig.getX() + diffx, posConfig.getY() + diffy, Pocket.RADIUS, this.colour);
    }

    /**
     * Check if a point is in the pocket bounds
     * @param point A point to check.
     * @return True if the point is in the pocket bounds, false otherwise
     */
    public boolean isInPocket(Point2D point) {
        return this.shape.getBoundsInLocal().contains(point);
    }

    public Node getNode() {
        return this.shape;
    }

    public void addToGroup(ObservableList<Node> groupChildren) {
        groupChildren.add(this.shape);
    }
}
