package com.project.repository;

import com.project.domain.WarehouseLocation;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the WarehouseLocation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WarehouseLocationRepository extends JpaRepository<WarehouseLocation, Long>,JpaSpecificationExecutor {

}
