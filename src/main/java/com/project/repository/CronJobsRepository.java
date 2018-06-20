package com.project.repository;

import com.project.domain.CronJobs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CronJobsRepository extends JpaRepository<CronJobs, Long>, JpaSpecificationExecutor {


}
