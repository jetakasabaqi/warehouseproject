package com.project.service.impl;

import com.project.domain.ReceiverInfo;
import com.project.repository.ReceiverInfoRepository;
import com.project.service.ReceiverInfoService;
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
 * Service Implementation for managing ReceiverInfo.
 */
@Service
@Transactional
public class ReceiverInfoServiceImpl implements ReceiverInfoService {

    private final Logger log = LoggerFactory.getLogger(ReceiverInfoServiceImpl.class);

    private final ReceiverInfoRepository receiverInfoRepository;
    @Autowired
    private EntityManager entityManager;

    public ReceiverInfoServiceImpl(ReceiverInfoRepository receiverInfoRepository) {
        this.receiverInfoRepository = receiverInfoRepository;
    }

    /**
     * Save a receiverInfo.
     *
     * @param receiverInfo the entity to save
     * @return the persisted entity
     */
    @Override
    public ReceiverInfo save(ReceiverInfo receiverInfo) {
        log.debug("Request to save ReceiverInfo : {}", receiverInfo);
        return receiverInfoRepository.save(receiverInfo);
    }

    /**
     * Get all the receiverInfos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ReceiverInfo> findAll(Pageable pageable) {
        log.debug("Request to get all ReceiverInfos");
        return receiverInfoRepository.findAll(pageable);
    }

    /**
     * Get one receiverInfo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ReceiverInfo findOne(Long id) {
        log.debug("Request to get ReceiverInfo : {}", id);
        return receiverInfoRepository.findOne(id);
    }

    /**
     * Delete the receiverInfo by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ReceiverInfo : {}", id);
        receiverInfoRepository.delete(id);
    }

    /**
     * Get all the receivers by a filter
     *
     * @param query the filter
     * @return the list of entities
     */
    @Override
    public List<ReceiverInfo> findAll(CriteriaQuery<ReceiverInfo> query) {
        return ParseRsql.findAll(query, entityManager);
    }


}
