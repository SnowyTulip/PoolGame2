package PoolGame.Items;

import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * @projectName: Design_Pattern_Assignment_2
 * @package: PoolGame.Items
 * @className: PoolCue
 * @author: pi
 * @description: 击球球杆 包括球杆的图片:imageView <br/>
 * 因为球杆击球似乎有一些bug,因此选择使用一个碰撞箱体对球进行碰撞
 * 并且在接触到白球后停止运动，并将速度信息传递给球
 * @version: 1.0
 */
public class PoolCue implements Drawable {
    private Rectangle cueShape;
    private ImageView imageView;
    private double Length = 300;
    private double Width = 300;
    //碰撞箱体的宽度,尽量小
    private double CWidth = 4;
    private double SpeedScale = 3;
    //cuePos 实际上表示的左上角的位置
    private double[] cuePos = {0.0, 0.0};
    private double[] cueVel = {0.0, 0.0};
    public enum CueStatus {
        /** A cue ball type */
        Stop,
        /** All other ball type */
        Moving,
    };
    private CueStatus status;
    /**
     * Get the `Node` instance of the object.
     *
     * @return The `Node` instance for the object.
     */
    public PoolCue(){
        String imagePath = getClass().getResource("/images/poolCue.png").toExternalForm();
        Image image = new Image(imagePath);
        imageView = new ImageView(image);
        imageView.setFitWidth(Length);
        imageView.setFitHeight(Width);
        imageView.setVisible(false);
        status = CueStatus.Stop;

        this.cueShape = new Rectangle(Length,CWidth);
        this.cueShape.setFill(Color.BLUE);
        this.cueShape.setVisible(false);

    }
    public void PaintImage(double ballX,double ballY,double mouseX,double mouseY,double radius){
        double diffX = mouseX - ballX;
        double diffY = mouseY - ballY;
        double angle = Math.toDegrees(Math.atan2(diffY,diffX));
        this.imageView.setRotate(angle);
        // 球杆后退的偏差
        //这个设置的是矩形的左上角的位置，实际应该测算矩形的中心位置
        double newX = mouseX + (Length/2+radius)*Math.cos(Math.toRadians(angle));
        double newY = mouseY + (Length/2+radius)*Math.sin(Math.toRadians(angle));
        this.cuePos[0] = newX - Length/2;
        this.cuePos[1] = newY - Width/2;
        this.imageView.setLayoutX( this.cuePos[0]);
        this.imageView.setLayoutY( this.cuePos[1]);

        //计算碰撞箱体位置信息
        this.cueShape.setLayoutX( this.cuePos[0] );
        this.cueShape.setLayoutY( this.cuePos[1] + Width/2 - CWidth/2);
        this.cueShape.setRotate(angle);
        this.cueShape.setVisible(false);

        this.imageView.setVisible(true);
    }
    public void reset(){
        this.cuePos[0] = 0;
        this.cuePos[1] = 0;
        this.cueVel[0] = 0;
        this.cueVel[1] = 0;
        this.imageView.setVisible(false);
        this.status = CueStatus.Stop;
    }

    public void CueMove(){
        if (status == CueStatus.Moving) {
            this.cuePos[0] += this.cueVel[0] * SpeedScale;
            this.cuePos[1] += this.cueVel[1] * SpeedScale;
            this.imageView.setLayoutX(this.cuePos[0]);
            this.imageView.setLayoutY(this.cuePos[1]);

            this.cueShape.setLayoutX(this.cuePos[0]);
            this.cueShape.setLayoutY(this.cuePos[1] + Width/2 - CWidth/2);
        }
        else{
            reset();
        }
    }
    public boolean isCollidesWithBall (Ball ball){
        //需要计算杆的顶点到球的距离
        //this.cuePos 实际上为杆的左上角的坐标
//        boolean collidesWithBall = false;
//        double posX = this.cuePos[0] + this.Length/2;
//        double posY = this.cuePos[1];
//        double distance = Math.sqrt(Math.pow(ballX - posX,2) + Math.pow(ballY - posY,2));
////        System.out.println("distance:" + distance);
//        if(distance <= radius) {
//            collidesWithBall =  true;
//        }
        Bounds ballBounds = ball.getNode().getBoundsInParent();
        Bounds cueBounds  = this.cueShape.getBoundsInParent();
//        Bounds cueBounds  = this.getNode().getBoundsInParent();

        return cueBounds.intersects(ballBounds);
    }
    public void LetCueGo(double Vx,double Vy){
        this.cueVel[0] = Vx;
        this.cueVel[1] = Vy;
        this.imageView.setVisible(true);
        this.status = CueStatus.Moving;
    }
    public void LetBallGo(Ball ball){
        this.status = CueStatus.Stop;
        ball.setXVel(this.cueVel[0]);
        ball.setYVel(this.cueVel[1]);
        this.reset();
    }
    public CueStatus getStatus(){
        return this.status;
    }
//    public void Paint(double ballX,double ballY,double mouseX,double mouseY,double radius){
//        double diffX = mouseX - ballX;
//        double diffY = mouseY - ballY;
//        double angle = Math.toDegrees(Math.atan2(diffY,diffX));
//        this.cueShape.setRotate(angle);
//        // 球杆后退的偏差
//        //这个设置的是矩形的左上角的位置，实际应该测算矩形的中心位置
//        double newX = mouseX + (Length/2+radius)*Math.cos(Math.toRadians(angle));
//        double newY = mouseY + (Length/2+radius)*Math.sin(Math.toRadians(angle));
//
//        this.cueShape.setLayoutX( newX - this.cueShape.getWidth()/2);
//        this.cueShape.setLayoutY( newY - this.cueShape.getHeight()/2);
//        //计算位置信息
//
////        this.cueShape.setY( ballY );
////        this.cueShape.setX( ballX );
//        this.cueShape.setVisible(true);
//    }
    public void HiddenCue(){
//        this.cueShape.setVisible(false);
        this.imageView.setVisible(false);
    }


    @Override
    public Node getNode() {
        return this.imageView;
    }

    /**
     * Add the object to the JavaFX group so they can be drawn.
     *
     * @param groupChildren The list of `Node` obtained from the JavaFX Group.
     */
    @Override
    public void addToGroup(ObservableList<Node> groupChildren) {
        groupChildren.add(this.cueShape);
        groupChildren.add(this.imageView);
    }
}
