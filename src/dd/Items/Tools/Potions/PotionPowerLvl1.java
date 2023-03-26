package dd.Items.Tools.Potions;

import java.awt.Point;

import dd.Items.IItem;

public class PotionPowerLvl1 extends Potion{

	private static int LIFE = 0, POWER = 3, COST = 20;
	
	public PotionPowerLvl1(Point position) {
		super(COST, LIFE, POWER, position);
	}
	
	public PotionPowerLvl1() {
		this(new Point());
	}

	@Override
	public IItem cloneItem() {
		return new PotionPowerLvl1((Point) position.clone());
	}

	@Override
	public String toString() {
		return "Power " + super.toString();
	}
}
