package tgra.prog2.game;

import java.util.ArrayList;

public class StarryBackground {
	private ArrayList<Star> stars;
	
	public StarryBackground(int numberOfStars) {
		this.stars = new ArrayList<Star>();
		for (int i = 0; i < numberOfStars; i++) {
			stars.add(new Star());
		}
	}
	
	public void update(float deltaTime) {
		// Update every star in the sky.
		// Maybe stick with having it in the same loop in display
		// rather than loop over the list twice..
		for (Star star : stars) {
			star.update(deltaTime);
		}
	}

	public void display() {
		ModelMatrix.main.pushMatrix();
		
		GraphicsEnvironment.setColour(1.0f, 1.0f, 1.0f);
		// Draw every star
		for (Star star : stars) {
			//star.update();
			star.display();
		}
		
		ModelMatrix.main.popMatrix();
	}
}
