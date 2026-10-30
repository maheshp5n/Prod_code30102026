package com.pratikshat.exicom.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.pratikshat.exicom.entity.BtCaseExpectedProblems;


public interface BtCaseExpectedProblemsRepository extends JpaRepository<BtCaseExpectedProblems, Long>{
	
	
	List<BtCaseExpectedProblems> findByBtProblemMaster_Id(int problemId);
	
	BtCaseExpectedProblems findByBtProblemMaster_IdAndProblemName(int problemId, String problemName);
	

}
