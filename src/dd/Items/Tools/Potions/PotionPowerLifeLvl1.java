package dd.Items.Tools.Potions;

import java.awt.Point;

import dd.Items.IItem;

public class PotionPowerLifeLvl1 extends Potion{

	private static int LIFE = 2, POWER = 3, COST = 15;
	
	public PotionPowerLifeLvl1(Point position) {
		super(COST, LIFE, POWER, position);
	}
	
	public PotionPowerLifeLvl1() {
		this(new Point());
	}

	@Override
	public IItem cloneItem() {
		return new PotionPowerLifeLvl1((Point) position.clone());
	}

	@Override
	public String toString() {
		return "Power&Life " + super.toString();
	}
}
