package dd.Items.Tools.Pickaxes;

import java.awt.Point;

import dd.Items.Tools.Tool;

public abstract class Pickaxe extends Tool{
	protected final static char TEXTURE = '^';
	
	public Pickaxe(int duration, int maxDuration, int value, Point position) {
		super(duration, maxDuration, value, position);
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
	public int plusPower() {
		return 0;
	}
	
	@Override
	public String toString() {
		return "Pickaxe: (Duration: " + getDuration() + ")";
	}
}
