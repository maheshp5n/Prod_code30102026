package com.pratikshat.exicom.repository;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.ApplicationNotificationDetails;
@Repository
public interface ApplicationNotificationDetailsRepository extends JpaRepository<ApplicationNotificationDetails, Long>,ApplicationNotificationDetailsRepositoryCustom {
	Page<ApplicationNotificationDetails> findByUserEmailIdOrderByNofificationDateDesc(String userEmailid,Pageable page);
	ApplicationNotificationDetails findByUserEmailIdAndNotificationId(String userEmailId,long notificationId);
	@Modifying
	@Transactional
	@Query("update ApplicationNotificationDetails ap set ap.notificationRead=:read where ap.userEmailId=:userid")
	void updateAllRead(@Param("userid")String userid,@Param("read")boolean read);
}
