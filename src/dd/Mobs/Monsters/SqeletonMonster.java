package dd.Mobs.Monsters;

import java.awt.Point;

import dd.Mobs.IMob;
import dd.Mobs.IPlayer;

public class SqeletonMonster extends Monster{

	private final static int LIFE = 5, POWER = 8, KILL_COINS = 7/*, KILL_POWER = 4*/;
	
	public SqeletonMonster(Point position, IPlayer player) {
		super(LIFE, LIFE, POWER, KILL_COINS, position, player);
	}
	
	public SqeletonMonster(Point position) {
		this(position, null);
	}
	
	@Override
	public String toString() {
		return "Sqeleton (Power: " + getPower() + " - Life: " + getLives() + ")";
	}
	
	public IMob cloneMob() {
		return new SqeletonMonster((Point) getPosition().clone(), player);
	}

}
