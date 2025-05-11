package com.enotes.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import static com.enotes.util.ConstantUtil.Role_User;
import static com.enotes.util.ConstantUtil.Role_Admin;
import static com.enotes.util.ConstantUtil.Role_Admin_User;
import static com.enotes.util.ConstantUtil.Default_Page_No;
import static com.enotes.util.ConstantUtil.Default_Page_Size;

@RequestMapping("/api/v1/note")
public interface NoteControllerEndpoint {
	
	@PostMapping("/save")
	@PreAuthorize(Role_User)
	public ResponseEntity<?> saveNote(@RequestParam String notes, @RequestParam(required = false) MultipartFile file) throws Exception;
	
	@GetMapping("/getnotes")
	@PreAuthorize(Role_Admin)
	public ResponseEntity<?> getAllNotes();
	
	@GetMapping("/{id}")
	@PreAuthorize(Role_Admin)
	public ResponseEntity<?> getNoteById(@PathVariable Integer id);
	
	@GetMapping("/download/{id}")
	@PreAuthorize(Role_Admin_User)
	public ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception;
	
	@GetMapping("/user-notes")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getAllNotesByUser(
			@RequestParam(name="pageNo", defaultValue = Default_Page_No) Integer pageNo,
			@RequestParam(name="pageSize", defaultValue = Default_Page_Size) Integer pageSize);
	
	@GetMapping("/search")
	@PreAuthorize(Role_User)
	public ResponseEntity<?> getUserNotesBySearch(
			@RequestParam(name="key") String key,
			@RequestParam(name="pageNo", defaultValue = Default_Page_No) Integer pageNo,
			@RequestParam(name="pageSize", defaultValue= Default_Page_Size) Integer pageSize);
	
	@GetMapping("/delete/{id}")
	@PreAuthorize(Role_User)
	public ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws Exception;
	
	@GetMapping("/restore/{id}")
	@PreAuthorize(Role_User)
	public ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception;
	
	@GetMapping("/recycle")
	@PreAuthorize(Role_User)
	public ResponseEntity<?> getUserRecycleBinNotes() throws Exception;
	
	@DeleteMapping("/delete/{id}")
	@PreAuthorize(Role_User)
	public ResponseEntity<?> hardDeleteNotes(@PathVariable Integer id) throws Exception;
	
	@DeleteMapping("/delete-recycle")
	@PreAuthorize(Role_User)
	public ResponseEntity<?> emptyUserRecycle() throws Exception;
	
	@GetMapping("/fav/{noteId}")
	@PreAuthorize(Role_User)
	public ResponseEntity<?> favouriteNotes(@PathVariable Integer noteId) throws Exception;
	
	@DeleteMapping("/unfav/{favId}")
	@PreAuthorize(Role_User)
	public ResponseEntity<?> unfavouriteNote(@PathVariable Integer favId) throws Exception;
	
	@GetMapping("/fav-note")
	@PreAuthorize(Role_User)
	public ResponseEntity<?> getUserFavouriteNotes() throws Exception;
	
	@GetMapping("/copy/{id}")
	@PreAuthorize(Role_User)
	public ResponseEntity<?> copyNotes(@PathVariable Integer id) throws Exception;
	
	
}
