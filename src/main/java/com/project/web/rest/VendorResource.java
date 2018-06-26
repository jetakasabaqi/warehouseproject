package com.project.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.project.domain.Vendor;
import com.project.rsql1.jpa.JpaCriteriaQueryVisitor;
import com.project.service.VendorService;
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
 * REST controller for managing Vendor.
 */
@RestController
@RequestMapping("/api")
public class VendorResource {

    private final Logger log = LoggerFactory.getLogger(VendorResource.class);

    private static final String ENTITY_NAME = "vendor";

    private final VendorService vendorService;

    private final EntityManager entityManager;

    public VendorResource(VendorService vendorService, EntityManager entityManager) {
        this.vendorService = vendorService;
        this.entityManager = entityManager;
    }

    /**
     * POST  /vendors : Create a new vendor.
     *
     * @param vendor the vendor to create
     * @return the ResponseEntity with status 201 (Created) and with body the new vendor, or with status 400 (Bad Request) if the vendor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/vendors")
    @Timed
    public ResponseEntity<Vendor> createVendor(@RequestBody Vendor vendor) throws URISyntaxException {
        log.debug("REST request to save Vendor : {}", vendor);
        if (vendor.getId() != null) {
            throw new BadRequestAlertException("A new vendor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (vendor.getFirstName() == null || vendor.getLastName() == null || vendor.getContactPerson() == null || vendor.getAddress() == null) {
            throw new BadRequestAlertException("Fields cannot be nul", ENTITY_NAME, "null");
        }
        Vendor result = vendorService.save(vendor);
        return ResponseEntity.created(new URI("/api/vendors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /vendors : Updates an existing vendor.
     *
     * @param vendor the vendor to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated vendor,
     * or with status 400 (Bad Request) if the vendor is not valid,
     * or with status 500 (Internal Server Error) if the vendor couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/vendors")
    @Timed
    public ResponseEntity<Vendor> updateVendor(@RequestBody Vendor vendor) throws URISyntaxException {
        log.debug("REST request to update Vendor : {}", vendor);
        if (vendor.getFirstName() == null || vendor.getLastName() == null || vendor.getContactPerson() == null || vendor.getAddress() == null) {
            throw new BadRequestAlertException("Fields cannot be null", ENTITY_NAME, "null");
        } else {
            if (vendor.getId() == null) {
                return createVendor(vendor);
            }
            Vendor result = vendorService.save(vendor);
            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, vendor.getId().toString()))
                .body(result);
        }
    }


    /**
     * GET  /vendors/:id : get the "id" vendor.
     *
     * @param id the id of the vendor to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the vendor, or with status 404 (Not Found)
     */
    @GetMapping("/vendors/{id}")
    @Timed
    public ResponseEntity<Vendor> getVendor(@PathVariable Long id) throws Exception {
        log.debug("REST request to get Vendor : {}", id);
        Vendor vendor = vendorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(vendor));
    }

    /**
     * DELETE  /vendors/:id : delete the "id" vendor.
     *
     * @param id the id of the vendor to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/vendors/{id}")
    @Timed
    public ResponseEntity<Void> deleteVendor(@PathVariable Long id) throws Exception {
        log.debug("REST request to delete Vendor : {}", id);
        vendorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/vendors")
    @ResponseBody
    public ResponseEntity<List<Vendor>> findAllByRsql(@RequestParam(value = "search", required = false) String search, Pageable pageable) {


        if (search == null) {
            Page<Vendor> page = vendorService.findAll(pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/vendors");
            return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
        } else {
            RSQLVisitor<CriteriaQuery<Vendor>, EntityManager> visitor = new JpaCriteriaQueryVisitor<Vendor>();
            final Node rootNode = new RSQLParser().parse(search);
            CriteriaQuery<Vendor> query = rootNode.accept(visitor, entityManager);
            List<Vendor> vendors = vendorService.findAll(query);


            return new ResponseEntity<>(vendors, HttpStatus.OK);
        }
    }
}
