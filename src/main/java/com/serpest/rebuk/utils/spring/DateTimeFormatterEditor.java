package com.serpest.rebuk.utils.spring;

import java.beans.PropertyEditorSupport;
import java.time.format.DateTimeFormatter;

public class DateTimeFormatterEditor extends PropertyEditorSupport {

	@Override
	public void setAsText(String textValue) {
		setValue(DateTimeFormatter.ofPattern(textValue));
	}

}
