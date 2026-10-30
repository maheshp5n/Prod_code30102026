package com.pratikshat.exicom.repository;

import com.pratikshat.exicom.entity.BtJobCardQcPhysicalcheck;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BtJobCardQcPhysicalcheckRepository extends JpaRepository<BtJobCardQcPhysicalcheck,Long> {
    
}
