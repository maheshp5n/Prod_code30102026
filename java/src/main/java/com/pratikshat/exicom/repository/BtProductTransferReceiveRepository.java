package com.pratikshat.exicom.repository;

import com.pratikshat.exicom.entity.BtProductTransferReceive;
import com.pratikshat.exicom.repository.custom.BtProductTransferReceiveRepositoryCustom;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BtProductTransferReceiveRepository extends JpaRepository<BtProductTransferReceive,Long>,BtProductTransferReceiveRepositoryCustom{
    
}
