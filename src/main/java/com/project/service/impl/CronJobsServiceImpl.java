package com.project.service.impl;

import com.project.domain.CronJobs;
import com.project.repository.CronJobsRepository;
import com.project.service.CronJobsService;
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
public class CronJobsServiceImpl implements CronJobsService {

    private final Logger log = LoggerFactory.getLogger(CronJobsServiceImpl.class);

    private final CronJobsRepository cronJobsRepository;

    @Autowired
    EntityManager entityManager;

    public CronJobsServiceImpl(CronJobsRepository cronJobsRepository) {
        this.cronJobsRepository = cronJobsRepository;
    }

    /**
     * Save a cron job.
     *
     * @param cronJobs the entity to save
     * @return the persisted entity
     */
    @Override
    public CronJobs save(CronJobs cronJobs) {

        log.debug("Request to save CronJob : {}", cronJobs);
        return cronJobsRepository.save(cronJobs);
    }

    /**
     * Get all cronJobs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<CronJobs> findAll(Pageable pageable) {
        log.debug("Request to get all CronJobs");
        return cronJobsRepository.findAll(pageable);
    }

    /**
     * Get one cronJob by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public CronJobs findOne(Long id) {

        log.debug("Request to get CronJob : {}", id);


        return cronJobsRepository.findOne(id);
    }

    /**
     * Delete the cronJob by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {

        log.debug("Request to delete CronJob : {}", id);
        cronJobsRepository.delete(id);
    }

    /**
     * Get all the cronJobs by a filter
     *
     * @param query the filter
     * @return the list of entities
     */
    @Override
    public List<CronJobs> findAll(CriteriaQuery<CronJobs> query) {
        return findAll(query);
    }
}
