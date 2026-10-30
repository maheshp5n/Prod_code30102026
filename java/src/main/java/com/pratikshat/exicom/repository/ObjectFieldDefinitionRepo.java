package com.pratikshat.exicom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ObjectFieldDefinitionRepo extends JpaRepository<com.pratikshat.exicom.entity.ObjectFieldDefinition, Long> {
	com.pratikshat.exicom.entity.ObjectFieldDefinition findByObjectDefAndObjectFieldName(com.pratikshat.exicom.entity.ObjectDefinition obj,String fildName);
	java.util.List<com.pratikshat.exicom.entity.ObjectFieldDefinition> findByObjectDef_ObjectName(String objectName);
}
