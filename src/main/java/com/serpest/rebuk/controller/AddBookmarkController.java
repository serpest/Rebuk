package com.serpest.rebuk.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.util.Pair;

public class AddBookmarkController {

	private Dialog<Pair<String, String>> addBookmarkDialog;

	@FXML
	private TextField nameField;

	@FXML
	private TextField pagesField;

	@FXML
	public void initialize() throws IOException {
		addBookmarkDialog = new Dialog<>();
		addBookmarkDialog.setTitle("Add bookmark");
		addBookmarkDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		addBookmarkDialog.setResultConverter(dialogButton -> {
			if (dialogButton == ButtonType.OK) {
				return new Pair<>(nameField.getText(), pagesField.getText());
			}
			return null;
		});
	}

	public void setRoot(Parent root) {
		addBookmarkDialog.getDialogPane().setContent(root);
	}

	public Dialog<Pair<String, String>> getAddBookmarkDialog() {
		return addBookmarkDialog;
	}

}
