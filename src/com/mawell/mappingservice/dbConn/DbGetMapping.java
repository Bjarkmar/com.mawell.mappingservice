package com.mawell.mappingservice.dbConn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mawell.doa.DbConnection;
import com.mawell.mappingservice.utils.Configuration;

/**
 * 
 * @author Andreas Bj�rkmar
 * @date 2014-03-04
 * @version 0.1
 *
 */
public class DbGetMapping {

	PreparedStatement query;
	String resultString = null;
	String[][] returnString = null;
	String errorCode = null;
	Connection conn;
	//temp:
	String hsaid;
	String int_id;
	public DbGetMapping(){
		try {
			Connection conn = DbConnection.MappingDbConn().getConnection(); 
		} catch (Exception se){
			//TODO handle error.
		}
	}
	public DbGetMapping(Connection openConnection){
		conn = openConnection;
	}

	
	public ResultSet getAllResults() throws SQLException{
		ResultSet res = null;
		//Get results from database.
		String queryString = "SELECT ";
		String[] headers = this.getHeaders();
		for (int i=0;i<headers.length-1;i++) queryString=queryString+headers[i]+" ,";
		queryString=queryString+headers[headers.length-1]+" FROM MappingTable;";
		try{

			PreparedStatement query=conn.prepareStatement(queryString);
			res = query.executeQuery();
		}
		catch (SQLException se){
			se.printStackTrace();
		}
		catch (Exception e){
			e.printStackTrace();
		}
		finally{
			//if (res != null) res.close();
			//if (query != null) query.close();
			//if(conn != null) conn.close();
		}
		return res;
	}
	/**
	 * The method returns a String array with all posts in the mapping table that matches
	 * the contraints in the argument in the form of:
	 * select * where column[i] = row[i];
	 * @author Andreas Bj�rkmar
	 * @date 2014-05-21
	 * @version 0.1
	 * 
	 * @param column String[] The column name in mapping table.
	 * @param row String[] The criteria value for each column.
	 *
	 */
	public String[][] getResults(String[] column, String[] row) throws SQLException{
		//Create sql query:		
		String[] headers = this.getHeaders();
		String sqlQuery = "SELECT ";
		for (int i=0; i<headers.length-1; i++){
			sqlQuery = sqlQuery + headers[i] + ", ";
		}
		Configuration config = new Configuration();
		sqlQuery = sqlQuery + headers[headers.length - 1] + " FROM " + config.getTableName() + " WHERE ";
		for (int j=0; j<column.length-1; j++){
				sqlQuery = sqlQuery + column[j] + " = ? AND ";
		}
		sqlQuery = sqlQuery + column[column.length-1] + " = ? ;";
		try {
			//conn = DbConnection.MappingDbConn().getConnection(); //Get the connection "MappingDbConn"
			query = conn.prepareStatement(sqlQuery); //SQL query
			//Set parameters.
			for (int i=0; i<column.length; i++){ 
				query.setString(i+1, row[i].toUpperCase());
			}
			//Execute query
			ResultSet rs = query.executeQuery();
			if(rs.next()!=false){
				rs.last();
				returnString = new String[rs.getRow()][this.getHeaders().length];
				int i=rs.getRow();
				rs.next();
		        while (rs.previous()) {
		        	i--;
		        	for(int j=0;j<this.getHeaders().length;j++){
		        		returnString[i][j] = rs.getString(this.getHeaders()[j]);
		        	}
		        }
			}
			else{
				errorCode ="510";//No matching entries in DB.
				returnString= null;
			}
			
			conn.close(); //Close connection
			
		}
		catch (SQLException se)
		{
			errorCode = "521"; // Database exception
			se.printStackTrace();
			returnString = null;
			throw se;
		}
		catch (Exception e){
			e.printStackTrace();
			errorCode = errorCode + "520";
			returnString = null; //TODO Proper error handling.	
		}
		finally{
			//if (conn != null) conn.close(); //Close connection.
		}
		return returnString;
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
	 * @author Andreas Bj�rkmar
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