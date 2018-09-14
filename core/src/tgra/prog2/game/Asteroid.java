package tgra.prog2.game;

import java.util.ArrayList;

public class Asteroid {
	// Will contain functions and variables for the asteroid object(s).
	Point2D position;
	private Vector2D motion;
	float rotationAngle;
	private float rotationSpeed;
	float objectSize;
	
	public Asteroid(float x, float y, float size, float mx, float my) {		
		int quarter = (int)(Math.random() * 100 + 1);

		if (quarter > 25 && quarter <= 50) {// x > 0, y < 0
			y *= -1;
		}
		if (quarter > 50 && quarter <= 75) { // x < 0, y > 0
			x *= -1;
		}
		if (quarter > 75) { // x < 0, y < 0
			x *= -1;
			y *= -1;
		}
		
		this.position = new Point2D(x, y);
		this.motion = new Vector2D(mx, my);
		this.rotationAngle = 0.0f;
		this.rotationSpeed = 0.2f;
		this.objectSize = size;
	}
	
	public Asteroid() {
		float x = (float) (Math.random() * (100));
		float y = (float) (Math.random() * (100));		
		float mx = (float) (Math.random() * (20-2));
		float my = (float) (Math.random() * (20-2));
		
		int quarter = (int)(Math.random() * 100 + 1);

		if (quarter > 25 && quarter <= 50) {// x > 0, y < 0
			y *= -1;
		}
		if (quarter > 50 && quarter <= 75) { // x < 0, y > 0
			x *= -1;
		}
		if (quarter > 75) { // x < 0, y < 0
			x *= -1;
			y *= -1;
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
		this.position.x += this.motion.x * deltaTime;
		this.position.y += this.motion.y * deltaTime;
		
		this.rotationAngle += rotationSpeed * deltaTime;
	}
		
	public void display() {
		ModelMatrix.main.pushMatrix();
		
		ModelMatrix.main.addTranslation(position.x, position.y, 0.0f);
		ModelMatrix.main.addScale(objectSize - 0.2f, objectSize, 1.0f);
		ModelMatrix.main.addRoatationZ(this.rotationAngle);
		GraphicsEnvironment.setShaderModelMatrix(ModelMatrix.main);
		
		GraphicsEnvironment.setColour(0.5f, 0.5f, 0.5f);
		CircleGraphic.drawSolidCircle();
		
		ModelMatrix.main.popMatrix();
	}
	
	public ArrayList<Asteroid> detectCollision(ArrayList<Asteroid> asteroids)
	{
		boolean collision = false;
		ArrayList<Asteroid> asteroidsToAdd = new ArrayList<Asteroid>();
		ArrayList<Asteroid> asteroidsToRm = new ArrayList<Asteroid>();
		
		for(Asteroid currAsteroid: asteroids) {
			
			if(currAsteroid == this) {
				continue;
			}	
			
			if(asteroidsToRm.contains(currAsteroid)) {
				continue;
			}
			
			if (Math.abs((this.position.x + this.objectSize) - (currAsteroid.position.x + currAsteroid.objectSize)) <= this.objectSize
					&& Math.abs((this.position.y + this.objectSize) - (currAsteroid.position.y + currAsteroid.objectSize)) <= this.objectSize) {
				
				collision = true;
				
				//System.out.println("x: " + ((this.position.x + this.objectSize) - (currAsteroid.position.x + currAsteroid.objectSize)));
				//System.out.println("y: " + ((this.position.y + this.objectSize) - (currAsteroid.position.y + currAsteroid.objectSize)));
				System.out.println("object: "+ this.objectSize);
				System.out.println("object curr: "+ currAsteroid.objectSize);
				
				if(currAsteroid.objectSize > 1.5) {
					asteroidsToAdd.add(new Asteroid(currAsteroid.position.x, currAsteroid.position.y, currAsteroid.objectSize/2, -currAsteroid.motion.x, -currAsteroid.motion.y));
					asteroidsToAdd.add(new Asteroid(currAsteroid.position.x, currAsteroid.position.y, currAsteroid.objectSize/2, currAsteroid.motion.x, currAsteroid.motion.y));
				}
				
				if(this.objectSize > 1.5) {
					//numAsteroids++;
					asteroidsToAdd.add(new Asteroid(this.position.x, this.position.y, this.objectSize/2, -this.motion.x, -this.motion.y));
					asteroidsToAdd.add(new Asteroid(this.position.x, this.position.y, this.objectSize/2, this.motion.x, this.motion.y));
				}
		
				asteroidsToRm.add(currAsteroid);
			}
		}
		
		for(Asteroid a: asteroidsToAdd) {
			asteroids.add(a);
		}
		
		if(collision) {
			asteroidsToRm.add(this);
		}
		
		for(Asteroid a: asteroidsToRm) {
			asteroids.remove(a);
		}
		
		return asteroids;		
	}
	
	public boolean intersects(Asteroid checkAsteroid) {
		return false;
	}
}
