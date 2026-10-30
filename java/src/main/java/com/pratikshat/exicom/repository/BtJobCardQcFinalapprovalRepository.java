package com.pratikshat.exicom.repository;

import com.pratikshat.exicom.entity.BtJobCardQcFinalapproval;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BtJobCardQcFinalapprovalRepository extends JpaRepository<BtJobCardQcFinalapproval,Long>{
    
}
