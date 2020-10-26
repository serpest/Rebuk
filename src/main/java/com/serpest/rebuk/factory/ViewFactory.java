package com.serpest.rebuk.factory;

import java.io.IOException;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.serpest.rebuk.controller.ControllersMediator;
import com.serpest.rebuk.controller.EditBookmarkController;
import com.serpest.rebuk.utils.javafx.ControllerProxyFXMLLoader;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;

public class ViewFactory implements ApplicationContextAware {

	public final static String APP_NAME = "Rebuk";
	public final static Image APP_ICON = new Image(ClassLoader.getSystemResource("img/icon.png").toString());

	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public void setupLibraryView(Stage stage) throws Exception {
		ControllerProxyFXMLLoader loader = new ControllerProxyFXMLLoader(ClassLoader.getSystemResource("fxml/LibraryScene.fxml"));
		loader.setController(applicationContext.getBean("libraryController"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle(APP_NAME);
		stage.getIcons().add(APP_ICON);
	}

	public Stage getBookOverviewStage() throws Exception {
		ControllerProxyFXMLLoader loader = new ControllerProxyFXMLLoader(ClassLoader.getSystemResource("fxml/BookOverviewScene.fxml"));
		loader.setController(applicationContext.getBean("bookOverviewController"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Book overview");
		stage.getIcons().add(APP_ICON);
		stage.initModality(Modality.APPLICATION_MODAL);
		return stage;
	}

	public Dialog<Pair<String, String>> getEditBookmarkDialog() throws IOException {
		FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemResource("fxml/EditBookmarkScene.fxml"));
		loader.setController(applicationContext.getBean("editBookmarkController"));
		Parent root = loader.load();
		EditBookmarkController controller = loader.getController();

		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.setTitle("Edit bookmark");
		((Stage) dialog.getDialogPane().getScene().getWindow()).getIcons().add(APP_ICON);
		dialog.getDialogPane().setContent(root);
		dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		dialog.setResultConverter(button -> {
			if (button == ButtonType.OK) {
				return new Pair<>(controller.getNameFieldText(), controller.getPagesFieldText());
			}
			return null;
		});
		return dialog;
	}

	public void setupControllersMediator() throws Exception {
		ControllersMediator controllersMediator = (ControllersMediator) applicationContext.getBean("controllersMediator");
		controllersMediator.setBookOverviewStage(getBookOverviewStage());
		controllersMediator.setEditBookmarkDialog(getEditBookmarkDialog());
	}

}
