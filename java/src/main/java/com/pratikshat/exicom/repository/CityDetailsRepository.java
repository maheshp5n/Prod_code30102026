package com.pratikshat.exicom.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.entity.CityDetails;
@Repository
public interface CityDetailsRepository extends JpaRepository<CityDetails, String>,CityDetailsCustomRepository {
	int countByCityName(String cityName);
	@Query("select new com.pratikshat.exicom.model.response.CityDetailsResponse(city) from CityDetails city left join city.cityCatagory cat")
	Page<com.pratikshat.exicom.model.response.CityDetailsResponse> getAll(Pageable page);
	@Query("select new com.pratikshat.exicom.model.response.CityDetailsResponse(city) from CityDetails city left join city.cityCatagory cat where city.cityName=:cityName")
	com.pratikshat.exicom.model.response.CityDetailsResponse getByCityName(@Param("cityName")String cityName);
	@Modifying
	@Transactional
	@Query("update CityDetails city set city.cityCatagory.cityCategoryId=:cityCategoryId,city.cityName=:cityNameUpdated,city.modifiedDate=:modifiedDate,city.modifiedBy.emailId=:modifiedBy,city.synced=:synced where city.cityName=:cityName")
	void updateCityCatagory_cityCatagoryNameAndCityNameByCityName(@Param("cityCategoryId")int cityCategoryId,@Param("cityName")String cityName,@Param("modifiedDate")Date modifiedDate,@Param("modifiedBy")String modifiedBy,@Param("cityNameUpdated")String cityNameUpdated,@Param("synced")boolean synced);
	
	@Query("select new com.pratikshat.exicom.model.response.CityDetailsResponse(city) from CityDetails city left join city.cityCatagory cat left join CityRecentVisit rc on city.cityName=rc.cityName where rc.visitoryId=:visitoryId")
	Page<com.pratikshat.exicom.model.response.CityDetailsResponse> getRecentAll(@Param("visitoryId")String visitoryId,Pageable page);
	
	
	@Query("select new com.pratikshat.exicom.model.response.CityForListReponse(city.cityName) from CityDetails city")
	List<com.pratikshat.exicom.model.response.CityForListReponse> getCityForList();
}
