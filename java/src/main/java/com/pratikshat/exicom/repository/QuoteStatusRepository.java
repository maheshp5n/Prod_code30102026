package com.pratikshat.exicom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.QuoteStatus;

@Repository
public interface QuoteStatusRepository extends JpaRepository<QuoteStatus, Integer>{

	@Query("select st from QuoteStatus st where st.id in :statuslist")
	List<QuoteStatus> getStatus(@Param("statuslist")List<Integer> statuslist);
	
	@Query(value="SELECT \r\n" + 
			"`name` AS `name` \r\n" + 
			"FROM `quote_status`\r\n" + 
			"ORDER BY `name` ASC",nativeQuery=true)
	java.util.List<String> getQuoteStatusList();
}
