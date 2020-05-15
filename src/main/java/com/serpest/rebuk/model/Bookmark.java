package com.serpest.rebuk.model;

import java.io.Serializable;
import java.time.LocalDateTime;

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
	}

	public String getPages() {
		return pages;
	}

	public void setPages(String pages) {
		this.pages = pages;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

}
