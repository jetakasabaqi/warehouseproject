package com.project.web.rest;


import com.codahale.metrics.annotation.Timed;
import com.project.domain.Vendor;
import com.project.domain.WeightUnit;
import com.project.rsql1.jpa.JpaCriteriaQueryVisitor;
import com.project.service.VendorService;
import com.project.service.WeightUnitService;
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

@RestController
@RequestMapping("/api")
public class WeightUnitResource {
    private final Logger log = LoggerFactory.getLogger(VendorResource.class);

    private static final String ENTITY_NAME = "weight_unit";

    private final WeightUnitService weightUnitService;

    private final EntityManager entityManager;

    public WeightUnitResource(WeightUnitService weightUnitService, EntityManager entityManager) {
        this.weightUnitService = weightUnitService;
        this.entityManager = entityManager;
    }

    /**
     * POST  /vendors : Create a new weightUnit.
     *
     * @param weightUnit the weightUnit to create
     * @return the ResponseEntity with status 201 (Created) and with body the new weightUnit, or with status 400 (Bad Request) if the weightUnit has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/weight-unit")
    @Timed
    public ResponseEntity<WeightUnit> createWeightUnit(@RequestBody WeightUnit weightUnit) throws URISyntaxException {
        log.debug("REST request to save weightUnit : {}", weightUnit);
        if (weightUnit.getId() != null) {
            throw new BadRequestAlertException("A new weightUnit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WeightUnit result = weightUnitService.save(weightUnit);
        return ResponseEntity.created(new URI("/api/weightUnit/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /weightUnit : Updates an existing weightUnit.
     *
     * @param weightUnit the weightUnit to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated weightUnit,
     * or with status 400 (Bad Request) if the weightUnit is not valid,
     * or with status 500 (Internal Server Error) if the weightUnit couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/weightUnit")
    @Timed
    public ResponseEntity<WeightUnit> updateWeightUnit(@RequestBody WeightUnit weightUnit) throws URISyntaxException {
        log.debug("REST request to update weightUnit : {}", weightUnit);
        if (weightUnit.getId() == null) {
            return createWeightUnit(weightUnit);
        }
        WeightUnit result = weightUnitService.save(weightUnit);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, weightUnit.getId().toString()))
            .body(result);
    }

    /**
     * GET  /weightUnit : get all the weightUnit.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of weightUnit in body
     */
//    @GetMapping("/vendors")
//    @Timed
//    public ResponseEntity<List<Vendor>> getAllVendors(Pageable pageable) {
//        log.debug("REST request to get a page of Vendors");
//        Page<Vendor> page = vendorService.findAll(pageable);
//        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/vendors");
//        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
//    }

    /**
     * GET  /weightUnit/:id : get the "id" weightUnit.
     *
     * @param id the id of the weightUnit to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the weightUnit, or with status 404 (Not Found)
     */
    @GetMapping("/weight_unit/{id}")
    @Timed
    public ResponseEntity<WeightUnit> getWeightUnit(@PathVariable Long id) {
        log.debug("REST request to get weightUnit : {}", id);
        WeightUnit weightUnit = weightUnitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(weightUnit));
    }

    /**
     * DELETE  /weightUnit/:id : delete the "id" weightUnit.
     *
     * @param id the id of the weightUnit to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/weight_unit/{id}")
    @Timed
    public ResponseEntity<Void> deleteWeightUnit(@PathVariable Long id) {
        log.debug("REST request to delete weightUnit : {}", id);
        weightUnitService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/weight_unit")
    @ResponseBody
    public ResponseEntity<List<WeightUnit>> findAllByRsql(@RequestParam(value = "search",required = false) String search, Pageable pageable) {


        if (search == null) {
            Page<WeightUnit> page = weightUnitService.findAll(pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/weight_unit");
            return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
        } else {
            RSQLVisitor<CriteriaQuery<WeightUnit>, EntityManager> visitor = new JpaCriteriaQueryVisitor<WeightUnit>();
            final Node rootNode = new RSQLParser().parse(search);
            CriteriaQuery<WeightUnit> query = rootNode.accept(visitor, entityManager);
            List<WeightUnit> weightUnits = weightUnitService.findAll(query);


            return new ResponseEntity<>(weightUnits, HttpStatus.OK);
        }
    }
}
