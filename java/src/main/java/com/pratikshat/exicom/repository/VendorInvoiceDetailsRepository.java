package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.VendorInvoiceDetails;
@Repository
public interface VendorInvoiceDetailsRepository extends JpaRepository<VendorInvoiceDetails, Long> {
	//GET ALL VENDOR INVOICE STAUTS FOR DROPDOWN LIST
	
	@Query(value="SELECT S.ID AS statusId,S.name AS statusName FROM `vendor_invoice_status` S",nativeQuery=true)
	List<Map<String,Object>> getAllInvoiceStatus();
	
	@Query(value="SELECT \r\n" + 
			"	inv.`created_by_email_id` AS invoiceCreatedByEmailId,\r\n" + 
			"	inv.`created_date` AS invoiceCreatedDate,\r\n" + 
			"	grn.`entry_sheet_number` AS entrySheetNumber,\r\n" + 
			"	grn.`grn_date` AS grnDate,\r\n" + 
			"	grn.`grn_number` AS grnNumber,\r\n" + 
			"	sap.sap_number as sapNumber,\r\n"+
			" 	sap.sap_date as sapDate,\r\n"+
			"	inv.`id` AS invoiceId,\r\n" + 
			"	inv.`invoice_amount` AS invoiceAmount,\r\n" + 
			"	inv.`invoice_status_id` AS invoiceStatusId,\r\n" + 
			"	inv.`last_modified_by_email_id` AS invoiceLastModifiedEmailId,\r\n" + 
			"	inv.`last_modified_date` AS invoiceLastModifiedDate,\r\n" + 
			"	inv.`manual_id` AS invoiceManualId,\r\n" + 
			"	inv.`owner_email_id` AS invoiceOnwerEmailId,\r\n" + 
			"	inv.`po_date` AS invoicePoDate,\r\n" + 
			"	inv.`remarks` AS invoiceRemarks,\r\n" + 
			"	inv.`vendor_id` AS invoiceVendorId,\r\n" + 
			"	inv.`vendor_invoice_date` AS vendorInvoiceDate,\r\n" + 
			"	inv.`vendor_invoice_number` AS vendorInvoiceNumber,\r\n" + 
			"	inv.`vendor_name` AS vendorName,\r\n" + 
			"	inv.`vendor_po_id` AS vendorPoID,\r\n" + 
			"	inv.`wcc_details_id` AS wccDetailsId,\r\n" + 
			" 	inv.invoice_copy_url as invoiceCopyUrl,\r\n"+
			"	inv.invoice_tax_amount as invoiceTaxAmount,\r\n"+
			" 	inv.invoice_total_amount as invoiceTotalAmount,\r\n"+
			"	vis.`name` AS invoiceStatus,\r\n" + 
			"	po.`busineed_area` AS poBusineesArea,\r\n" + 
			"	po.`circle_circle_name` AS poCircleName,\r\n" + 
			"	po.`code_for_internal_ref` AS poCodeForInternalRef,\r\n" + 
			"	po.`cost_centre` AS poCostCentre,\r\n" + 
			"	po.`gl_code` AS glCode,\r\n" + 
			"	po.`po_category_id` AS poCategoryId,\r\n" + 
			"	po.`po_date` AS poDate,\r\n" + 
			"	po.`po_expiry_date` AS poExpiryDate,\r\n" + 
			"	po.`po_number` AS poNumber,\r\n" + 
			"	po.`remaining_quantity` AS poRemainingQuantity,\r\n" + 
			"	po.`total_quantity` AS poTotalQuantity,\r\n" + 
			"	po.`vendor_code` AS povendorCode,\r\n" + 
			"	po.`vendor_name` AS povendorName,\r\n" +
			"	wcc.`manual_id` AS wccManualId,\r\n" + 
			"	wcc.`service_center_legal_entity_id` AS serviceCenterId,\r\n" + 
			"	lgl.`legal_entity_name` AS serviceCenterName,\r\n" + 
			"	crb.`first_name` AS createdByFirstName,\r\n" + 
			"	crb.`last_name` AS createdByLastName,\r\n" + 
			"	crb.`middle_name` AS createdByMiddleName,\r\n" + 
			"	mdb.`first_name` AS lastModifiedByFirstName,\r\n" + 
			"	mdb.`last_name` AS lastModifiedByLastName,\r\n" + 
			"	mdb.`middle_name` AS lastModifiedByMiddleName,\r\n "+
			"	invcode.id as internalCodeId,\r\n"+
			"	invcode.internal_service_code as internalServiceCode,\r\n"+
			//"	inv.invoice_tax_amount as invoiceTaxAmount,\r\n"+
			//" 	inv.invoice_total_amount as invoiceTotalAmount,\r\n"+
			" 	cts.`contact_id` as vendorContactId,\r\n"+
			"	cts.`account_id` as vendorAccountSapCode,\r\n"+
			"	invs.`name` AS vendorInvocieStatus\r\n"+
			"	\r\n" + 
			"	FROM `vendor_invoice_details` inv \r\n" + 
			"	JOIN `vendor_po_details` po ON inv.`vendor_po_id`=po.`id`\r\n" + 
			"	JOIN `wcc_details` wcc ON inv.`wcc_details_id`=wcc.`id`\r\n" + 
			"	JOIN `vendor_invoice_status` vis ON inv.`invoice_status_id`=vis.`id` \r\n" + 
			"	JOIN `employee_details` crb ON crb.`email_id`=inv.`created_by_email_id`\r\n" + 
			"	JOIN `employee_details` mdb ON mdb.`email_id`=inv.`last_modified_by_email_id`\r\n" + 
			"	JOIN `legal_entity` lgl ON lgl.`legal_entity_id`=wcc.`service_center_legal_entity_id`\r\n"+
			"	JOIN `vendor_invoice_status` invs ON invs.`id`=inv.`invoice_status_id`\r\n"+
			"	JOIN `contact_details` cts on cts.`email_id`=inv.`created_by_email_id`\r\n"+
			" 	LEFT JOIN vendor_invoice_grn grn on grn.invoice_id=inv.id\r\n"+
			" 	LEFT JOIN vendor_invoice_sap sap on sap.invoice_id=inv.id\r\n"+
			"	LEFT JOIN internal_service_code invcode on invcode.id=grn.internal_service_code_id\r\n"+
			"	WHERE inv.`id`=:id",nativeQuery=true)
	public Map<String,Object> getVendorInvoiceById(@Param("id")Long id);
	
