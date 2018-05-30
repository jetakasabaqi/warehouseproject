package com.project.repository;


import com.project.domain.ProductDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface ProductDetailsRepository extends JpaRepository<ProductDetails, Long>,JpaSpecificationExecutor {
}
