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
	
	private static int mode = 3; //1 = Classroom, 2 = Bus, 3 = Theater
	
	public static void main(String[] args)
	{
		CAEvac obj = new CAEvac();
		
		if(mode == 1)
			obj.createClassroom();
		else if(mode == 2)
			obj.createBus();
		else
			obj.createTheater();
		
		obj.setDoorDistances(0);
		obj.visualizeRoom();
		
		System.out.println("Evacuation Time: " + obj.simulate());
		//System.out.println("Evacuation Time: " + obj.bulkSim(100));
	}
	
	public double bulkSim(double trials)
	{
		double toRet = 0;
		for(int i = 0; i < trials; i++)
		{
			toRet += simulate();
			currentRoom.clear();
			
			if(mode == 1)
				createClassroom();
			else if(mode == 2)
				createBus();
			else
				createTheater();
			
			setDoorDistances(0);
			System.out.println(i);
		}
		return toRet / trials;
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
					Random r = new Random();
					int panic = r.nextInt(20);
					for(Tile t2 : currentRoom)
					{
						int xdis = Math.abs(t.getX() - t2.getX());
						int ydis = Math.abs(t.getY() - t2.getY());
						if(t2.getDistanceFromDoor() > t.getDistanceFromDoor() && t2.getOccupied() == true && xdis <= 1 && ydis <= 1 && panic != 0)
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
		roomWidth = 20;
		roomLength = 16;
		doorX = 0;
		doorY = 1;
		for(int i = 0; i < roomLength; i++)
		{
			for(int i2 = 0; i2 < roomWidth; i2++)
			{
				currentRoom.add(new Tile(true, false, i2, i));
			}
		}
		
		createWall(0, 0, 0, roomLength - 1);
		createWall(0, 0, roomWidth - 1, 0);
		createWall(roomWidth - 1, 0, roomWidth - 1, roomLength - 1);
		createWall(0, roomLength - 1, roomWidth - 1, roomLength - 1);
		
		for(Tile t : currentRoom)
			if(t.getX() == doorX && t.getY() == doorY)
			{
				t.setOccupiable(true);
				break;
			}
		
		createWall(4, 1, 4, 3);
		createWall(7, 1, 7, 3);
		createWall(10, 1, 10, 3);
		createWall(13, 1, 13, 3);
		createWall(16, 1, 16, 3);
		
		createWall(4, 6, 4, 9);
		createWall(7, 6, 7, 9);
		createWall(10, 6, 10, 9);
		createWall(13, 6, 13, 9);
		createWall(16, 6, 16, 9);
		
		createWall(4, 12, 4, 14);
		createWall(7, 12, 7, 14);
		createWall(10, 12, 10, 14);
		createWall(13, 12, 13, 14);
		createWall(16, 12, 16, 14);
		
		createPersonGroup(5, 1, 5, 3);
		createPersonGroup(8, 1, 8, 3);
		createPersonGroup(11, 1, 11, 3);
		createPersonGroup(14, 1, 14, 3);
		createPersonGroup(17, 1, 17, 3);
		
		createPersonGroup(5, 6, 5, 9);
		createPersonGroup(8, 6, 8, 9);
		createPersonGroup(11, 6, 11, 9);
		createPersonGroup(14, 6, 14, 9);
		createPersonGroup(17, 6, 17, 9);
		
		createPersonGroup(5, 12, 5, 14);
		createPersonGroup(8, 12, 8, 14);
		createPersonGroup(11, 12, 11, 14);
		createPersonGroup(14, 12, 14, 14);
		createPersonGroup(17, 12, 17, 14);
	}
	
	public void createBus()
	{
		roomWidth = 0;
		roomLength = 1;
		doorX = 24;
		doorY = 6;
		for(int i = 0; i < roomLength; i++)
		{
			for(int i2 = 0; i2 < roomWidth; i2++)
			{
				currentRoom.add(new Tile(true, false, i2, i));
			}
		}
		
		createWall(0, 0, 0, roomLength - 1);
		createWall(0, 0, roomWidth - 1, 0);
		createWall(roomWidth - 1, 0, roomWidth - 1, roomLength - 1);
		createWall(0, roomLength - 1, roomWidth - 1, roomLength - 1);
		
		for(Tile t : currentRoom)
			if(t.getX() == doorX && t.getY() == doorY)
			{
				t.setOccupiable(true);
				break;
			}
		
		createWall(1, 1, 1, 2);
		createWall(1, 4, 1, 5);
		createWall(3, 1, 3, 2);
		createWall(3, 4, 3, 5);
		createWall(5, 1, 5, 2);
		createWall(5, 4, 5, 5);
		createWall(7, 1, 7, 2);
		createWall(7, 4, 7, 5);
		createWall(9, 1, 9, 2);
		createWall(9, 4, 9, 5);
		createWall(11, 1, 11, 2);
		createWall(11, 4, 11, 5);
		createWall(13, 1, 13, 2);
		createWall(13, 4, 13, 5);
		createWall(15, 1, 15, 2);
		createWall(15, 4, 15, 5);
		createWall(17, 1, 17, 2);
		createWall(17, 4, 17, 5);
		createWall(19, 1, 19, 2);
		createWall(19, 4, 19, 5);
		createWall(21, 1, 21, 2);
		createWall(21, 4, 21, 5);
		createWall(23, 1, 23, 2);
		createWall(23, 4, 23, 5);
		
		createPersonGroup(2, 1, 2, 2);
		createPersonGroup(2, 4, 2, 5);
		createPersonGroup(4, 1, 4, 2);
		createPersonGroup(4, 4, 4, 5);
		createPersonGroup(6, 1, 6, 2);
		createPersonGroup(6, 4, 6, 5);
		createPersonGroup(8, 1, 8, 2);
		createPersonGroup(8, 4, 8, 5);
		createPersonGroup(10, 1, 10, 2);
		createPersonGroup(10, 4, 10, 5);
		createPersonGroup(12, 1, 12, 2);
		createPersonGroup(12, 4, 12, 5);
		createPersonGroup(14, 1, 14, 2);
		createPersonGroup(14, 4, 14, 5);
		createPersonGroup(16, 1, 16, 2);
		createPersonGroup(16, 4, 16, 5);
		createPersonGroup(18, 1, 18, 2);
		createPersonGroup(18, 4, 18, 5);
		createPersonGroup(20, 1, 20, 2);
		createPersonGroup(20, 4, 20, 5);
		createPersonGroup(22, 1, 22, 2);
		createPersonGroup(22, 4, 22, 5);
		createPersonGroup(24, 1, 24, 1);
	}
	
	public void createTheater()
	{
		roomWidth = 22;
		roomLength = 30;
		doorX = 0;
		doorY = 1;
		for(int i = 0; i < roomLength; i++)
		{
			for(int i2 = 0; i2 < roomWidth; i2++)
			{
				currentRoom.add(new Tile(true, false, i2, i));
			}
		}
		
		createWall(0, 0, 0, roomLength - 1);
		createWall(0, 0, roomWidth - 1, 0);
		createWall(roomWidth - 1, 0, roomWidth - 1, roomLength - 1);
		createWall(0, roomLength - 1, roomWidth - 1, roomLength - 1);
		
		for(Tile t : currentRoom)
			if(t.getX() == doorX && t.getY() == doorY)
			{
				t.setOccupiable(true);
				break;
			}
		
		createWall(1, 28, 4, 28);
		createWall(7, 28, 14, 28);
		createWall(17, 28, 20, 28);
		createWall(1, 26, 4, 26);
		createWall(7, 26, 14, 26);
		createWall(17, 26, 20, 26);
		createWall(1, 24, 4, 24);
		createWall(7, 24, 14, 24);
		createWall(17, 24, 20, 24);
		createWall(1, 22, 4, 22);
		createWall(7, 22, 14, 22);
		createWall(17, 22, 20, 22);
		createWall(1, 20, 4, 20);
		createWall(7, 20, 14, 20);
		createWall(17, 20, 20, 20);
		createWall(1, 18, 4, 18);
		createWall(7, 18, 14, 18);
		createWall(17, 18, 20, 18);
		createWall(1, 16, 4, 16);
		createWall(7, 16, 14, 16);
		createWall(17, 16, 20, 16);
		createWall(1, 14, 4, 14);
		createWall(7, 14, 14, 14);
		createWall(17, 14, 20, 14);
		createWall(1, 12, 4, 12);
		createWall(7, 12, 14, 12);
		createWall(17, 12, 20, 12);
		createWall(1, 10, 4, 10);
		createWall(7, 10, 14, 10);
		createWall(17, 10, 20, 10);
		createWall(1, 8, 4, 8);
		createWall(7, 8, 14, 8);
		createWall(17, 8, 20, 8);
		createWall(1, 6, 4, 6);
		createWall(7, 6, 14, 6);
		createWall(17, 6, 20, 6);
		
		createPersonGroup(1, 27, 4, 27);
		createPersonGroup(7, 27, 14, 27);
		createPersonGroup(17, 27, 20, 27);
		createPersonGroup(1, 25, 4, 25);
		createPersonGroup(7, 25, 14, 25);
		createPersonGroup(17, 25, 20, 25);
		createPersonGroup(1, 23, 4, 23);
		createPersonGroup(7, 23, 14, 23);
		createPersonGroup(17, 23, 20, 23);
		createPersonGroup(1, 21, 4, 21);
		createPersonGroup(7, 21, 14, 21);
		createPersonGroup(17, 21, 20, 21);
		createPersonGroup(1, 19, 4, 19);
		createPersonGroup(7, 19, 14, 19);
		createPersonGroup(17, 19, 20, 19);
		createPersonGroup(1, 17, 4, 17);
		createPersonGroup(7, 17, 14, 17);
		createPersonGroup(17, 17, 20, 17);
		createPersonGroup(1, 15, 4, 15);
		createPersonGroup(7, 15, 14, 15);
		createPersonGroup(17, 15, 20, 15);
		createPersonGroup(1, 13, 4, 13);
		createPersonGroup(7, 13, 14, 13);
		createPersonGroup(17, 13, 20, 13);
		createPersonGroup(1, 11, 4, 11);
		createPersonGroup(7, 11, 14, 11);
		createPersonGroup(17, 11, 20, 11);
		createPersonGroup(1, 9, 4, 9);
		createPersonGroup(7, 9, 14, 9);
		createPersonGroup(17, 9, 20, 9);
		createPersonGroup(1, 7, 4, 7);
		createPersonGroup(7, 7, 14, 7);
		createPersonGroup(17, 7, 20, 7);
		createPersonGroup(1, 5, 4, 5);
		createPersonGroup(7, 5, 14, 5);
		createPersonGroup(17, 5, 20, 5);
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
						t.setDistanceFromDoor(1000);
					}
				}
			}
		}
	}
	
	public void createPersonGroup(int x1, int y1, int x2, int y2)
	{
		for(int i = y1; i <= y2; i++)
		{
			for(int i2 = x1; i2 <= x2; i2++)
			{
				for(Tile t : currentRoom)
				{
					if(t.getY() == i && t.getX() == i2)
					{
						t.setOccupied(true);
					}
				}
			}
		}
	}
	
	public void ditributeRandom(int num)
	{
		Collections.shuffle(currentRoom);
		int i = 0;
		int i2 = 0;
		while(i2 < num)
		{
			if(currentRoom.get(i).getOccupiable())
			{
				currentRoom.get(i).setOccupied(true);
				i2++;
			}
			i++;
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
		for(int i = 0; i < roomLength; i++)
		{
			for(int i2 = 0; i2 < roomWidth; i2++)
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
		for(int i = 0; i < roomLength; i++)
		{
			for(int i2 = 0; i2 < roomWidth; i2++)
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
