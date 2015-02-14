package eventManagement;

import java.io.Serializable;
import java.util.HashMap;

public class Event implements Serializable{

	private static final long serialVersionUID = 1961908266537141919L;
	
	private static int MAX_ARGS = 8;
	
	public enum EventType {
		EVENT_TYPE_PLAYER_COLLISION,
		EVENT_TYPE_PLAYER_DEATH,
		EVENT_TYPE_PLAYER_SPAWN,
		EVENT_TYPE_PLAYER_INPUT,
		EVENT_TYPE_OBJECT_SPAWN,
		EVENT_TYPE_RECORDING_START,
		EVENT_TYPE_RECORDING_STOP
	}
	
	public EventType type;
	public int numArgs;
	public HashMap<String, Object> args = new HashMap<String, Object>();
	private long deliveryTime;
	private int priority;
	
	public HashMap<String, HashMap<String, Object>[]> objectMap = new HashMap<String, HashMap<String, Object>[]>();
	
	public Event(EventType t) {
		this.type = t;
		this.numArgs = 0;
	}

	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}

	public int getNumArgs() {
		return numArgs;
	}

	public void setNumArgs(int numArgs) {
		this.numArgs = numArgs;
	}

	public HashMap<String, Object> getArgs() {
		return args;
	}

	public void setArgs(HashMap<String, Object> args) {
		this.args = args;
	}
	
	public long getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(long deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public HashMap<String, HashMap<String, Object>[]> getObjectMap() {
		return objectMap;
	}

	public void setObjectMap(HashMap<String, HashMap<String, Object>[]> objectMap) {
		this.objectMap = objectMap;
	}

	public boolean addArg(String str, Object obj) {
		if (this.numArgs < MAX_ARGS) {
			this.args.put(str, obj);
			this.numArgs++;
			return true;
		} else
			return false;
	}
	
	public void dispatch() {
		EventManager.handleEvents(this);
		//System.out.println(this.type.toString() + " EVENT DISPATCH");
	}
	
}
