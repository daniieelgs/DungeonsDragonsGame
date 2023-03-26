package dd.Mobs;

import java.awt.Point;

public interface IMob{

	public int getLives();
	public int removeLive(int live);
	
	public int getPower();

	public int maxLives();
	public int maxPower();
	
	public boolean isLive();

	public Point getPosition();
	public void setPosition(Point pos);
	
	public char texture();  
	
	public IMob cloneMob();
}
