package com.pratikshat.exicom.repository;

import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.SaEngRating;

@Repository
public interface SaEngRatingRepository extends JpaRepository<SaEngRating, Long> {
	
	@Query(value="SELECT \r\n" + 
			"IFNULL(ROUND(AVG(engrating),2),0) AS `Avg Rating`,\r\n" + 
			"5 AS `Max Rating`\r\n" + 
			"FROM `sa_eng_rating` rat\r\n" + 
			"JOIN `service_appointment` sa ON sa.`service_appointment_id`=rat.`sa_id`\r\n" + 
			"WHERE sa.`owner_details_email_id`=:user ", nativeQuery=true)
	Map<String,Object> getRating(@Param("user")String user);
	
}
