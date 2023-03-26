package dd.Items.Tools.Shields;

import java.awt.Point;

import dd.Items.Tools.Tool;

public abstract class Shield extends Tool{
	
	protected final static char TEXTURE = '⛻';
	
	private int plusLife;
	
	public Shield(int duration, int maxDuration, int value, int plusLife, Point position) {
		super(duration, maxDuration, value, position);
		this.plusLife = plusLife;
	}

	@Override
	public int plusLife() {
		return plusLife;
	}
	
	@Override
	public int plusPower() {
		return 0;
	}
	
	@Override
	public char texture() {
		return TEXTURE;
	}
	
	@Override
	public String toString() {
		return "Shield: (Life: " + plusLife + " - Duration: " + duration + ")" ;
	}

}
