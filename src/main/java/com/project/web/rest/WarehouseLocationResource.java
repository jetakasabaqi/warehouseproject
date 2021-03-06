package com.project.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.project.domain.WarehouseLocation;
import com.project.service.WarehouseLocationService;
import com.project.web.rest.errors.BadRequestAlertException;
import com.project.web.rest.util.HeaderUtil;
import com.project.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing WarehouseLocation.
 */
@RestController
@RequestMapping("/api")
public class WarehouseLocationResource {

    private final Logger log = LoggerFactory.getLogger(WarehouseLocationResource.class);

    private static final String ENTITY_NAME = "warehouseLocation";

    private final WarehouseLocationService warehouseLocationService;

    public WarehouseLocationResource(WarehouseLocationService warehouseLocationService) {
        this.warehouseLocationService = warehouseLocationService;
    }

    /**
     * POST  /warehouse-locations : Create a new warehouseLocation.
     *
     * @param warehouseLocation the warehouseLocation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new warehouseLocation, or with status 400 (Bad Request) if the warehouseLocation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/warehouse-locations")
    @Timed
    public ResponseEntity<WarehouseLocation> createWarehouseLocation(@RequestBody WarehouseLocation warehouseLocation) throws URISyntaxException {
        log.debug("REST request to save WarehouseLocation : {}", warehouseLocation);
        if (warehouseLocation.getId() != null) {
            throw new BadRequestAlertException("A new warehouseLocation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WarehouseLocation result = warehouseLocationService.save(warehouseLocation);
        return ResponseEntity.created(new URI("/api/warehouse-locations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /warehouse-locations : Updates an existing warehouseLocation.
     *
     * @param warehouseLocation the warehouseLocation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated warehouseLocation,
     * or with status 400 (Bad Request) if the warehouseLocation is not valid,
     * or with status 500 (Internal Server Error) if the warehouseLocation couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/warehouse-locations")
    @Timed
    public ResponseEntity<WarehouseLocation> updateWarehouseLocation(@RequestBody WarehouseLocation warehouseLocation) throws URISyntaxException {
        log.debug("REST request to update WarehouseLocation : {}", warehouseLocation);
        if (warehouseLocation.getId() == null) {
            return createWarehouseLocation(warehouseLocation);
        }
        WarehouseLocation result = warehouseLocationService.save(warehouseLocation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, warehouseLocation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /warehouse-locations : get all the warehouseLocations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of warehouseLocations in body
     */
    @GetMapping("/warehouse-locations")
    @Timed
    public ResponseEntity<List<WarehouseLocation>> getAllWarehouseLocations(Pageable pageable) {
        log.debug("REST request to get a page of WarehouseLocations");
        Page<WarehouseLocation> page = warehouseLocationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/warehouse-locations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /warehouse-locations/:id : get the "id" warehouseLocation.
     *
     * @param id the id of the warehouseLocation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the warehouseLocation, or with status 404 (Not Found)
     */
    @GetMapping("/warehouse-locations/{id}")
    @Timed
    public ResponseEntity<WarehouseLocation> getWarehouseLocation(@PathVariable Long id) {
        log.debug("REST request to get WarehouseLocation : {}", id);
        WarehouseLocation warehouseLocation = warehouseLocationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(warehouseLocation));
    }

    /**
     * DELETE  /warehouse-locations/:id : delete the "id" warehouseLocation.
     *
     * @param id the id of the warehouseLocation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/warehouse-locations/{id}")
    @Timed
    public ResponseEntity<Void> deleteWarehouseLocation(@PathVariable Long id) {
        log.debug("REST request to delete WarehouseLocation : {}", id);
        warehouseLocationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
