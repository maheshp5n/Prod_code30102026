package com.psl.exicom;

import com.mysql.cj.x.protobuf.MysqlxPrepare;

import java.io.*;
import java.sql.Time;
import java.util.Date;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ReportExecuter {
    public static Integer limitRange=1000;
    public static String dbUrl;
    public static String dbUserName;
    public static String dbUserPass;
    public static String filePath;
    public static String logFilePath;
    public static String logFileNamePrefix="report_maker_log";
    public static String finalPathName="";
    public static String readOnlyDb;


    public static void main(String args[]){
        try{
            ReportExecuter.propertyLoader();
            ReportExecuter.setLogFiles(ReportExecuter.getLogFileName());
        }catch (Exception ex){
            System.err.println("Unable to load properties file");
            System.exit(0);
        }
        new ReportExecuter().startExecution();
    }
    public void startExecution(){
        ScheduledExecutorService timer= Executors.newScheduledThreadPool(2);
        timer.scheduleWithFixedDelay(new Executer(),0,1, TimeUnit.MINUTES);
        timer.scheduleWithFixedDelay(new LoggerPathChange(),0,10,TimeUnit.SECONDS);

    }
    public static String getLogFileName(){
        ReportExecuter.finalPathName=ReportExecuter.logFilePath+File.separator+logFileNamePrefix+(new Date().getTime())+".txt";
        return ReportExecuter.finalPathName;
    }

    static void propertyLoader() throws IOException {
        Properties properties=new Properties();
        properties.load(new InputStreamReader(new FileInputStream("report.properties")));
        ReportExecuter.dbUrl=properties.get("dbUrl").toString();
        ReportExecuter.dbUserName=properties.get("dbUserName").toString();
        ReportExecuter.dbUserPass=properties.get("dbUserPass").toString();
        ReportExecuter.limitRange=Integer.parseInt(properties.get("limitRange").toString());
        ReportExecuter.filePath=properties.get("filePath").toString();
        ReportExecuter.logFilePath=properties.getProperty("logFilePath");
        ReportExecuter.readOnlyDb=properties.getProperty("readOnlyDb");
    }
    public  class Executer implements Runnable{
        public  void  run(){
            new SqlExecutor().executeReport();
        }
    }
    public class LoggerPathChange implements Runnable{
        public void run(){
            ReportExecuter.updateLogfile();
        }
    }
    public static void updateLogfile(){
        String filePath=ReportExecuter.finalPathName;
        try {
            File file=new File(filePath);
            if(file.exists()&&file.isFile()) {
                long size=file.length();

                if(size>0) {
                    System.out.flush();
                    System.err.flush();
                    long sizekb=size/1024;
                    long sizemb=sizekb/1024;

                    if(sizemb>=10) {
                        try {
                            System.out.close();
                            System.err.close();
                        }catch(Exception ex) {
                            ex.printStackTrace();
                        }
                        ReportExecuter.setLogFiles(getLogFileName());
                    }
                }
            }


        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    public static void setLogFiles(String filePathname){
        PrintStream ps=null;
        try{
            ps=new PrintStream(filePathname);
            System.setErr(ps);
            System.setOut(ps);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
