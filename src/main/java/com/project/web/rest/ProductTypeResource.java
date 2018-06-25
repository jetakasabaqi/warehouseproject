package com.project.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.project.domain.ProductType;
import com.project.rsql1.jpa.JpaCriteriaQueryVisitor;
import com.project.service.ProductTypeService;
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
 * REST controller for managing Product Types.
 */
@RestController
@RequestMapping("/api")
public class ProductTypeResource {

    private final Logger log = LoggerFactory.getLogger(StatusResource.class);

    private static final String ENTITY_NAME = "status";

    private final ProductTypeService productTypeService;


    private final EntityManager entityManager;

    public ProductTypeResource(ProductTypeService productTypeServive, EntityManager entityManager) {
        this.productTypeService = productTypeServive;
        this.entityManager = entityManager;
    }

    /**
     * POST  /productType : Create a new productType.
     *
     * @param productType the productType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new productType, or with status 400 (Bad Request) if the productType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/product_type")
    @Timed
    public ResponseEntity<ProductType> createProductType(@RequestBody ProductType productType) throws URISyntaxException {
        log.debug("REST request to save productType : {}", productType);
        if (productType.getId() != null) {
            throw new BadRequestAlertException("A new productType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductType result = productTypeService.save(productType);
        return ResponseEntity.created(new URI("/api/product_type/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /productType : Updates an existing productType.
     *
     * @param productType the productType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated productType,
     * or with status 400 (Bad Request) if the productType is not valid,
     * or with status 500 (Internal Server Error) if the productType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/product-type")
    @Timed
    public ResponseEntity<ProductType> updateProductType(@RequestBody ProductType productType) throws URISyntaxException {
        log.debug("REST request to update productType : {}", productType);
        if (productType.getId() == null) {
            return createProductType(productType);
        }
        ProductType result = productTypeService.save(productType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, productType.getId().toString()))
            .body(result);
    }


    /**
     * GET  /productType/:id : get the "id" productType.
     *
     * @param id the id of the productType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the productType, or with status 404 (Not Found)
     */
    @GetMapping("/product-type/{id}")
    @Timed
    public ResponseEntity<ProductType> getProductType(@PathVariable Long id) throws Exception {
        log.debug("REST request to get productType : {}", id);
        ProductType productType = productTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(productType));
    }

    /**
     * DELETE  /productType/:id : delete the "id" productType.
     *
     * @param id the id of the productType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/product_type/{id}")
    @Timed
    public ResponseEntity<Void> deleteProductType(@PathVariable Long id) {
        log.debug("REST request to delete Status : {}", id);
        productTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/product_type")
    @ResponseBody
    public ResponseEntity<List<ProductType>> findAllByRsql(@RequestParam(value = "search",required = false) String search, Pageable pageable) {


        if (search == null) {
            Page<ProductType> page = productTypeService.findAll(pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/product_type");
            return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
        } else {
            RSQLVisitor<CriteriaQuery<ProductType>, EntityManager> visitor = new JpaCriteriaQueryVisitor<ProductType>();
            final Node rootNode = new RSQLParser().parse(search);
            CriteriaQuery<ProductType> query = rootNode.accept(visitor, entityManager);
            List<ProductType> productTypes = productTypeService.findAll(query);


            return new ResponseEntity<>(productTypes, HttpStatus.OK);
        }
    }
}
