package dd.Mobs;

import dd.Items.Coin;
import dd.Items.Tools.ITool;

public interface IPlayer extends IMob{

	public int addLive(int live);
	
	public int addPower(int power);
	public int removePower(int power);
	
	public Coin getCoins();
	public Coin addCoin(Coin coin);
	public Coin removeCoin(Coin coin);
	
	public ITool[] getInventory();
	public ITool[] addInventory(ITool tool);
	public ITool[] removeInventory(ITool tool);

	public ITool[] getBaggage();
	public ITool[] addBaggage(ITool tool);
	public ITool[] removeBaggage(ITool tool);
	
	public ITool moveInventoryToBaggage(ITool tool);
	public ITool moveBaggageToInventory(ITool tool);
	
	public void updateItems();
	
}
