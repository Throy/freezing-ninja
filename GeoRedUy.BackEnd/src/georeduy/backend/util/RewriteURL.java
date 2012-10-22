package georeduy.backend.util;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

/**
 * Servlet Filter implementation class RewriteURL
 */
@WebFilter("/RewriteURL")
public class RewriteURL implements Filter {

    /**
     * Default constructor. 
     */
    public RewriteURL() {
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
		String path = httpRequest.getRequestURI();
		if (!path.contains("/GeoRedUy.BackEnd/main/") && !path.contains("/GeoRedUy.BackEnd/media/") && !path.contains("/GeoRedUy.BackEnd/scripts/")) {
			path = path.replace("/GeoRedUy.BackEnd/", "/main/");
			request.getRequestDispatcher(path).include(request, response);
		} else {
			// pass the request along the filter chain
			chain.doFilter(request, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
