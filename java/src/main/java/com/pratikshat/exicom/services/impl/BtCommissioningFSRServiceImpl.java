package com.pratikshat.exicom.services.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.entity.BtAssets;
import com.pratikshat.exicom.entity.BtCommissioningAssetMasterUpdate;
import com.pratikshat.exicom.entity.BtCommissioningFSR;
import com.pratikshat.exicom.entity.BtCoolingMethod;
import com.pratikshat.exicom.entity.BtPendingReasonCustomerBucket;
import com.pratikshat.exicom.entity.BtPowerPlantMake;
import com.pratikshat.exicom.entity.BtServiceAppointment;
import com.pratikshat.exicom.entity.BtSiteEnvironmentalConditions;
import com.pratikshat.exicom.entity.BtSiteType;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.BtCommissioningAssetMasterUpdateRequest;
import com.pratikshat.exicom.model.request.BtCommissioningFSRRequest;
import com.pratikshat.exicom.model.request.BtCommissioningFSRUpdateRequest;
import com.pratikshat.exicom.model.request.BtServiceFSRRequest;
import com.pratikshat.exicom.model.response.BtCommissioningFSRResponse;
import com.pratikshat.exicom.repository.BtAssetsRepository;
import com.pratikshat.exicom.repository.BtCommissioningAssetMasterUpdateRepository;
import com.pratikshat.exicom.repository.BtCommissioningFSRRepository;
import com.pratikshat.exicom.repository.BtCoolingMethodRepository;
import com.pratikshat.exicom.repository.BtPendingReasonCustomerBucketRepository;
import com.pratikshat.exicom.repository.BtPowerPlantMakeRepository;
import com.pratikshat.exicom.repository.BtServiceAppointmentRepository;
import com.pratikshat.exicom.repository.BtSiteEnvironmentalConditionsRepository;
import com.pratikshat.exicom.repository.BtSiteTypeRepository;
import com.pratikshat.exicom.services.BtCommissioningFSRService;

@Service
public class BtCommissioningFSRServiceImpl implements BtCommissioningFSRService {

	@Autowired
	BtServiceAppointmentRepository dbServiceAppointmentRepo;

	@Autowired
	BtSiteTypeRepository dbSiteType;

	@Autowired
	BtSiteEnvironmentalConditionsRepository dbSiteEnvironmentalConditionsRepo;

	@Autowired
	BtCoolingMethodRepository dbCoolingMethodRepo;

	@Autowired
	BtPowerPlantMakeRepository dbPowerPlantMakeRepo;

	@Autowired
	BtPendingReasonCustomerBucketRepository dbPendingReasonCustomerBucketRepo;

	@Autowired
	BtAssetsRepository dbAssetRepo;

	@Autowired
	BtCommissioningAssetMasterUpdateRepository dbAssetMasterUpdateRepo;

	@Autowired
	BtCommissioningFSRRepository dbCommissioningFSRRepo;

	@Override
	public Map<String, Object> getSingle(Object id, LoginUser user) throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
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

