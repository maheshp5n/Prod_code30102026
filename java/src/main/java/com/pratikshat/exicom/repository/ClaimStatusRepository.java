package com.pratikshat.exicom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.ClaimStatus;

@Repository
public interface ClaimStatusRepository extends JpaRepository<ClaimStatus, Long>{
	List<ClaimStatus> findAll();
}
