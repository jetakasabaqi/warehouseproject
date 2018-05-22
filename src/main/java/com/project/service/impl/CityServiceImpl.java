package com.project.service.impl;

import com.project.service.CityService;
import com.project.domain.City;
import com.project.repository.CityRepository;
import com.project.service.util.ParseRsql;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;


/**
 * Service Implementation for managing City.
 */
@Service
@Transactional
public class CityServiceImpl implements CityService {

    private final Logger log = LoggerFactory.getLogger(CityServiceImpl.class);

    private final CityRepository cityRepository;

    @Autowired
    EntityManager entityManager;

    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    /**
     * Save a city.
     *
     * @param city the entity to save
     * @return the persisted entity
     */
    @Override
    public City save(City city) {
        log.debug("Request to save City : {}", city);
        return cityRepository.save(city);
    }

    /**
     * Get all the cities.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<City> findAll(Pageable pageable) {
        log.debug("Request to get all Cities");
        return cityRepository.findAll(pageable);
    }

    /**
     * Get one city by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public City findOne(Long id) {
        log.debug("Request to get City : {}", id);
        return cityRepository.findOne(id);
    }

    /**
     * Delete the city by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete City : {}", id);
        cityRepository.delete(id);
    }

    @Override
    public List<City> findAll(CriteriaQuery<City> query) {
        return ParseRsql.findAll(query, entityManager);
    }


}
