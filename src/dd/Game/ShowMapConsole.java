package dd.Game;

import java.util.Scanner;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import dd.Rooms.Map;
import Extends.IntegerExtends;
import Extends.StringExtends;
import Extends.ThreadExtends;

public class ShowMapConsole implements ControlInterface{

	private Scanner scan;

	private final String PRE_INPUT = "> ";
	
	public ShowMapConsole() {
		scan = new Scanner(System.in);
	}
	
	@Override
	public void showMap(Map map) {
		
		for(char[] c1 : map.map()) {
			
			for(char c : c1) {
				System.out.print(c);
			}
			System.out.print("\n");
		}
		
		
	}
	
	@Override
	public boolean ask(String msg) {
		
		
		char answer = ' ';
		
		do {
			
			System.out.println(msg + " [Y/n]");
			
			answer = scan.next().toUpperCase().charAt(0);
			
		}while("YN".indexOf(answer) == -1);
		
		return answer == 'Y';
	}

	@Override
	public Input readInput() {
		return readInput(PRE_INPUT);
	}

	@Override
	public Input readInput(String s) {
		
		System.out.print(s);
				
		ControlInterface.Input inputType = null;
		
		do {
						
			char input = scan.next().toUpperCase().charAt(0);
			
			ControlInterface.Input[] inputTypes = Stream.of(ControlInterface.Input.values()).filter(n -> n.instrucction() == input).toArray(ControlInterface.Input[]::new);
			
			if(inputTypes.length > 0) inputType = inputTypes[0];
			else {
				showError("Invalid input");
				System.out.print(s);
			}
			
		}while(inputType == null);
		
		return inputType;
	}

	public void showInfoLine(String msg, boolean newLine) {
		System.out.print(msg + (newLine ? "\n" : ""));
	}
	
	@Override
	public void showInfo(String msg) {
		showInfoLine(msg, true);
	}

	@Override
	public void showError(String err) {
		System.err.println(err);
	}

