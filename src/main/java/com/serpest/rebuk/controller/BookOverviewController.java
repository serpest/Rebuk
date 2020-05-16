package com.serpest.rebuk.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.serpest.rebuk.controller.cells.DeleteButtonTableCell;
import com.serpest.rebuk.model.Book;
import com.serpest.rebuk.model.Book.Status;
import com.serpest.rebuk.model.Bookmark;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Pair;

public class BookOverviewController {

	private Book book;

	private Scene scene;
	private Parent libraryRoot;
	private Dialog<Pair<String, String>> addBookmarkDialog;
	private LibraryController libraryController;

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

	@FXML
	public void initialize() throws IOException {
		statusChoiceBox.getItems().setAll(Status.values());
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		pagesColumn.setCellValueFactory(new PropertyValueFactory<>("pages"));
		dateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
		actionsColumn.setCellFactory(cell -> new DeleteButtonTableCell<Bookmark>((event, index) -> handleDeleteBookmarkAction(event, index)));
	}

	@FXML
	public void handleBackButtonAction(ActionEvent event) {
		libraryController.upgradeBooksTableView();
		scene.setRoot(libraryRoot);
	}

	@FXML
	public void handleSaveButtonAction(ActionEvent event) {
		book.setFilename(filenameField.getText());
		book.setTitle(titleField.getText());
		book.setAuthors(authorsField.getText());
		book.setStatus(statusChoiceBox.getValue());
		book.setNotes(notesTextArea.getText());
		List<Bookmark> bookmarksWithoutListeners = new ArrayList<>(Arrays.asList(bookmarksTableView.getItems().toArray(new Bookmark[bookmarksTableView.getItems().size()])));
		book.setBookmarks(bookmarksWithoutListeners);

		libraryController.saveLibrary();
	}

	@FXML
	public void handleAddBookmarkButtonAction(ActionEvent event) {
		Optional<Pair<String, String>> result = addBookmarkDialog.showAndWait();
		if (result.isPresent()) {
			Bookmark bookmark = new Bookmark(result.get().getKey(), result.get().getValue());
			book.addBookmark(bookmark);
			libraryController.saveLibrary();
			addBookmarkToTableView(bookmark);
		}
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}

	public void setLibraryRoot(Parent libraryRoot) {
		this.libraryRoot = libraryRoot;
	}

	public void setLibraryController(LibraryController libraryController) {
		this.libraryController = libraryController;
	}

	void setBook(Book book) {
		this.book = book;
		upgradeBookView();
	}

	private void upgradeBookView() {
		filenameField.setText(book.getFilename());
		titleField.setText(book.getTitle());
		authorsField.setText(book.getAuthors());
		statusChoiceBox.setValue(book.getStatus());
		notesTextArea.setText(book.getNotes());
		upgradeBookmarksTableView();
	}

	private void upgradeBookmarksTableView() {
		bookmarksTableView.getItems().clear();
		bookmarksTableView.getItems().addAll(book.getBookmarks());
	}

	private void addBookmarkToTableView(Bookmark bookmark) {
		bookmarksTableView.getItems().add(bookmark);
	}

	private void deleteTableViewBookmark(int index) {
		bookmarksTableView.getItems().remove(index);
	}

	public void setAddBookmarkDialog(Dialog<Pair<String, String>> addBookmarkDialog) {
		this.addBookmarkDialog = addBookmarkDialog;
	}

	private void handleDeleteBookmarkAction(ActionEvent event, int elementIndex) {
		book.getBookmarks().remove(elementIndex);
		libraryController.saveLibrary();
		deleteTableViewBookmark(elementIndex);
	}

}
