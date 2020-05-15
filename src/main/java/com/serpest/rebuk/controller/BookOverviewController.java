package com.serpest.rebuk.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

import com.serpest.rebuk.model.Book;
import com.serpest.rebuk.model.Book.Status;
import com.serpest.rebuk.model.Bookmark;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import javafx.util.Pair;

public class BookOverviewController {

	private class ActionsCell extends TableCell<Bookmark, Button[]> {

		private Button deleteButton;

		private ActionsCell() {
			deleteButton = new Button("Delete");
			deleteButton.setOnAction((event) -> handleDeleteBookmarkAction(event));
		}

		@Override
		protected void updateItem(Button[] buttons, boolean empty) {
			super.updateItem(buttons, empty);
			if (empty)
				setGraphic(null);
			else
				setGraphic(new HBox(deleteButton));
		}

	}

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
	private TableView<Bookmark> bookmarksTableView;
	@FXML
	private TableColumn<Bookmark, String> nameColumn;
	@FXML
	private TableColumn<Bookmark, String> pagesColumn;
	@FXML
	private TableColumn<Bookmark, LocalDateTime> dateTimeColumn;
	@FXML
	private TableColumn<Bookmark, Button[]> actionsColumn;
	@FXML
	private TextArea notesTextArea;
	@FXML
	private ChoiceBox<Book.Status> statusChoiceBox;

	@FXML
	public void initialize() throws IOException {
		statusChoiceBox.getItems().setAll(Status.values());
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		pagesColumn.setCellValueFactory(new PropertyValueFactory<>("pages"));
		dateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
		actionsColumn.setCellFactory(new Callback<>() {
			@Override
			public TableCell<Bookmark, Button[]> call(TableColumn<Bookmark, Button[]> column) {
				return new ActionsCell();
			}
		});
	}

	@FXML
	public void handleBackButtonAction(ActionEvent event) {
		scene.setRoot(libraryRoot);
	}

	@FXML
	public void handleSaveButtonAction(ActionEvent event) {
		book.setFilename(filenameField.getText());
		book.setTitle(titleField.getText());
		book.setAuthors(authorsField.getText());
		book.setStatus(statusChoiceBox.getValue());
		book.setNotes(notesTextArea.getText());
		book.setBookmarks(bookmarksTableView.getItems());

		libraryController.saveLibrary();
		libraryController.upgradeBooksTableView();
	}

	@FXML
	public void handleAddBookmarkButtonAction(ActionEvent event) {
		Optional<Pair<String, String>> result = addBookmarkDialog.showAndWait();
		if (result.isPresent()) {
			Bookmark bookmark = new Bookmark(result.get().getKey(), result.get().getValue());
			book.addBookmark(bookmark);
			libraryController.saveLibrary();
			addBookmarkToTableView(bookmark);
			libraryController.upgradeBooksTableView();
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

	private void addBookmarkToTableView(Bookmark bookmark) {
		bookmarksTableView.getItems().add(bookmark);
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

	public void setAddBookmarkDialog(Dialog<Pair<String, String>> addBookmarkDialog) {
		this.addBookmarkDialog = addBookmarkDialog;
	}

	private void handleDeleteBookmarkAction(ActionEvent event) {
		int elementIndex = getActionsCellIndex(event);
		book.getBookmarks().remove(elementIndex);
		libraryController.saveLibrary();
		deleteTableViewBookmark(elementIndex);
		libraryController.upgradeBooksTableView();
	}

	private int getActionsCellIndex(ActionEvent event) {
		return ((ActionsCell) ((Node) event.getSource()).getParent().getParent()).getIndex();
	}

	private void deleteTableViewBookmark(int index) {
		bookmarksTableView.getItems().remove(index);
	}

}
