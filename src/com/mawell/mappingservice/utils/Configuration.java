package com.mawell.mappingservice.utils;

import java.util.Date;

//import com.mawell.mappingservice.batch.ConfigurationTest;
public class Configuration {
	public Configuration(){
	}
	//Configurations test
	String tableName = "MappingTable";
	String notificationAddress = "andreas.bjarkmar@mawell.com";
	String logFileRoot = "C:/temp/";
	String logFileName = "MappingServiceLog.txt";
	String rootSource = "http://172.16.40.29:8080/";
	String link = "com.mawell.mappingservice/";
	String notificationTableName = "ConfigTable";
	
	int noOfDays = 1;
	
	String exportFileExtention = "/files/dbexport.csv";
	String[] headers = {"id","hsaid","id_type","added", "name"};
	String notificationTime = "10:20"; //On the format hh:mm
	//String path = request.getServletContext().getRealPath("") + "/files/dbexport.csv";
	
	//Configurations customer test
	
//	String tableName = "MappingTable";
//	String notificationAddress = "andreas.bjarkmar@mawell.com";
//	String logFileRoot = "C:/temp/";
//	String logFileName = "MappingServiceLog.txt";
//	String exportFileRoot = "C:/temp/";
//	String rootSource = "http://mapper1.bfr.vgregion.se:8080/";
//	String link = "com.mawell.mappingservice/";
//	String notificationTableName = "ConfigTable";
//	int noOfDays = 1;
	
	
	//Not used in current set up.
	int maxMailPerUnit = 5; //The maximum number of mails to be sent during the specified time above.
	int minTimeBetweenTwoEmails = 10;//A notification is not sent if last is sent within the previous n seconds.
	
	
	/**
	 * Return table name.
	 * @return String Table Name
	 */
	public String getTableName(){
		return tableName;
	}
	public String[] getHeaders(){
		String[] headers2 = {"id","hsaid","id_type","added", "name"};
		return headers;
	}
	
	public String getNotifTable(){
		return notificationTableName;
	}
	
	public String getNotifiactionMessage(){
		
		String message = "Följande avdelningar saknar korrekt HSA-id. Klicka på länken till höger i respektive rad för att lägga till HSA-id alternativt klicka <a href='"+rootSource+link+"'>här</a> för att lägga till en ny mappning.<br/><br/>";
		return message;
	}
	/**
	 * Returns the address where to send the e-mail accordning to a list in config file.
	 * @param idType String The returned e-mail address is dependent on which criteria according to a schema.
	 * @return String Returns the address where to send the e-mail.
	 */
	public String getNotificationAddress(){
		return "andreas.bjarkmar@mawell.com";
	}
	public String getNotificationAddress(String criteria){
		String retval;
		switch (criteria) {
		case "ris1": retval = "andreas.bjarkmar@mawell.com";
				break;
		case "ris2": retval ="abjarkmar@gmail.com";
				break;
		default: retval = notificationAddress;
		}
		return retval;
	}
	
	public String getCurrentLogFile(){
		return logFileRoot+logFileName;
	}
	public String newLogFile(){
		Date date = new Date();
		logFileName = date.toString();
		return logFileName;
	}
	/**
	 * Returns the path to the graphical web interface without the index.html extention but with a / at the end.
	 * @return String the web address.
	 */
	public String getWebAddress(){
		return rootSource+link;
	}
	
	public String getExportFile(){
		return exportFileExtention;
	}
	
	public String getNotificationTime(){
		return notificationTime;
	}
	
	/**
	 * 
	 * @return int[] with int[0]=noOfDays, int[1]=maxMailPerUnit, int[2]=minTimeBetweenTwoEmails
	 */
	public int[] getPrefForNotification(){
		int[] retval = {noOfDays, maxMailPerUnit, minTimeBetweenTwoEmails};
		return retval;
	}
}
