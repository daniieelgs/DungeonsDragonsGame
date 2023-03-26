package dd.Items.Tools.Swords;

import java.awt.Point;

import dd.Items.Tools.Tool;

public abstract class Sword extends Tool{
	
	protected final static char TEXTURE = '/';
	
	protected int plusPower;
	
	public Sword(int duration, int maxDuration, int value, int plusPower, Point position) {
		super(duration, maxDuration, value, position);
		this.plusPower = plusPower;
	}

	@Override
	public int plusPower() {
		return plusPower;
	}
	
	@Override
	public int plusLife() {
		return 0;
	}
	
	@Override
	public char texture() {
		return TEXTURE;
	}

	@Override
	public String toString() {
		return "Sword: (Power: " + plusPower() + " - Duration: " + getDuration() + ")";
	}
}
