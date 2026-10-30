package com.pratikshat.exicom.repository;

import com.pratikshat.exicom.entity.QueryBrowserExecutionLog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QueryBrowserExecutionLogRepository extends JpaRepository<QueryBrowserExecutionLog,Long> {
    
}
