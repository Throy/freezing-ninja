package georeduy.backend.controllers;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import georeduy.backend.model.User;
import georeduy.backend.util.GeoRedClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;


public class Login extends HttpServlet {
   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    	HttpSession session = request.getSession();
    	
    	String userName = request.getParameter("UserName");
    	String password = request.getParameter("Password");
    	
    	if (userName != null && password != null) {   		
	    	Map<String, String> params = new HashMap<String, String>();
			params.put("userName", userName);
			params.put("password", password);
	
			try {
				session.setAttribute("Token", GeoRedClient.Get("/Session", params, null));

				String result = GeoRedClient.Get("/Session/GetUserInfo", null, (String)session.getAttribute("Token"));
				Gson gson = new Gson();
				User user = gson.fromJson(result, User.class);
				session.setAttribute("User", user);
				
				request.setAttribute("Success", "true");
				request.setAttribute("Header", "<meta http-equiv=\"refresh\" content=\"2\">");
			} catch (Exception e) {
	        	request.setAttribute("ErrorMsg", e.getMessage());
	        }
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
