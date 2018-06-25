package com.project.service;

import com.project.domain.Vendor;
import com.project.domain.WeightUnit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public interface WeightUnitService {

    /**
     * Save a weight unit.
     *
     * @param weightUnit the entity to save
     * @return the persisted entity
     */
    WeightUnit save(WeightUnit weightUnit);

    /**
     * Get all the weight units.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<WeightUnit> findAll(Pageable pageable);

    /**
     * Get the "id" weight unit.
     *
     * @param id the id of the entity
     * @return the entity
     */
    WeightUnit findOne(Long id) throws Exception;

    /**
     * Delete the "id" weight unit.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Get all the weight units by a filter
     *
     * @param query
     * @return the list of entities
     */
    List<WeightUnit> findAll(CriteriaQuery<WeightUnit> query);
}
