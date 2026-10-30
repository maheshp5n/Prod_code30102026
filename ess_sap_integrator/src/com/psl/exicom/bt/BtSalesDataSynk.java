package com.psl.exicom.bt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BtSalesDataSynk implements Runnable {
  public void run() {
    synkSalesData();
  }
  
  private void synkSalesData() {
    SqlHelper _sap = new SqlHelper("SAP");
    Map<String, List<Map<String, Object>>> sldata = new HashMap<>();
    try {
      List<Map<String, Object>> _sapSales = _sap.getResultSet("select * from CRM_BT_SPARE_CONSUP where SFLAG='CR' AND TFLAG='N'");
      if (_sapSales != null && _sapSales.size() > 0)
        for (Map<String, Object> d : _sapSales) {
          SqlHelper sap = new SqlHelper("SAP");
          SqlHelper crm = new SqlHelper("CRM");
          try {
            sap.beginTransaction();
            crm.beginTransaction();
            Map<String, Object> product = crm.getSingleResult("select * from bt_products where manual_id=?", new Object[] { d.get("MATNR") });
            Map<String, Object> sLocation = crm.getSingleResult("select * from location where location_id=?", new Object[] { d.get("LGORT") });
            if (sLocation == null)
              throw new Exception("Sales Location Not found"); 
            AutoKeyNumber key = new AutoKeyNumber();
            crm.executeUpdate("insert into bt_sap_sales_data(bstdk,bstkd,invoice,invoice_date,kunnr,lfimg,lgort,matnr,mblnr,mjahr,remark,sernr,sflag,skunnr,sydat,sytim,tflag,vbeln,wadat,werks,posnr,sale_type) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", new Object[] { 
                  d.get("BSTDK"), 
                  d.get("BSTKD"), 
                  d.get("INVOICE"), 
                  d.get("INVOICE_DATE"), 
                  d.get("KUNNR"), 
                  d.get("LFIMG"), 
                  d.get("LGORT"), 
                  d.get("MATNR"), 
                  d.get("MBLNR"), 
                  d.get("MJAHR"), 
                  d.get("REMARK"), 
                  d.get("SERNR"), 
                  d.get("SFLAG"), 
                  d.get("SKUNNR"), 
                  d.get("SYDAT"), 
                  d.get("SYTIM"), 
                  "Y", 
                  d.get("VBELN"), 
                  d.get("WADAT"), 
                  d.get("WERKS"), 
                  d.get("POSNR"), 
                  d.get("SALE_TYPE") }, key);
            System.out.println("Product part code " + product.get("manual_id") + " is " + product.get("product_type"));
            Map<String, Object> stock = crm.getSingleResult("SELECT * FROM `bt_product_item` WHERE `warehouse_location_id`=? AND `product_id`=?", new Object[] { sLocation.get("location_id"), product.get("id") });
            float okQty = Float.parseFloat(stock.get("exicom_total_ok_quantity").toString());
            float totalQty = Float.parseFloat(stock.get("exicom_total_quantity").toString());
            float salesQty = Float.parseFloat(d.get("LFIMG").toString());
            System.out.println("Sales Quantity is " + salesQty + " existing ok qty " + okQty + " totalQty " + totalQty);
            okQty -= salesQty;
            totalQty -= salesQty;
            System.out.println("After operation okqty " + okQty + " totalQty " + totalQty + " Product item id " + stock.get("id") + "Product item manual id" + stock.get("manual_id"));
            if (okQty < 0.0F)
              throw new Exception("Stock not found for product " + product.get("manual_id") + " at location id" + sLocation.get("location_id") + " location name " + sLocation.get("location_name")); 
            crm.executeUpdate("UPDATE `bt_product_item` SET `exicom_total_ok_quantity`=?, `exicom_total_quantity`=? WHERE `id`=?", new Object[] { Float.valueOf(okQty), 
                  Float.valueOf(totalQty), 
                  stock.get("id") });
            crm.executeUpdate("INSERT INTO `bt_tem_sales_insert` (`stok_id`, `ok_qty`, `total_qty`) VALUES (?,?,?)", new Object[] { stock.get("id"), Float.valueOf(okQty), Float.valueOf(totalQty) });
            sap.executeUpdate("update CRM_BT_SPARE_CONSUP set TFLAG='Y',CRM_DOCNO=? where VBELN=? and MATNR=? and SERNR=? and POSNR=?", new Object[] { key.getKey(), d.get("VBELN"), d.get("MATNR"), d.get("SERNR"), d.get("POSNR") });
            List<Map<String, Object>> dd = sldata.get(sLocation.get("location_id").toString());
            if (dd != null) {
              dd.add(d);
              sldata.put(sLocation.get("location_id").toString(), dd);
            } else {
              dd = new ArrayList<>();
              dd.add(d);
              sldata.put(sLocation.get("location_id").toString(), dd);
            } 
            sap.commit();
            crm.commit();
            System.out.println("BtSales Data done....");
          } catch (Exception e) {
            sap.rollBack();
            crm.rollBack();
            String remarks = e.getLocalizedMessage();
            e.printStackTrace();
            if (remarks == null) {
              remarks = "NULL_POINTER";
            } else if (remarks.length() > 99) {
              remarks = remarks.substring(0, 99);
            } 
            sap.executeUpdate("update CRM_BT_SPARE_CONSUP set SFLAG='ER',REMARK=? where VBELN=? and MATNR=? and SERNR=? and POSNR=?", new Object[] { remarks, d.get("VBELN"), d.get("MATNR"), d.get("SERNR"), d.get("POSNR") });
            crm.executeUpdate("insert into bt_sap_sales_data(bstdk,bstkd,invoice,invoice_date,kunnr,lfimg,lgort,matnr,mblnr,mjahr,remark,sernr,sflag,skunnr,sydat,sytim,tflag,vbeln,wadat,werks,posnr,sale_type) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", new Object[] { 
                  d.get("BSTDK"), 
                  d.get("BSTKD"), 
                  d.get("INVOICE"), 
                  d.get("INVOICE_DATE"), 
                  d.get("KUNNR"), 
                  d.get("LFIMG"), 
                  d.get("LGORT"), 
                  d.get("MATNR"), 
                  d.get("MBLNR"), 
                  d.get("MJAHR"), 
                  remarks, 
                  d.get("SERNR"), 
                  "ER", 
                  d.get("SKUNNR"), 
                  d.get("SYDAT"), 
                  d.get("SYTIM"), 
                  "N", 
                  d.get("VBELN"), 
                  d.get("WADAT"), 
                  d.get("WERKS"), 
                  d.get("POSNR"), 
                  d.get("SALE_TYPE") });
          } 
        }  
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
