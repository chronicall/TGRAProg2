package tgra.prog2.game;

import java.nio.FloatBuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.BufferUtils;

public class BoxGraphic {
	private static FloatBuffer vertexBuffer;
	
	public static void create() {
		float[] array = {-0.5f, -0.5f,
						-0.5f, 0.5f,
						0.5f, 0.5f,
						0.5f, -0.5f};

		vertexBuffer = BufferUtils.newFloatBuffer(8);
		vertexBuffer.put(array);
		vertexBuffer.rewind();
	}
	
	public static void draw() {
		Gdx.gl.glVertexAttribPointer(GraphicsEnvironment.positionLoc, 2, GL20.GL_FLOAT, false, 0, vertexBuffer);
		Gdx.gl.glDrawArrays(GL20.GL_TRIANGLE_STRIP, 0, 4);
	}
}
