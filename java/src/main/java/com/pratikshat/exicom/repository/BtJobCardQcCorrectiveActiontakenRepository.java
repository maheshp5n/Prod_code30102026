package com.pratikshat.exicom.repository;

import com.pratikshat.exicom.entity.BtJobCardQcCorrectiveActiontaken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BtJobCardQcCorrectiveActiontakenRepository extends JpaRepository<BtJobCardQcCorrectiveActiontaken,Long>{
    
}
