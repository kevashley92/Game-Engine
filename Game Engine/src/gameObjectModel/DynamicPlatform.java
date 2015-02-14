package gameObjectModel;
import processing.core.PShape;

public class DynamicPlatform extends Platform implements Runnable {

	private static final long serialVersionUID = 4482894922279523472L;
	
	public int[] startLoc;
	private static boolean left;
	
	public DynamicPlatform (PShape obj, int[] loc, int w, int h) {
		super(obj, loc, w, h, true);
		startLoc = loc;
		this.mover.speed = 5;
	}

	public void run() {
			if ( !left && this.location[0] <= startLoc[0] + 300) {
				this.mover.setDx(this.mover.speed);//this.mover.platformMoveRight();
				this.mover.move();
				if (this.location[0] == startLoc[0] + 300)
					left = true;
			} else if ( left && this.location[0] >= startLoc[0]) {
				this.mover.setDx(-this.mover.speed);//this.mover.platformMoveLeft();
				this.mover.move();
				if ( this.location[0] == startLoc[0])
					left = false;
			}
	}
}
