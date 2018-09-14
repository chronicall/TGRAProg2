package tgra.prog2.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class Spaceship {
	private ModelMatrix origin;
	private Vector3D velocity;
	
	public Spaceship() {
		this.origin = new ModelMatrix();
		this.origin.loadIdentityMatrix();
		this.origin.addScale(3.5f, 3.5f, 1.0f);
		
		this.velocity = new Vector3D();
	}
	
	public void detectCollisions() {
	}
	
	public void update(float deltaTime, int speedMultiplier) {
		// TODO: Check for collision with the edges
		
		// TODO: Check for other collisions, i.e. with asteroids.
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			this.origin.addRoatationZ(180.0f * deltaTime);
		}
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			this.origin.addRoatationZ(-180.0f * deltaTime);
		}
		
		if (Gdx.input.isKeyPressed(Keys.W)) {
			Vector3D facing = this.origin.getB();
			this.velocity.x += facing.x * deltaTime * 10.0f;
			this.velocity.y += facing.y * deltaTime * 10.0f;
		}
		if (Gdx.input.isKeyPressed(Keys.S)) {
			Vector3D facing = this.origin.getB();
			this.velocity.x -= facing.x * deltaTime * 10.0f;
			this.velocity.y -= facing.y * deltaTime * 10.0f;
		}
	
		this.origin.addTranslationBaseCoords(velocity.x * deltaTime, velocity.y * deltaTime, 0.0f);
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
	}
}
