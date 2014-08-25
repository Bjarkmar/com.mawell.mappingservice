package com.mawell.mappingservice.dbConn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.mawell.doa.DbConnection;

public class DbSetMapping {
	PreparedStatement query = null;
	Connection conn = null;
	//Create sql query:	
	
//	String sqlQuery = "INSERT INTO MappingTable(id,hsaid,id_type,added,active,name,valid_from) VALUES('id2','hsaid2','ris2',now(),true,'test2',now());";
	public void setValues(String id, String idType, String hsaid, String name, boolean active) throws SQLException {
	
		String sqlQuery = "INSERT INTO MappingTable(id , hsaid, id_type, added, active, name ) "
				+ "VALUES(?, ?, ?, now(), true, ?);";//TODO get Table name from config?
		
		try {
			conn = DbConnection.MappingDbConn().getConnection(); //Get the connection "MappingDbConn"
			query = conn.prepareStatement(sqlQuery); //SQL query
			//Set parameters.
			query.setString(1, id);
			query.setString(2, hsaid);
			query.setString(3, idType);
			query.setString(4, name);
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
}
