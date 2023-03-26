package dd.Items;


public interface IDoor extends IItem{

	public void open();
	public void close();
	public boolean isOpen();
	public DOOR_CARDINALS getCardinal();
	public int getPower();
	
	public enum DOOR_CARDINALS{
		
		NORTH, SOUTH, EAST, WEST;
		
		public DOOR_CARDINALS oposite() {

			switch (this) {
			case NORTH:
				
				return SOUTH;
			
			case SOUTH:
				
				return NORTH;
			
			case EAST:
				
				return WEST;

			case WEST:
				
				return EAST;
				
			default:
				return null;
			}
			
		}
		
	}
	
}
