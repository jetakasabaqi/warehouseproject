package com.project.service.impl;

import com.project.domain.Price;
import com.project.repository.PriceRepository;
import com.project.service.PriceService;
import com.project.service.util.ParseRsql;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;


/**
 * Service Implementation for managing Price.
 */
@Service
@Transactional
public class PriceServiceImpl implements PriceService {

    private final Logger log = LoggerFactory.getLogger(PriceServiceImpl.class);
    @Autowired
    private final PriceRepository priceRepository;

    @Autowired
    private EntityManager entityManager;

    public PriceServiceImpl(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    /**
     * Save a price.
     *
     * @param price the entity to save
     * @return the persisted entity
     */
    @Override
    public Price save(Price price) {
        log.debug("Request to save Price : {}", price);
        return priceRepository.save(price);
    }

    /**
     * Get all the prices.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Price> findAll(Pageable pageable) {
        log.debug("Request to get all Prices");
        return priceRepository.findAll(pageable);
    }

    /**
     * Get one price by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Price findOne(Long id) throws Exception {
        log.debug("Request to get Price : {}", id);
        Price price = priceRepository.findOne(id);
        if (price == null) {
            throw new Exception("PriceID not found");
        } else {
            return priceRepository.findOne(id);
        }
    }

    /**
     * Delete the price by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) throws Exception {
        log.debug("Request to delete Price : {}", id);
        Price price = priceRepository.findOne(id);
        if (price == null) {
            throw new Exception("PriceID not found");
        } else {
            priceRepository.delete(id);
        }
    }

    /**
     * Get all the prices by a filter
     *
     * @param query the filter
     * @return the list of entities
     */
    @Override
    public List<Price> findAll(CriteriaQuery<Price> query) {
        return ParseRsql.findAll(query, entityManager);
    }


}
