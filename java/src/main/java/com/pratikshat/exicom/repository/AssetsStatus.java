package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetsStatus extends JpaRepository<com.pratikshat.exicom.entity.AssetsStatus,Integer> {
	
	
	@Query("select st from com.pratikshat.exicom.entity.AssetsStatus st where st.assetsStatusId in :statuslist")
	List<com.pratikshat.exicom.entity.AssetsStatus> getStatus(@Param("statuslist")List<Integer> statuslist);
	
	@Query("select status from AssetsStatus status where status.assetsStatusId=:id")
	 com.pratikshat.exicom.entity.AssetsStatus getWithId(int id); 
	

	@Query(value = "SELECT st.`assets_status_id` AS id, st.`assets_status_name` AS `name`\r\n"
			+ "\r\n"
			+ "FROM `assets_status` st",nativeQuery = true)
	List<Map<String, Object>> getallStatus();

}
