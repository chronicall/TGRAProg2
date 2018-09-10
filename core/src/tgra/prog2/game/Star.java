package tgra.prog2.game;

public class Star {
	private float objectSize;
	private Point2D position;
	private int quarter;
	
	public Star(float x, float y) {
		this.position = new Point2D(x, y);
		
		this.objectSize = (float)Math.random() * 2;
		
		this.quarter = (int)Math.random() * 100 + 1;
		if (this.quarter > 75) { // x < 0, y < 0
			this.position.x *= -1;
			this.position.y *= -1;
		} else if (this.quarter > 50) { // x < 0, y > 0
			this.position.x *= -1;
		} else if (this.quarter > 25) { // x > 0, y < 0
			this.position.y *= -1;
		} else {} // x > 0, y > 0
	}

	public void update() {
		
	}
	
	public void display() {
		ModelMatrix.main.pushMatrix();
		
		ModelMatrix.main.addTranslation(position.x, position.y, 0.0f);
		ModelMatrix.main.addScale(objectSize, objectSize, 1.0f);
		GraphicsEnvironment.setShaderModelMatrix(ModelMatrix.main);
		
		CircleGraphic.drawSolidCircle();
		
		ModelMatrix.main.popMatrix();
	}
}
