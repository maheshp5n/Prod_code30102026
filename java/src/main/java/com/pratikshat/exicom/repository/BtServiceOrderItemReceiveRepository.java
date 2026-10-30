package com.pratikshat.exicom.repository;


import java.util.List;
import java.util.Map;

import com.pratikshat.exicom.entity.BtServiceOrderItemReceive;
import com.pratikshat.exicom.repository.custom.BtServiceOrderItemReceiveRepositoryCustom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BtServiceOrderItemReceiveRepository extends JpaRepository<BtServiceOrderItemReceive,Long>,BtServiceOrderItemReceiveRepositoryCustom{
    
}
