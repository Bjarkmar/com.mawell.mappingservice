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
        System.out.println("Scheduled task executed");// TODO Auto-generated constructor stub
    }
	
	@SuppressWarnings("unused")
	@Schedule(second="0", minute="56", hour="14", dayOfWeek="Mon-Fri",
      dayOfMonth="*", month="*", year="*", info="MyTimer")
    private void scheduledTimeout(final Timer t) {
        System.out.println("@Schedule called at: " + new java.util.Date());
        
        //Get e-mail addresses to be notified.
        Configuration config = new Configuration();
        String addressList = config.getNotificationAddress(); //This should be changed to String[]
        String mailContent = config.getNotifiactionMessage();
        try{
        	String queryString = "SELECT * FROM notificationtable WHERE notification_receiver = ?";
        	Connection conn = DbConnection.MappingDbConn().getConnection(); 
        	PreparedStatement query = conn.prepareStatement(queryString); //SQL query
        	query.setString(1, addressList.toUpperCase());//Change to ...List[i]

			ResultSet res = query.executeQuery();
			String id;
			while(res.next()){
				mailContent = mailContent + res.getString("id");
			}
			System.out.println(mailContent);
        }
        catch(SQLException se){
        	//TODO Error handling
        }
        catch(Exception e){
        	//TODO Error handling
        }
        try{
        	Mail eMail = new Mail();
        	eMail.SendMail(mailContent, "andreas.bjarkmar@mawell.com");
        }
        catch(Exception e){
        	e.printStackTrace();
        }
    }
}