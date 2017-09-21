import java.awt.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Class that implements a ball with a position and velocity.
 */
public class Ball {
	// Constants
	/**
	 * The radius of the ball.
	 */
	public static final int BALL_RADIUS = 8;
	/**
	 * The initial velocity of the ball in the x direction.
	 */
	public static final double INITIAL_VX = 1e-7;
	/**
	 * The initial velocity of the ball in the y direction.
	 */
	public static final double INITIAL_VY = 1e-7;
	
	public static final double ACCELERATION = 2e-8;

	// Instance variables
	// (x,y) is the position of the center of the ball.
	private double x, y;
	private double vx, vy;
	private Circle circle;
	private int bottomHits;

	/**
	 * @return the Circle object that represents the ball on the game board.
	 */
	public Circle getCircle () {
		return circle;
	}
	
	public int getBottomHits(){
		return bottomHits;
	}

	/**
	 * Constructs a new Ball object at the centroid of the game board
	 * with a default velocity that points down and right.
	 */
	public Ball () {
		x = GameImpl.WIDTH/2;
		y = GameImpl.HEIGHT/2;
		vx = INITIAL_VX;
		vy = INITIAL_VY;
		bottomHits = 0;

		circle = new Circle(BALL_RADIUS, BALL_RADIUS, BALL_RADIUS);
		circle.setLayoutX(x - BALL_RADIUS);
		circle.setLayoutY(y - BALL_RADIUS);
		circle.setFill(Color.BLACK);
	}

	/**
	 * Updates the position of the ball, given its current position and velocity,
	 * based on the specified elapsed time since the last update.
	 * @param deltaNanoTime the number of nanoseconds that have transpired since the last update
	 */
	public void updatePosition (long deltaNanoTime, Paddle paddle) {
		updateVelocity();
		// changes direction if ball collides with paddle
		if(touching(paddle.getX(), paddle.getY(), 
					paddle.getRectangle().getWidth(), 
					paddle.getRectangle().getHeight()))
			{ vy *= -1; }
		double dx = vx * deltaNanoTime;
		double dy = vy * deltaNanoTime;
		x += dx;
		y += dy;

		circle.setTranslateX(x - (circle.getLayoutX() + BALL_RADIUS));
		circle.setTranslateY(y - (circle.getLayoutY() + BALL_RADIUS));
	}
	
	/**
	 * Updates the x and y velocities of the ball
	 */
	private void updateVelocity (){
		//Update the x velocity if the ball is about to collide with the left or right walls
		if(x<=BALL_RADIUS){
			vx = Math.abs(vx);
			x = BALL_RADIUS;
		} else if (x>=GameImpl.WIDTH-BALL_RADIUS){
			vx = -1*Math.abs(vx);
			x = GameImpl.WIDTH-BALL_RADIUS;
		}
		//Update the y velocity if the ball is about to collide with the top or bottom wall
		if(y<=BALL_RADIUS){
			vy = Math.abs(vy);
			y = BALL_RADIUS;
		} else if (y>=GameImpl.HEIGHT-BALL_RADIUS){
			vy = -1*Math.abs(vy);
			y = GameImpl.HEIGHT-BALL_RADIUS;
			//Updates the counter for bottom wall collisions for the game loss condition
			bottomHits++;
		}
	}
	
	/**
	 * Increases the magnitude of the x and y velocity in either the negative or positive
	 * direction
	 */
	public void increaseVelocity(){
		vx = vx > 0 ? vx + ACCELERATION : vx - ACCELERATION;
		vy = vy > 0 ? vy + ACCELERATION : vy - ACCELERATION;
	}
	
	/**
	 * Determines whether the ball intersects the rectangle at the given x and y coordinates 
	 * with the given width and height
	 * @param x is the x value at the center of the rectangular area of intersection
	 * @param y is the y value at the center of the rectangular area of intersection
	 * @param width is the width of the rectangular area of intersection
	 * @param height is the height of the rectangular area of intersection
	 * @return boolean true if the ball collides with the object and false if it down not
	 */
	public boolean touching(double x, double y, double width, double height){
		return this.x - BALL_RADIUS < x + width/2 &&
			   this.x + BALL_RADIUS > x - width/2 &&
			   this.y - BALL_RADIUS < y + height/2 &&
			   this.y + BALL_RADIUS > y - height/2;
	}
}
