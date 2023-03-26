package dd.Items.Tools.Pickaxes;

import java.awt.Point;

import dd.Items.IItem;

public class DiamondPickaxe extends Pickaxe{

	private final static int DURATION = 12 , COST = 40;

	public DiamondPickaxe(Point position) {
		super(DURATION, DURATION, COST, position);
	}
	
	public DiamondPickaxe() {
		this(new Point());
	}
	
	@Override
	public IItem cloneItem() {
		return new DiamondPickaxe((Point) position.clone());
	}
	
	@Override
	public String toString() {
		return "Diamond " + super.toString();
	}

	
}
