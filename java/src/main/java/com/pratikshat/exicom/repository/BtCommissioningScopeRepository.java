package com.pratikshat.exicom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.CommissioningScope;

@Repository
public interface BtCommissioningScopeRepository extends JpaRepository<CommissioningScope, Integer>{
	
	CommissioningScope findById(int id);

}
