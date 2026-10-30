package com.pratikshat.exicom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.LProductReturnOrderLineItem;

@Repository
public interface LProductReturnOrderLineItemRepository extends JpaRepository<LProductReturnOrderLineItem, Long> {

}
