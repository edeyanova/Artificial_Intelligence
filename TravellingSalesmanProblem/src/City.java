
public class City {
	
	public int x;
	public int y;
	
	public City() {
		// TODO Auto-generated constructor stub
		this.x = (int) (Math.random() * 200);
		this.y = (int) (Math.random() * 200);
	}

	public City(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return this.x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return this.y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public int distanceTo(City city) {
		int xDistance = Math.abs(getX() - city.getX());
		int yDistance = Math.abs(getY() - city.getY());

		int distance = (int) Math.sqrt((xDistance * xDistance) + (yDistance * yDistance));
		return distance;
	}

	@Override
	public String toString() {
		return "City [x=" + x + ", y=" + y + "]";
	}
	
	
}