	@Transactional(rollbackFor = { BadRequestException.class, UnauthoriseException.class,
			Exception.class }, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED)
	@Override
	public BtCommissioningFSRResponse saveObject(BtCommissioningFSRRequest req, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		
		
		BtCommissioningFSR commFsr = new BtCommissioningFSR();

		BtServiceAppointment btServiceAppointment = dbServiceAppointmentRepo
				.findByServiceAppointmentId(req.getAppointment());
		//validate Owner of service app
		if (!btServiceAppointment.getOwnerDetails().getEmailId().equalsIgnoreCase(user.getUsername())) {
			throw new com.pratikshat.exicom.exception.UnauthoriseException(
					"Only service appointment  owner can create FSR");
		}
	
		
		if (btServiceAppointment.isFsrCreated() == true) {
			throw new BadRequestException("FSR already created on this service appointment");
		}
		if (btServiceAppointment == null)
			throw new BadRequestException("No service appointment found");

		commFsr.setBtServiceAppointment(btServiceAppointment);

		if (req.getContactPersonAtSite() == null && req.getContactPersonAtSite().toString().equalsIgnoreCase(""))
			throw new BadRequestException("Contact Person at site is blank");
		commFsr.setContactPersonAtSite(req.getContactPersonAtSite());

		if (req.getContactPersonAtSiteContactNumber() == 0
				&& String.valueOf(req.getContactPersonAtSiteContactNumber()).equalsIgnoreCase(""))
			throw new BadRequestException("Contact person at site contact no. is blank");
		commFsr.setContactPersonAtSiteContactNo(req.getContactPersonAtSiteContactNumber());

		if (req.getDateOfSiteVisit() == null && req.getDateOfSiteVisit().toString().equalsIgnoreCase(""))
			throw new BadRequestException("Date of site visit is blank");
		commFsr.setDateOfSiteVisit(req.getDateOfSiteVisit());

		BtSiteType btSiteType = dbSiteType.getBySitetypeWithId(req.getSiteType());
		if (btSiteType == null)
			throw new BadRequestException("Site type not find");
		commFsr.setBtSiteType(btSiteType);

		BtSiteEnvironmentalConditions btSiteEnvironmentalConditions = dbSiteEnvironmentalConditionsRepo
				.getBySiteEnvironmentalConditionsWithId(req.getBtSiteEnvironmentalConditions());
		if (btSiteEnvironmentalConditions == null)
			throw new BadRequestException("Site enviromental condition not found");
		commFsr.setBtSiteEnvironmentalConditions(btSiteEnvironmentalConditions);

		if (req.getEarthigStatus() == 0 && String.valueOf(req.getEarthigStatus()).equalsIgnoreCase(""))
			throw new BadRequestException("Earthing status is blank");
		commFsr.setEarthingStatus(req.getEarthigStatus());

		BtCoolingMethod btCoolingMethod = dbCoolingMethodRepo.getByCoolingMethodWithId(req.getBtCoolingMethod());
		if (btCoolingMethod == null)
			throw new BadRequestException("Cooling method not found");
		commFsr.setBtCoolingMethod(btCoolingMethod);

		if (req.getAmbientTemp() == 0 && String.valueOf(req.getAmbientTemp()).equalsIgnoreCase(""))
			throw new BadRequestException("Ambient temp  is blank");
		commFsr.setAmbientTemperature(req.getAmbientTemp());

		if (req.getCommissionScope() == null && req.getCommissionScope().toString().equalsIgnoreCase(""))
			throw new BadRequestException("Commission scope is blank");
		commFsr.setCommissionScope(req.getCommissionScope());

		if (req.getTotalEquipmentDcLoad() == 0 && String.valueOf(req.getTotalEquipmentDcLoad()).equalsIgnoreCase(""))
			throw new BadRequestException("Total equipment DC load is blank");
		commFsr.setTotalEquipmentDCLoad(req.getTotalEquipmentDcLoad());

		if (req.getInstalltionStatus() == null && req.getInstalltionStatus().equalsIgnoreCase(""))
			throw new BadRequestException("Installtion status is blank");
		commFsr.setInstallationStatus(req.getInstalltionStatus());

		if (req.getSmpsToCbmsVolGap() == 0 && String.valueOf(req.getSmpsToCbmsVolGap()).equalsIgnoreCase(""))
			throw new BadRequestException("SMPS to CBMS voltage gap is blank");
		commFsr.setSmpsToCbmsVolGap(req.getSmpsToCbmsVolGap());

		if (req.getCbmsToBatVolGap() == 0 && String.valueOf(req.getCbmsToBatVolGap()).equalsIgnoreCase(""))
			throw new BadRequestException("CBMS to BAT voltage gap is blank");
		commFsr.setCbmsToBatVolGap(req.getCbmsToBatVolGap());

		if (req.getDcLoadCurrent() == 0 && String.valueOf(req.getDcLoadCurrent()).equalsIgnoreCase(""))
			throw new BadRequestException("DC load current is blank");
		commFsr.setDcLoadCurrent(req.getDcLoadCurrent());

		if (req.getMaxModuleTemp() == 0 && String.valueOf(req.getMaxModuleTemp()).equalsIgnoreCase(""))
			throw new BadRequestException("Max module temp is blank");
		commFsr.setMaxModuleTemp(req.getMaxModuleTemp());

		if (req.getChargeVoltage() == 0 && String.valueOf(req.getChargeVoltage()).equalsIgnoreCase(""))
			throw new BadRequestException("Charging voltage is blank");
		commFsr.setChargeVoltage(req.getChargeVoltage());

		if (req.getFloatVoltage() == 0 && String.valueOf(req.getFloatVoltage()).equalsIgnoreCase(""))
			throw new BadRequestException("Float voltage is blank");
		commFsr.setFloatVoltage(req.getFloatVoltage());

		BtPowerPlantMake btPowerPlantMake = dbPowerPlantMakeRepo.getByPowerPlantMakeWithId(req.getPowerPlantMake());
		if (btPowerPlantMake == null)
			throw new BadRequestException("Power plant make not found");
		commFsr.setBtPowerPlantMake(btPowerPlantMake);

		if (req.getActiveRectifers() == 0 && String.valueOf(req.getActiveRectifers()).equalsIgnoreCase(""))
			throw new BadRequestException("Active rectifers is blank");
		commFsr.setActiveRectifiers(req.getActiveRectifers());

		commFsr.setExicomEngRemarks(req.getExicomEngRemarks());

		commFsr.setCustomerRemarks(req.getCustomerRemarks());
		
		//set and get observations
		commFsr.setObservation1(req.getObservation1());
		commFsr.setObservation2(req.getObservation2());
		commFsr.setObservation3(req.getObservation3());
		commFsr.setObservation4(req.getObservation4());
		
		

		if (req.getCommStatus() == null || req.getCommStatus().toString().equalsIgnoreCase(""))
			throw new BadRequestException("Commissioning status is blank");
		
		commFsr.setCommStatus(req.getCommStatus());
		if (req.getCommStatus().toLowerCase().equalsIgnoreCase("completed")) {
			commFsr.setPendingBucket(" ");
			commFsr.setPendingReasonExicomBucket(" ");
		} else if (req.getCommStatus().toLowerCase().equalsIgnoreCase("incompleted")) {
			
			if (req.getPendingBucket() == null || req.getPendingBucket().toString().equalsIgnoreCase(""))
				throw new BadRequestException("Pending bucket is blank");
			commFsr.setPendingBucket(req.getPendingBucket());

			if (req.getPendingBucket().toLowerCase().contains("exicom")) {

				if (req.getPendingReasonExicomBucket() == null
						|| req.getPendingReasonExicomBucket().toString().equalsIgnoreCase(""))
					throw new BadRequestException("Pending reason exicom bucket is blank");
				commFsr.setPendingReasonExicomBucket(req.getPendingReasonExicomBucket());
			}
			if (req.getPendingBucket().toLowerCase().contains("customer")) {
				BtPendingReasonCustomerBucket btPendingReasonCustomerBucket = dbPendingReasonCustomerBucketRepo
						.getByPendingReasonCustomerWithId(req.getPendingReasonCustomerBucket());

				if (btPendingReasonCustomerBucket == null || btPendingReasonCustomerBucket.toString().equalsIgnoreCase("") )
					throw new BadRequestException("Pending Reason Customer Bucket not found");
				commFsr.setBtPendingReasonCustomerBucket(btPendingReasonCustomerBucket);
			}

		} else {
			throw new BadRequestException("Comm status should be completed on incompleted");
		}

		if (req.getComplainAttandByEngName() == null && req.getComplainAttandByEngName().equalsIgnoreCase(""))
			throw new BadRequestException("Complain attend by eng name is blank");
		commFsr.setComplainAttandbyEngName(req.getComplainAttandByEngName());

		if (req.getEngNumber() == 0 && String.valueOf(req.getEngNumber()).equalsIgnoreCase(""))
			throw new BadRequestException("Eng name is blank");
		commFsr.setEngNo(req.getEngNumber());

		commFsr.setOwner(new EmployeeDetails(user.getUsername()));

		btServiceAppointment.setFsrCreated(true);
		dbServiceAppointmentRepo.updateDetails(btServiceAppointment, user);

		dbCommissioningFSRRepo.saveDetails(commFsr, user);

		if (req.getBtCommissioningAssetMasterUpdateRequests().size() >= 2) {

		    for (BtCommissioningAssetMasterUpdateRequest ass : req.getBtCommissioningAssetMasterUpdateRequests()) {

		        boolean isCbmsNA = ass.getEquipmentType().contains("CBMS")
		                           && "NA".equalsIgnoreCase(ass.getEquipmentSrNo());

		        BtCommissioningAssetMasterUpdate masterUpdate = new BtCommissioningAssetMasterUpdate();

		        masterUpdate.setSrNo(ass.getSrNo());

		        if (ass.getEquipmentType() == null || ass.getEquipmentType().equalsIgnoreCase(""))
		            throw new BadRequestException("Equipment type is blank");
		        masterUpdate.setEquipmentType(ass.getEquipmentType());

		        if (isCbmsNA) {
		            masterUpdate.setEquipmentSrNo("NA");
		            masterUpdate.setBtAssets(null);
		            masterUpdate.setSoftwareVersion("NA");
		            masterUpdate.setSoc(0);
		            masterUpdate.setSoh(0);
		            masterUpdate.setCurrentCondition(null);
		        } else {
		            BtAssets btAsset = dbAssetRepo.getWithId(ass.getBtAsset());
		            if (btAsset == null)
		                throw new BadRequestException("Asset not found");
		            masterUpdate.setBtAssets(btAsset);
		            masterUpdate.setSoftwareVersion(ass.getSoftwareVersion());
		            masterUpdate.setEquipmentSrNo(ass.getEquipmentSrNo());
		            masterUpdate.setSoc(ass.getSoc());
		            masterUpdate.setSoh(ass.getSoh());

		            if (ass.getCurrentCondition() == 0)
		                masterUpdate.setCurrentCondition("Ok");
		            if (ass.getCurrentCondition() == 1)
		                masterUpdate.setCurrentCondition("Faulty");
		        }

		        masterUpdate.setBtCommissioningFSR(commFsr);
		        dbAssetMasterUpdateRepo.saveDetails(masterUpdate, user);
		    }
		} else {
		    throw new BadRequestException("Please file asset master update");
		}
		return null;
		
	}
	
	

