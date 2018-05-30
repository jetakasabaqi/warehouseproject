package com.project.service;

import com.project.domain.Product;
import com.project.domain.ProductType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public interface ProductTypeService {

    /**
     * Save a product type.
     *
     * @param productType the entity to save
     * @return the persisted entity
     */
    ProductType save(ProductType productType);

    /**
     * Get all the products types.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ProductType> findAll(Pageable pageable);

    /**
     * Get the "id" product.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ProductType findOne(Long id);

    /**
     * Delete the "id" product type.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    List<ProductType> findAll(CriteriaQuery<ProductType> query);
}