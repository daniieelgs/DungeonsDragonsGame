package dd.Items.Tools.Potions;

import java.awt.Point;

import dd.Items.IItem;

public class PotionPowerLvl2 extends Potion{

	private static int LIFE = 0, POWER = 15, COST = 40;
	
	public PotionPowerLvl2(Point position) {
		super(COST, LIFE, POWER, position);
	}
	
	public PotionPowerLvl2() {
		this(new Point());
	}

	@Override
	public IItem cloneItem() {
		return new PotionPowerLvl2((Point) position.clone());
	}

	@Override
	public String toString() {
		return "Power " + super.toString();
	}
}
