package com.pratikshat.exicom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.PlantDetails;
@Repository
public interface PlantRepository extends JpaRepository<PlantDetails, String> {
	java.util.List<PlantDetails> findAll();
}
