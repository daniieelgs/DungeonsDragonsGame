package dd.Items.Tools.Shields;

import java.awt.Point;

import dd.Items.IItem;

public class WoodenShield extends Shield{

	private final static int DURATION = 2, PLUS_LIFE = 3, COST = 10;
	
	public WoodenShield(Point position) {
		super(DURATION, DURATION, COST, PLUS_LIFE, position);
	}
	
	public WoodenShield() {
		this(new Point());
	}

	@Override
	public IItem cloneItem() {
		return new WoodenShield((Point) position.clone());
	}
	
	@Override
	public String toString() {
		return "Wooden " + super.toString();
	}

}
