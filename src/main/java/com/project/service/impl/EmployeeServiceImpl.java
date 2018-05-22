package com.project.service.impl;

import com.project.domain.Employee;
import com.project.repository.EmployeeRepository;
import com.project.service.EmployeeService;
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
import java.util.List;


/**
 * Service Implementation for managing Employee.
 */
@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private final EmployeeRepository employeeRepository;

    @Autowired
    private EntityManager entityManager;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /**
     * Save a employee.
     *
     * @param employee the entity to save
     * @return the persisted entity
     */
    @Override
    public Employee save(Employee employee) {
        log.debug("Request to save Employee : {}", employee);
        return employeeRepository.save(employee);
    }

    /**
     * Get all the employees.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Employee> findAll(Pageable pageable) {
        log.debug("Request to get all Employees");
        return employeeRepository.findAll(pageable);
    }

    /**
     * Get one employee by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Employee findOne(Long id) {
        log.debug("Request to get Employee : {}", id);
        return employeeRepository.findOne(id);
    }

    /**
     * Delete the employee by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Employee : {}", id);
        employeeRepository.delete(id);
    }


    @Override
    public List<Employee> findAll(CriteriaQuery<Employee> query) {
        return ParseRsql.findAll(query, entityManager);
    }

}
