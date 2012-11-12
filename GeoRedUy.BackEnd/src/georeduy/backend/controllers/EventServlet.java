package georeduy.backend.controllers;


import georeduy.backend.model.Event;
import georeduy.backend.model.Tag;
import georeduy.backend.util.GeoRedClient;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class EventServlet extends HttpServlet {
   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    	HttpSession session = request.getSession();
    	Gson gson = new Gson();
		
		try {    	
	    	if (request.getParameter("NewEvent") != null) {
	    		request.setAttribute("NewEvent", "true");
	    		
	    	} else if (request.getParameter("AddEvent") != null) {  
	    		
	        	String name = request.getParameter("Name");
	        	String description = request.getParameter("Description");
	        	String address = request.getParameter("Address");
	        	double latitude = Double.parseDouble(request.getParameter("Latitude"));
	        	double longitude = Double.parseDouble(request.getParameter("Longitude"));
	        	String imageUrl = request.getParameter("ImageUrl");
	        	Date date = new SimpleDateFormat("dd/MM/yy", Locale.ENGLISH).parse(request.getParameter("dateBegin"));
	        	
	        	Event event = new Event();
	        	event.setName(name);
	        	event.setDescription(description);
	        	event.setAddress(address);
	        	event.setImageUrl(imageUrl);
	        	event.setDate(date);
	        	
	        	Double[] coordinates = {longitude, latitude};
	        	event.setCoordinates(coordinates);
	        	
	        	String tags = request.getParameter("Tags");
	        	if (tags != null && !tags.trim().equals("")) {
	        		String[] tagsStr = request.getParameter("Tags").split(",");
	        		for (String tagStr : tagsStr) {
	        			Tag tag = new Tag();
	        			tag.setName(tagStr.trim());
	        			event.addTag(tag);
	        		}
	        	}
	        	
	        	Map<String, String> params  = new HashMap <String, String>();
	    		params.put ("eventInfo", gson.toJson(event));
	
				GeoRedClient.Post("/Events/New", params, (String)session.getAttribute("Token"));
	    	}
	    	
	    	Map<String, String> params = new HashMap <String, String>();
			params.put ("from", "0");
			params.put ("count", "10");
	    	
	    	String result = GeoRedClient.Get("/Events/Get", params, (String)session.getAttribute("Token"));
			Type listType = new TypeToken<ArrayList<Event>>() {}.getType();
	
			List<Event> events = gson.fromJson(result, listType);
			request.setAttribute("Events", events);
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
