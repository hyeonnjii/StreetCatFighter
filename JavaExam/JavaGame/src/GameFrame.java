import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

	//멤버 변수들을 통해, 각 패널들 별로 고유한 코드번호 부여
	public static final int BEGINNING_PANEL = -1; //시작메뉴 패널
	public static final int GAME_PANEL = 0; //게임 플레이 패널
	
	
	private BeginningPanel beginningPanel; //시작메뉴 패널
	private GamePanel gamePanel; //게임 플레이 패널
	
	public GameFrame() {
		
		
		//패널 선언 및 생성
		beginningPanel = new BeginningPanel(this);
		gamePanel = new GamePanel(this);
		
		//시작 화면을 ContentPane으로 지정
		setContentPane(beginningPanel);
	}
		
	
	   //사용자의 버튼 클릭에 따라 ContentPane을 달리주어 화면을 변화시키는 메소드
	   //코드번호를 매개변수 --> setContentPane함수를 이용하여 선택한 메뉴에 알맞게 화면을 변경
	   public void swapPanel(int selectedMenu) {

	      switch (selectedMenu) {
	         case BEGINNING_PANEL: //-1
	            setContentPane(beginningPanel);
	            
	            // 뒤로가기 혹은 NO_OPTION을 통해 시작화면으로 온 경우, timecount를 다시 재설정해야 원래대로 동작
	            gamePanel.gameCount.stop();
	            gamePanel.reset();
	            break;
	         case GAME_PANEL: //0
	            setContentPane(gamePanel);
	            break;
	      }
	   }
	
	
}
