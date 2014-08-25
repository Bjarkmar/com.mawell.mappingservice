package org.mawell.doa;

import javax.naming.*;
import javax.sql.*;
/**
 * 
 * @author Andreas Bjärkmar
 * @version 1.0
 * Changed 2014-02-18
 */
public class DbConnection {

	private static DataSource Database = null;
	private static Context context = null;
	
	public static DataSource MappingDbConn() throws Exception {
		
		if (Database != null){
			return Database;
		}
		try {
			if (context == null) {
				context = new InitialContext();
			}
			Database = (DataSource) context.lookup("MappingDB");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return Database;
	}
}
