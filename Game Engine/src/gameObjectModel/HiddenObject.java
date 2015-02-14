package gameObjectModel;

public class HiddenObject extends GameObject {

	private static final long serialVersionUID = -4529969883010175137L;
	int width, height;
	
	public HiddenObject(int[] loc, int w, int h) {
		super(loc, w, h);
		this.mover = new Movement(this);
		this.width = w;
		this.height = h;
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
