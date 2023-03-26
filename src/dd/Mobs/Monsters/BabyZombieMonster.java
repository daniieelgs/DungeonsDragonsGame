package dd.Mobs.Monsters;

import java.awt.Point;

import dd.Mobs.IMob;
import dd.Mobs.IPlayer;

public class BabyZombieMonster extends Monster{

	private final static int LIFE = 20, POWER = 90, KILL_COINS = 65;
	
	public BabyZombieMonster(Point position, IPlayer player) {
		super(LIFE, LIFE, POWER, KILL_COINS, position, player);
	}
	
	public BabyZombieMonster(Point position) {
		this(position, null);
	}
	
	@Override
	public String toString() {
		return "Bby Zombie (Power: " + getPower() + " - Life: " + getLives() + ")";
	}
	
	public IMob cloneMob() {
		return new BabyZombieMonster((Point) getPosition().clone(), player);
	}

}
