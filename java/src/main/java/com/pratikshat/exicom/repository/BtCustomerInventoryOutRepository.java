package com.pratikshat.exicom.repository;

import com.pratikshat.exicom.entity.BtCustomerInventoryOut;
import com.pratikshat.exicom.repository.custom.BtCustomerInventoryOutRepositoryCustom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BtCustomerInventoryOutRepository extends JpaRepository<BtCustomerInventoryOut,Long>,BtCustomerInventoryOutRepositoryCustom {
    
}
