package dd.Mobs.Monsters;

import java.awt.Point;

import dd.Mobs.IMob;
import dd.Mobs.IPlayer;

public class WitherMonster extends Monster{

	private final static int LIFE = 20, POWER = 80, KILL_COINS = 55;
	
	public WitherMonster(Point position, IPlayer player) {
		super(LIFE, LIFE, POWER, KILL_COINS, position, player);
	}
	
	public WitherMonster(Point position) {
		this(position, null);
	}
	
	@Override
	public String toString() {
		return "Wither (Power: " + getPower() + " - Life: " + getLives() + ")";
	}
	
	public IMob cloneMob() {
		return new WitherMonster((Point) getPosition().clone(), player);
	}

}
