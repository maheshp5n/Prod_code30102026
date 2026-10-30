package com.pratikshat.exicom.services.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.entity.ActiveAlarms;
import com.pratikshat.exicom.entity.BtAssetMasterUpdate;
import com.pratikshat.exicom.entity.BtAssets;
import com.pratikshat.exicom.entity.BtCase;
import com.pratikshat.exicom.entity.BtCoolingMethod;
import com.pratikshat.exicom.entity.BtFaultyAssetParameters;

import com.pratikshat.exicom.entity.BtFsr;
import com.pratikshat.exicom.entity.BtPendingReasonCustomerBucket;
import com.pratikshat.exicom.entity.BtPowerPlantMake;
import com.pratikshat.exicom.entity.BtResolutionMethod;
import com.pratikshat.exicom.entity.BtServiceAppointment;
import com.pratikshat.exicom.entity.BtSiteEnvironmentalConditions;
import com.pratikshat.exicom.entity.BtSiteType;
import com.pratikshat.exicom.entity.BtSolutionImparted;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.BtAssetMasterUpdateRequest;
import com.pratikshat.exicom.model.request.BtServiceFSRRequest;
import com.pratikshat.exicom.model.request.BtServiceFSRUpdateRequest;
import com.pratikshat.exicom.repository.ActiveAlarmsRepository;
import com.pratikshat.exicom.repository.BtAssetMasterUpdateRepository;
import com.pratikshat.exicom.repository.BtAssetsRepository;
import com.pratikshat.exicom.repository.BtCaseRepository;
import com.pratikshat.exicom.repository.BtCoolingMethodRepository;
import com.pratikshat.exicom.repository.BtFaultyAssetParametersRepository;
import com.pratikshat.exicom.repository.BtPendingReasonCustomerBucketRepository;
import com.pratikshat.exicom.repository.BtPowerPlantMakeRepository;
import com.pratikshat.exicom.repository.BtResolutionMethodRepository;
import com.pratikshat.exicom.repository.BtServiceAppointmentRepository;
import com.pratikshat.exicom.repository.BtServiceFSRRepository;
import com.pratikshat.exicom.repository.BtSiteEnvironmentalConditionsRepository;
import com.pratikshat.exicom.repository.BtSiteTypeRepository;
import com.pratikshat.exicom.repository.BtSolutionImpartedRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;
import com.pratikshat.exicom.services.BtServiceFSRService;

@Service
public class BtServiceFSRServiceImpl implements BtServiceFSRService {

	@Autowired
	BtServiceFSRRepository dbServiceFSRRepository;

	@Autowired
	BtAssetMasterUpdateRepository dbAssetMasterUpdateRepo;

	@Autowired
	BtCaseRepository btCaseRepo;

	@Autowired
	UserProfileRepository dbProfile;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	BtCaseRepository dbCaseRepo;

	@Autowired
	BtSiteTypeRepository dbSiteType;

	@Autowired
	BtSiteEnvironmentalConditionsRepository dbSiteEnvironmentalConditionsRepo;

	@Autowired
	BtCoolingMethodRepository dbCoolingMethodRepo;

	@Autowired
	BtPowerPlantMakeRepository dbPowerPlantMakeRepo;

	@Autowired
	BtSolutionImpartedRepository dbSolutionImpartedRepo;

	@Autowired
	BtResolutionMethodRepository dbResolutionMethodRepo;

	@Autowired
	BtPendingReasonCustomerBucketRepository dbPendingReasonCustomerBucketRepo;

	@Autowired
	BtServiceAppointmentRepository dbServiceAppointmentRepo;

	@Autowired
	BtAssetsRepository dbAssetsRepo;

	@Autowired
	ActiveAlarmsRepository dbActiveAlarmsRepo;

	@Autowired
	BtFaultyAssetParametersRepository dbAssetParametersRepo;

	@Autowired
	BtSiteTypeRepository dbBtSiteTypeRepo;