	@Query(value="SELECT \r\n" + 
			"	inv.`created_by_email_id` AS invoiceCreatedByEmailId,\r\n" + 
			"	inv.`created_date` AS invoiceCreatedDate,\r\n" + 
			"	grn.`entry_sheet_number` AS entrySheetNumber,\r\n" + 
			"	grn.`grn_date` AS grnDate,\r\n" + 
			"	grn.`grn_number` AS grnNumber,\r\n" + 
			"	sap.sap_number as sapNumber,\r\n"+
			" 	sap.sap_date as sapDate,\r\n"+
			"	inv.`id` AS invoiceId,\r\n" + 
			"	inv.`invoice_amount` AS invoiceAmount,\r\n" + 
			"	inv.`invoice_status_id` AS invoiceStatusId,\r\n" + 
			"	inv.`last_modified_by_email_id` AS invoiceLastModifiedEmailId,\r\n" + 
			"	inv.`last_modified_date` AS invoiceLastModifiedDate,\r\n" + 
			"	inv.`manual_id` AS invoiceManualId,\r\n" + 
			"	inv.`owner_email_id` AS invoiceOnwerEmailId,\r\n" + 
			"	inv.`po_date` AS invoicePoDate,\r\n" + 
			"	inv.`remarks` AS invoiceRemarks,\r\n" + 
			"	inv.`vendor_id` AS invoiceVendorId,\r\n" + 
			"	inv.`vendor_invoice_date` AS vendorInvoiceDate,\r\n" + 
			"	inv.`vendor_invoice_number` AS vendorInvoiceNumber,\r\n" + 
			"	inv.`vendor_name` AS vendorName,\r\n" + 
			"	inv.`vendor_po_id` AS vendorPoID,\r\n" + 
			"	inv.`wcc_details_id` AS wccDetailsId,\r\n" + 
			" 	inv.invoice_copy_url as invoiceCopyUrl,\r\n"+
			"	inv.invoice_tax_amount as invoiceTaxAmount,\r\n"+
			" 	inv.invoice_total_amount as invoiceTotalAmount,\r\n"+
			"	vis.`name` AS invoiceStatus,\r\n" + 
			"	po.`busineed_area` AS poBusineesArea,\r\n" + 
			"	po.`circle_circle_name` AS poCircleName,\r\n" + 
			"	po.`code_for_internal_ref` AS poCodeForInternalRef,\r\n" + 
			"	po.`cost_centre` AS poCostCentre,\r\n" + 
			"	po.`gl_code` AS glCode,\r\n" + 
			"	po.`po_category_id` AS poCategoryId,\r\n" + 
			"	po.`po_date` AS poDate,\r\n" + 
			"	po.`po_expiry_date` AS poExpiryDate,\r\n" + 
			"	po.`po_number` AS poNumber,\r\n" + 
			"	po.`remaining_quantity` AS poRemainingQuantity,\r\n" + 
			"	po.`total_quantity` AS poTotalQuantity,\r\n" + 
			"	po.`vendor_code` AS povendorCode,\r\n" + 
			"	po.`vendor_name` AS povendorName,\r\n" + 
			"	wcc.`manual_id` AS wccManualId,\r\n" + 
			"	wcc.`service_center_legal_entity_id` AS serviceCenterId,\r\n" + 
			"	lgl.`legal_entity_name` AS serviceCenterName,\r\n" + 
			"	crb.`first_name` AS createdByFirstName,\r\n" + 
			"	crb.`last_name` AS createdByLastName,\r\n" + 
			"	crb.`middle_name` AS createdByMiddleName,\r\n" + 
			"	mdb.`first_name` AS lastModifiedByFirstName,\r\n" + 
			"	mdb.`last_name` AS lastModifiedByLastName,\r\n" + 
			"	mdb.`middle_name` AS lastModifiedByMiddleName,\r\n"+
			" 	cts.`contact_id` as vendorContactId,\r\n"+
			"	cts.`account_id` as vendorAccountSapCode,\r\n"+
			"	invs.`name` AS vendorInvocieStatus\r\n"+
			"	\r\n" + 
			"	FROM `vendor_invoice_details` inv \r\n" + 
			"	JOIN `vendor_po_details` po ON inv.`vendor_po_id`=po.`id`\r\n" + 
			"	JOIN `wcc_details` wcc ON inv.`wcc_details_id`=wcc.`id`\r\n" + 
			"	JOIN `vendor_invoice_status` vis ON inv.`invoice_status_id`=vis.`id` \r\n" + 
			"	JOIN `employee_details` crb ON crb.`email_id`=inv.`created_by_email_id`\r\n" + 
			"	JOIN `employee_details` mdb ON mdb.`email_id`=inv.`last_modified_by_email_id`\r\n" + 
			"	JOIN `legal_entity` lgl ON lgl.`legal_entity_id`=wcc.`service_center_legal_entity_id`\r\n"+
			"	JOIN `vendor_invoice_status` invs ON invs.`id`=inv.`invoice_status_id`\r\n"+
			"	JOIN `contact_details` cts on cts.`email_id`=inv.`created_by_email_id`\r\n"+
			" 	LEFT JOIN vendor_invoice_grn grn on grn.invoice_id=inv.id\r\n"+
			" 	LEFT JOIN vendor_invoice_sap sap on sap.invoice_id=inv.id\r\n"+
			"	WHERE inv.`owner_email_id`=:emailId",countQuery="SELECT \r\n" + 
					" count(*) as countt\r\n"+
					"	FROM `vendor_invoice_details` inv \r\n" + 
					"	JOIN `vendor_po_details` po ON inv.`vendor_po_id`=po.`id`\r\n" + 
					"	JOIN `wcc_details` wcc ON inv.`wcc_details_id`=wcc.`id`\r\n" + 
					"	JOIN `vendor_invoice_status` vis ON inv.`invoice_status_id`=vis.`id` \r\n" + 
					"	JOIN `employee_details` crb ON crb.`email_id`=inv.`created_by_email_id`\r\n" + 
					"	JOIN `employee_details` mdb ON mdb.`email_id`=inv.`last_modified_by_email_id`\r\n" + 
					"	JOIN `legal_entity` lgl ON lgl.`legal_entity_id`=wcc.`service_center_legal_entity_id`\r\n"+
					"	JOIN `vendor_invoice_status` invs ON invs.`id`=inv.`invoice_status_id`\r\n"+
					"	JOIN `contact_details` cts on cts.`email_id`=inv.`created_by_email_id`\r\n"+
					"	WHERE inv.`owner_email_id`=:emailId",nativeQuery=true)
	public Page<Map<String,Object>> getVendorInvoiceListByOwner(@Param("emailId")String emailId,Pageable page);
	
	
	@Query(value="SELECT \r\n" + 
			"	inv.`created_by_email_id` AS invoiceCreatedByEmailId,\r\n" + 
			"	inv.`created_date` AS invoiceCreatedDate,\r\n" + 
			"	grn.`entry_sheet_number` AS entrySheetNumber,\r\n" + 
			"	grn.`grn_date` AS grnDate,\r\n" + 
			"	grn.`grn_number` AS grnNumber,\r\n" + 
			"	sap.sap_number as sapNumber,\r\n"+
			" 	sap.sap_date as sapDate,\r\n"+
			"	inv.`id` AS invoiceId,\r\n" + 
			"	inv.`invoice_amount` AS invoiceAmount,\r\n" + 
			"	inv.`invoice_status_id` AS invoiceStatusId,\r\n" + 
			"	inv.`last_modified_by_email_id` AS invoiceLastModifiedEmailId,\r\n" + 
			"	inv.`last_modified_date` AS invoiceLastModifiedDate,\r\n" + 
			"	inv.`manual_id` AS invoiceManualId,\r\n" + 
			"	inv.`owner_email_id` AS invoiceOnwerEmailId,\r\n" + 
			"	inv.`po_date` AS invoicePoDate,\r\n" + 
			"	inv.`remarks` AS invoiceRemarks,\r\n" + 
			"	inv.`vendor_id` AS invoiceVendorId,\r\n" + 
			"	inv.`vendor_invoice_date` AS vendorInvoiceDate,\r\n" + 
			"	inv.`vendor_invoice_number` AS vendorInvoiceNumber,\r\n" + 
			"	inv.`vendor_name` AS vendorName,\r\n" + 
			"	inv.`vendor_po_id` AS vendorPoID,\r\n" + 
			"	inv.`wcc_details_id` AS wccDetailsId,\r\n" + 
			" 	inv.invoice_copy_url as invoiceCopyUrl,\r\n"+
			"	inv.invoice_tax_amount as invoiceTaxAmount,\r\n"+
			" 	inv.invoice_total_amount as invoiceTotalAmount,\r\n"+
			"	vis.`name` AS invoiceStatus,\r\n" + 
			"	po.`busineed_area` AS poBusineesArea,\r\n" + 
			"	po.`circle_circle_name` AS poCircleName,\r\n" + 
			"	po.`code_for_internal_ref` AS poCodeForInternalRef,\r\n" + 
			"	po.`cost_centre` AS poCostCentre,\r\n" + 
			"	po.`gl_code` AS glCode,\r\n" + 
			"	po.`po_category_id` AS poCategoryId,\r\n" + 
			"	po.`po_date` AS poDate,\r\n" + 
			"	po.`po_expiry_date` AS poExpiryDate,\r\n" + 
			"	po.`po_number` AS poNumber,\r\n" + 
			"	po.`remaining_quantity` AS poRemainingQuantity,\r\n" + 
			"	po.`total_quantity` AS poTotalQuantity,\r\n" + 
			"	po.`vendor_code` AS povendorCode,\r\n" + 
			"	po.`vendor_name` AS povendorName,\r\n" + 
			"	wcc.`manual_id` AS wccManualId,\r\n" + 
			"	wcc.`service_center_legal_entity_id` AS serviceCenterId,\r\n" + 
			"	lgl.`legal_entity_name` AS serviceCenterName,\r\n" + 
			"	crb.`first_name` AS createdByFirstName,\r\n" + 
			"	crb.`last_name` AS createdByLastName,\r\n" + 
			"	crb.`middle_name` AS createdByMiddleName,\r\n" + 
			"	mdb.`first_name` AS lastModifiedByFirstName,\r\n" + 
			"	mdb.`last_name` AS lastModifiedByLastName,\r\n" + 
			"	mdb.`middle_name` AS lastModifiedByMiddleName,\r\n "+
			" 	cts.`contact_id` as vendorContactId,\r\n"+
			"	cts.`account_id` as vendorAccountSapCode,\r\n"+
			"	invs.`name` AS vendorInvocieStatus\r\n"+
			"	\r\n" + 
			"	FROM `vendor_invoice_details` inv \r\n" + 
			"	JOIN `vendor_po_details` po ON inv.`vendor_po_id`=po.`id`\r\n" + 
			"	JOIN `wcc_details` wcc ON inv.`wcc_details_id`=wcc.`id`\r\n" + 
			"	JOIN `vendor_invoice_status` vis ON inv.`invoice_status_id`=vis.`id` \r\n" + 
			"	JOIN `employee_details` crb ON crb.`email_id`=inv.`created_by_email_id`\r\n" + 
			"	JOIN `employee_details` mdb ON mdb.`email_id`=inv.`last_modified_by_email_id`\r\n" + 
			"	JOIN `legal_entity` lgl ON lgl.`legal_entity_id`=wcc.`service_center_legal_entity_id`\r\n"+
			" 	JOIN `vendor_invoice_status` invs ON invs.`id`=inv.`invoice_status_id`\r\n"+
			"	JOIN `contact_details` cts on cts.`email_id`=inv.`created_by_email_id`\r\n"+
			" 	LEFT JOIN vendor_invoice_grn grn on grn.invoice_id=inv.id\r\n"+
			" 	LEFT JOIN vendor_invoice_sap sap on sap.invoice_id=inv.id\r\n"+
			"	WHERE inv.`owner_email_id` in (:emailIds)",countQuery ="SELECT \r\n" + 
					"  count(*) as countt\r\n"+
					"	FROM `vendor_invoice_details` inv \r\n" + 
					"	JOIN `vendor_po_details` po ON inv.`vendor_po_id`=po.`id`\r\n" + 
					"	JOIN `wcc_details` wcc ON inv.`wcc_details_id`=wcc.`id`\r\n" + 
					"	JOIN `vendor_invoice_status` vis ON inv.`invoice_status_id`=vis.`id` \r\n" + 
					"	JOIN `employee_details` crb ON crb.`email_id`=inv.`created_by_email_id`\r\n" + 
					"	JOIN `employee_details` mdb ON mdb.`email_id`=inv.`last_modified_by_email_id`\r\n" + 
					"	JOIN `legal_entity` lgl ON lgl.`legal_entity_id`=wcc.`service_center_legal_entity_id`\r\n"+
					"	JOIN `vendor_invoice_status` invs ON invs.`id`=inv.`invoice_status_id`\r\n"+
					"	WHERE inv.`owner_email_id` in (:emailIds)" ,nativeQuery=true)
	public Page<Map<String,Object>> getVendorInvoiceListByOwners(@Param("emailIds")List<String> emailId,Pageable page);
	
	
	@Query(value="SELECT \r\n" + 
			"	inv.`created_by_email_id` AS invoiceCreatedByEmailId,\r\n" + 
			"	inv.`created_date` AS invoiceCreatedDate,\r\n" + 
			"	grn.`entry_sheet_number` AS entrySheetNumber,\r\n" + 
			"	grn.`grn_date` AS grnDate,\r\n" + 
			"	grn.`grn_number` AS grnNumber,\r\n" + 
			"	sap.sap_number as sapNumber,\r\n"+
			" 	sap.sap_date as sapDate,\r\n"+
			"	inv.`id` AS invoiceId,\r\n" + 
			"	inv.`invoice_amount` AS invoiceAmount,\r\n" + 
			"	inv.`invoice_status_id` AS invoiceStatusId,\r\n" + 
			"	inv.`last_modified_by_email_id` AS invoiceLastModifiedEmailId,\r\n" + 
			"	inv.`last_modified_date` AS invoiceLastModifiedDate,\r\n" + 
			"	inv.`manual_id` AS invoiceManualId,\r\n" + 
			"	inv.`owner_email_id` AS invoiceOnwerEmailId,\r\n" + 
			"	inv.`po_date` AS invoicePoDate,\r\n" + 
			"	inv.`remarks` AS invoiceRemarks,\r\n" + 
			"	inv.`vendor_id` AS invoiceVendorId,\r\n" + 
			"	inv.`vendor_invoice_date` AS vendorInvoiceDate,\r\n" + 
			"	inv.`vendor_invoice_number` AS vendorInvoiceNumber,\r\n" + 
			"	inv.`vendor_name` AS vendorName,\r\n" + 
			"	inv.`vendor_po_id` AS vendorPoID,\r\n" + 
			"	inv.`wcc_details_id` AS wccDetailsId,\r\n" + 
			" 	inv.invoice_copy_url as invoiceCopyUrl,\r\n"+
			"	inv.invoice_tax_amount as invoiceTaxAmount,\r\n"+
			" 	inv.invoice_total_amount as invoiceTotalAmount,\r\n"+
			"	vis.`name` AS invoiceStatus,\r\n" + 
			"	po.`busineed_area` AS poBusineesArea,\r\n" + 
			"	po.`circle_circle_name` AS poCircleName,\r\n" + 
			"	po.`code_for_internal_ref` AS poCodeForInternalRef,\r\n" + 
			"	po.`cost_centre` AS poCostCentre,\r\n" + 
			"	po.`gl_code` AS glCode,\r\n" + 
			"	po.`po_category_id` AS poCategoryId,\r\n" + 
			"	po.`po_date` AS poDate,\r\n" + 
			"	po.`po_expiry_date` AS poExpiryDate,\r\n" + 
			"	po.`po_number` AS poNumber,\r\n" + 
			"	po.`remaining_quantity` AS poRemainingQuantity,\r\n" + 
			"	po.`total_quantity` AS poTotalQuantity,\r\n" + 
			"	po.`vendor_code` AS povendorCode,\r\n" + 
			"	po.`vendor_name` AS povendorName,\r\n" + 
			"	wcc.`manual_id` AS wccManualId,\r\n" + 
			"	wcc.`service_center_legal_entity_id` AS serviceCenterId,\r\n" + 
			"	lgl.`legal_entity_name` AS serviceCenterName,\r\n" + 
			"	crb.`first_name` AS createdByFirstName,\r\n" + 
			"	crb.`last_name` AS createdByLastName,\r\n" + 
			"	crb.`middle_name` AS createdByMiddleName,\r\n" + 
			"	mdb.`first_name` AS lastModifiedByFirstName,\r\n" + 
			"	mdb.`last_name` AS lastModifiedByLastName,\r\n" + 
			"	mdb.`middle_name` AS lastModifiedByMiddleName,\r\n "+
			" 	cts.`contact_id` as vendorContactId,\r\n"+
			"	cts.`account_id` as vendorAccountSapCode,\r\n"+
			"	invs.`name` AS vendorInvocieStatus\r\n"+
			"	\r\n" + 
			"	FROM `vendor_invoice_details` inv \r\n" + 
			"	JOIN `vendor_po_details` po ON inv.`vendor_po_id`=po.`id`\r\n" + 
			"	JOIN `wcc_details` wcc ON inv.`wcc_details_id`=wcc.`id`\r\n" + 
			"	JOIN `vendor_invoice_status` vis ON inv.`invoice_status_id`=vis.`id` \r\n" + 
			"	JOIN `employee_details` crb ON crb.`email_id`=inv.`created_by_email_id`\r\n" + 
			"	JOIN `employee_details` mdb ON mdb.`email_id`=inv.`last_modified_by_email_id`\r\n" + 
			"	JOIN `legal_entity` lgl ON lgl.`legal_entity_id`=wcc.`service_center_legal_entity_id`\r\n"+
			" 	JOIN `vendor_invoice_status` invs ON invs.`id`=inv.`invoice_status_id`\r\n"+
			"	JOIN `contact_details` cts on cts.`email_id`=inv.`created_by_email_id`\r\n"+
			" 	LEFT JOIN vendor_invoice_grn grn on grn.invoice_id=inv.id\r\n"+
			" 	LEFT JOIN vendor_invoice_sap sap on sap.invoice_id=inv.id\r\n"+
			"	WHERE inv.`owner_email_id` in (:emailIds) and inv.`invoice_status_id`=:statusId",countQuery ="SELECT \r\n" + 
					"  count(*) as countt\r\n"+
					"	FROM `vendor_invoice_details` inv \r\n" + 
					"	JOIN `vendor_po_details` po ON inv.`vendor_po_id`=po.`id`\r\n" + 
					"	JOIN `wcc_details` wcc ON inv.`wcc_details_id`=wcc.`id`\r\n" + 
					"	JOIN `vendor_invoice_status` vis ON inv.`invoice_status_id`=vis.`id` \r\n" + 
					"	JOIN `employee_details` crb ON crb.`email_id`=inv.`created_by_email_id`\r\n" + 
					"	JOIN `employee_details` mdb ON mdb.`email_id`=inv.`last_modified_by_email_id`\r\n" + 
					"	JOIN `legal_entity` lgl ON lgl.`legal_entity_id`=wcc.`service_center_legal_entity_id`\r\n"+
					"	JOIN `vendor_invoice_status` invs ON invs.`id`=inv.`invoice_status_id`\r\n"+
					"	WHERE inv.`owner_email_id` in (:emailIds) and inv.`invoice_status_id`=:statusId" ,nativeQuery=true)
	public Page<Map<String,Object>> getVendorInvoiceListByOwners(@Param("emailIds")List<String> emailId,@Param("statusId")int statusId,Pageable page);
	
