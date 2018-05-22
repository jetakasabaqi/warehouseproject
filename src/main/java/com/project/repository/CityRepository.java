package com.project.repository;

import com.project.domain.City;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;


/**
 * Spring Data JPA repository for the City entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CityRepository extends JpaRepository<City, Long> ,JpaSpecificationExecutor{


}
