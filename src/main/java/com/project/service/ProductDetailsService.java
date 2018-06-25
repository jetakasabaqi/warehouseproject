package com.project.service;

import com.project.domain.ProductDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public interface ProductDetailsService {
    /**
     * Save a product details.
     *
     * @param productDetails the entity to save
     * @return the persisted entity
     */
    ProductDetails save(ProductDetails productDetails);

    /**
     * Get all the products details.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ProductDetails> findAll(Pageable pageable);

    /**
     * Get the "id" product details.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ProductDetails findOne(Long id) throws Exception;

    /**
     * Delete the "id" product details.
     *
     * @param id the id of the entity
     */
    void delete(Long id) throws Exception;

    /**
     * Get all the productDetails by a filter
     *
     * @param query
     * @return the list of entities
     */
    List<ProductDetails> findAll(CriteriaQuery<ProductDetails> query);
}
