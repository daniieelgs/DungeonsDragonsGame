package dd.Mobs.Monsters;

import java.awt.Point;

import dd.Mobs.IMob;
import dd.Mobs.IPlayer;

public class DevastadorMonster extends Monster{

	private final static int LIFE = 15, POWER = 45, KILL_COINS = 28/*, KILL_POWER = 10*/;
	
	public DevastadorMonster(Point position, IPlayer player) {
		super(LIFE, LIFE, POWER, KILL_COINS, position, player);
	}
	
	public DevastadorMonster(Point position) {
		this(position, null);
	}
	
	@Override
	public String toString() {
		return "Devastador (Power: " + getPower() + " - Life: " + getLives() + ")";
	}
	
	public IMob cloneMob() {
		return new DevastadorMonster((Point) getPosition().clone(), player);
	}

}
