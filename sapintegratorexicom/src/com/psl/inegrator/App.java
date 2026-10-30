package com.psl.inegrator;

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
	// public static String salesLocation;

	public App() {
		System.out.println("testing for application constructor is working or not");
	}

	public static String fillZero(String msg, int size) {
		StringBuilder builder = new StringBuilder();
		if (msg.length() >= size) {
			builder.append(msg);
			return builder.toString();
		} else {
			int restCount = size - msg.length();
			for (int i = 0; i < restCount; i++) {
				builder.append("0");
			}
			builder.append(msg);
		}
		return builder.toString();
	}

	@Override
	public void run() {
		while (true) {
			try {
				File file = new File(App.logFileName);
				if (file.exists() && file.isFile()) {
					long size = file.length();

					if (size > 0) {
						System.out.flush();
						System.err.flush();
						long sizekb = size / 1024;
						long sizemb = sizekb / 1024;

						if (sizemb >= 10) {
							try {
								System.out.close();
								System.err.close();
							} catch (Exception ex) {
								ex.printStackTrace();
							}

							App.getLogFileName();
							setLoggingFile();
						}
					}
				}

			} catch (Exception ex) {
				ex.printStackTrace();
			}

			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String args[]) {
		try {
			FileInputStream fi = new FileInputStream("sap.properties");
			Properties prp = new Properties();
			prp.load(fi);
			App.SAPDBURL = prp.getProperty("SAPDBURL");
			App.SAPDBPASS = prp.getProperty("SAPDBPASS");
			App.SAPDBUSERNAME = prp.getProperty("SAPDBUSERNAME");
			App.CRMDBPASS = prp.getProperty("CRMDBPASS");
			App.CRMDBURL = prp.getProperty("CRMDBURL");
			App.CRMDBUSERNAME = prp.getProperty("CRMDBUSERNAME");
			App.user = prp.getProperty("USER");
			App.logDirectory = prp.getProperty("LOGDIR");
			// App.salesLocation=prp.getProperty("SALESLOCATION");
			App.getLogFileName();
			setLoggingFile();
			App app = new App();
			new Thread(app).start();

			ScheduledExecutorService timer = Executors.newScheduledThreadPool(15);

			Runtime.getRuntime().addShutdownHook(new Thread() {
				public void run() {
					timer.shutdown();
				}
			});

			timer.scheduleWithFixedDelay(new PartcodeChange(), 0, 15, TimeUnit.MINUTES);

			timer.scheduleWithFixedDelay(new ProductRequest(), 0, 15, TimeUnit.MINUTES);

			timer.scheduleWithFixedDelay(new InvoiceNumberSynck(), 0, 15, TimeUnit.MINUTES);

			timer.scheduleWithFixedDelay(new ExpenseSynk(), 0, 15, TimeUnit.MINUTES);

		//	timer.scheduleWithFixedDelay(new BillOfMaterialSynk(), 0, 4, TimeUnit.HOURS);

			timer.scheduleWithFixedDelay(new ProductMaster(), 0, 4, TimeUnit.HOURS);

			timer.scheduleWithFixedDelay(new CustomerMaster(), 0, 4, TimeUnit.HOURS);

			timer.scheduleWithFixedDelay(new ServiceAppointmentExpense(), 0, 15, TimeUnit.MINUTES);

			timer.scheduleWithFixedDelay(new ProductTransfer(), 0, 7, TimeUnit.MINUTES);

			timer.scheduleWithFixedDelay(new VendorPoSynk(), 0, 15, TimeUnit.MINUTES);

			timer.scheduleWithFixedDelay(new Assets(), 0, 4, TimeUnit.HOURS);

			timer.scheduleWithFixedDelay(new ProductConsumeSynk(), 0, 15, TimeUnit.MINUTES);

			timer.scheduleWithFixedDelay(new ServiceOrderSynk(), 0, 7, TimeUnit.MINUTES);

			timer.scheduleWithFixedDelay(new SalesDataSynk(), 0, 10, TimeUnit.MINUTES);

			timer.scheduleWithFixedDelay(new CsToFactoryMovement(), 0, 10, TimeUnit.MINUTES);

		} catch (Exception ex) {

			ex.printStackTrace();
		}
	}

	static void setLoggingFile() {

		PrintStream ps = null;

		try {
			ps = new PrintStream(App.logFileName);
			System.setOut(ps);
			System.setErr(ps);

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
	}

	static void getLogFileName() {

		App.logFileName = String.format("%s%ssapintegrator_%s.txt", App.logDirectory, File.separator,
				new Date().getTime());
	}

}
