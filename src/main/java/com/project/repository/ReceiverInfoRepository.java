package com.project.repository;

import com.project.domain.ReceiverInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the ReceiverInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReceiverInfoRepository extends JpaRepository<ReceiverInfo, Long>, JpaSpecificationExecutor {

}
