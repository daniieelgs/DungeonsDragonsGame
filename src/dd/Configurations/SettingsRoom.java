package dd.Configurations;


import java.util.Arrays;
import java.util.Collections;

import org.json.simple.JSONObject;

import Tuples.Pair;

public class SettingsRoom {

	private JSONObject jObj;
		
	public final static int MAX_LEVEL = -1;
	
	public final static String 
	AXE = "axe", 
	SWORD = "sword", 
	SHIELD = "shield",
	POTION = "potion",
	COIN = "coin",
	WOODEN = "wooden",
	STONE = "stone",
	IRON = "iron",
	GOLDEN = "golden",
	DIAMOND = "diamong",
	LIFE1 = "life1",
	LIFE2 = "life2",
	LIFE3 = "life3",
	POWER1 = "power1",
	POWER2 = "power2",
	POWER3 = "power3",
	POWER_LIFE1 = "powerLife1",
	POWER_LIFE2 = "powerLife2",
	POWER_LIFE3 = "powerLife3",
	COIN1 = "coin1",
	COIN2 = "coin2",
	COIN3 = "coin3",
	COIN4 = "coin4",
	COIN5 = "coin5",
	COIN_SEPCIAL = "special",
	ZOMBIE = "zombie",
	SQELETON = "sqeleton",
	SPIDER = "spider",
	CREEPER = "creeper",
	PIGLIN = "piglin",
	BLAZE = "blaze",
	DEVASTADOR = "devastador",
	GUARDIAN = "guardian",
	WARDEN = "warden",
	WITHER = "wither",
	BBYZOMBIE = "bbyZombie",
	ENDDRAGON = "endDragon"
	;

	
	private final String MAX_KEY = "max", MOBS_SECTION = "mobs", ITEMS_SECTION = "items", PRCT = "prct", NUM = "num", MIN = "min", MAX = "max";
	
	public SettingsRoom(JSONObject jObj) {
		
		this.jObj = jObj;
				
	}
	
	@SuppressWarnings("unchecked")
	private String[] getLevelsSection(JSONObject obj) {
		
		String[] levels =  (String[]) obj.keySet().stream().filter(n -> !n.equals("max")).toArray(String[]::new);
		
		Collections.reverse(Arrays.asList(levels));
		
		return levels;
	}
	
	private JSONObject levelObj(String section, int level) {
		
		JSONObject obj = (JSONObject) jObj.get(section);
		
		String[] levels = getLevelsSection(obj);
		
		String levelKey = MAX_KEY;
				
		if(level != MAX_LEVEL) {
						
			int i = 0;
			
			while(levelKey.equals(MAX_KEY) && i < levels.length) {
				
				if(level < Integer.parseInt(levels[i]))
					levelKey = levels[i];
					
				i++;
			}
			
		}
				
		return (JSONObject) obj.get(levelKey);
	}
	
	private Pair<Double, Pair<Integer, Integer>> getPrct(JSONObject obj) {
		
		if(obj == null) return new Pair<>(0.0, new Pair<>(0, 0));
				
		double prct = Double.valueOf(obj.get(PRCT).toString());

		JSONObject numObj = (JSONObject) obj.get(NUM);
		
		int min = Integer.parseInt(numObj.get(MIN).toString());
		int max = Integer.parseInt(numObj.get(MAX).toString());
		
		Pair<Integer, Integer> min_max = new Pair<Integer, Integer>(min, max);
	
		return new Pair<>(prct, min_max);
		
	}
	
	public Pair<Double, Pair<Integer, Integer>> getPrctMob(int level, String mob) {
		
		JSONObject levelObj = levelObj(MOBS_SECTION, level);
		
		JSONObject obj = (JSONObject) levelObj.get(mob);
		
		return getPrct(obj);
	}
	
	public Pair<Double, Pair<Integer, Integer>> getPrctItem(int level, String family, String item) {
		
		JSONObject levelObj = levelObj(ITEMS_SECTION, level);
				
		JSONObject objFamily = (JSONObject) levelObj.get(family);
				
		if(objFamily == null) return getPrct(null);
		
		JSONObject obj = (JSONObject) objFamily.get(item);
		
		return getPrct(obj);
	}
	
}
