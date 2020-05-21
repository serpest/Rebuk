package com.serpest.rebuk.load;

import java.io.IOException;

import com.serpest.rebuk.controller.BookOverviewController;
import com.serpest.rebuk.controller.EditBookmarkController;
import com.serpest.rebuk.controller.LibraryController;
import com.serpest.rebuk.controller.MainAppController;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;

public class ViewFactory {

	public static Scene createLibraryView() {
		FXMLLoader loader = new FXMLLoader(ViewFactory.class.getResource("/fxml/LibraryScene.fxml"));
		loader.setControllerFactory(arg -> new LibraryController(createBookOverviewView()));
		Parent root;
		try {
			root = loader.load();
		} catch (IOException exc) {
			throw new IllegalStateException(exc);
		}
		Scene scene = new Scene(root);
		return scene;
	}

	public static Pair<Stage, BookOverviewController> createBookOverviewView() {
		FXMLLoader loader = new FXMLLoader(ViewFactory.class.getResource("/fxml/BookOverviewScene.fxml"));
		loader.setControllerFactory(arg -> new BookOverviewController(createEditBookmarkDialog()));
		Parent root;
		try {
			root = loader.load();
		} catch (IOException exc) {
			throw new IllegalStateException(exc);
		}
		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Book overview");
		stage.getIcons().add(MainAppController.APP_ICON);
		stage.initModality(Modality.APPLICATION_MODAL);
		BookOverviewController controller = loader.getController();
		return new Pair<>(stage, controller);
	}

	public static Pair<Dialog<Pair<String, String>>, EditBookmarkController> createEditBookmarkDialog() {
		FXMLLoader loader = new FXMLLoader(ViewFactory.class.getResource("/fxml/EditBookmarkScene.fxml"));
		Parent root;
		try {
			root = loader.load();
		} catch (IOException exc) {
			throw new IllegalStateException(exc);
		}
		EditBookmarkController controller = loader.getController();

		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.setTitle("Edit bookmark");
		((Stage) dialog.getDialogPane().getScene().getWindow()).getIcons().add(MainAppController.APP_ICON);
		dialog.getDialogPane().setContent(root);
		dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		dialog.setResultConverter(button -> {
			if (button == ButtonType.OK) {
				return new Pair<>(controller.getNameFieldText(), controller.getPagesFieldText());
			}
			return null;
		});
		return new Pair<>(dialog, controller);
	}

}
