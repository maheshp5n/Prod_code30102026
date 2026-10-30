package com.pratikshat.exicom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.OppertunityType;

@Repository
public interface OppertunityTypeRepository extends JpaRepository<OppertunityType, Long> {
	List<OppertunityType> findAll();
}
