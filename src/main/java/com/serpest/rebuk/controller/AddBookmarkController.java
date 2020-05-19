package com.serpest.rebuk.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AddBookmarkController {

	@FXML
	private TextField nameField;

	@FXML
	private TextField pagesField;

	public String getNameFieldText() {
		return nameField.getText();
	}

	public void clearNameField() {
		nameField.clear();
	}

	public String getPagesFieldText() {
		return pagesField.getText();
	}

	public void clearPagesField() {
		pagesField.clear();
	}

}
