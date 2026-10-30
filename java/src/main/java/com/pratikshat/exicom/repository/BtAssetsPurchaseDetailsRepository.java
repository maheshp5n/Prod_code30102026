package com.pratikshat.exicom.repository;

import com.pratikshat.exicom.entity.BtAssetsPurchaseDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface BtAssetsPurchaseDetailsRepository extends JpaRepository<BtAssetsPurchaseDetails,Long>,BtAssetsPurchaseDetailsRepositoryCustome{
	@Query("select purchase from BtAssetsPurchaseDetails purchase where purchase.id=:id")
	Optional<BtAssetsPurchaseDetails> findById(@Param("id")Long id);

}
