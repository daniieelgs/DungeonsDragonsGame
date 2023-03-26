package dd.Items;

import java.awt.Point;

public class Coin implements IItem{

	private int value;
	
	private final static char TEXTURE = '◌', TEXTURE_SPECIAL = '●';
	
	private Point position;
	
	public Coin(int value, Point position) {
		if(value < 0) value = 0;
		this.value = value;
		this.position = position;
	}

	public Coin(int value) {
		this(value, null);
	}
	
	@Override
	public int getValue() {
		return value;
	}

	@Override
	public char texture() {
				
		if(getValue() <= 50) return TEXTURE;
		
		return TEXTURE_SPECIAL;
	}

	@Override
	public Point getPosition() {
		return position;
	}
	
	@Override
	public String toString() {
		return "Coin (" + getValue() + "$)";
	}

	@Override
	public void setPosition(Point p) {
		position = p;
	}

	@Override
	public IItem cloneItem() {
		return new Coin(value, (Point) position.clone());
	}
	
}
