package eventManagement;

import java.util.HashMap;

public class StartRecordingEvent extends Event {

	private static final long serialVersionUID = -5185832894116748314L;
	
	private long startTime;
	private int playerIndex;
	private HashMap<String, HashMap<String, Object>[]> initialObjectMap;

	public StartRecordingEvent(long startTime, int playerIndex, HashMap<String, HashMap<String, Object>[]> initialObjectMap) {
		super(EventType.EVENT_TYPE_RECORDING_START);
		this.setStartTime(startTime);
		this.setPlayerIndex(playerIndex);
		this.setInitialObjectMap(initialObjectMap);
		super.addArg("StartTime", startTime);
		super.addArg("PlayerIndex", playerIndex);
		super.addArg("InitialObjectMap", initialObjectMap);
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime2) {
		this.startTime = startTime2;
	}

	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}

	public HashMap<String, HashMap<String, Object>[]> getInitialObjectMap() {
		return initialObjectMap;
	}

	public void setInitialObjectMap(HashMap<String, HashMap<String, Object>[]> initialObjectMap) {
		this.initialObjectMap = initialObjectMap;
	}

}
