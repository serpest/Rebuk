package com.serpest.rebuk.model.datetime;

import java.time.LocalDateTime;

public interface EditableDate {

	void setCreationDateTime(LocalDateTime creationDateTime);

	void updateCreationDateTimeToNow();

	void setLastModifiedDateTime(LocalDateTime lastModifiedDateTime);

	void updateLastModifiedDateTimeToNow();

}
