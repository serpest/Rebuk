package com.serpest.rebuk.controller;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainAppController {

	public final static Image APP_ICON = new Image(MainAppController.class.getResource("/img/icon.png").toString());

	Stage primaryStage;

	public MainAppController(Stage primaryStage, Scene primaryScene) {
		this.primaryStage = primaryStage;
		initPrimaryStage(primaryScene);
	}

	public void showPrimaryStage() {
		primaryStage.show();
	}

	private void initPrimaryStage(Scene primaryScene) {
		primaryStage.setTitle("Rebuk");
		primaryStage.getIcons().add(APP_ICON);
		primaryStage.setScene(primaryScene);
	}

}
