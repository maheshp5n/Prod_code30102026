package com.pratikshat.exicom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.BatterySiteType;

@Repository
public interface BatterySiteTypeRepository extends JpaRepository<BatterySiteType, Integer> {

	List<BatterySiteType> findAll();
}
