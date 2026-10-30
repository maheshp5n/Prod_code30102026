package com.pratikshat.exicom.repository;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Phaser;

import javax.imageio.ImageIO;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImage;
import com.itextpdf.text.pdf.PdfLine;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPRow;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.pratikshat.exicom.config.FileStorageProperties;
import com.pratikshat.exicom.config.MiscellaneousUtils;
import com.pratikshat.exicom.entity.BtCase;
import com.pratikshat.exicom.entity.BtCustomerInventoryIn;
import com.pratikshat.exicom.entity.BtCustomerInventoryOut;
import com.pratikshat.exicom.entity.BtJobCard;
import com.pratikshat.exicom.entity.BtProducts;
import com.pratikshat.exicom.entity.BtRMADetails;
import com.pratikshat.exicom.entity.CircleDetails;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.utils.DateConverter;
import com.pratikshat.exicom.entity.BtJobCardProductConsume;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.geo.Circle;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import com.pratikshat.exicom.entity.BtAssets;
import com.pratikshat.exicom.entity.BtAssetsAmcdetails;
import com.pratikshat.exicom.entity.BtAssetsWarrantyDetails;
import com.pratikshat.exicom.repository.BtAssetsRepository;
import com.pratikshat.exicom.repository.BtAssetsAmcdetailsRepository;
import com.pratikshat.exicom.repository.BtAssetsWarrantyDetailsRepository;


@Component
public class BRCCasePdfGen {

    @Autowired JdbcTemplate jdbc;
    @Autowired BtCustomerInventoryInRepository dbCustomerInventoryIn;
    @Autowired BtRmaDetailsRepository dbRma;
    @Autowired BtCaseRepository dbCase;
    @Autowired BtJobCardRepository dbJobcard;
    @Autowired BtProductsRepository dbProduct;
    @Autowired BtCustomerInventoryOutRepository dbOut;
    @Autowired BtJobCardProductConsumeRepository dbProductConsumption;
    @Autowired BtJobCardQcRepository dbQc;

    @Autowired BtAssetsRepository dbAssets;
    @Autowired BtAssetsAmcdetailsRepository dbAmc;
    @Autowired BtAssetsWarrantyDetailsRepository dbWarranty;
	@Autowired CircleDetailsRepository dbCircle;

    @Autowired
	private AccountsOwnerDetailsRepository dbDev;

    @Autowired
	FileStorageProperties storage;

    private static Logger logger=LoggerFactory.getLogger(BRCCasePdfGen.class);

