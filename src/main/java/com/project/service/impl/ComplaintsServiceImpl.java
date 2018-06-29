package com.project.service.impl;

import com.project.domain.Complaints;
import com.project.repository.ComplaintsRepository;
import com.project.service.ComplaintsService;
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
 * Service Implementation for managing Complaints.
 */
@Service
@Transactional
public class ComplaintsServiceImpl implements ComplaintsService {


    private final Logger log = LoggerFactory.getLogger(ComplaintsServiceImpl.class);

    private final ComplaintsRepository complaintsRepository;

    @Autowired
    EntityManager entityManager;

    public ComplaintsServiceImpl(ComplaintsRepository complaintsRepository) {
        this.complaintsRepository = complaintsRepository;
    }

    /**
     * Save a complain.
     *
     * @param complaints the entity to save
     * @return the persisted entity
     */
    @Override
    public Complaints save(Complaints complaints) {
        log.debug("Request to save Complain : {}", complaints);
        return complaintsRepository.save(complaints);
    }

    /**
     * Get all the complaints.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<Complaints> findAll(Pageable pageable) throws Exception {
        log.debug("Request to get all Complaints");
        return  complaintsRepository.findAll(pageable);

    }

    /**
     * Get one complain by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Complaints findOne(Long id) throws Exception {
        log.debug("Request to get Complain : {}", id);
        Complaints complaints=complaintsRepository.findOne(id);

            return complaints;


    }

    /**
     * Delete the complain by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) throws Exception {
        log.debug("Request to delete Complain : {}", id);
        Complaints complaints=findOne(id);
        if(complaints==null)
        {
            throw new Exception("ComplaintID not found");
        }
        else {
            complaintsRepository.delete(id);
        }


    }

    /**
     * Get all the complains by a filter
     *
     * @param query the filter
     * @return the list of entities
     */
    @Override
    public List<Complaints> findAll(CriteriaQuery<Complaints> query) throws Exception {
      return ParseRsql.findAll(query,entityManager);


    }
}
