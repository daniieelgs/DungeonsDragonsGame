package dd.Items.Tools.Shields;

import java.awt.Point;

import dd.Items.IItem;

public class IronShield extends Shield{

	private final static int DURATION = 4, PLUS_LIFE = 6, COST = 20;
	
	public IronShield(Point position) {
		super(DURATION, DURATION, COST, PLUS_LIFE, position);
	}
	
	public IronShield() {
		this(new Point());
	}

	@Override
	public IItem cloneItem() {
		return new IronShield((Point) position.clone());
	}
	
	@Override
	public String toString() {
		return "Iron " + super.toString();
	}

}
