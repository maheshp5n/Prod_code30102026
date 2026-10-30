package com.pratikshat.exicom.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.entity.BtServiceAppointment;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.BtServiceAppointmentExpRequest;
import com.pratikshat.exicom.model.response.StatusMessageResponse;
import com.pratikshat.exicom.repository.BtServiceAppointmentExpCustomRepositoryImpl;
import com.pratikshat.exicom.repository.BtServiceAppointmentExpRepository;
import com.pratikshat.exicom.repository.BtServiceAppointmentRepository;
import com.pratikshat.exicom.repository.BtServiceAppointmentRepositoryCustomeImpl;
import com.pratikshat.exicom.services.BtServiceAppointmentExpService;

@Service
public class BtServiceAppointmentExpServiceImpl implements BtServiceAppointmentExpService {

	@Autowired
	BtServiceAppointmentExpCustomRepositoryImpl dBExpRepoImpl;

	@Autowired
	BtServiceAppointmentRepositoryCustomeImpl btServiceAppointmentImpl;

	@Autowired
	BtServiceAppointmentRepository btServiceAppointmentRepo;

	@Autowired
	BtServiceAppointmentExpRepository btSADB;

	@Override
	public Map<String, Object> getSingle(Object id, LoginUser user) throws BadRequestException, UnauthoriseException {

		if (id == null && id.toString().equalsIgnoreCase("null") && id.toString().equalsIgnoreCase("")
				&& Long.parseLong(id.toString()) == 0) {
			throw new BadRequestException(" btServiceAppointmentExp id is null");
		}
		return btSADB.getById(Long.parseLong(id.toString()));
	}

