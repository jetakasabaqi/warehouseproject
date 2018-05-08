package com.project.service.impl;

import com.project.service.WarehouseLocationService;
import com.project.domain.WarehouseLocation;
import com.project.repository.WarehouseLocationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing WarehouseLocation.
 */
@Service
@Transactional
public class WarehouseLocationServiceImpl implements WarehouseLocationService {

    private final Logger log = LoggerFactory.getLogger(WarehouseLocationServiceImpl.class);

    private final WarehouseLocationRepository warehouseLocationRepository;

    public WarehouseLocationServiceImpl(WarehouseLocationRepository warehouseLocationRepository) {
        this.warehouseLocationRepository = warehouseLocationRepository;
    }

    /**
     * Save a warehouseLocation.
     *
     * @param warehouseLocation the entity to save
     * @return the persisted entity
     */
    @Override
    public WarehouseLocation save(WarehouseLocation warehouseLocation) {
        log.debug("Request to save WarehouseLocation : {}", warehouseLocation);
        return warehouseLocationRepository.save(warehouseLocation);
    }

    /**
     * Get all the warehouseLocations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<WarehouseLocation> findAll(Pageable pageable) {
        log.debug("Request to get all WarehouseLocations");
        return warehouseLocationRepository.findAll(pageable);
    }

    /**
     * Get one warehouseLocation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public WarehouseLocation findOne(Long id) {
        log.debug("Request to get WarehouseLocation : {}", id);
        return warehouseLocationRepository.findOne(id);
    }

    /**
     * Delete the warehouseLocation by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete WarehouseLocation : {}", id);
        warehouseLocationRepository.delete(id);
    }
}
