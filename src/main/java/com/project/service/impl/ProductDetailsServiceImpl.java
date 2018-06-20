package com.project.service.impl;

import com.project.domain.ProductDetails;
import com.project.repository.ProductDetailsRepository;
import com.project.service.ProductDetailsService;
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
 * Service Implementation for managing Product.
 */
@Service
@Transactional
public class ProductDetailsServiceImpl implements ProductDetailsService {

    private final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    private ProductDetailsRepository productDetailsRepository;

    @Autowired
    private EntityManager entityManager;


    public ProductDetailsServiceImpl(ProductDetailsRepository productDetailsRepository) {
        this.productDetailsRepository = productDetailsRepository;
    }


    /**
     * Save a product.
     *
     * @param productDetails the entity to save
     * @return the persisted entity
     */
    @Override
    public ProductDetails save(ProductDetails productDetails) {
        log.debug("Request to save Product : {}", productDetails);
        return productDetailsRepository.save(productDetails);
    }

    /**
     * Get all the products.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProductDetails> findAll(Pageable pageable) {
        log.debug("Request to get all Products");
        return productDetailsRepository.findAll(pageable);
    }

    /**
     * Get one product by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ProductDetails findOne(Long id) {
        log.debug("Request to get Product Details : {}", id);
        return productDetailsRepository.findOne(id);
    }

    /**
     * Delete the product by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Product : {}", id);
        productDetailsRepository.delete(id);
    }

    /**
     * Get all the productDetails by a filter
     *
     * @param query the filter
     * @return the list of entities
     */
    @Override
    public List<ProductDetails> findAll(CriteriaQuery<ProductDetails> query) {
        return ParseRsql.findAll(query, entityManager);
    }


}
