package com.project.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.project.domain.EmailTemplates;
import com.project.domain.Employee;
import com.project.rsql1.jpa.JpaCriteriaQueryVisitor;
import com.project.service.EmailTemplatesService;
import com.project.service.EmployeeService;
import com.project.web.rest.errors.BadRequestAlertException;
import com.project.web.rest.util.HeaderUtil;
import com.project.web.rest.util.PaginationUtil;
import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import cz.jirutka.rsql.parser.ast.RSQLVisitor;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Employee.
 */
@RestController
@RequestMapping("/api")
public class EmailTemplatesResource {

    private final Logger log = LoggerFactory.getLogger(EmailTemplatesResource.class);

    private static final String ENTITY_NAME = "email_templates";

    private final EmailTemplatesService emailTemplatesService;

    private final EntityManager entityManager;

    public EmailTemplatesResource(EmailTemplatesService emailTemplatesService, EntityManager entityManager) {
        this.emailTemplatesService = emailTemplatesService;
        this.entityManager = entityManager;
    }

    /**
     * POST  /employees : Create a new employee.
     *
     * @param employee the employee to create
     * @return the ResponseEntity with status 201 (Created) and with body the new employee, or with status 400 (Bad Request) if the employee has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/email-templates")
    @Timed
    public ResponseEntity<EmailTemplates> createEmployee(@RequestBody EmailTemplates employee) throws URISyntaxException {
        log.debug("REST request to save Employee : {}", employee);
        if (employee.getId() != null) {
            throw new BadRequestAlertException("A new employee cannot already have an ID", ENTITY_NAME, "idexists");
        }

        EmailTemplates result = emailTemplatesService.save(employee);
        return ResponseEntity.created(new URI("/api/employees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /employees : Updates an existing employee.
     *
     * @param employee the employee to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated employee,
     * or with status 400 (Bad Request) if the employee is not valid,
     * or with status 500 (Internal Server Error) if the employee couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/email-templates")
    @Timed
    public ResponseEntity<EmailTemplates> updateEmployee(@RequestBody EmailTemplates employee) throws URISyntaxException {
        log.debug("REST request to update Employee : {}", employee);
        if (employee.getName() == null ) {
            throw new BadRequestAlertException("Fields cannot be null", ENTITY_NAME, "null");
        } else {
            if (employee.getId() == null) {
                return createEmployee(employee);
            }
            EmailTemplates result = emailTemplatesService.save(employee);
            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, employee.getId().toString()))
                .body(result);
        }
    }


    /**
     * GET  /employees/:id : get the "id" employee.
     *
     * @param id the id of the employee to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the employee, or with status 404 (Not Found)
     */
    @GetMapping("/email-templates/{id}")
    @Timed
    public ResponseEntity<EmailTemplates> getEmployee(@PathVariable Long id) throws Exception {
        log.debug("REST request to get Email Templates : {}", id);

        EmailTemplates employee = emailTemplatesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(employee));
    }

    @GetMapping("/email-templates/{id}/template")
    @Timed
    public ResponseEntity<String> getTemplate(@PathVariable Long id) throws Exception {
        log.debug("REST request to get Email Templates : {}", id);

        String template = emailTemplatesService.getTemplateByID(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(template));
    }
    /**
     * DELETE  /employees/:id : delete the "id" employee.
     *
     * @param id the id of the employee to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/email-templates/{id}")
    @Timed
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) throws Exception {
        log.debug("REST request to delete Employee : {}", id);
        emailTemplatesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/email-templates")
    @ResponseBody
    public ResponseEntity<List<EmailTemplates>> findAllByRsql(@RequestParam(value = "search", required = false) String search, Pageable pageable) {


        if (search == null) {
            Page<EmailTemplates> page = emailTemplatesService.findAll(pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/email-templates");
            return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
        } else {
            RSQLVisitor<CriteriaQuery<EmailTemplates>, EntityManager> visitor = new JpaCriteriaQueryVisitor<EmailTemplates>();
            final Node rootNode = new RSQLParser().parse(search);
            CriteriaQuery<EmailTemplates> query = rootNode.accept(visitor, entityManager);
            List<EmailTemplates> employees = emailTemplatesService.findAll(query);


            return new ResponseEntity<>(employees, HttpStatus.OK);
        }
    }


}



