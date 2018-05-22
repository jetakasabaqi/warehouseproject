package com.project.service;

import com.project.domain.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

/**
 * Service Interface for managing Status.
 */
public interface StatusService {

    /**
     * Save a status.
     *
     * @param status the entity to save
     * @return the persisted entity
     */
    Status save(Status status);

    /**
     * Get all the statuses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Status> findAll(Pageable pageable);

    /**
     * Get the "id" status.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Status findOne(Long id);

    /**
     * Delete the "id" status.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    List<Status> findAll(CriteriaQuery<Status> query);
}
