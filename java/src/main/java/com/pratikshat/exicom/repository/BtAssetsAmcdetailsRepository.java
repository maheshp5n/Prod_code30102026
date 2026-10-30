package com.pratikshat.exicom.repository;

import com.pratikshat.exicom.entity.BtAssetsAmcdetails;

import org.springframework.stereotype.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface BtAssetsAmcdetailsRepository extends JpaRepository<BtAssetsAmcdetails,Long>,BtAssetsAmcdetailsRepositoryCustome{
	@Query("select amc from BtAssetsAmcdetails amc where amc.id=:id")
	Optional<BtAssetsAmcdetails> findById(@Param("id")Long id);

}
