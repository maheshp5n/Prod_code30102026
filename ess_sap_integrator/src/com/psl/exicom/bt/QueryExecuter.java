package com.psl.exicom.bt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryExecuter {
  Connection con;
  
  public QueryExecuter(Connection con) {
    this.con = con;
  }
  
  List<Map<String, Object>> getResultSet(String query, Object[] objs) throws Exception {
    PreparedStatement pst = null;
    ResultSet rst = null;
    List<Map<String, Object>> lst = new ArrayList<>();
    try {
      pst = this.con.prepareStatement(query);
      if (objs != null)
        for (int i = 0; i < objs.length; i++)
          pst.setObject(i + 1, objs[i]);  
      rst = pst.executeQuery();
      ResultSetMetaData rsmt = rst.getMetaData();
      int columnCount = rsmt.getColumnCount();
      while (rst.next()) {
        Map<String, Object> mp = new HashMap<>();
        for (int i = 1; i <= columnCount; i++) {
          String label = rsmt.getColumnLabel(i);
          if (mp.containsKey(label))
            throw new Exception("duplicate alise"); 
          mp.put(label, rst.getObject(i));
        } 
        lst.add(mp);
      } 
    } catch (Exception ex) {
      throw ex;
    } finally {
      if (rst != null) {
        try {
          rst.close();
        } catch (Exception exception) {}
        rst = null;
      } 
      if (pst != null) {
        try {
          pst.close();
        } catch (Exception exception) {}
        pst = null;
      } 
    } 
    return lst;
  }
  
  public int updateExecute(String query, Object[] param) throws Exception {
    PreparedStatement pst = null;
    try {
      pst = this.con.prepareStatement(query);
      if (param != null && param.length > 0)
        for (int i = 0; i < param.length; i++)
          pst.setObject(i + 1, param[i]);  
      return pst.executeUpdate();
    } catch (Exception ex) {
      if (param != null && param.length > 0) {
        byte b;
        int i;
        Object[] arrayOfObject;
        for (i = (arrayOfObject = param).length, b = 0; b < i; ) {
          Object o = arrayOfObject[b];
          if (o != null)
            System.out.println(o.toString()); 
          b++;
        } 
      } 
      throw ex;
    } finally {
      if (pst != null)
        pst.close(); 
      pst = null;
    } 
  }
  
  public int updateExecute(String query, Object[] param, AutoKeyNumber keyHodler) throws Exception {
    ResultSet rst = null;
    PreparedStatement pst = null;
    try {
      pst = this.con.prepareStatement(query, 1);
      if (param != null && param.length > 0)
        for (int i = 0; i < param.length; i++)
          pst.setObject(i + 1, param[i]);  
      int ri = pst.executeUpdate();
      rst = pst.getGeneratedKeys();
      if (rst.next())
        keyHodler.setKey(Long.valueOf(rst.getLong(1))); 
      return ri;
    } catch (Exception ex) {
      if (param != null && param.length > 0) {
        byte b;
        int i;
        Object[] arrayOfObject;
        for (i = (arrayOfObject = param).length, b = 0; b < i; ) {
          Object o = arrayOfObject[b];
          System.out.println(o.toString());
          b++;
        } 
      } 
      throw ex;
    } finally {
      if (rst != null)
        rst.close(); 
      if (pst != null)
        pst.close(); 
      rst = null;
      pst = null;
    } 
  }
}
