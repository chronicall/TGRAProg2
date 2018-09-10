package tgra.prog2.game;

import java.util.ArrayList;

public class StarryBackground {
	private ArrayList<Star> stars;
	
	public StarryBackground(int numberOfStars) {
		this.stars = new ArrayList<Star>();
		
		for (int i = 0; i < numberOfStars; i++) {
			stars.add(new Star((float)Math.random() * 100 + 1, (float)Math.random() * 100 + 1));
		}
	}
	
	public void update() {
		
	}

	public void display() {
		ModelMatrix.main.pushMatrix();
		
		GraphicsEnvironment.setColour(1.0f, 1.0f, 1.0f);
		// Draw a bunch of circle graphics. All of whom are white.
		
		// Draw every star
		for (Star star : stars) {
			//star.update();
			star.display();
		}
		
		ModelMatrix.main.popMatrix();
	}
}
