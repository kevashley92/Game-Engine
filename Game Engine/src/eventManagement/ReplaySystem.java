package eventManagement;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class ReplaySystem implements Serializable {

	private static final long serialVersionUID = 2987473626536664356L;
	
	private double speed;
	private int playerIndex;
	private long tauStart;
	private long tauEnd;
	private double tic;
	private int deltaTime = 1/60;
	
	public boolean isReplaying = false;
	
	//EventHandler replayHandler = new EventHandler();
	
	private Queue<Event> replayEvents = new LinkedList<Event>();
	
	private HashMap<String, HashMap<String, Object>[]> initialObjectMap = new HashMap<String, HashMap<String, Object>[]>();
	
	public ReplaySystem(int playerIndex) {
		this.setPlayerIndex(playerIndex);
		this.tic = 1;
		this.tauStart = (long)Double.POSITIVE_INFINITY;
		this.tauEnd = (long)Double.POSITIVE_INFINITY;
		/*for (int i = 0; i < Event.EventType.values().length; i++) {
			EventManager.registerMe(Event.EventType.values()[i], replayHandler);
		}*/
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
		this.tic = 1/speed;
		this.tauEnd = (long) (this.tauStart + ((this.tauEnd - this.tauStart)/speed));
	}

	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}

	public long getTauStart() {
		return tauStart;
	}

	public void setTauStart(long startTime) {
		this.tauStart = startTime;
	}

	public long getTauEnd() {
		return tauEnd;
	}

	public void setTauEnd(long tauEnd) {
		this.tauEnd = tauEnd;
	}

	public double getTic() {
		return tic;
	}

	public void setTic(long tic) {
		this.tic = tic;
	}

	public int getDeltaTime() {
		return deltaTime;
	}

	public void setDeltaTime(int deltaTime) {
		this.deltaTime = deltaTime;
	}

	public void addToReplay(Event e) {
		this.replayEvents.add(e);
	}
	
	public Queue<Event> getReplayEvents() {
		return replayEvents;
	}

	public void setReplayEvents(Queue<Event> replayEvents) {
		this.replayEvents = replayEvents;
	}

	public HashMap<String, HashMap<String, Object>[]> getInitialObjectMap() {
		return initialObjectMap;
	}

	public void setInitialObjectMap(HashMap<String, HashMap<String, Object>[]> initialObjectMap) {
		this.initialObjectMap = initialObjectMap;
	}

	public void playEvents(double speed) {
		//HashMap<String, HashMap<String, Object>[]> newMap = new HashMap<String, HashMap<String, Object>[]>();
		setSpeed(speed);
		
		//System.out.println(this.tauStart + " " + this.tauEnd);
		
		//int playerSpeed = MainProgram.playerList.get(this.playerIndex).getMover().getSpeed();
		//MainProgram.playerList.get(this.playerIndex).getMover().setSpeed((int) (playerSpeed * speed));
		
		Event pEvent = this.replayEvents.peek();
		if (pEvent == null) {
			System.out.println("NO EVENTS");
		}
		if (pEvent != null) {
			//if (( speed >= 1 && t == 1) || (speed < 1 && t == )) {
			try {
				Thread.sleep((long)((1000/60)*this.tic));
			} catch (InterruptedException e) {
				//e.printStackTrace();
			}
			this.replayEvents.remove();
			System.out.println(pEvent.getDeliveryTime() + " " + pEvent.type.toString() + " " + pEvent.getArgs().toString());
			/*newMap = replayHandler.onEvent(pEvent);*/pEvent.dispatch();
			pEvent = this.replayEvents.peek();
			this.isReplaying = true;
		} else {
			this.isReplaying = false;
		}
		
		//return newMap;
	}
}
