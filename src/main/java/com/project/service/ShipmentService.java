package com.project.service;

import com.lowagie.text.DocumentException;
import com.project.domain.Shipment;
import com.project.domain.Status;
import com.project.service.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.criteria.CriteriaQuery;
import java.io.IOException;
import java.util.List;

/**
 * Service Interface for managing Shipment.
 */
public interface ShipmentService {

    /**
     * Save a shipment.
     *
     * @param shipment the entity to save
     * @return the persisted entity
     */
    Shipment save(Shipment shipment);

    /**
     * Get all the shipments.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Shipment> findAll(Pageable pageable);

    /**
     * Get the "id" shipment.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Shipment findOne(Long id);

    /**
     * Delete the "id" shipment.
     *
     * @param id the id of the entity
     */
    void delete(Long id);


    /**
     * Get all the shipments by a filter
     *
     * @param query
     * @return the list of entities
     */
    List<Shipment> findAll(CriteriaQuery<Shipment> query);

    boolean shipmentValidation(Shipment shipment) throws Exception;

    /**
     * Get all the shipments by client id
     *
     * @param person_id the filter, the clients id
     * @return the list of entities
     */
    List<PackageDTO> getAllShipmentsByClientId(Long person_id);

    /**
     * Get one shipments by a filter
     *
     * @param query the filter, the clients id
     * @return the entity
     */
    PackageDTO findOnePackage(CriteriaQuery<PackageDTO> query);


    /**
     * Get one shipments by client id and product id
     *
     * @param productid,person_id
     * @return the entity
     */
    PackageDTO getShipmentsByClientIdAndProductID(Long productid, Long person_id);

    /**
     * Get one shipments status details
     *
     * @param package_id,person_id
     * @return the entity
     */
    PackageStatusDTO getPackageStatusDetails(Long person_id, Long package_id);

    /**
     * Get one shipments package info
     *
     * @param product_id,person_id
     * @return the entity
     */
    PackageInfoDTO getPackageInfo(Long person_id, Long product_id);

    /**
     * Get inbound packages
     *
     * @param pageable
     * @return pages of the entities
     */
    Page<InboundPackagesDTO> getInboundPackages(Pageable pageable);

    /**
     * Get outbound packages
     *
     * @param pageable
     * @return pages of the entities
     */

    Page<OutboundPackageDTO> getOutboundPackages(Pageable pageable);

    /**
     * Get the number of packages delivered
     *
     * @return entity
     */
    NoOfPacksDeliveredDTO getNoOfPacksDelivered();

    /**
     * Get all the packages delivered by any country
     *
     * @return list of the entities
     */
    List<NoOfPackByAnyCountryDTO> getNoOfPacksByAnyCountry();

    /**
     * Get number of pending packages
     *
     * @return list of the entities
     */
    List<NoOfPacksPendingDTO> getNoOfPacksPending();

    /**
     * Get number clients with 4 or more packages
     *
     * @return list of the entities
     */
    List<LoyalClientsDTO> getLoyalClients();

    /**
     * Get number of packages delivered by a country
     *
     * @return entity
     */
    NoOfPacksDeliveredDTO getNoOfPacksDeliveredByCountry(String country);

    List<NoOfPacksPendingDTO> getNoOfPacksPending(Pageable pageable);

    List<LoyalClientsDTO> getLoyalClients(Pageable pageable);

    List<NoOfPackByAnyCountryDTO> getNoOfPacksByAnyCountry(Pageable pageable);

    /**
     * send weekly report email
     *
     * @return true if email is sent, false if not
     */
    Boolean weeklyReport() throws IOException, DocumentException;

    Boolean changeStatus(Long id,Status status) throws IOException, DocumentException;
}
