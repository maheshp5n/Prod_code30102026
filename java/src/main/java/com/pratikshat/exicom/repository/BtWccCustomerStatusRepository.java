package com.pratikshat.exicom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pratikshat.exicom.entity.BtWccCustomStatus;

public interface BtWccCustomerStatusRepository extends JpaRepository<BtWccCustomStatus, Integer> {

	BtWccCustomStatus findByStatus(String string);

}
