package com.pratikshat.exicom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.ServiceReportStatus;

@Repository
public interface ServiceReportStatusRepository extends JpaRepository<ServiceReportStatus, Long> {

	List<ServiceReportStatus> findAll();
}
