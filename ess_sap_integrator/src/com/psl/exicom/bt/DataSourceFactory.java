package com.psl.exicom.bt;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.mysql.cj.jdbc.MysqlDataSource;
import javax.sql.DataSource;

public class DataSourceFactory {
  public static DataSource getSapDataSource() {
    SQLServerDataSource msssqldb = new SQLServerDataSource();
    msssqldb.setURL(App.SAPDBURL);
    msssqldb.setUser(App.SAPDBUSERNAME);
    msssqldb.setPassword(App.SAPDBPASS);
    return (DataSource)msssqldb;
  }
  
  public static DataSource getCrmDataSource() {
    MysqlDataSource mysqlDatasource = new MysqlDataSource();
    mysqlDatasource.setUrl(App.CRMDBURL);
    mysqlDatasource.setUser(App.CRMDBUSERNAME);
    mysqlDatasource.setPassword(App.CRMDBPASS);
    return (DataSource)mysqlDatasource;
  }
}
