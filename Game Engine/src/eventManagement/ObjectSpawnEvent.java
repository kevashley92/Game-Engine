package eventManagement;

public class ObjectSpawnEvent extends Event {

	private static final long serialVersionUID = 6912244724306270402L;
	
	private int[] location;

	public ObjectSpawnEvent(int[] location) {
		super(EventType.EVENT_TYPE_OBJECT_SPAWN);
		this.setLocation(location);
		super.addArg("ObjectLocation", location);
	}

	public int[] getLocation() {
		return location;
	}

	public void setLocation(int[] location) {
		this.location = location;
	}
}
