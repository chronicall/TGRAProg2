package tgra.prog2.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import tgra.prog2.game.Prog2Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Programming Assignment 2 - Spaceship vs. Asteroids"; // or whatever you like
		config.height = 800;
		config.width = 1000;
		new LwjglApplication(new Prog2Game(), config);
	}
}
