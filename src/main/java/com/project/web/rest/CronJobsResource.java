package com.project.web.rest;


import com.codahale.metrics.annotation.Timed;
import com.project.domain.CronJobs;
import com.project.rsql1.jpa.JpaCriteriaQueryVisitor;
import com.project.service.CronJobsService;
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
public class CronJobsResource {

    private final Logger log = LoggerFactory.getLogger(CityResource.class);

    private static final String ENTITY_NAME = "cronJobs";

    private final CronJobsService cronJobsService;

    private final EntityManager entityManager;

    public CronJobsResource(CronJobsService cronJobsService, EntityManager entityManager) {
        this.cronJobsService = cronJobsService;
        this.entityManager = entityManager;
    }


    @PostMapping("/cronJobs")
    @Timed
    public ResponseEntity<CronJobs> createCronJobs(@RequestBody CronJobs cronJobs) throws URISyntaxException {
        log.debug("REST request to save a cron : {}", cronJobs);
        if (cronJobs.getId() != null) {
            throw new BadRequestAlertException("A new cron cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (cronJobs.getCron() == null) {
            throw new BadRequestAlertException("Cron cannot be null.", ENTITY_NAME, "null");
        }
        CronJobs result = cronJobsService.save(cronJobs);
        return ResponseEntity.created(new URI("/api/cronJobs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/cronJobs")
    @Timed
    public ResponseEntity<CronJobs> updateCronJobs(@RequestBody CronJobs cronJobs) throws URISyntaxException {
        log.debug("REST request to update Cron : {}", cronJobs);
        if (cronJobs.getCron() == null) {
            throw new BadRequestAlertException("Cron cannot be null", ENTITY_NAME, "null");
        } else {
            if (cronJobs.getId() == null) {


                return createCronJobs(cronJobs);
            }
            CronJobs result = cronJobsService.save(cronJobs);
            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cronJobs.getId().toString()))
                .body(result);
        }
    }

    @GetMapping("/cronJobs/{id}")
    @Timed
    public ResponseEntity<CronJobs> getCronJobs(@PathVariable Long id) throws Exception {
        log.debug("REST request to get Cron : {}", id);
        CronJobs cronJobs = cronJobsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(cronJobs));
    }

    @DeleteMapping("/cronJobs/{id}")
    @Timed
    public ResponseEntity<Void> deleteCronJobs(@PathVariable Long id) throws Exception {
        log.debug("REST request to delete CronJobs : {}", id);
        cronJobsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/cronJobs")
    @ResponseBody
    public ResponseEntity<List<CronJobs>> findAllByRsql(@RequestParam(value = "search", required = false) String search, Pageable pageable) {

        if (search == null) {
            Page<CronJobs> page = cronJobsService.findAll(pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cronJobs");
            return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
        } else {
            RSQLVisitor<CriteriaQuery<CronJobs>, EntityManager> visitor = new JpaCriteriaQueryVisitor<CronJobs>();
            final Node rootNode = new RSQLParser().parse(search);
            CriteriaQuery<CronJobs> query = rootNode.accept(visitor, entityManager);
            List<CronJobs> cronJobs = cronJobsService.findAll(query);


            return new ResponseEntity<>(cronJobs, HttpStatus.OK);
        }
    }

}
