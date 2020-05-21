package com.serpest.rebuk.view.cells;

import java.util.function.BiConsumer;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class EditDeleteButtonTableCell<S> extends ButtonsTableCell<S> {

	public EditDeleteButtonTableCell(BiConsumer<ActionEvent, Integer> handleEditAction, BiConsumer<ActionEvent, Integer> handleDeleteAction) {
		Button editButton = new Button("Edit");
		editButton.setOnAction((event) -> handleEditAction.accept(event, getIndex()));
		Button deleteButton = new Button("Delete");
		deleteButton.setOnAction((event) -> handleDeleteAction.accept(event, getIndex()));
		setButtons(editButton, deleteButton);
	}

}