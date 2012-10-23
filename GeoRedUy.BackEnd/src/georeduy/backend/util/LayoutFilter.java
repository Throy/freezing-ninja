package georeduy.backend.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class RewriteURL
 */
@WebFilter("/RewriteURL")
public class LayoutFilter implements Filter {

    /**
     * Default constructor. 
     */
    public LayoutFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpServletResponse httpResponse = (HttpServletResponse)response;
        HttpSession session = httpRequest.getSession();
        
        String contentPath = httpRequest.getRequestURI();
        
        if (contentPath.contains("/GeoRedUy.BackEnd/media/") || contentPath.contains("/GeoRedUy.BackEnd/scripts/")) {
        	chain.doFilter(request, response);
		} else {
	        PrintWriter pw = response.getWriter();
	        response.setContentType("text/html;charset=UTF-8");
	        
	        contentPath = contentPath.replace("/GeoRedUy.BackEnd/", "/");
	        if (contentPath.equals("/"))
	        	contentPath = "/home";
	        request.setAttribute("contentPath", contentPath);
	        
	        if (request.getParameter("LogOut") != null)
	        {
	        	session.removeAttribute("Token");
	        	httpResponse.sendRedirect("/GeoRedUy.BackEnd/");
	        	return;
	        }
	        
	        String token = (String)session.getAttribute("Token");
	        
	        if (token == null) {
	        	pw.print(Dispatcher.fetch("login_layout", httpRequest, httpResponse));
	        } else {
	        	pw.print(Dispatcher.fetch("layout", httpRequest, httpResponse));
	        }
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
