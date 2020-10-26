package com.serpest.rebuk.model.datetime;

import java.time.LocalDateTime;

public abstract class DatableImpl implements Datable, EditableDate {

	private LocalDateTime creationDateTime;

	private LocalDateTime lastModifiedDateTime;

	public DatableImpl() {
		updateCreationDateTimeToNow();
		updateLastModifiedDateTimeToNow();
	}

	@Override
	public LocalDateTime getCreationDateTime() {
		return creationDateTime;
	}

	@Override
	public void setCreationDateTime(LocalDateTime creationDateTime) {
		this.creationDateTime = creationDateTime;
	}

	@Override
	public void updateCreationDateTimeToNow() {
		creationDateTime = LocalDateTime.now();
	}

	@Override
	public LocalDateTime getLastModifiedDateTime() {
		return lastModifiedDateTime;
	}

	@Override
	public void setLastModifiedDateTime(LocalDateTime lastModifiedDateTime) {
		this.lastModifiedDateTime = lastModifiedDateTime;
	}

	@Override
	public void updateLastModifiedDateTimeToNow() {
		lastModifiedDateTime = LocalDateTime.now();
	}

}
