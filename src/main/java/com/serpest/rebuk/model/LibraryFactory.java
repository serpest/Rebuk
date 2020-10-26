package com.serpest.rebuk.model;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class LibraryFactory implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public Library getLibrary() {
		return (Library) applicationContext.getBean("library");
	}

	public Book getBook() {
		return (Book) applicationContext.getBean("book");
	}

	public Bookmark getBookmark() {
		return (Bookmark) applicationContext.getBean("bookmark");
	}

}
