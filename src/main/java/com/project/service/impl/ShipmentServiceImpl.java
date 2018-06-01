package com.project.service.impl;

import com.project.service.ShipmentService;
import com.project.domain.Shipment;
import com.project.domain.Vendor;
import com.project.repository.ShipmentRepository;
import com.project.service.ShipmentService;
import com.project.service.dto.InboundPackagesDTO;
import com.project.service.dto.OutboundPackageDTO;
import com.project.service.dto.PackageInfoDTO;
import com.project.service.dto.PackageStatusDTO;
import com.project.service.util.ParseRsql;
import com.project.service.dto.PackageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;


/**
 * Service Implementation for managing Shipment.
 */
@Service
@Transactional
public class ShipmentServiceImpl implements ShipmentService {

    private final Logger log = LoggerFactory.getLogger(ShipmentServiceImpl.class);

    private final ShipmentRepository shipmentRepository;

    @Autowired
    private EntityManager entityManager;

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
        boolean ok = true;
        if (shipment.getSenderP().getId() == null && shipment.getSenderV().getId() == null) {
            ok = false;
            throw new Exception("SenderPerson && SenderVendor  ID must not be null!");
        } else if (shipment.getReceiver().getId() == null) {
            ok = false;
            throw new Exception("Receiver ID must not be null!");
        } else if (shipment.getDeliverEmployee().getId() == null) {
            ok = false;
            throw new Exception("Employee ID must not be null!");
        } else if (shipment.getStatus().getId() == null) {
            ok = false;
            throw new Exception("Status ID must not be null!");
        } else if (shipment.getProduct().getId() == null) {
            ok = false;
            throw new Exception("Product ID must not be null!");
        } else if (shipment.getLocation().getId() == null) {
            ok = false;
            throw new Exception("WarehouseLocation ID must not be null!");
        }


        return ok;
    }

    @Override
    public List<Shipment> findAll(Specification spec) {
        log.debug(("Request to get all shipments by " + spec));
        return shipmentRepository.findAll(spec);
    }

    @Override
    public Vendor jeta(Long id) {
        return shipmentRepository.jeta(id);
    }

    @Override
    public List<Shipment> findAll(CriteriaQuery<Shipment> query) {
        return ParseRsql.findAll(query,entityManager);
    }


    @Override
    public List<PackageDTO> getAllShipmentsByClientId(Long person_id) {
        return shipmentRepository.getAllRecordsBySender(person_id);
    }

    @Override
    public PackageDTO findOnePackage( CriteriaQuery<PackageDTO> query) {
        return (PackageDTO) ParseRsql.findAll(query,entityManager);
    }

    @Override
    public PackageDTO getShipmentsByClientIdAndProductID(Long productid, Long person_id) {
        return shipmentRepository.getAllByPersonAndProduct(productid,person_id);
    }



    @Override
    public PackageStatusDTO getPackageStatusDetails(Long person_id, Long packageId) {
        return shipmentRepository.getPackageDetails(person_id,packageId);
    }

    @Override
    public PackageInfoDTO getPackageInfo(Long person_id, Long product_id) {
        return shipmentRepository.getPackageInfo(person_id,product_id);
    }

    @Override
    public Page<InboundPackagesDTO> getInboundPackages(Pageable pageable) {
        return shipmentRepository.getInboundPackages(pageable);
    }

    @Override
    public Page<OutboundPackageDTO> getOutboundPackages(Pageable pageable) {
        return shipmentRepository.getOutboundPackages(pageable);
    }
}
