package com.serpest.rebuk.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.serpest.rebuk.model.Book;
import com.serpest.rebuk.model.Bookmark;
import com.serpest.rebuk.model.Library;

public class ImportExportLibraryHandler implements ApplicationContextAware {

	private String defaultLibraryPath;

	private Gson gson;

	private ApplicationContext applicationContext;

	public void init() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Library.class, new InstanceCreator<Library>() {
			@Override
			public Library createInstance(Type type) {
				return (Library) applicationContext.getBean("library");
			}
		});
		gsonBuilder.registerTypeAdapter(Book.class, new InstanceCreator<Book>() {
			@Override
			public Book createInstance(Type type) {
				return (Book) applicationContext.getBean("book");
			}
		});
		gsonBuilder.registerTypeAdapter(Bookmark.class, new InstanceCreator<Bookmark>() {
			@Override
			public Bookmark createInstance(Type type) {
				return (Bookmark) applicationContext.getBean("bookmark");
			}
		});
		gson = gsonBuilder.create();
	}

	public String getDefaultLibraryPath() {
		return defaultLibraryPath;
	}

	public void setDefaultLibraryPath(String defaultLibraryPath) {
		this.defaultLibraryPath = defaultLibraryPath;
	}

	public Library importLibrary() throws IOException {
		return importLibrary(defaultLibraryPath);
	}

	public Library importLibrary(String libraryPath) throws IOException {
		return importLibrary(new FileInputStream(libraryPath));
	}

	public Library importLibrary(InputStream in) throws IOException {
		JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
		Library library = gson.fromJson(reader, Library.class);
		reader.close();
		return library;
	}

	public void exportLibrary(Library library) throws IOException {
		exportLibrary(defaultLibraryPath, library);
	}

	public void exportLibrary(String libraryPath, Library library) throws IOException {
		new File(libraryPath).getParentFile().mkdirs(); // Create parent directories if they don't exist
		exportLibrary(new FileOutputStream(libraryPath), library);
	}

	public void exportLibrary(OutputStream out, Library library) throws IOException {
		JsonWriter writer = new JsonWriter(new OutputStreamWriter(out, "UTF-8"));
		writer.setIndent("\t");
		gson.toJson(library, Library.class, writer);
		writer.close();
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

}
