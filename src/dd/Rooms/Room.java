package dd.Rooms;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import dd.Configurations.SettingsRoom;
import dd.Exceptions.FullMap;
import dd.Exceptions.InvalidCardinal;
import dd.Items.Coin;
import dd.Items.Door;
import dd.Items.IDoor;
import dd.Items.IItem;
import dd.Items.IDoor.DOOR_CARDINALS;
import dd.Items.Tools.Shop;
import dd.Items.Tools.Shop.ShopCategory;
import dd.Items.Tools.Pickaxes.DiamondPickaxe;
import dd.Items.Tools.Pickaxes.GoldenPickaxe;
import dd.Items.Tools.Pickaxes.IronPickaxe;
import dd.Items.Tools.Pickaxes.StonePickaxe;
import dd.Items.Tools.Potions.PotionLifeLvl1;
import dd.Items.Tools.Potions.PotionLifeLvl2;
import dd.Items.Tools.Potions.PotionLifeLvl3;
import dd.Items.Tools.Potions.PotionPowerLifeLvl1;
import dd.Items.Tools.Potions.PotionPowerLifeLvl2;
import dd.Items.Tools.Potions.PotionPowerLifeLvl3;
import dd.Items.Tools.Potions.PotionPowerLvl1;
import dd.Items.Tools.Potions.PotionPowerLvl2;
import dd.Items.Tools.Potions.PotionPowerLvl3;
import dd.Items.Tools.Shields.IronShield;
import dd.Items.Tools.Shields.WoodenShield;
import dd.Items.Tools.Swords.DiamondSword;
import dd.Items.Tools.Swords.GoldenSword;
import dd.Items.Tools.Swords.IronSword;
import dd.Items.Tools.Swords.StoneSword;
import dd.Mobs.IPlayer;
import dd.Mobs.Monsters.BabyZombieMonster;
import dd.Mobs.Monsters.BlazeMonster;
import dd.Mobs.Monsters.CreeperMonster;
import dd.Mobs.Monsters.DevastadorMonster;
import dd.Mobs.Monsters.EndDragonMonster;
import dd.Mobs.Monsters.GuardianMonster;
import dd.Mobs.Monsters.IMonster;
import dd.Mobs.Monsters.PiglinMonster;
import dd.Mobs.Monsters.SpiderMonster;
import dd.Mobs.Monsters.SqeletonMonster;
import dd.Mobs.Monsters.WardenMonster;
import dd.Mobs.Monsters.WitherMonster;
import dd.Mobs.Monsters.ZombieMonster;
import Extends.IntegerExtends;
import Tuples.Pair;

public class Room implements IRoom{
		
	private Map map;
	
	private IPlayer player;
	
	private Point oldPlayerPosition;
		
	private DOOR_CARDINALS playerDoor;
	
	private boolean doorNorth, doorSouth, doorEast, doorWest;
	
	private HashMap<DOOR_CARDINALS, IRoom> roomsDoor;

	private ArrayList<IItem> items;
	private ArrayList<IMonster> monsters;
	private ArrayList<IDoor> doors;
	private ArrayList<Point> avaiableSpawnEntities;
	
	private Settings settings;
	
	public Room(IPlayer player, boolean doorNorth, boolean doorSouth, boolean doorEast, boolean doorWest, DOOR_CARDINALS playerDoor, SettingsRoom set) {
		this.player = player;
		this.doorNorth = doorNorth;
		this.doorSouth = doorSouth;
		this.doorEast = doorEast;
		this.doorWest = doorWest;
		
		this.playerDoor = playerDoor;
		
		int maxPower = player.getPower() - Stream.of(player.getBaggage()).map(n -> n.plusPower()).reduce(0, (a, b) -> a+b).intValue();
		
		settings = new Settings(set, maxPower);
		
		items = new ArrayList<>();
		monsters = new ArrayList<>();
		doors = new ArrayList<>();
		
		roomsDoor = new HashMap<>();
	}
	
	public Room(IPlayer player, boolean doorNorth, boolean doorSouth, boolean doorEast, boolean doorWest, SettingsRoom set) {
		this(player, doorNorth, doorSouth, doorEast, doorWest, null, set);
	}
	
