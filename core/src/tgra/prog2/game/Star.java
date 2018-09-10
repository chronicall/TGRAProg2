package tgra.prog2.game;

public class Star {
	private float objectSize;
	private Point2D position;
	private int quarter;
	
	public Star() {
		this.position = new Point2D((float)Math.random() * 100 + 1, (float)Math.random() * 100 + 1);
		
		this.objectSize = (float)(Math.random() * 1.5f);
		
		// 25% chance for star to be positioned in any one quarter.
		this.quarter = (int)(Math.random() * 100 + 1);
		if (this.quarter <= 25) { // x > 0, y > 0
		}
		if (this.quarter > 25 && this.quarter <= 50) {// x > 0, y < 0
			this.position.y *= -1;
		}
		if (this.quarter > 50 && this.quarter <= 75) { // x < 0, y > 0
			this.position.x *= -1;
		}
		if (this.quarter > 75) { // x < 0, y < 0
			this.position.x *= -1;
			this.position.y *= -1;
		}
	}

	public void update() {
		// Update the position of a star to imitate parallax scrolling.
		// This needs to be uniform with every star in the sky, so need to have some
		// factor that makes them move in a uniform way. Need to use the same coord system
		// and move them by the same vector.
	}
	
	public void display() {
		ModelMatrix.main.pushMatrix();

		ModelMatrix.main.addTranslation(position.x, position.y, 0.0f);
		ModelMatrix.main.addScale(objectSize, objectSize, 1.0f);
		GraphicsEnvironment.setShaderModelMatrix(ModelMatrix.main);
		
		// Draw a circle to signify a star.
		CircleGraphic.drawSolidCircle();
		
		ModelMatrix.main.popMatrix();
	}
}
