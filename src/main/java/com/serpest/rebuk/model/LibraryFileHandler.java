package com.serpest.rebuk.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class LibraryFileHandler {

	private Gson gson;

	public LibraryFileHandler() {
		gson = new Gson();
	}

	public void writeJsonStream(OutputStream out, Library library) throws IOException {
		JsonWriter writer = new JsonWriter(new OutputStreamWriter(out, "UTF-8"));
		writer.setIndent("   ");
		writer.beginArray();
		for (Book book : library) {
			gson.toJson(book, Book.class, writer);
		}
		writer.endArray();
		writer.close();
	}

	public Library readJsonStream(InputStream in) throws IOException {
		JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
		Library library = new Library();
		reader.beginArray();
		while (reader.hasNext()) {
			Book book = gson.fromJson(reader, Book.class);
			library.addBook(book);
		}
		reader.endArray();
		reader.close();
		return library;
	}

}
