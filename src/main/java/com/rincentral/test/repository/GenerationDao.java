package com.rincentral.test.repository;

import com.rincentral.test.domain.Generation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenerationDao extends JpaRepository<Generation, Long> {

//    List<Generation> findByTitleAndModelTitle(String title, String modelTitle);
}