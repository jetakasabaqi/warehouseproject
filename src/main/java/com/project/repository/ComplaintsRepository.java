package com.project.repository;

import com.project.domain.City;
import com.project.domain.Complaints;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface ComplaintsRepository  extends JpaRepository<Complaints, Long>,JpaSpecificationExecutor {
}
