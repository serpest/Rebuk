package com.serpest.rebuk.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.serpest.rebuk.controller.MainAppController;

public class Bookmark implements Serializable {

	private static final long serialVersionUID = -1121879802231194293L;

	private String name;

	private String pages;

	private LocalDateTime dateTime;

	public Bookmark(String name, String pages) {
		this(name, pages, LocalDateTime.now());
	}

	public Bookmark(String name, String pages, LocalDateTime dateTime) {
		this.name = name;
		this.pages = pages;
		this.dateTime = dateTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		updateDateTimeNow();
	}

	public String getPages() {
		return pages;
	}

	public void setPages(String pages) {
		this.pages = pages;
		updateDateTimeNow();
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public String getFormattedDateTime() {
		if (dateTime == null)
			return "";
		return MainAppController.DATE_TIME_FORMATTER.format(dateTime);
	}

	private void updateDateTimeNow() {
		dateTime = LocalDateTime.now();
	}

}
