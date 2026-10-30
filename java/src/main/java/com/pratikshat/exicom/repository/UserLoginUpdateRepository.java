package com.pratikshat.exicom.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.UserLoginUpdates;
@Repository
public interface UserLoginUpdateRepository extends JpaRepository<com.pratikshat.exicom.entity.UserLoginUpdates, String> {
	UserLoginUpdates findByUserEmailid(String userEmailId);
	List<UserLoginUpdates> findByLastSessionId(String sessionId);
	@Modifying
	@Transactional
	@Query("update UserLoginUpdates up set up.active=:flg where up.lastSessionId=:sessionId")
	void updateActiveByLastSessionId(@Param("flg") boolean flg,@Param("sessionId")String sessionId);
}
