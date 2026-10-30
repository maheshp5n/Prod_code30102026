package com.pratikshat.exicom.repository;

import com.pratikshat.exicom.entity.BtApproval;

public interface BtApprovalRepositoryCustome extends GenericOperation<BtApproval>{
	public void enableDisable(Long id,boolean flag);
}