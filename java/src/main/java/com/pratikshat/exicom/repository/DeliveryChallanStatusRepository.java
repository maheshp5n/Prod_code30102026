package com.pratikshat.exicom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.DeliveryChallanStatus;

@Repository
public interface DeliveryChallanStatusRepository extends JpaRepository<DeliveryChallanStatus, Long> {

	List<DeliveryChallanStatus> findAll();
}
