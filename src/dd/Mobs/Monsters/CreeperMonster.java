package dd.Mobs.Monsters;

import java.awt.Point;

import dd.Mobs.IMob;
import dd.Mobs.IPlayer;

public class CreeperMonster extends Monster{

	private final static int LIFE = 8, POWER = 17, KILL_COINS = 15/*, KILL_POWER = 7*/;
	
	public CreeperMonster(Point position, IPlayer player) {
		super(LIFE, LIFE, POWER, KILL_COINS, position, player);
	}
	
	public CreeperMonster(Point position) {
		this(position, null);
	}
	
	@Override
	public String toString() {
		return "Creeper (Power: " + getPower() + " - Life: " + getLives() + ")";
	}
	
	public IMob cloneMob() {
		return new CreeperMonster((Point) getPosition().clone(), player);
	}

}
