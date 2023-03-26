package dd.Mobs.Monsters;

import java.awt.Point;

import dd.Mobs.IMob;
import dd.Mobs.IPlayer;

public class GuardianMonster extends Monster{

	private final static int LIFE = 15, POWER = 60, KILL_COINS = 35/*, KILL_POWER = 11*/;
	
	public GuardianMonster(Point position, IPlayer player) {
		super(LIFE, LIFE, POWER, KILL_COINS, position, player);
	}
	
	public GuardianMonster(Point position) {
		this(position, null);
	}
	
	@Override
	public String toString() {
		return "Guardian (Power: " + getPower() + " - Life: " + getLives() + ")";
	}

	public IMob cloneMob() {
		return new GuardianMonster((Point) getPosition().clone(), player);
	}

}
