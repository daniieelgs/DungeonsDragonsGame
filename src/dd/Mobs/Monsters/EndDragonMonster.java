package dd.Mobs.Monsters;

import java.awt.Point;

import dd.Mobs.IMob;
import dd.Mobs.IPlayer;

public class EndDragonMonster extends Monster{

	private final static int LIFE = 30, POWER = 120, KILL_COINS = 100, KILL_POWER = 100;
	
	public EndDragonMonster(int power, Point position, IPlayer player) {
		super(LIFE, LIFE, power, power, KILL_COINS, position, player);
	}
	
	public EndDragonMonster(Point position, IPlayer player) {
		this(POWER, position, player);
	}
	
	public EndDragonMonster(Point position) {
		this(position, null);
	}
	
	@Override
	public String toString() {
		return "END DRAGON (Power: " + getPower() + " - Life: " + getLives() + ")";
	}
	
	@Override
	public int getKillPower() {
		return KILL_POWER;
	}
	
	public IMob cloneMob() {
		return new EndDragonMonster(getPower(), (Point) getPosition().clone(), player);
	}

}
