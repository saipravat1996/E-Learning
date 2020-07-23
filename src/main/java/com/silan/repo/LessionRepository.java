package com.silan.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.silan.model.Lession;

public interface LessionRepository extends JpaRepository<Lession, String> {

	@Query("select  chapter from Lession  ")
	List<String> getAllChapter() ;
}
