package com.pratikshat.exicom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemConfirParamRepo extends JpaRepository<com.pratikshat.exicom.entity.SystemConfigurationParameter, Long> {
	com.pratikshat.exicom.entity.SystemConfigurationParameter findByObjectNameAndInvokingStatusAndActive(String objectName,String invokingStatus,boolean active);
	java.util.List<com.pratikshat.exicom.entity.SystemConfigurationParameter> findByObjectNameAndActive(String objectName,boolean active);
}
