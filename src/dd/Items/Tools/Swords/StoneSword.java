package dd.Items.Tools.Swords;

import java.awt.Point;

import dd.Items.IItem;

public class StoneSword extends Sword{

	private final static int DURATION = 2, PLUS_POWER = 5, COST = 10;
	//DURATION 3
	public StoneSword(Point position) {
		super(DURATION, DURATION, COST, PLUS_POWER, position);
	}
	
	public StoneSword() {
		this(new Point());
	}

	@Override
	public int getValue() {
		return COST;
	}
	
	@Override
	public IItem cloneItem() {
		return new StoneSword((Point) position.clone());
	}
	
	@Override
	public String toString() {
		return "Stone " + super.toString();
	}

}
