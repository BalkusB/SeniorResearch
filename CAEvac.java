import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class CAEvac 
{
	private ArrayList<Tile> currentRoom = new ArrayList<Tile>();
	private int roomWidth;
	private int roomLength;
	private int doorX;
	private int doorY;
	private double maxDistance = 0;
	
	public static void main(String[] args)
	{
		CAEvac obj = new CAEvac();
		obj.createClassroom();
		obj.setDoorDistances(0);
		obj.visualizeRoom();
		System.out.println("Evacuation Time: " + obj.simulate());
	}
	
	public int simulate()
	{
		int time = 0;
		while(!allEvacuated())
		{
			visualizeRoom2();
			Collections.shuffle(currentRoom);
			move(1);
			time++;
		}
		return time;
	}
	
	public void move(double i)
	{
		if(i > maxDistance)
			return;
		
		if(i == 1)
		{
			for(Tile t : currentRoom)
				if(t.getDistanceFromDoor() == 1 && t.getOccupied() == true)
					t.setOccupied(false);
		}
		
		for(Tile t : currentRoom)
		{
			if(t.getDistanceFromDoor() == i)
			{
				if(!t.getOccupied() && t.getOccupiable() == true)
				{
					//Random r = new Random();
					//int panic = r.nextInt(20);
					for(Tile t2 : currentRoom)
					{
						int xdis = Math.abs(t.getX() - t2.getX());
						int ydis = Math.abs(t.getY() - t2.getY());
						if(t2.getDistanceFromDoor() > t.getDistanceFromDoor() && t2.getOccupied() == true && xdis <= 1 && ydis <= 1)
						{
							t.setOccupied(true);
							t2.setOccupied(false);
							break;
						}
					}
				}
			}
		}
		move(i + .5);
	}
	
	public boolean allEvacuated()
	{
		for(Tile t : currentRoom)
		{
			if(t.getOccupied() == true)
				return false;
		}
		return true;
	}
	
	public void createClassroom()
	{
		roomWidth = 7;
		roomLength = 7;
		doorX = 3;
		doorY = 0;
		for(int i = 0; i < roomWidth; i++)
		{
			for(int i2 = 0; i2 < roomLength; i2++)
			{
				currentRoom.add(new Tile(true, false, i, i2));
			}
		}
		
		createWall(0, 0, 0, roomLength - 1);
		createWall(0, 0, roomWidth - 1, 0);
		createWall(roomWidth - 1, 0, roomWidth - 1, roomLength - 1);
		createWall(0, roomLength - 1, roomWidth - 1, roomLength - 1);
		
		for(Tile t : currentRoom)
			if(t.getX() == doorX && t.getY() == doorY)
				t.setOccupiable(true);
		
		createObstacle(2, 2);
		createObstacle(2, 4);
		createObstacle(4, 2);
		createObstacle(4, 4);
		
		createPerson(1, 5);
		createPerson(2, 5);
		createPerson(3, 5);
		createPerson(4, 5);
		createPerson(5, 5);
	}
	
	public void createObstacle(int x, int y)
	{
		for(Tile t : currentRoom)
		{
			if(t.getX() == x && t.getY() == y)
			{
				t.setOccupiable(false);
				t.setDistanceFromDoor(1000);
			}
		}
	}
	
	public void createWall(int x1, int y1, int x2, int y2)
	{
		for(int i = y1; i <= y2; i++)
		{
			for(int i2 = x1; i2 <= x2; i2++)
			{
				for(Tile t : currentRoom)
				{
					if(t.getY() == i && t.getX() == i2)
					{
						t.setOccupiable(false);
					}
				}
			}
		}
	}
	
	public void createPerson(int x, int y)
	{
		for(Tile t : currentRoom)
		{
			if(t.getX() == x && t.getY() == y)
			{
				t.setOccupied(true);
			}
		}
	}
	
	public void visualizeRoom()
	{
		for(int i = 0; i < roomWidth; i++)
		{
			for(int i2 = 0; i2 < roomLength; i2++)
			{
				for(Tile t : currentRoom)
				{
					if(t.getY() == i && t.getX() == i2)
					{
						if(t.getOccupiable() == true)
							System.out.print(t.getDistanceFromDoor() + " ");
						else
							System.out.print("XXX ");
					}
				}
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public void visualizeRoom2()
	{
		for(int i = 0; i < roomWidth; i++)
		{
			for(int i2 = 0; i2 < roomLength; i2++)
			{
				for(Tile t : currentRoom)
				{
					if(t.getY() == i && t.getX() == i2)
					{
						if(t.getOccupiable() == true)
						{
							if(t.getOccupied() == true)
								System.out.print("1 ");
							else
								System.out.print("0 ");
						}
							
						else
							System.out.print("X ");
					}
				}
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public void setDoorDistances(double i)
	{
		if(allDoorDistancesSet())
			return;
		if(i == 0)
		{
			for(Tile t : currentRoom)
			{
				if(t.getX() == doorX && t.getY() == doorY)
				{
					t.setDistanceFromDoor(1);
				}
			}
			i++;
		}
		for(Tile t : currentRoom)
		{
			if(t.getDistanceFromDoor() == i)
			{
				for(Tile t2: currentRoom)
				{
					int xdis = Math.abs(t.getX() - t2.getX());
					int ydis = Math.abs(t.getY() - t2.getY());
					if(xdis <= 1 && ydis <= 1 && t2.getDistanceFromDoor() == -1)
					{
						if(xdis + ydis == 2)
							t2.setDistanceFromDoor(i + 1.5);
						if(xdis + ydis == 1)
							t2.setDistanceFromDoor(i + 1);
					}
				}
			}
		}
		maxDistance = i + .5;
		setDoorDistances(i + .5);
	}
	
	public boolean allDoorDistancesSet()
	{
		for(Tile t : currentRoom)
		{
			if(t.getDistanceFromDoor() == -1)
				return false;
		}
		return true;
	}
}
