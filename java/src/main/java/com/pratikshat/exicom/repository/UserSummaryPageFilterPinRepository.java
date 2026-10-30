package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.UserSummaryPageFilterPin;

@Repository
public interface UserSummaryPageFilterPinRepository extends JpaRepository<UserSummaryPageFilterPin, Long>,UserSummaryPageFilterPinRepositoryCustome {
	@Query(value="SELECT \r\n" + 
			"	upf.`id` AS userSummaryPageFilterId,\r\n" + 
			"	smmry.`fileter_name` AS filterName,\r\n" + 
			"	smmry.`filter_param` AS filterParam,\r\n" + 
			"	pin.`pind` AS pined\r\n" + 
			"	FROM `user_summary_page_filter` upf\r\n" + 
			"	JOIN `summary_page_filter` smmry ON upf.page_filter_id=smmry.id\r\n" + 
			"	JOIN `uinavigation_modules` mnv ON mnv.`ui_modules_id`=smmry.`navigation_module_ui_modules_id`\r\n" + 
			"	JOIN employee_details emp ON upf.`user_profile_id`=emp.`user_profile_id`  \r\n" + 
			"	LEFT JOIN `user_summary_page_filter_pin` pin ON pin.`user_email_id`=emp.`email_id` AND pin.`filter_id`=upf.`id`\r\n" + 
			"	WHERE emp.`email_id`=:emailId AND mnv.`url` =:url",nativeQuery=true)
	public List<Map<String,Object>> getSummaryPageFilter(@Param("emailId")String userEmailId,@Param("url")String pageurl);
}
