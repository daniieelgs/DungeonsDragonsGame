package dd.Items.Tools.Pickaxes;

import java.awt.Point;

import dd.Items.IItem;

public class StonePickaxe extends Pickaxe{

	private final static int DURATION = 3, COST = 10;
	//DURATION = 3
	public StonePickaxe(Point position) {
		super(DURATION, DURATION, COST, position);
	}
	
	public StonePickaxe() {
		this(new Point());
	}
	
	@Override
	public IItem cloneItem() {
		return new StonePickaxe((Point) position.clone());
	}
	
	@Override
	public String toString() {
		return "Stone " + super.toString();
	}

}
