package dd.Items.Tools.Pickaxes;

import java.awt.Point;

import dd.Items.IItem;

public class GoldenPickaxe extends Pickaxe{

	private final static int DURATION = 9, COST = 30;

	public GoldenPickaxe(Point position) {
		super(DURATION, DURATION, COST, position);
	}
	
	public GoldenPickaxe() {
		this(new Point());
	}
	
	@Override
	public IItem cloneItem() {
		return new GoldenPickaxe((Point) position.clone());
	}
	
	@Override
	public String toString() {
		return "Golden" + super.toString();
	}

	
}
