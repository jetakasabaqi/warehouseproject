package com.project.service.impl;

import com.project.domain.Vendor;
import com.project.domain.WeightUnit;
import com.project.repository.VendorRepository;
import com.project.repository.WeightUnitRepository;
import com.project.service.WeightUnitService;
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
@Service
@Transactional
public class WeightUnitServiceImpl implements WeightUnitService {

    private final Logger log = LoggerFactory.getLogger(WeightUnitServiceImpl.class);

    private final WeightUnitRepository weightUnitRepository;

    @Autowired
    private EntityManager entityManager;

    public WeightUnitServiceImpl(WeightUnitRepository weightUnitRepository) {
        this.weightUnitRepository = weightUnitRepository;
    }

    /**
     * Save a weight unit.
     *
     * @param weightUnit the entity to save
     * @return the persisted entity
     */
    @Override
    public WeightUnit save(WeightUnit weightUnit) {
        log.debug("Request to save Weight Unit : {}", weightUnit);
        return weightUnitRepository.save(weightUnit);
    }

    /**
     * Get all the weight units.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<WeightUnit> findAll(Pageable pageable) {
        log.debug("Request to get all Vendors");
        return weightUnitRepository.findAll(pageable);
    }

    /**
     * Get one weight unit by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public WeightUnit findOne(Long id) {
        log.debug("Request to get weight unit : {}", id);
        return weightUnitRepository.findOne(id);
    }

    /**
     * Delete the weight unit by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete weight unit : {}", id);
        weightUnitRepository.delete(id);
    }

    @Override
    public List<WeightUnit> findAll(CriteriaQuery<WeightUnit> query) {
        return ParseRsql.findAll(query, entityManager);
    }
}
