package com.pratikshat.exicom.repository;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.model.request.BoardingExpenseRequest;

public interface BoardingExpenseCustomeRepository {

	public BoardingExpenseRequest saveBoardingExpense(BoardingExpenseRequest request,LoginUser user) throws Exception;
	public BoardingExpenseRequest updateBoardingExpense(BoardingExpenseRequest request,LoginUser user) throws Exception;
	
	public long deleteBoardingExpense(long boardingExpenseId, LoginUser user) throws Exception;
}
