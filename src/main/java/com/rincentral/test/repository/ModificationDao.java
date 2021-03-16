package com.rincentral.test.repository;

import com.rincentral.test.domain.Modification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModificationDao extends JpaRepository<Modification, Long> {

    Modification findByTitleAndGenerationTitleAndGenerationModelTitle(
            String title, String generationTitle, String generationModelTitle);
//    Modification findModificationByTitleAndModelTitle(String title, String modelTitle);
}
