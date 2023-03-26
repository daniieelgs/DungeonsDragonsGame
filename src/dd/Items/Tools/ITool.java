package dd.Items.Tools;

import dd.Items.IItem;

public interface ITool extends IItem{

	public int getDuration();
	public void setDuration(int duration);
	
	public boolean isBreak();
	
	public int maxDuration();
	
	public int plusPower();
	public int plusLife();
	
}
