package georeduy.backend.controllers;


import georeduy.backend.model.Product;
import georeduy.backend.model.RetailStore;
import georeduy.backend.model.Retailer;
import georeduy.backend.model.User;
import georeduy.backend.model.UserNotificationsTypes;
import georeduy.backend.util.GeoRedClient;
import georeduy.backend.util.Util;

import java.io.DataInputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@MultipartConfig
public class RetailServlet extends HttpServlet {
   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
		try {    	
	    	if (request.getParameter("AddRetailer") != null) {  
	    		AddRetailer(request);
	    	} else if (request.getParameter("AddStore") != null) {  
	    		AddStore(request);
	    	} else if (request.getParameter("AddProduct") != null) {  
	    		AddProduct(request);
	    	} else if (request.getParameter("AddStoreProduct") != null) {  
	    		AddStoreProduct(request);
	    	}	    	
	    	
	    	if (request.getParameter("ListRetailers") != null) {  
	    		ListRetailers(request);
	    	} else if (request.getParameter("ListMyStores") != null) {  
	    		ListMyStores(request);
	    	} else if (request.getParameter("ListMyProducts") != null) {  
	    		ListMyProducts(request);
	    	} else if (request.getParameter("ShowStore") != null) {  
	    		ShowStore(request);
	    	}
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
    
    
    public void AddRetailer(HttpServletRequest request) throws Exception {
    	// obtener datos de la solicitud
    	String name = request.getParameter("Name");
    	String description = request.getParameter("Description");
    	String userName = request.getParameter("UserName");
    	String imageUrl = request.getParameter("ImageUrl");
    	
    	// crear empresa
    	Retailer retailer = new Retailer();
    	retailer.setName(name);
    	retailer.setDescription(description);
    	retailer.setImageUrl(imageUrl);
    	
    	// asignar adminstrador de empresa
    	User user = new User();
    	user.setUserName(userName);
    	UserNotificationsTypes notiTypes = new UserNotificationsTypes ();
    	notiTypes.setNotitype1_contactsVisits (false);
    	notiTypes.setNotitype2_contactsComments (false);
    	notiTypes.setNotitype3_contactsReviews (false);
    	notiTypes.setNotitype4_sites (false);
    	notiTypes.setNotitype5_products (false);
    	notiTypes.setNotitype6_events (false);
    	user.setNotificationsTypes (notiTypes);
    	retailer.setUser(user);
    	
    	// invocar al webservice de crear empresa
    	Gson gson = new Gson();
    	
    	Map<String, String> params  = new HashMap <String, String>();
		params.put ("retailerInfo", gson.toJson(retailer));

		GeoRedClient.Post("/Retail/NewRetailer", params, (String)request.getSession().getAttribute("Token"));
    }
    
    public void AddStore(HttpServletRequest request) throws Exception {
    	String name = request.getParameter("Name");
    	String phoneNumber = request.getParameter("PhoneNumber");
    	String address = request.getParameter("Address");
    	String paypalMail = request.getParameter("PayPalMail");
    	double latitude = Double.parseDouble(request.getParameter("Latitude"));
    	double longitude = Double.parseDouble(request.getParameter("Longitude"));
    	String imageUrl = request.getParameter("ImageUrl");
    	
    	RetailStore store = new RetailStore();
    	store.setName(name);
    	store.setPhoneNumber(phoneNumber);
    	store.setPayPalMail(paypalMail);
    	store.setImageUrl(imageUrl);
    	store.setAddress(address);

    	Double[] coordinates = {longitude, latitude};
    	store.setCoordinates(coordinates);
    	
    	Gson gson = new Gson();
    	
    	Map<String, String> params  = new HashMap <String, String>();
		params.put ("storeInfo", gson.toJson(store));

		GeoRedClient.Post("/Retail/NewStore", params, (String)request.getSession().getAttribute("Token"));
    }
    
    public void AddProduct(HttpServletRequest request) throws Exception {
    	String name = Util.getValue(request.getPart("Name"));
    	String description = Util.getValue(request.getPart("Description"));
    	double price = Double.parseDouble (Util.getValue(request.getPart("Price")));
    	
    	Product product = new Product();
    	product.setName(name);
    	product.setDescription(description);
    	product.setPrice(Double.toString(price));
    	product.setReleaseDate (new Date ());
    	product.setImage(Util.getBytes(request.getPart("imageData").getInputStream()));
    	// *** en realidad, releaseDate debería ser un campo del formulario ***
    	
    	Gson gson = new Gson();
    	
    	Map<String, String> params  = new HashMap <String, String>();
		params.put ("productInfo", gson.toJson(product));

		GeoRedClient.Post("/Products/New", params, (String)request.getSession().getAttribute("Token"));
    }
    
    public void AddStoreProduct(HttpServletRequest request) throws Exception {
    	Map<String, String> params  = new HashMap <String, String>();
		params.put ("productName", request.getParameter("Name"));
		params.put ("storeId", request.getParameter("StoreId"));

		GeoRedClient.Get("/Products/AddStoreProduct", params, (String)request.getSession().getAttribute("Token"));
    }
    
    public void ListRetailers(HttpServletRequest request) throws Exception {
    	Map<String, String> params = new HashMap <String, String>();
		params.put ("from", "0");
		params.put ("count", "10");
    	
    	String result = GeoRedClient.Get("/Retail/GetRetailers", params, (String)request.getSession().getAttribute("Token"));
		Type listType = new TypeToken<ArrayList<Retailer>>() {}.getType();
		
		Gson gson = new Gson();
		
		List<Retailer> retailer = gson.fromJson(result, listType);
		request.setAttribute("Retailers", retailer);
    }
    
    public void ListMyStores(HttpServletRequest request) throws Exception {
    	Map<String, String> params = new HashMap <String, String>();
		params.put ("from", "0");
		params.put ("count", "10");
    	
    	String result = GeoRedClient.Get("/Retail/GetStores", params, (String)request.getSession().getAttribute("Token"));
		Type listType = new TypeToken<ArrayList<RetailStore>>() {}.getType();
		
		Gson gson = new Gson();
		
		List<RetailStore> stores = gson.fromJson(result, listType);
		request.setAttribute("Stores", stores);
    }
    
    public void ListMyProducts(HttpServletRequest request) throws Exception {
    	Map<String, String> params = new HashMap <String, String>();
		params.put ("from", "0");
		params.put ("count", "10");
    	
    	String result = GeoRedClient.Get("/Products/GetByRetailer", params, (String)request.getSession().getAttribute("Token"));
		Type listType = new TypeToken<ArrayList<Product>>() {}.getType();
		
		Gson gson = new Gson();
		
		List<Product> products = gson.fromJson(result, listType);
		request.setAttribute("Products", products);
    }
    
    public void ShowStore(HttpServletRequest request) throws Exception {
    	Map<String, String> params = new HashMap <String, String>();
		params.put ("id", request.getParameter("ShowStore"));
    	
    	String result = GeoRedClient.Get("/Retail/GetStore", params, (String)request.getSession().getAttribute("Token"));
		Gson gson = new Gson();
		
		RetailStore store = gson.fromJson(result, RetailStore.class);
		request.setAttribute("Store", store);
		
		params = new HashMap <String, String>();
		params.put ("from", "0");
		params.put ("count", "10");
		params.put ("id", request.getParameter("ShowStore"));
    	
    	result = GeoRedClient.Get("/Products/GetByStore", params, (String)request.getSession().getAttribute("Token"));
		Type listType = new TypeToken<ArrayList<Product>>() {}.getType();
		
		List<Product> products = gson.fromJson(result, listType);
		request.setAttribute("Products", products);
    }

}
