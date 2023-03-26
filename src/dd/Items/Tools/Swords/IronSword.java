package dd.Items.Tools.Swords;

import java.awt.Point;

import dd.Items.IItem;

public class IronSword extends Sword{

	private final static int DURATION = 4, PLUS_POWER = 10, COST = 20;
	//DURATION 3
	public IronSword(Point position) {
		super(DURATION, DURATION, COST, PLUS_POWER, position);
	}
	
	public IronSword() {
		this(new Point());
	}
	
	@Override
	public IItem cloneItem() {
		return new IronSword((Point) position.clone());
	}
	
	@Override
	public String toString() {
		return "Iron " + super.toString();
	}

}
