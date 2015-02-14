package gameObjectModel;
import processing.core.PShape;

public class Character extends GameObject {

	private static final long serialVersionUID = -6578023083232986272L;
	
	private boolean dead = true;

	public Character(PShape obj, int[] loc, int w, int h) {
		super(loc, w, h);
		this.collider = new Collision(this);
		this.mover = new Movement(this);
		this.renderer = new Rendering(this);
		this.renderer.setVisualObject(obj);
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}
}