	@Override
	public Map<String, Object> getSingle(Object id, LoginUser user) throws BadRequestException, UnauthoriseException {
		boolean view = dbProfile.hasObjectAccess(user.getUsername(), BtFsr.class, UserProfileRepository.VIEW);
		if (view) {

		} else {
			throw new UnauthoriseException("You are not Authorised");
		}
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
	public String saveObject(BtServiceFSRRequest req, LoginUser user) throws BadRequestException, UnauthoriseException {
		BtFsr fsr = new BtFsr();

		BtServiceAppointment btServiceAppointment = dbServiceAppointmentRepo
				.findByServiceAppointmentId(req.getAppointment());
		// validate Owner of service app
		if (!btServiceAppointment.getOwnerDetails().getEmailId().equalsIgnoreCase(user.getUsername())) {
			throw new com.pratikshat.exicom.exception.UnauthoriseException(
					"Only service appointment  owner can create FSR");
		}
		if (btServiceAppointment.isFsrCreated() == true) {
			throw new BadRequestException("FSR already created on this service appointment");
		}
		if (btServiceAppointment == null)
			throw new BadRequestException("No service appointment found");
		fsr.setBtServiceAppointment(btServiceAppointment);

		BtCase btCase = btServiceAppointment.getCaseDetails();
		fsr.setBtCase(btCase);

		BtSiteEnvironmentalConditions btSiteEnvironmentalConditions = dbSiteEnvironmentalConditionsRepo
				.getBySiteEnvironmentalConditionsWithId(req.getBtSiteEnvironmentalConditions());
		if (btSiteEnvironmentalConditions == null)
			throw new BadRequestException("Site enviromental condition not found");
		fsr.setBtSiteEnvironmentalConditions(btSiteEnvironmentalConditions);

		BtCoolingMethod btCoolingMethod = dbCoolingMethodRepo.getByCoolingMethodWithId(req.getBtCoolingMethod());
		if (btCoolingMethod == null)
			throw new BadRequestException("Cooling method not found");
		fsr.setBtCoolingMethod(btCoolingMethod);

		BtPowerPlantMake btPowerPlantMake = dbPowerPlantMakeRepo.getByPowerPlantMakeWithId(req.getBtPowerPlantMake());
		if (btPowerPlantMake == null)
			throw new BadRequestException("Power plant make not found");
		fsr.setBtPowerPlantMake(btPowerPlantMake);

		BtResolutionMethod btResolutionMethod = dbResolutionMethodRepo
				.getByResolutionMethodWithId(req.getBtResolutionMethod());
		if (btResolutionMethod == null)
			throw new BadRequestException("Resolution method not found");
		fsr.setBtResolutionMethod(btResolutionMethod);

		BtSiteType btSiteType = dbSiteType.getBySitetypeWithId(req.getBtSiteType());
		if (btSiteType == null)
			throw new BadRequestException("Site type not find");
		fsr.setBtSiteType(btSiteType);

		BtSolutionImparted btSolutionImparted = dbSolutionImpartedRepo
				.getByBtSolutionImpartedWithId(req.getBtSolutionImparted());
		if (btSolutionImparted == null)
			throw new BadRequestException("Solution Imparted not found");
		fsr.setBtSolutionImparted(btSolutionImparted);

		if (req.getContactPersonAtSite() == null && req.getContactPersonAtSite().equalsIgnoreCase(""))
			throw new BadRequestException("Contact person at site is blank");
		fsr.setContactPersonAtSite(req.getContactPersonAtSite());

		if (req.getContactPersonAtSiteNo() == 0 && String.valueOf(req.getContactPersonAtSiteNo()).equalsIgnoreCase(""))
			throw new BadRequestException("Contact person at site no. is blank");
		fsr.setContactPersonAtSiteContactNo(req.getContactPersonAtSiteNo());
		System.out.println(req.getDateOfSiteVisit() + " Date ");
		if (req.getDateOfSiteVisit() == null && req.getDateOfSiteVisit().toString().equalsIgnoreCase(""))
			throw new BadRequestException("Date of site visit is blank");
		fsr.setDateOfSiteVisit(req.getDateOfSiteVisit());

		if (req.getEarthigStatus() == 0 && String.valueOf(req.getEarthigStatus()).equalsIgnoreCase(""))
			throw new BadRequestException("Earthing status is blank");
		fsr.setEarthingStatus(req.getEarthigStatus());

		if (req.getAmbientTemp() == 0 && String.valueOf(req.getAmbientTemp()).equalsIgnoreCase(""))
			throw new BadRequestException("Ambient temp is blank");
		fsr.setAmbientTemperature(req.getAmbientTemp());

		if (req.getTotalEquipmentDcLoad() == 0 && String.valueOf(req.getTotalEquipmentDcLoad()).equalsIgnoreCase(""))
			throw new BadRequestException("Total equipment DC load is blank");
		fsr.setTotalEquipmentDCLoad(req.getTotalEquipmentDcLoad());

		if (req.getActiveRectifiers() == 0 && String.valueOf(req.getActiveRectifiers()).equalsIgnoreCase(""))
			throw new BadRequestException("Active rectifiers is blank");
		fsr.setActiveRectifiers(req.getActiveRectifiers());

		if (req.getSmpsToCbmsVolGap() == 0 && String.valueOf(req.getSmpsToCbmsVolGap()).equalsIgnoreCase(""))
			throw new BadRequestException("SMPS to CBMS vol gap is blank");
		fsr.setSmpsToCbmsVolGap(req.getSmpsToCbmsVolGap());

		if (req.getCbmsToBatVolGap() == 0 && String.valueOf(req.getCbmsToBatVolGap()).equalsIgnoreCase(""))
			throw new BadRequestException("CBMS to BAT vol gap is blank");
		fsr.setCbmsToBatVolGap(req.getCbmsToBatVolGap());

		if (req.getDcLoadCurrent() == 0 && String.valueOf(req.getDcLoadCurrent()).equalsIgnoreCase(""))
			throw new BadRequestException("DC load current is blank");
		fsr.setDcLoadCurrent(req.getDcLoadCurrent());

		if (req.getMaxModuleTemp() == 0 && String.valueOf(req.getMaxModuleTemp()).equalsIgnoreCase(""))
			throw new BadRequestException("Max module temp is blank");
		fsr.setMaxModuleTemp(req.getMaxModuleTemp());

		if (req.getFaultObservedByExicomEng() == null && req.getFaultObservedByExicomEng().equalsIgnoreCase(""))
			throw new BadRequestException("Fault observed by exicom eng is blank");
		fsr.setFaultObservedByExicomEng(req.getFaultObservedByExicomEng());

		if (req.getCorrectiveActionInDetails() == null && req.getCorrectiveActionInDetails().equalsIgnoreCase(""))
			throw new BadRequestException("Corrective action in details is blank");
		fsr.setCorrectiveActionsInDetails(req.getCorrectiveActionInDetails());

		if (req.getCaseStatus() == null || req.getCaseStatus().equalsIgnoreCase(""))
			throw new BadRequestException("Case status is blank");
		fsr.setCaseStatus(req.getCaseStatus());

		if (req.getCaseStatus().toLowerCase().equalsIgnoreCase("completed")) {
			fsr.setPendingBucket(" ");
			fsr.setPendingReasonExicomBucket(" ");
		} else if (req.getCaseStatus().toLowerCase().equalsIgnoreCase("incompleted")) {
			if (req.getPendingBucket() == null || req.getPendingBucket().equalsIgnoreCase(""))
				throw new BadRequestException("Please fill pending bucket");
			fsr.setPendingBucket(req.getPendingBucket());
			if (req.getPendingBucket().toLowerCase().contains("customer")) {

				BtPendingReasonCustomerBucket btPendingReasonCustomerBucket = dbPendingReasonCustomerBucketRepo
						.getByPendingReasonCustomerWithId(req.getBtPendingReasonCustomerBucket());
				if (btPendingReasonCustomerBucket == null
						|| btPendingReasonCustomerBucket.toString().equalsIgnoreCase(""))
					throw new BadRequestException("Pending Reason customer bucket not found");
				fsr.setBtPendingReasonCustomerBucket(btPendingReasonCustomerBucket);

			}
			if (req.getPendingBucket().toLowerCase().contains("exicom")) {

				if (req.getPendingReasonExicomBucket() == null
						|| req.getPendingReasonExicomBucket().equalsIgnoreCase(""))
					throw new BadRequestException("Pending reason exicom bucket is blank");
				fsr.setPendingReasonExicomBucket(req.getPendingReasonExicomBucket());

			}
		} else {
			throw new BadRequestException("Comm status should be completed on incompleted");
		}

		if (req.getComplainAttendByEngName() == null && req.getComplainAttendByEngName().equalsIgnoreCase(""))
			throw new BadRequestException("Complain attend by eng name is blank");
		fsr.setComplainAttendByEngName(req.getComplainAttendByEngName());

		if (req.getEngNumber() == 0 && String.valueOf(req.getEngNumber()).equalsIgnoreCase(""))
			throw new BadRequestException("Eng name is blank");
		fsr.setEngineerNo(req.getEngNumber());

		if (req.getCustomerRemark() == null && req.getCustomerRemark().equalsIgnoreCase(""))
			throw new BadRequestException("Customer remark is blank");
		fsr.setCustomerRemarks(req.getCustomerRemark());
		
		if (req.getObservation1() == null && req.getObservation1().equalsIgnoreCase(""))
			throw new BadRequestException("Observation1 is blank");
		fsr.setObservation1(req.getObservation1());
		
		if (req.getObservation2() == null && req.getObservation2().equalsIgnoreCase(""))
			throw new BadRequestException("Observation2 is blank");
		fsr.setObservation2(req.getObservation2());
		
		if (req.getObservation3() == null && req.getObservation3().equalsIgnoreCase(""))
			throw new BadRequestException("Observation3 is blank");
		fsr.setObservation3(req.getObservation3());
		
		if (req.getObservation4() == null && req.getObservation4().equalsIgnoreCase(""))
			throw new BadRequestException("Observation4 is blank");
		fsr.setObservation4(req.getObservation4());

		fsr.setOwner(new EmployeeDetails(user.getUsername()));
		btServiceAppointment.setFsrCreated(true);
		dbServiceAppointmentRepo.updateDetails(btServiceAppointment, user);
		dbServiceFSRRepository.saveDetails(fsr, user);

		if (req.getAsseRequests().size() >= 2) {

			for (BtAssetMasterUpdateRequest am : req.getAsseRequests()) {
				BtAssetMasterUpdate assetMasterUpdate = new BtAssetMasterUpdate();

				assetMasterUpdate.setBtFsr(fsr);
				assetMasterUpdate.setSrNo(am.getSrNo());
				if (am.getEquipmentType() == null && am.getEquipmentType().equalsIgnoreCase(""))
					throw new BadRequestException("Equipment type is blank");
				assetMasterUpdate.setEquipmentType(am.getEquipmentType());

				String equipmentType = am.getEquipmentType();
				String equipmentSrNo = am.getEquipmentSrNo();

				/* CBMS NA handling */
				if (equipmentType != null && equipmentType.toUpperCase().contains("CBMS")
				        && "NA".equalsIgnoreCase(equipmentSrNo)) {

				    assetMasterUpdate.setBtAssets(null);   // No asset mapping
				} 
				else {

				    if (am.getBtAsset() == null || am.getBtAsset() == 0) {
				        throw new BadRequestException("Product not found for " + equipmentType);
				    }

				    BtAssets btAssets = dbAssetsRepo.getWithId(am.getBtAsset());

				    if (btAssets == null) {
				        throw new BadRequestException("Asset not found for id " + am.getBtAsset());
				    }

				    assetMasterUpdate.setBtAssets(btAssets);
				}

				if (am.getEquipmentSrNo() == null && am.getEquipmentSrNo().equalsIgnoreCase(""))
					throw new BadRequestException("Equipment Sr No. is blank");
				assetMasterUpdate.setEquipmentSrNo(am.getEquipmentSrNo());

				if (am.getSoftwareVersion() == null || am.getSoftwareVersion().trim().isEmpty() || "0".equalsIgnoreCase(am.getSoftwareVersion())) {
				    // For CBMS with NA serial, allow blank software version
				    if (am.getEquipmentType() != null && am.getEquipmentType().toUpperCase().contains("CBMS")
				            && "NA".equalsIgnoreCase(am.getEquipmentSrNo())) {
				        assetMasterUpdate.setSoftwareVersion("NA");
				    } else {
				        throw new BadRequestException("Software version is blank");
				    }
				} else {
				    assetMasterUpdate.setSoftwareVersion(am.getSoftwareVersion());
				}

//				if (am.getWarrantyStatus() == null && am.getWarrantyStatus().equalsIgnoreCase(""))
//					throw new BadRequestException("Warranty status is blank");
//				assetMasterUpdate.setWarrantyStatus(am.getWarrantyStatus());
//				List<Integer> status= new ArrayList<>();
//				status.add(0);//ok
//				status.add(1);//faulty
//			    assetMasterUpdate.setInstallationDate(btProducts);

				if (am.getCurrentCondition() == null && am.getCurrentCondition().equalsIgnoreCase(""))
					throw new BadRequestException("Current condition is blank");
				if (am.getCurrentCondition().contains("0")) {
					assetMasterUpdate.setCurrentCondition("Ok");
				} else {
					assetMasterUpdate.setCurrentCondition("Faulty");
				}

				dbAssetMasterUpdateRepo.saveDetails(assetMasterUpdate, user);

				BtFaultyAssetParameters assets = new BtFaultyAssetParameters();
				if (am.getCurrentCondition().contains("1")) {
					int count = 0;
					if (count < 1) {

						if (am.getFlag() == 1) {
							assets.setBtFsr(fsr);

							if (am.getEquipmentName() == null)
								throw new BadRequestException("Equipment name is blank");
							assets.setEquipmentName(am.getEquipmentName());

							if (am.getVoltage() == 0 && String.valueOf(am.getVoltage()).equalsIgnoreCase(""))
								throw new BadRequestException("Voltage is blank");
							assets.setVoltage(am.getVoltage());

							if (am.getSoc() == 0 && String.valueOf(am.getSoc()).equalsIgnoreCase(""))
								throw new BadRequestException("SOC is blank");
							assets.setSoc(am.getSoc());

							if (am.getSoh() == 0 && String.valueOf(am.getSoh()).equalsIgnoreCase(""))
								throw new BadRequestException("SOH is blank");
							assets.setSoh(am.getSoh());

							ActiveAlarms activeAlarms = dbActiveAlarmsRepo.getOne(am.getActiveAlarms());
							if (am.getActiveAlarms() == 0 && String.valueOf(am.getActiveAlarms()).equalsIgnoreCase(""))
								throw new BadRequestException("Active alarm not found");
							assets.setActiveAlarms(activeAlarms);

							if (am.getProblemSince() == null && am.getProblemSince().toString().equalsIgnoreCase(""))
								throw new BadRequestException("Problem since is blank");
							assets.setProblemSince(am.getProblemSince());

							if (am.getMinCellVoltage() == 0
									&& String.valueOf(am.getMinCellVoltage()).equalsIgnoreCase(""))
								throw new BadRequestException("Min cell voltage is blank");
							assets.setMinCellVoltage(am.getMinCellVoltage());

							if (am.getMaxCellVoltage() == 0
									&& String.valueOf(am.getMaxCellVoltage()).equalsIgnoreCase(""))
								throw new BadRequestException("Max cell voltage is blank");
							assets.setMaxCellVoltage(am.getMaxCellVoltage());

							if (am.getVolGap() == 0 && String.valueOf(am.getVolGap()).equalsIgnoreCase(""))
								throw new BadRequestException("Vol gap is blank");
							assets.setVolGap(am.getVolGap());

							if (am.getBatteryTemp() == 0 && String.valueOf(am.getBatteryTemp()).equalsIgnoreCase(""))
								throw new BadRequestException("Battery temp is blank");
							assets.setBatteryTemp(am.getBatteryTemp());

							if (am.getLastCycleDel() == 0 && String.valueOf(am.getLastCycleDel()).equalsIgnoreCase(""))
								throw new BadRequestException("Last cycle del is blank");
							assets.setLastCycleDel(am.getLastCycleDel());

							if (am.getLogStatus() == null && am.getLogStatus().equalsIgnoreCase(""))
								throw new BadRequestException("Log Status is blank");
							assets.setLogStatus(am.getLogStatus());

							if (am.getBatteryRequiredAtBRC() == null
									&& am.getBatteryRequiredAtBRC().equalsIgnoreCase(""))
								throw new BadRequestException("Battery required at BRC is blank");
							assets.setBatteryRequiredAtBRC(am.getBatteryRequiredAtBRC());

							dbAssetParametersRepo.saveDetails(assets, user);
							count++;

						}
					}
				}

			}
		} else {
			throw new BadRequestException("Please file asset master update");
		}

		return "{}";
	}

	@Override
	@Transactional(rollbackFor = { BadRequestException.class, UnauthoriseException.class,
			Exception.class }, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED)
	public String updateObject(BtServiceFSRUpdateRequest req, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		BtFsr fsr = dbServiceFSRRepository.findById(req.getServiceFSRId()).get();

		if (fsr == null) {
			throw new BadRequestException("Service FSR not created");
		}

		BtServiceAppointment btServiceAppointment = dbServiceAppointmentRepo
				.findByServiceAppointmentId(fsr.getBtServiceAppointment().getId());
		// validate Owner of service app
		if (!btServiceAppointment.getOwnerDetails().getEmailId().equalsIgnoreCase(user.getUsername())) {
			throw new com.pratikshat.exicom.exception.UnauthoriseException(
					"Only service appointment  owner can create FSR");
		}
		if (btServiceAppointment == null)
			throw new BadRequestException("No service appointment found");

		if (btServiceAppointment.isFsrCreated() != true) {
			throw new BadRequestException("FSR not created on this service appointment");
		}

		fsr.setBtServiceAppointment(btServiceAppointment);

		BtCase btCase = btServiceAppointment.getCaseDetails();
		fsr.setBtCase(btCase);

		BtSiteEnvironmentalConditions btSiteEnvironmentalConditions = dbSiteEnvironmentalConditionsRepo
				.getBySiteEnvironmentalConditionsWithId(req.getBtSiteEnvironmentalConditions());
		if (btSiteEnvironmentalConditions == null)
			throw new BadRequestException("Site enviromental condition not found");
		fsr.setBtSiteEnvironmentalConditions(btSiteEnvironmentalConditions);

		BtCoolingMethod btCoolingMethod = dbCoolingMethodRepo.getByCoolingMethodWithId(req.getBtCoolingMethod());
		if (btCoolingMethod == null)
			throw new BadRequestException("Cooling method not found");
		fsr.setBtCoolingMethod(btCoolingMethod);

		BtPowerPlantMake btPowerPlantMake = dbPowerPlantMakeRepo.getByPowerPlantMakeWithId(req.getBtPowerPlantMake());
		if (btPowerPlantMake == null)
			throw new BadRequestException("Power plant make not found");
		fsr.setBtPowerPlantMake(btPowerPlantMake);

		BtResolutionMethod btResolutionMethod = dbResolutionMethodRepo
				.getByResolutionMethodWithId(req.getBtResolutionMethod());
		if (btResolutionMethod == null)
			throw new BadRequestException("Resolution method not found");
		fsr.setBtResolutionMethod(btResolutionMethod);

		BtSiteType btSiteType = dbSiteType.getBySitetypeWithId(req.getBtSiteType());
		if (btSiteType == null)
			throw new BadRequestException("Site type not find");
		fsr.setBtSiteType(btSiteType);

		BtSolutionImparted btSolutionImparted = dbSolutionImpartedRepo
				.getByBtSolutionImpartedWithId(req.getBtSolutionImparted());
		if (btSolutionImparted == null)
			throw new BadRequestException("Solution Imparted not found");
		fsr.setBtSolutionImparted(btSolutionImparted);

		if (req.getContactPersonAtSite() == null && req.getContactPersonAtSite().equalsIgnoreCase(""))
			throw new BadRequestException("Contact person at site is blank");
		fsr.setContactPersonAtSite(req.getContactPersonAtSite());

		if (req.getContactPersonAtSiteNo() == 0 && String.valueOf(req.getContactPersonAtSiteNo()).equalsIgnoreCase(""))
			throw new BadRequestException("Contact person at site no. is blank");
		fsr.setContactPersonAtSiteContactNo(req.getContactPersonAtSiteNo());
		System.out.println(req.getDateOfSiteVisit() + " Date ");
		if (req.getDateOfSiteVisit() == null && req.getDateOfSiteVisit().toString().equalsIgnoreCase(""))
			throw new BadRequestException("Date of site visit is blank");
		fsr.setDateOfSiteVisit(req.getDateOfSiteVisit());

		if (req.getEarthigStatus() == 0 && String.valueOf(req.getEarthigStatus()).equalsIgnoreCase(""))
			throw new BadRequestException("Earthing status is blank");
		fsr.setEarthingStatus(req.getEarthigStatus());

		if (req.getAmbientTemp() == 0 && String.valueOf(req.getAmbientTemp()).equalsIgnoreCase(""))
			throw new BadRequestException("Ambient temp is blank");
		fsr.setAmbientTemperature(req.getAmbientTemp());

		if (req.getTotalEquipmentDcLoad() == 0 && String.valueOf(req.getTotalEquipmentDcLoad()).equalsIgnoreCase(""))
			throw new BadRequestException("Total equipment DC load is blank");
		fsr.setTotalEquipmentDCLoad(req.getTotalEquipmentDcLoad());

		if (req.getActiveRectifiers() == 0 && String.valueOf(req.getActiveRectifiers()).equalsIgnoreCase(""))
			throw new BadRequestException("Active rectifiers is blank");
		fsr.setActiveRectifiers(req.getActiveRectifiers());

		if (req.getSmpsToCbmsVolGap() == 0 && String.valueOf(req.getSmpsToCbmsVolGap()).equalsIgnoreCase(""))
			throw new BadRequestException("SMPS to CBMS vol gap is blank");
		fsr.setSmpsToCbmsVolGap(req.getSmpsToCbmsVolGap());

		if (req.getCbmsToBatVolGap() == 0 && String.valueOf(req.getCbmsToBatVolGap()).equalsIgnoreCase(""))
			throw new BadRequestException("CBMS to BAT vol gap is blank");
		fsr.setCbmsToBatVolGap(req.getCbmsToBatVolGap());

		if (req.getDcLoadCurrent() == 0 && String.valueOf(req.getDcLoadCurrent()).equalsIgnoreCase(""))
			throw new BadRequestException("DC load current is blank");
		fsr.setDcLoadCurrent(req.getDcLoadCurrent());

		if (req.getMaxModuleTemp() == 0 && String.valueOf(req.getMaxModuleTemp()).equalsIgnoreCase(""))
			throw new BadRequestException("Max module temp is blank");
		fsr.setMaxModuleTemp(req.getMaxModuleTemp());

		if (req.getFaultObservedByExicomEng() == null && req.getFaultObservedByExicomEng().equalsIgnoreCase(""))
			throw new BadRequestException("Fault observed by exicom eng is blank");
		fsr.setFaultObservedByExicomEng(req.getFaultObservedByExicomEng());

		if (req.getCorrectiveActionInDetails() == null && req.getCorrectiveActionInDetails().equalsIgnoreCase(""))
			throw new BadRequestException("Corrective action in details is blank");
		fsr.setCorrectiveActionsInDetails(req.getCorrectiveActionInDetails());

		if (req.getCaseStatus() == null || req.getCaseStatus().equalsIgnoreCase(""))
			throw new BadRequestException("Case status is blank");
		fsr.setCaseStatus(req.getCaseStatus());

		if (req.getCaseStatus().toLowerCase().equalsIgnoreCase("completed")) {
			fsr.setPendingBucket(" ");
			fsr.setPendingReasonExicomBucket(" ");
		} else if (req.getCaseStatus().toLowerCase().equalsIgnoreCase("incompleted")) {
			if (req.getPendingBucket() == null || req.getPendingBucket().equalsIgnoreCase(""))
				throw new BadRequestException("Please fill pending bucket");
			fsr.setPendingBucket(req.getPendingBucket());
			if (req.getPendingBucket().toLowerCase().contains("customer")) {

				BtPendingReasonCustomerBucket btPendingReasonCustomerBucket = dbPendingReasonCustomerBucketRepo
						.getByPendingReasonCustomerWithId(req.getBtPendingReasonCustomerBucket());
				if (btPendingReasonCustomerBucket == null
						|| btPendingReasonCustomerBucket.toString().equalsIgnoreCase(""))
					throw new BadRequestException("Pending Reason customer bucket not found");
				fsr.setBtPendingReasonCustomerBucket(btPendingReasonCustomerBucket);

			}
			if (req.getPendingBucket().toLowerCase().contains("exicom")) {

				if (req.getPendingReasonExicomBucket() == null
						|| req.getPendingReasonExicomBucket().equalsIgnoreCase(""))
					throw new BadRequestException("Pending reason exicom bucket is blank");
				fsr.setPendingReasonExicomBucket(req.getPendingReasonExicomBucket());

			}
		} else {
			throw new BadRequestException("Comm status should be completed on incompleted");
		}

		if (req.getComplainAttendByEngName() == null && req.getComplainAttendByEngName().equalsIgnoreCase(""))
			throw new BadRequestException("Complain attend by eng name is blank");
		fsr.setComplainAttendByEngName(req.getComplainAttendByEngName());

		if (req.getEngNumber() == 0 && String.valueOf(req.getEngNumber()).equalsIgnoreCase(""))
			throw new BadRequestException("Eng name is blank");
		fsr.setEngineerNo(req.getEngNumber());

		if (req.getCustomerRemark() == null && req.getCustomerRemark().equalsIgnoreCase(""))
			throw new BadRequestException("Customer remark is blank");
		fsr.setCustomerRemarks(req.getCustomerRemark());
		
		if (req.getObservation1() == null && req.getObservation1().equalsIgnoreCase(""))
			throw new BadRequestException("Observation1 is blank");
		fsr.setObservation1(req.getObservation1());
		
		if (req.getObservation2() == null && req.getObservation2().equalsIgnoreCase(""))
			throw new BadRequestException("Observation2 is blank");
		fsr.setObservation2(req.getObservation2());
		
		if (req.getObservation3() == null && req.getObservation3().equalsIgnoreCase(""))
			throw new BadRequestException("Observation3 is blank");
		fsr.setObservation3(req.getObservation3());
		
		if (req.getObservation4() == null && req.getObservation4().equalsIgnoreCase(""))
			throw new BadRequestException("Observation4 is blank");
		fsr.setObservation4(req.getObservation4());

		fsr.setOwner(new EmployeeDetails(user.getUsername()));
		btServiceAppointment.setFsrCreated(true);

		dbServiceAppointmentRepo.updateDetails(btServiceAppointment, user);

		dbServiceFSRRepository.updateDetails(fsr, user);

		if (req.getAsseRequests().size() >= 2) {

			//////////////////// Delete Asset Master Update////////////////////////
			dbAssetMasterUpdateRepo.deleteByCommFSRId(req.getServiceFSRId());
			//////////////////// Delete Faulty Asset Parameter////////////////////////
			long faultyAssetParameter = dbAssetParametersRepo.getFaultyAssetParameterCoumt(req.getServiceFSRId());
			System.out.println(faultyAssetParameter + " Count");
			if (faultyAssetParameter != 0)
				dbAssetParametersRepo.deleteByCommFSRId(req.getServiceFSRId());

			for (BtAssetMasterUpdateRequest am : req.getAsseRequests()) {
				BtAssetMasterUpdate assetMasterUpdate = new BtAssetMasterUpdate();

				assetMasterUpdate.setBtFsr(fsr);
				assetMasterUpdate.setSrNo(am.getSrNo());
				if (am.getEquipmentType() == null && am.getEquipmentType().equalsIgnoreCase(""))
					throw new BadRequestException("Equipment type is blank");
				assetMasterUpdate.setEquipmentType(am.getEquipmentType());

//				BtAssets btAssets = dbAssetsRepo.getWithId(am.getBtAsset());
//
//				if (am.getBtAsset() == 0 && String.valueOf(am.getBtAsset()).equalsIgnoreCase(""))
//					throw new BadRequestException("Product not found");
//				assetMasterUpdate.setBtAssets(btAssets);
//
//				if (am.getEquipmentSrNo() == null && am.getEquipmentSrNo().equalsIgnoreCase(""))
//					throw new BadRequestException("Equipment Sr No. is blank");
//				assetMasterUpdate.setEquipmentSrNo(am.getEquipmentSrNo());
//
//				if (am.getSoftwareVersion().equalsIgnoreCase("0") && String.valueOf(am.getSoftwareVersion()).equalsIgnoreCase(""))
//					throw new BadRequestException("Software version is blank");
//				assetMasterUpdate.setSoftwareVersion(am.getSoftwareVersion());
//
////			if (am.getWarrantyStatus() == null && am.getWarrantyStatus().equalsIgnoreCase(""))
////					throw new BadRequestException("Warranty status is blank");
////				assetMasterUpdate.setWarrantyStatus(am.getWarrantyStatus());
////				List<Integer> status= new ArrayList<>();
////				status.add(0);//ok
////				status.add(1);//faulty
////			    assetMasterUpdate.setInstallationDate(btProducts);
//
//				if (am.getCurrentCondition() == null || am.getCurrentCondition().equalsIgnoreCase(""))
//					throw new BadRequestException("Current condition is blank");
//				if (am.getCurrentCondition().contains("0")) {
//					assetMasterUpdate.setCurrentCondition("Ok");
//				} else {
//					assetMasterUpdate.setCurrentCondition("Faulty");
//				}
//
//				dbAssetMasterUpdateRepo.saveDetails(assetMasterUpdate, user);
				
				String equipmentType = am.getEquipmentType();
				boolean isCbmsNA = "CBMS".equalsIgnoreCase(equipmentType) 
				&& "NA".equalsIgnoreCase(am.getEquipmentSrNo()) ;

				if(isCbmsNA){
				assetMasterUpdate.setBtAssets(null);
				assetMasterUpdate.setSoftwareVersion("NA");
				assetMasterUpdate.setEquipmentSrNo("NA");
				
				if(am.getCurrentCondition() != null && am.getCurrentCondition().contains("0"))
					assetMasterUpdate.setCurrentCondition("Ok");
				else
					assetMasterUpdate.setCurrentCondition("Faulty");

				}

				else {
				BtAssets btAssets = dbAssetsRepo.getWithId(am.getBtAsset());

								if (am.getBtAsset() == 0 && String.valueOf(am.getBtAsset()).equalsIgnoreCase(""))
									throw new BadRequestException("Product not found");
								assetMasterUpdate.setBtAssets(btAssets);

								if (am.getEquipmentSrNo() == null && am.getEquipmentSrNo().equalsIgnoreCase(""))
									throw new BadRequestException("Equipment Sr No. is blank");
								assetMasterUpdate.setEquipmentSrNo(am.getEquipmentSrNo());

								if (am.getSoftwareVersion().equalsIgnoreCase("0") && String.valueOf(am.getSoftwareVersion()).equalsIgnoreCase(""))
									throw new BadRequestException("Software version is blank");
								assetMasterUpdate.setSoftwareVersion(am.getSoftwareVersion());
								
								if (am.getCurrentCondition() == null || am.getCurrentCondition().equalsIgnoreCase(""))
									throw new BadRequestException("Current condition is blank");
								if (am.getCurrentCondition().contains("0")) {
									assetMasterUpdate.setCurrentCondition("Ok");
								} else {
									assetMasterUpdate.setCurrentCondition("Faulty");
								}
				}

				dbAssetMasterUpdateRepo.saveDetails(assetMasterUpdate, user);

				BtFaultyAssetParameters assets = new BtFaultyAssetParameters();
				if (!isCbmsNA && am.getCurrentCondition().contains("1")) {
					int count = 0;
					if (count < 1) {

						if (am.getFlag() == 1) {
							assets.setBtFsr(fsr);

							if (am.getEquipmentName() == null)
								throw new BadRequestException("Equipment name is blank");
							assets.setEquipmentName(am.getEquipmentName());

							if (am.getVoltage() == 0 && String.valueOf(am.getVoltage()).equalsIgnoreCase(""))
								throw new BadRequestException("Voltage is blank");
							assets.setVoltage(am.getVoltage());

							if (am.getSoc() == 0 && String.valueOf(am.getSoc()).equalsIgnoreCase(""))
								throw new BadRequestException("SOC is blank");
							assets.setSoc(am.getSoc());

							if (am.getSoh() == 0 && String.valueOf(am.getSoh()).equalsIgnoreCase(""))
								throw new BadRequestException("SOH is blank");
							assets.setSoh(am.getSoh());

							ActiveAlarms activeAlarms = dbActiveAlarmsRepo.getOne(am.getActiveAlarms());
							if (am.getActiveAlarms() == 0 && String.valueOf(am.getActiveAlarms()).equalsIgnoreCase(""))
								throw new BadRequestException("Active alarm not found");
							assets.setActiveAlarms(activeAlarms);

							if (am.getProblemSince() == null && am.getProblemSince().toString().equalsIgnoreCase(""))
								throw new BadRequestException("Problem since is blank");
							assets.setProblemSince(am.getProblemSince());

							if (am.getMinCellVoltage() == 0
									&& String.valueOf(am.getMinCellVoltage()).equalsIgnoreCase(""))
								throw new BadRequestException("Min cell voltage is blank");
							assets.setMinCellVoltage(am.getMinCellVoltage());

							if (am.getMaxCellVoltage() == 0
									&& String.valueOf(am.getMaxCellVoltage()).equalsIgnoreCase(""))
								throw new BadRequestException("Max cell voltage is blank");
							assets.setMaxCellVoltage(am.getMaxCellVoltage());

							if (am.getVolGap() == 0 && String.valueOf(am.getVolGap()).equalsIgnoreCase(""))
								throw new BadRequestException("Vol gap is blank");
							assets.setVolGap(am.getVolGap());

							if (am.getBatteryTemp() == 0 && String.valueOf(am.getBatteryTemp()).equalsIgnoreCase(""))
								throw new BadRequestException("Battery temp is blank");
							assets.setBatteryTemp(am.getBatteryTemp());

							if (am.getLastCycleDel() == 0 && String.valueOf(am.getLastCycleDel()).equalsIgnoreCase(""))
								throw new BadRequestException("Last cycle del is blank");
							assets.setLastCycleDel(am.getLastCycleDel());

							if (am.getLogStatus() == null && am.getLogStatus().equalsIgnoreCase(""))
								throw new BadRequestException("Log Status is blank");
							assets.setLogStatus(am.getLogStatus());

							if (am.getBatteryRequiredAtBRC() == null
									&& am.getBatteryRequiredAtBRC().equalsIgnoreCase(""))
								throw new BadRequestException("Battery required at BRC is blank");
							assets.setBatteryRequiredAtBRC(am.getBatteryRequiredAtBRC());

							dbAssetParametersRepo.saveDetails(assets, user);
							count++;

						}
					}
				}

			}
		} else {
			throw new BadRequestException("Please file asset master update");
		}

		return "{}";
	}

}