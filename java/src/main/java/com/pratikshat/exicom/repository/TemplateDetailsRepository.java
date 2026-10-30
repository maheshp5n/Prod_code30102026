package com.pratikshat.exicom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.entity.TemplateDetails;
import com.pratikshat.exicom.entity.TemplateType;
import com.pratikshat.exicom.model.response.TemplateResponse;
@Repository
public interface TemplateDetailsRepository extends JpaRepository<com.pratikshat.exicom.entity.TemplateDetails, Long> {
	int countByTemplaeCode(String templatecode);
	@Transactional
	@Query("from TemplateDetails tc where tc.id=:id")
	TemplateDetails getByIds(Long id);
	
	@Query("from TemplateDetails tc where tc.templaeCode=:templateCode")
	TemplateDetails getByTemplaeCode(@Param("templateCode") String templaeCode);
	@Query("select new com.pratikshat.exicom.model.response.TemplateResponse(COALESCE(tc.id,0),COALESCE(tc.templaeCode,''),COALESCE(tc.templateType,''),COALESCE(tc.body,''),COALESCE(ct.emailId,''),COALESCE(ct.firstName,''),COALESCE(ct.lastName,''),COALESCE(ct.middleName,''),COALESCE(md.emailId,''),COALESCE(md.firstName,''),COALESCE(md.middleName,''),COALESCE(md.lastName,''),COALESCE(tc.createdDate),COALESCE(tc.lastModifiedDate),COALESCE(tc.active,false)) from TemplateDetails tc join tc.createdBy ct join tc.lastModifiedBy md where tc.id=:id")
	TemplateResponse getById(@Param("id")Long id);
	@Query("select new com.pratikshat.exicom.model.response.TemplateResponse(COALESCE(tc.id,0),COALESCE(tc.templaeCode,''),COALESCE(tc.templateType,''),COALESCE(tc.body,''),COALESCE(ct.emailId,''),COALESCE(ct.firstName,''),COALESCE(ct.lastName,''),COALESCE(ct.middleName,''),COALESCE(md.emailId,''),COALESCE(md.firstName,''),COALESCE(md.middleName,''),COALESCE(md.lastName,''),COALESCE(tc.createdDate),COALESCE(tc.lastModifiedDate),COALESCE(tc.active,false)) from TemplateDetails tc join tc.createdBy ct join tc.lastModifiedBy md")
	List<TemplateResponse> getAll();
	@Query("select new com.pratikshat.exicom.model.response.TemplateResponse(COALESCE(tc.id,0),COALESCE(tc.templaeCode,''),COALESCE(tc.templateType,''),COALESCE(tc.body,''),COALESCE(ct.emailId,''),COALESCE(ct.firstName,''),COALESCE(ct.lastName,''),COALESCE(ct.middleName,''),COALESCE(md.emailId,''),COALESCE(md.firstName,''),COALESCE(md.middleName,''),COALESCE(md.lastName,''),COALESCE(tc.createdDate),COALESCE(tc.lastModifiedDate),COALESCE(tc.active,false)) from TemplateDetails tc join tc.createdBy ct join tc.lastModifiedBy md where tc.templateType=:templatetype")
	List<TemplateResponse> getByTemplatetype(@Param("templatetype")TemplateType templateType);
	
	
	@Query("Select td from TemplateDetails td where td.active=true and td.templateType =:val and td.templaeCode=:Code")
	TemplateDetails getTemplateByTpeAndCode(Enum val,String Code);
}
