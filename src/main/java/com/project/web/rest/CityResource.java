package com.project.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.project.domain.City;
import com.project.rsql1.jpa.JpaCriteriaQueryVisitor;
import com.project.service.CityService;
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
public class CityResource {

    private final Logger log = LoggerFactory.getLogger(CityResource.class);

    private static final String ENTITY_NAME = "city";

    private final CityService cityService;

    private final EntityManager entityManager;

    public CityResource(CityService cityService, EntityManager entityManager) {
        this.cityService = cityService;
        this.entityManager = entityManager;
    }

    /**
     * POST  /cities : Create a new city.
     *
     * @param city the city to create
     * @return the ResponseEntity with status 201 (Created) and with body the new city, or with status 400 (Bad Request) if the city has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cities")
    @Timed
    public ResponseEntity<City> createCity(@RequestBody City city) throws URISyntaxException {
        log.debug("REST request to save City : {}", city);
        if (city.getId() != null) {
            throw new BadRequestAlertException("A new city cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (city.getCityName() == null || city.getCityName() == "") {
            throw new BadRequestAlertException("cityName cannot be null", ENTITY_NAME, "null");
        }
        City result = cityService.save(city);
        return ResponseEntity.created(new URI("/api/cities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cities : Updates an existing city.
     *
     * @param city the city to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated city,
     * or with status 400 (Bad Request) if the city is not valid,
     * or with status 500 (Internal Server Error) if the city couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cities")
    @Timed
    public ResponseEntity<City> updateCity(@RequestBody City city) throws URISyntaxException {
        log.debug("REST request to update City : {}", city);
        if (city.getCityName() == null || city.getCityName() == "") {
            throw new BadRequestAlertException("cityName cannot be null", ENTITY_NAME, "null");
        } else {
            if (city.getId() == null) {

                return createCity(city);


            }
            City result = cityService.save(city);
            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, city.getId().toString()))
                .body(result);
        }
    }


    /**
     * GET  /cities/:id : get the "id" city.
     *
     * @param id the id of the city to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the city, or with status 404 (Not Found)
     */
    @GetMapping("/cities/{id}")
    @Timed
    public ResponseEntity<City> getCity(@PathVariable Long id) throws Exception {

        City city = cityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(city));
    }


    /**
     * DELETE  /cities/:id : delete the "id" city.
     *
     * @param id the id of the city to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cities/{id}")
    @Timed
    public ResponseEntity<Void> deleteCity(@PathVariable Long id) throws Exception {
        log.debug("REST request to delete City : {}", id);

        cityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();


    }

    @RequestMapping(method = RequestMethod.GET, value = "/cities")
    @ResponseBody
    public ResponseEntity<List<City>> findAllByRsql(@RequestParam(value = "search", required = false) String search, Pageable pageable) {

        if (search == null) {
            Page<City> page = null;
            HttpHeaders headers = null;
            try {
                page = cityService.findAll(pageable);
                PaginationUtil.generatePaginationHttpHeaders(page, "/api/cities");
                return new ResponseEntity<>(page.getContent(), headers, HttpStatus.BAD_REQUEST);
            } catch (Exception e) {
                return new ResponseEntity<>(null, headers, HttpStatus.BAD_REQUEST);
            }


        } else {
            RSQLVisitor<CriteriaQuery<City>, EntityManager> visitor = new JpaCriteriaQueryVisitor<City>();
            final Node rootNode = new RSQLParser().parse(search);
            CriteriaQuery<City> query = rootNode.accept(visitor, entityManager);
            List<City> cities = null;
            try {
                cities = cityService.findAll(query);
            } catch (Exception e) {
                e.printStackTrace();
            }


            return new ResponseEntity<>(cities, HttpStatus.OK);
        }
    }
}
