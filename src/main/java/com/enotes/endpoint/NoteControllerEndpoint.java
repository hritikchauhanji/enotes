package com.enotes.endpoint;

import static com.enotes.util.ConstantUtil.Default_Page_No;
import static com.enotes.util.ConstantUtil.Default_Page_Size;
import static com.enotes.util.ConstantUtil.Role_Admin;
import static com.enotes.util.ConstantUtil.Role_Admin_User;
import static com.enotes.util.ConstantUtil.Role_User;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.enotes.dto.NoteRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Notes", description = "All the notes operation APIs")
@RequestMapping("/api/v1/note")
public interface NoteControllerEndpoint {
	
	@Operation(summary = "Save Note", tags = {"Notes", "User"}, description = "User save note")
	@PostMapping(value="/", consumes = "multipart/form-data")
	@PreAuthorize(Role_Admin)
	public ResponseEntity<?> saveNote(@RequestParam @Parameter(description = "Json String Notes", required = true, content = @Content(schema = @Schema(implementation = NoteRequest.class))) String notes, @RequestParam(required = false) MultipartFile file) throws Exception;
	
	@Operation(summary = "Get All Notes", tags = {"Notes", "User"}, description = "Admin can get all notes")
	@GetMapping("/getnotes")
	@PreAuthorize(Role_Admin)
	public ResponseEntity<?> getAllNotes();

	@Operation(summary = "Get All Notes of user by subject", tags = {"Notes", "User"}, description = "User can get all notes")
	@GetMapping("/user/subject/{subjectId}")
	@PreAuthorize(Role_Admin_User)
	public ResponseEntity<?> getAllMyNotesBySubject(@PathVariable Integer subjectId);

	@Operation(summary = "Get All Notes by subject", tags = {"Notes", "User"}, description = "get all notes")
	@GetMapping("/subject/{subjectId}")
	@PreAuthorize(Role_Admin_User)
	public ResponseEntity<?> getAllNotesBySubject(@PathVariable Integer subjectId);

	@Operation(summary = "Get All Notes of admin by subject", tags = {"Notes", "User"}, description = "User can get all notes")
	@GetMapping("/admin/subject/{subjectId}")
	@PreAuthorize(Role_Admin_User)
	public ResponseEntity<?> getAllNotesAdminBySubject(@PathVariable Integer subjectId);

	@Operation(summary = "Get Note By Id", tags = {"Notes", "User"}, description = "Admin can get note by id")
	@GetMapping("/{id}")
	@PreAuthorize(Role_Admin_User)
	public ResponseEntity<?> getNoteById(@PathVariable Integer id);
	
	@Operation(summary = "Download Note File", tags = {"Notes", "User"}, description = "Admin & User can both download note file")
	@GetMapping("/download/{id}")
	@PreAuthorize(Role_Admin_User)
	public ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception;
	
	@Operation(summary = "Get All Notes By User", tags = {"Notes", "User"}, description = "User can get all notes")
	@GetMapping("/user-notes")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getAllNotesByUser(
			@RequestParam(name="pageNo", defaultValue = Default_Page_No) Integer pageNo,
			@RequestParam(name="pageSize", defaultValue = Default_Page_Size) Integer pageSize);
	
	@Operation(summary = "Get User Notes By Search", tags = {"Notes", "User"}, description = "User can get notes by search")
	@GetMapping("/search")
	@PreAuthorize(Role_User)
	public ResponseEntity<?> getUserNotesBySearch(
			@RequestParam(name="key") String key,
			@RequestParam(name="pageNo", defaultValue = Default_Page_No) Integer pageNo,
			@RequestParam(name="pageSize", defaultValue= Default_Page_Size) Integer pageSize);
	
	@Operation(summary = "Delete Note By Id", tags = {"Notes", "User"}, description = "User can delete note by Id")
	@GetMapping("/delete/{id}")
	@PreAuthorize(Role_User)
	public ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws Exception;
	
	@Operation(summary = "Restore Note By Id", tags = {"Notes", "User"}, description = "User can restore note by Id")
	@GetMapping("/restore/{id}")
	@PreAuthorize(Role_User)
	public ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception;
	
	@Operation(summary = "Get Notes From Recycle Bin", tags = { "Notes",
	"User" }, description = "Get Notes From Recycle Bin")
	@GetMapping("/recycle")
	@PreAuthorize(Role_User)
	public ResponseEntity<?> getUserRecycleBinNotes() throws Exception;
	
	@Operation(summary = "Hard Delete Notes", tags = { "Notes", "User" }, description = "Hard Delete Notes")
	@DeleteMapping("/delete/{id}")
	@PreAuthorize(Role_Admin)
	public ResponseEntity<?> hardDeleteNotes(@PathVariable Integer id) throws Exception;
	
	@Operation(summary = "Empty User Recycle Bin", tags = { "Notes", "User" }, description = "Empty User Recycle Bin")
	@DeleteMapping("/delete-recycle")
	@PreAuthorize(Role_User)
	public ResponseEntity<?> emptyUserRecycle() throws Exception;
	
	@Operation(summary = "Favorite Note", tags = { "Notes", "User" }, description = "User favorite notes")
	@GetMapping("/fav/{noteId}")
	@PreAuthorize(Role_User)
	public ResponseEntity<?> favouriteNotes(@PathVariable Integer noteId) throws Exception;
	
	@Operation(summary = "UnFavoriteNote", tags = { "Notes", "User" }, description = "User UnFavorite Notes")
	@DeleteMapping("/unfav/{favId}")
	@PreAuthorize(Role_User)
	public ResponseEntity<?> unfavouriteNote(@PathVariable Integer favId) throws Exception;
	
	@Operation(summary = "Get User Favorite Notes", tags = { "Notes", "User" }, description = "User Favorite Notes")
	@GetMapping("/fav-note")
	@PreAuthorize(Role_User)
	public ResponseEntity<?> getUserFavouriteNotes() throws Exception;
	
	@Operation(summary = "Copy Notes", tags = { "Notes", "User" }, description = "Copy Notes")
	@GetMapping("/copy/{id}")
	@PreAuthorize(Role_User)
	public ResponseEntity<?> copyNotes(@PathVariable Integer id) throws Exception;
	
	
}
