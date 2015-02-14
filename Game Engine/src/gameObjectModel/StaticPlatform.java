package gameObjectModel;
import processing.core.*;

public class StaticPlatform extends Platform {

	private static final long serialVersionUID = 5634681979676652610L;

	public StaticPlatform (PShape obj, int[] loc, int w, int h) {
		super(obj, loc, w, h, false);
	}
	
}
