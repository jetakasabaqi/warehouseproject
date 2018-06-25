package com.project.service.impl;

import com.project.domain.Vendor;
import com.project.repository.VendorRepository;
import com.project.service.VendorService;
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
 * Service Implementation for managing Vendor.
 */
@Service
@Transactional
public class VendorServiceImpl implements VendorService {

    private final Logger log = LoggerFactory.getLogger(VendorServiceImpl.class);

    private final VendorRepository vendorRepository;

    @Autowired
    private EntityManager entityManager;

    public VendorServiceImpl(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    /**
     * Save a vendor.
     *
     * @param vendor the entity to save
     * @return the persisted entity
     */
    @Override
    public Vendor save(Vendor vendor) {
        log.debug("Request to save Vendor : {}", vendor);
        return vendorRepository.save(vendor);
    }

    /**
     * Get all the vendors.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Vendor> findAll(Pageable pageable) {
        log.debug("Request to get all Vendors");
        return vendorRepository.findAll(pageable);
    }

    /**
     * Get one vendor by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Vendor findOne(Long id) throws Exception {
        log.debug("Request to get Vendor : {}", id);
        Vendor vendor = vendorRepository.findOne(id);
        if (vendor == null) {
            throw new Exception("VendorID not found");
        } else {
            return vendorRepository.findOne(id);
        }
    }

    /**
     * Delete the vendor by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) throws Exception {
        log.debug("Request to delete Vendor : {}", id);
        Vendor vendor = vendorRepository.findOne(id);
        if (vendor == null) {
            throw new Exception("VendorID not found");
        } else {
            vendorRepository.delete(id);
        }
    }

    /**
     * Get all the vendors by a filter
     *
     * @param query the filter
     * @return the list of entities
     */
    @Override
    public List<Vendor> findAll(CriteriaQuery<Vendor> query) {
        return ParseRsql.findAll(query, entityManager);
    }


}
