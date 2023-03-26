package dd.Rooms;

import java.awt.Dimension;
import java.awt.Point;

public class Map {

	private char[][] map;
	
	public final static char WALL = '█', ROAD = ' ', DOOR = '░';
	
	public final static Dimension dim = new Dimension(30, 15);
	
	public final static int height = dim.height;
	public final static int width = dim.width;
	
	public Map() {
		
		map = new char[dim.height][dim.width];
		
	}
	
	public char[][] map() {
		return map;
	}
	
	public char getElement(Point p) {
		return getElement(p.x, p.y);
	}
	
	public char getElement(int x, int y) {
		return map[y][x];
	}
	
	public void setElement(char c, Point p) {
		setElement(c, p.x, p.y);
	}
	
	public void removeElement(Point p) {
		setElement(ROAD, p);
	}
	
	public void setElement(char c, int x, int y) {
		map[y][x] = c;
	}
	
	public double getWidth() {
		return dim.getWidth();
	}
	
	public double getHeight() {
		return dim.getHeight();
	}
}