	@Query(value="SELECT \r\n" + 
			"	inv.`created_by_email_id` AS invoiceCreatedByEmailId,\r\n" + 
			"	inv.`created_date` AS invoiceCreatedDate,\r\n" + 
			"	grn.`entry_sheet_number` AS entrySheetNumber,\r\n" + 
			"	grn.`grn_date` AS grnDate,\r\n" + 
			"	grn.`grn_number` AS grnNumber,\r\n" + 
			"	sap.sap_number as sapNumber,\r\n"+
			" 	sap.sap_date as sapDate,\r\n"+
			"	inv.`id` AS invoiceId,\r\n" + 
			"	inv.`invoice_amount` AS invoiceAmount,\r\n" + 
			"	inv.`invoice_status_id` AS invoiceStatusId,\r\n" + 
			"	inv.`last_modified_by_email_id` AS invoiceLastModifiedEmailId,\r\n" + 
			"	inv.`last_modified_date` AS invoiceLastModifiedDate,\r\n" + 
			"	inv.`manual_id` AS invoiceManualId,\r\n" + 
			"	inv.`owner_email_id` AS invoiceOnwerEmailId,\r\n" + 
			"	inv.`po_date` AS invoicePoDate,\r\n" + 
			"	inv.`remarks` AS invoiceRemarks,\r\n" + 
			"	inv.`vendor_id` AS invoiceVendorId,\r\n" + 
			"	inv.`vendor_invoice_date` AS vendorInvoiceDate,\r\n" + 
			"	inv.`vendor_invoice_number` AS vendorInvoiceNumber,\r\n" + 
			"	inv.`vendor_name` AS vendorName,\r\n" + 
			"	inv.`vendor_po_id` AS vendorPoID,\r\n" + 
			"	inv.`wcc_details_id` AS wccDetailsId,\r\n" + 
			" 	inv.invoice_copy_url as invoiceCopyUrl,\r\n"+
			"	inv.invoice_tax_amount as invoiceTaxAmount,\r\n"+
			" 	inv.invoice_total_amount as invoiceTotalAmount,\r\n"+
			"	vis.`name` AS invoiceStatus,\r\n" + 
			"	po.`busineed_area` AS poBusineesArea,\r\n" + 
			"	po.`circle_circle_name` AS poCircleName,\r\n" + 
			"	po.`code_for_internal_ref` AS poCodeForInternalRef,\r\n" + 
			"	po.`cost_centre` AS poCostCentre,\r\n" + 
			"	po.`gl_code` AS glCode,\r\n" + 
			"	po.`po_category_id` AS poCategoryId,\r\n" + 
			"	po.`po_date` AS poDate,\r\n" + 
			"	po.`po_expiry_date` AS poExpiryDate,\r\n" + 
			"	po.`po_number` AS poNumber,\r\n" + 
			"	po.`remaining_quantity` AS poRemainingQuantity,\r\n" + 
			"	po.`total_quantity` AS poTotalQuantity,\r\n" + 
			"	po.`vendor_code` AS povendorCode,\r\n" + 
			"	po.`vendor_name` AS povendorName,\r\n" + 
			"	wcc.`manual_id` AS wccManualId,\r\n" + 
			"	wcc.`service_center_legal_entity_id` AS serviceCenterId,\r\n" + 
			"	lgl.`legal_entity_name` AS serviceCenterName,\r\n" + 
			"	crb.`first_name` AS createdByFirstName,\r\n" + 
			"	crb.`last_name` AS createdByLastName,\r\n" + 
			"	crb.`middle_name` AS createdByMiddleName,\r\n" + 
			"	mdb.`first_name` AS lastModifiedByFirstName,\r\n" + 
			"	mdb.`last_name` AS lastModifiedByLastName,\r\n" + 
			"	mdb.`middle_name` AS lastModifiedByMiddleName,\r\n"+
			" 	cts.`contact_id` as vendorContactId,\r\n"+
			"	cts.`account_id` as vendorAccountSapCode,\r\n"+
			"	invs.`name` AS vendorInvocieStatus\r\n"+
			"	\r\n" + 
			"	FROM `vendor_invoice_details` inv \r\n" + 
			"	JOIN `vendor_po_details` po ON inv.`vendor_po_id`=po.`id`\r\n" + 
			"	JOIN `wcc_details` wcc ON inv.`wcc_details_id`=wcc.`id`\r\n" + 
			"	JOIN `vendor_invoice_status` vis ON inv.`invoice_status_id`=vis.`id` \r\n" + 
			"	JOIN `employee_details` crb ON crb.`email_id`=inv.`created_by_email_id`\r\n" + 
			"	JOIN `employee_details` mdb ON mdb.`email_id`=inv.`last_modified_by_email_id`\r\n" + 
			"	JOIN `legal_entity` lgl ON lgl.`legal_entity_id`=wcc.`service_center_legal_entity_id`\r\n"+
			"	JOIN `vendor_invoice_status` invs ON invs.`id`=inv.`invoice_status_id`\r\n"+
			"	JOIN `contact_details` cts on cts.`email_id`=inv.`created_by_email_id`\r\n"+
			" 	LEFT JOIN vendor_invoice_grn grn on grn.invoice_id=inv.id\r\n"+
			" 	LEFT JOIN vendor_invoice_sap sap on sap.invoice_id=inv.id\r\n"+
			"	",countQuery="SELECT \r\n" + 
					" count(*) as countt\r\n"+
					"	FROM `vendor_invoice_details` inv \r\n" + 
					"	JOIN `vendor_po_details` po ON inv.`vendor_po_id`=po.`id`\r\n" + 
					"	JOIN `wcc_details` wcc ON inv.`wcc_details_id`=wcc.`id`\r\n" + 
					"	JOIN `vendor_invoice_status` vis ON inv.`invoice_status_id`=vis.`id` \r\n" + 
					"	JOIN `employee_details` crb ON crb.`email_id`=inv.`created_by_email_id`\r\n" + 
					"	JOIN `employee_details` mdb ON mdb.`email_id`=inv.`last_modified_by_email_id`\r\n" + 
					"	JOIN `legal_entity` lgl ON lgl.`legal_entity_id`=wcc.`service_center_legal_entity_id`\r\n"+
					"	JOIN `vendor_invoice_status` invs ON invs.`id`=inv.`invoice_status_id`\r\n"+
					"	JOIN `contact_details` cts on cts.`email_id`=inv.`created_by_email_id`\r\n"+
					" 	LEFT JOIN vendor_invoice_grn grn on grn.invoice_id=inv.id\r\n"+
					" 	LEFT JOIN vendor_invoice_sap sap on sap.invoice_id=inv.id\r\n"+
					"	",nativeQuery=true)
	public Page<Map<String,Object>> getVendorInvoiceList(Pageable page);
	
	
	@Query(value="SELECT \r\n" + 
			"	inv.`created_by_email_id` AS invoiceCreatedByEmailId,\r\n" + 
			"	inv.`created_date` AS invoiceCreatedDate,\r\n" + 
			"	grn.`entry_sheet_number` AS entrySheetNumber,\r\n" + 
			"	grn.`grn_date` AS grnDate,\r\n" + 
			"	grn.`grn_number` AS grnNumber,\r\n" + 
			"	sap.sap_number as sapNumber,\r\n"+
			" 	sap.sap_date as sapDate,\r\n"+
			"	inv.`id` AS invoiceId,\r\n" + 
			"	inv.`invoice_amount` AS invoiceAmount,\r\n" + 
			"	inv.`invoice_status_id` AS invoiceStatusId,\r\n" + 
			"	inv.`last_modified_by_email_id` AS invoiceLastModifiedEmailId,\r\n" + 
			"	inv.`last_modified_date` AS invoiceLastModifiedDate,\r\n" + 
			"	inv.`manual_id` AS invoiceManualId,\r\n" + 
			"	inv.`owner_email_id` AS invoiceOnwerEmailId,\r\n" + 
			"	inv.`po_date` AS invoicePoDate,\r\n" + 
			"	inv.`remarks` AS invoiceRemarks,\r\n" + 
			"	inv.`vendor_id` AS invoiceVendorId,\r\n" + 
			"	inv.`vendor_invoice_date` AS vendorInvoiceDate,\r\n" + 
			"	inv.`vendor_invoice_number` AS vendorInvoiceNumber,\r\n" + 
			"	inv.`vendor_name` AS vendorName,\r\n" + 
			"	inv.`vendor_po_id` AS vendorPoID,\r\n" + 
			"	inv.`wcc_details_id` AS wccDetailsId,\r\n" + 
			" 	inv.invoice_copy_url as invoiceCopyUrl,\r\n"+
			"	inv.invoice_tax_amount as invoiceTaxAmount,\r\n"+
			" 	inv.invoice_total_amount as invoiceTotalAmount,\r\n"+
			"	vis.`name` AS invoiceStatus,\r\n" + 
			"	po.`busineed_area` AS poBusineesArea,\r\n" + 
			"	po.`circle_circle_name` AS poCircleName,\r\n" + 
			"	po.`code_for_internal_ref` AS poCodeForInternalRef,\r\n" + 
			"	po.`cost_centre` AS poCostCentre,\r\n" + 
			"	po.`gl_code` AS glCode,\r\n" + 
			"	po.`po_category_id` AS poCategoryId,\r\n" + 
			"	po.`po_date` AS poDate,\r\n" + 
			"	po.`po_expiry_date` AS poExpiryDate,\r\n" + 
			"	po.`po_number` AS poNumber,\r\n" + 
			"	po.`remaining_quantity` AS poRemainingQuantity,\r\n" + 
			"	po.`total_quantity` AS poTotalQuantity,\r\n" + 
			"	po.`vendor_code` AS povendorCode,\r\n" + 
			"	po.`vendor_name` AS povendorName,\r\n" + 
			"	wcc.`manual_id` AS wccManualId,\r\n" + 
			"	wcc.`service_center_legal_entity_id` AS serviceCenterId,\r\n" + 
			"	lgl.`legal_entity_name` AS serviceCenterName,\r\n" + 
			"	crb.`first_name` AS createdByFirstName,\r\n" + 
			"	crb.`last_name` AS createdByLastName,\r\n" + 
			"	crb.`middle_name` AS createdByMiddleName,\r\n" + 
			"	mdb.`first_name` AS lastModifiedByFirstName,\r\n" + 
			"	mdb.`last_name` AS lastModifiedByLastName,\r\n" + 
			"	mdb.`middle_name` AS lastModifiedByMiddleName,\r\n"+
			" 	cts.`contact_id` as vendorContactId,\r\n"+
			"	cts.`account_id` as vendorAccountSapCode,\r\n"+
			"	invs.`name` AS vendorInvocieStatus\r\n"+
			"	\r\n" + 
			"	FROM `vendor_invoice_details` inv \r\n" + 
			"	JOIN `vendor_po_details` po ON inv.`vendor_po_id`=po.`id`\r\n" + 
			"	JOIN `wcc_details` wcc ON inv.`wcc_details_id`=wcc.`id`\r\n" + 
			"	JOIN `vendor_invoice_status` vis ON inv.`invoice_status_id`=vis.`id` \r\n" + 
			"	JOIN `employee_details` crb ON crb.`email_id`=inv.`created_by_email_id`\r\n" + 
			"	JOIN `employee_details` mdb ON mdb.`email_id`=inv.`last_modified_by_email_id`\r\n" + 
			"	JOIN `legal_entity` lgl ON lgl.`legal_entity_id`=wcc.`service_center_legal_entity_id`\r\n"+
			"	JOIN `vendor_invoice_status` invs ON invs.`id`=inv.`invoice_status_id`\r\n"+
			"	JOIN `contact_details` cts on cts.`email_id`=inv.`created_by_email_id`\r\n"+
			" 	LEFT JOIN vendor_invoice_grn grn on grn.invoice_id=inv.id\r\n"+
			" 	LEFT JOIN vendor_invoice_sap sap on sap.invoice_id=inv.id where inv.`invoice_status_id`=:statusid\r\n"+
			"	",countQuery="SELECT \r\n" + 
					" count(*) as countt\r\n"+
					"	FROM `vendor_invoice_details` inv \r\n" + 
					"	JOIN `vendor_po_details` po ON inv.`vendor_po_id`=po.`id`\r\n" + 
					"	JOIN `wcc_details` wcc ON inv.`wcc_details_id`=wcc.`id`\r\n" + 
					"	JOIN `vendor_invoice_status` vis ON inv.`invoice_status_id`=vis.`id` \r\n" + 
					"	JOIN `employee_details` crb ON crb.`email_id`=inv.`created_by_email_id`\r\n" + 
					"	JOIN `employee_details` mdb ON mdb.`email_id`=inv.`last_modified_by_email_id`\r\n" + 
					"	JOIN `legal_entity` lgl ON lgl.`legal_entity_id`=wcc.`service_center_legal_entity_id`\r\n"+
					"	JOIN `vendor_invoice_status` invs ON invs.`id`=inv.`invoice_status_id`\r\n"+
					"	JOIN `contact_details` cts on cts.`email_id`=inv.`created_by_email_id`\r\n"+
					" 	LEFT JOIN vendor_invoice_grn grn on grn.invoice_id=inv.id\r\n"+
					" 	LEFT JOIN vendor_invoice_sap sap on sap.invoice_id=inv.id\r\n"+
					"	 where inv.`invoice_status_id`=:statusid",nativeQuery=true)
	public Page<Map<String,Object>> getVendorInvoiceList(@Param("statusid")int statusId,Pageable page);
	
