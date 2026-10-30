package com.pratikshat.exicom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.entity.StateWithQuotePdf;

@Repository
@Transactional
public interface StateWithQuotePdfRepository extends JpaRepository<StateWithQuotePdf, Long>{

	@Query("select sqp from StateWithQuotePdf sqp where sqp.state.stateId=:stateId")
	StateWithQuotePdf getByState(@Param("stateId")long stateId);
	@Transactional
	@Query("select sqp from StateWithQuotePdf sqp join sqp.quotePdfHeaderAndFooter qtf where qtf.id=:templateId")
	List<StateWithQuotePdf> getStatesByTemplateId(@Param("templateId")long templateId);
}
