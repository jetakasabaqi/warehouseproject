package com.project.service;

import com.project.domain.Price;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Price.
 */
public interface PriceService {

    /**
     * Save a price.
     *
     * @param price the entity to save
     * @return the persisted entity
     */
    Price save(Price price);

    /**
     * Get all the prices.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Price> findAll(Pageable pageable);

    /**
     * Get the "id" price.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Price findOne(Long id);

    /**
     * Delete the "id" price.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
