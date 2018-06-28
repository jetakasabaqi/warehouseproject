package com.project.service;

import com.project.domain.EmailTemplates;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

/**
 * Service Interface for managing EmailTemplates.
 */
public interface EmailTemplatesService {

    /**
     * Save a email template.
     *
     * @param emailTemplates the entity to save
     * @return the persisted entity
     */
    EmailTemplates save(EmailTemplates emailTemplates);

    /**
     * Get all the email templates.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<EmailTemplates> findAll(Pageable pageable);

    /**
     * Get the "id" email template.
     *
     * @param id the id of the entity
     * @return the entity
     */
    EmailTemplates findOne(Long id) throws Exception;

    /**
     * Delete the "id" email template.
     *
     * @param id the id of the entity
     */
    void delete(Long id) throws Exception;

    /**
     * Get all the email templates by a filter
     *
     * @param query
     * @return the list of entities
     */
    List<EmailTemplates> findAll(CriteriaQuery<EmailTemplates> query);

    String getTemplateByID(Long i);
}
