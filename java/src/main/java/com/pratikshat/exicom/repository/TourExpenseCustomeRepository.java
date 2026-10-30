package com.pratikshat.exicom.repository;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.model.request.TourExpenseRequest;

public interface TourExpenseCustomeRepository {

	public TourExpenseRequest saveTourExpense(TourExpenseRequest request,LoginUser user) throws Exception;
	public TourExpenseRequest updateTourExpense(TourExpenseRequest request,LoginUser user) throws Exception;
	
	public long deleteTourExpense(long id, LoginUser user) throws Exception;
}
