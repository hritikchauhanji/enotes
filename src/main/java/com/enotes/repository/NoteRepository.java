package com.enotes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enotes.entity.Notes;

public interface NoteRepository extends JpaRepository<Notes, Integer> {

}
