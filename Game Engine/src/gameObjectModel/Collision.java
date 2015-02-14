package gameObjectModel;

import java.util.LinkedList;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

public class Collision implements Serializable {

	private static final long serialVersionUID = -4777551744271112431L;
	
	GameObject obj;
	
	public Collision(GameObject object) {
		this.obj = object;
	}
	
	public boolean testPlayerDeathCollision(DeathZone dz, LinkedList<SpawnPoint> sp) {
		int[] xRange = { this.obj.location[0], this.obj.location[0] + 40 };
		int[] yRange = { this.obj.location[1] - 40, this.obj.location[1] };
		if (dz.intersects(xRange, yRange)) {
			return true;
		}
		return false;
	}
	
	public boolean testPlayerPlatformCollision(Platform p, int i) {
		Rectangle2D platformBox = new Rectangle(p.location[0], p.location[1], p.width, p.height);
		Rectangle2D characterBox = new Rectangle(this.obj.location[0], this.obj.location[1] - 20, 40, 40);
		if (characterBox.intersects(platformBox)) {
			if (characterBox.getY() + 40 > platformBox.getY()) {
				this.obj.location[1] = p.location[1];
				//System.out.println("INTERSECTING " + i);
			} else if (characterBox.getX() + 40 > platformBox.getX() + p.width) {
				this.obj.location[0] = p.location[0]+p.width;//this.obj.mover.canMoveRight = false;
				//System.out.println("INTERSECTS RIGHT " + i);
			} else if (characterBox.getX() < platformBox.getX()) {
				this.obj.location[0] = p.location[0] - 40;//this.obj.mover.canMoveLeft = false;
				//System.out.println("INTERSECTS LEFT " + i);
			}
			return true;
		} else {
			return false;
		}
	}
}
