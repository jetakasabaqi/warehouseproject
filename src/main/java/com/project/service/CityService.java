package com.project.service;

import com.project.domain.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

/**
 * Service Interface for managing City.
 */
public interface CityService {

    /**
     * Save a city.
     *
     * @param city the entity to save
     * @return the persisted entity
     */
    City save(City city);

    /**
     * Get all the cities.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<City> findAll(Pageable pageable);

    /**
     * Get the "id" city.
     *
     * @param id the id of the entity
     * @return the entity
     */
    City findOne(Long id);

    /**
     * Delete the "id" city.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Get all the cities by a filter
     *
     * @param query
     * @return the list of entities
     */
    List<City> findAll(CriteriaQuery<com.project.domain.City> query);
}
