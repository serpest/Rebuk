package com.serpest.rebuk.controller.custom.cells;

import java.util.function.BiConsumer;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class OverviewDeleteButtonsTableCell<S> extends ButtonsTableCell<S> {

	public OverviewDeleteButtonsTableCell(BiConsumer<ActionEvent, Integer> handleOverviewAction,
			BiConsumer<ActionEvent, Integer> handleDeleteAction) {
		Button overviewButton = new Button("Overview");
		overviewButton.setOnAction((event) -> handleOverviewAction.accept(event, getIndex()));
		Button deleteButton = new Button("Delete");
		deleteButton.setOnAction((event) -> handleDeleteAction.accept(event, getIndex()));
		setButtons(overviewButton, deleteButton);
	}

}