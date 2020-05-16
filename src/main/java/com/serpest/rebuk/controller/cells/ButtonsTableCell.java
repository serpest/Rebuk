package com.serpest.rebuk.controller.cells;

import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;

public class ButtonsTableCell<S> extends TableCell<S, Void> {

	private Button[] buttons;

	public ButtonsTableCell(Button... buttons) {
		this.buttons = buttons;
	}

	protected ButtonsTableCell() {}

	@Override
	protected void updateItem(Void item, boolean empty) {
		super.updateItem(item, empty);
		if (empty) {
	         setGraphic(null);
		} else {
			setGraphic(new HBox(buttons));
		}
	}

	protected void setButtons(Button... buttons) {
		this.buttons = buttons;
	}

}