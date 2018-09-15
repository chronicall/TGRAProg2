package tgra.prog2.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class Spaceship {
	private ModelMatrix origin;
	private Vector3D velocity;
	private int speedMultiplier;
	
	public ArrayList<LaserBlast> blasts;
	private float blasterCooldown;
	private int blasterCount;
	
	public Spaceship() {
		this.origin = new ModelMatrix();
		this.origin.loadIdentityMatrix();
		this.origin.addScale(3.5f, 3.5f, 1.0f);
		
		this.velocity = new Vector3D();
		
		this.blasts = new ArrayList<LaserBlast>();
		
		this.blasterCooldown = 0.0f;
		this.blasterCount = 3;
	}
	
	public void update(float deltaTime) {
		// If reaching the edge of the screen, appear on the other side of it.
		Point3D originPoint = this.origin.getOrigin();
		if (originPoint.x >= 100) {
			this.origin.addTranslationBaseCoords(-200.0f, 0.0f, 0.0f);
		} else if (originPoint.x <= -100) {
			this.origin.addTranslationBaseCoords(200.0f, 0.0f, 0.0f);
		}
		if (originPoint.y >= 100) {
			this.origin.addTranslationBaseCoords(0.0f, -200.0f, 0.0f);
		} else if (originPoint.y <= -100) {
			this.origin.addTranslationBaseCoords(0.0f, 200.0f, 0.0f);
		}
		
		// Speed multiplier just for fun.
		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
			this.speedMultiplier = 3;
		} else {
			this.speedMultiplier = 1;
		}
		
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			this.origin.addRoatationZ(180.0f * deltaTime);
		}
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			this.origin.addRoatationZ(-180.0f * deltaTime);
		}
		
		if (Gdx.input.isKeyPressed(Keys.W)) {
			Vector3D facing = this.origin.getB();
			this.velocity.x += facing.x * deltaTime * 10.0f * this.speedMultiplier;
			this.velocity.y += facing.y * deltaTime * 10.0f * this.speedMultiplier;
		}
		if (Gdx.input.isKeyPressed(Keys.S)) {
			Vector3D facing = this.origin.getB();
			this.velocity.x -= facing.x * deltaTime * 10.0f;
			this.velocity.y -= facing.y * deltaTime * 10.0f;
		}
		
		this.blasterCooldown += 1.0f * deltaTime;
		
		if (this.blasterCooldown >= 2.0f) {
			this.blasterCooldown = 0.0f;
			this.blasterCount = 3;
		}
		
		if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
			// Only allow 6 laser blasts to be "alive" at any one time.
			if (this.blasterCount > 0 && this.blasterCooldown <= 3.0f) {
				this.blasterCount -= 1;
				LaserBlast blast = new LaserBlast(this.origin, this.velocity, 1);
				blasts.add(blast);
				
				blast = new LaserBlast(this.origin, this.velocity, -1);
				blasts.add(blast);
			}
		}
	
		this.origin.addTranslationBaseCoords(velocity.x * deltaTime, velocity.y * deltaTime, 0.0f);
		
		for (LaserBlast blast : blasts) {
			blast.update(deltaTime);
		}
	}
	
	public void display() {
		ModelMatrix.main.pushMatrix();
		
		ModelMatrix.main.addTransformation(origin.matrix);
		GraphicsEnvironment.setShaderModelMatrix(ModelMatrix.main);
		
		// "Command module" of the spaceship.
		GraphicsEnvironment.setColour(1.0f, 0.0f, 0.0f);
		CircleGraphic.drawSolidCircle();

		GraphicsEnvironment.setColour(0.8f, 0.8f, 0.0f);
		CircleGraphic.drawOutlinedCircle();
		
		ModelMatrix.main.pushMatrix();
		ModelMatrix.main.addScale(2.5f, 0.5f, 1.0f);
		GraphicsEnvironment.setShaderModelMatrix(ModelMatrix.main);
		
		GraphicsEnvironment.setColour(0.0f, 0.0f, 1.0f);
		BoxGraphic.draw();
		ModelMatrix.main.popMatrix();
		
		ModelMatrix.main.pushMatrix();
		ModelMatrix.main.addTranslation(1.3f, 0.5f, 0.0f);
		ModelMatrix.main.addRoatationZ(-110);
		ModelMatrix.main.addScale(2.25f, 0.35f, 1.0f);
		GraphicsEnvironment.setShaderModelMatrix(ModelMatrix.main);
		
		GraphicsEnvironment.setColour(0.0f, 0.0f, 1.0f);
		BoxGraphic.draw();
		ModelMatrix.main.popMatrix();
		
		ModelMatrix.main.pushMatrix();
		ModelMatrix.main.addTranslation(-1.3f, 0.5f, 0.0f);
		ModelMatrix.main.addRoatationZ(110);
		ModelMatrix.main.addScale(2.25f, 0.35f, 1.0f);
		GraphicsEnvironment.setShaderModelMatrix(ModelMatrix.main);
		
		GraphicsEnvironment.setColour(0.0f, 0.0f, 1.0f);
		BoxGraphic.draw();
		ModelMatrix.main.popMatrix();
		
		ModelMatrix.main.popMatrix();
		
		for (LaserBlast blast : blasts) {
			blast.display();
		}
	}
	
	// TODO: Use a better collision algorithm..
	public boolean detectCollision(Asteroid asteroid) {
		Point3D originPoint = this.origin.getOrigin();
		float distance = (float)Math.pow(asteroid.position.x - originPoint.x, 2) + (float)Math.pow(asteroid.position.y - originPoint.y, 2);
		
		if (distance < Math.pow(3.5f + asteroid.objectSize, 2)) {
			return true;
		}
		
		return false;
	}
}
