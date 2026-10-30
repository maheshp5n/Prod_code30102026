package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.SSA;
import com.pratikshat.exicom.model.response.SSAForList;
import com.pratikshat.exicom.model.response.SSAResponse;

@Repository
public interface SSARepository extends JpaRepository<SSA,Long>,SSACustomRepository {

	@Query("select new com.pratikshat.exicom.model.response.SSAResponse(ssa) from SSA ssa left join ssa.circle cr left join ssa.createdBy cby left join ssa.modifiedBy mby left join ssa.owner onr where ssa.id=:id")
	SSAResponse getById(@Param("id")long id);
	
	@Query("select new com.pratikshat.exicom.model.response.SSAResponse(ssa) from SSA ssa left join ssa.circle cr left join ssa.createdBy cby left join ssa.modifiedBy mby left join ssa.owner onr")
	Page<SSAResponse> getAllPageable(Pageable page);
	
	@Query("select new com.pratikshat.exicom.model.response.SSAForList(ssa.id, ssa.name) from SSA ssa left join ssa.circle cr where cr.circleName=:circleName")
	List<SSAForList> getSAAForList(@Param("circleName")String circleName);
	
	@Query("select new com.pratikshat.exicom.model.response.SSAResponse(ssa) from SSA ssa left join ssa.circle cr left join ssa.createdBy cby left join ssa.modifiedBy mby left join ssa.owner onr where cr.circleName=:circleName")
	Page<SSAResponse> getSAAByCircleName(@Param("circleName")String circleName, Pageable page);
	
	@Query("select new com.pratikshat.exicom.model.response.SSAResponse(ssa) from SSA ssa left join ssa.circle cr left join ssa.createdBy cby left join ssa.modifiedBy mby left join ssa.owner onr where ssa.name=:name and cr.circleName=:circleName")
	SSAResponse getByNameAndCircle(@Param("name")String name, @Param("circleName")String circleName);
	
	@Query(value = "SELECT * FROM `ssa` sr WHERE sr.`name`=:nae",nativeQuery = true)
	SSA getSsaId(@Param("nae")String nae);
	
	@Query(value = "SELECT MIN(sr.id) AS id, sr.name AS `name`  FROM `ssa` sr WHERE sr.name=:nae",nativeQuery = true)
	Map<String, Object> getDistinctSsaId(@Param("nae")String nae);
}
