package eventManagement;

import java.util.*;

import eventManagement.Event.EventType;

public class EventManager {

	private static HashMap<Event.EventType, LinkedList<EventHandler>> hm = new HashMap<Event.EventType, LinkedList<EventHandler>>();
	
	public static void registerMe(Event.EventType t, EventHandler h) {
		LinkedList<EventHandler> l = hm.get(h);
		if (l == null) {
			l = new LinkedList<EventHandler>();
			hm.put(t, l);
		}
		l.add(h);
	}
	
	public static void handleEvents(Event pEvent) {
		for (int j = 0; j < EventType.values().length; j++) {
			if (hm.get(EventType.values()[j]) != null) {
				for (int i = 0; i < hm.get(EventType.values()[j]).size(); i++) {
					hm.get(EventType.values()[j]).get(i).onEvent(pEvent);
				}
			}
		}
	}
}
