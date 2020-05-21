package com.serpest.rebuk.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class EditBookmarkController {

	@FXML
	private TextField nameField;

	@FXML
	private TextField pagesField;

	public String getNameFieldText() {
		return nameField.getText();
	}

	public void setNameFieldText(String text) {
		nameField.setText(text);
	}

	public void clearNameField() {
		nameField.clear();
	}

	public String getPagesFieldText() {
		return pagesField.getText();
	}

	public void setPagesFieldText(String text) {
		pagesField.setText(text);
	}

	public void clearPagesField() {
		pagesField.clear();
	}

	public void clearFields() {
		nameField.clear();
		pagesField.clear();
	}

}
