package com.mawell.mappingservice.dbConn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mawell.doa.DbConnection;


/**
 * 
 * @author Andreas Bjärkmar
 * @date 2014-03-04
 * @version 0.1
 *
 */
public class DbNotifications {

	PreparedStatement query = null;
	String resultString = null;
	String[][] returnString = null;
	Connection conn = null;
	String errorCode = null;
	
	//temp:
	String hsaid;
	String int_id;
	
	/**
	 * This method returns all notifications of a time 
	 * @param int days the number of days for how long back to look.
	 * @return ResultSet with the results corresponding to request
	 * @throws SQLException
	 */
	public ResultSet getPreviousNotifications(int days) throws SQLException{
		ResultSet res = null;
		//Get results from database.
		String queryString = "SELECT *";
//		String[] headers = this.getHeaders();
//		for (int i=0;i<headers.length-1;i++) queryString=queryString+headers[i]+" ,";
		queryString=queryString + " FROM NotificationTable WHERE DATE_SUB(CURDATE(), INTERVAL "
				+ days + " DAY) <= time ;";
		
//		mysql> SELECT something FROM tbl_name
//	    -> WHERE DATE_SUB(CURDATE(),INTERVAL 30 DAY) <= date_col;
		
		try{
			conn = DbConnection.MappingDbConn().getConnection();
			query=conn.prepareStatement(queryString);
			res = query.executeQuery();
			conn.close();
		}
		catch (SQLException se){
			se.printStackTrace();
		}
		catch (Exception e){
			e.printStackTrace();
		}
		finally{
			if(conn != null) conn.close();
		}
		return res;
	}


	public void setValues(String id, String idType, String name, boolean sent, String eMail) throws SQLException {
		
		String sqlQuery = "INSERT INTO NotificationTable(id , id_type, time, sent, name, notification_receiver) "
				+ "VALUES(?, ?, now(), ?, ?, ?);";//TODO get Table name from config?
		
		try {
			conn = DbConnection.MappingDbConn().getConnection(); //Get the connection "MappingDbConn"
			query = conn.prepareStatement(sqlQuery); //SQL query
			//Set parameters.
			query.setString(1, id);
			query.setString(2, idType);
			query.setBoolean(3, sent);
			query.setString(4, name);
			query.setString(5, eMail);
			//Execute query
			query.executeUpdate();
			conn.close();
		}
		catch(SQLException se) {
			se.printStackTrace();
			throw se;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if (conn != null) conn.close();
		}
	}
	
	/**
	 * This method returns the headers that is used in the DB table.
	 * The values should come from a config file.
	 * @return
	 */
	public String[] getHeaders(){
		String[] headers = {"id","hsaid","id_type","added", "name"}; //TODO get from config file.
		return headers;
	}
	
	/**
	 * getTableHeaders()
	 * Returns the column names of the table for the object. Note that 
	 * it will not return the index column name. Thus, the array is 
	 * 1 element shorter than the number of columns in the table.
	 * 
	 * @date 2014-03-10
	 * @author Andreas Bjärkmar
	 * @return String[] an array with the column names of the table.
	 * @throws SQLException
	 * @version v1.0
	 */
	public String[] getTableHeaders() throws SQLException{
		String[] headers;
		try{
			conn = DbConnection.MappingDbConn().getConnection(); //Get the connection "MappingDbConn"
			query = conn.prepareStatement("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name = 'mappingtable' AND table_schema = 'mappingdb';"); //This is the SQL query
			ResultSet rs = query.executeQuery();
			rs.last();
			headers = new String[rs.getRow()-1]; //Creating array with length of1 less than last row.
			rs.first();
			int i = 0;
			while(rs.next()){
				headers[i] = rs.getString(1); //place column names from DB in array.
				i++;
			}
			conn.close(); //Close connection
		}
		catch (Exception e){
			e.printStackTrace();
			errorCode = "400";
			return null; //TODO Proper error handling.
		}
		finally{
			if (conn != null) conn.close(); //Close connection.
		}
		return headers;
	}
	
	/**
	 * This method returns the error code if an exception is thown of the class.
	 * If no error was found the return string is null.
	 * 
	 * @return String errorCode
	 */
	public String getErrorCode(){
		return errorCode;
	}
}
