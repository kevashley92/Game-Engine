package eventManagement;

import mainPrograms.MainProgram.ObjectTypes;

public class PlayerCollisionEvent extends Event {

	private static final long serialVersionUID = -941657293000439248L;

	private int playerIndex;
	private int objectIndex;
	private ObjectTypes objectType;
	
	public PlayerCollisionEvent(int playerIndex, int objectIndex, ObjectTypes objectType) {
		super(EventType.EVENT_TYPE_PLAYER_COLLISION);
		this.playerIndex = playerIndex;
		this.objectIndex = objectIndex;
		this.objectType = objectType;
		super.addArg("PlayerIndex", playerIndex);
		super.addArg("ObjectIndex", objectIndex);
		super.addArg("ObjectType", objectType);
	}

	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}

	public int getObjectIndex() {
		return objectIndex;
	}

	public void setObjectIndex(int objectIndex) {
		this.objectIndex = objectIndex;
	}

	public ObjectTypes getObjectType() {
		return objectType;
	}

	public void setObjectType(ObjectTypes objectType) {
		this.objectType = objectType;
	}

}