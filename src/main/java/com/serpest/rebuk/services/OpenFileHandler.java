package com.serpest.rebuk.services;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class OpenFileHandler {

	public static void openFileWithDefaultApp(File file) throws IOException {
		Desktop.getDesktop().open(file);
	}

}
