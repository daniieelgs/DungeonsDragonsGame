package dd.Game;

import java.awt.Point;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.json.simple.parser.ParseException;

import Extends.AsciiArt;
import JSONControl.JSONWritterReader;
import Tuples.Pair;
import dd.Items.Coin;
import dd.Items.Tools.ITool;
import dd.Items.Tools.Pickaxes.DiamondPickaxe;
import dd.Items.Tools.Potions.PotionLifeLvl1;
import dd.Items.Tools.Potions.PotionPowerLifeLvl1;
import dd.Items.Tools.Potions.PotionPowerLvl1;
import dd.Items.Tools.Shields.IronShield;
import dd.Items.Tools.Swords.DiamondSword;
import dd.Items.Tools.Swords.IronSword;
import dd.Items.Tools.Swords.StoneSword;
import dd.Mobs.Player;
import dd.Rooms.Map;
import dd.Rooms.TableMaps;


public class DangeonsDragons{

	private final String pathLan = "lan";
	private final String confFile = "config/conf.json";
	
	public DangeonsDragons() {
		
		try {
			start();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		
	}
	
	private Properties findProperties(String propFile) throws IOException {
		
		Properties prop = new Properties();
		
		InputStream in = getClass().getClassLoader().getResourceAsStream(propFile);
				
		prop.load(in);
		
		return prop;
		
	}
	
	private Properties selectLanguaje(ControlInterface control) throws IOException, ParseException {
		
		final Properties lan = findProperties(pathLan + "/languaje_type.properties");
			
		JSONWritterReader jw = new JSONWritterReader(confFile);
		
		String defaultLan = jw.read("defaultLan").toString();
		
		List<Pair<String, String>> languajes = new ArrayList<>(lan.size());
				
		lan.keySet().forEach(n -> languajes.add(new Pair<String, String>(n.toString().split("[.]")[1], lan.getProperty(n.toString()))));
		
		Properties lanDefault = findProperties(String.format(pathLan + "/%s.properties", defaultLan.toLowerCase()));
		control.showInfo(lanDefault.getProperty("selectLan"), defaultLan);
		
		for(int i = 0; i < languajes.size(); i++) control.showInfo("   - [" + (i+1) + "] "  + languajes.get(i).element1 + ": " + languajes.get(i).element2);
		
		int select = control.readNumber(0, languajes.size()) - 1;
		
		if(languajes.get(select).element1.equals("PER")) {
			control.showError(lanDefault.getProperty("networkError"));
			System.exit(0);
		}
				
		String lanName = languajes.get(select).element1;
		
		if(lanName.equals(lan.getProperty("default")) || select == 0) return lanDefault;
		
		jw.write("defaultLan", lanName);
		
		try {
			return findProperties(String.format(pathLan + "/%s.properties", lanName.toLowerCase()));
		}catch (Exception e) {
			control.showError(lanDefault.getProperty("lanError"));
			System.exit(0);
		}
		
		return null;
		
	}
	
	@SuppressWarnings("unused")
	private Player getTestPlayer() {
		
		List<ITool> inventory = new ArrayList<>();
		List<ITool> baggage = new ArrayList<>();
		
		baggage.add(new DiamondSword());
		baggage.add(new IronShield());
		baggage.add(new DiamondPickaxe());
		
		inventory.add(new StoneSword());
		inventory.add(new IronSword());
		inventory.add(new PotionLifeLvl1());
		inventory.add(new PotionPowerLvl1());
		inventory.add(new PotionPowerLifeLvl1());
		
		return new Player(12, 100, new Point(Map.width / 2, Map.height - 1), new Coin(50), inventory, baggage);
	}
	
	private void start() throws IOException, ParseException {

		ShowMapConsole console = new ShowMapConsole();
		
		Properties lan = selectLanguaje(console);
				
		TableMaps map;
		
	
		do {
			Player player = new Player(Map.width / 2, Map.height - 1);
			
			player = getTestPlayer(); //test
			
			map = new TableMaps(player, "config", console, lan);
			
			map.play();
		}while(map.getControl().ask(lan.getProperty("askPlayAgain")));
		
		
		map.getControl().showInfo(AsciiArt.goodBye());
		
		console.close();
		
	}
	
	public static void main(String[] args) {

		new DangeonsDragons();
				
	}

}
