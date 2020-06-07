package com.serpest.rebuk;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.serpest.rebuk.controller.MainAppController;
import com.serpest.rebuk.load.ViewFactory;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

	private static final String PROPERTIES_FILE = "data" + File.separator + "Rebuk_options.properties";

	public static Properties options;

	static {
		try {
			options = new Properties();
			loadProperties();
		} catch (IOException exc) {}
	}

	public static void main(String[] args) {
		launch(args);
	}

	private static void loadProperties() throws IOException {
		FileInputStream inStream = new FileInputStream(PROPERTIES_FILE);
		options.load(inStream);
		inStream.close();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Scene primaryScene = ViewFactory.createLibraryView();
		MainAppController mainAppController = new MainAppController(primaryStage, primaryScene);
		mainAppController.showPrimaryStage();
	}

}
