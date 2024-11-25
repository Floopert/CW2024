package com.example.demo.controller;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

import com.example.demo.eventListeners.LevelEventListener;
import com.example.demo.levels.LevelParent;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class Controller implements LevelEventListener {

	private static final String LEVEL_ONE_CLASS_NAME = "com.example.demo.levels.LevelOne";
	private final Stage stage;

	public Controller(Stage stage) {
		this.stage = stage;
	}

	public void launchGame() throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException  {

			stage.show();

			try {
				goToLevel(LEVEL_ONE_CLASS_NAME);
			}
			catch (ClassNotFoundException | NoSuchMethodException | SecurityException |
			InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e){
				e.printStackTrace();
			}
			
	}

	private void goToLevel(String className) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
			
				Class<?> myClass = Class.forName(className);
				Method method = myClass.getMethod("getInstance", double.class, double.class);
				LevelParent myLevel = (LevelParent) method.invoke(null, stage.getHeight(), stage.getWidth());
				myLevel.addEventListener(this);
				Scene scene = myLevel.initializeScene();
				stage.setScene(scene);
				myLevel.startGame();

	}
	

	@Override
	public void changeLevel(LevelParent currentLevel, String levelName){
		try {
			currentLevel.removeEventListener(this);
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
