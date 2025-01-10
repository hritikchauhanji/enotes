package com.enotes.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.enotes.dto.NoteDto;

public interface NoteService {

	Boolean saveNote(String notes,MultipartFile file) throws Exception;

	List<NoteDto> getAllNotes();
	
	NoteDto getNoteById(Integer id);
}
