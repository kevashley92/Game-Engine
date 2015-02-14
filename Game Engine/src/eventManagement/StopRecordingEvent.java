package eventManagement;

public class StopRecordingEvent extends Event {

	private static final long serialVersionUID = -2299072786658523387L;
	
	private long stopTime;
	private int playerIndex;

	public StopRecordingEvent(long stopTime, int playerIndex) {
		super(EventType.EVENT_TYPE_RECORDING_STOP);
		this.setStopTime(stopTime);
		this.setPlayerIndex(playerIndex);
		super.addArg("StopTime", stopTime);
		super.addArg("PlayerIndex", playerIndex);
	}

	public long getStopTime() {
		return stopTime;
	}

	public void setStopTime(long stopTime) {
		this.stopTime = stopTime;
	}

	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}
}
