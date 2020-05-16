package com.serpest.rebuk;

import java.io.IOException;

import com.serpest.rebuk.controller.AddBookmarkController;
import com.serpest.rebuk.controller.BookOverviewController;
import com.serpest.rebuk.controller.LibraryController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Pair;

public class MainApp extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	private Scene scene;

	private Parent libraryRoot;
	private LibraryController libraryController;

	private Parent bookOverviewRoot;
	private BookOverviewController bookOverviewController;

	private Parent addBookmarkRoot;
	private Dialog<Pair<String, String>> addBookmarkDialog;
	private AddBookmarkController addBookmarkController;

	@Override
	public void start(Stage stage) throws Exception {
		loadLibraryUI();
		loadBookOverviewUI();
		loadAddBookmarkDialog();
		configure(stage);
		stage.show();
	}

	private void loadLibraryUI() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LibraryScene.fxml"));
		libraryRoot = loader.load();
		libraryController = loader.getController();
	}

	private void loadBookOverviewUI() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/BookOverviewScene.fxml"));
		bookOverviewRoot = loader.load();
		bookOverviewController = loader.getController();
	}

	private void loadAddBookmarkDialog() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AddBookmarkDialogScene.fxml"));
		addBookmarkRoot = loader.load();
		addBookmarkController = loader.getController();
		addBookmarkDialog = addBookmarkController.getAddBookmarkDialog();
	}

	private void configure(Stage stage) {
		scene = new Scene(libraryRoot);
		stage.setTitle("Rebuk");
		stage.getIcons().add(new Image(getClass().getResource("/img/icon.png").toString()));
		stage.setScene(scene);
		libraryController.setScene(scene);
		libraryController.setBookOverviewRoot(bookOverviewRoot);
		libraryController.setBookOverviewController(bookOverviewController);
		bookOverviewController.setScene(scene);
		bookOverviewController.setLibraryRoot(libraryRoot);
		bookOverviewController.setLibraryController(libraryController);
		bookOverviewController.setAddBookmarkDialog(addBookmarkDialog);
		addBookmarkController.setRoot(addBookmarkRoot);
	}

}