	@Override
	@Transactional(rollbackFor = { BadRequestException.class, UnauthoriseException.class,
			Exception.class }, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED)
	public Map<String, Object> updateObject(BtCommissioningFSRUpdateRequest req, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		
		BtCommissioningFSR commFsr = dbCommissioningFSRRepo.findById(req.getCommFSRId()).get();
		
		
		if(commFsr==null) {
			throw new BadRequestException("Commissioning FSR not created");
		}
		BtServiceAppointment btServiceAppointment = dbServiceAppointmentRepo.findByServiceAppointmentId(commFsr.getBtServiceAppointment().getId());
		//validate Owner of service app
		if (!btServiceAppointment.getOwnerDetails().getEmailId().equalsIgnoreCase(user.getUsername())) {
			throw new com.pratikshat.exicom.exception.UnauthoriseException(
					"Only service appointment  owner can edit FSR");
		}
		if (btServiceAppointment == null)
			throw new BadRequestException("No service appointment found");
		System.out.println(commFsr.getBtServiceAppointment().getId()+" Id");
		System.out.println(btServiceAppointment.isFsrCreated()+" Boolean Value");
		if (btServiceAppointment.isFsrCreated() != true) {
			throw new BadRequestException("FSR not created on this appoinment id");
		}

		commFsr.setBtServiceAppointment(btServiceAppointment);

		if (req.getContactPersonAtSite() == null && req.getContactPersonAtSite().toString().equalsIgnoreCase(""))
			throw new BadRequestException("Contact person at site is blank");
		commFsr.setContactPersonAtSite(req.getContactPersonAtSite());

		if (req.getContactPersonAtSiteContactNumber() == 0
				&& String.valueOf(req.getContactPersonAtSiteContactNumber()).equalsIgnoreCase(""))
			throw new BadRequestException("Contact person at site contact no. is blank");
		commFsr.setContactPersonAtSiteContactNo(req.getContactPersonAtSiteContactNumber());

		if (req.getDateOfSiteVisit() == null && req.getDateOfSiteVisit().toString().equalsIgnoreCase(""))
			throw new BadRequestException("Date of site visit is blank");
		commFsr.setDateOfSiteVisit(req.getDateOfSiteVisit());

		BtSiteType btSiteType = dbSiteType.getBySitetypeWithId(req.getSiteType());
		if (btSiteType == null)
			throw new BadRequestException("Site type not find");
		commFsr.setBtSiteType(btSiteType);

		BtSiteEnvironmentalConditions btSiteEnvironmentalConditions = dbSiteEnvironmentalConditionsRepo.getBySiteEnvironmentalConditionsWithId(req.getBtSiteEnvironmentalConditions());
		if (btSiteEnvironmentalConditions == null)
			throw new BadRequestException("Site enviromental condition not found");
		commFsr.setBtSiteEnvironmentalConditions(btSiteEnvironmentalConditions);

		if (req.getEarthigStatus() == 0 && String.valueOf(req.getEarthigStatus()).equalsIgnoreCase(""))
			throw new BadRequestException("Earthing status is blank");
		commFsr.setEarthingStatus(req.getEarthigStatus());

		BtCoolingMethod btCoolingMethod = dbCoolingMethodRepo.getByCoolingMethodWithId(req.getBtCoolingMethod());
		if (btCoolingMethod == null)
			throw new BadRequestException("Cooling method not found");
		commFsr.setBtCoolingMethod(btCoolingMethod);

		if (req.getAmbientTemp() == 0 && String.valueOf(req.getAmbientTemp()).equalsIgnoreCase(""))
			throw new BadRequestException("Ambient temp  is blank");
		commFsr.setAmbientTemperature(req.getAmbientTemp());

		if (req.getCommissionScope() == null && req.getCommissionScope().toString().equalsIgnoreCase(""))
			throw new BadRequestException("Commission scope is blank");
		commFsr.setCommissionScope(req.getCommissionScope());

		if (req.getTotalEquipmentDcLoad() == 0 && String.valueOf(req.getTotalEquipmentDcLoad()).equalsIgnoreCase(""))
			throw new BadRequestException("Total equipment DC load is blank");
		commFsr.setTotalEquipmentDCLoad(req.getTotalEquipmentDcLoad());

		if (req.getInstalltionStatus() == null && req.getInstalltionStatus().equalsIgnoreCase(""))
			throw new BadRequestException("Installtion status is blank");
		commFsr.setInstallationStatus(req.getInstalltionStatus());

		if (req.getSmpsToCbmsVolGap() == 0 && String.valueOf(req.getSmpsToCbmsVolGap()).equalsIgnoreCase(""))
			throw new BadRequestException("SMPS to CBMS voltage gap is blank");
		commFsr.setSmpsToCbmsVolGap(req.getSmpsToCbmsVolGap());

		if (req.getCbmsToBatVolGap() == 0 && String.valueOf(req.getCbmsToBatVolGap()).equalsIgnoreCase(""))
			throw new BadRequestException("CBMS to BAT voltage gap is blank");
		commFsr.setCbmsToBatVolGap(req.getCbmsToBatVolGap());

		if (req.getDcLoadCurrent() == 0 && String.valueOf(req.getDcLoadCurrent()).equalsIgnoreCase(""))
			throw new BadRequestException("DC load current is blank");
		commFsr.setDcLoadCurrent(req.getDcLoadCurrent());

		if (req.getMaxModuleTemp() == 0 && String.valueOf(req.getMaxModuleTemp()).equalsIgnoreCase(""))
			throw new BadRequestException("Max module temp is blank");
		commFsr.setMaxModuleTemp(req.getMaxModuleTemp());

		if (req.getChargeVoltage() == 0 && String.valueOf(req.getChargeVoltage()).equalsIgnoreCase(""))
			throw new BadRequestException("Charging voltage is blank");
		commFsr.setChargeVoltage(req.getChargeVoltage());

		if (req.getFloatVoltage() == 0 && String.valueOf(req.getFloatVoltage()).equalsIgnoreCase(""))
			throw new BadRequestException("Float voltage is blank");
		commFsr.setFloatVoltage(req.getFloatVoltage());

		BtPowerPlantMake btPowerPlantMake = dbPowerPlantMakeRepo.getByPowerPlantMakeWithId(req.getPowerPlantMake());
		if (btPowerPlantMake == null)
			throw new BadRequestException("Power plant make not found");
		commFsr.setBtPowerPlantMake(btPowerPlantMake);

		if (req.getActiveRectifers() == 0 && String.valueOf(req.getActiveRectifers()).equalsIgnoreCase(""))
			throw new BadRequestException("Active rectifers is blank");
		commFsr.setActiveRectifiers(req.getActiveRectifers());

		commFsr.setExicomEngRemarks(req.getExicomEngRemarks());

		commFsr.setCustomerRemarks(req.getCustomerRemarks());
		//set get ob
		commFsr.setObservation1(req.getObservation1());
		commFsr.setObservation2(req.getObservation2());
		commFsr.setObservation3(req.getObservation3());
		commFsr.setObservation4(req.getObservation4());

	//////////////////Commissiong Status/////////////////////////////////////////////
		if (req.getCommStatus() == null || req.getCommStatus().toString().equalsIgnoreCase(""))
			throw new BadRequestException("Commissioning status is blank");
		
		commFsr.setCommStatus(req.getCommStatus());
	////////////////If comm status completed/////////////////	
		if (req.getCommStatus().toLowerCase().equalsIgnoreCase("completed")) {
			commFsr.setPendingBucket(" ");
			commFsr.setPendingReasonExicomBucket(" ");
	////////////if comm status incompleted////////////////////////////		
		} else if (req.getCommStatus().toLowerCase().equalsIgnoreCase("incompleted")) {
			
			if (req.getPendingBucket() == null || req.getPendingBucket().toString().equalsIgnoreCase(""))
				throw new BadRequestException("Pending bucket is blank");
			commFsr.setPendingBucket(req.getPendingBucket());

			if (req.getPendingBucket().toLowerCase().contains("exicom")) {

				if (req.getPendingReasonExicomBucket() == null
						|| req.getPendingReasonExicomBucket().toString().equalsIgnoreCase(""))
					throw new BadRequestException("Pending reason exicom bucket is blank");
				commFsr.setPendingReasonExicomBucket(req.getPendingReasonExicomBucket());
			}
			if (req.getPendingBucket().toLowerCase().contains("customer")) {
				BtPendingReasonCustomerBucket btPendingReasonCustomerBucket = dbPendingReasonCustomerBucketRepo
						.getByPendingReasonCustomerWithId(req.getPendingReasonCustomerBucket());

				if (btPendingReasonCustomerBucket == null || btPendingReasonCustomerBucket.toString().equalsIgnoreCase("") )
					throw new BadRequestException("Pending reason Customer Bucket not found");
				commFsr.setBtPendingReasonCustomerBucket(btPendingReasonCustomerBucket);
			}

		} else {
			throw new BadRequestException("Comm status should be completed on incompleted");
		}

		if (req.getComplainAttandByEngName() == null && req.getComplainAttandByEngName().equalsIgnoreCase(""))
			throw new BadRequestException("Complain attend by eng name is blank");
		commFsr.setComplainAttandbyEngName(req.getComplainAttandByEngName());

		if (req.getEngNumber() == 0 && String.valueOf(req.getEngNumber()).equalsIgnoreCase(""))
			throw new BadRequestException("Eng name is blank");
		commFsr.setEngNo(req.getEngNumber());

		commFsr.setOwner(new EmployeeDetails(user.getUsername()));

		btServiceAppointment.setFsrCreated(true);
		dbServiceAppointmentRepo.updateDetails(btServiceAppointment, user);

		dbCommissioningFSRRepo.updateDetails(commFsr, user);

		if (req.getBtCommissioningAssetMasterUpdateRequests().size() >= 2) {

		    dbAssetMasterUpdateRepo.deleteByCommFSRId(req.getCommFSRId());

		    for (BtCommissioningAssetMasterUpdateRequest ass : req.getBtCommissioningAssetMasterUpdateRequests()) {

		        boolean isCbmsNA = ass.getEquipmentType().contains("CBMS")
		                           && "NA".equalsIgnoreCase(ass.getEquipmentSrNo());

		        BtCommissioningAssetMasterUpdate masterUpdate = new BtCommissioningAssetMasterUpdate();

		        masterUpdate.setSrNo(ass.getSrNo());

		        if (ass.getEquipmentType() == null || ass.getEquipmentType().equalsIgnoreCase(""))
		            throw new BadRequestException("Equipment type is blank");
		        masterUpdate.setEquipmentType(ass.getEquipmentType());

		        if (isCbmsNA) {
		            masterUpdate.setEquipmentSrNo("NA");
		            masterUpdate.setBtAssets(null);
		            masterUpdate.setSoftwareVersion(null);
		            masterUpdate.setSoc(0);
		            masterUpdate.setSoh(0);
		            masterUpdate.setCurrentCondition(null);
		        } else {
		            BtAssets btAsset = dbAssetRepo.getWithId(ass.getBtAsset());
		            if (btAsset == null)
		                throw new BadRequestException("Asset not found");
		            masterUpdate.setBtAssets(btAsset);
		            masterUpdate.setSoftwareVersion(ass.getSoftwareVersion());
		            masterUpdate.setEquipmentSrNo(ass.getEquipmentSrNo());
		            masterUpdate.setSoc(ass.getSoc());
		            masterUpdate.setSoh(ass.getSoh());

		            if (ass.getCurrentCondition() == 0)
		                masterUpdate.setCurrentCondition("Ok");
		            if (ass.getCurrentCondition() == 1)
		                masterUpdate.setCurrentCondition("Faulty");
		        }

		        masterUpdate.setBtCommissioningFSR(commFsr);
		        dbAssetMasterUpdateRepo.saveDetails(masterUpdate, user);
		    }
		} else {
		    throw new BadRequestException("Please file asset master update");
		}
		return null;
	}

}