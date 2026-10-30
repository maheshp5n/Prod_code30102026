package com.psl.exicom.bt;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BtAssetsDismantle implements Runnable {
  public void run() {
    synkAssetsDismantle();
  }
  
  private void synkAssetsDismantle() {
    SqlHelper _sap = new SqlHelper("SAP");
    List<Map<String, Object>> errorDataForMail = new ArrayList<>();
    try {
      List<Map<String, Object>> _assetsDismantle = _sap
        .getResultSet("select * from CRM_BT_DISMANTLING where SFLAG='CR' and TFLAG='N'");
      for (Map<String, Object> d : _assetsDismantle) {
        SqlHelper sap = new SqlHelper("SAP");
        SqlHelper crm = new SqlHelper("CRM");
        try {
          sap.beginTransaction();
          crm.beginTransaction();
          AutoKeyNumber key = new AutoKeyNumber();
          System.out.println(d);
          String remark = "";
          String crmRemark = "";
          if (d.get("REMARK") != null && !d.get("REMARK").toString().equalsIgnoreCase("null") && 
            !d.get("REMARK").toString().trim().equalsIgnoreCase(""))
            remark = d.get("REMARK").toString(); 
          if (d.get("CRM_REMARK") != null && !d.get("CRM_REMARK").toString().equalsIgnoreCase("null") && 
            !d.get("CRM_REMARK").toString().trim().equalsIgnoreCase(""))
            crmRemark = d.get("CRM_REMARK").toString(); 
          crm.executeUpdate(
              " INSERT INTO `bt_assets_dismantle_data` (`AUFNR`,`BUDAT_MKPF`,`MATNR`,`MANGE`,`WERKS`,`LGORT`,`CHARGE`,`BWART`,`MBLNR`,`ZEILE`,`SFLAG`,`TFLAG`,`REMARK`,`SYDAT`,`SYTIM`,`CRM_REMARK`, created_date) VALUE (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", 
              new Object[] { 
                d.get("AUFNR"), d.get("BUDAT_MKPF"), d.get("MATNR"), d.get("MANGE"), 
                d.get("WERKS"), d.get("LGORT"), d.get("CHARGE"), d.get("BWART"), d.get("MBLNR"), 
                d.get("ZEILE"), 
                d.get("SFLAG"), d.get("TFLAG"), remark, d.get("SYDAT"), 
                d.get("SYTIM"), crmRemark, new Date() }, key);
          Map<String, Object> product = crm.getSingleResult("SELECT * FROM `bt_products` WHERE `manual_id`=?", 
              new Object[] { d.get("MATNR") });
          if (product == null)
            throw new Exception((new StringBuilder()).append(d.get("MATNR")).append(" product doesn't Exist").toString()); 
          Map<String, Object> location = crm.getSingleResult("SELECT * FROM `location` WHERE location_id=?", 
              new Object[] { d.get("LGORT") });
          if (location == null)
            throw new Exception("This location " + d.get("LGORT") + " doesn't exist in CRM"); 
          Map<String, Object> productItem = crm.getSingleResult(
              "SELECT * FROM `bt_product_item` WHERE `warehouse_location_id`=? AND `product_id`=?;", 
              new Object[] { d.get("LGORT"), product.get("id") });
          float totalQty = 0.0F;
          float okQty = 0.0F;
          float dismatleQty = 0.0F;
          long productItemId = 0L;
          System.out.println("Product Item " + productItem);
          if (productItem == null) {
            if (d.get("UDFLAG").toString().equalsIgnoreCase("D"))
              throw new Exception("Cannot Down Stock bcz stock doesn't exist against warehouse " + 
                  d.get("LGORT") + " and partcode " + d.get("MATNR")); 
            AutoKeyNumber productItemKey = new AutoKeyNumber();
            crm.executeUpdate(
                "INSERT INTO `bt_product_item` (`created_date`, `last_modified_date`, `customer_total_ber_quantity`,`customer_total_faulty_quantity`,\r\n`customer_total_ok_quantity`, `customer_total_quantity`, `customer_total_rejected_quantity`, `customer_total_scrap_quantity`, \r\n`exicom_total_ber_quantity`, `exicom_total_faulty_quantity`, `exicom_total_ok_quantity`, `exicom_total_quantity`, `exicom_total_rejected_quantity`,\r\n`exicom_total_scrap_quantity`, `manual_id`, `created_by_email_id`, `last_modified_by_email_id`, `product_id`, `warehouse_location_id`, \r\n`owner_email_id`) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", 
                
                new Object[] { 
                  new Date(), new Date(), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), 
                  Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), "", App.user, 
                  App.user, product.get("id"), d.get("LGORT"), App.user }, productItemKey);
            productItemId = productItemKey.getKey().longValue();
            String manualId = String.format("PI-BT-%s", new Object[] { MiscellaneousUtils.fillZero(String.valueOf(productItemId), 5) });
            crm.executeUpdate("UPDATE `bt_product_item` SET `manual_id`=? WHERE id=?", 
                new Object[] { manualId, Long.valueOf(productItemId) });
          } 
          if (productItem != null) {
            totalQty = Float.parseFloat(productItem.get("exicom_total_quantity").toString());
            okQty = Float.parseFloat(productItem.get("exicom_total_ok_quantity").toString());
            productItemId = Long.parseLong(productItem.get("id").toString());
          } 
          if (d.get("UDFLAG").toString().equalsIgnoreCase("U")) {
            totalQty += (float)Long.parseLong(d.get("MANGE").toString());
            okQty += (float)Long.parseLong(d.get("MANGE").toString());
            dismatleQty = Float.parseFloat(d.get("MANGE").toString());
            crm.executeUpdate(
                "UPDATE `bt_product_item` SET `exicom_total_quantity`=? ,`exicom_total_ok_quantity`=? WHERE `id`=?", 
                new Object[] { Float.valueOf(totalQty), Float.valueOf(okQty), Long.valueOf(productItemId) });
          } else if (d.get("UDFLAG").toString().equalsIgnoreCase("D")) {
            totalQty -= Float.parseFloat(d.get("MANGE").toString());
            okQty -= Float.parseFloat(d.get("MANGE").toString());
            dismatleQty = Float.parseFloat(d.get("MANGE").toString());
            if (okQty < 0.0F)
              throw new Exception("Insufficent Qty okQty" + okQty); 
            crm.executeUpdate(
                "UPDATE `bt_product_item` SET `exicom_total_quantity`=? ,`exicom_total_ok_quantity`=? WHERE `id`=?", 
                
                new Object[] { Float.valueOf(totalQty), Float.valueOf(okQty), Long.valueOf(productItemId) });
          } else {
            throw new Exception("Invalid Flag " + d.get("UDFLAG"));
          } 
          crm.executeUpdate(
              " INSERT INTO `bt_assets_dismantle_stock` (`product_item_id`, `total_qty`, `ok_qty`, `dismantle_qty`, ud_flag, bt_assets_dismantle_data_id, part_code, aufnr)\r\nvalues (?, ?, ?, ?, ?, ?, ?, ?)", 
              
              new Object[] { Long.valueOf(productItemId), Float.valueOf(totalQty), Float.valueOf(okQty), Float.valueOf(dismatleQty), d.get("UDFLAG"), Long.valueOf(key.getKey().longValue()), d.get("MATNR"), d.get("AUFNR") });
          sap.executeUpdate("update CRM_BT_DISMANTLING set TFLAG='Y', CRM_REMARK=? where AUFNR=? and MATNR=?", 
              new Object[] { "Successfuly Done", d.get("AUFNR"), d.get("MATNR") });
          sap.commit();
          crm.commit();
        } catch (Exception e) {
          sap.rollBack();
          crm.rollBack();
          System.out.println("Error " + e.getLocalizedMessage());
          String crmRemark = "Error " + e.getLocalizedMessage();
          e.printStackTrace();
          AutoKeyNumber key = new AutoKeyNumber();
          String remark = "";
          if (d.get("REMARK") != null && !d.get("REMARK").toString().equalsIgnoreCase("null") && 
            !d.get("REMARK").toString().trim().equalsIgnoreCase(""))
            remark = d.get("REMARK").toString(); 
          Map<String, Object> map = new HashMap<>();
          map.put("AUFNR", d.get("AUFNR"));
          map.put("MATNR", d.get("MATNR"));
          map.put("MANGE", d.get("MANGE"));
          map.put("WERKS", d.get("WERKS"));
          map.put("LGORT", d.get("LGORT"));
          map.put("UDFLAG", d.get("UDFLAG"));
          map.put("REMARK", crmRemark);
          errorDataForMail.add(map);
          crm.executeUpdate(
              " INSERT INTO `bt_assets_dismantle_data` (`AUFNR`,`BUDAT_MKPF`,`MATNR`,`MANGE`,`WERKS`,`LGORT`,`CHARGE`,`BWART`,`MBLNR`,`ZEILE`,`SFLAG`,`TFLAG`,`REMARK`,`SYDAT`,`SYTIM`,`CRM_REMARK`, created_date) VALUE (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", 
              new Object[] { 
                d.get("AUFNR"), d.get("BUDAT_MKPF"), d.get("MATNR"), d.get("MANGE"), 
                d.get("WERKS"), d.get("LGORT"), d.get("CHARGE"), d.get("BWART"), d.get("MBLNR"), 
                d.get("ZEILE"), 
                "ER", "N", remark, d.get("SYDAT"), d.get("SYTIM"), crmRemark, 
                new Date() }, key);
          sap.executeUpdate(
              "update CRM_BT_DISMANTLING set SFLAG='ER', TFLAG='N', CRM_REMARK=? where AUFNR=? and MATNR=?", 
              new Object[] { crmRemark, d.get("AUFNR"), d.get("MATNR") });
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    sendMailOnError(errorDataForMail);
  }
  
  private void sendMailOnError(List<Map<String, Object>> errorDataForMail) {
    System.out.println("Error Mail Details " + errorDataForMail);
    if (errorDataForMail != null && !errorDataForMail.isEmpty())
      try {
        List<String> mailTo = new ArrayList<>();
        mailTo.add("vijay.kumar1@exicom.in");
        mailTo.add("sunil.dhyani@exicom.in");
        MailHelper mailHelper = new MailHelper("Issue", mailContent(errorDataForMail), mailTo);
        Thread th = new Thread(mailHelper);
        th.start();
      } catch (Exception ex) {
        ex.printStackTrace();
      }  
  }
  
  private String mailContent(List<Map<String, Object>> errorDataForMail) {
    StringBuilder body = new StringBuilder();
    body.append(this.headerString);
    for (Map<String, Object> content : errorDataForMail)
      body.append(mailData(content)); 
    body.append(this.footerString);
    return body.toString();
  }
  
  private String mailData(Map<String, Object> content) {
    StringBuilder sb = new StringBuilder();
    sb.append("<tr style='height:15.0pt'>\r\n  <td width=121 nowrap valign=bottom style='width:91.0pt;border:solid windowtext 1.0pt;\r\n  border-top:none;padding:0cm 5.4pt 0cm 5.4pt;height:15.0pt'>\r\n  <p class=MsoNormal><span style='color:black'>" + 
        
        content.get("AUFNR").toString() + "</span></p>\r\n" + 
        "  </td>\r\n" + 
        "  <td width=121 nowrap valign=bottom style='width:91.0pt;border-top:none;\r\n" + 
        "  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\r\n" + 
        "  padding:0cm 5.4pt 0cm 5.4pt;height:15.0pt'>\r\n" + 
        "  <p class=MsoNormal><span style='color:black'>" + content.get("MATNR") + "</span></p>\r\n" + 
        "  </td>\r\n" + 
        "  <td width=121 nowrap valign=bottom style='width:91.0pt;border-top:none;\r\n" + 
        "  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\r\n" + 
        "  padding:0cm 5.4pt 0cm 5.4pt;height:15.0pt'>\r\n" + 
        "  <p class=MsoNormal><span style='color:black'>" + content.get("MANGE").toString() + "</span></p>\r\n" + 
        "  </td>\r\n" + 
        "  <td width=121 nowrap valign=bottom style='width:91.0pt;border-top:none;\r\n" + 
        "  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\r\n" + 
        "  padding:0cm 5.4pt 0cm 5.4pt;height:15.0pt'>\r\n" + 
        "  <p class=MsoNormal><span style='color:black'>" + content.get("WERKS") + "</span></p>\r\n" + 
        "  </td>\r\n" + 
        "  <td width=187 nowrap valign=bottom style='width:140.1pt;border-top:none;\r\n" + 
        "  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\r\n" + 
        "  padding:0cm 5.4pt 0cm 5.4pt;height:15.0pt'>\r\n" + 
        "  <p class=MsoNormal><span style='color:black'>" + content.get("REMARK") + "</span></p>\r\n" + 
        "  </td>\r\n" + 
        " </tr>");
    return sb.toString();
  }
  
  private String headerString = "<html>\r\n\t\t\t\r\n\t\t\t<head>\r\n\t\t\t<meta http-equiv=Content-Type content=\\text/html; charset=windows-1252\\>\r\n\t\t\t<meta name=Generator content=\\Microsoft Word 15 (filtered)\\>\r\n\t\t\t<style>\r\n\t\t\t<!--\r\n\t\t\t /* Font Definitions */\r\n\t\t\t @font-face\r\n\t\t\t\t{font-family:\\Cambria Math\\;\r\n\t\t\t\tpanose-1:2 4 5 3 5 4 6 3 2 4;}\r\n\t\t\t@font-face\r\n\t\t\t\t{font-family:Calibri;\r\n\t\t\t\tpanose-1:2 15 5 2 2 2 4 3 2 4;}\r\n\t\t\t /* Style Definitions */\r\n\t\t\t p.MsoNormal, li.MsoNormal, div.MsoNormal\r\n\t\t\t\t{margin:0cm;\r\n\t\t\t\tfont-size:11.0pt;\r\n\t\t\t\tfont-family:\\Calibri\\,sans-serif;}\r\n\t\t\t.MsoChpDefault\r\n\t\t\t\t{font-family:\\Calibri\\,sans-serif;}\r\n\t\t\t.MsoPapDefault\r\n\t\t\t\t{margin-bottom:8.0pt;\r\n\t\t\t\tline-height:107%;}\r\n\t\t\t@page WordSection1\r\n\t\t\t\t{size:595.3pt 841.9pt;\r\n\t\t\t\tmargin:72.0pt 72.0pt 72.0pt 72.0pt;}\r\n\t\t\tdiv.WordSection1\r\n\t\t\t\t{page:WordSection1;}\r\n\t\t\t-->\r\n\t\t\t</style>\r\n\t\t\t\r\n\t\t\t</head>\r\n\t\t\t\r\n\t\t\t<body lang=EN-IN style='word-wrap:break-word'>\r\n\t\t\t\r\n\t\t\t<div class=WordSection1>\r\n\t\t\t\r\n\t\t\t<p class=MsoNormal><span style='color:#1F497D'>&nbsp;</span></p>\r\n\t\t\t\r\n\t\t\t<p class=MsoNormal><span style='color:#1F497D'>Dear User,</span></p>\r\n\t\t\t\r\n\t\t\t<p class=MsoNormal><span style='color:#1F497D'>&nbsp;</span></p>\r\n\t\t\t\r\n\t\t\t<p class=MsoNormal><span style='color:#1F497D'>This is to inform you that\r\n\t\t\t\r\n\t\t\tsome issue occur while adjusting these inventory from SAP and CRM.</span></p>\r\n\t\t\t\r\n\t\t\t<p class=MsoNormal><span style='color:#1F497D'>&nbsp;</span></p>\r\n\t\t\t\r\n\t\t\t<p class=MsoNormal><span style='color:#1F497D'>Following are the details.</span></p>\r\n\t\t\t\r\n\t\t\t<p class=MsoNormal><span style='color:#1F497D'>&nbsp;</span></p>\r\n\t\t\t\r\n\t\t\t<table class=MsoNormalTable border=0 cellspacing=0 cellpadding=0 width=793\r\n\t\t\t style='width:595.1pt;border-collapse:collapse'>\r\n\t\t\t <tr style='height:15.0pt'>\r\n\t\t\t  <td width=121 nowrap style='width:91.0pt;border:solid windowtext 1.0pt;\r\n\t\t\t  background:#D9D9D9;padding:0cm 5.4pt 0cm 5.4pt;height:15.0pt'>\r\n\t\t\t  <p class=MsoNormal><b><span style='color:black'>Sap Number</span></b></p>\r\n\t\t\t  </td>\r\n\t\t\t  <td width=121 nowrap style='width:91.0pt;border:solid windowtext 1.0pt;\r\n\t\t\t  border-left:none;background:#D9D9D9;padding:0cm 5.4pt 0cm 5.4pt;height:15.0pt'>\r\n\t\t\t  <p class=MsoNormal><b><span style='color:black'>Part Code</span></b></p>\r\n\t\t\t  </td>\r\n\t\t\t  <td width=121 nowrap style='width:91.0pt;border:solid windowtext 1.0pt;\r\n\t\t\t  border-left:none;background:#D9D9D9;padding:0cm 5.4pt 0cm 5.4pt;height:15.0pt'>\r\n\t\t\t  <p class=MsoNormal><b><span style='color:black'>Qty</span></b></p>\r\n\t\t\t  </td>\r\n\t\t\t  <td width=121 nowrap style='width:91.0pt;border:solid windowtext 1.0pt;\r\n\t\t\t  border-left:none;background:#D9D9D9;padding:0cm 5.4pt 0cm 5.4pt;height:15.0pt'>\r\n\t\t\t  <p class=MsoNormal><b><span style='color:black'>Plant</span></b></p>\r\n\t\t\t  </td>\r\n\t\t\t  <td width=187 nowrap style='width:140.1pt;border:solid windowtext 1.0pt;\r\n\t\t\t  border-left:none;background:#D9D9D9;padding:0cm 5.4pt 0cm 5.4pt;height:15.0pt'>\r\n\t\t\t  <p class=MsoNormal><b><span style='color:black'>Remark</span></b></p>\r\n\t\t\t  </td>\r\n\t\t\t </tr>\r\n";
  
  private String footerString = "</table>\r\n\r\n<p class=MsoNormal><span style='color:#1F497D'>&nbsp;</span></p>\r\n\r\n<p class=MsoNormal><span style='color:#1F497D'>&nbsp;</span></p>\r\n\r\n<p class=MsoNormal><span style='color:#1F497D'>This is system generated mail.\r\nPlease do not reply.</span></p>\r\n\r\n<p class=MsoNormal><span style='color:#1F497D'>&nbsp;</span></p>\r\n\r\n<p class=MsoNormal><span style='color:#1F497D'>Thanks.</span></p>\r\n\r\n<p class=MsoNormal><span style='color:#1F497D'>Pulse Support</span></p>\r\n\r\n<p class=MsoNormal><span style='color:#1F497D'>&nbsp;</span></p>\r\n\r\n<p class=MsoNormal><span style='color:#1F497D'>&nbsp;</span></p>\r\n\r\n<p class=MsoNormal>&nbsp;</p>\r\n\r\n</div>\r\n\r\n</body>\r\n\r\n</html>";
}
