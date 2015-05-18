package com.mawell.mappingservice.utils;
import com.mawell.mappingservice.utils.Configuration;
import com.mawell.mappingservice.dbConn.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.io.*;
import java.sql.*;
/**
 * Notification class to handle the notifications.
 * @author Andreas Bjärkmar
 * 2015-04-14: Added functionality to pass on DB-connection.
 *
 */
public class Notification {
	String id;
	String idType;
	String displayName;
	Configuration config = new Configuration();
	String name = "";
	final Logger logger = LogManager.getLogger(Notification.class.getName());
	Connection conn = null;
	public Notification (String s_id, String s_idType, String s_displayName, Connection c) {
		id = s_id;
		idType = s_idType;
		displayName = s_displayName;
		conn = c;
	}
	public Notification (String s_id, String s_idType, String s_displayName) {
		id = s_id;
		idType = s_idType;
		displayName = s_displayName;
	}
	public Notification (Configuration in_config){
		id=null;
		idType=null;
		config = in_config;
	}
	String defaultAddress = config.getNotificationAddress();
	
	
	
	/**
	 * This method sends a notification according to rules set up in configuration.
	 * Changed to pass the db-connection.
	 */
	
	public void sendNotification(){
		DbNotifications notifications = new DbNotifications(conn);
		//if (notifications.checkNotification(id, idType)) return;
		String eMail = config.getNotificationAddress(idType);
		try {
			if (notifications.checkNotification(id, idType) != false){
				notifications.setValues(id, idType, displayName, true, eMail);
			}
		} catch (SQLException e) {
			// Auto-generated catch block
			logger.error("Could not connect with notification databese");

		
		}
	}	
		
	/**
	 * This method sends a notification to a specified address.
	 * @param emailAddress
	 *
	public void sendNotificationTo(String emailAddress){
		
	}
	*/
	
	
	
	
	/**
	 * This method sends a custom message to the default message receiver.
	 * @param emailAddress
	 */
	public void sendNotificationMessage(String inputMessage){
		sendEmail(inputMessage, defaultAddress); 
	}
	
	
	/**
	 * This method writes a line to the log file.
	 * @param message
	 * @param code
	 */

	private void writeLog(String message, int code){
		//Create string to write to file.
		Date datum = new Date();
		String line=datum.toString()+" | " + code + " | " + message;
		String logFilePath = config.getCurrentLogFile();
		File log = new File(logFilePath);
		if (log.length() < 1000000){
			log = new File(config.newLogFile());
		}
		PrintWriter writer = null;
		try {
		    writer = new PrintWriter(new FileWriter(log, true));
		    writer.println(line);
		} catch (IOException ex) {
		  // report
		} finally {
		   if (writer != null) writer.close();
		}
	}
	
	
	
	/**
	 * 
	 * @param message
	 * @return
	 */
	private void sendEmail(String message, String toAddress){
		Mail sender = new Mail();
		int c = 123;
		writeLog(message, c);
		try{
			sender.SendMail(message, toAddress);
		}
		catch (Exception e) {
			logger.error("The e-mail notification cannot be sent.");
		}
	}	
}
