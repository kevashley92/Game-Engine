package mainPrograms;
//import gameObjectModel.DeathZone;
//import gameObjectModel.SpawnPoint;

import java.io.*;
import java.net.*;
import java.util.*;

import eventManagement.ReplaySystem;

import processing.core.*;

public class Client extends PApplet {

	private static final long serialVersionUID = 1L;
	
	private static boolean leftPressed, rightPressed, upPressed, exit = false;
	private static boolean startRecord = false;
	private static boolean stopRecord = true;
	private static boolean sentRecord = false;
	private boolean recordPressed = false;
	private boolean isReplaying = false;
	private boolean mapRecieved = false;
	
	ReplaySystem replaySystem = new ReplaySystem(0);

	private static LinkedList<int[]> playerList = new LinkedList<int[]>();
	private static LinkedList<int[]> staticPlatforms = new LinkedList<int[]>();
	private static LinkedList<int[]> dynamicPlatforms = new LinkedList<int[]>();
	//private static LinkedList<SpawnPoint> spawnPoints = new LinkedList<SpawnPoint>();
	//private static LinkedList<DeathZone> deathZones = new LinkedList<DeathZone>();
	
	private HashMap<String, HashMap<String, Object>[]> objectMap = new HashMap<String, HashMap<String, Object>[]>();
	
	//static PImage currentScreen;
	ObjectInputStream in;
	
	PShape player, dynamicPlatform, staticPlatform;
	
	public void setup() {
		size(MainProgram.screenX, MainProgram.screenY, P2D);
		smooth();
		//frameRate(60);
		//currentScreen = createImage(MainProgram.screenX, MainProgram.screenY, RGB);
	}
	
	// Handle key presses
	public void keyPressed() {
		if (key == CODED) {
			if (keyCode == LEFT)
				leftPressed = true;
			if (keyCode == RIGHT)
				rightPressed = true;
		}
		if (key == ' ') {
			upPressed = true;
		}
		if (key == 'r') {
			if (!startRecord) {
				startRecord = true;
				stopRecord = false;
			}
		}
		if (key == 't') {
			if (startRecord) {
				stopRecord = true;
				startRecord = false;
			}
		}
		if (key == 27)
			exit = true;
	}
		
	// Handle when keys are released
	public void keyReleased() {
		if (key == CODED) {
			if (keyCode == LEFT)
				leftPressed = false;
			if (keyCode == RIGHT)
				rightPressed = false;
		}
		if (key == ' ') {
			upPressed = false;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public void startClient() {
		String hostName = "localhost";
		int portNum = 5005;
		try {
			Socket clientSocket = new Socket(hostName, portNum);
			Scanner input = new Scanner(System.in);
			String command = null;
			ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
			in = new ObjectInputStream(clientSocket.getInputStream());
			
			PApplet.main(new String[] { "mainPrograms.Client" });			
			
			while(true) {
				if (!replaySystem.isReplaying) {
					if (exit)
						command = "exit";
					if (leftPressed)
						command = "left";
					if (rightPressed)
						command = "right";
					if (upPressed)
						command = "jump";
					if (startRecord && !sentRecord) {
						command = "startrecording";
						sentRecord = true;
						recordPressed = true;
					}
					if (stopRecord && sentRecord) {
						command = "stoprecording";
						sentRecord = false;
						recordPressed = true;
						isReplaying = true;
					}
					if (!leftPressed && !rightPressed && !upPressed && !recordPressed && !exit) {
						command = "stop";
					}
					recordPressed = false;
					out.writeObject(command);
				}
				Thread.sleep(10);

				if (isReplaying && !mapRecieved) {
					replaySystem = (ReplaySystem) in.readObject();
					isReplaying = true;
					//System.out.println(replaySystem.getTauEnd());
					objectMap = (HashMap<String, HashMap<String, Object>[]>)replaySystem.getInitialObjectMap();
					mapRecieved = true;
					System.out.println("GOT INITIAL MAP");
				} else if (!replaySystem.isReplaying) {
					objectMap = (HashMap<String, HashMap<String, Object>[]>) in.readObject();
					//System.out.println("USING NORMAL MAP");
				}
				
				int width, height;
				int[] location;
				HashMap<String, Object>[] staticPlatformMap = objectMap.get("StaticPlatforms");
				for (int i = 0; i < staticPlatformMap.length; i++) {
					width = (int) staticPlatformMap[i].get("Width");
					height = (int) staticPlatformMap[i].get("Height");
					location = (int[]) staticPlatformMap[i].get("Location");
					int[] newPlatform = { width, height, location[0], location[1] };
					if (staticPlatforms.size() < staticPlatformMap.length)
						staticPlatforms.add(newPlatform);
					staticPlatforms.set(i, newPlatform);
				}
				
				HashMap<String, Object>[] dynamicPlatformMap = objectMap.get("DynamicPlatforms");
				for (int i = 0; i < dynamicPlatformMap.length; i++) {
					width = (int) dynamicPlatformMap[i].get("Width");
					height = (int) dynamicPlatformMap[i].get("Height");
					location = (int[]) dynamicPlatformMap[i].get("Location");
					int[] newPlatform = { width, height, location[0], location[1] };
					if (dynamicPlatforms.size() < dynamicPlatformMap.length)
						dynamicPlatforms.add(newPlatform);
					dynamicPlatforms.set(i, newPlatform);
				}
				
				HashMap<String, Object>[] playerMap = objectMap.get("Players");
				for (int i = 0; i < playerMap.length; i++) {
					width = (int) playerMap[i].get("Width");
					height = (int) playerMap[i].get("Height");
					location = (int[]) playerMap[i].get("Location");
					int[] newPlayer = { width, height, location[0], location[1] };
					if (playerList.size() < playerMap.length)
						playerList.add(newPlayer);
					playerList.set(i, newPlayer);
				}
				
				if (replaySystem.isReplaying) {
					replaySystem.playEvents(1);
					isReplaying = true;
					if (!replaySystem.isReplaying) {
						isReplaying = false;
						mapRecieved = false;
						System.out.println("Replaying done");
					}
				}
				
				if(command != null && command.equalsIgnoreCase("exit")) {
					break;
				}
			}
			input.close();
			clientSocket.close();
		} catch(IOException e) {
			//e.printStackTrace();
		} catch(InterruptedException e) {
			//e.printStackTrace();
		} catch(ClassNotFoundException e) {
			//e.printStackTrace();
		}
	}
	
	public static void main (String args[]) {
		(new Client()).startClient();
	}
	
	public void draw() {
		background(color(0, 100, 200));
		for (int i = 0; i < staticPlatforms.size(); i++) {
			staticPlatform = createShape(RECT, 0, 0, staticPlatforms.get(i)[0], staticPlatforms.get(i)[1]);
			staticPlatform.setFill(color(0, 300, 0));
			shape(staticPlatform, staticPlatforms.get(i)[2], staticPlatforms.get(i)[3]);
		}
		for (int i = 0; i < dynamicPlatforms.size(); i++) {
			dynamicPlatform = createShape(RECT, 0, 0, dynamicPlatforms.get(i)[0], dynamicPlatforms.get(i)[1]);
			dynamicPlatform.setFill(color(200, 200, 200));
			shape(dynamicPlatform, dynamicPlatforms.get(i)[2], dynamicPlatforms.get(i)[3]);
		}
		for (int i = 0; i < playerList.size(); i++) {
			player = createShape(RECT, 0, 0, playerList.get(i)[0], playerList.get(i)[1]);
			shape(player, playerList.get(i)[2], playerList.get(i)[3]-playerList.get(i)[1]);
		}
	}
	
}
