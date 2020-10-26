package com.serpest.rebuk.controller;

import java.util.Optional;

import com.serpest.rebuk.model.Book;
import com.serpest.rebuk.model.Bookmark;

import javafx.scene.control.Dialog;
import javafx.stage.Stage;
import javafx.util.Pair;

public class ControllersMediator {

	private Stage bookOverviewStage;
	private BookOverviewController bookOverviewController;

	private Dialog<Pair<String, String>> editBookmarkDialog;
	private EditBookmarkController editBookmarkController;

	public Stage getBookOverviewStage() {
		return bookOverviewStage;
	}

	public void setBookOverviewStage(Stage bookOverviewStage) {
		this.bookOverviewStage = bookOverviewStage;
	}

	public BookOverviewController getBookOverviewController() {
		return bookOverviewController;
	}

	public void setBookOverviewController(BookOverviewController bookOverviewController) {
		this.bookOverviewController = bookOverviewController;
	}

	public Dialog<Pair<String, String>> getEditBookmarkDialog() {
		return editBookmarkDialog;
	}

	public void setEditBookmarkDialog(Dialog<Pair<String, String>> editBookmarkDialog) {
		this.editBookmarkDialog = editBookmarkDialog;
	}

	public EditBookmarkController getEditBookmarkController() {
		return editBookmarkController;
	}

	public void setEditBookmarkController(EditBookmarkController editBookmarkController) {
		this.editBookmarkController = editBookmarkController;
	}

	Optional<Pair<String, String>> showAddBookmarkDialog() {
		editBookmarkController.clearFields();
		editBookmarkDialog.setTitle("Add bookmark");
		Optional<Pair<String, String>> result = editBookmarkDialog.showAndWait();
		return result;
	}

	Optional<Pair<String, String>> showEditBookmarkDialog(Bookmark bookmark) {
		editBookmarkController.clearFields();
		editBookmarkController.setNameFieldText(bookmark.getName());
		editBookmarkController.setPagesFieldText(bookmark.getPages());
		editBookmarkDialog.setTitle("Edit bookmark");
		Optional<Pair<String, String>> result = editBookmarkDialog.showAndWait();
		return result;
	}

	void showBookOverview(Book book) {
		bookOverviewController.setBook(book);
		bookOverviewStage.showAndWait();
	}

}
