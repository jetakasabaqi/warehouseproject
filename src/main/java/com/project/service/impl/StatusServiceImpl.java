package com.project.service.impl;

import com.project.domain.Status;
import com.project.repository.StatusRepository;
import com.project.service.StatusService;
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
 * Service Implementation for managing Status.
 */
@Service
@Transactional
public class StatusServiceImpl implements StatusService {

    private final Logger log = LoggerFactory.getLogger(StatusServiceImpl.class);

    private final StatusRepository statusRepository;

    @Autowired
    private EntityManager entityManager;

    public StatusServiceImpl(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    /**
     * Save a status.
     *
     * @param status the entity to save
     * @return the persisted entity
     */
    @Override
    public Status save(Status status) {
        log.debug("Request to save Status : {}", status);
        return statusRepository.save(status);
    }

    /**
     * Get all the statuses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Status> findAll(Pageable pageable) {
        log.debug("Request to get all Statuses");
        return statusRepository.findAll(pageable);
    }

    /**
     * Get one status by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Status findOne(Long id) {
        log.debug("Request to get Status : {}", id);
        return statusRepository.findOne(id);
    }

    /**
     * Delete the status by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Status : {}", id);
        statusRepository.delete(id);
    }

    @Override
    public List<Status> findAll(CriteriaQuery<Status> query) {
        return ParseRsql.findAll(query, entityManager);
    }


}
