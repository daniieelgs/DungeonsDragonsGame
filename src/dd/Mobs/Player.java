package dd.Mobs;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import dd.Items.Coin;
import dd.Items.Tools.ITool;
import dd.Items.Tools.Potions.Potion;

public class Player implements IPlayer{

	final private static int MAX_LIVES = 12, MIN_LIVES = 0, DEFAULT_LIVES = 10, MAX_POWER = 100, MIN_POWER = 0, DEFAULT_POWER = 1, DEFAULT_COINS = 0;
	
	private final static char TEXTURE = '¶';
	
	private Coin coins;
	
	private int live = DEFAULT_LIVES, power = DEFAULT_POWER;
	private Point position;
	
	private List<ITool> inventory, baggage;

	public Player(int x, int y) {
		this(new Point(x, y));
	}
	
	public Player(Point position) {
		this(DEFAULT_LIVES, DEFAULT_POWER, position);
	}
	
	public Player(int live, int power, Point position) {
		this(live, power, position, new Coin(DEFAULT_COINS), new ArrayList<>(), new ArrayList<>());
	}
	
	public Player(int live, int power, Point position, Coin coins, List<ITool> inventory, List<ITool> baggage) {
		this.live = live;
		this.power = power;
		this.position = position;
		this.coins = coins;
		this.inventory = inventory;
		this.baggage = baggage;
	}
	
	public int getLives() {
		
		int lifesPlus = live;
		
		lifesPlus += baggage.stream().map(n -> n.plusLife()).reduce(0, (a, b) -> a + b).intValue();
		
		return lifesPlus;
	}

	public int addLive(int live) {
		
		this.live += live;
		
		if(this.live > maxLives()) this.live = maxLives();
		
		return getLives();
	}

	public int removeLive(int live) {
		
		this.live -= live;
		
		if(this.live < MIN_LIVES) this.live = MIN_LIVES;
		
		return getLives();
	}

	public int getPower() {
		
		int powerPlus = power;
		
		powerPlus += baggage.stream().map(n -> n.plusPower()).reduce(0, (a, b) -> a + b).intValue();
				
		return powerPlus;
	}

	public int addPower(int power) {
		
		this.power += power;
		
		if(this.power > maxPower()) this.power = maxPower();
		
		return getPower();
	}

	public int removePower(int power) {

		this.power -= power;
		
		if(this.power < MIN_POWER) this.power = MIN_POWER;
		
		return getPower();
	}

	@Override
	public Coin getCoins() {
		return coins;
	}

	@Override
	public Coin addCoin(Coin coin) {
		coins = new Coin(coins.getValue() + coin.getValue());
		return coins;
	}

	@Override
	public Coin removeCoin(Coin coin) {
		coins = new Coin(coins.getValue() - coin.getValue());
		return coins;
	}

	@Override
	public ITool[] getInventory() {
		return inventory.toArray(new ITool[inventory.size()]);
	}

	@Override
	public ITool[] addInventory(ITool tool) {
		inventory.add(tool);
		return getInventory();
	}

	@Override
	public ITool[] removeInventory(ITool tool) {
		inventory.removeIf(n -> n.equals(tool));
		return getInventory();
	}
	
	@Override
	public ITool[] getBaggage() {
		return baggage.toArray(new ITool[baggage.size()]);
	}

	@Override
	public ITool[] addBaggage(ITool tool) {
		
		if(tool instanceof Potion) {
			
			Potion p = (Potion) tool;
			
			addLive(p.plusLife());
			addPower(p.plusPower());
			
			p.setDuration(p.getDuration() - 1);
			
			if(p.isBreak()) return getBaggage();
			
		}
		
		ITool[] samesTools = baggage.stream().filter(n -> n.getClass().getSuperclass() == tool.getClass().getSuperclass()).toArray(ITool[]::new);
		
		if(samesTools.length > 0) moveBaggageToInventory(samesTools[0]);
		
		baggage.add(tool);
		return getBaggage();
	}

	@Override
	public ITool[] removeBaggage(ITool tool) {
		baggage.removeIf(n -> n.equals(tool));
		return getBaggage();
	}
	

	@Override
	public ITool moveInventoryToBaggage(ITool tool) {
		
		removeInventory(tool);
		addBaggage(tool);
		
		return tool;
	}

	@Override
	public ITool moveBaggageToInventory(ITool tool) {
		
		removeBaggage(tool);
		addInventory(tool);
		
		return tool;
	}

	public Point getPosition() {
		return position;
	}

	@Override
	public void setPosition(Point pos) {
		position = pos;
	}

	public boolean isLive() {
		return getLives() > MIN_LIVES;
	}

	public int maxLives() {
		return MAX_LIVES;
	}

	@Override
	public int maxPower() {
		return MAX_POWER;
	}

	@Override
	public char texture() {
		return TEXTURE;
	}

	@Override
	public IMob cloneMob() {
		return new Player(getLives(), getPower(), (Point) getPosition().clone(), coins, inventory, baggage);
	}

	public void updateItems() {
		updateToolList(baggage);
		updateToolList(inventory);
	}
	
	private void updateToolList(List<ITool> list) {
		
		Iterator<ITool> it = list.iterator();
		
		while(it.hasNext()) {
			
			ITool tool = it.next();
			
			if(tool.isBreak()) it.remove();
			
		}
		
	}

}
