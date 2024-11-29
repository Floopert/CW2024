package com.example.demo.controller;

import java.lang.reflect.Method;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import com.example.demo.eventListeners.LevelEventListener;
import com.example.demo.levels.LevelParent;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class Controller implements LevelEventListener {

	// -----------------------------------------------------------------------------------------------
	// Constants
	private static final String FXML_FOLDER_PATH = "/com/example/demo/fxml/";
	private static final String MAIN_MENU_FXML_NAME = "menu";


	// -----------------------------------------------------------------------------------------------
	// Private Fields
	private static Controller instance;
	private final Stage stage;
	private static Scene scene;



	// -----------------------------------------------------------------------------------------------
	// Constructor

	private Controller(Stage stage) {
		this.stage = stage;
	}

	public static Controller getInstance(Stage stage) {
		if (instance == null) {
			instance = new Controller(stage);
		}
		return instance;
	}


	// -----------------------------------------------------------------------------------------------
	// Function for game launch

	public void launchGame() throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException  {

			stage.show();

			try {
				goToFXML(null, null, MAIN_MENU_FXML_NAME);
			}
			catch (SecurityException | IllegalArgumentException | IOException e){
				e.printStackTrace();
			}
			
	}


	// -----------------------------------------------------------------------------------------------
	// Functions to navigate through FXML pages


	/**
	 * This method is used to change the scene to a fxml page. E.g. Main Menu, Lose Game page, Win Game page.
	 * 
	 * @param currentLevel
	 * The LevelParent object reference to the current level prior to switching to the fxml page.
	 * If currentLevel is null, it means that there was no level prior to switching to the fxml page. Such as during the start of the game.
	 * Or when switching between fxml pages.
	 * Or it means that the level does not need to be destroyed. (Used during pause game)
	 * 
	 * @param levelToReturn
	 * The String containing the class path of the level to return to if the fxml page has a button that returns to the level.
	 * Such as restarting the level after a loss.
	 * 
	 * @param fxml
	 * The String containing the name of the fxml file to switch to.
	 */
	@Override
	public void goToFXML(LevelParent currentLevel, String levelToReturn, String fxml) throws IOException {
        
		if (currentLevel != null){
			currentLevel.removeEventListener(this);
			currentLevel.destroyInstance();
		}
		
		scene = new Scene(loadFXML(levelToReturn, fxml), stage.getWidth(), stage.getHeight());
		stage.setScene(scene);
	}

	
	/**
	 * This method is used to load the fxml file and return the Parent object.
	*/ 
    private static Parent loadFXML(String levelToReturn, String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Controller.class.getResource(FXML_FOLDER_PATH + fxml + ".fxml"));
		Parent root = fxmlLoader.load();
		
		FxmlController fxmlController = fxmlLoader.getController();
		fxmlController.setMainController(instance);

		if (levelToReturn != null){
			fxmlController.setLevelToReturn(levelToReturn);
		}

		return root;
	}




	// -----------------------------------------------------------------------------------------------
	// Functions to get instance of a level
	

	public LevelParent getLevelInstance(String className) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
			
				Class<?> myClass = Class.forName(className);
				Method method = myClass.getMethod("getInstance", double.class, double.class);
				LevelParent myLevel = (LevelParent) method.invoke(null, stage.getHeight(), stage.getWidth());
				return myLevel;
	}

	


	// -----------------------------------------------------------------------------------------------
	// Functions to navigate through levels

	@Override
	public void changeLevel(LevelParent currentLevel, String levelName){
		try {
			if (currentLevel != null){
				currentLevel.removeEventListener(this);
				currentLevel.destroyInstance();
			}
			goToLevel(levelName);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			
			e.printStackTrace();
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText(e.getClass().toString());
			alert.show();
		}
	}


	private void goToLevel(String className) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
				
				LevelParent myLevel = getLevelInstance(className);
				myLevel.addEventListener(this);
				scene = myLevel.initializeScene();
				stage.setScene(scene);
				myLevel.startGame();

	}
	

	public void resumeLevel(String currentLevel){
		
		try {
			LevelParent myLevel = getLevelInstance(currentLevel);
			scene = myLevel.getScene();
			stage.setScene(scene);
			myLevel.resumeGame();
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			
			e.printStackTrace();

		}
		
	}

}
