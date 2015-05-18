package com.mawell.mappingservice.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mawell.mappingservice.dbConn.*;
//import com.mawell.mappingservice.utils.Notification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Servlet implementation class InsertEntry
 */
@WebServlet("/InsertEntry/*")
public class InsertEntry extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final Logger logger = LogManager.getLogger(InsertEntry.class.getName());
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertEntry() {
        super();
        // Empty contructor
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
		response.setHeader("Access-Control-Allow-Methods", "GET");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		String idType = request.getParameter("idType");
		String hsaid = request.getParameter("hsaid");
		String name = request.getParameter("name");
		String override_input = request.getParameter("override");

		Boolean override = override_input.equalsIgnoreCase("true") || override_input.equalsIgnoreCase("1");
		// Generate response
	@SuppressWarnings("unused")
		PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-control", "no-cache, no-store");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "-1");
 
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setHeader("Access-Control-Max-Age", "86400");

        try {
        	boolean retval = insertIntoDatabase(id, hsaid, idType, name, override);
        	if (retval ==true){
        		int status = 200;
        		response.setStatus(status);
        	} else {
        		logger.warn("Entry with id " + id + " and id type " + idType + " and HSA-id " + hsaid + " could not be inserted into the database.");
        		response.sendError(403, "The mapping you are trying to enter already exists. Do you want to replace it?");
        	}
        }
        catch (SQLException se){
        	logger.error("No connection to database. Entry with id " + id + " and id type " + idType + " and HSA-id " + hsaid + " could not be inserted into the database.");
        	response.sendError(520, "Could not insert enrty due to an unknown error");
        }

	}
	
	
	
	String errorCode = null;
	private boolean insertIntoDatabase(String id, String hsaid, String idType, String name, Boolean override) throws SQLException{
		try{
			DbGetMapping Mapping = new DbGetMapping();
			//Getting values from input object into string arrays.
			String[] column = new String[2];
			String[] row = new String[2];
			column[0]="id"; row[0]=id;
			column[1]="id_type"; row[1]=idType;
			//Get matching previous mappings.
			String[][] temp = Mapping.getResults(column, row);
			//If no mapping exists
			if(temp == null){
				if (name == null) {name = "";}
				DbSetMapping newMapping = new DbSetMapping();
				newMapping.setValues(id, idType, hsaid, name, true);
			}
			//If mapping should be overwritten.
			else if(override == true){
				DbSetMapping newMapping = new DbSetMapping();
				newMapping.replaceValues(id, idType, hsaid, name, true);
			}
			else {
				return false;
			}
			
		}
		catch(SQLException se){
			 throw se;
		}
		return true;
	}
}

