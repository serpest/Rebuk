package com.serpest.rebuk.model;

import java.util.HashSet;
import java.util.Set;

import com.serpest.rebuk.model.datetime.DatableImpl;

public class BookImpl extends DatableImpl implements Book {

	private String title;

	private String authors;

	private String filename;

	private String notes;

	private Set<Bookmark> bookmarks;

	private BookStatus status;

	public BookImpl() {
		this(null);
	}

	public BookImpl(String filename) {
		this(filename, null, null);
	}

	public BookImpl(String filename, String title, String authors) {
		this(filename, title, authors, BookStatus.NEW);
	}

	public BookImpl(String filename, String title, String authors, BookStatus status) {
		this.filename = filename;
		this.title = title;
		this.authors = authors;
		this.status = status;
		bookmarks = new HashSet<>();
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String getAuthors() {
		return authors;
	}

	@Override
	public void setAuthors(String authors) {
		this.authors = authors;
	}

	@Override
	public String getFilename() {
		return filename;
	}

	@Override
	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Override
	public String getNotes() {
		return notes;
	}

	@Override
	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Override
	public Set<Bookmark> getBookmarks() {
		return bookmarks;
	}

	@Override
	public void setBookmarks(Set<Bookmark> bookmarks) {
		this.bookmarks = bookmarks;
	}

	@Override
	public BookStatus getStatus() {
		return status;
	}

	@Override
	public void setStatus(BookStatus status) {
		this.status = status;
	}

	@Override
	public void addBookmark(Bookmark bookmark) {
		bookmarks.add(bookmark);
	}

	@Override
	public void removeBookmark(Bookmark bookmark) {
		bookmarks.remove(bookmark);
	}

	@Override
	public int getBookmarksSize() {
		return bookmarks.size();
	}

}
