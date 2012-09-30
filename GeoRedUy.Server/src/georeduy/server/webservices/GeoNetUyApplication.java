package georeduy.server.webservices;

import java.util.Set;
import java.util.HashSet;
import javax.ws.rs.core.Application;

public class GeoNetUyApplication extends Application {

	private Set<Object> singletons = new HashSet<Object>();
	private Set<Class<?>> empty = new HashSet<Class<?>>();
	public GeoNetUyApplication(){
	     singletons.add(new Session());
	     singletons.add(new Notifications());
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
