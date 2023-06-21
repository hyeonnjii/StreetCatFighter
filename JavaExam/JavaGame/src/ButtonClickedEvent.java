import java.awt.event.*;
import javax.swing.*;

public class ButtonClickedEvent implements MouseListener {
	private GameFrame parent; //GameFrame 접근용 레퍼런스
	
	private int selectedMenu; //패널 전환시 사용되는 int 값
	private ImageIcon enteredIcon; //마우스가 올라갔을 경우에만 보여지는 이미지
	private ImageIcon presentIcon; //기존 이미지
	
	//현재 이미지와 마우스가 올라갔을 때 변경될 이미지를 매개변수로 받는 생성자
	public ButtonClickedEvent(GameFrame parent, int selectedMenu, ImageIcon enteredIcon, ImageIcon presentIcon) {
		this.parent = parent;
		//이동할 메뉴의 코드번호
		this.selectedMenu = selectedMenu;
		//마우스가 올라갔을 때 보여지게 할 이미지 아이콘
		this.enteredIcon = enteredIcon;
		//마우스가 다시 내려갔을 때 보여지게 할 기존 이미지 아이콘
		this.presentIcon = presentIcon;
	}
	
	@Override //마우스가 컴포넌트 위에 올라갈 때의 이벤트
	public void mouseEntered(MouseEvent e) {
		JLabel label = (JLabel)(e.getComponent());
		label.setIcon(enteredIcon); //마우스가 올라갈 때의 이미지로 변경
	}
	
	@Override //마우스가 컴포넌트 위를 벗어날 때의 이벤트
	public void mouseExited(MouseEvent e) {
		JLabel label = (JLabel)(e.getComponent());
		label.setIcon(presentIcon); //원래 이미지로 변경
	}
		
	@Override //마우스 버튼이 뗴어질 때의 이벤트
	public void mouseReleased(MouseEvent e) {
		JLabel label = (JLabel)(e.getComponent());
		label.setIcon(presentIcon); //원래 이미지로 변경
	}
	
	@Override //버튼이 클릭되었을 때의 이벤트
	public void mouseClicked(MouseEvent e) {
		//GameFrame의 swapPanel 함수 호출
		parent.swapPanel(selectedMenu);
	}

	@Override
	public void mousePressed(MouseEvent e) {}
}
