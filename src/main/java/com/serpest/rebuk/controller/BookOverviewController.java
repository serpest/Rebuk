package com.serpest.rebuk.controller;

import java.io.IOException;

import com.serpest.rebuk.model.Book;
import com.serpest.rebuk.model.LibraryFactory;
import com.serpest.rebuk.services.OpenFileHandler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public interface BookOverviewController extends UpdatableView {

	@FXML
	void initialize() throws IOException;

	@FXML
	void handleSaveButtonAction(ActionEvent event);

	@FXML
	void handleOpenButtonAction(ActionEvent event);

	@FXML
	void handleAddBookmarkButtonAction(ActionEvent event);

	ControllersMediator getControllersMediator();

	void setControllersMediator(ControllersMediator controllersMediator);

	LibraryFactory getLibraryFactory();

	void setLibraryFactory(LibraryFactory libraryFactory);

	OpenFileHandler getOpenFileHandler();

	void setOpenFileHandler(OpenFileHandler openFileHandler);

	Book getBook();

	void setBook(Book book);

}