package georeduy.server.dao;

import georeduy.server.logic.model.Event;

import java.awt.Point;
import java.util.List;

import org.bson.types.ObjectId;

public interface IEventDao {

    public void saveEvent(Event event);

    public Event find(ObjectId siteId);

    public Event findByName(String name);

    public List<Event> getNearEvents(double latitude, double longitude, double radius);
    
    public List<Event> getEvents();
    
    public List<Event> getEvents(int from, int count);
}
