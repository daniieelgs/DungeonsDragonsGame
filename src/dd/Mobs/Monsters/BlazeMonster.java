package dd.Mobs.Monsters;

import java.awt.Point;

import dd.Mobs.IMob;
import dd.Mobs.IPlayer;

public class BlazeMonster extends Monster{

	private final static int LIFE = 10, POWER = 34, KILL_COINS = 22/*, KILL_POWER = 9*/;
	
	public BlazeMonster(Point position, IPlayer player) {
		super(LIFE, LIFE, POWER, KILL_COINS, position, player);
	}
	
	public BlazeMonster(Point position) {
		this(position, null);
	}
	
	@Override
	public String toString() {
		return "Blaze (Power: " + getPower() + " - Life: " + getLives() + ")";
	}
	
	public IMob cloneMob() {
		return new BlazeMonster((Point) getPosition().clone(), player);
	}

}
