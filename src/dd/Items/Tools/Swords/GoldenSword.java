package dd.Items.Tools.Swords;

import java.awt.Point;

import dd.Items.IItem;

public class GoldenSword extends Sword{

	private final static int DURATION = 3, PLUS_POWER = 20, COST = 30;
	//DURATION 3
	public GoldenSword(Point position) {
		super(DURATION, DURATION, COST, PLUS_POWER, position);
	}
	
	public GoldenSword() {
		this(new Point());
	}
	
	@Override
	public IItem cloneItem() {
		return new GoldenSword((Point) position.clone());
	}
	
	@Override
	public String toString() {
		return "Golden " + super.toString();
	}

}
