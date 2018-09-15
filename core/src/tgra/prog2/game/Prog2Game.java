package tgra.prog2.game;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class Prog2Game extends ApplicationAdapter {

	private int renderingProgramID;
	private int vertexShaderID;
	private int fragmentShaderID;
	
	private StarryBackground background;
	
	private Spaceship spaceship;
	
	public ArrayList<Asteroid> asteroids;
	private int numAsteroids;

	@Override
	public void create () {
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

		// Initial set up of objects.
		BoxGraphic.create();
		CircleGraphic.create();
		
		ModelMatrix.main = new ModelMatrix();
		
		this.background = new StarryBackground(250);
		
		this.spaceship = new Spaceship();
		
		this.numAsteroids = 6;
		this.asteroids = new ArrayList<Asteroid>();
		for(int i = 0; i < this.numAsteroids; i++) {
			this.asteroids.add(new Asteroid());
		}
	}
	
	// Called when the ship is destroyed and restarts the "level".
	public void resetLevel() {
		this.spaceship = new Spaceship();
		
		this.asteroids = new ArrayList<Asteroid>();
		for(int i = 0; i < this.numAsteroids; i++) {
			this.asteroids.add(new Asteroid());
		}
	}
	
	// Called when there are no asteroids left of this level.
	// The next level has 2 more asteroids than the previous.
	public void nextLevel() {
		this.spaceship = new Spaceship();
		this.numAsteroids += 2;
		this.asteroids = new ArrayList<Asteroid>();
		for(int i = 0; i < this.numAsteroids; i++) {
			this.asteroids.add(new Asteroid());
		}
	}
	
	public void update () {
		if (asteroids.size() == 0) {
			this.nextLevel();
		}
		
		float deltaTime = Gdx.graphics.getDeltaTime();
		
		spaceship.update(deltaTime);
		
		// TODO: Optimize to not check every asteroid, only areas of the screen that
		// 		 could possibly end up with a collision happening.
		for (Asteroid asteroid : asteroids) {
			asteroid.update(deltaTime);
			if (spaceship.detectCollision(asteroid)) {
				this.resetLevel();
			}
		}
		
		// To be able to manipulate the list when looping through it.
		ArrayList<Asteroid> asteroidsTmp = new ArrayList<Asteroid>();
		asteroidsTmp.addAll(asteroids);
		
		ArrayList<LaserBlast> blastsToRemove = new ArrayList<LaserBlast>();
		
		for (Asteroid asteroid : asteroids) {
			for (LaserBlast blast : spaceship.blasts) {
				Point3D blastOriginPoint = blast.origin.getOrigin();
				if (blastOriginPoint.x >= 100 || blastOriginPoint.x <= -100 || blastOriginPoint.y >= 100 || blastOriginPoint.y <= -100) {
					blastsToRemove.add(blast);
				}
				if (blast.detectCollision(asteroid)) {
					asteroidsTmp.addAll(asteroid.breakAsteroid(asteroid));
					asteroidsTmp.remove(asteroid);
					blastsToRemove.add(blast);
				}
			}
			// TODO: Optimize to not have to check every asteroid against every other asteroid.
			if (asteroid.detectCollision(asteroids)) {
				asteroidsTmp.addAll(asteroid.breakAsteroid(asteroid));
				asteroidsTmp.remove(asteroid);
			}
		}
		asteroids.removeAll(asteroids);
		asteroids.addAll(asteroidsTmp);
		spaceship.blasts.removeAll(blastsToRemove);
		
		background.update(deltaTime);
	}
	
	public void display() {
		GraphicsEnvironment.setWindow(-100, 100, -100, 100);
		
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		ModelMatrix.main.loadIdentityMatrix();
		
		background.display();
		
		spaceship.display();
		
		for (Asteroid asteroid : asteroids) {
			asteroid.display();
		}
	}

	@Override
	public void render () {
		update();
		display();
	}
}
