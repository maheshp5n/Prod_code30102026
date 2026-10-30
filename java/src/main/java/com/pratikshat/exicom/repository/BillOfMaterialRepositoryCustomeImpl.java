package com.pratikshat.exicom.repository;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.pratikshat.exicom.entity.BillOfMaterial;
import com.pratikshat.exicom.entity.BillOfMaterialDesignRef;
import com.pratikshat.exicom.entity.BillOfMaterialLocation;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.Products;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.BillOfMaterialRequest;

@Repository
public class BillOfMaterialRepositoryCustomeImpl implements BillOfMaterialRepositoryCustome {
	@PersistenceContext
	EntityManager em;

	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED,propagation = Propagation.REQUIRES_NEW,rollbackFor = {RuntimeException.class,BadRequestException.class,UnauthoriseException.class,Exception.class})
	public void saveBillOfMaterial(String partCode, java.util.List<BillOfMaterialRequest> lstreq, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		Date toDAte=new Date();
		EmployeeDetails currentUser=new EmployeeDetails(user.getUsername());
		
		long count=em.createQuery("select count(bm) from BillOfMaterial bm where bm.fProduct=?1",Long.class).setParameter(1, new Products(partCode)).getSingleResult();
		if(count>0) {
			throw new BadRequestException(String.format("BOM for Product part code %s already exists",partCode));
		}
		if(lstreq==null||lstreq.size()==0) throw new BadRequestException("List of raw/semi finished material not found");
		for(BillOfMaterialRequest req:lstreq) {
			
			if(partCode.equalsIgnoreCase(req.getProductPartCode())) throw new BadRequestException("Finished part code can not have same raw part code");
			int rq=(int)req.getRequiredQuantity(); // req.getRequiredQuantity();
			if(rq!=req.getLocationName().size()) throw new BadRequestException("Please provide all location");
			long counter=em.createQuery("select count(bom) from BillOfMaterial bom where bom.fProduct=?1 and bom.rProduct=?2",Long.class).setParameter(1, new Products(partCode)).setParameter(2, new Products(req.getProductPartCode())).getSingleResult();
			if(counter>0) throw new BadRequestException(String.format("Product part code %s already added to BOM", req.getProductPartCode()));
			
			BillOfMaterial bom=new BillOfMaterial();
			bom.setCreatedBy(currentUser);
			bom.setCreatedDate(toDAte);
			bom.setfProduct(new Products(partCode));
			bom.setModifiedBy(currentUser);
			bom.setModifiedDate(toDAte);
			bom.setOwner(currentUser);
			bom.setReqQty(req.getRequiredQuantity());
			bom.setrProduct(new Products(req.getProductPartCode()));
			em.persist(bom);
			for(String location:req.getLocationName()) {
				BillOfMaterialLocation _loc=new BillOfMaterialLocation();
				_loc.setBom(bom);
				_loc.setCreatedBy(currentUser);
				_loc.setCreatedDate(toDAte);
				_loc.setLocationName(location);
				_loc.setModifiedBy(currentUser);
				_loc.setModifiedDate(toDAte);
				em.persist(_loc);
			}
			if(req.getDrawaingRef()!=null&&req.getDrawaingRef().size()>0) {
				for(String drae:req.getDrawaingRef()) {
					BillOfMaterialDesignRef _dref=new BillOfMaterialDesignRef();
					_dref.setBillOfMaterialDesign(drae);
					_dref.setBom(bom);
					_dref.setCreatedBy(currentUser);
					_dref.setCreatedDate(toDAte);
					_dref.setModifiedBy(currentUser);
					_dref.setModifiedDate(toDAte);
					em.persist(_dref);
				}
			}
		}
	}

	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED,propagation = Propagation.REQUIRES_NEW,rollbackFor = {RuntimeException.class,BadRequestException.class,UnauthoriseException.class,Exception.class})
	public void addProductToBillOfMaterial(String partCode, BillOfMaterialRequest req, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		Date toDAte=new Date();
		EmployeeDetails currentUser=new EmployeeDetails(user.getUsername());
		
		if(req.getRequiredQuantity()!=req.getLocationName().size()) throw new BadRequestException("Please provide all location");
		
		long counter=em.createQuery("select count(bom) from BillOfMaterial bom where bom.fProduct=?1 and bom.rProduct=?2",Long.class).setParameter(1, new Products(partCode)).setParameter(2, new Products(req.getProductPartCode())).getSingleResult();
		if(counter>0) throw new BadRequestException(String.format("Product part code %s already added to BOM", req.getProductPartCode()));
		BillOfMaterial bom=new BillOfMaterial();
		bom.setCreatedBy(currentUser);
		bom.setCreatedDate(toDAte);
		bom.setfProduct(new Products(partCode));
		bom.setModifiedBy(currentUser);
		bom.setModifiedDate(toDAte);
		bom.setOwner(currentUser);
		bom.setReqQty(req.getRequiredQuantity());
		bom.setrProduct(new Products(req.getProductPartCode()));
		em.persist(bom);
		for(String location:req.getLocationName()) {
			BillOfMaterialLocation _loc=new BillOfMaterialLocation();
			_loc.setBom(bom);
			_loc.setCreatedBy(currentUser);
			_loc.setCreatedDate(toDAte);
			_loc.setLocationName(location);
			_loc.setModifiedBy(currentUser);
			_loc.setModifiedDate(toDAte);
			em.persist(_loc);
		}
		if(req.getDrawaingRef()!=null&&req.getDrawaingRef().size()>0) {
			for(String drae:req.getDrawaingRef()) {
				BillOfMaterialDesignRef _dref=new BillOfMaterialDesignRef();
				_dref.setBillOfMaterialDesign(drae);
				_dref.setBom(bom);
				_dref.setCreatedBy(currentUser);
				_dref.setCreatedDate(toDAte);
				_dref.setModifiedBy(currentUser);
				_dref.setModifiedDate(toDAte);
				em.persist(_dref);
			}
		}
	}

	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED,propagation = Propagation.REQUIRES_NEW,rollbackFor = {RuntimeException.class,BadRequestException.class,UnauthoriseException.class,Exception.class})
	public void uploadExcelFile(MultipartFile file, String fPartCode, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		EmployeeDetails emp=new EmployeeDetails(user.getUsername());
		Date today=new Date();
		
		if(file==null||fPartCode==null) {
			throw new BadRequestException("File of Finished Part not found");
		}
		
		if(file.isEmpty())
			throw new BadRequestException("File is empty");
		Workbook wb;
		try {
			wb = new XSSFWorkbook(file.getInputStream());
			Sheet sheet=wb.getSheetAt(0);
			DataFormatter formatter=new DataFormatter();
			Iterator<Row> rows=sheet.iterator();
			rows.next();
			while(rows.hasNext()) {
				Row drow=rows.next();
				
				String level= formatter.formatCellValue(drow.getCell(0));
				String rpartcode=formatter.formatCellValue(drow.getCell(1)).trim();
				float quantity=Float.parseFloat(formatter.formatCellValue(drow.getCell(3)));
				BillOfMaterial bom=null;
				try {
					bom=em.createQuery("select bom from BillOfMaterial bom where bom.fProduct=?1 and bom.rProduct=?2",BillOfMaterial.class)
							.setParameter(1, new Products(fPartCode))
							.setParameter(2, rpartcode)
							.getSingleResult();
				}catch(Exception ex) {
					
				}
				if(bom==null) {
					bom=new BillOfMaterial();
					bom.setCreatedBy(emp);
					bom.setCreatedDate(today);
					bom.setLevel(level);
					bom.setfProduct(new Products(fPartCode));
					bom.setMaterialType(formatter.formatCellValue(drow.getCell(5)));
					bom.setModifiedBy(emp);
					bom.setModifiedDate(today);
					bom.setOwner(emp);
					bom.setPlant1(formatter.formatCellValue(drow.getCell(6)));
					bom.setPlant2(formatter.formatCellValue(drow.getCell(7)));
					bom.setReqQty(quantity);
					bom.setrProduct(new Products(rpartcode));
					bom.setUmo(formatter.formatCellValue(drow.getCell(4)));
					em.persist(bom);
				}
				else {
					bom.setModifiedBy(emp);
					bom.setModifiedDate(today);
					bom.setLevel(level);
					bom.setUmo(formatter.formatCellValue(drow.getCell(4)));
					bom.setMaterialType(formatter.formatCellValue(drow.getCell(5)));
					bom.setPlant1(formatter.formatCellValue(drow.getCell(6)));
					bom.setPlant2(formatter.formatCellValue(drow.getCell(7)));
					bom.setReqQty(quantity);
					em.persist(bom);
				}
				StringBuilder locRef=new StringBuilder();
				
				locRef.append(formatter.formatCellValue(drow.getCell(14)));
				locRef.append(",");
				locRef.append(formatter.formatCellValue(drow.getCell(15)));
				locRef.append(",");
				locRef.append(formatter.formatCellValue(drow.getCell(16)));
				locRef.append(",");
				locRef.append(formatter.formatCellValue(drow.getCell(17)));
				locRef.append(",");
				locRef.append(formatter.formatCellValue(drow.getCell(18)));
				locRef.append(",");
				locRef.append(formatter.formatCellValue(drow.getCell(19)));
				
				String[] locations=locRef.toString().split(",");
				for(String loc:locations) {
					if(!loc.equalsIgnoreCase("")) {
						BillOfMaterialLocation bomLoc=null;
						try {
							bomLoc=em.createQuery("select loc from BillOfMaterialLocation loc where loc.bom=?1 and loc.locationName=?2",BillOfMaterialLocation.class)
									.setParameter(1, bom)
									.setParameter(2, loc.trim())
									.getSingleResult();
						}catch(NoResultException ex) {
							
						}
						if(bomLoc==null) {
							bomLoc=new BillOfMaterialLocation();
							bomLoc.setBom(bom);
							bomLoc.setCreatedBy(emp);
							bomLoc.setCreatedDate(today);
							bomLoc.setLocationName(loc.trim());
							bomLoc.setModifiedBy(emp);
							bomLoc.setModifiedDate(today);
						}
					}
				}
				
				StringBuilder designRef=new StringBuilder();
				designRef.append(formatter.formatCellValue(drow.getCell(8)));
				locRef.append(",");
				designRef.append(formatter.formatCellValue(drow.getCell(9)));
				locRef.append(",");
				designRef.append(formatter.formatCellValue(drow.getCell(10)));
				locRef.append(",");
				designRef.append(formatter.formatCellValue(drow.getCell(11)));
				locRef.append(",");
				designRef.append(formatter.formatCellValue(drow.getCell(12)));
				locRef.append(",");
				designRef.append(formatter.formatCellValue(drow.getCell(13)));
				
				String[] des=locRef.toString().split(",");
				for(String de:des) {
					if(!de.equalsIgnoreCase("")) {
						BillOfMaterialDesignRef ref=null;
						try {
							ref=em.createQuery("select ref from BillOfMaterialDesignRef ref where ref.bom=?1 and ref.billOfMaterialDesign=?2",BillOfMaterialDesignRef.class)
									.setParameter(1, bom)
									.setParameter(2,de.trim())
									.getSingleResult();
						}catch(Exception ex) {
							
						}
						if(ref==null) {
							new BillOfMaterialDesignRef();
							
							ref.setBillOfMaterialDesign(de.trim());
							ref.setBom(bom);
							ref.setCreatedBy(emp);
							ref.setCreatedDate(today);
							ref.setModifiedBy(emp);
							ref.setModifiedDate(today);
							em.persist(ref);
						}
							
					}
				}
			}
			
		} catch (Exception e) {
			throw new BadRequestException(e);
		}
	}
}
