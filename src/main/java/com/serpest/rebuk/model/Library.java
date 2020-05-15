package com.serpest.rebuk.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Library implements Serializable, Iterable<Book> {

	private static final long serialVersionUID = -7115762618036310656L;

	private List<Book> books;

	public Library() {
		books = new ArrayList<>();
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	public void addBook(Book book) {
		books.add(book);
	}

	public void removeBook(int index) {
		books.remove(index);
	}

	public int size() {
		return books.size();
	}

	public Book getRandomBook() throws NoSuchFieldException {
		if (books.size() == 0)
			throw new NoSuchFieldException();
		Random random = new Random();
		return books.get(random.nextInt(books.size()));
	}

	@Override
	public Iterator<Book> iterator() {
		return books.iterator();
	}

}
