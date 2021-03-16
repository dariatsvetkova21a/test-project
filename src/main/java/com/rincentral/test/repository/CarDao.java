package com.rincentral.test.repository;

import com.rincentral.test.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CarDao extends JpaRepository<Car, Long>, JpaSpecificationExecutor<Car> {
}
