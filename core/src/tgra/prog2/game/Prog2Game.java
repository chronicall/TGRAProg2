package tgra.prog2.game;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;

public class Prog2Game extends ApplicationAdapter {
	private int speedMultiplier;

	private int renderingProgramID;
	private int vertexShaderID;
	private int fragmentShaderID;
	
	private StarryBackground background;
	
	private Spaceship spaceship1;
	private Spaceship spaceship2;
	
	public ArrayList<Asteroid> asteroids;
	private int numAsteroids;

	@Override
	public void create () {
		this.speedMultiplier = 1;

		String vertexShaderString;
		String fragmentShaderString;

		vertexShaderString = Gdx.files.internal("shaders/simple2D.vert").readString();
		fragmentShaderString =  Gdx.files.internal("shaders/simple2D.frag").readString();

		vertexShaderID = Gdx.gl.glCreateShader(GL20.GL_VERTEX_SHADER);
		fragmentShaderID = Gdx.gl.glCreateShader(GL20.GL_FRAGMENT_SHADER);
	
		Gdx.gl.glShaderSource(vertexShaderID, vertexShaderString);
		Gdx.gl.glShaderSource(fragmentShaderID, fragmentShaderString);
	
		Gdx.gl.glCompileShader(vertexShaderID);
		Gdx.gl.glCompileShader(fragmentShaderID);

		renderingProgramID = Gdx.gl.glCreateProgram();
	
		Gdx.gl.glAttachShader(renderingProgramID, vertexShaderID);
		Gdx.gl.glAttachShader(renderingProgramID, fragmentShaderID);
	
		Gdx.gl.glLinkProgram(renderingProgramID);

		GraphicsEnvironment.positionLoc				= Gdx.gl.glGetAttribLocation(renderingProgramID, "a_position");
		Gdx.gl.glEnableVertexAttribArray(GraphicsEnvironment.positionLoc);

		GraphicsEnvironment.modelMatrixLoc			= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_modelMatrix");
		GraphicsEnvironment.projectionMatrixLoc	= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_projectionMatrix");

		GraphicsEnvironment.colorLoc				= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_color");

		Gdx.gl.glUseProgram(renderingProgramID);
		
		GraphicsEnvironment.setWindow(0, Gdx.graphics.getWidth(), 0, Gdx.graphics.getHeight());

		//COLOR IS SET HERE
		Gdx.gl.glUniform4f(GraphicsEnvironment.colorLoc, 0.7f, 0.2f, 0, 1);

		BoxGraphic.create();
		CircleGraphic.create();
		
		ModelMatrix.main = new ModelMatrix();
		
		// Set up background.
		background = new StarryBackground(100);
		
		spaceship1 = new Spaceship();
		spaceship2 = new Spaceship();
		
		// Set up asteroids.
		numAsteroids = 10; 
		asteroids = new ArrayList<Asteroid>();
		for(int i = 0; i < numAsteroids; i++) {
			asteroids.add(new Asteroid());
		}
	}
	
	public void update () {
		float deltaTime = Gdx.graphics.getDeltaTime();
		
		// Speed multiplier just for fun.
		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
			this.speedMultiplier = 5;
		} else {
			this.speedMultiplier = 1;
		}
		
		spaceship1.update(deltaTime, speedMultiplier);
		spaceship2.update(deltaTime, speedMultiplier + 2);
		 
		ListIterator<Asteroid> asteroidIterator = asteroids.listIterator();
		Asteroid currAsteroid;
		while (asteroidIterator.hasNext()) {
			currAsteroid = asteroidIterator.next();
			
			currAsteroid.update(deltaTime);
		}
		
		
		ArrayList<Asteroid> asteroidsTmp = new ArrayList<Asteroid>();
		ArrayList<Asteroid> asteroidsToAdd = new ArrayList<Asteroid>();
		Asteroid currAsteroidCheck;
		asteroidsTmp.addAll(asteroids);
		asteroidIterator = asteroids.listIterator();
		while (asteroidIterator.hasNext()) {
			currAsteroid = asteroidIterator.next();
			boolean collision = false;
			
			asteroidsTmp = currAsteroid.detectCollision(asteroidsTmp);
			
			/*if(collision)
			{
					numAsteroids++;
					
					asteroidIterator.add(new Asteroid(currAsteroid.position.x, currAsteroid.position.y, currAsteroid.objectSize/2));
					asteroidIterator.add(new Asteroid(currAsteroid.position.x, currAsteroid.position.y, currAsteroid.objectSize/2));
					
					//numAsteroids++;
					//asteroidIterator.add(new Asteroid(currAsteroidCheck.position.x, currAsteroidCheck.position.y, currAsteroidCheck.objectSize/2));
					//asteroidIterator.add(new Asteroid(currAsteroidCheck.position.x, currAsteroidCheck.position.y, currAsteroidCheck.objectSize/2));
			
					asteroidsToRemove.add(currAsteroid);
					asteroidsToRemove.add(currAsteroidCheck);
			}	
			*/	
			//}
		}
		
		asteroids.removeAll(asteroids);
		asteroids.addAll(asteroidsTmp);
		/*

		asteroidIterator = asteroidsToRemove.listIterator();
		while (asteroidIterator.hasNext()) {
			currAsteroid = asteroidIterator.next();
			asteroids.remove(currAsteroid);
		}*/
		/*
		asteroidIterator = asteroidsToAdd.iterator();
		while (asteroidIterator.hasNext()) {
			currAsteroid = asteroidIterator.next();
			asteroids.add(currAsteroid);
		}*/
	}
	
	public void display() {
		GraphicsEnvironment.setWindow(-100, 100, -100, 100);
		
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		ModelMatrix.main.loadIdentityMatrix();
		
		// Display background, currently just a black window.
		// TODO: Add a starry background.
		background.display();
		
		spaceship1.display();
		
		// Display asteroids.
		Iterator<Asteroid> asteroidIterator = asteroids.iterator();
		Asteroid currAsteroid;
		while (asteroidIterator.hasNext()) {
			currAsteroid = asteroidIterator.next();
			
			currAsteroid.display();
		}
		
		// Adds another spaceship, but local coords with the shitty collision makes it all weird.
//		ModelMatrix.main.pushMatrix();
		
//		ModelMatrix.main.addTranslation(3.0f, 4.0f, 0.0f);
//		GraphicsEnvironment.setShaderModelMatrix(ModelMatrix.main);
//		
//		spaceship2.display();
	}

	@Override
	public void render () {
		update();
		display();
	}
}
