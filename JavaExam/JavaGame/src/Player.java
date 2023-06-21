import java.awt.Color;
import java.awt.event.KeyEvent;

public class Player {
	
	// Player 구조체 설계
	public String name; //이름
	public int keyCode; //해당 키
	public Color color; //색깔
	
	public boolean isPressed; //눌렀는지 여부
	public int score;  //점수
	
	
	public Player(String pname, int pkeyCode, Color c) {
		name = pname;
		keyCode = pkeyCode;
		color = c;
		
		isPressed = false;
		score = 0;
	}

	
}