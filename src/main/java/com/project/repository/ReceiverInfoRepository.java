package com.project.repository;

import com.project.domain.ReceiverInfo;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ReceiverInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReceiverInfoRepository extends JpaRepository<ReceiverInfo, Long> {

}
