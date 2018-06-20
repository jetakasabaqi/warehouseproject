package com.project.service.impl;

import com.project.domain.ProductType;
import com.project.repository.ProductTypeRepository;
import com.project.service.ProductTypeService;
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
public class ProductTypeServiceImpl implements ProductTypeService {
    private final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    private ProductTypeRepository productTypeRepository;

    @Autowired
    private EntityManager entityManager;


    public ProductTypeServiceImpl(ProductTypeRepository productTypeRepository) {
        this.productTypeRepository = productTypeRepository;
    }


    /**
     * Save a product type.
     *
     * @param productType the entity to save
     * @return the persisted entity
     */

    public ProductType save(ProductType productType) {
        log.debug("Request to save Product : {}", productType);
        return productTypeRepository.save(productType);
    }

    /**
     * Get all the products types.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */

    @Override
    @Transactional(readOnly = true)
    public Page<ProductType> findAll(Pageable pageable) {
        log.debug("Request to get all Products Types");
        return productTypeRepository.findAll(pageable);
    }

    /**
     * Get one product by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ProductType findOne(Long id) {
        log.debug("Request to get Product type : {}", id);
        return productTypeRepository.findOne(id);
    }

    /**
     * Delete the product by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Product Type : {}", id);
        productTypeRepository.delete(id);
    }
    /**
     * Get all the productTypes by a filter
     *
     * @param query the filter
     * @return the list of entities
     */
    @Override
    public List<ProductType> findAll(CriteriaQuery<ProductType> query) {
        return ParseRsql.findAll(query, entityManager);
    }

}
