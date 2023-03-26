package dd.Mobs.Monsters;

import java.awt.Point;

import dd.Mobs.IMob;
import dd.Mobs.IPlayer;

public class SpiderMonster extends Monster{

	private final static int LIFE = 5, POWER = 12, KILL_COINS = 13/*, KILL_POWER = 5*/;
	
	public SpiderMonster(Point position, IPlayer player) {
		super(LIFE, LIFE, POWER, KILL_COINS, position, player);
	}
	
	public SpiderMonster(Point position) {
		this(position, null);
	}
	
	@Override
	public String toString() {
		return "Spider (Power: " + getPower() + " - Life: " + getLives() + ")";
	}
	
	public IMob cloneMob() {
		return new SpiderMonster((Point) getPosition().clone(), player);
	}

}
