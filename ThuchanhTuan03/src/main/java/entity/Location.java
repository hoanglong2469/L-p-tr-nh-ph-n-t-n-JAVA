package entity;

public class Location {
	private double y;
	private double x;
	
	
	public Location() {
	}


	public Location(double y, double x) {
		super();
		this.y = y;
		this.x = x;
	}


	public double getY() {
		return y;
	}


	public void setY(double y) {
		this.y = y;
	}


	public double getX() {
		return x;
	}


	public void setX(double x) {
		this.x = x;
	}


	@Override
	public String toString() {
		return "Location [y=" + y + ", x=" + x + "]";
	}
	
	
}
