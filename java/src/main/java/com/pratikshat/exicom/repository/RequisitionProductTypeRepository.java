package com.pratikshat.exicom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.RequisitionProductType;

@Repository
public interface RequisitionProductTypeRepository extends JpaRepository<RequisitionProductType, Long> {

	List<RequisitionProductType> findAll();
}
