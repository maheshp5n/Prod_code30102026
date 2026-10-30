package com.pratikshat.exicom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.AnnexureGLCode;

@Repository
public interface AnnexureGLCodeRepository extends JpaRepository<AnnexureGLCode, Integer>{

	List<AnnexureGLCode> findAll();
}
