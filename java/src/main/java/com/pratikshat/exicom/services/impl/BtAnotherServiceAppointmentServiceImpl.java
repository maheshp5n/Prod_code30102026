package com.pratikshat.exicom.services.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pratikshat.exicom.entity.BtAnotherServiceAppointment;
import com.pratikshat.exicom.entity.BtServiceAppointment;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.repository.BtAnotherServiceAppointmentRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;
import com.pratikshat.exicom.services.BtAnotherServiceAppointmentService;

@Service
public class BtAnotherServiceAppointmentServiceImpl implements BtAnotherServiceAppointmentService{
	
	@Autowired
	UserProfileRepository dbprofile;
	
	@Autowired
	BtAnotherServiceAppointmentRepository dbanotherSArepo;

	@Override
	public Map<String, Object> getSingle(Object id, LoginUser user) throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		boolean view = dbprofile.hasObjectAccess(user.getUsername(), BtServiceAppointment.class,
				UserProfileRepository.VIEW);
		boolean viewall = dbprofile.hasObjectAccess(user.getUsername(), BtServiceAppointment.class,
				UserProfileRepository.VIEW_ALL);
		
		if(view || viewall)
		{
			Long _id= Long.parseLong(id.toString());
			return dbanotherSArepo.getAnotherServiceById(_id);
		}else {
			throw new UnauthoriseException("You are not authorised");
		}
	}

	@Override
	public Page<Map<String, Object>> getPage(Pageable page, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> saveObject(Map<String, Object> request, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> updateObject(Map<String, Object> request, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> searchForList(String search, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> advanceSearch(Map<String, Object> searchRequest, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
