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

	private static Controller instance;
	private final Stage stage;
	private static Scene scene;

	private Controller(Stage stage) {
		this.stage = stage;
	}

	public static Controller getInstance(Stage stage) {
		if (instance == null) {
			instance = new Controller(stage);
		}
		return instance;
	}



	public void launchGame() throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException  {

			stage.show();

			try {
				goToFXML("menu");
			}
			catch (SecurityException | IllegalArgumentException | IOException e){
				e.printStackTrace();
			}
			
	}





	public void goToFXML(String fxml) throws IOException {
        scene = new Scene(loadFXML(fxml), stage.getWidth(), stage.getHeight());
		stage.setScene(scene);
	}

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Controller.class.getResource("/com/example/demo/fxml/" + fxml + ".fxml"));
		Parent root = fxmlLoader.load();
		
		FxmlController fxmlController = fxmlLoader.getController();
		fxmlController.setMainController(instance);

		return root;
	}



	
	private void goToLevel(String className) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
			
				Class<?> myClass = Class.forName(className);
				Method method = myClass.getMethod("getInstance", double.class, double.class);
				LevelParent myLevel = (LevelParent) method.invoke(null, stage.getHeight(), stage.getWidth());
				myLevel.addEventListener(this);
				scene = myLevel.initializeScene();
				stage.setScene(scene);
				myLevel.startGame();

	}
	

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


}
