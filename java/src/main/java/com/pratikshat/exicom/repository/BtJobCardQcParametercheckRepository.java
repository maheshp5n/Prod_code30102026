package com.pratikshat.exicom.repository;

import com.pratikshat.exicom.entity.BtJobCardQcParametercheck;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BtJobCardQcParametercheckRepository extends JpaRepository<BtJobCardQcParametercheck,Long>{
    
}
