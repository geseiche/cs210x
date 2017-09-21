import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.animation.AnimationTimer;
import javafx.scene.input.MouseEvent;
import javafx.event.*;
import java.util.*;
import javafx.scene.media.*;            
import javafx.scene.layout.*; 
import javafx.scene.control.Label;
import javafx.scene.image.*;

public class GameImpl extends Pane implements Game {
	/**
	 * Defines different states of the game.
	 */
	public enum GameState {
		WON, LOST, ACTIVE, NEW
	}

	// Constants
	/**
	 * The width of the game board.
	 */
	public static final int WIDTH = 400;
	/**
	 * The height of the game board.
	 */
	public static final int HEIGHT = 600;
	/**
	 * The number of rows of images
	 */
	final int animalRows = 4;
	/**
	 * The number of columns of images
	 */
	final int animalCols = 4;

	// Instance variables
	private Ball ball;
	private Paddle paddle;
	private LinkedList<Animal> animals;

	/**
	 * Constructs a new GameImpl.
	 */
	public GameImpl () {
		setStyle("-fx-background-color: white;");

		restartGame(GameState.NEW);
	}
	
	/**
	 * @return the name of the game
	 */
	public String getName () {
		return "Zutopia";
	}

	/**
	 * @return this Pane object
	 */
	public Pane getPane () {
		return this;
	}

	/**
	 * Resets the game back to the start by creating a new ball, new images, a new paddle, an event handler to 
	 * start the game, and an event handler to move the paddle
	 * @param the final state of the most recent game; used to determine the message displayed on the new game
	 */
	private void restartGame (GameState state) {
		getChildren().clear();  // remove all components from the game

		// Create and add ball
		ball = new Ball();
		getChildren().add(ball.getCircle());  // Add the ball to the game board

		// Create animal images
		final String[] animalFiles = {"duck.jpg", "goat.jpg", "horse.jpg"};
		Image[] animalImages = new Image[animalFiles.length];
		for(int i=0; i<animalFiles.length; i++){
			animalImages[i] = new Image(getClass().getResourceAsStream(animalFiles[i]));
		}
		//Randomly create animals
		animals = new LinkedList<Animal> ();
		Random random = new Random ();
		for(int x=WIDTH/(animalRows+1); x<=WIDTH*animalRows/(animalRows+1);x+=WIDTH/(animalRows+1)){
			for(int y=HEIGHT/((1+animalCols)*2); y<=HEIGHT*(animalCols)/(2*(animalCols+1));y+=HEIGHT/((animalCols+1)*2)){
				animals.add(new Animal (x, y, animalImages[random.nextInt(animalImages.length)]));
			}
		}
		//Add animals
		for(int i =0; i<animals.size();i++){
			getChildren().add(animals.get(i).getLabel());
		}
		
		// Create and add paddle
		paddle = new Paddle();
		getChildren().add(paddle.getRectangle());  // Add the paddle to the game board

		// Add start message
		final String message;
		if (state == GameState.LOST) {
			message = "Game Over\n";
		} else if (state == GameState.WON) {
			message = "You won!\n";
		} else {
			message = "";
		}
		final Label startLabel = new Label(message + "Click mouse to start");
		startLabel.setLayoutX(WIDTH / 2 - 50);
		startLabel.setLayoutY(HEIGHT / 2 + 100);
		getChildren().add(startLabel);

		// Add event handler to start the game
		setOnMouseClicked(new EventHandler<MouseEvent> () {
			@Override
			public void handle (MouseEvent e) {
				GameImpl.this.setOnMouseClicked(null);

				// As soon as the mouse is clicked, remove the startLabel from the game board
				getChildren().remove(startLabel);
				run();
			}
		});

		// Add another event handler to steer paddle
		setOnMouseMoved(new EventHandler<MouseEvent>(){
			public void handle (MouseEvent e){
				final double mouseX =e.getSceneX();
				final double mouseY =e.getSceneY();
				paddle.moveTo(mouseX, mouseY);
			}
		});
		
	}

	/**
	 * Begins the game-play by creating and starting an AnimationTimer.
	 */
	public void run () {
		// Instantiate and start an AnimationTimer to update the component of the game.
		new AnimationTimer () {
			private long lastNanoTime = -1;
			public void handle (long currentNanoTime) {
				if (lastNanoTime >= 0) {  // Necessary for first clock-tick.
					GameState state;
					if ((state = runOneTimestep(currentNanoTime - lastNanoTime)) != GameState.ACTIVE) {
						// Once the game is no longer ACTIVE, stop the AnimationTimer.
						stop();
						// Restart the game, with a message that depends on whether
						// the user won or lost the game.
						restartGame(state);
					}
				}
				// Keep track of how much time actually transpired since the last clock-tick.
				lastNanoTime = currentNanoTime;
			}
		}.start();
	}

	/**
	 * Updates the state of the game at each timestep. In particular, this method should
	 * move the ball, check if the ball collided with any of the animals, walls, or the paddle, etc.
	 * @param deltaNanoTime how much time (in nanoseconds) has transpired since the last update
	 * @return the current game state
	 */
	public GameState runOneTimestep (long deltaNanoTime) {
		ball.updatePosition(deltaNanoTime, paddle);
		Iterator<Animal> it = animals.iterator();
		//Checks whether the ball has collided with any animals and if so removes the animal from the pane
		while(it.hasNext()){
			Animal animal = it.next();
			if(ball.touching(animal.getX(), animal.getY(), animal.getWidth(), animal.getHeight())){
				getChildren().remove(animal.getLabel());
				it.remove();
				ball.increaseVelocity();
			}
			
		}
		//Determines the game state to return
		if(animals.isEmpty()){
			return GameState.WON;
		}
		if(ball.getBottomHits() >= 5){
			return GameState.LOST;
		}
		return GameState.ACTIVE;
	}
}