	@Override
	public Page<Map<String, Object>> getPage(Pageable page, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional(isolation = Isolation.READ_UNCOMMITTED, rollbackFor = { Exception.class, BadRequestException.class,
			UnauthoriseException.class, RuntimeException.class }, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Map<String, Object> saveObject(Map<String, Object> request, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		System.out.println("mnx");
		if (request.get("btExpId") == null && request.get("btExpId").toString().equalsIgnoreCase("")
				&& request.get("btExpId").toString().equalsIgnoreCase("null")
				&& Long.parseLong(request.get("btExpId").toString()) == 0) {
			throw new BadRequestException("BtExp Id Is Null");
		}

		if (request.get("costCenter") == null && request.get("costCenter").toString().equalsIgnoreCase("")
				&& request.get("costCenter").toString().equalsIgnoreCase("null")) {
			throw new BadRequestException("Cost Center Is Null");
		}

		if (request.get("btServicAppointmentExp") == null
				&& request.get("btServicAppointmentExp").toString().equalsIgnoreCase("")
				&& request.get("btServicAppointmentExp").toString().equalsIgnoreCase("null")) {
			throw new BadRequestException("btServicAppointmentExp List Is Null");
		}
		System.out.println("[][]");
		BtServiceAppointmentExpRequest req = new BtServiceAppointmentExpRequest();
		List<Map<String, Object>> list = new ArrayList();
		BtServiceAppointmentExpRequest reqs = null;

		System.out.println("ckxz");

		if (request.get("btExpId") != null && !request.get("btExpId").toString().equalsIgnoreCase("")
				&& !request.get("btExpId").toString().equalsIgnoreCase("null")
				&& Long.parseLong(request.get("btExpId").toString()) != 0) {
			req.setBtExpId(Long.parseLong(request.get("btExpId").toString()));
		}
		if (request.get("totalClaim") != null && !request.get("totalClaim").toString().equalsIgnoreCase("")
				&& !request.get("totalClaim").toString().equalsIgnoreCase("null")
				&& Double.parseDouble(request.get("totalClaim").toString()) != 0.0) {
			req.setTotalClaimAmount(Double.parseDouble(request.get("totalClaim").toString()));
		}
		if (request.get("costCenter") != null && !request.get("costCenter").toString().equalsIgnoreCase("")
				&& !request.get("costCenter").toString().equalsIgnoreCase("null")) {
			req.setCostCenter(request.get("costCenter").toString());
		}

		if (request.get("btServicAppointmentExp") != null
				&& !request.get("btServicAppointmentExp").toString().equalsIgnoreCase("")
				&& !request.get("btServicAppointmentExp").toString().equalsIgnoreCase("null")) {

			list = (List<Map<String, Object>>) request.get("btServicAppointmentExp");
		}

		System.out.println("list  " + list);
		System.out.println("jckxjz");
		if (!list.isEmpty()) {
			for (Map<String, Object> map : list) {

				if (map.get("btServicAppointmentId") == null
						&& map.get("btServicAppointmentId").toString().equalsIgnoreCase("null")
						&& map.get("btServicAppointmentId").toString().equalsIgnoreCase("")
						&& Long.parseLong(map.get("btServicAppointmentId").toString()) == 0) {
					throw new BadRequestException("btServicAppointment Id Is Null");
				}
				if (map.get("claimAmount") == null && map.get("claimAmount").toString().equalsIgnoreCase("null")
						&& map.get("claimAmount").toString().equalsIgnoreCase("")
						&& Double.parseDouble(map.get("claimAmount").toString()) == 0) {
					throw new BadRequestException("claimAmount Is Null");
				}
				req.setBtServiceAppointmentId(Long.parseLong(map.get("btServicAppointmentId").toString()));
				req.setClaimAmount(Double.parseDouble(map.get("claimAmount").toString()));
				System.out.println("nxnmn");
				Map<String, Object> appoint = btServiceAppointmentRepo
						.getById(Long.parseLong(map.get("btServicAppointmentId").toString()));
				if (!appoint.isEmpty()) {
					reqs = dBExpRepoImpl.saveDetails(req, user);
				} else {
					throw new BadRequestException("you have taken claimed ");
				}
				System.out.println("saved...");
				BtServiceAppointment appointment = btServiceAppointmentRepo
						.findByServiceAppointmentId(Long.parseLong(map.get("btServicAppointmentId").toString()));
				appointment.setServiceAppointmentExpense(true);
				btServiceAppointmentImpl.updateDetails(appointment, user);
			}

		}
		System.out.println("reqs  " + reqs.getBtServiceAppointmentExpId());

		Map<String, Object> map2 = btSADB.getById(reqs.getBtServiceAppointmentExpId());
		// dBExpenseRepoImpl.saveDetails(req, user);
		// dBExpenseRepoImpl.saveDetails(req, user);

		return map2;
	}

	@Transactional(isolation = Isolation.READ_UNCOMMITTED, rollbackFor = { Exception.class, BadRequestException.class,
			UnauthoriseException.class, RuntimeException.class }, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Map<String, Object> updateObject(Map<String, Object> request, LoginUser user)
			throws BadRequestException, UnauthoriseException {

		System.out.println("service request   " + request);

		if (request.get("btExpId") == null && request.get("btExpId").toString().equalsIgnoreCase("")
				&& request.get("btExpId").toString().equalsIgnoreCase("null")
				&& Long.parseLong(request.get("btExpId").toString()) == 0) {
			throw new BadRequestException("BtExp Id Is Null");
		}

		if (request.get("costCenter") == null && request.get("costCenter").toString().equalsIgnoreCase("")
				&& request.get("costCenter").toString().equalsIgnoreCase("null")) {
			throw new BadRequestException("Cost Center Is Null");
		}

		if (request.get("btServicAppointmentExp") == null
				&& request.get("btServicAppointmentExp").toString().equalsIgnoreCase("")
				&& request.get("btServicAppointmentExp").toString().equalsIgnoreCase("null")) {
			throw new BadRequestException("btServicAppointmentExp List Is Null");
		}
		System.out.println("[][]");
		BtServiceAppointmentExpRequest req = new BtServiceAppointmentExpRequest();
		List<Map<String, Object>> list = new ArrayList();
		BtServiceAppointmentExpRequest reqs = null;

		System.out.println("ckxz");

		if (request.get("btExpId") != null && !request.get("btExpId").toString().equalsIgnoreCase("")
				&& !request.get("btExpId").toString().equalsIgnoreCase("null")
				&& Long.parseLong(request.get("btExpId").toString()) != 0) {
			req.setBtExpId(Long.parseLong(request.get("btExpId").toString()));
		}
		if (request.get("totalClaim") != null && !request.get("totalClaim").toString().equalsIgnoreCase("")
				&& !request.get("totalClaim").toString().equalsIgnoreCase("null")
				&& Double.parseDouble(request.get("totalClaim").toString()) != 0.0) {
			req.setTotalClaimAmount(Double.parseDouble(request.get("totalClaim").toString()));
		}
		if (request.get("costCenter") != null && !request.get("costCenter").toString().equalsIgnoreCase("")
				&& !request.get("costCenter").toString().equalsIgnoreCase("null")) {
			req.setCostCenter(request.get("costCenter").toString());
		}

		if (request.get("btServicAppointmentExp") != null
				&& !request.get("btServicAppointmentExp").toString().equalsIgnoreCase("")
				&& !request.get("btServicAppointmentExp").toString().equalsIgnoreCase("null")) {

			list = (List<Map<String, Object>>) request.get("btServicAppointmentExp");
		}

		System.out.println("list  " + list);
		System.out.println("jckxjz");
		if (!list.isEmpty()) {
			for (Map<String, Object> map : list) {
				if (map.get("id") == null && map.get("id").toString().equalsIgnoreCase("")
						&& map.get("id").toString().equalsIgnoreCase("null")
						&& Long.parseLong(map.get("id").toString()) == 0) {
					throw new BadRequestException(" Id Is Null");
				}
				if (map.get("btServicAppointmentId") == null
						&& map.get("btServicAppointmentId").toString().equalsIgnoreCase("null")
						&& map.get("btServicAppointmentId").toString().equalsIgnoreCase("")
						&& Long.parseLong(map.get("btServicAppointmentId").toString()) == 0) {
					throw new BadRequestException("btServicAppointment Id Is Null");
				}

				if (map.get("id") == null && map.get("id").toString().equalsIgnoreCase("null")
						&& map.get("id").toString().equalsIgnoreCase("")
						&& Long.parseLong(map.get("id").toString()) == 0) {
					throw new BadRequestException("btServicAppointment Id Is Null");
				}
				if (map.get("amountToDisbursed") == null
						&& map.get("amountToDisbursed").toString().equalsIgnoreCase("null")
						&& map.get("amountToDisbursed").toString().equalsIgnoreCase("")
						&& Double.parseDouble(map.get("amountToDisbursed").toString()) == 0) {
					throw new BadRequestException("amountToDisbursed Is Null");
				}
				req.setBtServiceAppointmentExpId(Long.parseLong(map.get("id").toString()));
				req.setBtServiceAppointmentId(Long.parseLong(map.get("btServicAppointmentId").toString()));
				req.setClaimAmount(Double.parseDouble(map.get("claimAmount").toString()));
				req.setAmountToDisbursed(Double.parseDouble(map.get("amountToDisbursed").toString()));
				System.out.println("nxnmn");

				System.out.println("saved...");
				reqs = dBExpRepoImpl.updateDetails(req, user);
			}

		}

		Map<String, Object> map2 = btSADB.getById(reqs.getBtServiceAppointmentExpId());
		return map2;
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

	@Override
	public Page<Map<String, Object>> getList(long id, Pageable page) throws BadRequestException, UnauthoriseException {
		if (id == 0) {
			throw new BadRequestException(" btExpenseManagement id is null");
		}
		return btSADB.getAll(id, page);

	}

	@Transactional(isolation = Isolation.READ_UNCOMMITTED, rollbackFor = { Exception.class, BadRequestException.class,
			UnauthoriseException.class, RuntimeException.class }, propagation = Propagation.REQUIRES_NEW)
	@Override
	public StatusMessageResponse deleteBtServiceAppointmentExp(long id, LoginUser user)
			throws BadRequestException, UnauthoriseException {

		return dBExpRepoImpl.deleteBtServiceAppointmentExp(id, user);
	}

	@Override
	public List<Map<String, Object>> getBtServiceAppointmentDetailByExpId(String userEmailId) {

		return btSADB.getBtServiceAppointmentDetailByExpId(userEmailId);
	}

}
