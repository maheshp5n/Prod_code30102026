package com.pratikshat.exicom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.TimeList;

@Repository
public interface TimeListRepository extends JpaRepository<TimeList, String> {
	List<TimeList> findAll();
}
