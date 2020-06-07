package com.serpest.rebuk.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.serpest.rebuk.model.Book;
import com.serpest.rebuk.model.Book.Status;
import com.serpest.rebuk.model.Bookmark;
import com.serpest.rebuk.services.OpenFileHandler;
import com.serpest.rebuk.view.RebukAlert;
import com.serpest.rebuk.view.cells.EditDeleteButtonTableCell;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Pair;

public class BookOverviewController {

	private Book book;

	private Dialog<Pair<String, String>> editBookmarkDialog;
	private EditBookmarkController editBookmarkController;

	@FXML
	private TextField filenameField;
	@FXML
	private TextField titleField;
	@FXML
	private TextField authorsField;
	@FXML
	private ChoiceBox<Book.Status> statusChoiceBox;
	@FXML
	private TextArea notesTextArea;
	@FXML
	private TableView<Bookmark> bookmarksTableView;
	@FXML
	private TableColumn<Bookmark, String> nameColumn;
	@FXML
	private TableColumn<Bookmark, String> pagesColumn;
	@FXML
	private TableColumn<Bookmark, LocalDateTime> dateTimeColumn;
	@FXML
	private TableColumn<Bookmark, Void> actionsColumn;

	public BookOverviewController(Pair<Dialog<Pair<String, String>>, EditBookmarkController> editBookmarkView) {
		this.editBookmarkDialog = editBookmarkView.getKey();
		this.editBookmarkController = editBookmarkView.getValue();
	}

	@FXML
	public void initialize() throws IOException {
		statusChoiceBox.getItems().setAll(Status.values());
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		pagesColumn.setCellValueFactory(new PropertyValueFactory<>("pages"));
		dateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("formattedDateTime"));
		actionsColumn.setCellFactory(cell -> new EditDeleteButtonTableCell<Bookmark>(
				(event, index) -> handleEditBookmarkAction(event, index),
				(event, index) -> handleDeleteBookmarkAction(event, index)));
	}

	@FXML
	public void handleSaveButtonAction(ActionEvent event) {
		book.setFilename(filenameField.getText());
		book.setTitle(titleField.getText());
		book.setAuthors(authorsField.getText());
		book.setStatus(statusChoiceBox.getValue());
		book.setNotes(notesTextArea.getText());
		List<Bookmark> bookmarksWithoutListeners = new ArrayList<>(Arrays
				.asList(bookmarksTableView.getItems().toArray(new Bookmark[bookmarksTableView.getItems().size()])));
		book.setBookmarks(bookmarksWithoutListeners);
	}

	@FXML
	public void handleOpenButtonAction(ActionEvent event) {
		File file = new File(book.getFilename());
		if (file.exists()) {
			try {
				OpenFileHandler.openFile(file);
			} catch (IOException exc) {
				RebukAlert alert = new RebukAlert(AlertType.WARNING, "File not opened", "Rebuk had a problem opening the file.");
				alert.showAndWait();
			}
		} else {
			RebukAlert alert = new RebukAlert(AlertType.WARNING, "File not opened", "The file doesn't exists.");
			alert.showAndWait();
		}
	}

	@FXML
	public void handleAddBookmarkButtonAction(ActionEvent event) {
		editBookmarkController.clearFields();
		editBookmarkDialog.setTitle("Add bookmark");
		Optional<Pair<String, String>> result = editBookmarkDialog.showAndWait();
		if (result.isPresent()) {
			Bookmark bookmark = new Bookmark(result.get().getKey(), result.get().getValue());
			book.addBookmark(bookmark);
			addBookmarkToTableView(bookmark);
		}
	}

	void setBook(Book book) {
		this.book = book;
		updateBookView();
	}

	private void updateBookView() {
		filenameField.setText(book.getFilename());
		titleField.setText(book.getTitle());
		authorsField.setText(book.getAuthors());
		statusChoiceBox.setValue(book.getStatus());
		notesTextArea.setText(book.getNotes());
		updateBookmarksTableView();
	}

	private void updateBookmarksTableView() {
		bookmarksTableView.getItems().clear();
		bookmarksTableView.getItems().addAll(book.getBookmarks());
	}

	private void addBookmarkToTableView(Bookmark bookmark) {
		bookmarksTableView.getItems().add(bookmark);
	}

	private void deleteTableViewBookmark(int index) {
		bookmarksTableView.getItems().remove(index);
	}

	private void handleDeleteBookmarkAction(ActionEvent event, int elementIndex) {
		book.getBookmarks().remove(elementIndex);
		deleteTableViewBookmark(elementIndex);
	}

	private void handleEditBookmarkAction(ActionEvent event, int elementIndex) {
		Bookmark bookmark = bookmarksTableView.getItems().get(elementIndex);
		editBookmarkController.clearFields();
		editBookmarkController.setNameFieldText(bookmark.getName());
		editBookmarkController.setPagesFieldText(bookmark.getPages());
		editBookmarkDialog.setTitle("Edit bookmark");
		Optional<Pair<String, String>> result = editBookmarkDialog.showAndWait();
		if (result.isPresent()) {
			bookmark.setName(result.get().getKey());
			bookmark.setPages(result.get().getValue());
			book.addBookmark(bookmark);
			addBookmarkToTableView(bookmark);
		}
	}

}
