package com.psl.exicom;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqlExecutor {

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(ReportExecuter.readOnlyDb, ReportExecuter.dbUserName,
                ReportExecuter.dbUserPass);
    }

    private Connection getConnection(boolean write) throws SQLException {
        if (write)
            return DriverManager.getConnection(ReportExecuter.dbUrl, ReportExecuter.dbUserName,
                    ReportExecuter.dbUserPass);
        else
            return DriverManager.getConnection(ReportExecuter.readOnlyDb, ReportExecuter.dbUserName,
                    ReportExecuter.dbUserPass);

    }

    public void executeQuery(String query, File file) throws Exception {
        long totalRecord = getQueryCounter(query);
        long executedQuery = 0L;
        boolean headerfound = false;
        int columncount = 0;
        int excelDataRowNumber = 0;
        FileOutputStream fout = null;
        if (totalRecord > 0L) {
            try {
                fout = new FileOutputStream(file);
                while (totalRecord > executedQuery) {
                    Connection con = null;
                    PreparedStatement pst = null;
                    try {
                        con = getConnection();
                        pst = con.prepareStatement(
                                query + " limit " + ReportExecuter.limitRange + " OFFSET " + executedQuery);
                        pst.setQueryTimeout(60);
                        ResultSet rst = pst.executeQuery();
                        if (!headerfound) {
                            ResultSetMetaData rsmt = rst.getMetaData();
                            columncount = rsmt.getColumnCount();
                            for (int i = 1; i <= columncount; i++) {

                                if (i != 1)
                                    fout.write(",".getBytes(StandardCharsets.UTF_8));
                                fout.write(rsmt.getColumnLabel(i).getBytes(StandardCharsets.UTF_8));
                            }
                            fout.write("\r\n".getBytes(StandardCharsets.UTF_8));
                            fout.flush();
                            headerfound = true;
                            excelDataRowNumber++;
                        }
                        int _srecord = 0;
                        while (rst.next()) {
                            for (int i = 1; i <= columncount; i++) {
                                if (i != 1)
                                    fout.write(",".getBytes(StandardCharsets.UTF_8));
                                String data = rst.getString(i);
                                if (data != null) {
                                    data = data.replaceAll("\r", "");
                                    data = data.replaceAll("\n", "");

                                    // data=data.replaceAll(",",";");
                                    data = "\"" + data + "\"";
                                    fout.write(data.getBytes(StandardCharsets.UTF_8));
                                }
                            }
                            fout.write("\r\n".getBytes(StandardCharsets.UTF_8));
                            fout.flush();
                            executedQuery++;
                            _srecord++;
                        }
                        try {
                            rst.close();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        rst = null;
                        System.out.println("Total Record -> " + totalRecord + ", and total Done -> " + executedQuery);
                        // log.info("Total Record -> "+totalRecord+", and total Done ->
                        // "+executedQuery);
                        if (_srecord == 0)
                            break;
                    } catch (Exception ex) {
                        System.out.println("error in " + query);
                        ex.printStackTrace();
                        throw ex;
                    } finally {
                        if (pst != null) {
                            try {
                                pst.close();
                                pst = null;
                            } catch (Exception exd) {
                            }
                        }
                        if (con != null) {
                            try {
                                con.close();
                                con = null;
                            } catch (Exception ex) {

                            }
                        }
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                throw ex;
            } finally {
                try {
                    fout.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private long getQueryCounter(String query) {
        StringBuilder countQuery = new StringBuilder("select count(*) from (" + query + ") ctquery");
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rst = null;
        try {
            con = getConnection();
            pst = con.prepareStatement(countQuery.toString());
            rst = pst.executeQuery();
            if (rst.next()) {
                return rst.getLong(1);
            }
        } catch (Exception ex) {
            System.out.printf("Error in counting query %s", query);
            ex.printStackTrace();
        } finally {
            if (rst != null) {
                try {
                    if (!rst.isClosed())
                        rst.close();
                    rst = null;
                } catch (Exception ex) {

                }

            }
            if (pst != null) {
                try {
                    if (!pst.isClosed())
                        pst.close();
                    pst = null;

                } catch (Exception ex) {

                }
            }
            if (con != null) {
                try {
                    if (!con.isClosed())
                        con.close();
                    con = null;

                } catch (Exception ex) {

                }

            }
        }
        // }
        return 0L;
    }

    public void executeReport() {
        try {
            List<Map<String, Object>> pendingResult = getResultSet(
                    "select * from report_request where report_status='Pending'", null);
            if (pendingResult != null) {
                for (Map<String, Object> pending : pendingResult) {
                    try {
                        updateExecute("update report_request set report_status=? where id=?",
                                new Object[] { "Running", pending.get("id") });
                        File file = new File(
                                ReportExecuter.filePath + File.separator + pending.get("id").toString() + ".csv");
                        System.out.println("Report Execution Started :: Request Id " + pending.get("id").toString());
                        executeQuery(pending.get("query").toString(), file);
                        System.out.println("Report Execution Finished :: Request Id " + pending.get("id").toString());
                        long fileSize = 0;
                        if (file.exists() && file.isFile()) {
                            fileSize = file.length();
                        }
                        updateExecute("update report_request set report_status=?,file_size=? where id=?",
                                new Object[] { "Completed", fileSize, pending.get("id") });
                    } catch (Exception ex) {
                        String errorMessage = ex.getMessage();
                        if (errorMessage != null && errorMessage.length() > 249) {
                            errorMessage = errorMessage.substring(0, 249);
                        } else if (errorMessage == null) {
                            errorMessage = "";
                        }

                        updateExecute("update report_request set report_status=?,file_size=? where id=?",
                                new Object[] { "Error" + errorMessage, 0, pending.get("id") });
                        System.out.println("Error In Query :: " + pending.get("query").toString());
                        ex.printStackTrace();
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    List<Map<String, Object>> getResultSet(String query, Object[] objs) throws Exception {
        PreparedStatement pst = null;
        ResultSet rst = null;
        Connection con = getConnection();
        List<Map<String, Object>> lst = new ArrayList<Map<String, Object>>();
        try {
            pst = con.prepareStatement(query);
            if (objs != null) {
                for (int i = 0; i < objs.length; i++) {
                    pst.setObject((i + 1), objs[i]);
                }
            }
            rst = pst.executeQuery();
            ResultSetMetaData rsmt = rst.getMetaData();
            int columnCount = rsmt.getColumnCount();
            while (rst.next()) {
                Map<String, Object> mp = new HashMap<String, Object>();
                for (int i = 1; i <= columnCount; i++) {
                    // mp.put(rsmt.getColumnName(i),rst.getObject(i));
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
                } catch (Exception ex) {
                }
                rst = null;
            }
            if (pst != null) {
                try {
                    pst.close();
                } catch (Exception ex) {

                }
                pst = null;
            }
            if (con != null) {
                try {
                    con.close();
                } catch (Exception ex) {
                }
                con = null;
            }
        }
        return lst;
    }

    public int updateExecute(String query, Object[] param) throws Exception {
        PreparedStatement pst = null;
        Connection con = null;
        try {
            con = getConnection(true);
            pst = con.prepareStatement(query);
            if (param != null && param.length > 0) {
                for (int i = 0; i < param.length; i++) {
                    pst.setObject((i + 1), param[i]);
                }
            }
            return pst.executeUpdate();
        } catch (Exception ex) {

            if (param != null && param.length > 0) {
                for (Object o : param) {
                    if (o != null)
                        System.out.println(o.toString());
                }
            }
            throw ex;
        } finally {
            if (pst != null) {
                pst.close();
            }
            pst = null;
            if (con != null)
                con.close();
            con = null;
        }

    }
}