	public Room(IPlayer player, boolean allDoors, DOOR_CARDINALS playerDoor, SettingsRoom set) {
		this(player, allDoors, allDoors, allDoors, allDoors, playerDoor, set);
	}
	
	public Room(IPlayer player, boolean allDoors, SettingsRoom set) {
		this(player, allDoors, null, set);
	}
	
	public Room(IPlayer player, SettingsRoom set) {
		this(player, false, set);
	}

	@Override
	public int getLevel() {
		return 0;
	}

	@Override
	public int getNumberMonster() {
		return monsters.size();
	}

	@Override
	public int getNumberTreasure() {
		return 0;
	}


	@Override
	public Map generateRoom() {
		
		map = new Map();
		
		for(int i = 0; i < Map.height; i++) {
			for(int j = 0; j < Map.width; j++) {
				map.setElement(Map.WALL, j, i);
			}
		}
						
		Point playerPos = player.getPosition();
				
		map = MazeGenerator.build(map, playerPos.x, playerPos.y, Map.WALL, Map.ROAD);
				
		doors.stream().forEach(n -> map.setElement(n.texture(), n.getPosition()));
		
		avaiableSpawnEntities = new ArrayList<>();
		
		List<Point> exceptPointsa = doors.stream().map(n -> n.getPosition()).collect(Collectors.toList());
		
		exceptPointsa.add(player.getPosition());
		
		generateAvaiableSpawnEntities(exceptPointsa.toArray(new Point[exceptPointsa.size()]));
		
		generateItems();
		generateMonsters();
		
		doors.addAll(Arrays.asList(buildDoors()));
		
		oldPlayerPosition = (Point) playerPos.clone();
		
		map.setElement(player.texture(), playerPos);
				
		spawnPlayerToDoor(playerDoor);
		
		return map;
	}
	
	private ArrayList<Point> generateAvaiableSpawnEntities(Point... excepts) {
		
		for(int x = 0; x < Map.width; x++) {
			for(int y = 0; y < Map.height; y++) {
				Point p = new Point(x, y);
				
				if(!Arrays.asList(excepts).contains(p)) avaiableSpawnEntities.add(p);
			}
		}
		
		return avaiableSpawnEntities;
	}
	
	public Door[] buildDoors() {
		
		List<DOOR_CARDINALS> cardinals = new ArrayList<>();
		
		if(doorNorth) cardinals.add(DOOR_CARDINALS.NORTH);
		if(doorSouth) cardinals.add(DOOR_CARDINALS.SOUTH);
		if(doorEast) cardinals.add(DOOR_CARDINALS.EAST);
		if(doorWest) cardinals.add(DOOR_CARDINALS.WEST);
				
		settings.calculateDoorsValue();
		
		Door[] d = cardinals.stream().map(n -> {
			
				int coin = 0;
				int power = 0;
			
				if(n != playerDoor) {
					int[] coin_power = settings.generateDoorValue();
					
					coin = coin_power[0];
					power = coin_power[1];

				}		
			
				return generateDoor(n, coin, power);
			}).toArray(Door[]::new);
		
		Stream.of(d).forEach(n -> map.setElement(n.texture(), n.getPosition()));
			
		return d;
		
	}
	
	private Door generateDoor(DOOR_CARDINALS cardinal, int value, int power) {
		
		boolean open = value == 0 && power == 0;
		
		Point position = null;
		
		int pos = 0;
		
		switch (cardinal) {
		case NORTH:
			
			pos = getRandomPos(Map.ROAD, IntStream.range(0, Map.width).mapToObj(n -> new Point(n, 1)).toArray(Point[]::new)).x;

			position = new Point(pos, 0);
			
			break;
			
		case SOUTH:
				
			pos = getRandomPos(Map.ROAD, IntStream.range(0, Map.width).mapToObj(n -> new Point(n, Map.height-2)).toArray(Point[]::new)).x;
			
			position = new Point(pos, Map.height - 1);
													
			break;

		case WEST:
			
			pos = getRandomPos(Map.ROAD, IntStream.range(0, Map.height).mapToObj(n -> new Point(1, n)).toArray(Point[]::new)).y;
			
			position = new Point(0, pos);
			
			break;
			
		case EAST:
			
			pos = getRandomPos(Map.ROAD, IntStream.range(0, Map.height).mapToObj(n -> new Point(Map.height-2, n)).toArray(Point[]::new)).y;
			
			position = new Point(Map.width - 1, pos);
			
			break;
			
		default:
			throw new InvalidCardinal();
		}
		
		return new Door(value, power, open, position, cardinal);
		
	}
	
