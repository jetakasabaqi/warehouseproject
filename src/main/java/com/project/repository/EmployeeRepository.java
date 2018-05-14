package com.project.repository;

import com.project.domain.Employee;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.Optional;


/**
 * Spring Data JPA repository for the Employee entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

//    Optional<Object> findOneByLogin(String s);
//
//    Optional<Object> findOneByEmailIgnoreCase(String email);
}
