package com.enotes.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import com.enotes.dto.NoteDto;
import com.enotes.dto.NoteDto.CategoryDto;
import com.enotes.dto.NoteResponse;
import com.enotes.entity.FileDetails;
import com.enotes.entity.Notes;
import com.enotes.exceptionhandling.ResourceNotFoundException;
import com.enotes.repository.CategoryRepository;
import com.enotes.repository.FileRepository;
import com.enotes.repository.NoteRepository;
import com.enotes.service.NoteService;
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

	@Override
	public Boolean saveNote(String notes, MultipartFile file) throws Exception {

		ObjectMapper ob = new ObjectMapper();
		NoteDto noteDto = ob.readValue(notes, NoteDto.class);

		// category validation
		checkCategoryExist(noteDto.getCategory());

		Notes note = mapper.map(noteDto, Notes.class);

		FileDetails fileDtls = saveFileDetails(file);
		if (!ObjectUtils.isEmpty(fileDtls)) {
			note.setFileDetails(fileDtls);
		} else {
			note.setFileDetails(null);
		}

		// validation notes

		Notes saveNote = noteRepository.save(note);
		if (!ObjectUtils.isEmpty(saveNote)) {
			return true;
		}
		return false;
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
	public NoteResponse getAllNotesByUser(Integer userId, Integer pageNo, Integer pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<Notes> pageNotes = noteRepository.findByCreatedBy(userId,pageable);
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

}
