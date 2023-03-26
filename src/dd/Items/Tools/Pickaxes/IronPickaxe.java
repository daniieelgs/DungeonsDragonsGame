package dd.Items.Tools.Pickaxes;

import java.awt.Point;

import dd.Items.IItem;

public class IronPickaxe extends Pickaxe{

	private final static int DURATION = 6, COST = 20;

	public IronPickaxe(Point position) {
		super(DURATION, DURATION, COST, position);
	}
	
	public IronPickaxe() {
		this(new Point());
	}
	
	@Override
	public IItem cloneItem() {
		return new IronPickaxe((Point) position.clone());
	}
	
	@Override
	public String toString() {
		return "Iron " + super.toString();
	}

	
}
