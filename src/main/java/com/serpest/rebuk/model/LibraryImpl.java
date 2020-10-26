package com.serpest.rebuk.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class LibraryImpl implements Library {

	private Set<Book> books;

	public LibraryImpl() {
		books = new HashSet<>();
	}

	@Override
	public Set<Book> getBooks() {
		return books;
	}

	@Override
	public void setBooks(Set<Book> books) {
		this.books = books;
	}

	@Override
	public void addBook(Book book) {
		books.add(book);
	}

	@Override
	public void removeBook(Book book) {
		books.remove(book);
	}

	@Override
	public int getBooksSize() {
		return books.size();
	}

	@Override
	public Iterator<Book> iterator() {
		return books.iterator();
	}

}
