package com.project.repository;


import com.project.domain.Shipment;
import com.project.domain.Vendor;
import org.springframework.data.repository.query.Param;
import com.project.service.dto.PackageDTO;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


import java.util.List;




/**
 * Spring Data JPA repository for the Shipment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long>, JpaSpecificationExecutor {

    @Query("select sh.senderV from Shipment sh inner join Vendor v on sh.senderV.id =:id")
    Vendor jeta(@Param("id") Long id);

//    @Query("select new com.project.service.dto.PackageDTO(p.id, s.id, r.id, pr.id, s.statusName, r.fullName, r.address, r.zipCode)"+
//        "from Shipment sh " +
//        "inner join Receiver r on sh.receiver.id = r.id "+
//        "inner join Status s on sh.status.id = s.id  "+
//        "inner join Product p on sh.product.id = p.id " +
//        "inner join Price pr on p.price.id = pr.id "+
//        "where senderp_id =:senderp_id")
//    List<PackageDTO> getAllRecordsBySender(@Param("senderp_id") Long senderpId);

    @Query("select new com.project.service.dto.PackageDTO(p.id,s.id,r.id,pr.id,s.statusName,r.fullName,r.address,r.zipCode,pr.price)"+
    "from Shipment sh" +
    " inner join sh.status s "+
    " inner join sh.receiver r"+
    " inner join sh.product p" +
    " inner join p.price pr"+
    " where senderp_id=:senderp_id")
    List<PackageDTO> getAllRecordsBySender(@Param("senderp_id") Long senderpId);

}
