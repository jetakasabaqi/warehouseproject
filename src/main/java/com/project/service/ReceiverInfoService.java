package com.project.service;

import com.project.domain.ReceiverInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

/**
 * Service Interface for managing ReceiverInfo.
 */
public interface ReceiverInfoService {

    /**
     * Save a receiverInfo.
     *
     * @param receiverInfo the entity to save
     * @return the persisted entity
     */
    ReceiverInfo save(ReceiverInfo receiverInfo);

    /**
     * Get all the receiverInfos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ReceiverInfo> findAll(Pageable pageable);

    /**
     * Get the "id" receiverInfo.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ReceiverInfo findOne(Long id);

    /**
     * Delete the "id" receiverInfo.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Get all the receiverInfo by a filter
     *
     * @param query
     * @return the list of entities
     */
    List<ReceiverInfo> findAll(CriteriaQuery<ReceiverInfo> query);
}
