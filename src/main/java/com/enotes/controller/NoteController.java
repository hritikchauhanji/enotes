package com.enotes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.enotes.dto.NoteDto;
import com.enotes.service.NoteService;
import com.enotes.util.CommonUtil;

@RestController
@RequestMapping("/api/v1/note")
public class NoteController {

	@Autowired
	private NoteService noteService;
	
	@PostMapping("/save")
	ResponseEntity<?> saveNote(@RequestParam String notes,@RequestParam(required = false) MultipartFile file) throws Exception{
		Boolean saveNote = noteService.saveNote(notes,file);
		if(saveNote) {
			return CommonUtil.createBuildResponseMessage("Note saved...", HttpStatus.CREATED);
		} else {
			return CommonUtil.createErrorResponseMessage("Note note saved...", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/getnotes")
	ResponseEntity<?> getAllNotes(){
		List<NoteDto> allNotes = noteService.getAllNotes();
		if(CollectionUtils.isEmpty(allNotes)) {
			return ResponseEntity.noContent().build();
		}else {
			return CommonUtil.createBuildResponse(allNotes, HttpStatus.OK);
		}
	}
	
	@GetMapping("/{id}")
	ResponseEntity<?> getNoteById(@PathVariable Integer id){
		NoteDto noteById = noteService.getNoteById(id);
		if(ObjectUtils.isEmpty(noteById)) {
			return CommonUtil.createErrorResponseMessage("Internal Server Error", HttpStatus.NOT_FOUND);
		} else {
			return CommonUtil.createBuildResponse(noteById, HttpStatus.OK);
		}
	}
}
