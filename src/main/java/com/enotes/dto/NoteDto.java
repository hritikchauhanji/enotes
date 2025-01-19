package com.enotes.dto;

import java.time.LocalDate;
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

	private CategoryDto category;

	private Integer createdBy;

	private Date createdOn;

	private Integer updatedBy;

	private Date updatedOn;

	private FileDto fileDetails;

	private Boolean isDeleted;

	private LocalDateTime deletedOn;

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class FileDto {

		private String OriginalFileName;

		private String displayFileName;

	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class CategoryDto {

		private Integer id;

		private String name;
	}
}
