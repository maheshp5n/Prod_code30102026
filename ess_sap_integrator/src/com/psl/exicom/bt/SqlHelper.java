package com.psl.exicom.bt;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;

public class SqlHelper {
  String type = null;
  
  boolean transaction;
  
  Connection con = null;
  
  DataSource datasource = null;
  
  public SqlHelper(String type) {
    this.type = type;
    getDataSoruce();
  }
  
  private void getDataSoruce() {
    if (this.type.equalsIgnoreCase("SAP"))
      this.datasource = DataSourceFactory.getSapDataSource(); 
    if (this.type.equalsIgnoreCase("CRM"))
      this.datasource = DataSourceFactory.getCrmDataSource(); 
  }
  
  private void getConnection() throws SQLException {
    if (this.con == null || this.con.isClosed())
      this.con = this.datasource.getConnection(); 
  }
  
  public void beginTransaction() {
    this.transaction = true;
    try {
      getConnection();
      this.con.setAutoCommit(false);
    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException("transaction not supported");
    } 
  }
  
  public void rollBack() {
    try {
      this.con.rollback();
      this.transaction = false;
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if (this.con != null)
          this.con.close(); 
      } catch (Exception exception) {}
      this.transaction = false;
      this.con = null;
    } 
  }
  
  public void commit() {
    try {
      this.con.commit();
      this.transaction = false;
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if (this.con != null)
          this.con.close(); 
      } catch (Exception exception) {}
      this.con = null;
      this.transaction = false;
    } 
  }
  
  public List<Map<String, Object>> getResultSet(String query, Object[] param) throws Exception {
    try {
      getConnection();
      return (new QueryExecuter(this.con)).getResultSet(query, param);
    } catch (Exception ex) {
      throw ex;
    } finally {
      if (!this.transaction && 
        this.con != null) {
        try {
          this.con.close();
        } catch (Exception exception) {}
        this.con = null;
      } 
    } 
  }
  
  public List<Map<String, Object>> getResultSet(String query) throws Exception {
    return getResultSet(query, null);
  }
  
  public Map<String, Object> getSingleResult(String query, Object[] param) throws Exception {
    List<Map<String, Object>> lst = new ArrayList<>();
    try {
      lst = getResultSet(query, param);
    } catch (Exception ex) {
      throw ex;
    } 
    if (lst.size() == 1)
      return lst.get(0); 
    if (lst.size() > 1)
      throw new Exception("No unique result found"); 
    return null;
  }
  
  public Map<String, Object> getSingleResult(String query) throws Exception {
    return getSingleResult(query, null);
  }
  
  public int executeUpdate(String query, Object[] param) throws Exception {
    try {
      getConnection();
      return (new QueryExecuter(this.con)).updateExecute(query, param);
    } catch (Exception ex) {
      throw ex;
    } finally {
      if (!this.transaction && 
        this.con != null) {
        try {
          this.con.close();
          this.con = null;
        } catch (Exception exception) {}
        this.con = null;
      } 
    } 
  }
  
  public int executeUpdate(String query, Object[] param, AutoKeyNumber keyHolder) throws Exception {
    try {
      getConnection();
      return (new QueryExecuter(this.con)).updateExecute(query, param, keyHolder);
    } catch (Exception ex) {
      throw ex;
    } finally {
      if (!this.transaction && 
        this.con != null) {
        try {
          this.con.close();
          this.con = null;
        } catch (Exception exception) {}
        this.con = null;
      } 
    } 
  }
}
