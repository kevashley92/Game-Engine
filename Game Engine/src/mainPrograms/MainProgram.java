package mainPrograms;
import eventManagement.*;
import eventManagement.Event.EventType;
import gameObjectModel.*;

import java.io.*;
import java.util.*;

import processing.core.*;

public class MainProgram extends PApplet {

	private static final long serialVersionUID = 1L;
	
	public static HashMap<EventType, Integer> priorities = new HashMap<EventType, Integer>();
	
	private static LinkedList<ObjectOutputStream> outList = new LinkedList<ObjectOutputStream>();
	private static LinkedList<ObjectInputStream> inList = new LinkedList<ObjectInputStream>();
	public static LinkedList<Player> playerList = new LinkedList<Player>();
	public static LinkedList<StaticPlatform> staticPlatforms = new LinkedList<StaticPlatform>();
	public static LinkedList<DynamicPlatform> dynamicPlatforms = new LinkedList<DynamicPlatform>();
	public static LinkedList<SpawnPoint> spawnPoints = new LinkedList<SpawnPoint>();
	public static LinkedList<DeathZone> deathZones = new LinkedList<DeathZone>();
	
	public static HashMap<String, HashMap<String, Object>[]> objectMap = new HashMap<String, HashMap<String, Object>[]>();
	
	public static LinkedList<ReplaySystem> replays = new LinkedList<ReplaySystem>();
	
	public static LinkedList<Integer> clientReplaying = new LinkedList<Integer>();
	
	public static enum ObjectTypes {
		STATIC_PLATFORM,
		DYNAMIC_PLATFORM,
		SPAWN_POINT,
		DEATH_ZONE
	}

	static int screenX = 1280;
	static int screenY = 720;
	static boolean isJumping = false;
	
	public static int maxHeightFromPlatform = 200;
	
	static boolean colliding = false;
	
	static PShape playerShape;
	
	static Server newServer;
	
	static int currentPlayer = 0;//1;
	static int currentSpawnPoint = 0;

	PImage currentScreen = createImage(screenX, screenY, RGB);
	
	public static synchronized void addToOutput (ObjectOutputStream oos) {
		outList.add(oos);
		setupPriorities();
	}
	
	public static synchronized void addToInput (ObjectInputStream ois) {
		inList.add(ois);
		addPlayer();
	}

	public static void setupPriorities() {
		priorities.put(EventType.EVENT_TYPE_PLAYER_INPUT, 0);
		priorities.put(EventType.EVENT_TYPE_RECORDING_START, 4);
		priorities.put(EventType.EVENT_TYPE_RECORDING_STOP, 4);
		priorities.put(EventType.EVENT_TYPE_PLAYER_COLLISION, 1);
		priorities.put(EventType.EVENT_TYPE_PLAYER_SPAWN, 2);
		priorities.put(EventType.EVENT_TYPE_OBJECT_SPAWN, 2);
		priorities.put(EventType.EVENT_TYPE_PLAYER_DEATH, 3);
	}
	
	public static void addPlayer() {
		EventHandler playerInputEventHandler = new EventHandler();
		EventManager.registerMe(EventType.EVENT_TYPE_PLAYER_INPUT, playerInputEventHandler);
		EventHandler playerCollisionEventHandler = new EventHandler();
		EventManager.registerMe(EventType.EVENT_TYPE_PLAYER_COLLISION, playerCollisionEventHandler);
		EventHandler playerSpawnEventHandler = new EventHandler();
		EventManager.registerMe(EventType.EVENT_TYPE_PLAYER_SPAWN, playerSpawnEventHandler);
		EventHandler playerDeathEventHandler = new EventHandler();
		EventManager.registerMe(EventType.EVENT_TYPE_PLAYER_DEATH, playerDeathEventHandler);
		EventHandler playerStartRecordingHandler = new EventHandler();
		EventManager.registerMe(EventType.EVENT_TYPE_RECORDING_START, playerStartRecordingHandler);
		EventHandler playerStopRecordingHandler = new EventHandler();
		EventManager.registerMe(EventType.EVENT_TYPE_RECORDING_STOP, playerStopRecordingHandler);
		
		ReplaySystem playerReplay = new ReplaySystem(currentPlayer);
		replays.add(playerReplay);
		
		clientReplaying.add(0);
	
		/*EventHandler playerReplayHandler = new EventHandler();
		for (int i = 0; i < EventType.values().length; i++) {
			EventManager.registerMe(EventType.values()[i], playerReplayHandler);
		}*/
		
		int[] location = { 0, 0 };
		Player p = new Player(playerShape, location, currentPlayer, 40, 40);
		playerList.add(p);
		
		Event playerSpawnEvent = new PlayerSpawnEvent(currentPlayer);
		playerSpawnEvent.setDeliveryTime(System.currentTimeMillis());
		playerSpawnEvent.setPriority(priorities.get(playerSpawnEvent.getType()));
		EventQueue.queueEvent(playerSpawnEvent);
		EventQueue.dispatchEvents(System.currentTimeMillis(), 2);
		
		currentPlayer++;
	}
	
