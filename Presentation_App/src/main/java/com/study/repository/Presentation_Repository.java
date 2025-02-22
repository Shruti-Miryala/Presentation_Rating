package com.study.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.study.entity.Presentation;

@Repository
public interface Presentation_Repository extends JpaRepository<Presentation, Integer> {
	
	List<Presentation> findAllByStudentUid(Integer uid);
	
	Optional<Presentation> findByStudentUidAndPid(Integer uid, Integer pid);

	List<Presentation> findByStudentUid(Integer uid);
	

}
