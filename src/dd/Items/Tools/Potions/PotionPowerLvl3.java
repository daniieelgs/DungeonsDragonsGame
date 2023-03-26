package dd.Items.Tools.Potions;

import java.awt.Point;

import dd.Items.IItem;

public class PotionPowerLvl3 extends Potion{

	private static int LIFE = 0, POWER = 25, COST = 60;
	
	public PotionPowerLvl3(Point position) {
		super(COST, LIFE, POWER, position);
	}
	
	public PotionPowerLvl3() {
		this(new Point());
	}

	@Override
	public IItem cloneItem() {
		return new PotionPowerLvl3((Point) position.clone());
	}

	@Override
	public String toString() {
		return "Power " + super.toString();
	}
}
