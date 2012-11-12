package georeduy.server.dao;

import georeduy.server.logic.model.Event;

import georeduy.server.persistence.MongoConnectionManager;

import java.util.List;

import org.bson.types.ObjectId;

import com.google.code.morphia.dao.BasicDAO;

public class EventDaoImpl extends BasicDAO<Event, ObjectId> implements IEventDao {

	private ITagDao tagDao =  new TagDaoImpl();
	
    public EventDaoImpl() {
        super(Event.class, MongoConnectionManager.instance().getDb());
    }
    
    private Event ResolveReferences(Event event) {
		for (String tagId : event.getTagsIds()) {
			event.addTag(tagDao.find(new ObjectId(tagId)));
		}
    	
    	return event;
    }
    
    private List<Event> ResolveReferences(List<Event> events) {
    	for (Event event : events) {
    		ResolveReferences(event);
    	}
    	
    	return events;
    }

	@Override
    public void saveEvent(Event event) {
	    this.save(event);
    }
	
    @Override
    public Event find(ObjectId eventId) {
        return ResolveReferences(get(eventId));
    }

	@Override
    public Event findByName(String name) {
    	List<Event> events = createQuery().field("name").equal(name).asList();
    	events = ResolveReferences(events);
    	if (events.size() == 1) {
    		return ResolveReferences(events.get(0));
    	}
    	else {
    		return null;
    	}
    }

	@Override
    public List<Event> getNearEvents(double latitude, double longitude, double radius) {
		double miles = 0.0034997840172;
		List<Event> events = createQuery().field("coordinates").within(longitude, latitude, miles).asList();
		events = ResolveReferences(events);
	    if (events.size() > 0)
	    	return events;
	    else
	    	return null;
    }

	// obtener sitios
	@Override
    public List<Event> getEvents() {
		return ResolveReferences (createQuery().asList());
    }

	// obtener sitios, por página
	@Override
    public List<Event> getEvents(int from, int count) {
		return ResolveReferences (createQuery().offset(from).limit(count).asList());
    }
}
