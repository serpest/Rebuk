package com.serpest.rebuk.services;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class OpenFileHandler {

	public static void openFileWithDefaultApp(File file) throws IOException {
		Desktop.getDesktop().open(file);
	}

	public static void openFileWithCustomCommand(File file, String command) throws IOException {
		Runtime.getRuntime().exec(new String[] {command, file.getAbsolutePath()});
	}

	private String customOpenFileCommand;

	public String getCustomOpenFileCommand() {
		return customOpenFileCommand;
	}

	public void setCustomOpenFileCommand(String customOpenFileCommand) {
		this.customOpenFileCommand = customOpenFileCommand;
	}

	public void openFile(String filename) throws IOException {
		File file = new File(filename);
		if (!file.exists())
			throw new FileNotFoundException();
		if (customOpenFileCommand == null || customOpenFileCommand.length() == 0)
			openFileWithDefaultApp(file);
		else
			openFileWithCustomCommand(file, customOpenFileCommand);
	}

}
