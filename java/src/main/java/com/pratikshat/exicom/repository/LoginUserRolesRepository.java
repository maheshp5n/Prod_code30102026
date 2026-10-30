package com.pratikshat.exicom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.LoginUserRoles;
@Repository
public interface LoginUserRolesRepository extends JpaRepository<com.pratikshat.exicom.entity.LoginUserRoles, Long> {
	
	java.util.List<LoginUserRoles> findByLoginUser(LoginUser loginUser);
	
}
