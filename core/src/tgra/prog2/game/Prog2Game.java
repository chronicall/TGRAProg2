package tgra.prog2.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;

public class Prog2Game extends ApplicationAdapter {
	private int speedMultiplier;

	private int renderingProgramID;
	private int vertexShaderID;
	private int fragmentShaderID;
	
	private Point2D position;
	private Vector2D motion;

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
		
		this.position = new Point2D(0.0f, 0.0f);
		this.motion = new Vector2D(3.5f, 2.0f);

		BoxGraphic.create();
		CircleGraphic.create();
		
		ModelMatrix.main = new ModelMatrix();
	}
	
	public void update () {
		float deltaTime = Gdx.graphics.getDeltaTime();
		
		// Speed multiplier just for fun
		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
			this.speedMultiplier = 5;
		} else {
			this.speedMultiplier = 1;
		}
		
		// If bouncing box reaches an edge of the viewport, change the direction
		// on that axis
		if (this.position.x + 1.0f >= 10 || this.position.x - 1.0f <= -10) {
			this.motion.x *= -1;
		}
		if (this.position.y + 1.0f >= 10 || this.position.y - 1.0f <= -10) {
			this.motion.y *= -1;
		}
		this.position.x += this.motion.x * this.speedMultiplier * deltaTime;
		this.position.y += this.motion.y * this.speedMultiplier * deltaTime;
	}
	
	public void display() {
		GraphicsEnvironment.setWindow(-10, 10, -10, 10);
		
		Gdx.gl.glClearColor(0.0f, 0.3f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		ModelMatrix.main.loadIdentityMatrix();
		
		ModelMatrix.main.addTranslation(position.x, position.y, 0.0f);
		ModelMatrix.main.addScale(0.8f, 1.0f, 1.0f);
		GraphicsEnvironment.setShaderModelMatrix(ModelMatrix.main);
		
		GraphicsEnvironment.setColour(1.0f, 0.0f, 0.0f);
		CircleGraphic.drawSolidCircle();
		GraphicsEnvironment.setColour(0.4f, 0.0f, 0.0f);
		BoxGraphic.draw();
		GraphicsEnvironment.setColour(0.8f, 0.8f, 0.0f);
		CircleGraphic.drawOutlinedCircle();
	}

	@Override
	public void render () {
		update();
		display();
	}
}
