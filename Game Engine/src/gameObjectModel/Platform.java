package gameObjectModel;
import processing.core.*;

public class Platform extends PhysicalObject {
	
	private static final long serialVersionUID = -7303771166205366778L;
	boolean isMoving;
	
	public Platform (PShape obj, int[] loc, int w, int h, boolean moving) {
		super(obj, loc, w, h);
		this.isMoving = moving;
	}
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public boolean isMoving() {
		return isMoving;
	}

	public void setMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}
	
	public boolean intersects(int[] xRange, int[] yRange) {
		boolean intersecting = false;
		for (int i = 0; i < xRange.length; i++) {
			if (((xRange[i] > this.getLocation()[0]) && (xRange[i] < this.getLocation()[0] + this.width))
					&& ((yRange[i] > this.getLocation()[1]) && (yRange[i] < this.getLocation()[1] + this.height))) {
				intersecting = true;
			}
		}
		return intersecting;
	}
	
}
