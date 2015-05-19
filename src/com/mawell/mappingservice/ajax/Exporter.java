package com.mawell.mappingservice.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import org.mawell.doa.DbConnection;


import com.mawell.mapid.MapIdSOAPImpl;
//import com.mawell.mapid.FieldSet;
//import com.mawell.mappingservice.utils.Configuration;
import com.mawell.mappingservice.dbConn.DbGetMapping;
import com.mawell.mappingservice.utils.Configuration;

/**
 * Servlet implementation class InsertEntry
 */
@WebServlet("/Exporter/*")
public class Exporter extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final Logger logger = LogManager.getLogger(MapIdSOAPImpl.class.getName());
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Exporter() {
        super();
        // Empty constructor
    }
   
    
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Configuration config = new Configuration();
	    String path = request.getServletContext().getRealPath("") + config.getExportFile();
		String dbcontent = getDatabaseContent();
		PrintWriter writer = new PrintWriter(path, "Cp1252");
		writer.println(dbcontent);
		writer.close();
		

			// Generate response
			PrintWriter out = response.getWriter();
	        response.setContentType("test/plain");
	        response.setCharacterEncoding("UTF-8");
	        response.setHeader("Cache-control", "no-cache, no-store");
	        response.setHeader("Pragma", "no-cache");
	        response.setHeader("Expires", "-1");
	 
	        response.setHeader("Access-Control-Allow-Origin", "*");
	        response.setHeader("Access-Control-Allow-Methods", "POST");
	        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
	        response.setHeader("Access-Control-Max-Age", "86400");
	       
	        try {
	        	response.getWriter().write(config.getWebAddress()+ config.getExportFile());
	        	logger.info("Sending path to servlet: ");
	        	logger.debug(path);
	        }
	        catch(Exception e){
	        	logger.error("Error while writing to file.");
	        }
	        finally{
	        	out.flush();
	        	out.close();
	        }

		}
	
	private String getDatabaseContent(){
		//Initiate csv-file.
		String csvString = "Id;RIS-id;HSA-id;Namn;Tillagd\r";//TODO Get from config
		Connection conn1 = null;
		try {
			//Create connection
			conn1 = DbConnection.MappingDbConn().getConnection(); 
			// Get results from database.
			DbGetMapping database = new DbGetMapping(conn1);
			ResultSet results;
		
			results = database.getAllResults();
			
		// Loop result set and construct file.
			while(results.next()){
				csvString = csvString + results.getString("id")+";"+results.getString("id_type")
						+";"+results.getString("hsaid")+";"+results.getString("name")+";"+results.getString("added")+"\r";
			}
			results.close();
			conn1.close();
		}
		catch (SQLException se){
			logger.error("Error while getting results from database.");
			logger.debug(se.getMessage());
			return null;
		}
		catch (Exception e){
			logger.error("Error while constructing the Export file.");
			logger.debug(e.getMessage());
		}
		finally {
			try {
					conn1.close();
				} catch (Exception sse){
					logger.error("Can not close database connection.");
				}
		}
		return csvString;
	}
}
