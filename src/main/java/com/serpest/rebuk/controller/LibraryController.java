package com.serpest.rebuk.controller;

import com.google.gson.JsonSyntaxException;
import com.serpest.rebuk.model.Book;
import com.serpest.rebuk.model.Library;
import com.serpest.rebuk.services.LibraryFileHandler;
import com.serpest.rebuk.services.OpenFileHandler;
import com.serpest.rebuk.view.RebukAlert;
import com.serpest.rebuk.view.cells.OverviewOpenDeleteButtonsTableCell;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Pair;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

public class LibraryController {

	private final static String LIBRARY_FILENAME = "data" + File.separator + "library.json";

	private Library library;
	private LibraryFileHandler libraryFileHandler;

	private Stage bookOverviewStage;
	private BookOverviewController bookOverviewController;

	@FXML
	private TableView<Book> booksTableView;
	@FXML
	private TableColumn<Book, String> filenameColumn;
	@FXML
	private TableColumn<Book, String> titleColumn;
	@FXML
	private TableColumn<Book, String> authorsColumn;
	@FXML
	private TableColumn<Book, Book.Status> statusColumn;
	@FXML
	private TableColumn<Book, Boolean> bookmarksColumn;
	@FXML
	private TableColumn<Book, Void> actionsColumn;

	public LibraryController(Pair<Stage, BookOverviewController> bookOverviewView) {
		this.bookOverviewStage = bookOverviewView.getKey();
		this.bookOverviewController = bookOverviewView.getValue();
		libraryFileHandler = new LibraryFileHandler();
		loadLibrary();
	}

	@FXML
	public void initialize() throws IOException {
		filenameColumn.setCellValueFactory(new PropertyValueFactory<>("filename"));
		titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
		authorsColumn.setCellValueFactory(new PropertyValueFactory<>("authors"));
		statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
		bookmarksColumn.setCellValueFactory(new PropertyValueFactory<>("bookmarksNumber"));
		actionsColumn.setCellFactory(cell -> new OverviewOpenDeleteButtonsTableCell<>(
				(event, index) -> handleGetOverviewBookAction(event, index),
				(event, index) -> handleOpenBookAction(event, index),
				(event, index) -> handleDeleteBookAction(event, index)));
		updateBooksTableView();
	}

	@FXML
	public void handleAddBookButtonAction(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		Window window = ((Node) event.getTarget()).getScene().getWindow();
		File selectedFile = fileChooser.showOpenDialog(window);
		if (selectedFile != null) {
			Book book = new Book(selectedFile.getAbsolutePath());
			library.addBook(book);
			saveLibrary();
			addBookToTableView(book);
		}
	}

	@FXML
	public void handleRandomBookOverviewButtonAction(ActionEvent event) {
		try {
			Book book = library.getRandomBook();
			openBookOverview(book);
		} catch (NoSuchFieldException exc) {
			RebukAlert alert = new RebukAlert(AlertType.WARNING, "Library empty", "You can't get a random book because the library is empty.");
			alert.showAndWait();
		}
	}

	void saveLibrary() {
		try {
			new File(LIBRARY_FILENAME).getParentFile().mkdirs(); // Create parent directories if they don't exist
			libraryFileHandler.writeJsonStream(new FileOutputStream(LIBRARY_FILENAME), library);
		} catch (IOException exc) {
			RebukAlert alert = new RebukAlert(AlertType.WARNING, "Library not saved", "Rebuk had a problem saving the library.");
			alert.showAndWait();
		}
	}

	void updateBooksTableView() {
		booksTableView.getItems().clear();
		booksTableView.getItems().addAll(library.getBooks());
	}

	private void addBookToTableView(Book book) {
		booksTableView.getItems().add(book);
	}

	private void deleteTableViewBook(int index) {
		booksTableView.getItems().remove(index);
	}

	private void handleGetOverviewBookAction(ActionEvent event, int elementIndex) {
		Book book = booksTableView.getItems().get(elementIndex);
		openBookOverview(book);
	}

	private void handleOpenBookAction(ActionEvent event, int elementIndex) {
		Book book = booksTableView.getItems().get(elementIndex);
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

	private void handleDeleteBookAction(ActionEvent event, int elementIndex) {
		library.removeBook(elementIndex);
		saveLibrary();
		deleteTableViewBook(elementIndex);
	}

	private void openBookOverview(Book book) {
		bookOverviewController.setBook(book);
		bookOverviewStage.showAndWait();
		saveLibrary();
		updateBooksTableView();
	}

	private void loadLibrary() {
		try {
			library = libraryFileHandler.readJsonStream((new FileInputStream(LIBRARY_FILENAME)));
		} catch (IOException | JsonSyntaxException exc) {
			RebukAlert alert = new RebukAlert(AlertType.CONFIRMATION, "Library not loaded", "Do you want to create a new library?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() != ButtonType.OK)
				System.exit(1);
			library = new Library();
		}
	}

}
