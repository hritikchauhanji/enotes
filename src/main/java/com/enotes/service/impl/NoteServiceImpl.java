package com.enotes.service.impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.enotes.dto.NoteDto;
import com.enotes.dto.NoteDto.CategoryDto;
import com.enotes.entity.Notes;
import com.enotes.exceptionhandling.ResourceNotFoundException;
import com.enotes.repository.CategoryRepository;
import com.enotes.repository.NoteRepository;
import com.enotes.service.NoteService;

@Service
public class NoteServiceImpl implements NoteService{
	
	@Autowired
	private NoteRepository noteRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public Boolean saveNote(NoteDto noteDto) throws Exception {
		
		//category validation
		checkCategoryExist(noteDto.getCategory());
		
		//validation notes
		
		
		Notes note = mapper.map(noteDto, Notes.class);
		Notes saveNote = noteRepository.save(note);
		if(!ObjectUtils.isEmpty(saveNote)){
			return true;
		}
		return false;
	}

	private void checkCategoryExist(CategoryDto category) throws Exception {
		categoryRepository.findById(category.getId()).orElseThrow(()-> new ResourceNotFoundException("Category Id Invalid"));
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
		if(!ObjectUtils.isEmpty(note)) {
			return mapper.map(note, NoteDto.class);
		}
		return null;
	}

}
