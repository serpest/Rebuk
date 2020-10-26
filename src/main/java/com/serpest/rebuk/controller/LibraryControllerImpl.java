package com.serpest.rebuk.controller;

import com.google.gson.JsonSyntaxException;
import com.serpest.rebuk.model.Book;
import com.serpest.rebuk.model.BookStatus;
import com.serpest.rebuk.model.Library;
import com.serpest.rebuk.model.LibraryFactory;
import com.serpest.rebuk.services.ImportExportLibraryHandler;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class LibraryControllerImpl implements LibraryController {

	private Library library;

	private ControllersMediator controllersMediator;

	private LibraryFactory libraryFactory;

	private OpenFileHandler openFileHandler;

	private ImportExportLibraryHandler importExportLibraryHandler;

	@FXML
	private TextField searchField;
	@FXML
	private TableView<Book> booksTableView;
	@FXML
	private TableColumn<Book, String> booksFilenameColumn;
	@FXML
	private TableColumn<Book, String> booksTitleColumn;
	@FXML
	private TableColumn<Book, String> booksAuthorsColumn;
	@FXML
	private TableColumn<Book, BookStatus> booksStatusColumn;
	@FXML
	private TableColumn<Book, String> booksLastModifiedDateTimeColumn;
	@FXML
	private TableColumn<Book, Boolean> booksBookmarksSizeColumn;
	@FXML
	private TableColumn<Book, Void> booksActionsColumn;

	@Override
	@FXML
	public void initialize() throws IOException {
		booksFilenameColumn.setCellValueFactory(new PropertyValueFactory<>("filename"));
		booksTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
		booksAuthorsColumn.setCellValueFactory(new PropertyValueFactory<>("authors"));
		booksStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
		booksLastModifiedDateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("lastModifiedDateTime"));
		booksBookmarksSizeColumn.setCellValueFactory(new PropertyValueFactory<>("bookmarksSize"));
		booksActionsColumn.setCellFactory(cell -> new OverviewOpenDeleteButtonsTableCell<>(
				(event, index) -> handleShowBookOverviewAction(event, index),
				(event, index) -> handleOpenBookAction(event, index),
				(event, index) -> handleDeleteBookAction(event, index)));
	}

	@Override
	@FXML
	public void handleAddBookButtonAction(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		Window window = ((Node) event.getTarget()).getScene().getWindow();
		File selectedFile = fileChooser.showOpenDialog(window);
		if (selectedFile != null) {
			Book book = libraryFactory.getBook();
			book.setFilename(selectedFile.getAbsolutePath());
			library.addBook(book);
		}
	}

	@Override
	@FXML
	public void handleSearchFieldAction(ActionEvent event) {}

	@Override
	public void updateView() {
		booksTableView.getItems().clear();
		Collection<Book> books = library.getBooks();
		if (!searchField.getText().isBlank())
			books = books.parallelStream().filter(this::filterSearchedBooks).collect(Collectors.toSet());
		booksTableView.getItems().addAll(books);
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
	public ImportExportLibraryHandler getImportExportLibraryHandler() {
		return importExportLibraryHandler;
	}

	@Override
	public void setImportExportLibraryHandler(ImportExportLibraryHandler importExportLibraryHandler) {
		this.importExportLibraryHandler = importExportLibraryHandler;
	}

	@Override
	public Library getLibrary() {
		return library;
	}

	@Override
	public void setLibrary(Library library) {
		this.library = library;
	}

	@Override
	public void importLibrary() {
		try {
			setLibrary(importExportLibraryHandler.importLibrary());
		} catch (IOException | JsonSyntaxException exc) {
			RebukAlert alert = new RebukAlert(AlertType.CONFIRMATION, "Library not imported",
					"Do you want to create a new library?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() != ButtonType.OK)
				System.exit(1);
			library = libraryFactory.getLibrary();
		}
	}

	@Override
	public void exportLibrary() {
		try {
			importExportLibraryHandler.exportLibrary(library);
		} catch (IOException exc) {
			RebukAlert alert = new RebukAlert(AlertType.WARNING, "Library not exported",
					"Rebuk had a problem exporting the library.");
			alert.showAndWait();
		}
	}

	/**
	 * Checks if the searched text written in <code>searchField</code> is a
	 * substring of book filename, authors or title.
	 *
	 * @param book the book to inspect
	 * @return true if book is searched
	 */
	private boolean filterSearchedBooks(Book book) {
		String searchedText = searchField.getText().toLowerCase();
		String[] bookInfos = new String[] { book.getFilename(), book.getAuthors(), book.getTitle() };
		return Arrays.stream(bookInfos).filter(Objects::nonNull).map(String::toLowerCase)
				.anyMatch(bookInfo -> bookInfo.contains(searchedText));
	}


	private void handleShowBookOverviewAction(ActionEvent event, int elementIndex) {
		Book book = booksTableView.getItems().get(elementIndex);
		controllersMediator.showBookOverview(book);
	}

	private void handleOpenBookAction(ActionEvent event, int elementIndex) {
		Book book = booksTableView.getItems().get(elementIndex);
		try {
			openFileHandler.openFile(book.getFilename());
		} catch (FileNotFoundException exc) {
			RebukAlert alert = new RebukAlert(AlertType.WARNING, "File doesn't exist",
					"Rebuk had a problem opening the file.");
			alert.showAndWait();
		} catch (IOException exc) {
			RebukAlert alert = new RebukAlert(AlertType.WARNING, "File not opened",
					"Rebuk had a problem opening the file.");
			alert.showAndWait();
		}
	}

	private void handleDeleteBookAction(ActionEvent event, int elementIndex) {
		Book book = booksTableView.getItems().get(elementIndex);
		library.removeBook(book);
	}

}
