package dd.Mobs.Monsters;

import java.awt.Point;

import dd.Mobs.IMob;
import dd.Mobs.IPlayer;

public class ZombieMonster extends Monster{

	private final static int LIFE = 5, POWER = 3, KILL_COINS = 4/*, KILL_POWER = 2*/;
	
	public ZombieMonster(Point position, IPlayer player) {
		super(LIFE, LIFE, POWER, KILL_COINS, position, player);
	}
	
	public ZombieMonster(Point position) {
		this(position, null);
	}
	
	@Override
	public String toString() {
		return "Zombie (Power: " + getPower() + " - Life: " + getLives() + ")";
	}
	
	public IMob cloneMob() {
		return new ZombieMonster((Point) getPosition().clone(), player);
	}

}
