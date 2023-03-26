package dd.Mobs.Monsters;

import java.awt.Point;

import dd.Mobs.IMob;
import dd.Mobs.IPlayer;

public class EndermanMonster extends Monster{

	private final static int LIFE = 5, POWER = 3, KILL_COINS = 10/*, KILL_POWER = 2*/;
	
	public EndermanMonster(Point position, IPlayer player) {
		super(LIFE, LIFE, POWER, KILL_COINS, position, player);
	}
	
	public EndermanMonster(Point position) {
		this(position, null);
	}
	
	@Override
	public String toString() {
		return "Zombie (Power: " + getPower() + " - Life: " + getLives() + ")";
	}

	public IMob cloneMob() {
		return new EndermanMonster((Point) getPosition().clone(), player);
	}

}
