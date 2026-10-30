package com.pratikshat.exicom.sap;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SapConnectionProvider {
	@Value("${sap.datasource.url}")
	private String connectionDatasource;
	@Value("${sap.datasource.username}")
	private String connecctionUsername;
	@Value("${sap.datasource.password}")
	private String connectionUserpassword;
	
	
	private Connection getConnection() throws SQLException {
		Connection con=DriverManager.getConnection(connectionDatasource,connecctionUsername,connectionUserpassword);
		return con;
	}

	public Connection beginTransaction()  {
		try {
			Connection con=getConnection();
			con.setAutoCommit(false);
			return con;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Map<String,Object>> getResultSet(String query) throws Exception{
		return getResultSet(query,null);
	}
	public List<Map<String,Object>> getResultSet(String query,Object[] params) throws Exception{
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rst =null;
		try {
			con=getConnection();
			pst=con.prepareStatement(query);
			if(params!=null) {
				int counter=params.length;
				for(int i=1;i<=counter;i++) {
					pst.setObject(i,params[(i-1)]);
				}
			}
			rst=pst.executeQuery();
			ResultSetMetaData rsmt=rst.getMetaData();
			List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
			while(rst.next()) {
				Map<String,Object> _obMap=new HashMap<String,Object>();
				for(int j=1;j<=rsmt.getColumnCount();j++) {
					
					_obMap.put(rsmt.getColumnLabel(j),rst.getObject(j));
				}
				result.add(_obMap);
			}
			return result;
		}catch(Exception ex) {
			throw ex;
		}
		finally {
			if(rst!=null) {
				try {
					if(!rst.isClosed()) {
						rst.close();
						rst=null;
					}
				}catch(Exception ex) {
					
				}
			}
			if(pst!=null) {
				try {
					if(!pst.isClosed()) {
						pst.close();
						pst=null;
					}
					else {
						pst=null;
					}
				}catch(Exception ex) {
					
				}
			}
			if(con!=null) {
				try {
					if(!con.isClosed()) {
						con.close();
						con=null;
					}
					else {
						con=null;
					}
				}catch(Exception ex) {}
			}
		}
	}
	public int executeUpdate(String query,Object[] params,Connection _con) throws Exception{
		Connection con=_con;
		PreparedStatement pst=null;
		ResultSet rst =null;
		try {
			if(con==null||con.isClosed())
				con=getConnection();//DriverManager.getConnection(connectionDatasource,connecctionUsername,connectionUserpassword);
			pst=con.prepareStatement(query);
			if(params!=null) {
				int counter=params.length;
				for(int i=1;i<=counter;i++) {
					pst.setObject(i,params[(i-1)]);
				}
			}
			return pst.executeUpdate();
			
		}catch(Exception ex) {
			throw ex;
		}
		finally {
			
			if(rst!=null) {
				try {
					if(!rst.isClosed()) {
						rst.close();
						rst=null;
					}
				}catch(Exception ex) {
					
				}
			}
			if(pst!=null) {
				try {
					if(!pst.isClosed()) {
						pst.close();
						pst=null;
					}
					else {
						pst=null;
					}
				}catch(Exception ex) {
					
				}
			}
			if(con!=null&&con.getAutoCommit()) {
				try {
					if(!con.isClosed()) {
						con.close();
						con=null;
					}
					else {
						con=null;
					}
				}catch(Exception ex) {}
			}
		}
	}
	public void commitTransaction(Connection con) {
		try {
			con.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				if(!con.isClosed()) {
					con.close();
					con=null;
				}
				else {
					con=null;
				}
			}catch(Exception ex) {}
		}
		
	}
	public void rollBack(Connection con) {
		try {
			con.rollback();
			
		}
		catch(Exception ex) {}
		finally {
			try {
				if(!con.isClosed()) {
					con.close();
					con=null;
				}
				else {
					con=null;
				}
			}catch(Exception ex) {
				
			}
		}
	}
}
