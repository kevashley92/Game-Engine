package eventManagement;

public class PlayerDeathEvent extends Event {

	private static final long serialVersionUID = -5239542753062536807L;
	
	private int playerIndex;
	
	public PlayerDeathEvent(int playerIndex) {
		super(EventType.EVENT_TYPE_PLAYER_DEATH);
		this.playerIndex = playerIndex;
		super.addArg("PlayerIndex", playerIndex);
	}

	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}
}
