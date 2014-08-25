package com.mawell.mappingservice.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.mawell.doa.DbConnection;

//import com.mawell.mapid.FieldSet;
//import com.mawell.mappingservice.utils.Configuration;
import com.mawell.mappingservice.dbConn.DbGetMapping;

/**
 * Servlet implementation class InsertEntry
 */
@WebServlet("/Exporter/*")
public class Exporter extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Exporter() {
        super();
        // TODO Auto-generated constructor stub
    }
   
    
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//String path = "C:/Workspace/Eclipse/glassfish4/glassfish4/glassfish/domains/domain1/eclipseApps/com.mawell.mappingservice/files/dbexport.csv";
		String path = request.getServletContext().getRealPath("") + "/files/dbexport.csv";
		String dbcontent = getDatabaseContent();
		PrintWriter writer = new PrintWriter(path, "UTF-8");
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
	        
	        path = "http://localhost:8080/com.mawell.mappingservice/files/dbexport.csv";//TODO get from config file
	        try {
	        	response.getWriter().write(path);
	        }
	        catch(Exception e){
	        	e.printStackTrace();
	        }
	        finally{
	        	out.flush();
	        	out.close();
	        }

		}
	
	private String getDatabaseContent(){
		//Initiate csv-file.
		String csvString = "Id;RIS-id;HSA-id;Namn;Tillagd\r";//TODO Get from config
		
		try {
			//Create connection
			Connection conn1 = DbConnection.MappingDbConn().getConnection(); 
			// Get results from database.
			DbGetMapping database = new DbGetMapping(conn1);
			ResultSet results;
		
			results = database.getAllResults();
			
		// Loop result set and construct file.
			while(results.next()){
				csvString = csvString + results.getString("id")+";"+results.getString("id_type")
						+";"+results.getString("hsaid")+";"+results.getString("name")+";"+results.getString("added")+"\r";
			}
			conn1.close(); //TODO move to finally statement
		}
		catch (SQLException se){
			se.printStackTrace();
			return null;
		}
		catch (Exception e){
			e.printStackTrace();
		}
		finally {
		}
		//System.out.println(csvString);
		//Return result
		return csvString;
	}
}
