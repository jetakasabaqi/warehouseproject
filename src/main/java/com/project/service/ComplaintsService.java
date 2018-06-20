package com.project.service;

import com.project.domain.City;
import com.project.domain.Complaints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public interface ComplaintsService {

    /**
     * Save a complaint.
     *
     * @param complaints the entity to save
     * @return the persisted entity
     */
    Complaints save(Complaints complaints);

    /**
     * Get all the complaints.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Complaints> findAll(Pageable pageable);

    /**
     * Get the "id" complain.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Complaints findOne(Long id);

    /**
     * Delete the "id" complain.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Get all the complaints by a filter
     *
     * @param query
     * @return the list of entities
     */
    List<Complaints> findAll(CriteriaQuery<Complaints> query);
}
