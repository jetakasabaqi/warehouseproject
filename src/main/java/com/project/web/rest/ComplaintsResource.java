package com.project.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.project.domain.Complaints;
import com.project.rsql1.jpa.JpaCriteriaQueryVisitor;
import com.project.service.ComplaintsService;
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
 * REST controller for managing City.
 */
@RestController
@RequestMapping("/api")
public class ComplaintsResource {

    private final Logger log = LoggerFactory.getLogger(ComplaintsResource.class);

    private static final String ENTITY_NAME = "complaints";

    private final ComplaintsService complaintsService;

    private final EntityManager entityManager;

    public ComplaintsResource(ComplaintsService complaintsService, EntityManager entityManager) {
        this.complaintsService = complaintsService;
        this.entityManager = entityManager;
    }

    /**
     * POST  /complaints : Create a new complain.
     *
     * @param complaints the complain to create
     * @return the ResponseEntity with status 201 (Created) and with body the new complain, or with status 400 (Bad Request) if the complain has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/complaints")
    @Timed
    public ResponseEntity<Complaints> createComplain(@RequestBody Complaints complaints) throws URISyntaxException {
        log.debug("REST request to save complain : {}", complaints);
        if (complaints.getId() != null) {
            throw new BadRequestAlertException("A new city cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Complaints result = complaintsService.save(complaints);
        return ResponseEntity.created(new URI("/api/complaints/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /complaints : Updates an existing complain.
     *
     * @param complaints the complain to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated complaints,
     * or with status 400 (Bad Request) if the complaints is not valid,
     * or with status 500 (Internal Server Error) if the city couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/complaints")
    @Timed
    public ResponseEntity<Complaints> updateComplaints(@RequestBody Complaints complaints) throws URISyntaxException {
        log.debug("REST request to update City : {}", complaints);
        if (complaints.getId() == null) {
            return createComplain(complaints);
        }
        Complaints result = complaintsService.save(complaints);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, complaints.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cities : get all the cities.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of cities in body
     */
//    @GetMapping("/cities")
//    @Timed
//    public ResponseEntity<List<City>> getAllCities(Pageable pageable) {
//        log.debug("REST request to get a page of Cities");
//        Page<City> page = cityService.findAll(pageable);
//        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cities");
//        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
//    }

    /**
     * GET  /cities/:id : get the "id" city.
     *
     * @param id the id of the city to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the city, or with status 404 (Not Found)
     */
    @GetMapping("/complaints/{id}")
    @Timed
    public ResponseEntity<Complaints> getComplaints(@PathVariable Long id) {
        log.debug("REST request to get City : {}", id);
        Complaints complaints = complaintsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(complaints));
    }

    /**
     * DELETE  /cities/:id : delete the "id" city.
     *
     * @param id the id of the city to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/complaints/{id}")
    @Timed
    public ResponseEntity<Void> deleteComplaints(@PathVariable Long id) {
        log.debug("REST request to delete complaints : {}", id);
        complaintsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/complaints")
    @ResponseBody
    public ResponseEntity<List<Complaints>> findAllByRsql(@RequestParam(value = "search", required = false) String search, Pageable pageable) {

        if (search == null) {
            Page<Complaints> page = complaintsService.findAll(pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/complaints");
            return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
        } else {
            RSQLVisitor<CriteriaQuery<Complaints>, EntityManager> visitor = new JpaCriteriaQueryVisitor<Complaints>();
            final Node rootNode = new RSQLParser().parse(search);
            CriteriaQuery<Complaints> query = rootNode.accept(visitor, entityManager);
            List<Complaints> complaints = complaintsService.findAll(query);


            return new ResponseEntity<>(complaints, HttpStatus.OK);
        }
    }
}
