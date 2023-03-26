package dd.Mobs.Monsters;

import java.awt.Point;

import dd.Mobs.IMob;
import dd.Mobs.IPlayer;

public class WardenMonster extends Monster{

	private final static int LIFE = 20, POWER = 70, KILL_COINS = 45;
	
	public WardenMonster(Point position, IPlayer player) {
		super(LIFE, LIFE, POWER, KILL_COINS, position, player);
	}
	
	public WardenMonster(Point position) {
		this(position, null);
	}
	
	@Override
	public String toString() {
		return "Warden (Power: " + getPower() + " - Life: " + getLives() + ")";
	}
	
	public IMob cloneMob() {
		return new WardenMonster((Point) getPosition().clone(), player);
	}

}
