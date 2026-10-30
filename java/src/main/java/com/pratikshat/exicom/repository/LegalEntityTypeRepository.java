package com.pratikshat.exicom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.LegalEntityType;

@Repository
public interface LegalEntityTypeRepository extends JpaRepository<LegalEntityType, Long> {

	List<LegalEntityType> findAll();
}
