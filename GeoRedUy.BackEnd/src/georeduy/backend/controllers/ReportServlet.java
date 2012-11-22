package georeduy.backend.controllers;

import georeduy.backend.util.GeoRedClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ReportServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			String parameter = request.getParameter("Report");
			if (parameter != null) {
				if (parameter.equals("Visits")) {
					GetVisits(request);
				} else if (parameter.equals("Purchase")) {
					GetPurchases(request);
				}
			}

		} catch (Exception e) {
			request.setAttribute("ErrorMsg", e.getMessage());
		}
	}

	// <editor-fold defaultstate="collapsed"
	// desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
	/**
	 * Handles the HTTP <code>GET</code> method.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Returns a short description of the servlet.
	 * 
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>

	public void GetPurchases(HttpServletRequest request) throws Exception {
		System.out.println("---> GetPurchases -->");
		String fechaDesde = request.getParameter("fechaDesde");
		fechaDesde = fechaDesde == null ? "0" : fechaDesde;
		String fechaHasta = request.getParameter("fechaHasta");
		fechaHasta = fechaHasta == null ? "0" : fechaHasta;

		Map<String, String> params = new HashMap<String, String>();
		params.put("fechaDesde", fechaDesde);
		params.put("fechaHasta", fechaHasta);

		String result = GeoRedClient.Get("/Reports/Purchases", params,
				(String) request.getSession().getAttribute("Token"));
		System.out.println("---> resultado -->" + result);
		request.setAttribute("ReportData", result);

	}

	public void GetVisits(HttpServletRequest request) throws Exception {



	}

}
