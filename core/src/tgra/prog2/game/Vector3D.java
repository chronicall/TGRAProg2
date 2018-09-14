package tgra.prog2.game;

public class Vector3D {
	public float x;
	public float y;
	public float z;
	
	public Vector3D() {
		this.x = 0.0f;
		this.y = 0.0f;
		this.z = 0.0f;
	}
	
	public Vector3D(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3D add(Vector3D v) {
		return new Vector3D(this.x + v.x, this.y + v.y, this.z + v.z);
	}
	
	public Vector3D scale(int S) {
		return new Vector3D(this.x * S, this.y * S, this.z * S);
	}
	
	public float length() {
		return (float)(Math.sqrt(x*x+y*y+z*z));
	}
	
	public float dot(Vector3D v) {
		return (this.x * v.x + this.y * v.y + this.z * v.z);
	}
	
	public Vector3D normalize() {
		float len = this.length();
		
		return new Vector3D(this.x / len, this.y / len, this.z / len);
	}
}
