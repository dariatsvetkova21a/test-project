package com.rincentral.test.repository;

import com.rincentral.test.domain.Segment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SegmentDao extends JpaRepository<Segment, Long> {
}
