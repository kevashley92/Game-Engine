package gameObjectModel;

public class SpawnPoint extends HiddenObject {

	private static final long serialVersionUID = -6795556716627328071L;

	private int spawnIndex;	

	public SpawnPoint(int[] loc, int ind) {
		super(loc, 1, 1);
		spawnIndex = ind;
	}
	
	public int getSpawnIndex() {
		return spawnIndex;
	}

	public void setSpawnIndex(int spawnIndex) {
		this.spawnIndex = spawnIndex;
	}
	
}
