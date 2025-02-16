package com.enotes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enotes.entity.FavouriteNotes;

public interface FavouriteNoteRepository extends JpaRepository<FavouriteNotes, Integer > {

	List<FavouriteNotes> findByUserId(Integer userId);

}
