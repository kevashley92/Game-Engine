package eventManagement;

import gameObjectModel.Player;

import java.util.HashMap;
import java.util.Random;

import mainPrograms.MainProgram;
import mainPrograms.MainProgram.ObjectTypes;

public class EventHandler {

	HashMap<String, HashMap<String, Object>[]> currentMap = new HashMap<String, HashMap<String, Object>[]>();
	
	@SuppressWarnings("unchecked")
	public void onEvent(Event e) {
		currentMap = e.getObjectMap();
		switch(e.getType()) {
		case EVENT_TYPE_PLAYER_COLLISION:
			handleCollision((int)e.getArgs().get("PlayerIndex"), (int)e.getArgs().get("ObjectIndex"), (ObjectTypes)e.getArgs().get("ObjectType"));
			break;
		case EVENT_TYPE_PLAYER_DEATH:
			handleDeath((int)e.getArgs().get("PlayerIndex"));
			break;
		case EVENT_TYPE_PLAYER_SPAWN:
			handlePlayerSpawn((int)e.getArgs().get("PlayerIndex"));
			break;
		case EVENT_TYPE_PLAYER_INPUT:
			handleInput((String)e.getArgs().get("In"), (Player)e.getArgs().get("Player"), (int)e.getArgs().get("PlayerIndex"));
			break;
		case EVENT_TYPE_OBJECT_SPAWN:
			handleObjectSpawn();
			break;
		case EVENT_TYPE_RECORDING_START:
			handleRecordingStart((long)e.getArgs().get("StartTime"), (int)e.getArgs().get("PlayerIndex"), (HashMap<String, HashMap<String, Object>[]>)e.getArgs().get("InitialObjectMap"));
			break;
		case EVENT_TYPE_RECORDING_STOP:
			handleRecordingStop((long)e.getArgs().get("StopTime"), (int)e.getArgs().get("PlayerIndex"));
			break;
		default:
			break;
		}
		//return currentMap;
	}
	
	public void handleCollision(int playerIndex, int objectIndex, ObjectTypes objectType) {
		if (objectType == ObjectTypes.STATIC_PLATFORM) {
			MainProgram.playerList.get(playerIndex).getMover().setJumpHeight(MainProgram.staticPlatforms.get(objectIndex).getLocation()[1] - MainProgram.maxHeightFromPlatform);
			MainProgram.playerList.get(playerIndex).getMover().setJumpOnced(false);
		} else if (objectType == ObjectTypes.DYNAMIC_PLATFORM) {
			MainProgram.playerList.get(playerIndex).getMover().setJumpHeight(MainProgram.dynamicPlatforms.get(objectIndex).getLocation()[1] - MainProgram.maxHeightFromPlatform);
			MainProgram.playerList.get(playerIndex).getMover().setJumpOnced(false);
		}
	}
	
	public void handleDeath(int playerIndex) {
		MainProgram.playerList.get(playerIndex).setDead(true);
		System.out.println("PLAYER "+ playerIndex + " DIED.");
	}
	
	public void handlePlayerSpawn(int playerIndex) {
		Random r = new Random();
		int indexToSpawn = r.nextInt(2);
		MainProgram.playerList.get(playerIndex).setLocation(MainProgram.spawnPoints.get(indexToSpawn).getLocation());
		MainProgram.playerList.get(playerIndex).setDead(false);
		System.out.println("PLAYER " + playerIndex + " SPAWNED");
		//currentMap.get("Players")[playerIndex].put("Location", MainProgram.playerList.get(playerIndex).getLocation());
	}
	
	public void handleInput(String inLine, Player p, int playerIndex) {	
		if (inLine.equals("jump") && !p.getMover().hasJumpOnced()) {
			p.getMover().setDy(-p.getMover().getJumpSpeed());
		} else
		if (inLine.equals("left")) {
			p.getMover().setDx(-p.getMover().getSpeed());
		} else
		if (inLine.equals("right")) {
			p.getMover().setDx(p.getMover().getSpeed());
		} else
		if (inLine.equals("stop")) {
			p.getMover().setDx(0);
		}
		//currentMap.get("Players")[0].put("Location", p.getLocation());
	}
	
	public void handleObjectSpawn() {
		
	}
	
	public void handleRecordingStart(long startTime, int playerIndex, HashMap<String, HashMap<String, Object>[]> initialObjectMap) {
		//System.out.println("START RECORDING AT " + startTime);
		MainProgram.replays.get(playerIndex).setTauStart(startTime);
		MainProgram.replays.get(playerIndex).setTauEnd((long)Double.POSITIVE_INFINITY);
		MainProgram.replays.get(playerIndex).setInitialObjectMap(initialObjectMap);
	}
	
	public void handleRecordingStop(long stopTime, int playerIndex) {
		//System.out.println("STOP RECORDING AT " + stopTime);
		MainProgram.replays.get(playerIndex).setTauEnd(stopTime);
		MainProgram.replays.get(playerIndex).isReplaying = true;
		MainProgram.clientReplaying.set(playerIndex, 1);
		//MainProgram.replays.get(playerIndex).playEvents(1);
	}
}
