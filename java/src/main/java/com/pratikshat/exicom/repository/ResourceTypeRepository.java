package com.pratikshat.exicom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.ResourceType;

@Repository
public interface ResourceTypeRepository extends JpaRepository<ResourceType, Integer> {
	
	List<ResourceType> findAll();
}