	public static synchronized ObjectOutputStream getOutput(int index) {
		return outList.get(index);
	}
	
	public static synchronized ObjectInputStream getInput(int index) {
		return inList.get(index);
	}

	public void setup() {
		size(screenX, screenY, P2D);
		newServer = new Server();
		new Thread(newServer).start();
		playerShape = createShape(RECT, 0, 0, 40, 40);
		createWorld();
	}
	
	public void createWorld() {
		createPlatform(-500, 650, 300, 70);
		createPlatform(0, 650, 350, 70);
		createPlatform(350, 600, 200, 120);
		createPlatform(750, 600, 200, 120);
		createPlatform(1150, 550, screenX-1150, 170);
		createPlatform(screenX + 250, 600, 500, screenY-600);
		createPlatform(screenX + 500, 250, 250, 50);
		createPlatform(400, 250, 250, 50);
		createPlatform(100, 170, 250, 50);
		createMovingPlatform(700, 350, 200, 50);
		createMovingPlatform(screenX, 350, 200, 50);
		createSpawnPoint(200, 600);
		createSpawnPoint(400, 300);
		createSpawnPoint(750, 550);
		createDeathZone(0, 720, screenX, 100);
	}
	
	public static void startGame(int i) {
		while(true) {
			if (inList.size() > 0) {
				try {
					String inLine;
					inLine = (String) getInput(i).readObject();
					
					PlayerInputEvent event = new PlayerInputEvent(inLine, playerList.get(i), i);
					event.setDeliveryTime(System.currentTimeMillis());
					event.setPriority(priorities.get(event.getType()));
					EventQueue.queueEvent(event);

					EventQueue.dispatchEvents(System.currentTimeMillis(), 0);
					
					if (inLine.equals("startrecording")) {
						StartRecordingEvent recordEvent = new StartRecordingEvent(System.currentTimeMillis(), i, objectMap);
						recordEvent.setDeliveryTime(System.currentTimeMillis());
						recordEvent.setPriority(priorities.get(recordEvent.getType()));
						System.out.println(replays.get(i).getTauStart());
						EventQueue.queueEvent(recordEvent);
						EventQueue.dispatchEvents(System.currentTimeMillis(), 4);
					} else if (inLine.equals("stoprecording")) {
						StopRecordingEvent stopRecordEvent = new StopRecordingEvent(System.currentTimeMillis(), i);
						stopRecordEvent.setDeliveryTime(System.currentTimeMillis());
						stopRecordEvent.setPriority(priorities.get(stopRecordEvent.getType()));
						EventQueue.queueEvent(stopRecordEvent);
						EventQueue.dispatchEvents(System.currentTimeMillis(), 4);
						//replays.get(i).playEvents(1);
					}
					
					if (inLine.equalsIgnoreCase("exit")) {
						getOutput(i).close();
						getInput(i).close(); 
						outList.remove(i);
						inList.remove(i);
						playerList.remove(i);
						newServer.setUniqueId(newServer.getUniqueId()-1);
						break;
					}
				} catch (IOException e) {
					//e.printStackTrace();
				} catch (ClassNotFoundException e) {
					//e.printStackTrace();
				}
			}
		}
	}
	
	public void createPlatform(int x, int y, int w, int h) {
		PShape newPlatform = createShape(RECT, 0, 0, w, h);
		newPlatform.setFill(color(0, 300, 0));
		int location[] = { x, y };
		StaticPlatform sp = new StaticPlatform(newPlatform, location, w, h);
		staticPlatforms.add(sp);
	}
	
	public void createMovingPlatform(int x, int y, int w, int h) {
		PShape newPlatform = createShape(RECT, 0, 0, w, h);
		newPlatform.setFill(color(200, 200, 200));
		int location[] = { x, y };
		DynamicPlatform dp = new DynamicPlatform(newPlatform, location, w, h);
		dynamicPlatforms.add(dp);
	}
	
	public void createSpawnPoint(int x, int y) {
		int location[] = { x, y };
		SpawnPoint p = new SpawnPoint(location, currentSpawnPoint);
		spawnPoints.add(p);
		currentSpawnPoint++;
	}
	