    private Font normalFont = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.NORMAL, BaseColor.BLACK);
	private Font headerDetailsFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLACK);
    private Font headerDetailsFontX = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.BOLD, BaseColor.BLACK);
	private Font headerDetailsFontNormal = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLACK);
	private Font normalFontBlack = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.NORMAL);
	private Font headingFont = new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.BOLD);
	private Font productTableDataFont = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.NORMAL, BaseColor.BLACK);
	private Font productTableHeaderFont = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.BOLD, BaseColor.BLACK);

    private Font headingFontX = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
     
    public void getBRCPdf(Long brcCaseId,OutputStream out,FileStorageProperties property) throws BadRequestException,UnauthoriseException{
    	this.storage=property;
          
    
    	
        // Document document = new Document(PageSize.A4.rotate(), -40, -40, 40, 40);
        Document document = new Document(PageSize.A4);
        document.setMargins(0, 0, 20, 20);
        
		//ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {

            Optional<BtCustomerInventoryIn> csid=dbCustomerInventoryIn.findById(brcCaseId);
            if(!csid.isPresent())
                throw new BadRequestException("BRC Case number not found");
            BtCustomerInventoryIn indetails=csid.get();
            BtRMADetails rma=null;
            BtCase cs=null;
            BtAssets ast=null;
            BtAssetsAmcdetails amc=null;
            BtAssetsWarrantyDetails warranty=null;
            CircleDetails circle=null;
            String zone="";

            EmployeeDetails recceivedBy=null;
            
            if(indetails.getCircleName()!=null)
            {
            	try {
            		circle= dbCircle.findByCircleName(indetails.getCircleName());
            	}catch(Exception ex)
            	{
            		
            	}
            }

            if(circle != null && circle.getZoneBattery()!=null)
            {
            	zone=circle.getZoneBattery().getZoneName();
            	
            }
            if(indetails.getRma()!=null&& indetails.getRma().getId()!=null){
                rma=dbRma.findById(indetails.getRma().getId()).get();
                if(rma.getCaseDetails()!=null&& rma.getCaseDetails().getId()!=null)
                cs=dbCase.findById(rma.getCaseDetails().getId()).get();

                if(indetails.getAssets()!=null)
                    ast=dbAssets.findById(indetails.getAssets().getId()).get();
                else
                    ast=new BtAssets();
                if(ast!=null)
                {
                    Optional<BtAssetsAmcdetails> optionalAmc=dbAmc.findById(ast.getId());
                    if(optionalAmc.isPresent())
                        amc=optionalAmc.get();
                    else
                        amc =new BtAssetsAmcdetails();
                    Optional<BtAssetsWarrantyDetails> optionalWarranty=dbWarranty.findById(ast.getId());
                    if(optionalWarranty.isPresent())
                        warranty=optionalWarranty.get();
                    else
                        warranty=new BtAssetsWarrantyDetails();
                }
            }
            if(rma==null){
                rma=new BtRMADetails();
            }
            if(cs==null){
                cs=new BtCase();
            }
            recceivedBy=dbDev.findByEmailId(indetails.getCreatedBy().getEmailId());
            

            BtProducts product=dbProduct.findById(indetails.getProduct().getId()).get();
            List<BtJobCard> lstjobCards=dbJobcard.getByBrcCaseNumber(indetails);
            Optional<BtCustomerInventoryOut> outDetailsOptional=dbOut.findById(indetails.getId());

            BtJobCard jobCard=null;
            if(lstjobCards.size()>0)
                jobCard=lstjobCards.get(0);
            else
                jobCard=new BtJobCard();

            PdfWriter pdf=PdfWriter.getInstance(document, out);
            document.open();
            PdfPTable header=new PdfPTable(new float[]{30,70});
            header.setWidthPercentage(90);
            PdfPCell imgeCell=new PdfPCell(getImage());
            
            header.addCell(imgeCell);
            header.addCell(getPdfCell("BRC REPAIR REPORT", headingFontX));

            setNoBorder(header);
            document.add(header);
            document.add(getNewLine());
           
            document.add(getTitle("MATERIAL RECEIVE DETAILS",headerDetailsFontNormal));
            document.add(getNewLine2());

            PdfPTable matRecceiveTable=new PdfPTable(2);
            matRecceiveTable.addCell(getPdfCell("BRC Case No : "+indetails.getManualId(),normalFont));
            matRecceiveTable.addCell(getPdfCell("Customer’s Name : "+indetails.getAccountSapNumber(),normalFont));
            matRecceiveTable.addCell(getPdfCell("Circle / State : "+indetails.getCircleName(),normalFont));
            matRecceiveTable.addCell(getPdfCell("Zone : "+zone,normalFont));
            matRecceiveTable.addCell(getPdfCell("Site Case No : "+cs.getManualId(),normalFont));
            matRecceiveTable.addCell(getPdfCell("Exicom RMA No : "+(rma.getManualId()==null?"Received directly for repair":rma.getManualId()),normalFont));
            matRecceiveTable.addCell(getPdfCell("Receive Challan No : "+indetails.getChallanNumber(),normalFont));
//            logger.info("reiveChallanDate from DataBase is :"+indetails.getReceiveChallanDate());
//            logger.info("reiveChallanDate After DateConvertor conversion is :"+DateConverter.convertToDate(indetails.getReceiveChallanDate()));
//            logger.info("reiveChallanDate After String conversion is :"+sdf.format(DateConverter.convertToDate(indetails.getReceiveChallanDate())));
//            
            String receiveChallanDateCon= indetails.getChallanNumber()==null?"":sdf.format(DateConverter.convertToDate(indetails.getReceiveChallanDate()));
            matRecceiveTable.addCell(getPdfCell("Receive Challan Date : "+receiveChallanDateCon,normalFont));
//            System.out.println("Print the Receive Challan Date: " +receiveChallanDateCon);
//            logger.info("Print the Receive Challan Date : " +receiveChallanDateCon);
            
            
            matRecceiveTable.addCell(getPdfCell("Customer Warehouse : "+indetails.getDummayWarehouse(),normalFont));
            matRecceiveTable.addCell(getPdfCell("Customer Location : "+indetails.getDummayLocation(),normalFont));
            matRecceiveTable.addCell(getPdfCell("Customer Contact Name : "+indetails.getContactName(),normalFont));
            matRecceiveTable.addCell(getPdfCell("Customer Contact No:  : "+indetails.getContactNumber(),normalFont));
            matRecceiveTable.addCell(getPdfCell("Customer Courier Partner Name : "+indetails.getCustomerCourierPartnarName(),normalFont));
            matRecceiveTable.addCell(getPdfCell("Customer LR Number : "+indetails.getCustomerLRNumber(),normalFont));
            matRecceiveTable.addCell(getPdfCell("Customer Plant : "+indetails.getCustomerPlant(),normalFont));
            
//            logger.info("reiveDate from DataBase is :"+indetails.getReceiveDate());
//            logger.info("reiveDate After DateConvertor conversion is : "+DateConverter.convertToDate(indetails.getReceiveDate()));
//            logger.info("reiveDate After String conversion is :"+sdf.format(DateConverter.convertToDate(indetails.getReceiveDate())));
            
            String receiveDateCon= indetails.getReceiveDate()==null?"":sdf.format(DateConverter.convertToDate(indetails.getReceiveDate()));
            matRecceiveTable.addCell(getPdfCell("Receive Date : "+receiveDateCon,normalFont));
            
//            logger.info("Print the Receive Date : " +receiveDateCon);
            
            String sla="";
            if(rma!=null&&cs!=null){
                sla= MiscellaneousUtils.slaCalculation(cs.getCreatedDate(), indetails.getCreatedDate());
            }
            matRecceiveTable.addCell(getPdfCell("BRC Case Receive SLA : "+sla,normalFont));
            matRecceiveTable.addCell(getPdfCell("Case Reason : Battery Repair Case",normalFont));

            document.add(matRecceiveTable);
            document.add(getNewLine());
            //ASSET RELATED DETAILS 
            document.add(getTitle("ASSET RELATED DETAILS ",headerDetailsFontNormal));
            document.add(getNewLine2());
            PdfPTable assetRelatedDetails=new PdfPTable(2);
            assetRelatedDetails.addCell(getPdfCell("Asset Serial No : "+indetails.getAssetsSerialNumber(),normalFont));
            assetRelatedDetails.addCell(getPdfCell("Asset Part Code : "+indetails.getProductPartcode(),normalFont));
            assetRelatedDetails.addCell(getPdfCell("Product Description : "+product.getProductDescription(),normalFont));
            String wStatus="";
            if(warranty!=null && warranty.getWarrantyExpiryDate()!=null){
                if(warranty.getWarrantyExpiryDate().before(new Date())){
                    wStatus="Under Warranty";
                }
                else
                    wStatus="Out of Warranty";
            }
             
            String receiv="";
            if(recceivedBy!=null){
                if(recceivedBy.getFirstName()!=null) receiv+=recceivedBy.getFirstName();
                if(recceivedBy.getMiddleName()!=null) receiv+=(" "+recceivedBy.getMiddleName());
                if(recceivedBy.getLastName()!=null) receiv+=(" "+recceivedBy.getLastName());
            }
//            assetRelatedDetails.addCell(getPdfCell("Asset Warranty Status : "+wStatus,normalFont));
            String dwarranty="";
            if(indetails.getDynamicWarranty()!=null)
            {
            	dwarranty=indetails.getDynamicWarranty();
            }
            assetRelatedDetails.addCell(getPdfCell("Asset Warranty Status : "+dwarranty,normalFont));
            assetRelatedDetails.addCell(getPdfCell("Fault Observed Receipt : "+indetails.getRemarks(),normalFont));
            assetRelatedDetails.addCell(getPdfCell("Asset Received By : "+receiv,normalFont));
            assetRelatedDetails.addCell(getPdfCell("Packing Condition : "+indetails.getPackingCondation(),normalFont));
            assetRelatedDetails.addCell(getPdfCell("Physical Damage (If Any) : "+indetails.getPhysicalDamage(),normalFont));
            document.add(assetRelatedDetails);
            document.add(getNewLine());

            document.add(getTitle("REPAIR DETAILS",headerDetailsFontNormal));
            document.add(getNewLine2());
            PdfPTable repariDetails=new PdfPTable(2);
            String engFirstname="";
            String engMiddName="";
            String engLastName="";
            if(jobCard!=null&&jobCard.getAssignedTo()!=null){
                engFirstname=jobCard.getAssignedTo().getFirstName();
                engMiddName=jobCard.getAssignedTo().getMiddleName();
                engLastName=jobCard.getAssignedTo().getLastName();
            }
            repariDetails.addCell(getPdfCell("Engineer Name : "+engFirstname+" "+engMiddName+" "+engLastName,normalFont));
            String issuDate=jobCard.getAssignedDate()==null?"":sdf.format(jobCard.getAssignedDate());
            repariDetails.addCell(getPdfCell("Engineer Issued Date : "+issuDate,normalFont));
            repariDetails.addCell(getPdfCell("Job Card No : "+jobCard.getManualId(),normalFont));
            String repairDate=jobCard.getRepairDate()==null?"":sdf.format(jobCard.getRepairDate());
            repariDetails.addCell(getPdfCell("Repair Date : "+repairDate,normalFont));
            repariDetails.addCell(getPdfCell("BRC Eng. Corrective Action : "+jobCard.getCorrectiveAction(),normalFont));
            repariDetails.addCell(getPdfCell("BRC Eng. Resolution Method : "+jobCard.getEngResolutionMethod(),normalFont));
            repariDetails.addCell(getPdfCell("BRC Engineer Remark : "+jobCard.getEngRemarks(),normalFont));
            repariDetails.addCell(getPdfCell("Battery AH After Repair : "+jobCard.getBatteryAhAft(),normalFont));
            repariDetails.addCell(getPdfCell("Battery Voltage Before Repair : "+jobCard.getBatteryVoltageBef(),normalFont));
            repariDetails.addCell(getPdfCell("Battery Voltage After Repair : "+jobCard.getBatteryVoltageAft(),normalFont));
            repariDetails.addCell(getPdfCell("Battery Balance At : "+jobCard.getBtBalanceAt(),normalFont));
            repariDetails.addCell(getPdfCell("Material Shortage (If Any) : "+jobCard.getMatSorteg(),normalFont));
            repariDetails.addCell(getPdfCell("Cell Bulged Count : "+jobCard.getCellBledge(),normalFont));
            repariDetails.addCell(getPdfCell("BER Remark : "+jobCard.getBerRemarks(),normalFont));
            String fState="";
            if(jobCard.getRepairProductState()!=null){
                if(jobCard.getRepairProductState().equals(1))
                    fState="OK";
                else if (jobCard.getRepairProductState().equals(2))
                    fState="Faulty";
                else if (jobCard.getRepairProductState().equals(5))
                    fState="BER";
                else if (jobCard.getRepairProductState().equals(3))
                    fState="Scrap";
            }
            repariDetails.addCell(getPdfCell("Final Repair Status : "+fState,normalFont));
            repariDetails.addCell(getPdfCell("",normalFont));


            document.add(repariDetails);
            document.add(getNewLine());

            document.add(getTitle("SPARE REPLACED DETAILS",headerDetailsFontNormal));
            document.add(getNewLine2());
//            PdfPTable speCons=new PdfPTable(5);
            PdfPTable speCons=new PdfPTable(new float[]{10,15,50,10,15});
            speCons.addCell(getPdfCell("Sr. No.",normalFont));
            speCons.addCell(getPdfCell("Part Code",normalFont));
            speCons.addCell(getPdfCell("Description",normalFont));
            speCons.addCell(getPdfCell("Quantity",normalFont));
            speCons.addCell(getPdfCell("Consume From Exicom Stock",normalFont));

            if(jobCard!=null&&jobCard.getId()!=null){

                List<BtJobCardProductConsume> lstC=dbProductConsumption.getByJobCard(jobCard);
                if(lstC!=null&&lstC.size()>0){
                	   int i=1;
                    for(BtJobCardProductConsume pc:lstC){
                    	
                        speCons.addCell(getPdfCell(""+i,normalFont));
                        speCons.addCell(getPdfCell(pc.getProduct().getManualId(),normalFont));
                        speCons.addCell(getPdfCell(pc.getProduct().getProductDescription(),normalFont));
                        speCons.addCell(getPdfCell(""+pc.getQuantity(),normalFont));
                        String d="Yes";
                        if(pc.getConsumeFromStock()==null||!pc.getConsumeFromStock())
                            d="NO";
                        
                        speCons.addCell(getPdfCell(d,normalFont));
                        i++;
                    }
                }
            }

            document.add(speCons);
            document.add(getNewLine());

            java.util.Map<String,Object> qcDetails=null;
            if(jobCard!=null&&jobCard.getId()!=null){
                List<java.util.Map<String,Object>> lstQc=dbQc.getQcByJobCardForPdf(jobCard.getId());
                if(lstQc!=null&&lstQc.size()>0)
                    qcDetails=lstQc.get(0);
            }
            

            document.add(getTitle("QUALITY CHECK DETAILS",headerDetailsFontNormal));
            document.add(getNewLine2());
            PdfPTable qcTable=new PdfPTable(2);
            qcTable.addCell(getPdfCell("Job Card QC : "+(qcDetails==null?"":qcDetails.get("manual_id").toString()),normalFont));

            String apDAte=qcDetails==null?"":sdf.format((Date)qcDetails.get("last_modified_date"));

            qcTable.addCell(getPdfCell("Approved By QC date : "+apDAte,normalFont));
            qcTable.addCell(getPdfCell("Parameters Checks : "+(qcDetails==null?"":qcDetails.get("parameters_check").toString()),normalFont));
            qcTable.addCell(getPdfCell("Final Approved : "+(qcDetails==null?"":qcDetails.get("final_approval").toString()),normalFont));
            document.add(qcTable);
            document.add(getNewLine());
            BtCustomerInventoryOut outDetails=null;
            if(outDetailsOptional.isPresent()){
                outDetails=outDetailsOptional.get();
            }
            else outDetails=new BtCustomerInventoryOut();

            document.add(getTitle("DISPATCH DETAILS",headerDetailsFontNormal));
            document.add(getNewLine2());
            PdfPTable dispach=new PdfPTable(2);
            String disDate=outDetails.getOutDate()==null?"":sdf.format(outDetails.getOutDate());
            java.util.Calendar cal = java.util.Calendar.getInstance(); 
            String initDate="";
            if(outDetails.getOutDate()!=null){
                cal.setTime(outDetails.getOutDate());
                cal.add(java.util.Calendar.DAY_OF_MONTH, -3);
                initDate=sdf.format(cal.getTime());
            }
            
            dispach.addCell(getPdfCell("Dispatch Intimation Date : "+apDAte,normalFont));
            dispach.addCell(getPdfCell("Dispatched Date : "+disDate,normalFont));
            dispach.addCell(getPdfCell("Exicom Documentation No : "+initDate,normalFont));
            dispach.addCell(getPdfCell("Exicom Courier Partner Name : "+outDetails.getOutCourierPartner(),normalFont));
            dispach.addCell(getPdfCell("Exicom Delivery Challan No : "+outDetails.getOutdeleveryChallanNumber(),normalFont));
            dispach.addCell(getPdfCell("E-way Bill No : "+outDetails.getTrackingNumber(),normalFont));


            document.add(dispach);
            document.add(new Phrase(35, new Chunk(Chunk.NEWLINE)));
//<<<<<<< HEAD
////            add date and stamp
//            
//    		PdfPTable titleHeader = new PdfPTable(2);
//    		titleHeader.getDefaultCell().setBorder(Rectangle.NO_BORDER);
//    		Font regular = new Font(FontFamily.HELVETICA, 9);
//    		Font  font = new Font(FontFamily.HELVETICA, 9, Font.BOLD);
//
//    		PdfPTable t1 = new PdfPTable(2);
//    		t1.setWidths(new int[] { 5, 8 });
//    		t1.getDefaultCell().setBorder(Rectangle.NO_BORDER);
//
//    		PdfPCell quoteheadingCell = new PdfPCell();
//    		Paragraph quoteheadingParagraph = new Paragraph();
//    		quoteheadingParagraph.add(new Chunk("BRC HEAD NAME :",font));
//    		quoteheadingCell.addElement(quoteheadingParagraph);
//    		quoteheadingCell.setBorder(Rectangle.NO_BORDER);
//    		
//    		t1.addCell(quoteheadingCell);
//
//    		PdfPCell quoteValueCell = new PdfPCell();
//    		Paragraph quoteValueParagraph = new Paragraph();
////    		quoteValueParagraph.add(new Chunk("Niraj Kumar", normalFont));
//    		
//    		List<Map<String, Object>> repairheadList=new ArrayList<>();
//			repairheadList=jdbc.queryForList("SELECT \r\n" + 
//     		"CONCAT(first_name,' ',IFNULL(middle_name,''),' ',last_name) AS `brchead`\r\n" + 
//     		" FROM `contact_details` WHERE `contact_designation_designation_id`=53");
//           for(int i=0;i<=repairheadList.size()-1;i++) {
//
//    	     Map<String,Object> list = repairheadList.get(i);
//    	
//    	   quoteValueParagraph.add(new Chunk(""+list.get("brchead"), normalFont));  
//           }
//    		quoteValueCell.addElement(quoteValueParagraph);
//    		quoteValueCell.setBorder(Rectangle.NO_BORDER);
//    		t1.addCell(quoteValueCell);
//    		
//    		
//    		PdfPCell caseheadingCell = new PdfPCell();
//    		Paragraph caseheadingParagraph = new Paragraph();
//    		caseheadingParagraph.add(new Chunk("Date :",font));
//    		caseheadingCell.addElement(caseheadingParagraph);
//    		caseheadingCell.setBorder(Rectangle.NO_BORDER);
//    		t1.addCell(caseheadingCell);
//
//    		PdfPCell caseValueCell = new PdfPCell();
//    		Paragraph caseValueParagraph = new Paragraph();
//    		caseValueParagraph.add(new Chunk(disDate, normalFont));
//    		caseValueCell.addElement(caseValueParagraph);
//    		caseValueCell.setBorder(Rectangle.NO_BORDER);
//    		t1.addCell(caseValueCell);
//    		titleHeader.addCell(t1);
//    		
////    		for image prpose
//    		
//    		PdfPTable t2 = new PdfPTable(2);
//    		t2.setWidths(new int[] { 3, 3 });
//    		t2.getDefaultCell().setBorder(Rectangle.NO_BORDER);
//
//    		PdfPCell siteheadingCell = new PdfPCell();
//    		Paragraph siteheadingParagraph = new Paragraph();
//    		siteheadingParagraph.add(new Chunk("", normalFontBlack));
//    		siteheadingCell.addElement(siteheadingParagraph);
//    		siteheadingCell.setBorder(Rectangle.NO_BORDER);
//    		siteheadingCell.setVerticalAlignment(Element.ALIGN_RIGHT);
//    		t2.addCell(siteheadingCell);
//    		
//    		PdfPCell quoteValueCell1 = new PdfPCell();
//    		Paragraph quoteValueParagraph1 = new Paragraph();
//    		quoteValueParagraph1.add(new Chunk("SIGN & STAMP",font));
//    		quoteValueCell1.addElement(quoteValueParagraph1);
//    		quoteValueCell1.setBorder(Rectangle.NO_BORDER);
//    		t2.addCell(quoteValueCell1);
//    		
////    		
//
////    		
////    		
//    		PdfPCell siteheadingCell1 = new PdfPCell();
//    		Paragraph siteheadingParagraph1 = new Paragraph();
//    		siteheadingParagraph.add(new Chunk("", normalFontBlack));
//    		siteheadingCell1.addElement(siteheadingParagraph1);
//    		siteheadingCell1.setBorder(Rectangle.NO_BORDER);
//    		siteheadingCell1.setVerticalAlignment(Element.ALIGN_RIGHT);
//    		t2.addCell(siteheadingCell1);
//    		 Path fileStorageLocation = Paths.get(storage.getUploadDir()).toAbsolutePath().normalize();
//             Image image;
//             image = Image.getInstance(fileStorageLocation + "/" + "signstamp.jpg");
//
//            image.scaleToFit(100,100);
//			image.setAlignment(Element.ALIGN_RIGHT);
//			PdfPCell cellImage = new PdfPCell(image);
//			cellImage.setBorder(Rectangle.NO_BORDER);
//			cellImage.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
//			cellImage.setVerticalAlignment(Element.ALIGN_RIGHT);
//        	t2.addCell(cellImage);
//			t2.setPaddingTop(0);
//    		titleHeader.addCell(t2);
//
//    		try {
//    			document.add(titleHeader);
//    		} catch (DocumentException e) {
//    			throw new ExceptionConverter(e);
//    		}
//                        
////            closing
////            signTable.addCell(getPdfCell("SIGN & STAMP",headerDetailsFontX));
////
////            setNoBorder(signTable);
////
////            document.add(signTable);
////            document.add(getNewLine());
//=======
      
            PdfPTable signTable=new PdfPTable(new float[]{20,60,20});
            signTable.addCell("");
            signTable.addCell(getPdfCell("This Document is system generated and does not required sign & stamp ",headerDetailsFontX));
            signTable.addCell("");
//            signTable.addCell(getPdfCell("",headerDetailsFontX));
//            signTable.addCell(getPdfCell("DATE:",headerDetailsFontX));
//            signTable.addCell(getPdfCell("SIGN & STAMP",headerDetailsFontX));

            setNoBorder(signTable);

            document.add(signTable);
            document.add(getNewLine());
//>>>>>>> f7157883f101fd9394a768fa4a073a38e9fca66d

            // PdfPTable pTable=new PdfPTable(4);
            // pTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
            
            // final Phrase titlenCaseNumber=new Phrase();
            // titlenCaseNumber.add(new Chunk("Case Number",headerDetailsFont));
            // PdfPCell phead=new PdfPCell(titlenCaseNumber);
            // pTable.addCell(phead);
            // final Phrase titleCaseNumber_= new Phrase();
            // titleCaseNumber_.add(new Chunk(indetails.getManualId(),headerDetailsFont));
            // final PdfPCell pheadManualnumber=new PdfPCell(titleCaseNumber_);
            // pTable.addCell(pheadManualnumber);

            // final Phrase serialNumbe=new Phrase();
            // serialNumbe.add(new Chunk("Battery Serial No",headerDetailsFont));
            // PdfPCell serialNumberCell=new PdfPCell(serialNumbe);
            // pTable.addCell(serialNumberCell);

            // final Phrase serialNumbeText=new Phrase();
            // serialNumbeText.add(new Chunk(indetails.getAssetsSerialNumber(),headerDetailsFont));
            // PdfPCell serialNumberTextCell=new PdfPCell(serialNumbeText);
            // pTable.addCell(serialNumberTextCell);
            
            // PdfPTable lineTable=new PdfPTable(1);
            // lineTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
            
            // Chunk lineChunk=new Chunk("Asset Receive Information",headerDetailsFontNormal);
            // Phrase linePharse=new Phrase();
            // linePharse.add(lineChunk);
            // PdfPCell lineTableCell=new PdfPCell(linePharse);
            // lineTable.addCell(lineTableCell);

            // //ASSETS RECEIVED INFORMATION

            // final Phrase customerPhares=new Phrase(new Chunk("Customer",normalFont));
            // final Phrase customerTextPhares=new Phrase(new Chunk(MiscellaneousUtils.objToString(indetails.getAccountSapNumber()),normalFont));
            // final Phrase sitePhrase=new Phrase(new Chunk("State / Circle Name",normalFont));
            // final Phrase siteTextPhrase=new Phrase(new Chunk(MiscellaneousUtils.objToString(indetails.getCircleName()),normalFont));
            // final Phrase receiveChallanNumber=new Phrase(new Chunk("Receive Challan No",normalFont));
            // final Phrase receiveChallnNumbertext=new Phrase(new Chunk(MiscellaneousUtils.objToString(indetails.getChallanNumber()),normalFont));
            // final Phrase receiveChallanDate=new Phrase(new Chunk("Receive Challan Date",normalFont));
            // //String receiveChallanDateCon= indetails.getChallanNumber()==null?"":sdf.format(DateConverter.convertToDate(indetails.getReceiveChallanDate()));
            // final Phrase receiveChallanDateText=new Phrase(new Chunk( receiveChallanDateCon,normalFont));
            // final Phrase customerLRNumber=new Phrase(new Chunk("Customer LR Number",normalFont));
            // final Phrase customerLRNumberText=new Phrase(new Chunk(MiscellaneousUtils.objToString(indetails.getCustomerLRNumber()),normalFont));
            // final Phrase customerCourierpart=new Phrase(new Chunk("Customer Courier Partner Name",normalFont));
            // final Phrase customerCourierpartText=new Phrase(new Chunk(MiscellaneousUtils.objToString(indetails.getCustomerCourierPartnarName()),normalFont));
            // final Phrase customerplant=new Phrase(new Chunk("Customer Plant",normalFont));
            // final Phrase customerPlantText=new Phrase(new Chunk(MiscellaneousUtils.objToString(indetails.getCustomerPlant()),normalFont));
            // final Phrase customerWarehouse=new Phrase(new Chunk("Customer Warehouse",normalFont));
            // final Phrase customerWarehouseText=new Phrase(new Chunk(MiscellaneousUtils.objToString(indetails.getDummayWarehouse()),normalFont));
            // final Phrase receiveDate=new Phrase(new Chunk("Receive Date",normalFont));
            // //String receiveDateCon= indetails.getReceiveDate()==null?"":sdf.format(DateConverter.convertToDate(indetails.getReceiveDate()));
            // final Phrase receiveDateText=new Phrase(new Chunk(receiveDateCon ,normalFont));
            // final Phrase customerLocation=new Phrase(new Chunk("Customer Location",normalFont));
            // final Phrase customerLocationText=new Phrase(new Chunk(MiscellaneousUtils.objToString(indetails.getDummayLocation()),normalFont));
            // final Phrase contactName=new Phrase(new Chunk("Contact Name",normalFont));
            // final Phrase contactNameText=new Phrase(new Chunk(MiscellaneousUtils.objToString(indetails.getContactName()),normalFont));
            // final Phrase contactNumber=new Phrase(new Chunk("Contact No",normalFont));
            // final Phrase contactNumberText=new Phrase(new Chunk(MiscellaneousUtils.objToString(indetails.getContactNumber()),normalFont));
            // final Phrase packingCondation=new Phrase(new Chunk("Packing Condition",normalFont));
            // final Phrase packingCondationText=new Phrase(new Chunk(MiscellaneousUtils.objToString(indetails.getPackingCondation()),normalFont));
            // final Phrase physicalDamage=new Phrase(new Chunk("Physical Damage (If Any)",normalFont));
            // final Phrase physicalDamageText=new Phrase(new Chunk(MiscellaneousUtils.objToString(indetails.getPhysicalDamage()),normalFont));
            // final Phrase assetSerialNumber=new Phrase(new Chunk("Asset SN",normalFont));
            // final Phrase assetSerialNumberText=new Phrase(new Chunk(MiscellaneousUtils.objToString(indetails.getAssetsSerialNumber()),normalFont));
            // final Phrase partcode=new Phrase(new Chunk("Asset Part Code",normalFont));
            // final Phrase partcodeText=new Phrase(new Chunk(MiscellaneousUtils.objToString(indetails.getProductPartcode()),normalFont));
            // final Phrase productDesc=new Phrase(new Chunk("Product Description",normalFont));
            // final Phrase productDescText=new Phrase(new Chunk(MiscellaneousUtils.objToString(product.getProductDescription()),normalFont));
            // final Phrase faultObser=new Phrase(new Chunk("Fault Observed Receipt",normalFont));
            // final Phrase faultObservText=new Phrase(new Chunk("",normalFont));
            // final Phrase brcCaseNumber=new Phrase(new Chunk("BRC Case ",normalFont));
            // final Phrase brcCaseNumberText=new Phrase(new Chunk(MiscellaneousUtils.objToString(indetails.getManualId()),normalFont));
            // final Phrase warranty=new Phrase(new Chunk("Warranty Status",normalFont));
            // final Phrase warranty_text=new Phrase(new Chunk(MiscellaneousUtils.objToString(indetails.getDynamicWarranty()),normalFont));
            
            // final Phrase rmaNumber=new Phrase(new Chunk("RMA NO",normalFont));
            // String rmaNumber_S=rma==null?"Receive Directly for repair": MiscellaneousUtils.objToString(rma.getManualId());
            // final Phrase rmaNumberTExt=new Phrase(new Chunk(rmaNumber_S,normalFont));

            // final Phrase siteCase=new Phrase(new Chunk("Site Case ",normalFont));
            // String caseNumber=cs==null?" ":MiscellaneousUtils.objToString(cs.getManualId());
            // final Phrase siteCaseText=new Phrase(new Chunk(caseNumber,normalFont));
            // final Phrase remarks=new Phrase(new Chunk("Remarks (Receipt)",normalFont));
            // final Phrase remarksText=new Phrase(new Chunk(MiscellaneousUtils.objToString(indetails.getRemarks()),normalFont));
            // final Phrase assetsReInt=new Phrase(new Chunk("Asset Receive Day Interval",normalFont));
            // final Phrase assetsReintText=new Phrase(new Chunk("",normalFont));


            // final PdfPCell customerCell=new PdfPCell(customerPhares);
            // final PdfPCell customerTextCell=new PdfPCell(customerTextPhares);
            // final PdfPCell siteCell=new PdfPCell(sitePhrase);
            // final PdfPCell siteCellText=new PdfPCell(siteTextPhrase);
            // final PdfPCell receiveChallanCell=new PdfPCell(receiveChallanNumber);
            // final PdfPCell receiveChallanCellText=new PdfPCell(receiveChallnNumbertext);
            // final PdfPCell receiveChallanDatecell=new PdfPCell(receiveChallanDate);
            // final PdfPCell receiveChallanDateTExtCell=new PdfPCell(receiveChallanDateText);
            // final PdfPCell customerLRNumbercell=new PdfPCell(customerLRNumber);
            // final PdfPCell customerLRNumberTextCell=new PdfPCell(customerLRNumberText);
            // final PdfPCell customerCourierPartCell=new PdfPCell(customerCourierpart);
            // final PdfPCell customerCourierPrtTextCell=new PdfPCell(customerCourierpartText);
            // final PdfPCell customerPlantCell=new PdfPCell(customerplant);
            // final PdfPCell customerPlantTextCell=new PdfPCell(customerPlantText);
            // final PdfPCell customerWarehouseCell=new PdfPCell(customerWarehouse);
            // final PdfPCell customerWarehouseTextCell=new PdfPCell(customerWarehouseText);
            // final PdfPCell receiveDateCell=new PdfPCell(receiveDate);
            // final PdfPCell receiveDateTextCell=new PdfPCell(receiveDateText);
            // final PdfPCell customerLocationCell=new PdfPCell(customerLocation);
            // final PdfPCell customerLocationTextCell=new PdfPCell(customerLocationText);
            // final PdfPCell contactNameCell=new PdfPCell(contactName);
            // final PdfPCell contactNameTextCell=new PdfPCell(contactNameText);
            // final PdfPCell contactNumberCell=new PdfPCell(contactNumber);
            // final PdfPCell contactNumberTextCell=new PdfPCell(contactNumberText);
            // final PdfPCell packingCondationCell=new PdfPCell(packingCondation);
            // final PdfPCell packingCondationTextCell=new PdfPCell(packingCondationText);
            // final PdfPCell physicalDamageCell=new PdfPCell(physicalDamage);
            // final PdfPCell physicalDamageTextCell=new PdfPCell(physicalDamageText);
            // final PdfPCell assetsSerialNumberCell=new PdfPCell(assetSerialNumber);
            // final PdfPCell assetsSerialNumberTextCell=new PdfPCell(assetSerialNumberText);
            // final PdfPCell partcodeCell=new PdfPCell(partcode);
            // final PdfPCell partcodeTextCell=new PdfPCell(partcodeText);
            // final PdfPCell productDescCell=new PdfPCell(productDesc);
            // final PdfPCell productDescTextCell=new PdfPCell(productDescText);
            // final PdfPCell faultObserCell=new PdfPCell(faultObser);
            // final PdfPCell faultObserTextCell=new PdfPCell(faultObservText);

            // final PdfPCell brcCaseNumbercell=new PdfPCell(brcCaseNumber);
            // final PdfPCell brcCaseNumberTextCell=new PdfPCell(brcCaseNumberText);
            // final PdfPCell warrantyCell=new PdfPCell(warranty);
            // final PdfPCell warrantyTextCell=new PdfPCell(warranty_text);
            // final PdfPCell rmaNumberCell=new PdfPCell(rmaNumber);
            // final PdfPCell rmaNumberTextCell=new PdfPCell(rmaNumberTExt);
            // final PdfPCell sitCaseCell=new PdfPCell(siteCase);
            // final PdfPCell siteCaseTextCell=new PdfPCell(siteCaseText);
            // final PdfPCell remarksCell=new PdfPCell(remarks);
            // final PdfPCell remarksTextCell =new PdfPCell(remarksText);
            // final PdfPCell assetsReIntcell=new PdfPCell(assetsReInt);
            // final PdfPCell assetsReintTextCell=new PdfPCell(assetsReintText);

            // final PdfPTable ariTable=new PdfPTable(4);

            // ariTable.getDefaultCell().setBorder(0);
            // ariTable.addCell(customerCell);
            // ariTable.addCell(customerTextCell);
            // ariTable.addCell(siteCell);
            // ariTable.addCell(siteCellText);
            // ariTable.addCell(receiveChallanCell);
            // ariTable.addCell(receiveChallanCellText);
            // ariTable.addCell(receiveChallanDatecell);
            // ariTable.addCell(receiveChallanDateTExtCell);
            // ariTable.addCell(customerLRNumbercell);
            // ariTable.addCell(customerLRNumberTextCell);
            // ariTable.addCell(customerCourierPartCell);
            // ariTable.addCell(customerCourierPrtTextCell);
            // ariTable.addCell(customerPlantCell);
            // ariTable.addCell(customerPlantTextCell);
            // ariTable.addCell(customerWarehouseCell);
            // ariTable.addCell(customerWarehouseTextCell);
            // ariTable.addCell(receiveDateCell);
            // ariTable.addCell(receiveDateTextCell);
            // ariTable.addCell(customerLocationCell);
            // ariTable.addCell(customerLocationTextCell);
            // ariTable.addCell(contactNameCell);
            // ariTable.addCell(contactNameTextCell);
            // ariTable.addCell(contactNumberCell);
            // ariTable.addCell(contactNumberTextCell);
            // ariTable.addCell(packingCondationCell);
            // ariTable.addCell(packingCondationTextCell);
            // ariTable.addCell(physicalDamageCell);
            // ariTable.addCell(physicalDamageTextCell);
            // ariTable.addCell(assetsSerialNumberCell);
            // ariTable.addCell(assetsSerialNumberTextCell);
            // ariTable.addCell(partcodeCell);
            // ariTable.addCell(partcodeTextCell);
            // ariTable.addCell(productDescCell);
            // ariTable.addCell(productDescTextCell);
            // ariTable.addCell(faultObserCell);
            // ariTable.addCell(faultObserTextCell);
            // ariTable.addCell(brcCaseNumbercell);
            // ariTable.addCell(brcCaseNumberTextCell);
            // ariTable.addCell(warrantyCell);
            // ariTable.addCell(warrantyTextCell);
            // ariTable.addCell(rmaNumberCell);
            // ariTable.addCell(rmaNumberTextCell);
            // ariTable.addCell(sitCaseCell);
            // ariTable.addCell(siteCaseTextCell);
            // ariTable.addCell(remarksCell);
            // ariTable.addCell(remarksTextCell);
            // ariTable.addCell(assetsReIntcell);
            // ariTable.addCell(assetsReintTextCell);

            // setNoBorder(pTable);
            // setNoBorder(lineTable);
            // //setNoBorder(ariTable);

            // //END ASSETS RECEIVED INFORMATION

            // //START ASSETS REPARI INFORMATION

            // PdfPTable assetsRepariTable =new PdfPTable(1);
            // final Phrase assetsRepariPhrase=new Phrase(new Chunk("Asset Repair Information",headerDetailsFontNormal));
            // final PdfPCell assetsRepariCell=new PdfPCell(assetsRepariPhrase);
            // assetsRepariTable.addCell(assetsRepariCell);
            // setNoBorder(assetsRepariTable);

            // PdfPTable astRepInfoTable=new PdfPTable(4);

            // final Phrase jobCardP=new Phrase(new Chunk("Job Card No",normalFont));
            // final Phrase jobCardPText=new Phrase(new Chunk(MiscellaneousUtils.objToString(jobCard.getManualId()),normalFont));
            // final Phrase engineerNameP=new Phrase(new Chunk("Engineer Name",normalFont));
            // String ownerName="";
            // if(jobCard.getOwner()!=null){
            //     EmployeeDetails ow=jobCard.getOwner();
            //     ownerName=ow.getFirstName()==null?"":ow.getFirstName();
            //     ownerName+=ow.getLastName()==null?"":" "+ow.getLastName();
            // }
            // final Phrase engineerNamePText=new Phrase(new Chunk(MiscellaneousUtils.objToString(ownerName),normalFont));
            // final Phrase issueDateP=new Phrase(new Chunk("Engineer Issued Date",normalFont));
            // String engIssueDate=jobCard.getAssignedDate()==null?"":sdf.format(jobCard.getAssignedDate()).toString();
            // final Phrase issueDatePText=new Phrase(new Chunk(engIssueDate,normalFont));
            // final Phrase repariDateP=new Phrase(new Chunk("Repair Date",normalFont));
            // String repartDate= jobCard.getRepairDate()==null?"":MiscellaneousUtils.objToString(sdf.format(jobCard.getRepairDate())).toString();
            // final Phrase repairDatePText=new Phrase(new Chunk(repartDate,normalFont));
            // final Phrase batteryAhafP=new Phrase(new Chunk("Battery AH After Repair",normalFont));
            // final Phrase batteryAhafPText=new Phrase(new Chunk(MiscellaneousUtils.objToString(jobCard.getBatteryAhAft()),normalFont));
            // final Phrase brcremarksP=new Phrase(new Chunk("BRC Eng. Corrective Action",normalFont));
            // final Phrase brcremarksPText=new Phrase(new Chunk(MiscellaneousUtils.objToString(jobCard.getCorrectiveAction()),normalFont));
            // final Phrase brcEngResolP=new Phrase(new Chunk("BRC Eng Resolution Method",normalFont));
            // final Phrase brcEngResolPText=new Phrase(new Chunk(MiscellaneousUtils.objToString(jobCard.getEngResolutionMethod()),normalFont));
            // final Phrase engReP=new Phrase(new Chunk("BRC Engineer Remark",normalFont));
            // final Phrase engRepTExt=new Phrase(new Chunk(MiscellaneousUtils.objToString(jobCard.getEngRemarks()),normalFont));
            // final Phrase berRemarkP=new Phrase(new Chunk("BER Remark",normalFont));
            // final Phrase berRemarkPText=new Phrase(new Chunk(MiscellaneousUtils.objToString(jobCard.getBerRemarks()),normalFont));
            // final Phrase batterybalanceAtP= new Phrase(new Chunk("Battery Balance At",normalFont));
            // final Phrase batteryBalanceAtPt=new Phrase(new Chunk(MiscellaneousUtils.objToString(jobCard.getBtBalanceAt()),normalFont));
            // final Phrase cellBladgeCountP=new Phrase(new Chunk("Cell Bulged Count",normalFont));
            // final Phrase cellBladgeCountPText=new Phrase(new Chunk(MiscellaneousUtils.objToString(jobCard.getCellBledge()),normalFont));
            // final Phrase typeP=new Phrase(new Chunk("Current Status",normalFont));
            // String repairState="Faulty";
            // if(jobCard.getRepairProductState()!=null){
            //     if(jobCard.getRepairProductState().equals(1))
            //     repairState="OK";
            // else if(jobCard.getRepairProductState().equals(2))
            //     repairState="Faulty";
            // else if(jobCard.getRepairProductState().equals(5))
            //     repairState="BER";
            // }
                        
            // final Phrase typePText=new Phrase(new Chunk(repairState,normalFont));
            // final Phrase repApproved=new Phrase(new Chunk("Repair Approved",normalFont));
            // final Phrase repApprovedT=new Phrase(new Chunk("",normalFont));

            // astRepInfoTable.addCell(new PdfPCell(jobCardP));
            // astRepInfoTable.addCell(new PdfPCell(jobCardPText));
            // astRepInfoTable.addCell(new PdfPCell(engineerNameP));
            // astRepInfoTable.addCell(new PdfPCell(engineerNamePText));
            // astRepInfoTable.addCell(new PdfPCell(issueDateP));
            // astRepInfoTable.addCell(new PdfPCell(issueDatePText));
            // astRepInfoTable.addCell(new PdfPCell(repariDateP));
            // astRepInfoTable.addCell(new PdfPCell(repairDatePText));
            // astRepInfoTable.addCell(new PdfPCell(batteryAhafP));
            // astRepInfoTable.addCell(new PdfPCell(batteryAhafPText));
            // astRepInfoTable.addCell(new PdfPCell(brcremarksP));
            // astRepInfoTable.addCell(new PdfPCell(brcremarksPText));
            // astRepInfoTable.addCell(new PdfPCell(brcEngResolP));
            // astRepInfoTable.addCell(new PdfPCell(brcEngResolPText));
            // astRepInfoTable.addCell(new PdfPCell(engReP));
            // astRepInfoTable.addCell(new PdfPCell(engRepTExt));
            // astRepInfoTable.addCell(new PdfPCell(berRemarkP));
            // astRepInfoTable.addCell(new PdfPCell(berRemarkPText));
            // astRepInfoTable.addCell(new PdfPCell(batterybalanceAtP));
            // astRepInfoTable.addCell(new PdfPCell(batteryBalanceAtPt));
            // astRepInfoTable.addCell(new PdfPCell(cellBladgeCountP));
            // astRepInfoTable.addCell(new PdfPCell(cellBladgeCountPText));
            // astRepInfoTable.addCell(new PdfPCell(typeP));
            // astRepInfoTable.addCell(new PdfPCell(typePText));
            // astRepInfoTable.addCell(new PdfPCell(repApproved));
            // astRepInfoTable.addCell(new PdfPCell(repApprovedT));
            // setNoBorder(astRepInfoTable);
            // //END ASSETS REPARI INFORMATION

            // //ASSETS DISPATCH INFORMATION
            // Optional<BtCustomerInventoryOut> dispatchiinfoOptional=dbOut.findById(brcCaseId);
            // BtCustomerInventoryOut outInformation=null;
            // if(dispatchiinfoOptional.isPresent())
            //     outInformation=dispatchiinfoOptional.get();
            // else
            //     outInformation=new BtCustomerInventoryOut();

            // PdfPTable asstsinformationtableTitle=new PdfPTable(1);
            // asstsinformationtableTitle.addCell(new PdfPCell(new Phrase(new Chunk("Asset Dispatch Information",headerDetailsFontNormal))));
            // setNoBorder(asstsinformationtableTitle);
            // PdfPTable assDispatchInfo=new PdfPTable(4);
            // assDispatchInfo.addCell(new PdfPCell(new Phrase(new Chunk("Repair Approved Date",normalFont))));
            // assDispatchInfo.addCell(new PdfPCell(new Phrase(new Chunk("",normalFont))));
            // assDispatchInfo.addCell(new PdfPCell(new Phrase(new Chunk("Dispatch Intimation Date",normalFont))));
            // assDispatchInfo.addCell(new PdfPCell(new Phrase(new Chunk("",normalFont))));
            // assDispatchInfo.addCell(new PdfPCell(new Phrase(new Chunk("Dispatched Date",normalFont))));
            // String dispatchedDate= outInformation.getOutDate()==null?"":MiscellaneousUtils.objToString(sdf.format(outInformation.getOutDate()));
            // assDispatchInfo.addCell(new PdfPCell(new Phrase(new Chunk(dispatchedDate,normalFont))));
            // assDispatchInfo.addCell(new PdfPCell(new Phrase(new Chunk("Exicom LR/DOC NO",normalFont))));
            // assDispatchInfo.addCell(new PdfPCell(new Phrase(new Chunk(MiscellaneousUtils.objToString(outInformation.getOutLrDocNumber()),normalFont))));
            // assDispatchInfo.addCell(new PdfPCell(new Phrase(new Chunk("Exicom Courier Partner",normalFont))));
            // assDispatchInfo.addCell(new PdfPCell(new Phrase(new Chunk(MiscellaneousUtils.objToString(outInformation.getOutCourierPartner()),normalFont))));
            // assDispatchInfo.addCell(new PdfPCell(new Phrase(new Chunk("Exicom Delivery Challan No",normalFont))));
            // assDispatchInfo.addCell(new PdfPCell(new Phrase(new Chunk(MiscellaneousUtils.objToString(outInformation.getOutdeleveryChallanNumber()),normalFont))));
            // setNoBorder(assDispatchInfo);
            // document.add(pTable);
            // document.add(getNewLine());
            // document.add(lineTable);
            // document.add(getNewLine());
            // document.add(ariTable);
            // document.add(getNewLine());
            // document.add(assetsRepariTable);
            // document.add(getNewLine());
            // document.add(astRepInfoTable);
            // document.add(getNewLine());
            // document.add(asstsinformationtableTitle);
            // document.add(getNewLine());
            // document.add(assDispatchInfo);

            document.close();
            
        } catch (Exception e) {
            logger.error("Error in PDF creation of BRC case {} ", e);
        }

        
    }

    void setNoBorder(PdfPTable table){
        for(PdfPRow _r:table.getRows()){
            for(PdfPCell _c:_r.getCells()){
                _c.setBorder(Rectangle.NO_BORDER);
            }
        }
    }

    PdfPCell getPdfCell(String lable,Font font){
        if(lable==null)
            lable="";
        lable=lable.replaceAll("null", "");
        return new PdfPCell(new Phrase(new Chunk(lable,font)));
    }

    Image getImage(){
        Path fileStorageLocation = Paths.get(storage.getUploadDir()).toAbsolutePath().normalize();
        Image image;
        try {
            System.out.println(fileStorageLocation);
            image = Image.getInstance(fileStorageLocation + "/" + "logo.png");
            image.scaleToFit(150, 150);
            return image;
        } catch (BadElementException | IOException e) {
            
            e.printStackTrace();
        }
        
        return null;
    }
    
    Phrase getNewLine2() {
    	Phrase phrase=new Phrase(2,new Chunk(Chunk.NEWLINE));
    	return phrase;
    }

    Phrase getNewLine(){
        Phrase phrase=new Phrase(5,new Chunk(Chunk.NEWLINE));
        return phrase;
    }

    PdfPTable getTitle(String line,Font font){
        PdfPTable ptable =new PdfPTable(1);
        PdfPCell cell=getPdfCell(line,font);
        cell.setBorder(Rectangle.NO_BORDER);
        ptable.addCell(cell);
        
        return ptable;
    }
    
    
}
