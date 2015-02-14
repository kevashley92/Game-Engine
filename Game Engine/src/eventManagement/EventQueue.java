package eventManagement;

import java.util.*;

import eventManagement.Event.EventType;

import mainPrograms.MainProgram;

public class EventQueue {
	
	private static Queue<Event> eventQueue = new LinkedList<Event>();
	
	public static Queue<Event> getEventQueue() {
		return eventQueue;
	}

	public static void setEventQueue(Queue<Event> eventQueue) {
		EventQueue.eventQueue = eventQueue;
	}
	
	public static void queueEvent(Event event) {
		boolean replayEvent = false;
		for (int i = 0; i < MainProgram.replays.size(); i++) {
			if (!(event.getType().toString()).equals(EventType.EVENT_TYPE_RECORDING_STOP.toString()) &&
					//event.getType().toString().equals(Event.EventType.EVENT_TYPE_RECORDING_START.toString()) ||
					(long)event.getDeliveryTime() > MainProgram.replays.get(i).getTauStart() &&
					event.getDeliveryTime() <= MainProgram.replays.get(i).getTauEnd()) {
				event.setObjectMap(MainProgram.replays.get(i).getInitialObjectMap());
				MainProgram.replays.get(i).addToReplay(event);
				System.out.println("ADDED " + event.getType() + " TO REPLAY");
				replayEvent = true;
			}
		}
		if (!replayEvent)
			event.setObjectMap(MainProgram.objectMap);
		eventQueue.add(event);
	}
	
	public static void dispatchEvents(float currentTime, int priority) {
		Event pEvent = eventQueue.peek();
		while (pEvent != null && pEvent.getDeliveryTime() <= currentTime && pEvent.getPriority() == priority) {
			eventQueue.remove();
			pEvent.dispatch();
			pEvent = eventQueue.peek();
		}
	}
	
}
