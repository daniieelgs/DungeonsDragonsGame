package dd.Mobs.Monsters;

import java.awt.Point;

import dd.Mobs.IMob;
import dd.Mobs.IPlayer;

public class PiglinMonster extends Monster{

	private final static int LIFE = 9, POWER = 25, KILL_COINS = 18/*, KILL_POWER = 8*/;
	
	public PiglinMonster(Point position, IPlayer player) {
		super(LIFE, LIFE, POWER, KILL_COINS, position, player);
	}
	
	public PiglinMonster(Point position) {
		this(position, null);
	}
	
	@Override
	public String toString() {
		return "Piglin (Power: " + getPower() + " - Life: " + getLives() + ")";
	}
	
	public IMob cloneMob() {
		return new PiglinMonster((Point) getPosition().clone(), player);
	}

}
