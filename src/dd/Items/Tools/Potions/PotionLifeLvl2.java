package dd.Items.Tools.Potions;

import java.awt.Point;

import dd.Items.IItem;

public class PotionLifeLvl2 extends Potion{

	private static int LIFE = 5, POWER = 0, COST = 25;
	
	public PotionLifeLvl2(Point position) {
		super(COST, LIFE, POWER, position);
	}
	
	public PotionLifeLvl2() {
		this(new Point());
	}

	@Override
	public IItem cloneItem() {
		return new PotionLifeLvl2((Point) position.clone());
	}

	@Override
	public String toString() {
		return "Life " + super.toString();
	}
}
