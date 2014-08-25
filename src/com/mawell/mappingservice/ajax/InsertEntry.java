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
import com.mawell.mappingservice.utils.Notification;

/**
 * Servlet implementation class InsertEntry
 */
@WebServlet("/InsertEntry/*")
public class InsertEntry extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertEntry() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		String idType = request.getParameter("idType");
		String hsaid = request.getParameter("hsaid");
		String name = request.getParameter("name");
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
        	boolean retval = insertIntoDatabase(id, hsaid, idType, name);
        	if (retval ==true){
        		response.getWriter().write("success");
        		return;
        	} else {
        		response.sendError(403, "Could not be inserted into database.");//TODO A specified message should be returned.
        	}
        }
        catch (SQLException se){
        	se.printStackTrace();//TODO Error handling
        	Notification notification = new Notification(id, idType, "Namn saknas");
        	notification.writeLog("There were no connection to the database.", 521);
        }

        out.flush();
        out.close();
	}
	
	
	
	String errorCode = null;
	private boolean insertIntoDatabase(String id, String hsaid, String idType, String name) throws SQLException{
		try{
			DbGetMapping Mapping = new DbGetMapping();
			//Getting values from input object into string arrays.
			String[] column = new String[2];
			String[] row = new String[2];
			column[0]="id"; row[0]=id;
			column[1]="id_type"; row[1]=idType;
			//Get matching previous mappings.
			String[][] temp = Mapping.getResults(column, row);
			if(temp == null){
				DbSetMapping newMapping = new DbSetMapping();
				newMapping.setValues(id, idType, hsaid, name, true);
			}
			else {
				//TODO This might be problematic.
				if(temp.length == 0){
					DbSetMapping newMapping = new DbSetMapping();
					newMapping.setValues(id, idType, hsaid, name, true);
				}
				return false;
			}
			
		}
		catch(SQLException se){
			 throw se;
		}
		return true;
	}
}

