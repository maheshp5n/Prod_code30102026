package com.pratikshat.exicom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.ServiceOrderItem;

@Repository
public interface ServiceOrderItemRepository extends JpaRepository<ServiceOrderItem, Long> {

}
