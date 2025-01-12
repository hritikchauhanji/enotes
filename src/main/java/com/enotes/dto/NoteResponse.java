package com.enotes.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NoteResponse {

	private List<NoteDto> notes;
	
	private Integer pageNo;
	
	private Integer pageSize;
	
	private Long totalElements;
	
	private Integer totalPage;
	
	private Boolean isFirst;
	
	private Boolean isLast;
}
