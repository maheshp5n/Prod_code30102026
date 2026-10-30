package com.pratikshat.exicom.repository;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.BtCase;

@Repository
public interface BtWccCustomerRepository extends JpaRepository<BtCase, Long>{

	Page<Map<String, Object>> getCasesByStage(@Param("stage")String stage, Pageable page);

}
