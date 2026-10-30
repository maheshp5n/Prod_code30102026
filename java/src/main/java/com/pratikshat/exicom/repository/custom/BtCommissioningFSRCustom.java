package com.pratikshat.exicom.repository.custom;

import java.io.ByteArrayInputStream;

import com.pratikshat.exicom.entity.BtCommissioningFSR;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.repository.GenericOperation;

public interface BtCommissioningFSRCustom extends GenericOperation<BtCommissioningFSR>{

	public ByteArrayInputStream generatePdf(LoginUser user, long id) throws Exception;
}
