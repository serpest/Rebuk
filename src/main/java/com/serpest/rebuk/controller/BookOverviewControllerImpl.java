package com.serpest.rebuk.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.serpest.rebuk.model.Book;
import com.serpest.rebuk.model.BookStatus;
import com.serpest.rebuk.model.Bookmark;
import com.serpest.rebuk.model.LibraryFactory;
import com.serpest.rebuk.services.OpenFileHandler;
import com.serpest.rebuk.view.RebukAlert;
import com.serpest.rebuk.view.cells.EditDeleteButtonTableCell;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Pair;

public class BookOverviewControllerImpl implements BookOverviewController {

	private Book book;
	
	private ControllersMediator controllersMediator;

	private LibraryFactory libraryFactory;

	private OpenFileHandler openFileHandler;

	@FXML
	private TextField filenameField;
	@FXML
	private TextField titleField;
	@FXML
	private TextField authorsField;
	@FXML
	private ChoiceBox<BookStatus> bookStatusChoiceBox;
	@FXML
	private TextField lastModifiedDateTimeField;
	@FXML
	private TextArea notesTextArea;
	@FXML
	private TableView<Bookmark> bookmarksTableView;
	@FXML
	private TableColumn<Bookmark, String> bookmarksNameColumn;
	@FXML
	private TableColumn<Bookmark, String> bookmarksPagesColumn;
	@FXML
	private TableColumn<Bookmark, String> bookmarksLastModifiedDateTimeColumn;
	@FXML
	private TableColumn<Bookmark, Void> bookmarksActionsColumn;

	@Override
	@FXML
	public void initialize() throws IOException {
		bookStatusChoiceBox.getItems().setAll(BookStatus.values());
		bookmarksNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		bookmarksPagesColumn.setCellValueFactory(new PropertyValueFactory<>("pages"));
		bookmarksLastModifiedDateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("lastModifiedDateTime"));
		bookmarksActionsColumn.setCellFactory(cell -> new EditDeleteButtonTableCell<Bookmark>(
				(event, index) -> handleEditBookmarkAction(event, index),
				(event, index) -> handleDeleteBookmarkAction(event, index)));
	}

	@Override
	@FXML
	public void handleSaveButtonAction(ActionEvent event) {
		book.setFilename(filenameField.getText());
		book.setTitle(titleField.getText());
		book.setAuthors(authorsField.getText());
		book.setStatus(bookStatusChoiceBox.getValue());
		book.setNotes(notesTextArea.getText());
		Set<Bookmark> bookmarksWithoutListeners = new HashSet<>(bookmarksTableView.getItems());
		book.setBookmarks(bookmarksWithoutListeners);
	}

	@Override
	@FXML
	public void handleOpenButtonAction(ActionEvent event) {
		try {
			openFileHandler.openFile(book.getFilename());
		} catch (FileNotFoundException exc) {
			RebukAlert alert = new RebukAlert(AlertType.WARNING, "File doesn't exist", "Rebuk had a problem opening the file.");
			alert.showAndWait();
		} catch (IOException exc) {
			RebukAlert alert = new RebukAlert(AlertType.WARNING, "File not opened", "Rebuk had a problem opening the file.");
			alert.showAndWait();
		}
	}

	@Override
	@FXML
	public void handleAddBookmarkButtonAction(ActionEvent event) {
		Optional<Pair<String, String>> result = controllersMediator.showAddBookmarkDialog();
		if (result.isPresent()) {
			Bookmark bookmark = libraryFactory.getBookmark();
			bookmark.setName(result.get().getKey());
			bookmark.setPages(result.get().getValue());
			book.addBookmark(bookmark);
		}
	}

	@Override
	public void updateView() {
		filenameField.setText(book.getFilename());
		titleField.setText(book.getTitle());
		authorsField.setText(book.getAuthors());
		bookStatusChoiceBox.setValue(book.getStatus());
		notesTextArea.setText(book.getNotes());
		lastModifiedDateTimeField.setText(book.getLastModifiedDateTime().toString());
		bookmarksTableView.getItems().clear();
		bookmarksTableView.getItems().addAll(book.getBookmarks());
	}

	@Override
	public ControllersMediator getControllersMediator() {
		return controllersMediator;
	}

	@Override
	public void setControllersMediator(ControllersMediator controllersMediator) {
		this.controllersMediator = controllersMediator;
	}

	@Override
	public LibraryFactory getLibraryFactory() {
		return libraryFactory;
	}

	@Override
	public void setLibraryFactory(LibraryFactory libraryFactory) {
		this.libraryFactory = libraryFactory;
	}

	@Override
	public OpenFileHandler getOpenFileHandler() {
		return openFileHandler;
	}

	@Override
	public void setOpenFileHandler(OpenFileHandler openFileHandler) {
		this.openFileHandler = openFileHandler;
	}

	@Override
	public Book getBook() {
		return book;
	}

	@Override
	public void setBook(Book book) {
		this.book = book;
	}

	private void handleDeleteBookmarkAction(ActionEvent event, int elementIndex) {
		Bookmark bookmark = bookmarksTableView.getItems().get(elementIndex);
		book.getBookmarks().remove(bookmark);
	}


	private void handleEditBookmarkAction(ActionEvent event, int elementIndex) {
		Bookmark bookmark = bookmarksTableView.getItems().get(elementIndex);
		Optional<Pair<String, String>> result = controllersMediator.showEditBookmarkDialog(bookmark);
		if (result.isPresent()) {
			bookmark.setName(result.get().getKey());
			bookmark.setPages(result.get().getValue());
		}
	}


}
