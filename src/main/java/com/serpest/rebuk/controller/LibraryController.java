package com.serpest.rebuk.controller;

import com.serpest.rebuk.model.Book;
import com.serpest.rebuk.model.Library;
import com.serpest.rebuk.model.LibraryFileHandler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.util.Callback;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

public class LibraryController {

	private class ActionsCell extends TableCell<Book, Button[]> {

		private Button overviewButton;
		private Button deleteButton;

		private ActionsCell() {
			overviewButton = new Button("Overview");
			overviewButton.setOnAction((event) -> handleGetOverviewBookAction(event));
			deleteButton = new Button("Delete");
			deleteButton.setOnAction((event) -> handleDeleteBookAction(event));
		}

		@Override
		protected void updateItem(Button[] buttons, boolean empty) {
			super.updateItem(buttons, empty);
			if (empty)
				setGraphic(null);
			else
				setGraphic(new HBox(overviewButton, deleteButton));
		}

	}

	private static String LIBRARY_FILENAME = "data" + File.separator + "library.json";
	private Library library;
	private LibraryFileHandler libraryFileHandler;

	private Scene scene;
	private BookOverviewController bookOverviewController;
	private Parent bookOverviewRoot;

	@FXML
	private VBox rootPane;
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
	private TableColumn<Book, Button[]> actionsColumn;

	public LibraryController() {
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
		actionsColumn.setCellFactory(new Callback<>() {
			@Override
			public TableCell<Book, Button[]> call(TableColumn<Book, Button[]> column) {
				return new ActionsCell();
			}
		});

		upgradeBooksTableView();
	}

	@FXML
	public void handleAddBookButtonAction(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		File selectedFile = fileChooser.showOpenDialog(scene.getWindow());
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
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Rebuk warning");
			alert.setHeaderText("Library empty");
			alert.setContentText("You can't get a random book because the library is empty.");
			alert.showAndWait();
		}
	}

	void saveLibrary() {
		try {
			new File(LIBRARY_FILENAME).getParentFile().mkdirs();
			libraryFileHandler.writeJsonStream(new FileOutputStream(LIBRARY_FILENAME), library);
		} catch (IOException exc) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Rebuk warning");
			alert.setHeaderText("Library not saved");
			alert.setContentText("Rebuk had a problem saving the library.");
			alert.showAndWait();
		}
	}

	void upgradeBooksTableView() {
		booksTableView.getItems().clear();
		booksTableView.getItems().addAll(library.getBooks());
	}

	private void handleGetOverviewBookAction(ActionEvent event) {
		int elementIndex = getActionsCellIndex(event);
		Book book = booksTableView.getItems().get(elementIndex);
		openBookOverview(book);
	}

	private void openBookOverview(Book book) {
		bookOverviewController.setBook(book);
		scene.setRoot(bookOverviewRoot);
	}

	private void handleDeleteBookAction(ActionEvent event) {
		int elementIndex = getActionsCellIndex(event);
		library.removeBook(elementIndex);
		saveLibrary();
		deleteTableViewBook(elementIndex);
	}

	private int getActionsCellIndex(ActionEvent event) {
		return ((ActionsCell) ((Node) event.getSource()).getParent().getParent()).getIndex();
	}

	private void loadLibrary() {
		try {
			library = libraryFileHandler.readJsonStream((new FileInputStream(LIBRARY_FILENAME)));
		} catch (IOException exc) {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Rebuk warning");
			alert.setHeaderText("Library not loaded");
			alert.setContentText("Do you want to create a new library?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() != ButtonType.OK)
				System.exit(1);
			library = new Library();
		}
	}

	private void addBookToTableView(Book book) {
		booksTableView.getItems().add(book);
	}

	private void deleteTableViewBook(int index) {
		booksTableView.getItems().remove(index);
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}

	public void setBookOverviewController(BookOverviewController bookOverviewController) {
		this.bookOverviewController = bookOverviewController;
	}

	public void setBookOverviewRoot(Parent bookOverviewRoot) {
		this.bookOverviewRoot = bookOverviewRoot;
	}

}
