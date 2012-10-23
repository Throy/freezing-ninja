package georeduy.server.webservices;

import java.util.Set;
import java.util.HashSet;
import javax.ws.rs.core.Application;

public class GeoRedUyApplication extends Application {

	private Set<Object> singletons = new HashSet<Object>();
	private Set<Class<?>> empty = new HashSet<Class<?>>();
	public GeoRedUyApplication(){
	     singletons.add(new SessionService());
	     singletons.add(new NotificationsService());
	     singletons.add(new SitesService());
	}
	@Override
	public Set<Class<?>> getClasses() {
	     return empty;
	}
	@Override
	public Set<Object> getSingletons() {
	     return singletons;
	}
}
