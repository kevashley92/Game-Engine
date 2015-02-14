package gameObjectModel;

import processing.core.PShape;

public class PhysicalObject extends GameObject {

	private static final long serialVersionUID = -742553541091207461L;

	public PhysicalObject(PShape obj, int[] loc, int w, int h) {
		super(loc, w, h);
		this.collider = new Collision(this);
		this.mover = new Movement(this);
		this.renderer = new Rendering(this);
		this.renderer.setVisualObject(obj);
	}
}
