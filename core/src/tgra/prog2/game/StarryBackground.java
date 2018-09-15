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
		for (Star star : stars) {
			star.update(deltaTime);
		}
	}

	public void display() {
		ModelMatrix.main.pushMatrix();
		
		GraphicsEnvironment.setColour(1.0f, 1.0f, 1.0f);
		for (Star star : stars) {
			star.display();
		}
		
		ModelMatrix.main.popMatrix();
	}
}
