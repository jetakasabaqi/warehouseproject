package com.project.repository;

import com.project.domain.Price;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.math.BigDecimal;
import java.util.List;


/**
 * Spring Data JPA repository for the Price entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PriceRepository extends JpaRepository<Price, Long>, JpaSpecificationExecutor<Price>{


}
