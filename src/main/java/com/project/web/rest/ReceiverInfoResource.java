package com.project.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.project.domain.ReceiverInfo;
import com.project.service.ReceiverInfoService;
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
 * REST controller for managing ReceiverInfo.
 */
@RestController
@RequestMapping("/api")
public class ReceiverInfoResource {

    private final Logger log = LoggerFactory.getLogger(ReceiverInfoResource.class);

    private static final String ENTITY_NAME = "receiverInfo";

    private final ReceiverInfoService receiverInfoService;

    public ReceiverInfoResource(ReceiverInfoService receiverInfoService) {
        this.receiverInfoService = receiverInfoService;
    }

    /**
     * POST  /receiver-infos : Create a new receiverInfo.
     *
     * @param receiverInfo the receiverInfo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new receiverInfo, or with status 400 (Bad Request) if the receiverInfo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/receiver-infos")
    @Timed
    public ResponseEntity<ReceiverInfo> createReceiverInfo(@RequestBody ReceiverInfo receiverInfo) throws URISyntaxException {
        log.debug("REST request to save ReceiverInfo : {}", receiverInfo);
        if (receiverInfo.getId() != null) {
            throw new BadRequestAlertException("A new receiverInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReceiverInfo result = receiverInfoService.save(receiverInfo);
        return ResponseEntity.created(new URI("/api/receiver-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /receiver-infos : Updates an existing receiverInfo.
     *
     * @param receiverInfo the receiverInfo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated receiverInfo,
     * or with status 400 (Bad Request) if the receiverInfo is not valid,
     * or with status 500 (Internal Server Error) if the receiverInfo couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/receiver-infos")
    @Timed
    public ResponseEntity<ReceiverInfo> updateReceiverInfo(@RequestBody ReceiverInfo receiverInfo) throws URISyntaxException {
        log.debug("REST request to update ReceiverInfo : {}", receiverInfo);
        if (receiverInfo.getId() == null) {
            return createReceiverInfo(receiverInfo);
        }
        ReceiverInfo result = receiverInfoService.save(receiverInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, receiverInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /receiver-infos : get all the receiverInfos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of receiverInfos in body
     */
    @GetMapping("/receiver-infos")
    @Timed
    public ResponseEntity<List<ReceiverInfo>> getAllReceiverInfos(Pageable pageable) {
        log.debug("REST request to get a page of ReceiverInfos");
        Page<ReceiverInfo> page = receiverInfoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/receiver-infos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /receiver-infos/:id : get the "id" receiverInfo.
     *
     * @param id the id of the receiverInfo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the receiverInfo, or with status 404 (Not Found)
     */
    @GetMapping("/receiver-infos/{id}")
    @Timed
    public ResponseEntity<ReceiverInfo> getReceiverInfo(@PathVariable Long id) {
        log.debug("REST request to get ReceiverInfo : {}", id);
        ReceiverInfo receiverInfo = receiverInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(receiverInfo));
    }

    /**
     * DELETE  /receiver-infos/:id : delete the "id" receiverInfo.
     *
     * @param id the id of the receiverInfo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/receiver-infos/{id}")
    @Timed
    public ResponseEntity<Void> deleteReceiverInfo(@PathVariable Long id) {
        log.debug("REST request to delete ReceiverInfo : {}", id);
        receiverInfoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