	private Point getRandomPos(char c, Point... ids) {
		
		List<Point> list = Arrays.asList(Stream.of(ids).filter(n -> map.getElement(n) == c).toArray(Point[]::new));
		
		Collections.shuffle(list);
		
		return list.get(0);
		
	}
	
	public Point spawnPlayerToDoor(DOOR_CARDINALS door) {
		
		playerDoor = door;
		
		if(door == null) return null;
		
		map.removeElement(oldPlayerPosition);
		
		player.setPosition(getSpawnPointDoor(door));
		oldPlayerPosition = (Point) player.getPosition().clone();
		map.setElement(player.texture(), player.getPosition());
		
		return player.getPosition();
		
	}
	
	private Point getSpawnPointDoor(DOOR_CARDINALS door) {
		
		Point p = getDoorPoint(door);
		
		switch (door) {
			case NORTH:
											
				return new Point(p.x, p.y + 1);
	
			case SOUTH:
								
				return new Point(p.x, p.y - 1);
	
			case EAST:
								
				return new Point(p.x - 1, p.y);
				
			case WEST:
								
				return new Point(p.x + 1, p.y);
				
			default:
				throw new InvalidCardinal();
		}
				
	}
	
	private Point getDoorPoint(DOOR_CARDINALS door) {
				
		IDoor[] doorsFilter = doors.stream().filter(n -> n.getCardinal() == door).toArray(IDoor[]::new);
		
		if(doorsFilter.length == 0) return null;
		
		return doorsFilter[0].getPosition();
		
	}
	
	public void update() {
		updatePlayer();
		updateMobs();
		updateDoors();
	}
	
	private void updatePlayer() {
		
		map.removeElement(oldPlayerPosition);
		
		map.setElement(player.texture(), player.getPosition());
		
		oldPlayerPosition = (Point) player.getPosition().clone();
	}
	
	private void updateMobs() {
	
		Iterator<IMonster> it = monsters.iterator();
		
		while(it.hasNext()) {
			
			IMonster m = it.next();
			
			if(!m.isLive()) {
				map.removeElement(m.getPosition());
				it.remove();
			}
			
		}
		
	}
	
	private void updateDoors() {
		
		doors.stream().forEach(n -> map.setElement(n.texture(), n.getPosition()));
		
	}
	
	public IDoor isDoor(int x, int y) {
				
		IDoor[] doorsFilter = doors.stream().filter(n -> n.getPosition().x == x && n.getPosition().y == y).toArray(IDoor[]::new);
		
		if(doorsFilter.length == 0) return null;
		
		return doorsFilter[0];
		
	}
	
	public IDoor isDoor(Point p) {
		return isDoor(p.x, p.y);
	}

	@Override
	public Point[] generateMonsters() {
		
		ArrayList<Point> positions = new ArrayList<>();
		
		monsters.addAll(Arrays.asList(settings.generateMonsters()));
		
		for (IMonster monster : monsters) {
			
			Point p = monster.getPosition();
			
			positions.add(p);
			
			map.setElement(monster.texture(), p);
			
		}
		
		return null;
	}

	@Override
	public Point[] generateItems() {
		
		ArrayList<Point> positions = new ArrayList<>();
		
		items.addAll(Arrays.asList(settings.generateCoins()));
		items.addAll(Arrays.asList(settings.generateItems()));
		
		for (IItem item : items) {
			
			Point p = item.getPosition();
			
			positions.add(p);
			
			map.setElement(item.texture(), p);
			
		}
		
		return positions.toArray(new Point[positions.size()]);
	}
	
	public boolean isWall(int x, int y) {

		if(x<0||y<0||x>=Map.width||y>=Map.height) return false;
		
		char w=map.getElement(x, y);
		
		if(w==Map.WALL) return true;
		else return false;
		
	}
	
	@Override
	public boolean isWall(Point p) {

		return isWall(p.x, p.y);
		
	}

	@Override
	public boolean isRoad(Point p) {
		return isWall(p.x, p.y);
	}

