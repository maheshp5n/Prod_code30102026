package com.pratikshat.exicom.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.HelpGuide;
@Repository
public interface HelpGuideRepository extends JpaRepository<HelpGuide, Long> {
	Page<HelpGuide> findByLanuguage(String langulage,Pageable page);
	Page<HelpGuide> findByPlatForm(String platForm,Pageable page);
	Page<HelpGuide> findByLanuguageAndPlatForm(String language,String platform,Pageable page);
}
