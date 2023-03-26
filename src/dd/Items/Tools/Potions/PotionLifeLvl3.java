package dd.Items.Tools.Potions;

import java.awt.Point;

import dd.Items.IItem;

public class PotionLifeLvl3 extends Potion{

	private static int LIFE = 10, POWER = 0, COST = 50;
	
	public PotionLifeLvl3(Point position) {
		super(COST, LIFE, POWER, position);
	}
	
	public PotionLifeLvl3() {
		this(new Point());
	}

	@Override
	public IItem cloneItem() {
		return new PotionLifeLvl3((Point) position.clone());
	}

	@Override
	public String toString() {
		return "Life " + super.toString();
	}
}
