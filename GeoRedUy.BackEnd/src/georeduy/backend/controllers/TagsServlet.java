package georeduy.backend.controllers;


import georeduy.backend.model.Tag;
import georeduy.backend.util.GeoRedClient;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class TagsServlet extends HttpServlet {
   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    	HttpSession session = request.getSession();
    	Gson gson = new Gson();
		
		try {    	
	    	if (request.getParameter("NewTag") != null) {
	    		request.setAttribute("NewTag", "true");
	    		
	    	} else if (request.getParameter("AddTag") != null) {  
	    		
	        	String name = request.getParameter("Name");
	        	String description = request.getParameter("Description");
	        	
	        	Tag tag = new Tag();
	        	tag.setDescription(description);
	        	tag.setName(name);
	        	
	        	Map<String, String> params  = new HashMap <String, String>();
	    		params.put ("tagInfo", gson.toJson(tag));
	
				GeoRedClient.Post("/Tags/New", params, (String)session.getAttribute("Token"));
	    	}
	    	
	    	Map<String, String> params = new HashMap <String, String>();
			params.put ("from", "0");
			params.put ("count", "10");
	    	
	    	String result = GeoRedClient.Get("/Tags/Get", params, (String)session.getAttribute("Token"));
			Type listType = new TypeToken<ArrayList<Tag>>() {}.getType();
	
			List<Tag> tags = gson.fromJson(result, listType);
			request.setAttribute("Tags", tags);
    	} catch (Exception e) {
        	request.setAttribute("ErrorMsg", e.getMessage());
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
