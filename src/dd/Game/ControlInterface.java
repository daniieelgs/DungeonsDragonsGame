package dd.Game;

import java.util.stream.Stream;

import Extends.ThreadExtends;
import dd.Rooms.Map;

public interface ControlInterface {
	
	static long DEFAULT_TIME_WRTIE_EFECT = 35;
	
	public Input readInput();
	public Input readInput(String s);
	
	public int readNumber(int min, int max);
	public int readNumber(String s, int min, int max);
	 
	public void showInfo(String msg);
	
	public boolean ask(String msg);
	public void showError(String err);
	
	public void showMap(Map map);
	
	public void continueUser(String s);
	public void continueUser();

	public void close();
	
	default public void showInfoWriteEfect(String msg) {
		showInfoWriteEfectTime(msg, DEFAULT_TIME_WRTIE_EFECT);
	}
	
	default public void showInfoWriteEfectTime(String msg, long time) {
		
		Stream.of(msg.split("")).forEach(n -> {
			
			showInfoLine(String.valueOf(n), false);
			ThreadExtends.sleep(time);
			
		});
		
		showInfo("");
		
	}
	
	public void showInfoLine(String msg, boolean newLine);
	
	default public void showInfoWriteEfect(String msg, Object... obj) {
		showInfoWriteEfect(String.format(msg, obj));
	}
	
	default public void showInfo(String msg, Object... obj) {
		showInfo(String.format(msg, obj));
	}
	
	default boolean ask(String msg, Object... obj) {
		return ask(String.format(msg, obj));
	}
	
	default void showError(String msg, Object... obj) {
		showError(String.format(msg, obj));
	}
	
	public Fight fight(char monster, char player, int velMonster, int velPlayer, int prct);
	
	public enum Input{
	
		UP('W', "Move player UP"),
		DOWN('S', "Move player DOWN"),
		LEFT('A', "Move player LEFT"),
		RIGHT('D', "Move player RIGHT"),
		PLAYER('I', "Show player info"),
		INVENTORY('E', "Show player full inventory"),
		BAGGAGE('F', "Show player baggage"),
		USE('U', "Equipate o desequipate tools"),
		DROP('Q', "Drop item"),
		SHOP('B', "Open Shop/Buy"),
		SELL('V', "Open Shop/Sell"),
		EXIT('X', "Exit"),
		TUTORIAL('T', "Play tutorial"),
		HELP('?', "Help");

		
		private char instrucction;
		private String description;		
		
		private Input(char instrucction, String description) {
			this.instrucction = instrucction;
			this.description = description;
		}
		
		private Input(char instrucction) {
			this(instrucction, "");
		}
		
		public char instrucction() {
			return instrucction;
		}
		
		public String description() {
			return description;
		}
		
	}
	
	public enum Fight{
		MONSTER, PLAYER, EQUALS, NOTHING;
	}
	
	
}
