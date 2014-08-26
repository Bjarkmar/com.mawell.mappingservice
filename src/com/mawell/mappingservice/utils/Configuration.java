package com.mawell.mappingservice.utils;

import java.util.Date;
public class Configuration {
	public Configuration(){
		
	}
	//Configurations
	String tableName = "MappingTable";
	String notificationAddress = "andreas.bjarkmar@mawell.com";
	String logFileRoot = "C:/temp/";
	String logFileName = "MappingServiceLog.txt";
	String exportFileRoot = "C:/temp/";
	String rootSource = "http://localhost:8080/";
	String link = "com.mawell.mappingservice/";
	String notificationTableName = "ConfigTable";
	int noOfDays = 1;
	int maxMailPerUnit = 5; //The maximum number of mails to be sent during the specified time above.
	int minTimeBetweenTwoEmails = 10; //A notification is not sent if last is sent within the previous n seconds.
	
	/**
	 * Return table name.
	 * @return String Table Name
	 */
	public String getTableName(){
		return tableName;
	}
	
	public String getNotifTable(){
		return notificationTableName;
	}
	
	public String getNotifiactionMessage(){
		
		String message = "F�ljande avdelningar saknar korrekt HSA-id. Klicka p� l�nken till h�ger i respektive rad f�r att l�gga till HSA-id alternativt klicka <a href='"+rootSource+link+"'>h�r</a> f�r att l�gga till en ny mappning.<br/><br/>";
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
		return exportFileRoot + "export.csv";
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