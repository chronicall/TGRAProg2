package tgra.prog2.game;

public class Spaceship {
	private Point2D position;
	private Vector2D motion;
	private float rotationAngle;
	private float rotationSpeed;
	private float objectSize;
	
	public Spaceship() {
		this.position = new Point2D(0.0f, 0.0f);
		this.motion = new Vector2D(4.5f, 3.0f);
		this.rotationAngle = 0.0f;
		this.rotationSpeed = 0.0f;
		this.objectSize = 0.0f;
	}
	
	public void update(float deltaTime, int speedMultiplier) {
		// Reverses the direction of the spaceship if "colliding" with the edge of the window.
		// TODO: Just stop the movement if at the edge, when manual movement is implemented.
		//		 Currently uses the center of the "command module" to determine the collision.
		if (this.position.x + 1.0f >= 10 || this.position.x - 1.0f <= -10) {
			this.motion.x *= -1;
		}
		if (this.position.y + 1.0f >= 10 || this.position.y - 1.0f <= -10) {
			this.motion.y *= -1;
		}
		this.position.x += this.motion.x * speedMultiplier * deltaTime;
		this.position.y += this.motion.y * speedMultiplier * deltaTime;
		
		this.rotationAngle += rotationSpeed * deltaTime;
		
		this.objectSize = 1.0f;
	}
	
	public void display() {
		ModelMatrix.main.pushMatrix();
		
		ModelMatrix.main.addTranslation(position.x, position.y, 0.0f);
		ModelMatrix.main.addScale(objectSize - 0.2f, objectSize, 1.0f);
		ModelMatrix.main.addRoatationZ(this.rotationAngle);
		GraphicsEnvironment.setShaderModelMatrix(ModelMatrix.main);
		
		// "Command module" of the spaceship.
		GraphicsEnvironment.setColour(1.0f, 0.0f, 0.0f);
		CircleGraphic.drawSolidCircle();
		
		GraphicsEnvironment.setColour(0.4f, 0.0f, 0.0f);
		BoxGraphic.draw();
		
		GraphicsEnvironment.setColour(0.8f, 0.8f, 0.0f);
		CircleGraphic.drawOutlinedCircle();
		
		ModelMatrix.main.pushMatrix();
		
		// "Chassis" of the spaceship.
		ModelMatrix.main.addTranslation(0.0f, -2.0f, 0.0f);
		ModelMatrix.main.addScale(1.5f, 2.0f, 1.0f);
		GraphicsEnvironment.setShaderModelMatrix(ModelMatrix.main);
		
		GraphicsEnvironment.setColour(0.4f, 0.0f, 0.0f);
		BoxGraphic.draw();
		
		ModelMatrix.main.popMatrix();
	}
}
