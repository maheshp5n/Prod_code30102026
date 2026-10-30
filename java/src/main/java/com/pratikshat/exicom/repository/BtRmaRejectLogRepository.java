package com.pratikshat.exicom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.BtRmaRejectLog;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.UploadedFiles;
import com.pratikshat.exicom.repository.custom.BtRmaRejectLogCustom;

@Repository
public interface BtRmaRejectLogRepository extends JpaRepository<BtRmaRejectLog, Long>,BtRmaRejectLogCustom {

	

}
