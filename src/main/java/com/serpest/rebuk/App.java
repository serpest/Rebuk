package com.serpest.rebuk;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.serpest.rebuk.factory.ViewFactory;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

	private final static String CONFIG_LOCATION = ClassLoader.getSystemResource("spring/ApplicationContext.xml").toString();

	public static void main(final String[] args) {
		launch(args);
	}

	private ApplicationContext applicationContext;

	@Override
	public void start(Stage primaryStage) throws Exception {
		applicationContext = new ClassPathXmlApplicationContext(CONFIG_LOCATION);
		ViewFactory viewFactory = (ViewFactory) applicationContext.getBean("viewFactory");
		viewFactory.setupControllersMediator();
		viewFactory.setupLibraryView(primaryStage);
		primaryStage.show();
	}

}
