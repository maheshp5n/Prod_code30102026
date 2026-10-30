package com.pratikshat.exicom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pratikshat.exicom.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, String> {
	List<Department> findByActive(boolean active);
}
