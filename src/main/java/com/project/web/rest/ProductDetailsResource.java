package com.project.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.project.domain.ProductDetails;
import com.project.repository.PriceRepository;
import com.project.rsql1.jpa.JpaCriteriaQueryVisitor;
import com.project.service.*;
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
public class ProductDetailsResource {

    private final Logger log = LoggerFactory.getLogger(ProductDetailsResource.class);

    private static final String ENTITY_NAME = "product_details";

    private final ProductDetailsService productDetailsService;

    private final ProductTypeService productTypeService;

    private final PriceService priceService;

    private final WeightUnitService weightUnitService;

    private final ProductService productService;


    private final EntityManager entityManager;


    public ProductDetailsResource(ProductDetailsService productDetailsService, ProductTypeService productTypeService, PriceService priceService, WeightUnitService weightUnitService, ProductService productService, EntityManager entityManager) {
        this.productDetailsService = productDetailsService;
        this.entityManager = entityManager;
        this.productService = productService;
        this.weightUnitService = weightUnitService;
        this.priceService=priceService;
        this.productTypeService=productTypeService;
    }

    /**
     * POST  /productDetails : Create a new productDetails.
     *
     * @param productDetails the productDetails to create
     * @return the ResponseEntity with status 201 (Created) and with body the new productDetails, or with status 400 (Bad Request) if the productDetails has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/product_details")
    @Timed
    public ResponseEntity<ProductDetails> createProductDetails(@RequestBody ProductDetails productDetails) throws URISyntaxException {
        log.debug("REST request to save productDetails : {}", productDetails);
        if (productDetails.getId() != null) {
            throw new BadRequestAlertException("A new productDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductDetails result = productDetailsService.save(productDetails);
        return ResponseEntity.created(new URI("/api/product_details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /productDetails : Updates an existing productDetails.
     *
     * @param productDetails the productDetails to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated productDetails,
     * or with status 400 (Bad Request) if the productDetails is not valid,
     * or with status 500 (Internal Server Error) if the productDetails couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/product_details")
    @Timed
    public ResponseEntity<ProductDetails> updateProductDetails(@RequestBody ProductDetails productDetails) throws URISyntaxException {
        log.debug("REST request to update productDetails : {}", productDetails);
        if (productDetails.getId() == null) {
            return createProductDetails(productDetails);
        }
        ProductDetails result = productDetailsService.save(productDetails);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, productDetails.getId().toString()))
            .body(result);
    }

    /**
     * GET  /prices : get all the productDetails.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of productDetails in body
     */
//    @GetMapping("/prices")
//    @Timed
//    public ResponseEntity<List<Price>> getAllPrices(Pageable pageable) {
//        log.debug("REST request to get a page of Prices");
//        Page<Price> page = priceService.findAll(pageable);
//        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/prices");
//        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
//    }

    /**
     * GET  /productDetails/:id : get the "id" productDetails.
     *
     * @param id the id of the productDetails to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the productDetails, or with status 404 (Not Found)
     */
    @GetMapping("/product_details/{id}")
    @Timed
    public ResponseEntity<ProductDetails> getProductDetails(@PathVariable Long id) {
        log.debug("REST request to get Product Details : {}", id);
        ProductDetails productDetails = productDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(productDetails));
    }

    /**
     * DELETE  /productDetails/:id : delete the "id" productDetails.
     *
     * @param id the id of the productDetails to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/product_details/{id}")
    @Timed
    public ResponseEntity<Void> deletePrice(@PathVariable Long id) {
        log.debug("REST request to delete Product Details : {}", id);
        productDetailsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/product_details")
    @ResponseBody
    public ResponseEntity<List<ProductDetails>> findAllByRsql(@RequestParam(value = "search",required = false) String search, Pageable pageable) {


        if (search == null) {
            Page<ProductDetails> page = productDetailsService.findAll(pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/product_details");
            return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
        } else {
            RSQLVisitor<CriteriaQuery<ProductDetails>, EntityManager> visitor = new JpaCriteriaQueryVisitor<ProductDetails>();
            final Node rootNode = new RSQLParser().parse(search);
            CriteriaQuery<ProductDetails> query = rootNode.accept(visitor, entityManager);
            List<ProductDetails> productDetails = productDetailsService.findAll(query);


            return new ResponseEntity<>(productDetails, HttpStatus.OK);
        }
    }
}
