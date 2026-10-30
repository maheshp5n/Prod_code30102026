package com.pratikshat.exicom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.ProductsRecentViews;
@Repository
public interface ProductsRecentVisitRepository extends JpaRepository<ProductsRecentViews, Long> {
	int countByVisitedPartCodeAndUserId(String partCode,String userId);
	ProductsRecentViews findByVisitedPartCodeAndUserId(String partCode,String userId);
}
