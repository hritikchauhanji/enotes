package com.enotes.schedule;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.enotes.entity.Notes;
import com.enotes.repository.NoteRepository;

@Component
public class NoteScheduler {
	
	@Autowired
	private NoteRepository noteRepository;

//	@Scheduled(cron = "* * * ? * *")
	@Scheduled(cron = "0 0 0 * * ?")
	public void deleteNotesScheduler() {
//		System.out.println("i"); 
		LocalDateTime cutOffDate = LocalDateTime.now().minusDays(7);
		List<Notes> deletedNotes = noteRepository.findAllByIsDeletedAndDeletedOnBefore(true,cutOffDate);
		noteRepository.deleteAll(deletedNotes);
	}
}
