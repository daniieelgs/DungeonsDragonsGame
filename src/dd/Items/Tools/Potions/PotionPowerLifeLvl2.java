package dd.Items.Tools.Potions;

import java.awt.Point;

import dd.Items.IItem;

public class PotionPowerLifeLvl2 extends Potion{

	private static int LIFE = 3, POWER = 7, COST = 30;
	
	public PotionPowerLifeLvl2(Point position) {
		super(COST, LIFE, POWER, position);
	}
	
	public PotionPowerLifeLvl2() {
		this(new Point());
	}

	@Override
	public IItem cloneItem() {
		return new PotionPowerLifeLvl2((Point) position.clone());
	}

	@Override
	public String toString() {
		return "Power&Life " + super.toString();
	}
}