	@Override
	public boolean isRoad(int x, int y) {
		if(x<0||y<0||x>=Map.width||y>=Map.height) return false;
		
		char w=map.getElement(x, y);
		
		if(w==Map.ROAD) return true;
		else return false;
	}

	public Map getMap() {
		return map;
	}
	
	public IPlayer getPlayer() {
		return player;
	}

	@Override
	public IRoom getRoom(DOOR_CARDINALS door) {
		return roomsDoor.getOrDefault(door, null);
	}

	@Override
	public void setRoom(DOOR_CARDINALS door, IRoom room) {
		roomsDoor.put(door, room);
	}
	
	@Override
	public IItem takeItem(Point p) {
		return takeItem(p.x, p.y);
	}

	@Override
	public IItem takeItem(int x, int y) {

		IItem[] itemsFiltered = items.stream().filter(n -> n.getPosition().x == x && n.getPosition().y == y).toArray(IItem[]::new);
		
		if(itemsFiltered.length == 0) return null;
		
		items.remove(itemsFiltered[0]);

		map.setElement(Map.ROAD, itemsFiltered[0].getPosition());
		
		return itemsFiltered[0];
		
	}
	
	@Override
	public IMonster isMonster(Point p) {
		return isMonster(p.x, p.y);
	}

	@Override
	public IMonster isMonster(int x, int y) {

		IMonster[] monsterFiltered = monsters.stream().filter(n -> n.getPosition().x == x && n.getPosition().y == y).toArray(IMonster[]::new);
		
		if(monsterFiltered.length == 0) return null;
		
		return monsterFiltered[0];
		
	}
	
	
	private class Settings {
		
		private int[] num_DoorValueCoin, num_DoorValuePower;
		
		private SettingsRoom setting;
		private int playerPower;
		
		public Settings(SettingsRoom setting, int playerPower) {
			
			this.setting = setting; 
			this.playerPower = playerPower;
			
		}
		
			
		private void calculateDoorsValue() {
			
			double margin = .1;
			
			int min = player.getCoins().getValue();
			
			int max = player.getCoins().getValue() + items.stream().filter(n -> n instanceof Coin).map(n -> n.getValue()).reduce(0, (a, b) -> a + b).intValue() + monsters.stream().map(n -> n.getKillCoins()).reduce(0, (a, b) -> a + b).intValue();
			
			int maxPower = player.getPower() + monsters.stream().map(n -> n.getKillPower()).reduce(0, (a, b) -> a + b).intValue();
			
			if(maxPower > 100) maxPower = 100;
			
			min -= min*margin;
			max += max*margin;
			
			max /= 4;
			
			if(min <= 0) min = 1;
			
			num_DoorValueCoin = new int[] {min, max};
			num_DoorValuePower = new int[] {(maxPower * 3) / 4, maxPower};
			
		}
			
		
		public Coin[] generateCoins() { //TODO
			
			ArrayList<Coin> coins = new ArrayList<>();
			
			coins.addAll(generateCoin(setting.getPrctItem(playerPower, SettingsRoom.COIN, SettingsRoom.COIN1), 1, 10));
			coins.addAll(generateCoin(setting.getPrctItem(playerPower, SettingsRoom.COIN, SettingsRoom.COIN2), 11, 20));
			coins.addAll(generateCoin(setting.getPrctItem(playerPower, SettingsRoom.COIN, SettingsRoom.COIN3), 21, 30));
			coins.addAll(generateCoin(setting.getPrctItem(playerPower, SettingsRoom.COIN, SettingsRoom.COIN4), 31, 40));
			coins.addAll(generateCoin(setting.getPrctItem(playerPower, SettingsRoom.COIN, SettingsRoom.COIN5), 41, 50));
			coins.addAll(generateCoin(setting.getPrctItem(playerPower, SettingsRoom.COIN, SettingsRoom.COIN_SEPCIAL), 51, 100));
			
			return coins.toArray(new Coin[coins.size()]);
			
		}

