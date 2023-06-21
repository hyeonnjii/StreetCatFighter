import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.*;

public class StreetCatFighter {
	
	public static void main(String[] args) {
		
		JFrame frame = new GameFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false); // frame 의 크기를 변경할 수 없도록 설정
		
		frame.setTitle("Street Cat Fighter");
		frame.setSize(1000,700);
		
		
		// 노트북 중앙에 위치시키는 코드
		//프레임이 생성될 위치를 가운데로 지정(게임 화면 위치)
		Dimension frameSize = frame.getSize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		//(모니터화면 가로 - 프레임화면 가로)/2, (모니터화면 세로 - 프레임화면 세로)/2
		frame.setLocation((screenSize.width - frameSize.width)/2, (screenSize.height - frameSize.height)/2);
		frame.setResizable(false); //크기 조절 불가능하게

		
		// frame.pack(); > 해당 부분은 GameFrame에서 setSize를 통해 직접 크기를 설정해줬기 때문에 제외
		frame.setVisible(true);
	} // main()
	
}
