package dd.Items.Tools.Potions;

import java.awt.Point;

import dd.Items.IItem;

public class PotionLifeLvl1 extends Potion{

	private static int LIFE = 3, POWER = 0, COST = 15;
	
	public PotionLifeLvl1(Point position) {
		super(COST, LIFE, POWER, position);
	}
	
	public PotionLifeLvl1() {
		this(new Point());
	}

	@Override
	public IItem cloneItem() {
		return new PotionLifeLvl1((Point) position.clone());
	}

	@Override
	public String toString() {
		return "Life " + super.toString();
	}
}
