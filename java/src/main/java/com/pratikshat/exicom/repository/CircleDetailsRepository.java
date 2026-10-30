package com.pratikshat.exicom.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.entity.CircleDetails;
import com.pratikshat.exicom.entity.ZoneDetails;

@Repository
public interface CircleDetailsRepository extends JpaRepository<CircleDetails, String>, CircleDetailsRepositoryCustome {
	int countByCircleName(String circleName);

	CircleDetails findByCircleName(String circleName);

	@Transactional(readOnly = true)
	@Query("select new com.pratikshat.exicom.model.response.CircleDetailsResponse(c) from CircleDetails c left join c.serviceCenterBattery batLegal left join c.serviceCenterPower dctLegal where (batLegal.businessDoamin.businessDomainId=:businessDomainId or batLegal.businessDoamin.businessDomainId=3) or (dctLegal.businessDoamin.businessDomainId=:businessDomainId or dctLegal.businessDoamin.businessDomainId=3)")
	Page<com.pratikshat.exicom.model.response.CircleDetailsResponse> getAll(Pageable page, @Param("businessDomainId")int businessDomainId);

	@Transactional(readOnly = true)
	@Query("select new com.pratikshat.exicom.model.response.CircleDetailsResponse(c) from CircleDetails c where c.circleName=:circleName")
	com.pratikshat.exicom.model.response.CircleDetailsResponse getByCircleName(@Param("circleName") String circleName);

	@Transactional(readOnly = true)
	@Query("select new com.pratikshat.exicom.model.response.CircleDetailsForList(c.circleName) from CircleDetails c left join c.serviceCenterBattery batLegal left join c.serviceCenterPower dctLegal where batLegal.businessDoamin.businessDomainId=:businessDomainId or dctLegal.businessDoamin.businessDomainId=:businessDomainId")
	List<com.pratikshat.exicom.model.response.CircleDetailsForList> forList(@Param("businessDomainId")int businessDomainId);

	@Transactional(readOnly = true)
	@Query("select new com.pratikshat.exicom.model.response.CircleDetailsResponse(c) from CircleDetails c join CircleRecentVisit rc on c.circleName=rc.circleName where rc.visitorId=:username")
	Page<com.pratikshat.exicom.model.response.CircleDetailsResponse> getRecent(@Param("username") String username,
			Pageable page);

	@Transactional(readOnly = true)
	@Query("select new com.pratikshat.exicom.model.response.ZoneDetailsResponse(z) from CircleDetails c join c.zone z where c.circleName=:circleName")
	Page<com.pratikshat.exicom.model.response.ZoneDetailsResponse> getZoneByCircle(@Param("circleName") String circleName,
			Pageable page);
	
	
	@Query("select new com.pratikshat.exicom.model.response.CircleZoneForList(c.circleName,c.zone) from CircleDetails c")
	List<com.pratikshat.exicom.model.response.CircleZoneForList> getCircleZoneList();
	
	@Transactional(readOnly = true)
	@Query("select new com.pratikshat.exicom.model.response.CircleDetailsForList(c.circleName) from CircleDetails c where c.serviceCenterBattery.legalEntityId=:legalEntity or c.serviceCenterPower.legalEntityId=:legalEntity or c.serviceCenterEv.legalEntityId=:legalEntity")
	List<com.pratikshat.exicom.model.response.CircleDetailsForList> circleListByLegalEntity(@Param("legalEntity")long legalEntity);


	@Query("from CircleDetails c where c.serviceCenterBattery.legalEntityId=:legalEntity or c.serviceCenterPower.legalEntityId=:legalEntity or c.serviceCenterEv.legalEntityId=:legalEntity")
	List<CircleDetails> circleListByLegalEntitys(@Param("legalEntity")long legalEntity);
}
