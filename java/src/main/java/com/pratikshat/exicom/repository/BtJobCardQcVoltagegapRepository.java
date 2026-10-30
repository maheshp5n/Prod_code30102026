package com.pratikshat.exicom.repository;


import com.pratikshat.exicom.entity.BtJobCardQcVoltagegap;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BtJobCardQcVoltagegapRepository extends JpaRepository<BtJobCardQcVoltagegap,Long>{
    
}
