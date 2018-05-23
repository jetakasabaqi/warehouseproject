package com.project.repository;

import com.project.domain.Person;
import com.project.domain.Product;
import com.project.domain.Shipment;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.math.BigDecimal;
import java.util.List;


/**
 * Spring Data JPA repository for the Shipment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {

    @Query(value = "select sh .product from Shipment sh inner join Product p on sh.product=p.id where senderp_id=:senderp_id")
    List<BigDecimal> findAllBySenderPId(@Param("senderp_id")Long senderp_id);

}
