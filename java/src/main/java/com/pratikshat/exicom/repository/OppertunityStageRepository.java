package com.pratikshat.exicom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.OppertunityStage;

@Repository
public interface OppertunityStageRepository extends JpaRepository<OppertunityStage, Long> {
	List<OppertunityStage> findAll();
}
