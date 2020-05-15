package com.serpest.rebuk.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Book implements Serializable, Comparable<Book> {

	public static enum Status {

		NEW("New"), READING("Reading"), READ("Read");

		private String value;

		Status(String value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return value;
		}

	}

	private static final long serialVersionUID = 7438614807193744301L;

	private String title;

	private String authors;

	private String filename;

	private String notes;

	private List<Bookmark> bookmarks;

	private Status status;

	public Book(String filename) {
		this(filename, null, null);
	}

	public Book(String filename, String title, String authors) {
		this.filename = filename;
		this.title = title;
		this.authors = authors;
		status = Status.NEW;
		bookmarks = new ArrayList<>();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthors() {
		return authors;
	}

	public void setAuthors(String authors) {
		this.authors = authors;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public List<Bookmark> getBookmarks() {
		return bookmarks;
	}

	public void setBookmarks(List<Bookmark> bookmarks) {
		this.bookmarks = bookmarks;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void addBookmark(Bookmark bookmark) {
		bookmarks.add(bookmark);
	}

	public int getBookmarksNumber() {
		return bookmarks.size();
	}

	@Override
	public int compareTo(Book other) {
		if (getTitle() != null && other.getTitle() != null)
			return getTitle().compareTo(other.getTitle());
		else
			return getFilename().compareTo(other.getFilename());
	}

}
