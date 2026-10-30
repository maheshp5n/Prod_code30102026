package com.pratikshat.exicom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.IGSTBillingState;

@Repository
public interface IGSTBillingStateRepository extends JpaRepository<IGSTBillingState, Long> {

}
