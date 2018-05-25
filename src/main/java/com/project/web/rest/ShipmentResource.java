package com.project.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.project.domain.Shipment;
import com.project.rsql1.jpa.JpaCriteriaQueryVisitor;
import com.project.service.*;
import com.project.service.dto.PackageDTO;
import com.project.service.dto.PackageStatusDTO;
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
import org.springframework.data.repository.query.Param;
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
 * REST controller for managing Shipment.
 */
@RestController
@RequestMapping("/api")
public class ShipmentResource {

    private final Logger log = LoggerFactory.getLogger(ShipmentResource.class);

    private static final String ENTITY_NAME = "shipment";

    private final ShipmentService shipmentService;

    private final PersonService personService;

    private final ReceiverInfoService receiverInfo;

    private final VendorService vendorService;

    private final EmployeeService employeeService;

    private final StatusService statusService;


    private final  EntityManager entityManager;

    private final ProductService productService;

    private final WarehouseLocationService warehouseLocationService;

    public ShipmentResource(ShipmentService shipmentService, PersonService personService, ReceiverInfoService receiverInfo, VendorService vendorService, EmployeeService employeeService, StatusService statusService, EntityManager entityManager, ProductService productService, WarehouseLocationService warehouseLocationService) {
        this.shipmentService = shipmentService;
        this.personService = personService;
        this.receiverInfo = receiverInfo;
        this.vendorService = vendorService;
        this.employeeService = employeeService;
        this.statusService = statusService;
        this.entityManager = entityManager;
        this.productService = productService;
        this.warehouseLocationService = warehouseLocationService;
    }

    /**
     * POST  /shipments : Create a new shipment.
     *
     * @param shipment the shipment to create
     * @return the ResponseEntity with status 201 (Created) and with body the new shipment, or with status 400 (Bad Request) if the shipment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/shipments")
    @Timed
    public ResponseEntity<Shipment> createShipment(@RequestBody Shipment shipment) throws Exception {
        log.debug("REST request to save Shipment : {}", shipment);
        if (shipment.getId() != null) {
            throw new BadRequestAlertException("A new shipment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        boolean ok = shipmentService.shipmentValidation(shipment);
        if (ok) {
            Shipment result = shipmentService.save(shipment);
            return ResponseEntity.created(new URI("/api/shipments/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
        } else {
            return ResponseEntity.badRequest()
                .body(shipment);
        }
    }

    /**
     * PUT  /shipments : Updates an existing shipment.
     *
     * @param shipment the shipment to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated shipment,
     * or with status 400 (Bad Request) if the shipment is not valid,
     * or with status 500 (Internal Server Error) if the shipment couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/shipments")
    @Timed
    public ResponseEntity<Shipment> updateShipment(@RequestBody Shipment shipment) throws Exception {
        log.debug("REST request to update Shipment : {}", shipment);
        if (shipment.getId() == null) {
            return createShipment(shipment);
        }
        Shipment result = shipmentService.save(shipment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, shipment.getId().toString()))
            .body(result);
    }

    /**
     * GET  /shipments : get all the shipments.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of shipments in body
     */
//    @GetMapping("/shipments")
//    @Timed
//    public ResponseEntity<List<Shipment>> getAllShipments(Pageable pageable) {
//        log.debug("REST request to get a page of Shipments");
//        Page<Shipment> page = shipmentService.findAll(pageable);
//        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/shipments");
//        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
//    }

    /**
     * GET  /shipments/:id : get the "id" shipment.
     *
     * @param id the id of the shipment to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the shipment, or with status 404 (Not Found)
     */
    @GetMapping("/shipments/{id}")
    @Timed
    public ResponseEntity<Shipment> getShipment(@PathVariable Long id) {
        log.debug("REST request to get Shipment : {}", id);
        Shipment shipment = shipmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(shipment));
    }

    /**
     * DELETE  /shipments/:id : delete the "id" shipment.
     *
     * @param id the id of the shipment to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/shipments/{id}")
    @Timed
    public ResponseEntity<Void> deleteShipment(@PathVariable Long id) {
        log.debug("REST request to delete Shipment : {}", id);
        shipmentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

//    @RequestMapping(method = RequestMethod.GET, value = "/shipment")
//    @ResponseBody
//    public List<Shipment> findAllByRsql(@RequestParam(value = "search") String search) {
//
//        Node rootNode = new RSQLParser().parse(search);
//        Specification<Shipment> spec = rootNode.accept(new CustomRsqlVisitor<>());
//
//        return shipmentService.findAll(spec);
//    }

    @RequestMapping(method = RequestMethod.GET, value = "/shipments")
    @ResponseBody
    public ResponseEntity<List<Shipment>> findAllByRsql(@RequestParam(value = "search",required = false) String search, Pageable pageable) {


        if (search == null) {
            Page<Shipment> page = shipmentService.findAll(pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/shipments");
            return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
        } else {
            RSQLVisitor<CriteriaQuery<Shipment>, EntityManager> visitor = new JpaCriteriaQueryVisitor<Shipment>();
            final Node rootNode = new RSQLParser().parse(search);
            CriteriaQuery<Shipment> query = rootNode.accept(visitor, entityManager);
            List<Shipment> shipments = shipmentService.findAll(query);


            return new ResponseEntity<>(shipments, HttpStatus.OK);
        }
    }


    @GetMapping("/shipment/{person_id}/packages")
    @Timed
    public ResponseEntity<List<PackageDTO>> getShipmentsByClientId(@PathVariable Long person_id) {
        log.debug("REST request to get Shipments by Clients :{}", person_id);
        List<PackageDTO> results = shipmentService.getAllShipmentsByClientId(person_id);


        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(results));
    }

    @GetMapping("/shipment/{person_id}/package-status")
    @Timed
    public ResponseEntity<PackageStatusDTO> getPackageStatusDetails(@PathVariable(value = "person_id") Long person_id,@RequestParam(value = "product.id") Long packageId )
    {
        PackageStatusDTO packageStatusDTO=shipmentService.getPackageStatusDetails(person_id,packageId);

        return  new ResponseEntity<>(packageStatusDTO,HttpStatus.OK);
    }
}
