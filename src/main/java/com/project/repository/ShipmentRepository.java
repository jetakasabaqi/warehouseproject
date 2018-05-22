package com.project.repository;

import com.project.domain.Shipment;
import com.project.domain.Vendor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import sun.nio.cs.ext.SJIS;

import java.util.List;


/**
 * Spring Data JPA repository for the Shipment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long>, JpaSpecificationExecutor {

    @Query("select sh.senderV from Shipment sh inner join Vendor v on sh.senderV.id =:id")
    Vendor jeta(@Param("id") Long id);

}
