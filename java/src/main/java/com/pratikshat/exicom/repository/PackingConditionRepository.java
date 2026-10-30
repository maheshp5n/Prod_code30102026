package com.pratikshat.exicom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.PackingCondition;

@Repository
public interface PackingConditionRepository extends JpaRepository<PackingCondition, Integer> {

}
