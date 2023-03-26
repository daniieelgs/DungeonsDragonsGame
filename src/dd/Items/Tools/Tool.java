package dd.Items.Tools;

import java.awt.Point;
import java.util.Objects;

public abstract class Tool implements ITool{

	protected int duration, maxDuration, value;
	
	protected Point position;
		
	public Tool(int duration, int maxDuration, int value, Point position) {
		this.duration = duration;
		this.maxDuration = maxDuration;
		this.value = value;
		this.position = position;
	}
	
	@Override
	public int getDuration() {
		return duration;
	}

	@Override
	public void setDuration(int duration) {
		
		this.duration = duration;
		
		if(this.duration > maxDuration()) this.duration = maxDuration();
	}

	@Override
	public int maxDuration() {
		return maxDuration;
	}
	
	@Override
	public int getValue() {
		return value;
	}
	
	@Override
	public boolean isBreak() {
		return getDuration() == 0;
	}
	
	@Override
	public Point getPosition() {
		return position;
	}
	
	@Override
	public void setPosition(Point p) {
		position = p;
	}

	@Override
	public int hashCode() {
		return Objects.hash(duration, maxDuration, position);
	}

	@Override
	public boolean equals(Object obj) {
		
		if (this == obj) return true;
		
		if (!(obj instanceof Tool)) return false;

		Tool other = (Tool) obj;
		
		return duration == other.duration && maxDuration == other.maxDuration && Objects.equals(position, other.position);
	}
	
	
	
}
