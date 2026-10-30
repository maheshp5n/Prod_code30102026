package com.pratikshat.exicom.repository;

import com.pratikshat.exicom.entity.BtAssetsWarrantyDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface BtAssetsWarrantyDetailsRepository extends JpaRepository<BtAssetsWarrantyDetails,Long>,BtAssetsWarrantyDetailsRepositoryCustome{
	@Query("select warr from BtAssetsWarrantyDetails warr where warr.id=:id")
	Optional<BtAssetsWarrantyDetails> findById(@Param("id")Long id);
}
