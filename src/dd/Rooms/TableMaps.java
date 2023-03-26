package dd.Rooms;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.json.simple.parser.ParseException;

import dd.Configurations.SettingsRoom;
import dd.Exceptions.InvalidMoviment;
import dd.Game.ControlInterface;
import dd.Game.ControlInterface.Fight;
import dd.Game.ControlInterface.Input;
import dd.Items.Coin;
import dd.Items.Door;
import dd.Items.IDoor;
import dd.Items.IItem;
import dd.Items.Tools.ITool;
import dd.Items.Tools.Shop;
import dd.Items.Tools.Pickaxes.Pickaxe;
import dd.Items.Tools.Pickaxes.StonePickaxe;
import dd.Items.Tools.Potions.Potion;
import dd.Items.Tools.Potions.PotionLifeLvl1;
import dd.Items.Tools.Shields.Shield;
import dd.Items.Tools.Shields.WoodenShield;
import dd.Items.Tools.Swords.IronSword;
import dd.Items.Tools.Swords.StoneSword;
import dd.Items.Tools.Swords.Sword;
import dd.Mobs.IMob;
import dd.Mobs.IPlayer;
import dd.Mobs.Player;
import dd.Mobs.Monsters.EndDragonMonster;
import dd.Mobs.Monsters.IMonster;
import dd.Mobs.Monsters.ZombieMonster;
import Extends.AsciiArt;
import Extends.StringExtends;
import Extends.ThreadExtends;
import JSONControl.JSONReader;
import Tuples.Pair;

public class TableMaps {

	private final char POWER_SYMBOL = '⚡', MONEY_SYMBOL = '$', HEALTH_SYMBOL = '♥', BROKEN_SYMBOL = '⛔';

	private final String SETTING_ROOM_JSON = "settingsRoom.json";
	
	private IPlayer player;
	private ControlInterface control;

	private IRoom currentRoom;
	
	private boolean play, win;
	
	private Shop shop;
	
	private Properties lan;
	
	private SettingsRoom setting;
		
