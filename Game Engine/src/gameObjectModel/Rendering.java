package gameObjectModel;

import java.io.Serializable;

import processing.core.PShape;

public class Rendering implements Serializable {
	
	private static final long serialVersionUID = -5817042434633850624L;
	
	GameObject obj;
	transient PShape visualObject;
	
	public Rendering(GameObject object) {
		this.obj = object;
	}
	
	public PShape getVisualObject() {
		return this.visualObject;
	}

	public void setVisualObject(PShape visualObject) {
		this.visualObject = visualObject;
	}
}
