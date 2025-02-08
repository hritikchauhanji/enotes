package com.enotes.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import com.enotes.dto.FavouriteNotesDto;
import com.enotes.dto.NoteDto;
import com.enotes.dto.NoteDto.CategoryDto;
import com.enotes.dto.NoteDto.FileDto;
import com.enotes.dto.NoteResponse;
import com.enotes.entity.FavouriteNotes;
import com.enotes.entity.FileDetails;
import com.enotes.entity.Notes;
import com.enotes.exceptionhandling.ResourceNotFoundException;
import com.enotes.repository.CategoryRepository;
import com.enotes.repository.FavouriteNoteRepository;
import com.enotes.repository.FileRepository;
import com.enotes.repository.NoteRepository;
import com.enotes.service.NoteService;
import com.enotes.util.CommonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class NoteServiceImpl implements NoteService {

	@Autowired
	private NoteRepository noteRepository;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private CategoryRepository categoryRepository;

	@Value("${file.upload.path}")
	private String uploadpath;

	@Autowired
	private FileRepository fileRepository;
	
	@Autowired
	private FavouriteNoteRepository favouriteNoteRepository;

	@Override
	public Boolean saveNote(String notes, MultipartFile file) throws Exception {

		ObjectMapper ob = new ObjectMapper();
		NoteDto noteDto = ob.readValue(notes, NoteDto.class);
		
		noteDto.setIsDeleted(false);
		noteDto.setDeletedOn(null);
		
		
		// update notes
		if(!ObjectUtils.isEmpty(noteDto.getId())) {
			updateNotes(noteDto,file);
		}

		// category validation
		checkCategoryExist(noteDto.getCategory());

		Notes note = mapper.map(noteDto, Notes.class);

		FileDetails fileDtls = saveFileDetails(file);
		if (!ObjectUtils.isEmpty(fileDtls)) {
			note.setFileDetails(fileDtls);
		} else {
			if(ObjectUtils.isEmpty(noteDto.getId())) {
				note.setFileDetails(null);
			}
		}

		// validation notes

		Notes saveNote = noteRepository.save(note);
		if (!ObjectUtils.isEmpty(saveNote)) {
			return true;
		}
		return false;
	}

	private void updateNotes(NoteDto noteDto, MultipartFile file) throws Exception {
		Notes existNotes = noteRepository.findById(noteDto.getId()).orElseThrow(()-> new ResourceNotFoundException("Invalid noted Id..."));
		
		if(ObjectUtils.isEmpty(file)) {
			noteDto.setFileDetails(mapper.map(existNotes.getFileDetails(), FileDto.class));
		}
	}

	private FileDetails saveFileDetails(MultipartFile file) throws Exception {
		if (!ObjectUtils.isEmpty(file) && !file.isEmpty()) {
			String originalFilename = file.getOriginalFilename();
			String extension = FilenameUtils.getExtension(originalFilename);

			List<String> extensionAllow = Arrays.asList("pdf", "jpg");
			if (!extensionAllow.contains(extension)) {
				throw new IllegalAccessException("Invalid file format ! Upload only pdf & jpg");
			}

			FileDetails fileDtls = new FileDetails();

			fileDtls.setOriginalFileName(originalFilename);
			fileDtls.setDisplayFileName(getDisplayFileName(originalFilename));

			String rndString = UUID.randomUUID().toString();
			String uploadFilename = rndString + "." + extension;

			fileDtls.setUploadFileName(uploadFilename);
			fileDtls.setFileSize(file.getSize());

			File saveFile = new File(uploadpath);
			if (!saveFile.exists()) {
				saveFile.mkdir();
			}

			String storePath = uploadpath.concat(uploadFilename);
			fileDtls.setPath(storePath);

			long upload = Files.copy(file.getInputStream(), Paths.get(storePath));
			if (upload != 0) {
				FileDetails saveFileDtls = fileRepository.save(fileDtls);
				return saveFileDtls;
			}

		}
		return null;
	}

	private String getDisplayFileName(String originalFilename) {
		String extension = FilenameUtils.getExtension(originalFilename);
		String filename = FilenameUtils.removeExtension(originalFilename);
		if (filename.length() > 8) {
			filename = filename.substring(0, 7);
		}
		filename = filename + "." + extension;
		return filename;
	}

	private void checkCategoryExist(CategoryDto category) throws Exception {
		categoryRepository.findById(category.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Category Id Invalid"));
	}

	@Override
	public List<NoteDto> getAllNotes() {
		List<NoteDto> list = noteRepository.findAll().stream().map(note -> mapper.map(note, NoteDto.class)).toList();
		return list;
	}

	@Override
	public NoteDto getNoteById(Integer id) {
		Optional<Notes> note = noteRepository.findById(id);
		mapper.map(note, NoteDto.class);
		if (!ObjectUtils.isEmpty(note)) {
			return mapper.map(note, NoteDto.class);
		}
		return null;
	}

	@Override
	public byte[] downloadFile(FileDetails fileDtls) throws Exception {

		InputStream io = new FileInputStream(fileDtls.getPath());

		return StreamUtils.copyToByteArray(io);
	}

	@Override
	public FileDetails getFileDetails(Integer id) throws Exception {
		FileDetails fileDetails = fileRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("File is Note Found"));
		return fileDetails;
	}

	@Override
	public NoteResponse getAllNotesByUser(Integer pageNo, Integer pageSize) {
		Integer userId = CommonUtil.getLoggedInUser().getId();
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<Notes> pageNotes = noteRepository.findByCreatedByAndIsDeletedFalse(userId,pageable);
		List<NoteDto> noteDto = pageNotes.get().map(n -> mapper.map(n, NoteDto.class)).toList();
		NoteResponse notes = NoteResponse.builder()
				.notes(noteDto)
				.pageNo(pageNotes.getNumber())
				.pageSize(pageNotes.getSize())
				.totalElements(pageNotes.getTotalElements())
				.totalPage(pageNotes.getTotalPages())
				.isFirst(pageNotes.isFirst())
				.isLast(pageNotes.isLast())
				.build();
		
		return notes;
	}

	@Override
	public void softDeleteNotes(Integer id) throws Exception {
		Notes note = noteRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Invalid Id..."));
		if(!ObjectUtils.isEmpty(note)) {
			note.setIsDeleted(true);
			note.setDeletedOn(LocalDateTime.now());
			noteRepository.save(note);
		}
	}

	@Override
	public void restoreNotes(Integer id) throws Exception {
		Notes note = noteRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Invalid id..."));
		if(!ObjectUtils.isEmpty(note)) {
			note.setIsDeleted(false);
			note.setDeletedOn(null);
			noteRepository.save(note);
		}
	}

	@Override
	public List<NoteDto> getUserRecycleBinNotes() {
		Integer userId = CommonUtil.getLoggedInUser().getId();
		List<Notes> notes = noteRepository.findByCreatedByAndIsDeletedTrue(userId);
		List<NoteDto> list = notes.stream().map(note->mapper.map(note, NoteDto.class)).toList();
		return list;
	}

	@Override
	public void hardDeleteNotes(Integer id) throws Exception {
		Notes notes = noteRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Invalid Id..."));
		
		if(notes.getIsDeleted()) {
			noteRepository.delete(notes);
		} else {
			throw new IllegalArgumentException("Sorry You can't hard delete directly");
		}
	}


	@Override
	public void emptyRecycle() {
		Integer userId = CommonUtil.getLoggedInUser().getId();
		List<Notes> notes = noteRepository.findByCreatedByAndIsDeletedTrue(userId);
		if(!CollectionUtils.isEmpty(notes)) {
			noteRepository.deleteAll(notes);
		}
	}
	

	@Override
	public void favouriteNotes(Integer noteId) throws Exception {
		Integer userId = CommonUtil.getLoggedInUser().getId();
		Notes note = noteRepository.findById(noteId).orElseThrow(()-> new ResourceNotFoundException("Notes not available..."));
		FavouriteNotes favouriteNote = FavouriteNotes.builder().note(note).userId(userId).build();
		favouriteNoteRepository.save(favouriteNote);
	}

	@Override
	public void unFavouriteNotes(Integer favouriteNoteId) throws Exception{
		FavouriteNotes note = favouriteNoteRepository.findById(favouriteNoteId).orElseThrow(()-> new ResourceNotFoundException("Favourite Notes not available.."));
		favouriteNoteRepository.delete(note);
	}

	@Override
	public List<FavouriteNotesDto> getUserFavouriteNotes() {
		int userId = CommonUtil.getLoggedInUser().getId();
		List<FavouriteNotes> notes = favouriteNoteRepository.findByUserId(userId);
		List<FavouriteNotesDto> favouriteNotes = notes.stream().map(note -> mapper.map(note, FavouriteNotesDto.class)).toList();
		return favouriteNotes;
	}

	@Override
	public Boolean copyNotes(Integer id) throws Exception {
		Notes notes = noteRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Invalid id..."));
		
		Notes copyNotes = Notes.builder().title(notes.getTitle()).description(notes.getDescription()).category(notes.getCategory()).isDeleted(false).fileDetails(null).build();
		Notes save = noteRepository.save(copyNotes);
		if(!ObjectUtils.isEmpty(save)) {
			return true;
		}
		return false;
	}

}
