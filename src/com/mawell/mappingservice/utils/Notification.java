package com.mawell.mappingservice.utils;

import com.mawell.mappingservice.utils.Configuration;
import com.mawell.mappingservice.dbConn.*;

import java.util.Date;
import java.io.*;
import java.sql.*;

public class Notification {
	String id;
	String idType;
	String displayName;
	Configuration config;
	String name = "";
	public Notification (String s_id, String s_idType, String s_displayName) {
		id = s_id;
		idType = s_idType;
		displayName = s_displayName;
		config = new Configuration();
	}
	public Notification (Configuration in_config){
		id=null;
		idType=null;
		config = in_config;
	}
	String defaultAddress = "andreas.bjarkmar@mawell.com";//TODO config.getNotificationAddress("");
	
	
	
	/**
	 * This method sends a notifiaction according to rules set up in configuration.
	 */
	public void sendNotification(){
		Date currentDate = new Date();
		boolean flag = false;
		//Timestamp currentTime = new Timestamp();
		//Get conditions for when to send a notification.
		int[] prefs = config.getPrefForNotification();
		//Get previous notifications.
		DbNotifications notifications = new DbNotifications();
		try {
			ResultSet rs = notifications.getPreviousNotifications(prefs[0]);
			if (rs.next() == true){
				rs.last();
				if (rs.getRow()<prefs[1]+2){
					//Less than maximum number of sent notifications.
					while (rs.previous()){
						Timestamp ts = rs.getTimestamp("time");
						//Compare time to current notification.
						if (ts.compareTo(currentDate) <500) flag = true; //If too short time since last notif.
					}
					
				}
				else{
					//If maximum number of sent massages is reached.
					flag = true;
				}
			}

			if (flag == false) {
				//Build E-mail message
				String sendMessage = "No matching for id: "+id+" and id type: "+idType +".";
				sendMessage = sendMessage + "\r" + config.getWebAddress() + "index.html?name=" + displayName + "&id="+ id +"&id_type="+idType;
				//Send message.
				sendEmail(sendMessage, config.getNotificationAddress(""));
			}
			notifications.setValues(id, idType, name, !flag);
		} catch (SQLException se){
//TODO proper error handling.
		}
		
		
	}
	
	
	/**
	 * This method sends a notifiaction to a specified address.
	 * @param emailAddress
	 */
	public void sendNotificationTo(String emailAddress){
		//TODO Stub
	}
	
	
	
	
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
	//TODO Is this method called from outside or can it be made private?
	public void writeLog(String message, int code){
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
			writeLog("E-mail could not be sent.", 530);//TODO Perhaps this should come from config.
			//TODO do something here logging+notification
		}
	}	
}