	public TableMaps(IPlayer player, String configPath, ControlInterface control, Properties lan) {
		
		this.lan = lan;
		
		this.player = player;
		this.control = control;
		
		try {
			setting = new SettingsRoom(new JSONReader(configPath + "/" + SETTING_ROOM_JSON).json());
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		
		currentRoom = new Room(player, true, false, true, true, setting);
		
		shop = new Shop();
				
	}
	
	public ControlInterface getControl() {
		return control;
	}

	public void setControl(ControlInterface control) {
		this.control = control;
	}
	
	public void play() {
		
		play = true;
		win = false;
		
		control.showInfo(lan.getProperty("Title"));
		
		startUp();
		
		currentRoom.generateRoom();
		
		control.showMap(currentRoom.getMap());
	
		while(player.isLive() && play) {
			
			
			ControlInterface.Input input = control.readInput();
			
			switch (input) {
				case UP:
				case DOWN:
				case LEFT:	
				case RIGHT:
					movePLayer(input);
					break;
				case PLAYER:
					showExtensInfoPlayer();
					break;
				case INVENTORY:
					showAllPlayerTools();
					break;
				case BAGGAGE:
					control.showInfo("\n" + lan.getProperty("Baggage") + ":");
					showToolList(player.getBaggage());
					break;
				case DROP:
					dropItem();
					break;
				case USE:
					changeInventoryBaggage();
					break;
				case SHOP:
					shop();
					break;
				case SELL:
					sell();
					break;
				case EXIT:
					play = !control.ask(lan.getProperty("askExit"));
					break;
				case HELP:
					help();
					break;
				case TUTORIAL:
					tutorial();
					break;
				default:
					System.err.println("Unknown input");
					break;
			}
			
			if(!player.isLive()) {
				
				control.showInfo(AsciiArt.gameOverEmoji());
				
				ThreadExtends.sleep(1500);
			}
			
			if(play) {
				
				currentRoom.update();
				
				control.showMap(currentRoom.getMap());
								
				if(win) {
					
					control.showInfo(AsciiArt.maxPower());
					
					ThreadExtends.sleep(1500);

					control.showInfo(AsciiArt.youWin());
					
					ThreadExtends.sleep(1500);
					
					control.showInfo(AsciiArt.winEmoji());
					
					ThreadExtends.sleep(1500);
					
					play = false;
					
				}
				
			}else
				control.showInfo(AsciiArt.goodByeEmoji());
			
		}
		
	}

	private void startUp() {
		
		if(control.ask(lan.getProperty("askPlayTutorial"))) {
			
			tutorial();
		}
		
	}
	
	private void tutorial() {
		
		Consumer<Input> inputUser = (Input input) -> {
			
			Input u = null;
			
			do u = control.readInput(); while(u != input);
			
		};
		
		int waitTime = 200; //1500
		
		List<ITool> baggage = new ArrayList<>();
		List<ITool> inventory = new ArrayList<>();
		
		inventory.add(new IronSword());
				
		IPlayer p = new Player(10, 1, player.getPosition(), new Coin(5), inventory, baggage);
		
		control.showInfoWriteEfect(lan.getProperty("TutorialPlayerAspcet"),p.texture());
		ThreadExtends.sleep(waitTime);
		control.showInfoWriteEfect(lan.getProperty("TutorialPlayerLevel"), HEALTH_SYMBOL, POWER_SYMBOL, MONEY_SYMBOL);
		ThreadExtends.sleep(waitTime);
		control.showInfoWriteEfect(lan.getProperty("TutorialPlayerInstruction"), Input.PLAYER.instrucction());
		inputUser.accept(Input.PLAYER);
		showLifeMob(p);
		showPowerMob(p);
		showMoneyPlayer(p);
		ThreadExtends.sleep(waitTime*2);
		
		Room r = new Room(p, true, false, true, true, setting);
		r.generateRoom();
		
		control.showInfoWriteEfect(lan.getProperty("TutorialMaze"));
		control.showMap(r.getMap());
		ThreadExtends.sleep(waitTime*2);
		
		control.showInfoWriteEfect(lan.getProperty("TutorialMovePlayer"), Input.UP.instrucction(), Input.DOWN.instrucction(), Input.LEFT.instrucction(), Input.RIGHT.instrucction());
		
		IMonster m = new ZombieMonster(new Point());
		
		control.showInfoWriteEfect(lan.getProperty("TutorialMonsterAspcet"), m.texture());
		ThreadExtends.sleep(waitTime);
		control.showInfoWriteEfect(lan.getProperty("TutorialMonsterLevel"));
		ThreadExtends.sleep(waitTime);
		control.showInfo(m.toString());
		showInfoMonster(m);
		ThreadExtends.sleep(waitTime);
		
		control.showInfoWriteEfect(lan.getProperty("TutorialFight"));
		ThreadExtends.sleep(waitTime);
		control.showInfoWriteEfect(lan.getProperty("TutorialFightInstructions"), Input.LEFT.instrucction(), Input.RIGHT.instrucction(), Input.DOWN.instrucction());
		control.continueUser();
		
		Fight result = control.fight(m.texture(), p.texture(), 150, 150, 50);
		
		if(result == Fight.PLAYER) control.showInfo(lan.getProperty("TutorialWin"));
		else if(result == Fight.MONSTER) control.showInfo(lan.getProperty("TutorialLose"));
		else control.showInfo(lan.getProperty("TutorialEqual"));
		
		control.showInfoWriteEfect(lan.getProperty("TutorialIfWin"));
		ThreadExtends.sleep(waitTime);

		control.showInfoWriteEfect(lan.getProperty("TutorialCoins"));
		ThreadExtends.sleep(waitTime);
		control.showInfoWriteEfect(lan.getProperty("TutorialInventory"), Input.INVENTORY.instrucction());
		inputUser.accept(Input.INVENTORY);
		showAllPlayerTools(p);
		
		control.showInfoWriteEfect(lan.getProperty("TutorialBaggage"));
		
		control.showInfoWriteEfect(lan.getProperty("TutorialChangeInventory"), Input.USE.instrucction());
		inputUser.accept(Input.USE);
		changeInventoryBaggage(p);
		
		control.showInfoWriteEfect(lan.getProperty("TutorialDropItem"), Input.DROP.instrucction());
		inputUser.accept(Input.DROP);
		dropItem(p);
		
		control.showInfoWriteEfect(lan.getProperty("TutorialTools"));
		ThreadExtends.sleep(waitTime);
		control.showInfo(new Coin(0).texture() + " -> " + lan.getProperty("TutorialCoin"));
		control.showInfo(new StoneSword().texture() + " -> " + lan.getProperty("TutorialSword"));
		control.showInfo(new StonePickaxe().texture() + " -> " + lan.getProperty("TutorialPickaxe"));
		control.showInfo(new WoodenShield().texture() + " -> " + lan.getProperty("TutorialShield"));
		control.showInfo(new PotionLifeLvl1().texture() + " -> " + lan.getProperty("TutorialPotion"));
		control.showInfo(new Door(2, 2, false, null, null).texture() + " -> " + lan.getProperty("TutorialDoorClosed"));
		control.showInfo(new Door(0, 0, true, null, null).texture() + " -> " + lan.getProperty("TutorialDoorOpened"));
		ThreadExtends.sleep(waitTime*2);
		
		control.showInfoWriteEfect(lan.getProperty("TutorialDoor"));
		ThreadExtends.sleep(waitTime);

		control.showInfoWriteEfect(lan.getProperty("TutorialShop"), Input.SHOP.instrucction());
		inputUser.accept(Input.SHOP);
		shop(p);
		
		control.showInfoWriteEfect(lan.getProperty("TutorialSell"), Input.SELL.instrucction());
		inputUser.accept(Input.SELL);
		sell(p);
		
		control.showInfoWriteEfect(lan.getProperty("TutorialPlayerWin"), p.maxPower());
		ThreadExtends.sleep(waitTime);
		control.showInfoWriteEfect(lan.getProperty("TutorialPlayerLose"));
		ThreadExtends.sleep(waitTime);
		control.showInfoWriteEfect(lan.getProperty("TutorialMoreHelp"), Input.HELP.instrucction());
		ThreadExtends.sleep(waitTime);

		help();
		
		ThreadExtends.sleep(waitTime*2);
		
	}
	
	private void help() {
		
		control.showInfo("\n" + lan.getProperty("Commands") + ": ");
		
		Stream.of(Input.values()).forEach(n -> control.showInfo(n.instrucction() + " -> " + n.description()));
		
		control.showInfo("");
	}
	
	private void takeToolPlayer(ITool tool) {
		if(control.ask(lan.getProperty("askEquip"))) {
			player.addBaggage(tool);
			showShortInfoPlayer();
		}
		else player.addInventory(tool);
	}
	
	private void movePLayer(ControlInterface.Input move) {
		
		int pX = player.getPosition().x;
		int pY = player.getPosition().y;
		
		switch (move) {
			case UP:
				pY -= 1;					
				break;
	
			case DOWN:
				pY += 1;
				break;
				
			case LEFT:
				pX -= 1;
				break;
				
			case RIGHT:
				pX += 1;
				break;
			default:
				throw new InvalidMoviment(String.format("'%c' is not a valid moviment", move.instrucction()));
		}
		
		if(pX >= Map.width || pX < 0 || pY >= Map.height || pY < 0) {
			
			if(control.ask(lan.getProperty("askExit"))) play = false;
			
			return;
		}
		
		IItem item = currentRoom.takeItem(pX, pY);
		
		if(item != null) {
			
			control.showInfo(lan.getProperty("TakeItem"), item);
			
			player.setPosition(new Point(pX, pY));
			
			if(item instanceof Coin) {
				
				player.addCoin((Coin) item);
				showMoneyPlayer();
				
			}else if(item instanceof ITool) {
				
				ITool tool = (ITool) item;

				if(tool.plusLife() > 0) control.showInfo("+" + tool.plusLife() + HEALTH_SYMBOL);
				if(tool.plusPower() > 0) control.showInfo("+" + tool.plusPower() + POWER_SYMBOL);
			
				takeToolPlayer(tool);
				
			}
			
			
			return;
		}
		
		IMonster monster = currentRoom.isMonster(pX, pY);
		
		if(monster != null) {
			
			control.showInfo(lan.getProperty("Fight"), monster);
			
			if(fight(monster)) {
				
				if(monster.getKillCoins() > 0) control.showInfo("+" + monster.getKillCoins() + MONEY_SYMBOL);
				if(monster.getKillPower() > 0) control.showInfo("+" + monster.getPower() + POWER_SYMBOL);
				
				player.addCoin(new Coin(monster.getKillCoins()));
				player.addPower(monster.getKillPower());
				
				showExtensInfoPlayer();
				
				currentRoom.update();
				
				player.setPosition(new Point(pX, pY));
				
				if(monster instanceof EndDragonMonster) win = true;

			}else {
				
				control.showInfo(lan.getProperty("GameOver"));
				
				play = false;
				
			}
			
			return;
			
		}
		
		IDoor door = currentRoom.isDoor(pX, pY);
						
		if(door != null) {
							
			if(door.isOpen()) {
				
				IRoom nextRoom = currentRoom.getRoom(door.getCardinal());
				
				if(nextRoom == null) {
					
					nextRoom = new Room(player, true, door.getCardinal().oposite(), setting);
					
					nextRoom.generateRoom();
				
					currentRoom.setRoom(door.getCardinal(), nextRoom);	
					nextRoom.setRoom(door.getCardinal().oposite(), currentRoom);
					
				}else {
					
					nextRoom.spawnPlayerToDoor(door.getCardinal().oposite());
					
				}
				
				currentRoom = nextRoom;
				
			}else {
				
				showMoneyPlayer();
				showPowerMob(player);
				
				control.showInfo(lan.getProperty("PriceDoor"), door.getValue(), MONEY_SYMBOL, door.getPower(), POWER_SYMBOL);
				
				int playerMoney = player.getCoins().getValue();
				int doorMoney = door.getValue();
				
				if(playerMoney >= doorMoney && player.getPower() >= door.getPower()) {
				
					if(control.ask(lan.getProperty("askOpenDoor"))) {
						
						player.removeCoin(new Coin(doorMoney));
						
						door.open();
						
						showMoneyPlayer();
						control.showInfo(lan.getProperty("DoorOpened"));
							
						
					}
				
				}else control.showError(lan.getProperty("ErrorOpenDor"), (doorMoney > playerMoney ? lan.getProperty("Money") : lan.getProperty("Power")), (doorMoney > playerMoney ? (doorMoney - playerMoney) + MONEY_SYMBOL : (door.getPower() - player.getPower()) + POWER_SYMBOL));
				
			}
			

			
			return;
		}
		
		if(currentRoom.isWall(pX, pY)) {
			
			Pickaxe[] pickaxes = Stream.of(player.getBaggage()).filter(n -> n instanceof Pickaxe).toArray(Pickaxe[]::new);
			
			if(pickaxes.length > 0) {
				
				Pickaxe p = pickaxes[0];
				
				p.setDuration(p.getDuration() - 1);
				
				if(p.isBreak()) {
					control.showInfo(lan.getProperty("ToolBroken"), lan.getProperty("Pickaxe"));
					player.removeBaggage(p);
				}
				
				player.setPosition(new Point(pX, pY));
			}
			
			return;
		}
		
		player.setPosition(new Point(pX, pY));
		
	}
	
	private void showAllPlayerTools() {
		showAllPlayerTools(player);
	}
	
	private void showAllPlayerTools(IPlayer p) {
		
		control.showInfo("\n" + lan.getProperty("Baggage") + ":");
		showToolList(p.getBaggage());
		
		control.showInfo("\n" + lan.getProperty("Inventory") + ":");
		showToolList(p.getInventory());
	}
	
	private void showToolList(ITool[] tools) {
		
		if(tools.length == 0) control.showInfo("    " + lan.getProperty("Empty"));
		else Stream.of(tools).forEach(n -> control.showInfo("- " + n + 
				(n.plusPower() > 0 ? "\n    " + POWER_SYMBOL + "(" + n.plusPower() + "): " + StringExtends.repeat("◼", n.plusPower()) : "") + 
				(n.plusLife() > 0 ? "\n    " + HEALTH_SYMBOL + "(" + n.plusLife() + "): " + StringExtends.repeat("◼", n.plusLife()) : "") + 
				"\n    " + BROKEN_SYMBOL + "(" + n.getDuration()  + "/" + n.maxDuration() + "): " + StringExtends.repeat("◼", n.getDuration()) + StringExtends.repeat("◻", n.maxDuration() - n.getDuration())));
		
	}
	
	private void dropItem(IPlayer player) {
		
		Pair<ITool, Inventory> tuple = selectToolInventory(player);

		if(tuple == null) return;
		
		if(tuple.element1 == null) showAllPlayerTools(player);
		else {
			
			ITool tool = tuple.element1;
			Inventory list = tuple.element2;
			
			if(!control.ask(lan.getProperty("askDeleteTool"), tool)) return;
			
			if(list == Inventory.BAGGAGE)  player.removeBaggage(tool);
			else if(list == Inventory.INVENTORY) player.removeInventory(tool);
			
			dropItem(player);
		}
	}
	
	private void dropItem() {
		dropItem(player);
	}
	
	private void changeInventoryBaggage() {
		changeInventoryBaggage(player);
	}
	
	private void changeInventoryBaggage(IPlayer player) {
		
		Pair<ITool, Inventory> tuple = selectToolInventory(player);
		
		if(tuple == null) return;
		
		if(tuple.element1 == null) showAllPlayerTools(player);
		else {
			
			ITool tool = tuple.element1;
			Inventory list = tuple.element2;
			
			if(list == Inventory.BAGGAGE)  player.moveBaggageToInventory(tool);
			else if(list == Inventory.INVENTORY){
				player.moveInventoryToBaggage(tool);
				
				if(tool instanceof Potion) {
					control.showInfo("¡GLU, GLU, GLU!");
					showShortInfoPlayer(player);
				}
			}
			
			changeInventoryBaggage(player);
			
		}
		
	}
	
	private Pair<ITool, Inventory> selectToolInventory(IPlayer player){
		
		control.showInfo("\n" + lan.getProperty("Baggage") + ":");
		
		int index = 0;
		
		ITool[] baggage = player.getBaggage();
		ITool[] inventory = player.getInventory();
		
		if(baggage.length == 0) control.showInfo("    " + lan.getProperty("Empty"));
		else
			for (ITool tool : baggage) {
				
				index++;
				
				control.showInfo("- [" + index + "]: " + tool);
				
			}
		
		control.showInfo("\nInventory:");
		
		if(inventory.length == 0) control.showInfo("    " + lan.getProperty("Empty"));
		else
			for (ITool tool : inventory) {
				
				index++;
				
				control.showInfo("- [" + index + "]: " + tool);
				
			}
		
		if(baggage.length == 0 && inventory.length == 0) return null;
		
		control.showInfo(lan.getProperty("SelectItem"));
		int select = control.readNumber("» ", 0, index);

		if(select == 0) return new Pair<>();
		
		if(select <= baggage.length) return new Pair<>(baggage[select - 1], Inventory.BAGGAGE);
		
		return new Pair<>(inventory[select - 1 - baggage.length], Inventory.INVENTORY);
	}
	
	private void sell() {
		sell(player);
	}
	
	private void sell(IPlayer player) {
		
		control.showInfo("\n" + lan.getProperty("Sell") + ":");
		
		int[] index = new int[] {0};
		
		ITool[] inventory = player.getInventory();
		
		Stream.of(inventory).forEach(n -> {
			
			index[0]++;
			
			control.showInfo(" - [" + index[0] + "] " + n + ": " + (int)Math.round(n.getValue()*0.25) + MONEY_SYMBOL);
			
		});
		
		control.showInfo(" - [" + (inventory.length + 1) + "] " + lan.getProperty("SellPower"));
		
		control.showInfo(lan.getProperty("SelectItem"));
		
		int select = control.readNumber("» ", 0, inventory.length + 1) - 1;
		
		if(select < 0) {
			showAllPlayerTools(player);
			showMoneyPlayer(player);
			return;
		}
		
		if(select == inventory.length) {
			
			int maxPower = player.getPower() - Stream.of(player.getBaggage()).map(n -> n.plusPower()).reduce(0, (a, b) -> a+b).intValue();
						
			control.showInfo(lan.getProperty("MaxPowerSell") + ": " + maxPower + POWER_SYMBOL + "\n\t1"+POWER_SYMBOL+" = 1"+MONEY_SYMBOL);
			
			control.showInfo(lan.getProperty("InsertPower") + ":");
			
			int powerSell = control.readNumber("» ", 0, maxPower);
			
			if(powerSell > 0 && control.ask(lan.getProperty("askSell"), powerSell, POWER_SYMBOL, powerSell, MONEY_SYMBOL)) {
				
				player.removePower(powerSell);
				control.showInfo("-" + powerSell + POWER_SYMBOL);
				player.addCoin(new Coin(powerSell));
				control.showInfo("+" + powerSell + MONEY_SYMBOL);
				
				showPowerMob(player);
				showMoneyPlayer(player);
			}
						

		}else {
			ITool sellTool = inventory[select];
			int price = (int)Math.round(sellTool.getValue()*0.25);
			
			if(control.ask(lan.getProperty("askSellTool"), sellTool, price, MONEY_SYMBOL)) {
				
				player.removeInventory(sellTool);
				player.addCoin(new Coin(price));
				
				control.showInfo("+" + price + MONEY_SYMBOL);
				showMoneyPlayer(player);
				
			}
		}
		
		sell(player);
		
	}
	
	private void shop() {
		shop(player);
	}
	
	private void shop(IPlayer player) {
		
		control.showInfo("\n" + lan.getProperty("Shop") + ":");
		
		int[] index = new int[] {0};
		
		Stream.of(Shop.ShopCategory.values()).forEach(n -> {
			
			control.showInfo(" - " + n.categoryName());
			
			Stream.of(shop.getCategoryList(n)).forEach(m -> {
				
				index[0]++;
				
				control.showInfo("     - [" + index[0] + "] " + m + " : " + m.getValue() + MONEY_SYMBOL);
				
			});

		});

		control.showInfo("");
		
		control.showInfo(lan.getProperty("SelectItem"));
		
		int select = control.readNumber("» ", 0, index[0]);
		
		if(select == 0) return;
		
		int collect = 0;
		
		
		Shop.ShopCategory[] categories = Shop.ShopCategory.values();
		
		ITool[] listTool = null;
		
		for(int i = 0; i < categories.length && select > collect; i++) {
			
			if(select > collect) listTool = shop.getCategoryList(categories[i]);
			
			collect += shop.getCategoryList(categories[i]).length;
			
		}
		
		collect -= listTool.length;
		
		ITool tool = listTool[select - 1 - collect];
		
		if(control.ask(lan.getProperty("askBuyTool"), tool, tool.getValue(), MONEY_SYMBOL)) {
			
			if(tool.getValue() <= player.getCoins().getValue()) {
				
				player.removeCoin(new Coin(tool.getValue()));
				
				control.showInfo(lan.getProperty("Bought"));
				
				showMoneyPlayer(player);
			
				takeToolPlayer(tool);
				
				showAllPlayerTools(player);
				
				
			}else control.showError(lan.getProperty("MissingMoney"), (tool.getValue() - player.getCoins().getValue()), MONEY_SYMBOL);
			
		}
		
		shop(player);
				
	}
	
	private boolean fight(IMonster monster) {
				
		Fight winner;
		
		int minSpeed = 6;
		int maxSpeed = 300;
		int normalSpeed = 150;
		int maxPrct = 95;
		int minPrct = 5;
		
		control.showInfo(monster.texture() + ":");
		showInfoMonster(monster);
		
		control.showInfo("\nVS\n");
		
		control.showInfo(player.texture() + ":");
		showShortInfoPlayer();
		
		control.continueUser();

		
		do {
			
			int prct = 0;
			int speedMonster = 0;
			int speedPlayer = 0;
			
			int diference = 0;
			
			int damageMonster = 1;
			int damagePlayer = 1;
			
			if(monster.getPower() > player.getPower()) {
				
				diference = (monster.getPower() - player.getPower());
				
				prct = 50 + (int)(diference*2.5);
				prct = prct > maxPrct ? maxPrct : prct;

				speedMonster = normalSpeed - (int)(normalSpeed * (prct/100.0));
				speedPlayer = normalSpeed + (int)(normalSpeed * (prct/100.0));
				
//				double prctDamage = (Double.valueOf(monster.getPower()) / player.getPower()) / 10.0;
				
				double prctDamage = (prct / 2.0) / 100.0;
				
				damageMonster = (int)Math.round(player.maxLives() * prctDamage);
				
//				prctDamage = (Double.valueOf(player.getPower()) / monster.getPower()) / 10.0;
				
				prctDamage = ((100 - prct) / 2.0) / 100;
				
				damagePlayer = (int)Math.round(monster.maxLives() * prctDamage);
				
			}else {
				
				diference = (player.getPower() - monster.getPower());
				
//				prct = 50 - diference;
				prct = 50 - (int)(diference*2.5);
				prct = prct < minPrct ? minPrct : prct;
				
				speedMonster = normalSpeed + (int)(normalSpeed * ((100-prct)/100.0));
				speedPlayer = normalSpeed - (int)(normalSpeed * ((100-prct)/100.0));
				
//				double prctDamage = (Double.valueOf(player.getPower()) / monster.getPower()) / 10.0;
				
				double prctDamage = (prct / 2.0) / 100;
				
				damagePlayer = (int)Math.round(monster.maxLives() * prctDamage);
				
//				prctDamage = (Double.valueOf(monster.getPower()) / player.getPower()) / 10.0;
				
				prctDamage = ((100 - prct) / 2.0) / 100;
				
				damageMonster = (int)Math.round(player.maxLives() * prctDamage);
				
			}
						
			damageMonster = damageMonster < 2 ? 2 : damageMonster;
			damagePlayer = damagePlayer < 2 ? 2 : damagePlayer;

//			damageMonster *= 3;
//			damagePlayer *= 3;
			
			speedMonster = speedMonster < minSpeed ? minSpeed : speedMonster;
			speedMonster = speedMonster > maxSpeed ? maxSpeed : speedMonster;

			speedPlayer = speedPlayer < minSpeed ? minSpeed : speedPlayer;
			speedPlayer = speedPlayer > maxSpeed ? maxSpeed : speedPlayer;

			winner = control.fight(monster.texture(), player.texture(), speedMonster, speedPlayer, prct);
			
			if(winner == Fight.PLAYER) monster.removeLive(damagePlayer);
			else if(winner == Fight.MONSTER) player.removeLive(damageMonster);
			else if(winner == Fight.EQUALS){
				monster.removeLive(damagePlayer);
				player.removeLive(damageMonster);
			}
						
			control.showInfo(monster.texture() + ":");
			showInfoMonster(monster);
			control.showInfo("");
			
			control.showInfo(player.texture() + ":");

			
			ITool[] swords_shield = Stream.of(player.getBaggage()).filter(n -> n instanceof Sword || n instanceof Shield).toArray(ITool[]::new);
			
			showShortInfoPlayer();
			control.showInfo("");
			
			boolean brokenTool = false;
			
			for (ITool t : swords_shield) {
				
				t.setDuration(t.getDuration() - 1);
				
				if(t.isBreak()) {
					
					control.showInfo(lan.getProperty("ToolBroken"), (t instanceof Sword ? lan.getProperty("Sword") : (t instanceof Shield ? lan.getProperty("Shield") : "Unknown")));
					player.removeBaggage(t);
					
					brokenTool = true;
					
				}
				
			}
			
			if(brokenTool) showShortInfoPlayer();
			
			control.continueUser();
			
			if(!player.isLive()) {
				
				Potion[] utilPotions = Stream.of(player.getInventory()).filter(n -> n instanceof Potion && ((Potion) n).plusLife() > 0).toArray(Potion[]::new);
				
				if(utilPotions.length > 0 && control.ask(lan.getProperty("askGoingDie"))) {
					
					int[] index = new int[] {0};
					
					Stream.of(utilPotions).forEach(n -> {
						
						index[0]++;
						
						control.showInfo("- [" + index[0] + "] " + n);
						
					});
					
					control.showInfo("Select a potion");
					
					int select = control.readNumber("» ", 1, utilPotions.length) - 1;
					
					player.moveInventoryToBaggage(utilPotions[select]);
					
					control.showInfo("¡GLU, GLU, GLU!");
					
					showShortInfoPlayer();
					
					control.continueUser();
				}
				
			}

		}while(player.isLive() && monster.isLive());
		
		monster.removeLive(monster.getLives());
		
		return player.isLive();
		
	}
	
	private void showLifeMob(IMob mob) {							
		control.showInfo(HEALTH_SYMBOL + "(" + mob.getLives() + "/" + mob.maxLives() + "): " + StringExtends.repeat("◼", mob.getLives()) + StringExtends.repeat( "◻", mob.maxLives() - mob.getLives()));
	}
	
	private void showPowerMob(IMob mob) {
		control.showInfo(POWER_SYMBOL + "(" + mob.getPower() + "/" + mob.maxPower() + "): " + StringExtends.repeat("◼", mob.getPower()) + StringExtends.repeat( "◻", mob.maxPower() - mob.getPower()));
	}
	
	private void showMoneyPlayer(IPlayer p) {
		control.showInfo(MONEY_SYMBOL + ": " + p.getCoins().getValue());
	}
	
	private void showMoneyPlayer() {
		showMoneyPlayer(player);
	}
	
	private void showShortInfoPlayer() {
		showShortInfoPlayer(player);
	}
	
	private void showShortInfoPlayer(IPlayer player) {
		showLifeMob(player);
		showPowerMob(player);
	}
	
	private void showInfoMonster(IMonster monster) {
		showLifeMob(monster);
		showPowerMob(monster);
	}
	
	private void showExtensInfoPlayer() {
		showShortInfoPlayer();
		showMoneyPlayer();
	}
	
	private enum Inventory{
		INVENTORY, BAGGAGE;
	}
	
}
