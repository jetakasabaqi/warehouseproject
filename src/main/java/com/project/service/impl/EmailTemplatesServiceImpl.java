package com.project.service.impl;

import com.project.domain.EmailTemplates;
import com.project.repository.EmailTemplatesRepository;
import com.project.service.EmailTemplatesService;
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
 * Service Implementation for managing EmailTemplates.
 */
@Service
@Transactional
public class EmailTemplatesServiceImpl implements EmailTemplatesService {

    private final Logger log = LoggerFactory.getLogger(EmailTemplatesServiceImpl.class);

    private final EmailTemplatesRepository emailTemplatesRepository;

    @Autowired
    private EntityManager entityManager;

    public EmailTemplatesServiceImpl(EmailTemplatesRepository emailTemplatesRepository) {
        this.emailTemplatesRepository = emailTemplatesRepository;
    }

    /**
     * Save a email template.
     *
     * @param emailTemplates the entity to save
     * @return the persisted entity
     */
    @Override
    public EmailTemplates save(EmailTemplates emailTemplates) {
        log.debug("Request to save Email Template : {}", emailTemplates);
        return emailTemplatesRepository.save(emailTemplates);
    }

    /**
     * Get all the email templates.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EmailTemplates> findAll(Pageable pageable) {
        log.debug("Request to get all Employees");
        return emailTemplatesRepository.findAll(pageable);
    }

    /**
     * Get one email templates by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public EmailTemplates findOne(Long id) throws Exception {
        log.debug("Request to get Email Templates : {}", id);
        EmailTemplates emailTemplates = emailTemplatesRepository.findOne(id);
        if (emailTemplates == null)

        {
            throw new Exception("EmailTemplatesID not found.");
        } else {
            return emailTemplates;
        }
    }

    /**
     * Delete the email template by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) throws Exception {
        log.debug("Request to delete Email Templates : {}", id);
        emailTemplatesRepository.delete(id);
        EmailTemplates emailTemplates = emailTemplatesRepository.findOne(id);
        if (emailTemplates == null)

        {
            throw new Exception("EmailTemplatesID not found.");
        } else {
            emailTemplatesRepository.delete(id);
        }
    }

    /**
     * Get all the email templates by a filter
     *
     * @param query the filter
     * @return the list of entities
     */
    @Override
    public List<EmailTemplates> findAll(CriteriaQuery<EmailTemplates> query) {
        return ParseRsql.findAll(query, entityManager);
    }

    @Override
    public String getTemplateByID(Long i) {
        return emailTemplatesRepository.getTemplateByID(i);
    }

}
