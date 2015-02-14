package gameObjectModel;

import processing.core.PShape;

public class Player extends Character{

	private static final long serialVersionUID = -7675980941069537733L;

	int playerIndex;
	
	int width = 40;
	int height = 40;
	
	public Player(PShape obj, int[] loc, int index, int w, int h) {
		super(obj, loc, w, h);
		this.playerIndex = index;
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
}
