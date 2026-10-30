package com.pratikshat.exicom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.RequisitionStatus;

@Repository
public interface RequisitionStatusRepository extends JpaRepository<RequisitionStatus, Long> {

	List<RequisitionStatus> findAll();
}
