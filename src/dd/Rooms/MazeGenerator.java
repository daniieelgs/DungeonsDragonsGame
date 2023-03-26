package dd.Rooms;

import java.awt.Point;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;

public class MazeGenerator {

	private static Map map;
	private static char wall, road;
	private static int initX, initY;

	public static Map build(Map _map, int _initX, int _initY, char _wall, char _road){
		
		map = _map;
		
		initX = _initX;
		initY = _initY;
		
		road = _road;
		wall = _wall;
		
		_map = null;
		
		ArrayDeque<Point> stack=new ArrayDeque<Point>();
		
		int y=initY;
		int x=initX;
		
		//y=5;
		//x=5;
		Point id = new Point(x, y);
		map.setElement(road, id);
		
		stack.add(id);
		
		generateRandomMaze(stack, x, y);
				
		return map;
	}
	
	

	
	private static void generateRandomMaze(ArrayDeque<Point> _stack, int _x, int _y) {
		
		boolean moviment=false;
		int contador=0;
		
		int x=_x;
		int y=_y;
		
		ArrayDeque<Point> stack=_stack;
		
		ArrayList<Integer> moviments=new ArrayList<Integer>();
		
		while(!moviment&&contador<4) {
			
			int mov=(int)(Math.random()*4);
			
			boolean registrat=false;
			
			Iterator<Integer> it=moviments.iterator();
			
			while(!registrat&&it.hasNext()) {
				
				int i=it.next();
				
				if(mov==i) registrat=true;
				
			}
			
			
			if(!registrat) {
				moviments.add(mov);
				contador++;
				
				if(mov==0&&isWall(x, y+1)&&isWall(x, y+2)&&isWall(x+1, y+1)&&isWall(x-1, y+1)) { //S
					y++;
					moviment=true;
				}
				else if(mov==1&&isWall(x+1, y)&&isWall(x+2, y)&&isWall(x+1, y+1)&&isWall(x+1, y-1)) { //D
					x++; 
					moviment=true;
				}
				else if(mov==2&&isWall(x, y-1)&&isWall(x, y-2)&&isWall(x+1, y-1)&&isWall(x-1, y-1)) { //W
					y--;
					moviment=true;
				}
				else if(mov==3&&isWall(x-1, y)&&isWall(x-2, y)&&isWall(x-1, y+1)&&isWall(x-1, y-1)) { //A
					x--;
					moviment=true;
				}
				
			}
			
			
		}
		
		Point id;
		
		if(moviment) {
			map.setElement(road, x, y);
			id = new Point(x, y);
			stack.add(id);
		}else if(stack.size()>1){
			
			stack.removeLast();
			id=stack.getLast();
						
			y=id.y;
			x=id.x;
						
		}

		if(stack.size()>1) {
			generateRandomMaze(stack, x, y);
		}
		
		
	}
	
	private static boolean isWall(int x, int y) {

		if(x<0||y<0||x>=Map.width||y>=Map.height) return false;
		
		char w=map.getElement(x, y);
		
		if(w==wall) return true;
		else return false;
		
	}

	
}
