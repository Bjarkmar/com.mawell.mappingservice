package com.mawell.mappingservice.batch;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.Timer;

import org.mawell.doa.DbConnection;

import com.mawell.mappingservice.utils.Configuration;
import com.mawell.mappingservice.utils.Mail;

@Stateless
public class ScheduledTask {

    /**
     * Default constructor. 
     */
    public ScheduledTask() {
        System.out.println("Scheduled task initiated");// TODO Auto-generated constructor stub
    }
	
	//@SuppressWarnings("unused")
	@Schedule(second="0", minute="25", hour="15", dayOfWeek="Mon-Fri",
      dayOfMonth="*", month="*", year="*", info="MyTimer")
    private void scheduledTimeout(final Timer t) {
        System.out.println("@Schedule called at: " + new java.util.Date());
        
        //Get e-mail addresses to be notified.
        Configuration config = new Configuration();
        String[] addressList = new String[2];
        addressList[0]= config.getNotificationAddress(); //This should be changed to String[]
        addressList[1]= config.getNotificationAddress();
        
        
        try{
        	String queryString = "SELECT * FROM notificationtable WHERE notification_receiver = ?";
        	Connection conn = DbConnection.MappingDbConn().getConnection(); 
        	PreparedStatement query = conn.prepareStatement(queryString); //SQL query
        	//Start to write notifications into a table.
        	for(int i = 0 ; i<addressList.length ; i++){
        		String mailContent = config.getNotifiactionMessage();
        		mailContent += "<table border='0'>";
        		query.setString(1, addressList[i].toUpperCase());//Change to ...List[i]
        		ResultSet res = query.executeQuery();
        		while(res.next()){
        			mailContent += "<tr><td> " + res.getString("name") + " </td>"; //First column
            		mailContent += "<td> "+res.getString("id")+" </td>"; //Second column
            		mailContent += "<td> "+res.getString("id_type")+" </td>"; //Third column
            		mailContent += "<td> "+res.getString("time")+" </td>"; //Fourth column
        			mailContent = mailContent + "<td> <a href='"+ config.getWebAddress() + "index.html?"
        					+ "name="+res.getString("name")+"&id="+res.getString("id")+"&id_type=" 
        					+ res.getString("id_type") + "'>L�gg till mappning</a> </td></tr>";
        		}
        		mailContent += "</table>";
        		Mail eMail = new Mail();
        		eMail.SendMail(mailContent, "andreas.bjarkmar@mawell.com");
        		System.out.println(mailContent);
        	}
			if(conn!= null) conn.close();
        }
        catch(SQLException se){
        	//TODO Error handling
        }
        catch(Exception e){
        	//TODO Error handling
        }
        try{
        	
        }
        catch(Exception e){
        	e.printStackTrace();
        }
    }
}