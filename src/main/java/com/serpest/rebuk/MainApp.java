package com.serpest.rebuk;

import com.serpest.rebuk.controller.MainAppController;
import com.serpest.rebuk.load.ViewFactory;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Scene primaryScene = ViewFactory.createLibraryView();
		MainAppController mainAppController = new MainAppController(primaryStage, primaryScene);
		mainAppController.showPrimaryStage();
	}

}
