package com.pratikshat.exicom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserNavigationModulesRepository extends JpaRepository<com.pratikshat.exicom.entity.UserNavigationModule,Long> {

}
