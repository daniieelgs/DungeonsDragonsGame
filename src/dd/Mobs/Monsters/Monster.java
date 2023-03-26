package dd.Mobs.Monsters;

import java.awt.Point;

import dd.Mobs.IPlayer;

public abstract class Monster implements IMonster{

	private int life, maxLife, power, killCoins;
	private final static int MAX_POWER_DEFAULT = 100;
	private int maxPower;
	private Point position;
	private final static char TEXTURE = '☻';
	protected IPlayer player;

	public Monster(int life, int maxLife, int power, int maxPower, int killCoins, Point position, IPlayer player) {
		this.life = life;
		this.maxLife = life;
		this.maxPower = maxPower;
		this.power = power > maxPower ? maxPower : power;
		this.position = position;
		
		this.killCoins = killCoins;
		
		this.player = player;
	} 
	
	public Monster(int life, int maxLife, int power, int killCoins, Point position, IPlayer player) {
		this(life, maxLife, power, MAX_POWER_DEFAULT, killCoins, position, player);
	} 
	
	public Monster(int life, int maxLife, int power, int killCoins, Point position) {
		this(life, maxLife, power, killCoins, position, null);
	}
	
	@Override
	public int getLives() {
		return life;
	}

	@Override
	public int removeLive(int live) {
		
		this.life -= live;
		
		if(this.life < 0) this.life = 0;
		
		return this.life;
	}

	@Override
	public int getPower() {
		return power;
	}

	@Override
	public int maxLives() {
		return maxLife;
	}

	@Override
	public int maxPower() {
		return maxPower;
	}

	@Override
	public boolean isLive() {
		return life > 0;
	}

	@Override
	public Point getPosition() {
		return position;
	}

	@Override
	public void setPosition(Point pos) {
		position = pos;
	}

	@Override
	public char texture() {
		return TEXTURE;
	}
	
	@Override
	public int getKillPower() {
		
		if(player == null) return (int) (getPower()*.25);
		
		int diference = getPower() - player.getPower();
				
		if(diference == 0) return (int)Math.round(getPower()*0.1);
		
		if(diference > 0) return (int)Math.round(diference*0.5);
		
		diference *= -1;
		
		return (int)Math.round((100.0/diference)*0.1);
		
	}
	
	@Override
	public int getKillCoins() {
		return killCoins;
	}

}
