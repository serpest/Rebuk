package com.serpest.rebuk.model;

import java.util.Iterator;
import java.util.Set;

public interface Library {

	Set<Book> getBooks();

	void setBooks(Set<Book> books);

	void addBook(Book book);

	void removeBook(Book book);

	int getBooksSize();

	Iterator<Book> iterator();

}
