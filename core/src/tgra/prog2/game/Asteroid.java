package tgra.prog2.game;

import java.util.ArrayList;

public class Asteroid {
	Point2D position;
	private Vector2D motion;
	float rotationAngle;
	private float rotationSpeed;
	float objectSize;
	float invisTime;
	
	public Asteroid(float x, float y, float size, float mx, float my) {	
		while(my > 15) {
			my = my/2;
		}
		while(mx > 15) {
			mx = mx/2;
		}
		this.position = new Point2D(x, y);
		this.motion = new Vector2D(mx, my);
		this.rotationAngle = 0.0f;
		this.rotationSpeed = 0.2f;
		this.objectSize = size;
		this.invisTime = 10.0f;
	}
	
	public Asteroid() {
		float x = (float) (Math.random() * (95));
		float y = (float) (Math.random() * (95));		
		float mx = (float) (Math.random() * (20-2));
		float my = (float) (Math.random() * (20-2));
		
		int quarter = (int)(Math.random() * 100 + 1);

		if (quarter > 25 && quarter <= 50) {// x > 0, y < 0
			y *= -1;
			mx *= -1;
		}
		if (quarter > 50 && quarter <= 75) { // x < 0, y > 0
			x *= -1;
			my *= -1;
		}
		if (quarter > 75) { // x < 0, y < 0
			x *= -1;
			y *= -1;
			mx *= -1;
			my *= -1;
		}
		
		this.position = new Point2D(x, y);
		this.motion = new Vector2D(mx, my);
		this.rotationAngle = 0.0f;
		this.rotationSpeed = 0.2f;
		this.objectSize = 5.0f;
	}
	
	public void update(float deltaTime) {
		// Reverses the direction of the asteroid if "colliding" with the edge of the window.
		if (this.position.x + this.objectSize >= 100 || this.position.x - this.objectSize <= -100) {
			this.motion.x *= -1;
		}
		if (this.position.y + this.objectSize >= 100 || this.position.y - this.objectSize <= -100) {
			this.motion.y *= -1;
		}
		
		this.position.x 	+= this.motion.x * deltaTime;
		this.position.y 	+= this.motion.y * deltaTime;		
		this.rotationAngle 	+= rotationSpeed * deltaTime;
		
		if(this.invisTime > 0f) {
			this.invisTime -= 0.5f;
		}
	}
		
	public void display() {
		
		if(this.invisTime > 5f) {
			return;
		}
		
		ModelMatrix.main.pushMatrix();
		
		ModelMatrix.main.addTranslation(position.x, position.y, 0.0f);
		ModelMatrix.main.addScale(objectSize - 0.2f, objectSize, 1.0f);
		ModelMatrix.main.addRoatationZ(this.rotationAngle);
		GraphicsEnvironment.setShaderModelMatrix(ModelMatrix.main);
		
		GraphicsEnvironment.setColour(0.0f, 0.5f, 0.5f);
		CircleGraphic.drawSolidCircle();
		
		ModelMatrix.main.popMatrix();
	}
	
	// Breaks up the asteroid passed into two smaller ones. The smallest size possible is 1.5f.
	public ArrayList<Asteroid> breakAsteroid(Asteroid asteroid) {
		ArrayList<Asteroid> asteroidsToAdd = new ArrayList<Asteroid>();
		
		if (asteroid.objectSize > 1.5f) {
	 		asteroidsToAdd.add(new Asteroid(
					asteroid.position.x, asteroid.position.y, asteroid.objectSize/2, 
					asteroid.motion.x + 15, asteroid.motion.y + 15
				)
			);	
			asteroidsToAdd.add(new Asteroid(
					asteroid.position.x, asteroid.position.y, asteroid.objectSize/2, 
					asteroid.motion.x - 15, -asteroid.motion.y - 15
				)
			);
		}
		
		return asteroidsToAdd;
	}
	
	// TODO: Use a better collision algorithm..
	public boolean detectCollision(ArrayList<Asteroid> asteroids)
	{
		if(this.invisTime > 0f) {
			return false;
		}
		
		for(Asteroid asteroid: asteroids) {
			// We don't want to check for collision against the same object.
			if(asteroid == this) {
				continue;
			}
			
			float distance = (float)Math.pow(this.position.x - asteroid.position.x, 2) + (float)Math.pow(this.position.y - asteroid.position.y, 2);
			
			if (distance <= Math.pow(asteroid.objectSize + this.objectSize, 2)) {
				return true;
			}
		}
		
		return false;		
	}
}
