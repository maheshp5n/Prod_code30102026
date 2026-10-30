package com.pratikshat.exicom.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface WorkflowentityRepository extends JpaRepository<com.pratikshat.exicom.entity.WorkFlowEntity, Long> {
	@Query("select new com.pratikshat.exicom.model.response.WorkflowResponse(wr.id,wr.description,obj.objectLableName,obj.objectName,wr.dmlCommand,wr.onevent,crd.firstName,mdf.firstName,wr.createdDate,wr.modifiedDate,wr.className,wr.active,crd.middleName,crd.lastName,crd.emailId,mdf.middleName,mdf.lastName,mdf.emailId,wr.codeSnip) from WorkFlowEntity wr join wr.object obj join wr.createdBy crd join wr.modifiedBy mdf")
	java.util.List<com.pratikshat.exicom.model.response.WorkflowResponse> getworkflow();
	@Query("from WorkFlowEntity wr where wr.id=:id")
	com.pratikshat.exicom.entity.WorkFlowEntity getById(@Param("id")Long id);
	
	@Query("select new com.pratikshat.exicom.model.response.WorkflowResponse(wr.id,wr.description,obj.objectLableName,obj.objectName,wr.dmlCommand,wr.onevent,crd.firstName,mdf.firstName,wr.createdDate,wr.modifiedDate,wr.className,wr.active,crd.middleName,crd.lastName,crd.emailId,mdf.middleName,mdf.lastName,mdf.emailId,wr.codeSnip) from WorkFlowEntity wr join wr.object obj join wr.createdBy crd join wr.modifiedBy mdf where wr.id=:id")
	com.pratikshat.exicom.model.response.WorkflowResponse getworkflow(@Param("id")Long id);
	
}
