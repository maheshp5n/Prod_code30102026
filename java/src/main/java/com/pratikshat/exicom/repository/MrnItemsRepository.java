package com.pratikshat.exicom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.MrnItems;
@Repository
public interface MrnItemsRepository extends JpaRepository<MrnItems, Long> {
	
}
