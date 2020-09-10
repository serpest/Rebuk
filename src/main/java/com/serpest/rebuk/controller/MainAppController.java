package com.serpest.rebuk.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainAppController {

	public final static Image APP_ICON = new Image(MainAppController.class.getResource("/img/icon.png").toString());

	public final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("d MMM yyyy HH:mm");

	private static final String PROPERTIES_FILE = "data" + File.separator + "Rebuk_options.properties";

	public static Properties options;

	static {
		try {
			options = new Properties();
			loadProperties();
		} catch (IOException exc) {}
	}

	private static void loadProperties() throws IOException {
		FileInputStream inStream = new FileInputStream(PROPERTIES_FILE);
		options.load(inStream);
		inStream.close();
	}

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
