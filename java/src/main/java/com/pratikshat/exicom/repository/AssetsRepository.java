package com.pratikshat.exicom.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.Assets;
@Repository
public interface AssetsRepository extends JpaRepository<Assets, String>,AssetsCustomeRepository {
	int countBySerialNumber(String serialNumber);
	Assets findBySerialNumber(String serialNumber);
	@Query("select new com.pratikshat.exicom.model.response.AssetsResponse(ast) from Assets ast left join ast.product prd left join prd.subProductFamily sbp left join sbp.productFamily pf left join ast.account acc left join ast.plant plt left join ast.site site left join site.circle circle")
	Page<com.pratikshat.exicom.model.response.AssetsResponse> getAllAssets(Pageable page);
	@Query("select new com.pratikshat.exicom.model.response.AssetsResponse(ast) from Assets ast left join ast.product prd left join prd.subProductFamily sbp left join sbp.productFamily pf left join ast.account acc left join ast.plant plt left join ast.site site left join ast.createdBy left join ast.modifyedBy left join site.circle circle where ast.serialNumber=:serialNumber")
	com.pratikshat.exicom.model.response.AssetsResponse getAssetsBySerialNumber(@Param("serialNumber")String serialNumber);
	@Query("select new com.pratikshat.exicom.model.response.AssetsResponse(ast) from Assets ast left join ast.product prd left join prd.subProductFamily sbp left join sbp.productFamily pf left join ast.account acc left join ast.plant plt left join ast.site site join AssetsRecentVisit rc on ast.serialNumber=rc.assetsSerialId where rc.visitorId=:username")
	Page<com.pratikshat.exicom.model.response.AssetsResponse> getRecent(@Param("username")String username,Pageable page);
	@Query("select new com.pratikshat.exicom.model.response.AssetsForList(ast.serialNumber,prd.productPartCode,prd.pdoductDescription) from Assets ast left join ast.product prd")
	java.util.List<com.pratikshat.exicom.model.response.AssetsForList> getAssetsForList();
	@Query("select new com.pratikshat.exicom.model.response.AssetsForList(ast.serialNumber,prd.productPartCode,prd.pdoductDescription) from Assets ast left join ast.product prd where ast.status=:status")
	java.util.List<com.pratikshat.exicom.model.response.AssetsForList> getAssetsForList(@Param("status")com.pratikshat.exicom.entity.AssetsStatus assetsStatus);
	
	@Query("select ast.serialNumber from Assets ast join ast.site st join st.serviceCenter sc join sc.accountOwner onr where onr.emailId=:emailid")
	java.util.Set<String> getAssetsSerialNumberForFieldServiceEng(@Param("emailid")String emailid);
	
	@Query("select new com.pratikshat.exicom.model.response.AssetsResponse(ast) from Assets ast left join ast.product prd left join prd.subProductFamily sbp left join sbp.productFamily pf left join ast.account acc left join ast.plant plt left join ast.site site left join site.circle circle where ast.serialNumber in :serialnumber")
	Page<com.pratikshat.exicom.model.response.AssetsResponse> getAllAssets(@Param("serialnumber")java.util.Set<String>assetsSerialNumbers,Pageable page);
	
	@Query("select new com.pratikshat.exicom.model.response.AssetsForList(ast.serialNumber,prd.productPartCode,prd.pdoductDescription) from Assets ast left join ast.product prd left join ast.site st where st is null")
	java.util.List<com.pratikshat.exicom.model.response.AssetsForList> getAssetsForListForInstallation();
	
	@Query("select new com.pratikshat.exicom.model.response.AssetsForList(ast.serialNumber,prd.productPartCode,prd.pdoductDescription) from Assets ast left join ast.product prd left join ast.site st where st is null and ast.serialNumber like :searchKey")
	Page<com.pratikshat.exicom.model.response.AssetsForList> getAssetsForListForInstallation(@Param("searchKey")String searchKey, Pageable page);
	
	
	@Query("select new com.pratikshat.exicom.model.response.AssetsForList(ast.serialNumber,prd.productPartCode,prd.pdoductDescription) from Assets ast left join ast.product prd where ast.serialNumber like :serial")
	Page<com.pratikshat.exicom.model.response.AssetsForList> getAssetsForList(@Param("serial")String serialPart,Pageable page);
}
