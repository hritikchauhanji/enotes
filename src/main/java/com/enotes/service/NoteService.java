package com.enotes.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.enotes.dto.NoteDto;
import com.enotes.dto.NoteResponse;
import com.enotes.entity.FileDetails;

public interface NoteService {

	Boolean saveNote(String notes,MultipartFile file) throws Exception;

	List<NoteDto> getAllNotes();
	
	NoteDto getNoteById(Integer id);

	byte[] downloadFile(FileDetails fileDtls) throws Exception;

	FileDetails getFileDetails(Integer id) throws Exception;

	NoteResponse getAllNotesByUser(Integer userId, Integer pageNo, Integer pageSize);

	void softDeleteNotes(Integer id) throws Exception;

	void restoreNotes(Integer id) throws Exception;

	List<NoteDto> getUserRecycleBinNotes(Integer userId);
}
