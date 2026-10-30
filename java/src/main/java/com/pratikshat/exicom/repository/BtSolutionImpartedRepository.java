package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.BtSiteType;
import com.pratikshat.exicom.entity.BtSolutionImparted;

@Repository
public interface BtSolutionImpartedRepository extends JpaRepository<BtSolutionImparted, Integer>{
	
//	BtSolutionImparted findById(int id);
	
	@Query("select ct from BtSolutionImparted ct where ct.id=:id")
	BtSolutionImparted getByBtSolutionImpartedWithId(@Param("id") long l);

	@Query(value = "SELECT  \r\n"
			+ "	si.`id` AS id,\r\n"
			+ "	si.`name` AS solutionValue\r\n"
			+ "	FROM `bt_solution_imparted` si 	" ,nativeQuery = true)
	public List<Map<String, Object>> getBtSolutionImparted();
}
