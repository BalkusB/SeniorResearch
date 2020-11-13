
public class Tile 
{
	private boolean occupiable;
	private boolean occupied;
	private double distanceFromDoor = -1;
	private int x;
	private int y;
	
	public Tile(boolean isOccupiable, boolean isOccupied, int xco, int yco)
	{
		
		occupiable = isOccupiable;
		occupied = isOccupied;
		x = xco;
		y = yco;
	}
	
	public boolean getOccupiable() {return occupiable;}
	public boolean getOccupied() {return occupied;}
	public double getDistanceFromDoor() {return distanceFromDoor;}
	public int getX() {return x;}
	public int getY() {return y;}
	
	public void setOccupiable(boolean b) {occupiable = b;}
	public void setOccupied(boolean b) {occupied = b;}
	public void setDistanceFromDoor(double d) {distanceFromDoor = d;}
}
