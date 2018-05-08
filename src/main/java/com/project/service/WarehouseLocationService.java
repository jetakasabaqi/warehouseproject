package com.project.service;

import com.project.domain.WarehouseLocation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing WarehouseLocation.
 */
public interface WarehouseLocationService {

    /**
     * Save a warehouseLocation.
     *
     * @param warehouseLocation the entity to save
     * @return the persisted entity
     */
    WarehouseLocation save(WarehouseLocation warehouseLocation);

    /**
     * Get all the warehouseLocations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<WarehouseLocation> findAll(Pageable pageable);

    /**
     * Get the "id" warehouseLocation.
     *
     * @param id the id of the entity
     * @return the entity
     */
    WarehouseLocation findOne(Long id);

    /**
     * Delete the "id" warehouseLocation.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
