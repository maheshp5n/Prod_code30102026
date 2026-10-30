package com.pratikshat.exicom.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.entity.LegalEntity;
import com.pratikshat.exicom.model.request.LegalEntityResponse;
@Repository
@Transactional(readOnly =true)
public interface LegalEntityRepository extends JpaRepository<LegalEntity, Long>, LegalEntityCustomeRepository {
	int countByLegalEntityId(long legalEntityId);
	LegalEntity findByLegalEntityId(long legalEntityId);
	int countByLegalEntityName(String legalentityName);
	LegalEntity findByLegalEntityName(String legalEntityName);
	List<LegalEntity> findAll();
	@Query("select new com.pratikshat.exicom.model.request.LegalEntityResponse(l) from LegalEntity l left join l.createdBy crd left join l.modifiedBy mdf left join l.legalEntityType where l.businessDoamin.businessDomainId=:businessDomainId or l.businessDoamin.businessDomainId=3")
	Page<LegalEntityResponse> getAll(Pageable page, @Param("businessDomainId")int businessDomainId);
	@Query("select new com.pratikshat.exicom.model.request.LegalEntityResponse(l) from LegalEntity l  left join l.createdBy crd left join l.modifiedBy mdf left join l.legalEntityType where l.accountOwner.emailId=:ownerId")
	List<LegalEntityResponse> getByOwnerId(@Param("ownerId")String ownerId);
	@Query("select new com.pratikshat.exicom.model.request.LegalEntityResponse(l) from LegalEntity l  left join l.createdBy crd left join l.modifiedBy mdf left join l.legalEntityType join LegalEntityReceintVisit rs on l.legalEntityId=rs.legalEntityId where rs.visitorId=:ownerId and (l.businessDoamin.businessDomainId=:businessDomainId or l.businessDoamin.businessDomainId=3)")
	Page<LegalEntityResponse> getReceiveVisitByOwnerId(@Param("ownerId")String Id,Pageable page, @Param("businessDomainId")int businessDomainId);
	@Query("select new com.pratikshat.exicom.model.request.LegalEntityResponse(l) from LegalEntity l  left join l.createdBy crd left join l.modifiedBy mdf left join l.legalEntityType where l.legalEntityId=:id")
	LegalEntityResponse getByLegalEntityId(@Param("id")long id);
	@Query("select new com.pratikshat.exicom.model.response.ServiceCenterForList(l.legalEntityId,l.legalEntityName) from LegalEntity l where l.businessDoamin.businessDomainId=:businessDomainId or l.businessDoamin.businessDomainId=3")
	List<com.pratikshat.exicom.model.response.ServiceCenterForList> getListForServiceCenter(@Param("businessDomainId")int businessDomainId);
	@Query("select new com.pratikshat.exicom.model.response.ServiceCenterForList(l.legalEntityId,l.legalEntityName) from LegalEntity l where l.legalEntityType=:legalEntityType and (l.businessDoamin.businessDomainId=:businessDomainId or l.businessDoamin.businessDomainId=3) ")
	List<com.pratikshat.exicom.model.response.ServiceCenterForList> getListForServiceCenterByType(@Param("legalEntityType")String type, @Param("businessDomainId")int businessDomainId);
	@Query("select new com.pratikshat.exicom.model.response.LocationResponse(lc) from Location lc join lc.legalEntity lgl where lgl.legalEntityId=:legalEntityId")
	Page<com.pratikshat.exicom.model.response.LocationResponse> getLocationFromLegalEntityId(@Param("legalEntityId")long legalIntityId,Pageable page);
	@Query("select new com.pratikshat.exicom.model.request.Contacts(ct) from ServiceRecource sr join sr.location lc join lc.legalEntity lgl join sr.user usr join ContactDetails ct on ct.emailId=usr.emailId where lgl.legalEntityId=:legalEntityId")
	Page<com.pratikshat.exicom.model.request.Contacts> getEngByLegalEntity(@Param("legalEntityId")long legalEntityId,Pageable page);
	
	@Query("select new com.pratikshat.exicom.model.request.LegalEntityResponse(l) from LegalEntity l  left join l.createdBy crd left join l.modifiedBy mdf left join l.legalEntityType where l.legalEntityName=:legalEntityName")
	LegalEntityResponse getBylegalEntityName(@Param("legalEntityName")String legalEntityName);
}
