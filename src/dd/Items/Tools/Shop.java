package dd.Items.Tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import dd.Items.Tools.Pickaxes.DiamondPickaxe;
import dd.Items.Tools.Pickaxes.GoldenPickaxe;
import dd.Items.Tools.Pickaxes.IronPickaxe;
import dd.Items.Tools.Pickaxes.Pickaxe;
import dd.Items.Tools.Pickaxes.StonePickaxe;
import dd.Items.Tools.Potions.Potion;
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
import dd.Items.Tools.Shields.Shield;
import dd.Items.Tools.Shields.WoodenShield;
import dd.Items.Tools.Swords.DiamondSword;
import dd.Items.Tools.Swords.GoldenSword;
import dd.Items.Tools.Swords.IronSword;
import dd.Items.Tools.Swords.StoneSword;
import dd.Items.Tools.Swords.Sword;

public class Shop {

	
	private static Sword[] swords = new Sword[] { new StoneSword(), new IronSword(), new GoldenSword(), new DiamondSword() };
	private static Pickaxe[] pickaxes = new Pickaxe[] { new StonePickaxe(), new IronPickaxe(), new GoldenPickaxe(), new DiamondPickaxe() };
	private static Shield[] shields = new Shield[] { new WoodenShield(), new IronShield() };
	private static Potion[] potions = new Potion[] { new PotionLifeLvl1(), new PotionLifeLvl2(), new PotionLifeLvl3(), new PotionPowerLvl1(), new PotionPowerLvl2(), new PotionPowerLvl3(), new PotionPowerLifeLvl1(), new PotionPowerLifeLvl2(), new PotionPowerLifeLvl3() };
	
	private static HashMap<ShopCategory, Tool[]> shop = new HashMap<>();
	
	public Shop() {
		
		shop.put(ShopCategory.SWORD, swords);
		shop.put(ShopCategory.PICKAXE, pickaxes);
		shop.put(ShopCategory.SHIELDS, shields);
		shop.put(ShopCategory.POTIONS, potions);
		
	}
	
	public ITool[] getCategoryList(ShopCategory category) {
		
		return shop.get(category);
		
	}
	
	public ITool[] getAllToolsList() {
		
		List<ITool> tools = new ArrayList<>();
		
		Stream.of(ShopCategory.values()).forEach(n -> Collections.addAll(tools, getCategoryList(n)));
		
		return tools.toArray(new ITool[tools.size()]);
	}
	
	public enum ShopCategory{
		
		SWORD("Swords"), PICKAXE("Pickaxes"), SHIELDS("Shields"), POTIONS("Potions");
		
		private String nameCatgory;
		private ShopCategory(String nameCategory) {
			this.nameCatgory = nameCategory;
		}
		
		public String categoryName() {
			return nameCatgory;
		}
		
	}
	
}
