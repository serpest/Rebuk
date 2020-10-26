package com.serpest.rebuk.controller;

import java.io.IOException;

import com.serpest.rebuk.model.Library;
import com.serpest.rebuk.model.LibraryFactory;
import com.serpest.rebuk.services.ImportExportLibraryHandler;
import com.serpest.rebuk.services.OpenFileHandler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public interface LibraryController extends UpdatableView {

	@FXML
	void initialize() throws IOException;

	@FXML
	void handleAddBookButtonAction(ActionEvent event);

	@FXML
	void handleSearchFieldAction(ActionEvent event);

	ControllersMediator getControllersMediator();

	void setControllersMediator(ControllersMediator controllersMediator);

	LibraryFactory getLibraryFactory();

	void setLibraryFactory(LibraryFactory libraryFactory);

	OpenFileHandler getOpenFileHandler();

	void setOpenFileHandler(OpenFileHandler openFileHandler);

	ImportExportLibraryHandler getImportExportLibraryHandler();

	void setImportExportLibraryHandler(ImportExportLibraryHandler importExportLibraryHandler);

	Library getLibrary();

	void setLibrary(Library library);

	void importLibrary();

	void exportLibrary();

}