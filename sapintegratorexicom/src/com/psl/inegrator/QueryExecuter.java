package com.psl.inegrator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class QueryExecuter {
	private static final Logger LOGGER=LogManager.getLogger(QueryExecuter.class);
	Connection con;
	public QueryExecuter(Connection con) {
		this.con=con;
	}
	List<Map<String,Object>> getResultSet(String query,Object[] objs) throws Exception{
		PreparedStatement pst=null;
		ResultSet rst=null;
		List<Map<String,Object>> lst=new ArrayList<Map<String,Object>>();
		try {
			pst=con.prepareStatement(query);
			if(objs!=null) {
				for(int i=0;i<objs.length;i++) {
					pst.setObject((i+1),objs[i]);
				}
			}
			rst= pst.executeQuery();
			ResultSetMetaData rsmt=rst.getMetaData();
			int columnCount=rsmt.getColumnCount();
			while(rst.next()) {
				Map<String,Object> mp=new HashMap<String,Object>();
				for(int i=1;i<=columnCount;i++) {
					//mp.put(rsmt.getColumnName(i),rst.getObject(i));
					String label=rsmt.getColumnLabel(i);
					if(mp.containsKey(label))
						throw new Exception("duplicate alise");
					mp.put(label, rst.getObject(i));
				}
				lst.add(mp);
				
			}
		}catch(Exception ex) {
			LOGGER.error(query,ex);
			
			throw ex;
		}
		finally {
			if(rst!=null) {
				try {
					rst.close();
				}catch(Exception ex) {}
				rst=null;
			}
			if(pst!=null) {
				try {
					pst.close();
				}catch(Exception ex) {
					
				}
				pst=null;
			}
		}
		return lst;
	}
	
	public int updateExecute(String query,Object[] param) throws Exception{
		PreparedStatement pst=null;
		try {
			pst=con.prepareStatement(query);
			if(param!=null&&param.length>0) {
				for(int i=0;i<param.length;i++) {
					pst.setObject((i+1), param[i]);
					
				}
			}
			return pst.executeUpdate();
		}catch(Exception ex) {
			LOGGER.error(query,ex);
			if(param!=null&&param.length>0) {
				for(Object o:param) {
					if(o!=null)
						System.out.println(o.toString());
				}
			}
			throw ex;
		}
		finally {
			if(pst!=null) {
				pst.close();
			}
			pst=null;
		}
		
	}
	public int updateExecute(String query,Object[] param,AutoKeyNumber keyHodler) throws Exception{
		ResultSet rst=null;
		PreparedStatement pst=null;
		
		try {
			 pst=con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
			if(param!=null&&param.length>0) {
				for(int i=0;i<param.length;i++) {
					
					pst.setObject((i+1), param[i]);
					
				}
			}
			int ri=pst.executeUpdate();
			
			rst=pst.getGeneratedKeys();
			if(rst.next())
				keyHodler.setKey(rst.getLong(1));
			return ri;
		}catch(Exception ex) {
			LOGGER.error(query,ex);
			if(param!=null&&param.length>0) {
				for(Object o:param) {
					System.out.println(o.toString());
				}
			}
			throw ex;
		}
		finally {
			if(rst!=null)
				rst.close();
			if(pst!=null) {
				pst.close();
			}
			rst=null;
			pst=null;
		}
		
		
	}
}
