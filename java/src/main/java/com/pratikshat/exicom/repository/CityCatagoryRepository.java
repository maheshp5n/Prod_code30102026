package com.pratikshat.exicom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityCatagoryRepository extends JpaRepository<com.pratikshat.exicom.entity.CityCatagory, String> {
	List<com.pratikshat.exicom.entity.CityCatagory> findAll();
}
