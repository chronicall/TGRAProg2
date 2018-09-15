package tgra.prog2.game;

public class Vector2D {
	public float x;
	public float y;
	
	public Vector2D() {
		this.x = 0.0f;
		this.y = 0.0f;
	}
	
	public Vector2D(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2D add(Vector3D v) {
		return new Vector2D(this.x + v.x, this.y + v.y);
	}
	
	public Vector2D scale(int S) {
		return new Vector2D(this.x * S, this.y * S);
	}
	
	public float length() {
		return (float)(Math.sqrt(this.x*this.x+this.y*this.y));
	}
	
	public float dot(Vector2D v) {
		return (this.x * v.x + this.y * v.y);
	}
	
	public Vector2D normalize() {
		float len = this.length();
		
		return new Vector2D(this.x / len, this.y / len);
	}
}
