package com.serpest.rebuk.controller.cells;

import java.util.function.BiConsumer;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class DeleteButtonTableCell<S> extends ButtonsTableCell<S> {

	public DeleteButtonTableCell(BiConsumer<ActionEvent, Integer> handleDeleteAction) {
		Button deleteButton = new Button("Delete");
		deleteButton.setOnAction((event) -> handleDeleteAction.accept(event, getIndex()));
		setButtons(deleteButton);
	}

}