package gameObjectModel;
import java.io.Serializable;

public class GameObject implements Serializable {
	
	private static final long serialVersionUID = 3855522882487182084L;
	
	Collision collider;
	Movement mover;
	Rendering renderer;
	
	int[] location;
	int width, height;
	
	public GameObject(int[] loc, int w, int h) {
		collider = null;
		mover = null;
		renderer = null;
		this.location = loc;
		this.width = w;
		this.height = h;
	}
	
	public Collision getCollider() {
		return collider;
	}

	public void setCollider(Collision collider) {
		this.collider = collider;
	}

	public Movement getMover() {
		return mover;
	}

	public void setMover(Movement mover) {
		this.mover = mover;
	}

	public Rendering getRenderer() {
		return renderer;
	}

	public void setRenderer(Rendering renderer) {
		this.renderer = renderer;
	}
	
	public int[] getLocation() {
		return this.location;
	}
	
	public void setLocation(int[] loc) {
		this.location = loc;
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
