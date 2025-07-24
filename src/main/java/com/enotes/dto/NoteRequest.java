package com.enotes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteRequest {
	private String title;

	private String description;

	private SubjectRequest subject;
}
