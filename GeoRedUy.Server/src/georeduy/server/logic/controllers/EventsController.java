package georeduy.server.logic.controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

import georeduy.server.dao.CommentDaoImpl;
import georeduy.server.dao.EventDaoImpl;
import georeduy.server.dao.ICommentDao;
import georeduy.server.dao.IEventDao;
import georeduy.server.dao.ISiteDao;
import georeduy.server.dao.ITagDao;
import georeduy.server.dao.IUserDao;
import georeduy.server.dao.IVisitDao;
import georeduy.server.dao.SiteDaoImpl;
import georeduy.server.dao.TagDaoImpl;
import georeduy.server.dao.UserDaoImpl;
import georeduy.server.dao.VisitDaoImpl;
import georeduy.server.logic.model.Comment;
import georeduy.server.logic.model.Event;
import georeduy.server.logic.model.GeoRedConstants;
import georeduy.server.logic.model.Roles;
import georeduy.server.logic.model.Site;
import georeduy.server.logic.model.Tag;
import georeduy.server.logic.model.User;
import georeduy.server.logic.model.Visit;
import georeduy.server.util.Filter;
import georeduy.server.util.Util;

public class EventsController {
	private static EventsController s_instance = null;

	
	// objetos dao
	private IEventDao eventDao = new EventDaoImpl();
	private ITagDao tagDao = new TagDaoImpl();
	
	// constructores
	
	public EventsController() {
	}

	public static EventsController getInstance() {
		if (s_instance == null) {
			s_instance = new EventsController();
		}

		return s_instance;
	}
	
	// adminsitrar sitios
	
	public void NewEvent(Event event) throws Exception {
        if (eventDao.findByName(event.getName()) == null) {
        	List<Tag> realTags = new ArrayList<Tag>();
        	for (Tag tag : event.getTags()) {
        		Tag dbTag = tagDao.findByName(tag.getName().trim());
        		if (dbTag == null)
        			throw new Exception(GeoRedConstants.TAG_DOES_NOT_EXIST + ":" + tag.getName().trim());
        		realTags.add(dbTag);
        	}
        	event.setTags(realTags);
            eventDao.saveEvent(event);
            
            final Event eventF = event;
            NotificationsController.getInstance().BroadCast(event, new Filter() {

				@Override
                public boolean filter(String userId) {
					User user = SessionController.getInstance().GetUserById(userId);
					if ((Util.distanceHaversine(eventF.getCoordinates()[0], eventF.getCoordinates()[1], 
						user.getCoordinates()[0], user.getCoordinates()[1]) <= Util.BROADCAST_RANGE) ||
								Util.within(eventF.getCoordinates()[0], eventF.getCoordinates()[1], user.getMapRect())) {
						return false;
					}
	                return true;
                }});
            
        } else {
        	throw new Exception(GeoRedConstants.SITE_NAME_EXISTS);
        }
	}
	
	// obtener sitios
	public List<Event> getEvents () {
		return eventDao.getEvents();
	}

	// obtener sitios, por página
	public List<Event> getEvents (int from, int count) {
		return eventDao.getEvents(from, count);
	}

	// obtener sitios cercanos
	public List<Event> getEventsByPosition(int bottomLeftLatitude, int bottomLeftLongitude, int topRightLatitude, int topRightLongitude) {
		/*List<Site> lista = new ArrayList<Site>();
		Site sitio1 = new Site();
		sitio1.coordinates[0] = latitude/1e6;
		sitio1.coordinates[1] = longitud/1e6;
		sitio1.setName("prueba1");
		Site sitio2 = new Site();
		sitio2.coordinates[0] = latitude/1e6;
		sitio2.coordinates[1] = longitud/1e6;
		sitio2.setName("prueba2");
		lista.add(sitio1);
		lista.add(sitio2);*/
		return eventDao.getNearEvents(bottomLeftLatitude/1e6, bottomLeftLongitude/1e6, topRightLatitude/1e6, topRightLongitude/1e6);		
	}
	
	// obtener datos de un sitio.
	public Event getById (String eventId) {
		return eventDao.find (new ObjectId (eventId));		
	}
		
	
	}
