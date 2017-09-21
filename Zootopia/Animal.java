import javafx.scene.media.*;           
import javafx.scene.layout.*; 
import javafx.scene.control.Label;
import javafx.scene.image.*;
	
public class Animal {
	
	//Instance variables
	/**
	 * X coordinate of animal's location
	 */
	private double x;
	/**
	 * Y coordinate of animal's location
	 */
	private double y;
	/**
	 * Image of animal to display
	 */
	private Image image;
	/**
	 * Label of the image to display
	 */
	private Label imageLabel;
	
	
	/**
	 * Constructor for an animal object
	 * @param x is the x coordinate of the animal
	 * @param y is the y coordinate of the animal
	 * @param image is the image of the animal to display
	 */
	public Animal (double x, double y, Image image){
		this.x = x;
		this.y = y;
		this.image =image;
		imageLabel = new Label("", new ImageView(image));
		imageLabel.setLayoutX(x - image.getWidth()/2);
		imageLabel.setLayoutY(y - image.getHeight()/2);
	}
	
	/**
	 * @return x coordinate of the animal
	 */
	public double getX(){
		return x;
	}
	
	/**
	 * @return y coordinate of the animal
	 */
	public double getY(){
		return y;
	}
	
	/**
	 * @return width of the image
	 */
	public double getWidth(){
		return image.getWidth();
	}
	
	/**
	 * @return height of the image
	 */
	public double getHeight(){
		return image.getHeight();
	}
	
	/**
	 * @return image label of the image
	 */
	public Label getLabel (){
		return imageLabel;
	}

}
