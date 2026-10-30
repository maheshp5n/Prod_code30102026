package com.psl.inegrator;

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
	DataSource datasource=null;

	public SqlHelper(String type) {
		this.type = type;
		getDataSoruce();
	}
	private void getDataSoruce() {
		if(type.equalsIgnoreCase("SAP"))
			this.datasource=DataSourceFactory.getSapDataSource();
		if(type.equalsIgnoreCase("CRM"))
			this.datasource=DataSourceFactory.getCrmDataSource();
	}

	private void getConnection() throws SQLException {
		if(con==null||con.isClosed()) {
			con=this.datasource.getConnection();	
		}
		if(!transaction)
			this.con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
	}

	public void beginTransaction() {
		
		this.transaction = true;
		try {
			getConnection();
			con.setAutoCommit(false);
			con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public void rollBack() {
		try {
			
			con.rollback();
			transaction=false;
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.close();
				
			} catch (Exception ex) {
			}
			transaction=false;
			con=null;
		}

	}

	public void commit() {
		try {
			con.commit();
			transaction=false;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.close();
				
			} catch (Exception ex) {
			}
			con=null;
			transaction=false;
		}

	}

	public List<Map<String, Object>> getResultSet(String query, Object[] param) throws Exception {
		try {
			getConnection();
			return new QueryExecuter(con).getResultSet(query, param);
		} catch (Exception ex) {
			
			throw ex;
		} finally {
			if (!transaction) {
				if (con != null) {
					try {
						con.close();
						
					} catch (Exception ex) {
					}
					con = null;
				}
			}
		}
	}

	public List<Map<String, Object>> getResultSet(String query) throws Exception {
		return getResultSet(query, null);
	}

	public Map<String, Object> getSingleResult(String query, Object[] param) throws Exception {
		List<Map<String, Object>> lst = new ArrayList<Map<String, Object>>();// getResultSet(query,param);
		try {
			lst = getResultSet(query, param);
		} catch (Exception ex) {
			
			throw ex;
		}
		if (lst.size() == 1) {
			return lst.get(0);
		} else if(lst.size()>1)
			throw new Exception("No unique result found");
		return null;
	}

	public Map<String, Object> getSingleResult(String query) throws Exception {
		return getSingleResult(query, null);
	}

	
	public int executeUpdate(String query, Object[] param) throws Exception {
		
		try {
			getConnection();
			return new QueryExecuter(con).updateExecute(query, param);
		} catch (Exception ex) {
			
			throw ex;
		} finally {
			if (!transaction) {
				if (con != null) {
					try {
						con.close();
						con = null;
					} catch (Exception ex) {
					}
					con = null;
				}
			}
		}
	}
	public int executeUpdate(String query, Object[] param,AutoKeyNumber keyHolder) throws Exception {
		try {
			getConnection();
			return new QueryExecuter(con).updateExecute(query, param,keyHolder);
		} catch (Exception ex) {
			
			throw ex;
		} finally {
			if (!transaction) {
				if (con != null) {
					try {
						con.close();
						con = null;
					} catch (Exception ex) {
					}
					con = null;
				}
			}
		}
	}
}
