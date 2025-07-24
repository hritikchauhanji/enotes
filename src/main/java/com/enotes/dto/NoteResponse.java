package com.enotes.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NoteResponse {

	private List<NoteDto> notes;
}
