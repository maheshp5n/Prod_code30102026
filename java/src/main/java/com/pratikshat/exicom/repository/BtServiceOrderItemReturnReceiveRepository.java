package com.pratikshat.exicom.repository;

import com.pratikshat.exicom.entity.BtServiceOrderItemReturnReceive;
import com.pratikshat.exicom.repository.custom.BtServiceOrderItemReturnReceiveCustom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BtServiceOrderItemReturnReceiveRepository extends JpaRepository<BtServiceOrderItemReturnReceive,Long>,BtServiceOrderItemReturnReceiveCustom {
    
}
