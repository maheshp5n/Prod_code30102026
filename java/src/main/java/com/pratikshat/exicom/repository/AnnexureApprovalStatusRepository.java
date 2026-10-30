package com.pratikshat.exicom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.AnnexureApprovalStatus;

@Repository
public interface AnnexureApprovalStatusRepository extends JpaRepository<AnnexureApprovalStatus, Long> {

	List<AnnexureApprovalStatus> findAll();
}
