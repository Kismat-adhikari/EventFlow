package eventflow.services;

import eventflow.dao.EventDao;
import eventflow.models.Event;

public class EventService {

    private final EventDao eventDao = new EventDao();

    public boolean addNewEvent(Event event) {
        return eventDao.createEvent(event);
    }
}
