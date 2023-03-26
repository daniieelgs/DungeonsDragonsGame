package dd.Items;

import java.awt.Point;

public interface IItem {

	public int getValue();
	
	public char texture();
	
	public Point getPosition();
	public void setPosition(Point p);
	
	public IItem cloneItem();
	
}
