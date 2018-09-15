package tgra.prog2.game;

public class LaserBlast {
	public ModelMatrix origin;
	private Vector3D velocity;
	
	public LaserBlast(ModelMatrix origin, Vector3D originVelocity, int tmp) {
		this.origin = new ModelMatrix();
		this.origin.loadIdentityMatrix();
		this.origin.addTransformation(origin.matrix);
		this.origin.addTranslation(1.8f * tmp, 2.0f, 1.0f);
		
		this.velocity = origin.getB();
		this.velocity.x *= 40.0f;
		this.velocity.y *= 40.0f;
		this.velocity.x += originVelocity.x;
		this.velocity.y += originVelocity.y;
	}
	
	public void update(float deltaTime) {
	
		this.origin.addTranslationBaseCoords(velocity.x * deltaTime, velocity.y * deltaTime, 0.0f);
	}
	
	public void display() {
		ModelMatrix.main.pushMatrix();
		
		ModelMatrix.main.addTransformation(origin.matrix);

		ModelMatrix.main.addScale(2.0f, 2.0f, 1.0f);
		ModelMatrix.main.addScale(0.2f, 2.0f, 1.0f);
		GraphicsEnvironment.setShaderModelMatrix(ModelMatrix.main);
		
		GraphicsEnvironment.setColour(1.0f, 1.0f, 0.0f);
		BoxGraphic.draw();
		
		ModelMatrix.main.popMatrix();
	}
	
	public boolean detectCollision(Asteroid asteroid) {
		Point3D originPoint = this.origin.getOrigin();
		float distance = (float)Math.pow(asteroid.position.x - originPoint.x, 2) + (float)Math.pow(asteroid.position.y - originPoint.y, 2);
		
		if (distance < Math.pow(1.0f + asteroid.objectSize, 2)) {
			return true;
		}
		
		return false;
	}
}
