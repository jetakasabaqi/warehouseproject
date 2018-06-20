package com.project.repository;

import com.project.domain.WeightUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface WeightUnitRepository extends JpaRepository<WeightUnit, Long>, JpaSpecificationExecutor {
}
