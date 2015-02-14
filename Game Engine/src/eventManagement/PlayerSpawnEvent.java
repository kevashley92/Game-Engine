package eventManagement;

public class PlayerSpawnEvent extends Event {

	private static final long serialVersionUID = -1031258106635947112L;

	private int playerIndex;
	
	public PlayerSpawnEvent(int playerIndex) {
		super(EventType.EVENT_TYPE_PLAYER_SPAWN);
		this.setPlayerIndex(playerIndex);
		super.addArg("PlayerIndex", playerIndex);
	}

	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}

}
