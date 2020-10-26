package com.serpest.rebuk.model;

import com.serpest.rebuk.model.datetime.Datable;

public interface Bookmark extends Datable {

	String getName();

	void setName(String name);

	String getPages();

	void setPages(String pages);

}