		public IItem[] generateItems() {
			
			ArrayList<IItem> items = new ArrayList<>();
			
			items.addAll(generateItems(setting.getPrctItem(playerPower, SettingsRoom.AXE, SettingsRoom.STONE), new StonePickaxe(new Point())));
			items.addAll(generateItems(setting.getPrctItem(playerPower, SettingsRoom.SWORD, SettingsRoom.STONE), new StoneSword(new Point())));

			items.addAll(generateItems(setting.getPrctItem(playerPower, SettingsRoom.AXE, SettingsRoom.IRON), new IronPickaxe(new Point())));
			items.addAll(generateItems(setting.getPrctItem(playerPower, SettingsRoom.AXE, SettingsRoom.GOLDEN), new GoldenPickaxe(new Point())));
			items.addAll(generateItems(setting.getPrctItem(playerPower, SettingsRoom.AXE, SettingsRoom.DIAMOND), new DiamondPickaxe(new Point())));

			items.addAll(generateItems(setting.getPrctItem(playerPower, SettingsRoom.SWORD, SettingsRoom.IRON), new IronSword(new Point())));
			items.addAll(generateItems(setting.getPrctItem(playerPower, SettingsRoom.SWORD, SettingsRoom.GOLDEN), new GoldenSword(new Point())));
			items.addAll(generateItems(setting.getPrctItem(playerPower, SettingsRoom.SWORD, SettingsRoom.DIAMOND), new DiamondSword(new Point())));
			
			items.addAll(generateItems(setting.getPrctItem(playerPower, SettingsRoom.SHIELD, SettingsRoom.WOODEN), new WoodenShield(new Point())));
			items.addAll(generateItems(setting.getPrctItem(playerPower, SettingsRoom.SHIELD, SettingsRoom.IRON), new IronShield(new Point())));
			
			items.addAll(generateItems(setting.getPrctItem(playerPower, SettingsRoom.POTION, SettingsRoom.LIFE1), new PotionLifeLvl1(new Point())));
			items.addAll(generateItems(setting.getPrctItem(playerPower, SettingsRoom.POTION, SettingsRoom.LIFE2), new PotionLifeLvl2(new Point())));
			items.addAll(generateItems(setting.getPrctItem(playerPower, SettingsRoom.POTION, SettingsRoom.LIFE3), new PotionLifeLvl3(new Point())));

			items.addAll(generateItems(setting.getPrctItem(playerPower, SettingsRoom.POTION, SettingsRoom.POWER1), new PotionPowerLvl1(new Point())));
			items.addAll(generateItems(setting.getPrctItem(playerPower, SettingsRoom.POTION, SettingsRoom.POWER2), new PotionPowerLvl2(new Point())));
			items.addAll(generateItems(setting.getPrctItem(playerPower, SettingsRoom.POTION, SettingsRoom.POWER3), new PotionPowerLvl3(new Point())));

			items.addAll(generateItems(setting.getPrctItem(playerPower, SettingsRoom.POTION, SettingsRoom.POWER_LIFE1), new PotionPowerLifeLvl1(new Point())));
			items.addAll(generateItems(setting.getPrctItem(playerPower, SettingsRoom.POTION, SettingsRoom.POWER_LIFE2), new PotionPowerLifeLvl2(new Point())));
			items.addAll(generateItems(setting.getPrctItem(playerPower, SettingsRoom.POTION, SettingsRoom.POWER_LIFE3), new PotionPowerLifeLvl3(new Point())));
			
			return items.toArray(new IItem[items.size()]);
			
		}
		
