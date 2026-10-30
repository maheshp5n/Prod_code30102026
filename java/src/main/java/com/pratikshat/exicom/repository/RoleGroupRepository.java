package com.pratikshat.exicom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.RoleGroup;

@Repository
public interface RoleGroupRepository extends JpaRepository<RoleGroup, Integer> {
	
}
