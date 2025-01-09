package com.enotes.service;

import java.util.List;

import com.enotes.dto.NoteDto;

public interface NoteService {

	Boolean saveNote(NoteDto noteDto) throws Exception;

	List<NoteDto> getAllNotes();
	
	NoteDto getNoteById(Integer id);
}
