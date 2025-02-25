package com.enotes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.enotes.dto.FavouriteNotesDto;
import com.enotes.dto.NoteDto;
import com.enotes.dto.NoteResponse;
import com.enotes.endpoint.NoteControllerEndpoint;
import com.enotes.entity.FileDetails;
import com.enotes.service.NoteService;
import com.enotes.util.CommonUtil;

@RestController
public class NoteController implements NoteControllerEndpoint {

	@Autowired
	private NoteService noteService;

	@Override
	public ResponseEntity<?> saveNote(@RequestParam String notes, @RequestParam(required = false) MultipartFile file)
			throws Exception {
		Boolean saveNote = noteService.saveNote(notes, file);
		if (saveNote) {
			return CommonUtil.createBuildResponseMessage("Note saved...", HttpStatus.CREATED);
		} else {
			return CommonUtil.createErrorResponseMessage("Note note saved...", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<?> getAllNotes() {
		List<NoteDto> allNotes = noteService.getAllNotes();
		if (CollectionUtils.isEmpty(allNotes)) {
			return ResponseEntity.noContent().build();
		} else {
			return CommonUtil.createBuildResponse(allNotes, HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<?> getNoteById(@PathVariable Integer id) {
		NoteDto noteById = noteService.getNoteById(id);
		if (ObjectUtils.isEmpty(noteById)) {
			return CommonUtil.createErrorResponseMessage("Internal Server Error", HttpStatus.NOT_FOUND);
		} else {
			return CommonUtil.createBuildResponse(noteById, HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception {
		FileDetails fileDetails = noteService.getFileDetails(id);
		byte[] data = noteService.downloadFile(fileDetails);

		HttpHeaders headers = new HttpHeaders();
		String contentType = CommonUtil.getContentType(fileDetails.getOriginalFileName());
		headers.setContentType(MediaType.parseMediaType(contentType));
		headers.setContentDispositionFormData("attachment", fileDetails.getOriginalFileName());

		return ResponseEntity.ok().headers(headers).body(data);
	}

	@Override
	public ResponseEntity<?> getAllNotesByUser(
			@RequestParam(name="pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(name="pageSize", defaultValue= "3") Integer pageSize) {
		NoteResponse allNotes = noteService.getAllNotesByUser( pageNo , pageSize);
//		if (CollectionUtils.isEmpty(allNotes)) {
//			return ResponseEntity.noContent().build();
//		} else {
		return CommonUtil.createBuildResponse(allNotes, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> getUserNotesBySearch(
			@RequestParam(name="key") String key,
			@RequestParam(name="pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(name="pageSize", defaultValue= "3") Integer pageSize) {
		NoteResponse allNotes = noteService.getUserNotesBySearch(pageNo , pageSize, key);
//		if (CollectionUtils.isEmpty(allNotes)) {
//			return ResponseEntity.noContent().build();
//		} else {
		return CommonUtil.createBuildResponse(allNotes, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws Exception{
		noteService.softDeleteNotes(id);
		return CommonUtil.createBuildResponseMessage("Delete Success", HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception{
		noteService.restoreNotes(id);
		return CommonUtil.createBuildResponseMessage("Notes Restore Success", HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> getUserRecycleBinNotes() throws Exception{
		List<NoteDto> notes =  noteService.getUserRecycleBinNotes();
		if(CollectionUtils.isEmpty(notes)) {
			return ResponseEntity.noContent().build();
		}
		return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> hardDeleteNotes(@PathVariable Integer id) throws Exception{
		noteService.hardDeleteNotes(id);
		return CommonUtil.createBuildResponseMessage("Delete Success", HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> emptyUserRecycle() throws Exception{
		noteService.emptyRecycle();
		return CommonUtil.createBuildResponseMessage("Delete Success", HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> favouriteNotes(@PathVariable Integer noteId) throws Exception{
		noteService.favouriteNotes(noteId);
		return CommonUtil.createBuildResponseMessage("Add In Favourite Notes", HttpStatus.CREATED);
	}
	
	@Override
	public ResponseEntity<?> unfavouriteNote(@PathVariable Integer favId) throws Exception{
		noteService.unFavouriteNotes(favId);
		return CommonUtil.createBuildResponseMessage("Remove From Favourite Note", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> getUserFavouriteNotes() throws Exception{
		List<FavouriteNotesDto> userFavouriteNotes = noteService.getUserFavouriteNotes();
		if(CollectionUtils.isEmpty(userFavouriteNotes)) {
			return ResponseEntity.noContent().build();
		}
		return CommonUtil.createBuildResponse(userFavouriteNotes, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> copyNotes(@PathVariable Integer id) throws Exception{
		Boolean copyNotes = noteService.copyNotes(id);
		if(copyNotes) {
			return CommonUtil.createBuildResponse(copyNotes, HttpStatus.CREATED);
		}
		return CommonUtil.createBuildResponseMessage("Copy not save", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
