package com.serpest.rebuk.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.serpest.rebuk.model.Library;

public class LibraryFileHandler {

	private Gson gson;

	public LibraryFileHandler() {
		gson = new Gson();
	}

	public void writeJsonStream(OutputStream out, Library library) throws IOException {
		JsonWriter writer = new JsonWriter(new OutputStreamWriter(out, "UTF-8"));
		writer.setIndent("\t");
		gson.toJson(library, Library.class, writer);
		writer.close();
	}

	public Library readJsonStream(InputStream in) throws IOException {
		JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
		Library library = gson.fromJson(reader, Library.class);
		reader.close();
		return library;
	}

}
