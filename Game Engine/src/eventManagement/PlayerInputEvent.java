package eventManagement;

import gameObjectModel.Player;

public class PlayerInputEvent extends Event {

	private static final long serialVersionUID = -8999796614414783808L;
	
	String inLine;
	Player player;
	int playerIndex;
	
	public PlayerInputEvent(String inLine, Player p, int playerIndex) {
		super(EventType.EVENT_TYPE_PLAYER_INPUT);
		this.inLine = inLine;
		this.player = p;
		this.playerIndex = playerIndex;
		super.addArg("In", inLine);
		super.addArg("Player", p);
		super.addArg("PlayerIndex", playerIndex);
	}

	public String getInLine() {
		return inLine;
	}

	public void setInLine(String inLine) {
		this.inLine = inLine;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}
}
