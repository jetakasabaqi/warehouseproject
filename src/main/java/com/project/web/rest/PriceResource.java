package com.project.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.project.domain.Price;
import com.project.repository.PriceRepository;
import com.project.rsql1.jpa.JpaCriteriaQueryVisitor;
import com.project.service.PriceService;
import com.project.web.rest.errors.BadRequestAlertException;
import com.project.web.rest.util.HeaderUtil;
import com.project.web.rest.util.PaginationUtil;
import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import cz.jirutka.rsql.parser.ast.RSQLVisitor;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
 * REST controller for managing Price.
 */
@RestController
@RequestMapping("/api")
public class PriceResource {

    private final Logger log = LoggerFactory.getLogger(PriceResource.class);

    private static final String ENTITY_NAME = "price";

    private final PriceService priceService;


    private final EntityManager entityManager;
    @Autowired
    private PriceRepository priceRepository;

    public PriceResource(PriceService priceService, EntityManager entityManager) {
        this.priceService = priceService;
        this.entityManager = entityManager;
    }

    /**
     * POST  /prices : Create a new price.
     *
     * @param price the price to create
     * @return the ResponseEntity with status 201 (Created) and with body the new price, or with status 400 (Bad Request) if the price has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/prices")
    @Timed
    public ResponseEntity<Price> createPrice(@RequestBody Price price) throws URISyntaxException {
        log.debug("REST request to save Price : {}", price);
        if (price.getId() != null) {
            throw new BadRequestAlertException("A new price cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (price.getPrice()==null )
        {
            throw new BadRequestAlertException("Price cannot be null",ENTITY_NAME,"null");
        }
        Price result = priceService.save(price);
        return ResponseEntity.created(new URI("/api/prices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /prices : Updates an existing price.
     *
     * @param price the price to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated price,
     * or with status 400 (Bad Request) if the price is not valid,
     * or with status 500 (Internal Server Error) if the price couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/prices")
    @Timed
    public ResponseEntity<Price> updatePrice(@RequestBody Price price) throws URISyntaxException {
        log.debug("REST request to update Price : {}", price);
        if(price.getPrice()==null)
        {
            throw new BadRequestAlertException("Price cannot be null",ENTITY_NAME,"null");
        }
        else{
        if (price.getId() == null) {
            return createPrice(price);
        }
        Price result = priceService.save(price);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, price.getId().toString()))
            .body(result);
    }}



    /**
     * GET  /prices/:id : get the "id" price.
     *
     * @param id the id of the price to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the price, or with status 404 (Not Found)
     */
    @GetMapping("/prices/{id}")
    @Timed
    public ResponseEntity<Price> getPrice(@PathVariable Long id) throws Exception {
        log.debug("REST request to get Price : {}", id);
        Price price = priceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(price));
    }

    /**
     * DELETE  /prices/:id : delete the "id" price.
     *
     * @param id the id of the price to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/prices/{id}")
    @Timed
    public ResponseEntity<Void> deletePrice(@PathVariable Long id) throws Exception {
        log.debug("REST request to delete Price : {}", id);
        priceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/prices")
    @ResponseBody
    public ResponseEntity<List<Price>> findAllByRsql(@RequestParam(value = "search", required = false) String search, Pageable pageable) {


        if (search == null) {
            Page<Price> page = priceService.findAll(pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/prices");
            return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
        } else {
            RSQLVisitor<CriteriaQuery<Price>, EntityManager> visitor = new JpaCriteriaQueryVisitor<Price>();
            final Node rootNode = new RSQLParser().parse(search);
            CriteriaQuery<Price> query = rootNode.accept(visitor, entityManager);
            List<Price> prices = priceService.findAll(query);


            return new ResponseEntity<>(prices, HttpStatus.OK);
        }
    }
}
