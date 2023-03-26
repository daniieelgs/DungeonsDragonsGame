package dd.Items.Tools.Potions;

import java.awt.Point;

import dd.Items.IItem;

public class PotionPowerLifeLvl3 extends Potion{

	private static int LIFE = 5, POWER = 15, COST = 45;
	
	public PotionPowerLifeLvl3(Point position) {
		super(COST, LIFE, POWER, position);
	}
	
	public PotionPowerLifeLvl3() {
		this(new Point());
	}

	@Override
	public IItem cloneItem() {
		return new PotionPowerLifeLvl3((Point) position.clone());
	}

	@Override
	public String toString() {
		return "Power&Life " + super.toString();
	}
}