	public void createDeathZone(int x, int y, int w, int h) {
		int location[] = { x,y };
		DeathZone d = new DeathZone(location, w, h);
		deathZones.add(d);
	}
	
	public void drawPlayer( int index ) {
		if (!playerList.get(index).isDead())
			shape(playerList.get(index).getRenderer().getVisualObject(), 
					playerList.get(index).getLocation()[0], playerList.get(index).getLocation()[1]-40);
	}
	
	// Draw static platforms
	public void drawPlatforms() {
		noStroke();
		for (int i = 0; i < staticPlatforms.size(); i++) {
			shape(staticPlatforms.get(i).getRenderer().getVisualObject(), 
					staticPlatforms.get(i).getLocation()[0], staticPlatforms.get(i).getLocation()[1]);
		}
	}
	
	// Draw dynamic platforms
	public void drawMovingPlatforms() {
		for (int i = 0; i < dynamicPlatforms.size(); i++) {
			shape(dynamicPlatforms.get(i).getRenderer().getVisualObject(), 
					dynamicPlatforms.get(i).getLocation()[0], dynamicPlatforms.get(i).getLocation()[1]);
		}
	}
	
	// Constantly test for collisions for all players and keep the game updated
	public void update() {
		for (int i = 0; i < playerList.size(); i++) {
			playerList.get(i).getMover().move();
			
			// Test collisions with platforms
			colliding = false;
			for (int j = 0; j < staticPlatforms.size(); j++) {
				colliding = playerList.get(i).getCollider().testPlayerPlatformCollision(staticPlatforms.get(j), j);
				if (colliding) {
					Event collisionEvent = new PlayerCollisionEvent(i, j, ObjectTypes.STATIC_PLATFORM);
					collisionEvent.setDeliveryTime(System.currentTimeMillis());
					collisionEvent.setPriority(priorities.get(collisionEvent.getType()));
					EventQueue.queueEvent(collisionEvent);
					break;
				}
			}
			for (int j = 0; j < dynamicPlatforms.size(); j++) {
				colliding = playerList.get(i).getCollider().testPlayerPlatformCollision(dynamicPlatforms.get(j), j);
				if (colliding) {
					Event collisionEvent = new PlayerCollisionEvent(i, j, ObjectTypes.DYNAMIC_PLATFORM);
					collisionEvent.setDeliveryTime(System.currentTimeMillis());
					collisionEvent.setPriority(priorities.get(collisionEvent.getType()));
					EventQueue.queueEvent(collisionEvent);
					break;
				}
			}
			
			EventQueue.dispatchEvents(System.currentTimeMillis(), 1);
			
			// Test collisions with death zones
			for (int j = 0; j < deathZones.size(); j++) {
				if (playerList.get(i).getCollider().testPlayerDeathCollision(deathZones.get(j), spawnPoints)) {
					Event playerDeathEvent = new PlayerDeathEvent(i);
					playerDeathEvent.setDeliveryTime(System.currentTimeMillis());
					playerDeathEvent.setPriority(priorities.get(playerDeathEvent.getType()));
					EventQueue.queueEvent(playerDeathEvent);
					
					Event playerSpawnEvent = new PlayerSpawnEvent(i);
					playerSpawnEvent.setDeliveryTime(System.currentTimeMillis());
					playerSpawnEvent.setPriority(priorities.get(playerSpawnEvent.getType()));
					EventQueue.queueEvent(playerSpawnEvent);
				}
			}
			
			EventQueue.dispatchEvents(System.currentTimeMillis(), 2);
			EventQueue.dispatchEvents(System.currentTimeMillis(), 3);
			
			// Handle each player's jump height
			if (!colliding && playerList.get(i).getLocation()[1] > playerList.get(i).getMover().getJumpHeight()) {
				playerList.get(i).getMover().setDy(15);
			} else if (!colliding && playerList.get(i).getLocation()[1] < playerList.get(i).getMover().getJumpHeight()) {
				int [] newLoc = { playerList.get(i).getLocation()[0], playerList.get(i).getMover().getJumpHeight() };
				playerList.get(i).setLocation(newLoc);
				playerList.get(i).getMover().setDy(15);
				playerList.get(i).getMover().setJumpOnced(true);
			}
			
			// Handle players' screen boundaries
			if (playerList.get(i).getLocation()[0] < 0) {
				int [] newLoc = { 0, playerList.get(i).getLocation()[1] };
				playerList.get(i).setLocation(newLoc);
			} else if (playerList.get(i).getLocation()[0] + playerList.get(i).getWidth() > screenX) {
				int [] newLoc = { screenX - playerList.get(i).getWidth(), playerList.get(i).getLocation()[1] };
				playerList.get(i).setLocation(newLoc);
			}
			
			// Handle scrolling
			if (playerList.get(i).getLocation()[0] < 200 || playerList.get(i).getLocation()[0] > screenX - (200+playerList.get(i).getWidth())) {
				for (int j = 0; j < staticPlatforms.size(); j++) {
					staticPlatforms.get(j).getMover().setDx(-playerList.get(i).getMover().getDx());
					staticPlatforms.get(j).getMover().move();
				}
				for (int j = 0; j < dynamicPlatforms.size(); j++) {
					int [] newLoc = { dynamicPlatforms.get(j).getLocation()[0] + (-playerList.get(i).getMover().getDx()), dynamicPlatforms.get(j).getLocation()[1] };
					int [] newStart = { dynamicPlatforms.get(j).startLoc[0] + (-playerList.get(i).getMover().getDx()), dynamicPlatforms.get(j).startLoc[1] };
					dynamicPlatforms.get(j).startLoc = newStart;
					dynamicPlatforms.get(j).setLocation(newLoc);
				}
				for (int j = 0; j < spawnPoints.size(); j++) {
					spawnPoints.get(j).getMover().setDx(-playerList.get(i).getMover().getDx());
					spawnPoints.get(j).getMover().move();
				}
				int [] newPlayerLoc = { playerList.get(i).getLocation()[0], playerList.get(i).getLocation()[1]};
				if (playerList.get(i).getLocation()[0] < 200) {
					newPlayerLoc[0] = 200;
				} else if (playerList.get(i).getLocation()[0] > screenX - (200+playerList.get(i).getWidth())) {
					newPlayerLoc[0] = screenX - (200+playerList.get(i).getWidth());
				}
				playerList.get(i).setLocation( newPlayerLoc );
			}
		}
		
		// Make the moving platforms actually MOVE
		for (int i = 0; i < dynamicPlatforms.size(); i++) {
			dynamicPlatforms.get(i).run();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void draw() {
		background(color(0, 100, 200));
		drawPlatforms();
		drawMovingPlatforms();
		for (int i = 0; i < playerList.size(); i++) {
			drawPlayer(i);
		}
		update();
		
		if (inList.size() > 0) {
			try {
				for (int i = 0; i < outList.size(); i++) {
					if (!clientReplaying.isEmpty() && clientReplaying.get(i) == 0) {
						HashMap<String, Object>[] objectArray;
						HashMap<String, Object> individualObject;
						
						objectArray = new HashMap[staticPlatforms.size()];
						for (int j = 0; j < staticPlatforms.size(); j++) {
							individualObject = new HashMap<String, Object>();
							individualObject.put("Location", staticPlatforms.get(j).getLocation());
							individualObject.put("Width", staticPlatforms.get(j).getWidth());
							individualObject.put("Height", staticPlatforms.get(j).getHeight());
							objectArray[j] = individualObject;
						}
						objectMap.put("StaticPlatforms", objectArray);
						
						objectArray = new HashMap[dynamicPlatforms.size()];
						for (int j = 0; j < dynamicPlatforms.size(); j++) {
							individualObject = new HashMap<String, Object>();
							individualObject.put("Location", dynamicPlatforms.get(j).getLocation());
							individualObject.put("Width", dynamicPlatforms.get(j).getWidth());
							individualObject.put("Height", dynamicPlatforms.get(j).getHeight());
							objectArray[j] = individualObject;
						}
						objectMap.put("DynamicPlatforms", objectArray);
						
						objectArray = new HashMap[playerList.size()];
						for (int j = 0; j < playerList.size(); j++) {
							individualObject = new HashMap<String, Object>();
							individualObject.put("Location", playerList.get(j).getLocation());
							individualObject.put("Width", playerList.get(j).getWidth());
							individualObject.put("Height", playerList.get(j).getHeight());
							objectArray[j] = individualObject;
						}
						objectMap.put("Players", objectArray);
						
						getOutput(i).reset();
						getOutput(i).writeObject(objectMap);
					} else if (!clientReplaying.isEmpty() && clientReplaying.get(i) == 1){
						getOutput(i).reset();
						getOutput(i).writeObject(replays.get(i));
						clientReplaying.set(i, 0);
					}
				}
			} catch (IOException e) {
				//e.printStackTrace();
			}
		}
	}
	
	public static void main (String[] args) {
		PApplet.main(new String[] { "mainPrograms.MainProgram" });
	}
}
