package com.project.service.impl;

import com.project.domain.Product;
import com.project.service.ShipmentService;
import com.project.domain.Shipment;
import com.project.repository.ShipmentRepository;
import com.project.service.dto.PackageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;


/**
 * Service Implementation for managing Shipment.
 */
@Service
@Transactional
public class ShipmentServiceImpl implements ShipmentService {

    private final Logger log = LoggerFactory.getLogger(ShipmentServiceImpl.class);

    private final ShipmentRepository shipmentRepository;

    public ShipmentServiceImpl(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    /**
     * Save a shipment.
     *
     * @param shipment the entity to save
     * @return the persisted entity
     */
    @Override
    public Shipment save(Shipment shipment) {
        log.debug("Request to save Shipment : {}", shipment);
        return shipmentRepository.save(shipment);
    }

    /**
     * Get all the shipments.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Shipment> findAll(Pageable pageable) {
        log.debug("Request to get all Shipments");
        return shipmentRepository.findAll(pageable);
    }

    /**
     * Get one shipment by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Shipment findOne(Long id) {
        log.debug("Request to get Shipment : {}", id);
        return shipmentRepository.findOne(id);
    }

    /**
     * Delete the shipment by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Shipment : {}", id);
        shipmentRepository.delete(id);
    }

    @Override
    public boolean shipmentValidation(Shipment shipment) throws Exception {
        boolean ok=true;
        if(shipment.getSenderP().getId() == null && shipment.getSenderV().getId()== null){
            ok=false;
            throw new Exception("SenderPerson && SenderVendor  ID must not be null!"); }
        else if (shipment.getReceiver().getId()==null){
            ok=false;
            throw new Exception("Receiver ID must not be null!");}
        else if (shipment.getEmployee().getId()==null){
            ok=false;
            throw new Exception("Employee ID must not be null!");}
        else if (shipment.getStatus().getId()==null){
            ok=false;
            throw new Exception("Status ID must not be null!");}
        else if(shipment.getProduct().getId()==null){
            ok=false;
            throw new Exception("Product ID must not be null!");}
        else if(shipment.getLocation().getId()==null){
            ok=false;
            throw new Exception("WarehouseLocation ID must not be null!");}


        return ok;
    }

    @Override
    public List<PackageDTO> getAllShipmentsByClientId(Long person_id) {
        return shipmentRepository.findAllBySenderPId(person_id);
    }
}
