import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.layout.*;

public class GameApp extends Application {
	
	/**
	 * Constructor (same as the default)
	 */
	public GameApp () {
	}

	/**
	 * Creates a window with the game on it
	 * @param primaryStage is a stage to build on
	 */
	public void start (Stage primaryStage) {
		Game game = new GameImpl();
		primaryStage.setTitle(game.getName());
		primaryStage.setScene(new Scene(game.getPane(), GameImpl.WIDTH, GameImpl.HEIGHT));
                primaryStage.show();
	}

	/**
	 * Launches the game window
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
