package com.pratikshat.exicom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.CaseExpectedProblem;

@Repository
public interface CaseExpectedProblemRepository extends JpaRepository<CaseExpectedProblem, Long>{
	
	List<CaseExpectedProblem> findByBusinessDomain_BusinessDomainId(int businessDomainId);

}
