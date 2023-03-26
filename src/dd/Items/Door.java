package dd.Items;

import java.awt.Point;

public class Door implements IDoor{
	
	private int cost, powerCost;
	private boolean open;
	private Point position;
	private DOOR_CARDINALS cardinal;
	
	private final static char CLOSE = '░', OPEN = '▒';
	
	public Door(int cost, int powerCost, boolean open, Point position, DOOR_CARDINALS cardinal) {
		this.cost = cost;
		this.powerCost = powerCost;
		this.open = open;
		this.position = position;
		this.cardinal = cardinal;
	}
	
	public Door(int cost, int powerCost, Point position, DOOR_CARDINALS cardinal) {
		this(cost, powerCost, false, position, cardinal);
	}
	
	public Door(Point position, DOOR_CARDINALS cardinal) {
		this(0, 0, true, position, cardinal);
	}

	@Override
	public int getValue() {
		return cost;
	}
	
	@Override
	public int getPower() {
		return powerCost;
	}

	@Override
	public char texture() {
		return isOpen() ? OPEN : CLOSE;
	}

	@Override
	public Point getPosition() {
		return position;
	}
	
	public boolean isOpen() {
		return open;
	}
	
	public void open() {
		open = true;
	}
	
	public void close() {
		open = false;
	}

	@Override
	public DOOR_CARDINALS getCardinal() {
		return cardinal;
	}

	@Override
	public void setPosition(Point p) {
		position = p;		
	}

	@Override
	public IItem cloneItem() {
		return new Door(cost, powerCost, open, (Point) position.clone(), cardinal);
	}
	
}
