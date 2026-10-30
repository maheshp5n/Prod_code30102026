package com.pratikshat.exicom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.MrnLineItems;

@Repository
public interface MrnLineItemsRepository extends JpaRepository<MrnLineItems, Long> {
	
}
