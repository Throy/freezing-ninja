package georeduy.server.webservices;

import georeduy.server.logic.controllers.SessionController;
import georeduy.server.logic.model.User;

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
 * Servlet Filter implementation class AuthenticationFilter
 */
@WebFilter(servletNames = { "Resteasy" })
public class AuthenticationFilter implements Filter {

    /**
     * Default constructor. 
     */
    public AuthenticationFilter() {
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
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
	{	
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		String token = httpRequest.getHeader("Token");

		String s = httpRequest.getRequestURI();
	    if(token != null && SessionController.getInstance().isTokenValid(token))
	    {
	    	User user = SessionController.getInstance().GetClient(token);
	    	UserPrincipal principal = new UserPrincipal(user, token);
	    	User.setCurrent(user);
	    	
	        chain.doFilter(new ClientRequestWrapper(principal, user.getRoles(), httpRequest), response);
	    }
	    else
	    {
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