	@Override
	public Fight fight(char monster, char player, int velMonster, int velPlayer, int prct) {

		String spacesUpDown = " \n \n \n \n \n \n \n \n \n \n";
		
		char separator = '|';
		
		char shootChar = '✗';
		
		boolean[] finish = new boolean[] {false};
		
		boolean[] turnMonster = new boolean[] {false};
		
		boolean[] shoot = new boolean[] {false};
		
		int[] moveMonster = new int[] {5, 1}; //posMonster xMonster
		
		int[] movePlayer = new int[] {5, 1}; //pos x
				
		int distance = 4;
		int widht = 10;
		
		char[] lineMonster = new char[widht];
		final char[] linePlayer = new char[widht];
		
		int[] shootMonster = new int[] {0};
		boolean[] winMonster = new boolean[] {false};
		
		int movesPlayer = 0;
		
		int pointsPlyaer = 0;
		int pointsMonster = 0;
		
		for(int i = 0; i < widht; i ++) {
			lineMonster[i] = separator;
			linePlayer[i] = separator;
			
		}
		
		Consumer<Object> animation = (Object obj) -> {
			
			showInfo(spacesUpDown);
			
			for(int i = 0, k = distance-1 ; i < distance && k >= 0; i++, k--) {
				showInfo(StringExtends.repeat(spacesUpDown, 4));
				showInfo(new String(lineMonster));
				
				for(int j = 0; j < distance; j++) {
					
					if(!turnMonster[0]) {

						showInfoLine(StringExtends.repeat(" ",  !turnMonster[0] ? moveMonster[0] : movePlayer[0]) + (j <= i ? "❚" : "❙") + StringExtends.repeat(" ", widht - (!turnMonster[0] ? moveMonster[0] : movePlayer[0])) + "\n", false);
						
					}else {
						
						showInfoLine(StringExtends.repeat(" ",  !turnMonster[0] ? moveMonster[0] : movePlayer[0]) + (j >= k ? "❚" : "❙") + StringExtends.repeat(" ", widht - (!turnMonster[0] ? moveMonster[0] : movePlayer[0])) + "\n", false);
						
					}
					
					
				}
				
				
				
				showInfo(new String(linePlayer));
				showInfo(spacesUpDown);
				
				ThreadExtends.sleep(200);
			}
			
		};
		
		Thread t = new Thread(() -> {
			
			while(!turnMonster[0]) {
				
				switch (readInput("")) {
				case LEFT:
										
					linePlayer[movePlayer[0]] = separator;
					
					movePlayer[0]--;
					
					linePlayer[movePlayer[0]] = player;
					
					break;

				case RIGHT:
										
					linePlayer[movePlayer[0]] = separator;
					
					movePlayer[0]++;
					
					linePlayer[movePlayer[0]] = player;
					
					break;
					
				case DOWN:
										
					turnMonster[0] = true;
					
					shootMonster[0] = IntegerExtends.randomNumber(1, 30);
					winMonster[0] = IntegerExtends.randomNumber(1, 100) <= prct;
					
					shoot[0] = true;
					
					break;
					
				default:
					break;
				}
				
				
			}
			
		});
		
		t.start();
		
		while(!finish[0]) {
			
			if(shoot[0]) {

				showInfo(spacesUpDown);
				
				moveMonster[0] -= moveMonster[1];
				
				if(movePlayer[0] == moveMonster[0]) pointsPlyaer++;
				
				lineMonster[moveMonster[0]] = monster;

				animation.accept(null);
				
				int p = movePlayer[0];
				
				char c = lineMonster[p];
				
				lineMonster[p] = shootChar;
				
				showInfo(StringExtends.repeat(spacesUpDown, 4));
				showInfo(new String(lineMonster));
				showInfoLine(StringExtends.repeat(" \n", distance), false);
				showInfo(new String(linePlayer));
				
				showInfo(spacesUpDown);
								
				ThreadExtends.sleep(1000);
				
				shoot[0] = false;

				lineMonster[p] = c;

			}
			
			showInfo(StringExtends.repeat(spacesUpDown, 4));
							
			lineMonster[moveMonster[0]] = monster;
			linePlayer[movePlayer[0]] = player;
			
			
			showInfo(new String(lineMonster));
			showInfoLine(StringExtends.repeat(StringExtends.repeat(" ",  turnMonster[0] ? moveMonster[0] : movePlayer[0]) + "❙" + StringExtends.repeat(" ", widht - (turnMonster[0] ? moveMonster[0] : movePlayer[0])) + "\n", distance), false);
			showInfo(new String(linePlayer));

			
			if(!turnMonster[0]) {
				lineMonster[moveMonster[0]] = separator;		
				
				if(moveMonster[0] + 1 >= lineMonster.length) moveMonster[1] = -1;
				else if(moveMonster[0] - 1 < 0) moveMonster[1] = 1;
				
				moveMonster[0] += moveMonster[1];
			}else {

				if(movesPlayer >= shootMonster[0]) {
					
					if((winMonster[0] && movePlayer[0] == moveMonster[0]) || (!winMonster[0] && movePlayer[0] != moveMonster[0])) {
						if(winMonster[0]) pointsMonster++;
						turnMonster[0] = false;
						animation.accept(null);
						linePlayer[moveMonster[0]] = shootChar;
						finish[0] = true;
						showInfo(spacesUpDown);

						showInfo(StringExtends.repeat(spacesUpDown, 4));

						showInfo(new String(lineMonster));
						showInfoLine(StringExtends.repeat(" \n", distance), false);
						showInfo(new String(linePlayer));
						
						showInfo(spacesUpDown);

						ThreadExtends.sleep(2000);
					}
					
				}
				
				linePlayer[movePlayer[0]] = separator;		
				
				if(movePlayer[0] + 1 >= linePlayer.length) movePlayer[1] = - 1;
				else if(movePlayer[0] - 1 < 0) movePlayer[1] = 1;
				
				movePlayer[0] += movePlayer[1];
				
				movesPlayer++;
				
			}
			
			showInfo(spacesUpDown);
			
			ThreadExtends.sleep(!turnMonster[0] ? velMonster : velPlayer);
		}
		
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if((pointsPlyaer - pointsMonster) > 0) return Fight.PLAYER;
		if((pointsPlyaer - pointsMonster) < 0) return Fight.MONSTER;
		
		return (pointsPlyaer > 0 && pointsMonster > 0) ? Fight.EQUALS : Fight.NOTHING;
	}
	
	

	@Override
	public int readNumber(int min, int max) {
		return readNumber(PRE_INPUT, min, max);
	}

	@Override
	public int readNumber(String s, int min, int max) {
		
		String input = "";
		
		do {
			
			System.out.print(s);
			
			input = scan.next();
			
		}while(!Pattern.compile("-?\\d+(\\.\\d+)?").matcher(input).matches() || Integer.parseInt(input) < min || Integer.parseInt(input) > max);
		
		return Integer.parseInt(input);
	}

	@Override
	public void close() {
		scan.close();
	}

	@Override
	public void continueUser(String s) {
		
		
		System.out.print(s);
		
		scan.nextLine();
		scan.nextLine();
				
	}

	@Override
	public void continueUser() {
		continueUser("➥");		
	}

}
