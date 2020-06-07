package com.serpest.rebuk.services;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import com.serpest.rebuk.MainApp;

public class OpenFileHandler {

	private static final String CUSTOM_OPEN_FILE_COMMAND_PROPERTY = "customOpenFileCommand";

	public static void openFile(File file) throws IOException {
		if (MainApp.options.containsKey(CUSTOM_OPEN_FILE_COMMAND_PROPERTY)) {
			openFileWithCustomCommand(file, MainApp.options.getProperty(CUSTOM_OPEN_FILE_COMMAND_PROPERTY));
		} else
			openFileWithDefaultApp(file);
	}

	public static void openFileWithDefaultApp(File file) throws IOException {
		Desktop.getDesktop().open(file);
	}

	public static void openFileWithCustomCommand(File file, String command) throws IOException {
		String completeCommand = command + " \"" + file.getAbsolutePath() + "\"";
		Runtime.getRuntime().exec(completeCommand);
	}

}
