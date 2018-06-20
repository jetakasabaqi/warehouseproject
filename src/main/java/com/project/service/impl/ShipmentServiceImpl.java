package com.project.service.impl;

import com.lowagie.text.DocumentException;
import com.project.domain.Shipment;
import com.project.repository.ShipmentRepository;
import com.project.service.MailService;
import com.project.service.ShipmentService;
import com.project.service.dto.*;
import com.project.service.util.ParseRsql;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import java.io.IOException;
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

    @Autowired
    private MailService mailService;

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

    /**
     * Validate a shipment
     *
     * @param shipment the shipment to be validatet
     * @return true if everything is ok,false if not
     */
    @Override
    public boolean shipmentValidation(Shipment shipment) throws Exception {
        log.debug("Request to validate Shipment : {}", shipment.getId());
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


    /**
     * Get all the shipments by a filter
     *
     * @param query the filter
     * @return the list of entities
     */
    @Override
    public List<Shipment> findAll(CriteriaQuery<Shipment> query) {
        return ParseRsql.findAll(query, entityManager);
    }

    /**
     * Get all the shipments by client id
     *
     * @param person_id the filter, the clients id
     * @return the list of entities
     */
    @Override
    public List<PackageDTO> getAllShipmentsByClientId(Long person_id) {
        return shipmentRepository.getAllRecordsBySender(person_id);
    }

    /**
     * Get one shipments by a filter
     *
     * @param query the filter, the clients id
     * @return the entity
     */
    @Override
    public PackageDTO findOnePackage(CriteriaQuery<PackageDTO> query) {
        return (PackageDTO) ParseRsql.findAll(query, entityManager);
    }

    /**
     * Get one shipments by client id and product id
     *
     * @param productid,person_id
     * @return the entity
     */
    @Override
    public PackageDTO getShipmentsByClientIdAndProductID(Long productid, Long person_id) {
        return shipmentRepository.getAllByPersonAndProduct(productid, person_id);
    }

    /**
     * Get one shipments status details
     *
     * @param packageId,person_id
     * @return the entity
     */
    @Override
    public PackageStatusDTO getPackageStatusDetails(Long person_id, Long packageId) {
        return shipmentRepository.getPackageDetails(person_id, packageId);
    }

    /**
     * Get one shipments package info
     *
     * @param product_id,person_id
     * @return the entity
     */
    @Override
    public PackageInfoDTO getPackageInfo(Long person_id, Long product_id) {
        return shipmentRepository.getPackageInfo(person_id, product_id);
    }

    /**
     * Get inbound packages
     *
     * @param pageable
     * @return pages of the entities
     */
    @Override
    public Page<InboundPackagesDTO> getInboundPackages(Pageable pageable) {
        return shipmentRepository.getInboundPackages(pageable);
    }

    /**
     * Get outbound packages
     *
     * @param pageable
     * @return pages of the entities
     */
    @Override
    public Page<OutboundPackageDTO> getOutboundPackages(Pageable pageable) {
        return shipmentRepository.getOutboundPackages(pageable);
    }

    /**
     * Get the number of packages delivered
     *
     * @return entity
     */
    @Override
    public NoOfPacksDeliveredDTO getNoOfPacksDelivered() {
        return shipmentRepository.getNoOfPacksDelivered();
    }

    /**
     * Get all the packages delivered by any country
     *
     * @return list of the entities
     */
    @Override
    public List<NoOfPackByAnyCountryDTO> getNoOfPacksByAnyCountry() {
        return shipmentRepository.getNoOfPacksByAnyCountry();
    }

    /**
     * Get number of pending packages
     *
     * @return list of the entities
     */
    @Override
    public List<NoOfPacksPendingDTO> getNoOfPacksPending() {
        return shipmentRepository.getNoOfPacksPending();
    }

    /**
     * Get number clients with 4 or more packages
     *
     * @return list of the entities
     */
    @Override
    public List<LoyalClientsDTO> getLoyalClients() {
        return shipmentRepository.getLoyalClients();
    }

    /**
     * Get number of packages delivered by a country
     *
     * @return entity
     */
    @Override
    public NoOfPacksDeliveredDTO getNoOfPacksDeliveredByCountry(String country) {
        return shipmentRepository.getNoOfPacksDeliveredByCountry(country);
    }

    @Override
    public List<NoOfPacksPendingDTO> getNoOfPacksPending(Pageable pageable) {
        return shipmentRepository.getNoOfPacksPending(pageable);
    }

    @Override
    public List<LoyalClientsDTO> getLoyalClients(Pageable pageable) {
        return shipmentRepository.getLoyalClients(pageable);
    }

    @Override
    public List<NoOfPackByAnyCountryDTO> getNoOfPacksByAnyCountry(Pageable pageable) {
        return shipmentRepository.getNoOfPacksByAnyCountry(pageable);
    }

    /**
     * send weekly report email
     *
     * @return true if email is sent, false if not
     */
    @Override
    public Boolean weeklyReport() throws IOException, DocumentException {
        NoOfPacksDeliveredDTO delivered = getNoOfPacksDelivered();
        List<NoOfPackByAnyCountryDTO> country = getNoOfPacksByAnyCountry();
        List<NoOfPacksPendingDTO> pending = getNoOfPacksPending();
        List<LoyalClientsDTO> clients = getLoyalClients();

        mailService.sendEmailWeekly(delivered, country, pending, clients);

        return true;
    }
}
