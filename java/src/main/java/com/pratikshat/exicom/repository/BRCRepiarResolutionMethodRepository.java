package com.pratikshat.exicom.repository;


import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.BRCRepiarResolutionMethod;

@Repository
public interface BRCRepiarResolutionMethodRepository extends JpaRepository<BRCRepiarResolutionMethod, Integer> {

}