		public IMonster[] generateMonsters() {
			
			ArrayList<IMonster> monsters = new ArrayList<>();
									
			int plusPowerFinalBoss = 10;
			int powerFinalBoss = player.maxPower() + Stream.of(new Shop().getCategoryList(ShopCategory.SWORD)).max((a, b) -> a.plusPower() - b.plusPower()).get().plusPower() + plusPowerFinalBoss;
											
			monsters.addAll(generateMonsters(setting.getPrctMob(playerPower, SettingsRoom.ZOMBIE), new ZombieMonster(new Point(), player)));
			monsters.addAll(generateMonsters(setting.getPrctMob(playerPower, SettingsRoom.SQELETON), new SqeletonMonster(new Point(), player)));
			monsters.addAll(generateMonsters(setting.getPrctMob(playerPower, SettingsRoom.SPIDER), new SpiderMonster(new Point(), player)));
			monsters.addAll(generateMonsters(setting.getPrctMob(playerPower, SettingsRoom.CREEPER), new CreeperMonster(new Point(), player)));
			monsters.addAll(generateMonsters(setting.getPrctMob(playerPower, SettingsRoom.PIGLIN), new PiglinMonster(new Point(), player)));
			monsters.addAll(generateMonsters(setting.getPrctMob(playerPower, SettingsRoom.BLAZE), new BlazeMonster(new Point(), player)));
			monsters.addAll(generateMonsters(setting.getPrctMob(playerPower, SettingsRoom.DEVASTADOR), new DevastadorMonster(new Point(), player)));
			monsters.addAll(generateMonsters(setting.getPrctMob(playerPower, SettingsRoom.GUARDIAN), new GuardianMonster(new Point(), player)));
			monsters.addAll(generateMonsters(setting.getPrctMob(playerPower, SettingsRoom.WARDEN), new WardenMonster(new Point(), player)));
			monsters.addAll(generateMonsters(setting.getPrctMob(playerPower, SettingsRoom.WITHER), new WitherMonster(new Point(), player)));
			monsters.addAll(generateMonsters(setting.getPrctMob(playerPower, SettingsRoom.BBYZOMBIE), new BabyZombieMonster(new Point(), player)));
			monsters.addAll(generateMonsters(setting.getPrctMob(playerPower, SettingsRoom.ENDDRAGON), new EndDragonMonster(powerFinalBoss, new Point(), player)));
			
			return monsters.toArray(new IMonster[monsters.size()]);
			
		}
		

		private ArrayList<Coin> generateCoin(double prct, int[] minMax, int minCoinValue, int maxCoinValue) {
			
			ArrayList<Coin> coins = new ArrayList<>();
			
			for(int i = 0; i < IntegerExtends.randomNumber(minMax[0], minMax[1]); i++) {
				
				int random = IntegerExtends.randomNumber(0, 100);
				
				if(random <= prct) coins.add(new Coin(IntegerExtends.randomNumber(minCoinValue, maxCoinValue), generateValidPosition() ));
				
			}
			
			return coins;
			
		}
		
		private Point generateValidPosition() {
						
			if(avaiableSpawnEntities.size() == 0) throw new FullMap("Could not add more items. The map is full");
				
			return avaiableSpawnEntities.remove(IntegerExtends.randomNumber(0, avaiableSpawnEntities.size() - 1));
			
		}
		
		private ArrayList<Coin> generateCoin(Pair<Double, Pair<Integer, Integer>> values, int minCoinValue, int maxCoinValue){
			return generateCoin(values.element1, new int[] {values.element2.element1, values.element2.element1}, minCoinValue, maxCoinValue);
		}
		
		private ArrayList<IMonster> generateMonsters(double prct, int[] minMax, IMonster monster) {
			
			ArrayList<IMonster> monsters = new ArrayList<>();
												
			for(int i = 0; i < IntegerExtends.randomNumber(minMax[0], minMax[1]); i++) {
				
				monster = (IMonster) monster.cloneMob();
				
				int random = IntegerExtends.randomNumber(0, 100);
				
				monster.setPosition(generateValidPosition());
								
				if(random <= prct) monsters.add(monster);
				
			}
									
			return monsters;
			
		}
		
		private ArrayList<IMonster> generateMonsters(Pair<Double, Pair<Integer, Integer>> values, IMonster monster){
			return generateMonsters(values.element1, new int[] {values.element2.element1, values.element2.element2}, monster);
		}
		
		private ArrayList<IItem> generateItems(double prct, int[] minMax, IItem item) {
			
			ArrayList<IItem> items = new ArrayList<>();
																		
			for(int i = 0; i < IntegerExtends.randomNumber(minMax[0], minMax[1]); i++) {
								
				item = (IItem) item.cloneItem();
				
				int random = IntegerExtends.randomNumber(0, 100);
				
				item.setPosition(generateValidPosition());
								
				if(random <= prct) items.add(item);
				
			}
			
			return items;
			
		}
		
		private ArrayList<IItem> generateItems(Pair<Double, Pair<Integer, Integer>> values, IItem item){
			return generateItems(values.element1, new int[] {values.element2.element1, values.element2.element1}, item);
		}
		
		private int[] generateDoorValue() {
			
			return new int[] {IntegerExtends.randomNumber(num_DoorValueCoin[0], num_DoorValueCoin[1]), IntegerExtends.randomNumber(num_DoorValuePower[0], num_DoorValuePower[1])};
			
		}
		
	}

}
