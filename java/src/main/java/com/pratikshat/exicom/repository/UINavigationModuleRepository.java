package com.pratikshat.exicom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface UINavigationModuleRepository extends JpaRepository<com.pratikshat.exicom.entity.UINavigationModules,Long> {
	
}
