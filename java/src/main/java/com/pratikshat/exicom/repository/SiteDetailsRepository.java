package com.pratikshat.exicom.repository;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.SiteDetails;
@Repository
public interface SiteDetailsRepository extends JpaRepository<SiteDetails, Long>,SiteDetailsRepositoryCustome {
	int countBySiteId(long siteid);
	
	SiteDetails findBySiteId(long siteid);
	
	@Query("select new com.pratikshat.exicom.model.response.SiteDetailsForList(site.siteId,site.siteName,site.manualSiteId) from SiteDetails site")
	java.util.List<com.pratikshat.exicom.model.response.SiteDetailsForList> getSiteForList();
	@Query("select new com.pratikshat.exicom.model.response.SiteDetailsResponse(site) from SiteDetails site left join site.primaryAccount paccount left join site.owner owner left join site.account acou left join site.serviceCenter serv left join site.serviceResource res left join site.circle circ left join site.ssa zn")
	Page<com.pratikshat.exicom.model.response.SiteDetailsResponse> getByAll(Pageable page);
	@Query("select new com.pratikshat.exicom.model.response.SiteDetailsResponse(site) from SiteDetails site left join site.primaryAccount paccount left join site.owner owner left join site.account acou left join site.serviceCenter serv left join site.serviceResource res left join site.circle circ left join site.ssa zn left join site.createBy crd left join site.modifiedBy mdf where site.siteId=:siteId")
	com.pratikshat.exicom.model.response.SiteDetailsResponse getBySiteid(@Param("siteId")long siteId);
	@Query("select new com.pratikshat.exicom.model.response.SiteDetailsResponse(site) from SiteDetails site left join site.primaryAccount paccount left join site.owner owner left join site.account acou left join site.serviceCenter serv left join site.serviceResource res left join site.circle circ left join site.ssa zn join SiteDetailsRecentVisit rt on site.siteId=rt.siteId where rt.visitorId=:username")
	Page<com.pratikshat.exicom.model.response.SiteDetailsResponse> getRecentVisit(@Param("username")String username,Pageable page);
	
	@Query("select new com.pratikshat.exicom.model.response.SiteDetailsResponse(site) from SiteDetails site left join site.primaryAccount paccount left join site.owner owner left join site.account acou left join site.serviceCenter serv left join site.serviceResource res left join site.circle circ left join site.ssa zn where acou.sapCustomerNumber=:sapnumber")
	java.util.List<com.pratikshat.exicom.model.response.SiteDetailsResponse> getSiteDetailsByAccountSapNumber(@Param("sapnumber")String sapnumber);
	
	@Query("select new com.pratikshat.exicom.model.response.ServiceAppointmentResponse(sr) from ServiceAppointment sr join sr.caseDetails cs join cs.assets ast join ast.site st where st.siteId=:siteid")
	Page<com.pratikshat.exicom.model.response.ServiceAppointmentResponse> getReleatedServiceAppoint(@Param("siteid")long siteId,Pageable page);
	
	@Query("select new com.pratikshat.exicom.model.response.AssetsResponse(ast) from Assets ast join ast.site st where st.siteId=:siteid")
	Page<com.pratikshat.exicom.model.response.AssetsResponse> getAssetsBySiteId(@Param("siteid")long siteId,Pageable page);
	
	//created by shyamu
	@Query("select new com.pratikshat.exicom.model.response.SiteDetailsResponse(site) from SiteDetails site left join site.primaryAccount paccount left join site.owner owner left join site.account acou left join site.serviceCenter serv left join site.serviceResource res left join site.circle circ left join site.ssa zn where acou.sapCustomerNumber=:sapnumber")
	Page<com.pratikshat.exicom.model.response.SiteDetailsResponse> getSiteDetailsByAccountId(@Param("sapnumber")String sapnumber,Pageable page);
	//close
	
	@Query("select new com.pratikshat.exicom.model.response.SiteDetailsResponse(site) from SiteDetails site left join site.primaryAccount paccount left join site.owner owner left join site.account acou left join site.serviceCenter serv left join site.serviceResource res left join site.circle circ left join site.ssa zn where zn.id=:ssaId")
	Page<com.pratikshat.exicom.model.response.SiteDetailsResponse> getSiteDetailsBySsaId(@Param("ssaId")long ssaId,Pageable page);
	
	@Query("select new com.pratikshat.exicom.model.response.SiteDetailsResponse(site) from SiteDetails site left join site.primaryAccount paccount left join site.owner owner left join site.account acou left join site.serviceCenter serv left join site.serviceResource res left join site.circle circ left join site.ssa zn left join site.createBy crd left join site.modifiedBy mdf where site.manualSiteId=:manualSiteId")
	com.pratikshat.exicom.model.response.SiteDetailsResponse getByManualSiteId(@Param("manualSiteId")String manualSiteId);
	
	@Query("select new com.pratikshat.exicom.model.response.SiteDetailsForList(site.siteId,site.siteName,site.manualSiteId) from SiteDetails site where site.siteName like :siteName or site.manualSiteId like :siteName")
	Page<com.pratikshat.exicom.model.response.SiteDetailsForList> getSiteForList(@Param("siteName")String searchkey, Pageable page);
	
	@Query("select new com.pratikshat.exicom.model.response.SiteDetailsResponse(site) from SiteDetails site left join site.primaryAccount paccount left join site.owner owner left join site.account acou left join site.serviceCenter serv left join site.serviceResource res left join site.circle circ left join site.ssa zn left join site.createBy crd left join site.modifiedBy mdf where site.manualSiteId=:manualSiteId and site.siteName=:siteName and circ.circleName=:circle and acou.sapCustomerNumber=:account")
	com.pratikshat.exicom.model.response.SiteDetailsResponse getByManualSiteIdAndSiteNameAndCircleAndAccount(@Param("manualSiteId")String manualSiteId, @Param("siteName")String siteName, @Param("circle")String circle, @Param("account")String account);

	@Query(value="SELECT\r\n" + 
			"DISTINCT `customer`\r\n" + 
			"FROM `site_details`\r\n" + 
			"WHERE `customer` IS NOT NULL\r\n" + 
			"AND `customer` LIKE :customer \r\n" + 
			"ORDER BY `customer` ASC",nativeQuery=true)
	java.util.List<String> getSiteCustomerList(@Param("customer")String customer);
	
	long countByManualSiteId(String manualSiteId);
	long countBySiteName(String sitename);   
	long countByInfraId(String infraid);
	long countByIndusId(String indusid);
	
	@Query(value="SELECT * FROM `site_details`  WHERE `site_id`!=:siteId AND `site_name`=:siteName", nativeQuery =true)
	java.util.List<Map<String, Object>> getBySiteName(@Param("siteId") long siteId,@Param("siteName") String siteName);
	
	
	@Query(value="SELECT * FROM `site_details`  WHERE `site_id`!=:siteId AND `indus_id`=:inudusId", nativeQuery =true)
	java.util.List<Map<String, Object>> getByIndusId(@Param("siteId") long siteId,@Param("inudusId") String indusId);
	
	@Query(value="SELECT * FROM `site_details`  WHERE `site_id`!=:siteId AND `infra_id`=:infraId", nativeQuery =true)
	java.util.List<Map<String, Object>> getByInfraId(@Param("siteId") long siteId,@Param("infraId") String infraId);
	

}
