package dd.Items.Tools.Swords;

import java.awt.Point;

import dd.Items.IItem;

public class DiamondSword extends Sword{

	private final static int DURATION = 6, PLUS_POWER = 30, COST = 45;
	//DURATION 3
	public DiamondSword(Point position) {
		super(DURATION, DURATION, COST, PLUS_POWER, position);
	}
	
	public DiamondSword() {
		this(new Point());
	}
	
	@Override
	public IItem cloneItem() {
		return new DiamondSword((Point) position.clone());
	}
	
	@Override
	public String toString() {
		return "Diamond " + super.toString();
	}

}
