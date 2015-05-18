package com.mawell.mappingservice.batch;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.Timer;

import org.mawell.doa.DbConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//import com.mawell.mapid.MapIdSOAPImpl;
import com.mawell.mappingservice.utils.Configuration;
import com.mawell.mappingservice.utils.Mail;

@Stateless
public class ScheduledTask {
	final Logger logger = LogManager.getLogger(ScheduledTask.class.getName());
    /**
     * Default constructor. 
     */
    public ScheduledTask() {
    	
    	logger.info("Scheduled task initiated");
    }
	Configuration config = new Configuration();
	//String[] timeParts = config.getNotificationTime().split(":");
	//@SuppressWarnings("unused")
	@Schedule(second="0", minute="25", hour="16", dayOfWeek="Mon-Fri",
      dayOfMonth="*", month="*", year="*", info="MyTimer")
    private void scheduledTimeout(final Timer t) {
        logger.info("@Schedule called at: " + new java.util.Date());
        
        //Get e-mail addresses to be notified.
        
        String[] addressList = new String[2];
        addressList[0]= config.getNotificationAddress(); //TODO This should be changed to String[]
        addressList[1]= config.getNotificationAddress();
        
        
        try{
        	String queryString = "SELECT * FROM notificationtable WHERE notification_receiver = ?";
        	Connection conn = DbConnection.MappingDbConn().getConnection(); 
        	PreparedStatement query = conn.prepareStatement(queryString); //SQL query
        	//Start to write notifications into a table.
        	
        	for(int i = 0 ; i<addressList.length ; i++){
        		boolean test = false;
        		String mailContent = config.getNotifiactionMessage();
        		mailContent += "<table border='0'>";
        		//Add headers to mail.
        		mailContent += "<tr><td>Radnummer</td><td>Enhetsnamn</td><td>Id</td><td>RIS</td><td>Tid</td><td>Länk</td></tr>";
        		query.setString(1, addressList[i].toUpperCase());
        		ResultSet res = query.executeQuery();
        		int rowNumber = 1;
        		while(res.next()){
        			test = true;
        			mailContent += "<tr><td>" + rowNumber + "</td><td> " + res.getString("name") + " </td>"; //First column
            		mailContent += "<td> "+res.getString("id")+" </td>"; //Second column
            		mailContent += "<td> "+res.getString("id_type")+" </td>"; //Third column
            		mailContent += "<td> "+res.getString("time")+" </td>"; //Fourth column
        			mailContent = mailContent + "<td> <a href='"+ config.getWebAddress() + "index.html?"
        					+ "name="+res.getString("name")+"&id="+res.getString("id")+"&id_type=" 
        					+ res.getString("id_type") + "'>Lägg till mappning</a> </td></tr>";
        		rowNumber++;
        		}
        		//Send notification mail...
        		mailContent += "</table>";
        		if (test == true){
        			Mail eMail = new Mail();
        			eMail.SendMail(mailContent, addressList[i]);
        			logger.info("E-mail was sent to " + addressList[i]);
        			Statement deleteStatement = conn.createStatement();
        			deleteStatement.executeUpdate("DELETE FROM notificationtable WHERE notification_receiver = '" + addressList[i] + "'");
        		}
        	}
			if(conn!= null) conn.close();
        }
        catch(SQLException se){
        	logger.error("Error in sql while trying to send stored notifications.");
        	se.printStackTrace();
        }
        catch(Exception e){
        	logger.error("An error occured when trying to send e-mail.");
        }
    }
}