package gameObjectModel;

import java.io.Serializable;

public class Movement implements Serializable {

	private static final long serialVersionUID = 8536264726208823806L;

	GameObject obj;
	
	int speed =  10;
	int dx = 0;
	int dy = 0;
	int jumpHeight = 0;
	int jumpSpeed = 30;
	boolean jumpOnced = false;
	static boolean isJumping = false;

	public Movement(GameObject object) {
		this.obj = object;
	}
	
	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getDx() {
		return dx;
	}

	public void setDx(int dx) {
		this.dx = dx;
	}
	
	public int getDy() {
		return dy;
	}

	public void setDy(int dy) {
		this.dy = dy;
	}
	
	public int getJumpHeight() {
		return jumpHeight;
	}

	public void setJumpHeight(int jumpHeight) {
		this.jumpHeight = jumpHeight;
	}
	
	public int getJumpSpeed() {
		return jumpSpeed;
	}

	public void setJumpSpeed(int jumpSpeed) {
		this.jumpSpeed = jumpSpeed;
	}

	public boolean hasJumpOnced() {
		return jumpOnced;
	}

	public void setJumpOnced(boolean jumpOnced) {
		this.jumpOnced = jumpOnced;
	}

	public void move() {
		int[] loc = { this.obj.getLocation()[0] + dx, this.obj.getLocation()[1] + dy };
		this.obj.setLocation(loc);
	}

	public void platformMoveLeft() {
		int[] loc = { this.obj.getLocation()[0] - 5, this.obj.getLocation()[1] };
		this.obj.setLocation(loc);
	}
	
	public void platformMoveRight() {
		int[] loc = { this.obj.getLocation()[0] + 5, this.obj.getLocation()[1] };
		this.obj.setLocation(loc);
	}

	/*public void jump(boolean jumpSent) {
		int yUp = 15;
		int[] loc = { this.obj.getLocation()[0], this.obj.getLocation()[1] - yUp };
		this.obj.setLocation(loc);
		/*if (jumpSent && this.obj.getLocation()[1] == platformHeight)
			isJumping = true;
		if (isJumping) {
			int[] loc = { this.obj.getLocation()[0], 
								this.obj.getLocation()[1] - yUp };
			this.obj.setLocation(loc);
		}
		if ( this.obj.getLocation()[1] <= jumpHeight)
			isJumping = false;
		if ( this.obj.getLocation()[1] < platformHeight && !isJumping) {
			int[] loc = { this.obj.getLocation()[0], 
								this.obj.getLocation()[1] + yUp};
			this.obj.setLocation(loc);
		}*/
	//}*/
}
