package com.serpest.rebuk.model.datetime;

import java.time.LocalDateTime;

public interface Datable {

	LocalDateTime getCreationDateTime();

	LocalDateTime getLastModifiedDateTime();

}
