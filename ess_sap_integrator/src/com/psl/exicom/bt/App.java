package com.psl.exicom.bt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class App implements Runnable {
  public static String SAPDBURL;
  
  public static String SAPDBPASS;
  
  public static String SAPDBUSERNAME;
  
  public static String CRMDBURL;
  
  public static String CRMDBPASS;
  
  public static String CRMDBUSERNAME;
  
  public static String user;
  
  public static String logFileName;
  
  public static String logDirectory;
  
  public static String fillZero(String msg, int size) {
    StringBuilder builder = new StringBuilder();
    if (msg.length() >= size) {
      builder.append(msg);
      return builder.toString();
    } 
    int restCount = size - msg.length();
    for (int i = 0; i < restCount; i++)
      builder.append("0"); 
    builder.append(msg);
    return builder.toString();
  }
  
  public void run() {
    while (true) {
      try {
        File file = new File(logFileName);
        if (file.exists() && file.isFile()) {
          long size = file.length();
          if (size > 0L) {
            System.out.flush();
            System.err.flush();
            long sizekb = size / 1024L;
            long sizemb = sizekb / 1024L;
            if (sizemb >= 10L) {
              try {
                System.out.close();
                System.err.close();
              } catch (Exception ex) {
                ex.printStackTrace();
              } 
              getLogFileName();
              setLoggingFile();
            } 
          } 
        } 
      } catch (Exception ex) {
        ex.printStackTrace();
      } 
      try {
        Thread.sleep(10000L);
      } catch (InterruptedException e) {
        e.printStackTrace();
      } 
    } 
  }
  
  public static void main(String[] args) {
    try {
      FileInputStream fi = new FileInputStream("sap.properties");
      Properties prp = new Properties();
      prp.load(fi);
      SAPDBURL = prp.getProperty("SAPDBURL");
      SAPDBPASS = prp.getProperty("SAPDBPASS");
      SAPDBUSERNAME = prp.getProperty("SAPDBUSERNAME");
      CRMDBPASS = prp.getProperty("CRMDBPASS");
      CRMDBURL = prp.getProperty("CRMDBURL");
      CRMDBUSERNAME = prp.getProperty("CRMDBUSERNAME");
      user = prp.getProperty("USER");
      logDirectory = prp.getProperty("LOGDIR");
      getLogFileName();
      setLoggingFile();
      App app = new App();
      (new Thread(app)).start();
      final ScheduledExecutorService timer = Executors.newScheduledThreadPool(13);
      Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
              timer.shutdown();
            }
          });
      timer.scheduleWithFixedDelay(new BtPartCodeChange(), 0L, 5L, TimeUnit.MINUTES);
      timer.scheduleWithFixedDelay(new BtProductRequest(), 0L, 15L, TimeUnit.MINUTES);
      timer.scheduleWithFixedDelay(new BtProductTransfer(), 0L, 15L, TimeUnit.MINUTES);
      timer.scheduleWithFixedDelay(new BtExpenseSync(), 0L, 3L, TimeUnit.MINUTES);
      timer.scheduleWithFixedDelay(new BtCustomerMaster(), 0L, 4L, TimeUnit.HOURS);
      timer.scheduleWithFixedDelay(new BtProductSync(), 0L, 4L, TimeUnit.HOURS);
      timer.scheduleWithFixedDelay(new BtVendorPoSynk(), 0L, 15L, TimeUnit.MINUTES);
      timer.scheduleWithFixedDelay(new BtProductConsumeSynk(), 0L, 5L, TimeUnit.MINUTES);
      timer.scheduleWithFixedDelay(new BtSalesDataSynk(), 0L, 1L, TimeUnit.MINUTES);
      timer.scheduleWithFixedDelay(new BtAssets(), 0L, 4L, TimeUnit.HOURS);
      timer.scheduleAtFixedRate(new BtAssetsDismantle(), 0L, 2L, TimeUnit.MINUTES);
      timer.scheduleAtFixedRate(new DocSync(), 0L, 1L, TimeUnit.HOURS);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
  
  static void setLoggingFile() {
    PrintStream ps = null;
    try {
      ps = new PrintStream(logFileName);
      System.setOut(ps);
      System.setErr(ps);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } 
  }
  
  static void getLogFileName() {
    logFileName = String.format("%s%ssapintegrator_%s.txt", new Object[] { logDirectory, File.separator, Long.valueOf((new Date()).getTime()) });
  }
}
