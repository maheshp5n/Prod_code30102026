package com.pratikshat.exicom.repository;

import com.pratikshat.exicom.entity.BtProductReturnOrderItemReceive;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BtProductReturnOrderItemReceiveRepository extends JpaRepository<BtProductReturnOrderItemReceive,Long>{
    
}
