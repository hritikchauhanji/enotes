package com.enotes.dto;

import java.time.LocalDateTime;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NoteDto {

	private Integer id;

	private String title;

	private String description;

	private SubjectRequest subject;

	private Date createdOn;

	private Integer updatedBy;

	private Date updatedOn;

	private FileDto fileDetails;

	private Boolean isDeleted;

	private LocalDateTime deletedOn;

	// Add creator name here
	private Integer createdById;
	private String createdByFirstName;
	private String createdByLastName;

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class FileDto {

		private String OriginalFileName;

		private String displayFileName;

		private String uploadFileName;

	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class SubjectRequest {

		private Integer id;

		private String name;
	}
}
