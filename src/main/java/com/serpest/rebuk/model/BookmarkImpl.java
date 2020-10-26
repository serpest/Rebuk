package com.serpest.rebuk.model;

import com.serpest.rebuk.model.datetime.DatableImpl;

public class BookmarkImpl extends DatableImpl implements Bookmark {

	private String name;

	private String pages;

	public BookmarkImpl() {
		this(null);
	}

	public BookmarkImpl(String pages) {
		this(null, pages);
	}

	public BookmarkImpl(String name, String pages) {
		this.name = name;
		this.pages = pages;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getPages() {
		return pages;
	}

	@Override
	public void setPages(String pages) {
		this.pages = pages;
	}

}
