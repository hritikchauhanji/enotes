package com.enotes.dto;

import com.enotes.dto.NoteDto.CategoryDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteRequest {
	private String title;

	private String description;

	private CategoryDto category;
}
