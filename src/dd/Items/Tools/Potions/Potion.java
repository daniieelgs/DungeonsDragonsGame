package dd.Items.Tools.Potions;

import java.awt.Point;
import java.util.Objects;

import dd.Items.Tools.Tool;

public abstract class Potion extends Tool{

	protected final static char TEXTURE = '♤';
	private final static int DURATION = 1;
	
	protected int plusPower, plusLife;
		
	private double id = 0;
	
	public Potion(int value, int plusLife, int plusPower, Point position) {
		super(DURATION, DURATION, value, position);
		
		this.plusLife = plusLife;
		this.plusPower = plusPower;
		
		id = Math.random();
	}

	@Override
	public int plusPower() {
		return plusPower;
	}

	@Override
	public int plusLife() {
		return plusLife;
	}

	@Override
	public char texture() {
		return TEXTURE;
	}

	@Override
	public String toString() {
		return "Potion: (" + (plusPower() > 0 ? "Power: " + plusPower() + (plusLife() > 0 ? " - " : ""): "") + (plusLife() > 0 ? "Life: " + plusLife() : "") + ")";
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(duration, maxDuration, position, id);
	}
	
	public double id() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		
		if (this == obj) return true;
		
		if (!(obj instanceof Potion)) return false;

		Potion other = (Potion) obj;
		
		return id == other.id() && duration == other.getDuration() && maxDuration == other.maxDuration() && Objects.equals(position, other.getPosition());
	}
}