	@Query(value="SELECT \r\n" + 
			"	inv.`created_by_email_id` AS invoiceCreatedByEmailId,\r\n" + 
			"	inv.`created_date` AS invoiceCreatedDate,\r\n" + 
			"	grn.`entry_sheet_number` AS entrySheetNumber,\r\n" + 
			"	grn.`grn_date` AS grnDate,\r\n" + 
			"	grn.`grn_number` AS grnNumber,\r\n" + 
			"	sap.sap_number as sapNumber,\r\n"+
			" 	sap.sap_date as sapDate,\r\n"+
			"	inv.`id` AS invoiceId,\r\n" + 
			"	inv.`invoice_amount` AS invoiceAmount,\r\n" + 
			"	inv.`invoice_status_id` AS invoiceStatusId,\r\n" + 
			"	inv.`last_modified_by_email_id` AS invoiceLastModifiedEmailId,\r\n" + 
			"	inv.`last_modified_date` AS invoiceLastModifiedDate,\r\n" + 
			"	inv.`manual_id` AS invoiceManualId,\r\n" + 
			"	inv.`owner_email_id` AS invoiceOnwerEmailId,\r\n" + 
			"	inv.`po_date` AS invoicePoDate,\r\n" + 
			"	inv.`remarks` AS invoiceRemarks,\r\n" + 
			"	inv.`vendor_id` AS invoiceVendorId,\r\n" + 
			"	inv.`vendor_invoice_date` AS vendorInvoiceDate,\r\n" + 
			"	inv.`vendor_invoice_number` AS vendorInvoiceNumber,\r\n" + 
			"	inv.`vendor_name` AS vendorName,\r\n" + 
			"	inv.`vendor_po_id` AS vendorPoID,\r\n" + 
			"	inv.`wcc_details_id` AS wccDetailsId,\r\n" + 
			" 	inv.invoice_copy_url as invoiceCopyUrl,\r\n"+
			"	inv.invoice_tax_amount as invoiceTaxAmount,\r\n"+
			" 	inv.invoice_total_amount as invoiceTotalAmount,\r\n"+
			"	vis.`name` AS invoiceStatus,\r\n" + 
			"	po.`busineed_area` AS poBusineesArea,\r\n" + 
			"	po.`circle_circle_name` AS poCircleName,\r\n" + 
			"	po.`code_for_internal_ref` AS poCodeForInternalRef,\r\n" + 
			"	po.`cost_centre` AS poCostCentre,\r\n" + 
			"	po.`gl_code` AS glCode,\r\n" + 
			"	po.`po_category_id` AS poCategoryId,\r\n" + 
			"	po.`po_date` AS poDate,\r\n" + 
			"	po.`po_expiry_date` AS poExpiryDate,\r\n" + 
			"	po.`po_number` AS poNumber,\r\n" + 
			"	po.`remaining_quantity` AS poRemainingQuantity,\r\n" + 
			"	po.`total_quantity` AS poTotalQuantity,\r\n" + 
			"	po.`vendor_code` AS povendorCode,\r\n" + 
			"	po.`vendor_name` AS povendorName,\r\n" +
			"	wcc.`manual_id` AS wccManualId,\r\n" + 
			"	wcc.`service_center_legal_entity_id` AS serviceCenterId,\r\n" + 
			"	lgl.`legal_entity_name` AS serviceCenterName,\r\n" + 
			"	crb.`first_name` AS createdByFirstName,\r\n" + 
			"	crb.`last_name` AS createdByLastName,\r\n" + 
			"	crb.`middle_name` AS createdByMiddleName,\r\n" + 
			"	mdb.`first_name` AS lastModifiedByFirstName,\r\n" + 
			"	mdb.`last_name` AS lastModifiedByLastName,\r\n" + 
			"	mdb.`middle_name` AS lastModifiedByMiddleName,\r\n "+
			" 	cts.`contact_id` as vendorContactId,\r\n"+
			"	cts.`account_id` as vendorAccountSapCode,\r\n"+
			"	invs.`name` AS vendorInvocieStatus\r\n"+
			"	\r\n" + 
			"	FROM `vendor_invoice_details` inv \r\n" + 
			"	JOIN `vendor_po_details` po ON inv.`vendor_po_id`=po.`id`\r\n" + 
			"	JOIN `wcc_details` wcc ON inv.`wcc_details_id`=wcc.`id`\r\n" + 
			"	JOIN `vendor_invoice_status` vis ON inv.`invoice_status_id`=vis.`id` \r\n" + 
			"	JOIN `employee_details` crb ON crb.`email_id`=inv.`created_by_email_id`\r\n" + 
			"	JOIN `employee_details` mdb ON mdb.`email_id`=inv.`last_modified_by_email_id`\r\n" + 
			"	JOIN `legal_entity` lgl ON lgl.`legal_entity_id`=wcc.`service_center_legal_entity_id`\r\n"+
			"	JOIN `vendor_invoice_status` invs ON invs.`id`=inv.`invoice_status_id`\r\n"+
			"	JOIN `contact_details` cts on cts.`email_id`=inv.`created_by_email_id`\r\n"+
			" 	LEFT JOIN vendor_invoice_grn grn on grn.invoice_id=inv.id\r\n"+
			" 	LEFT JOIN vendor_invoice_sap sap on sap.invoice_id=inv.id\r\n"+
			"	WHERE wcc.`id`=:id",nativeQuery=true)
	public List<Map<String,Object>> getVendorInvoiceByWccId(@Param("id")Long id);
	@Query(value="select * from internal_service_code where business_domain_business_domain_id=:businessDomain",nativeQuery=true)
	public List<Map<String,Object>> getInternalServiceCode(int businessDomain);
	
	
	@Query(value="SELECT \r\n" + 
			"`name` AS `name`\r\n" + 
			"FROM `vendor_invoice_status`\r\n" + 
			"ORDER BY `name` ASC",nativeQuery=true)
	java.util.List<String> getInvoiceStatusList();
}
