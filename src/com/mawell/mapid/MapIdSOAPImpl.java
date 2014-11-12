/**
 * MapIdSOAPImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.mawell.mapid;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mawell.mapid.FieldSet;
import com.mawell.mappingservice.dbConn.DbGetMapping;
import com.mawell.mappingservice.utils.Notification;
import com.mawell.mappingservice.utils.LoggerClass;
import com.mawell.mappingservice.utils.Configuration;
import org.mawell.doa.DbConnection;

public class MapIdSOAPImpl implements com.mawell.mapid.MapId_PortType{
	String errorCode;

    public com.mawell.mapid.FieldSet[] getFields(java.lang.String id, java.lang.String idType, com.mawell.mapid.SearchFields[] input) throws java.rmi.RemoteException {
    	FieldSet[] response = null;
    	String errorCode = null;
    	String displayName;
    	Connection conn=null;
    	final Logger logger = LogManager.getLogger(MapIdSOAPImpl.class.getName());
    	if (input.length > 0) {
    		displayName = input[0].getValue();
    	}
    	else{
    		displayName = "Namn saknas";
    	}
    	if(id.length() > 0 && idType.length() > 0){
    		
    		try {
    			conn = DbConnection.MappingDbConn().getConnection();
        		DbGetMapping Mapper = new DbGetMapping(conn);
    			String[] columns = Mapper.getHeaders(); //Gets the column names of the table.
    			//Getting values from input object into string arrays. Not an optimal solution.
    			String[] cols = new String[2];
    			String[] rows = new String[2];
    			//insert id and id type
    			cols[0]="id"; rows[0]=id;
    			cols[1]="id_type"; rows[1]=idType;
    		//Get mapping
    			String[][] UnknownResult = Mapper.getResults(cols,rows);
    			if (UnknownResult != null){
    				response = new FieldSet[Mapper.getHeaders().length]; //Create an array of proper length.
    				//Parse results into response
    				for(int i=0;i<UnknownResult[0].length;i++){
    					response[i]=new FieldSet(columns[i],UnknownResult[0][i].toString());//Columns is not correct since what is returned is not the same.
    				}
    				logger.info("The following HSA-id was sent to requesting system: " + UnknownResult[0][1]);// Remove. only for debug purposes.
    				
    			}
    			else{
    				//Get error code and create error response
    				errorCode = Mapper.getErrorCode();
    			}
    			
    		}
    		catch (SQLException se){
    			errorCode="520";//Lost connection with database
    			logger.error("Lost connection with database");
    		}
    		catch (Exception e){
    			errorCode="520"; //I get here if sending in unmatched Strings
    			logger.error("The request was not valid.");
    		}
    		finally {
				if (conn != null) {
					try {
						conn.close();
					}
					catch(SQLException e){}
				}
    		}
    		//return response;
    	}
    	else{
			errorCode="512"; //No id or id type was entered.
			logger.warn("No id or id type was entered.");
    	}
    	if(errorCode != null){
    		if (errorCode!="510"){
    			response = new FieldSet[1];
    			response[0]=new FieldSet("error",errorCode);
    		}else{
    			logger.info("Could not find any mapping for id: " + id + " and id type: " + idType);
    			response = new FieldSet[0];//If error is no match in DB send empty response.
    		}
			Notification notification = new Notification(id, idType, displayName);
			notification.sendNotification();
    	}
        return response;
    }
}
