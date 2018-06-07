package com.project.service.impl;

import com.project.domain.CronJobs;
import com.project.domain.Employee;
import com.project.repository.CityRepository;
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

    @Override
    public CronJobs save(CronJobs cronJobs) {
        return cronJobsRepository.save(cronJobs);
    }

    @Override
    public Page<CronJobs> findAll(Pageable pageable) {
        return cronJobsRepository.findAll(pageable);
    }

    @Override
    public CronJobs findOne(Long id) {
        cronJobsRepository.findOne(id);

        return  cronJobsRepository.findOne(id);}


    @Override
    public void delete(Long id) {
       cronJobsRepository.delete(id);
    }

    @Override
    public List<CronJobs> findAll(CriteriaQuery<CronJobs> query) {
        return findAll(query);
    }
}
