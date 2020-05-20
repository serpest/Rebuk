package com.serpest.rebuk.view;

import javafx.scene.control.Alert;

public class RebukAlert extends Alert {

	public RebukAlert(AlertType alertType, String headerText, String contentText) {
		super(alertType);
		setTitle("Rebuk " + alertType.name().toLowerCase());
		setHeaderText(headerText);
		setContentText(contentText);
	}

}
