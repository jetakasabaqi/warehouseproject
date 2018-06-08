package com.project.service.impl;

import com.project.domain.Complaints;
import com.project.repository.CityRepository;
import com.project.repository.ComplaintsRepository;
import com.project.service.ComplaintsService;
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
public class ComplaintsServiceImpl implements ComplaintsService {


    private final Logger log = LoggerFactory.getLogger(ComplaintsServiceImpl.class);

    private final ComplaintsRepository complaintsRepository;

    @Autowired
    EntityManager entityManager;

    public ComplaintsServiceImpl(ComplaintsRepository complaintsRepository) {
        this.complaintsRepository = complaintsRepository;
    }
    @Override
    public Complaints save(Complaints complaints) {
        return complaintsRepository.save(complaints);
    }

    @Override
    public Page<Complaints> findAll(Pageable pageable) {
        return complaintsRepository.findAll(pageable);
    }

    @Override
    public Complaints findOne(Long id) {

        return complaintsRepository.findOne(id);
    }

    @Override
    public void delete(Long id) {

   complaintsRepository.delete(id);
    }

    @Override
    public List<Complaints> findAll(CriteriaQuery<Complaints> query) {
        return complaintsRepository.findAll();
    }
}
