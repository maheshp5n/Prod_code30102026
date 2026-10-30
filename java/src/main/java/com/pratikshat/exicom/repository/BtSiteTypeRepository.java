package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.pratikshat.exicom.entity.BtSiteType;


@Repository
public interface BtSiteTypeRepository extends JpaRepository<BtSiteType, Integer> {
	
//	List<BtSiteType> findAll();
	
	@Query("select ct from BtSiteType ct where ct.id=:id")
	BtSiteType getBySitetypeWithId(@Param("id") long l);

	
	@Query(value = "SELECT  \r\n"
			+ "	st.`id` AS id,\r\n"
			+ "	st.`type_name` AS siteTypeName\r\n"
			+ "	FROM `bt_site_type` st " , nativeQuery = true)
	public List<Map<String, Object>> getBtSiteType();
}
