package com.serpest.rebuk.model;

import java.util.Set;

import com.serpest.rebuk.model.datetime.Datable;

public interface Book extends Datable {

	String getTitle();

	void setTitle(String title);

	String getAuthors();

	void setAuthors(String authors);

	String getFilename();

	void setFilename(String filename);

	String getNotes();

	void setNotes(String notes);

	Set<Bookmark> getBookmarks();

	void setBookmarks(Set<Bookmark> bookmarks);

	BookStatus getStatus();

	void setStatus(BookStatus status);

	void addBookmark(Bookmark bookmark);

	void removeBookmark(Bookmark bookmark);

	int getBookmarksSize();

}
