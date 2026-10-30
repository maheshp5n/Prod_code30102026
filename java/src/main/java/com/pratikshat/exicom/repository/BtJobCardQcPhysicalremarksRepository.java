package com.pratikshat.exicom.repository;

import com.pratikshat.exicom.entity.BtJobCardQcPhysicalremarks;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BtJobCardQcPhysicalremarksRepository extends JpaRepository<BtJobCardQcPhysicalremarks,Long> {
    
}
