package com.study.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.study.entity.Rating;

@Repository
public interface Rating_Repository extends JpaRepository<Rating, Integer> {

	Optional<Rating> findByPresentationPidAndUserUid(Integer pid, Integer uid);

}
