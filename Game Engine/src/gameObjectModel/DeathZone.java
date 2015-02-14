package gameObjectModel;

public class DeathZone extends HiddenObject {

	private static final long serialVersionUID = -7211744464524662338L;

	public DeathZone(int[] loc, int w, int h) {
		super(loc, w, h);
	}
	
	public boolean intersects(int[] xRange, int[] yRange) {
		boolean intersecting = false;
		for (int i = 0; i < xRange.length; i++) {
			if (((xRange[i] > this.location[0]) && (xRange[i] < this.location[0] + this.width))
					&& ((yRange[i] > this.location[1]) && (yRange[i] < this.location[1] + this.width))) {
				intersecting = true;
			}
		}
		return intersecting;
	}
}
