package dd.Rooms;

import java.awt.Point;

import dd.Items.IDoor;
import dd.Items.IItem;
import dd.Items.IDoor.DOOR_CARDINALS;
import dd.Mobs.Monsters.IMonster;

public interface IRoom {
	
	public int getLevel();
	public int getNumberMonster();
	public int getNumberTreasure();
		
	public Map generateRoom();
	public Point[] generateMonsters();
	public Point[] generateItems();

	public IRoom getRoom(DOOR_CARDINALS door);
	public void setRoom(DOOR_CARDINALS door, IRoom room);
	
	public Map getMap();
	
	public boolean isWall(Point p);
	public boolean isWall(int x, int y);
	
	public boolean isRoad(Point p);
	public boolean isRoad(int x, int y);
	
	public IItem takeItem(Point p);
	public IItem takeItem(int x, int y);
	
	public IMonster isMonster(Point p);
	public IMonster isMonster(int x, int y);
	
	public IDoor isDoor(Point p);
	public IDoor isDoor(int x, int y);
	
	public Point spawnPlayerToDoor(DOOR_CARDINALS door);
	
	public void update();
	
}
