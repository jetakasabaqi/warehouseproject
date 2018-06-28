package com.project.repository;

import com.project.domain.EmailTemplates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the EmailTemplatesRepository entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmailTemplatesRepository extends JpaRepository<EmailTemplates, Long>, JpaSpecificationExecutor {

    @Query(" Select template from EmailTemplates where id=:i")
    String getTemplateByID(@Param(value = "i") Long i);
}
