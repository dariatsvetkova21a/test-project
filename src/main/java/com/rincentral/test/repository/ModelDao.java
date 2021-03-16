package com.rincentral.test.repository;

import com.rincentral.test.domain.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModelDao extends JpaRepository<Model, Long> {

    Model findByTitle(String title);
}
