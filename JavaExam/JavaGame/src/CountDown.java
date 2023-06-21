import javax.swing.*;
import java.awt.*;


public class CountDown extends JPanel implements Runnable{
	private static final int WINDOW_WIDTH = 1000; // frame width와 일치하는 상수값
	private static final int WINDOW_HEIGHT = 700; // frame height와 일치하는 상수값
	
	// thread 선언
	private Thread countThread;
	
	private JPanel backgroundPanel; // 투명도있는 검은색 배경 panel
	private ImageIcon imgThree, imgTwo, imgOne, imgFightLogo; // 3, 2, 1, Fight 이미지
	private JLabel lblCount1, lblCount2, lblCount3, lblFight; // 위의 이미지 아이콘을 넣기 위한 라벨
	public boolean isCountDown; // countDown 여부 확인 불린 변수
	
	private int imgWidth, imgHeight;

	
	public CountDown() {
		
		countThread = new Thread(this); // thread 생성
		
		// 페이지 설정
    	setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        setLayout(null);
        

        
        
        
        // 이미지 생성 및 라벨에 대입
        imgThree = new ImageIcon("./img/3.png");
        imgTwo = new ImageIcon("./img/2.png");
        imgOne = new ImageIcon("./img/1.png");
        imgFightLogo = new ImageIcon("./img/fight_logo.png");
        
        // 기존의 이미지를 원하는 크기에 맞춰 스케일 다운
        imgWidth = imgHeight = 500;
        imgThree = new ImageIcon(imgThree.getImage().getScaledInstance(imgWidth , imgHeight, java.awt.Image.SCALE_SMOOTH));
        imgTwo =  new ImageIcon(imgTwo.getImage().getScaledInstance(imgWidth , imgHeight, java.awt.Image.SCALE_SMOOTH));
        imgOne =  new ImageIcon(imgOne.getImage().getScaledInstance(imgWidth , imgHeight, java.awt.Image.SCALE_SMOOTH));
        imgFightLogo = new ImageIcon(imgFightLogo.getImage().getScaledInstance(700 , 700, java.awt.Image.SCALE_SMOOTH));
        
        
        // ImageIcon을 JLabel로 생성
        lblCount3 = new JLabel(imgThree);
        lblCount2 = new JLabel(imgTwo);
        lblCount1 = new JLabel(imgOne);
        lblFight = new JLabel(imgFightLogo);
        
        
        // frame의 정중앙에 위치시키기 위한 설정
        lblCount3.setBounds((WINDOW_WIDTH - imgWidth) / 2, (WINDOW_HEIGHT - imgHeight) / 2, imgWidth, imgHeight);
        lblCount2.setBounds((WINDOW_WIDTH - imgWidth) / 2, (WINDOW_HEIGHT - imgHeight) / 2, imgWidth, imgHeight);
        lblCount1.setBounds((WINDOW_WIDTH - imgWidth) / 2, (WINDOW_HEIGHT - imgHeight) / 2, imgWidth, imgHeight);
        lblFight.setBounds((WINDOW_WIDTH - imgWidth) / 2, (WINDOW_HEIGHT - imgHeight) / 2, imgWidth, imgHeight);
        
        
        // 각각 시간에 따라 보여지는 것을 달리 하기 위한 설정
        lblCount3.setVisible(false);
        lblCount2.setVisible(false);
        lblCount1.setVisible(false);
        lblFight.setVisible(false);
        
        add(lblCount3);
	    add(lblCount2);
	    add(lblCount1);
	    add(lblFight);
        
        
	    
	    // Background panel
        backgroundPanel = new JPanel();
        backgroundPanel.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        backgroundPanel.setBackground(new Color(0, 0, 0, 153)); // 검은색 투명도 60
        backgroundPanel.setVisible(false);
        add(backgroundPanel);
        
        
    }
	
	// countDown 시작 함수
	public void start() {
		if (countThread != null) {
			countThread.start();
			isCountDown = true;
		}
	}
	
	// countDown 정지 함수
	public void stop() {
		if (countThread != null) {
	        countThread.interrupt();
	        isCountDown = false;
		}
	}
	
	// 초기 설정으로 reset하는 함수
	public void reset() {
		countThread = new Thread(this);
		setVisible(true);
		start();
	}
	
	
	
	@Override
	public void run() {
		backgroundPanel.setVisible(true); // 해당 thread가 run 할 경우에 투명 검은색 패널 보이도록 설정
		
		
		try {
			Thread.sleep(500); // timesleep을 주어 2부터 나오는 문제를 해결하고자 함
		} catch (InterruptedException e) {
            e.printStackTrace();
        }
		
		
		// 거꾸로 for 문을 돌려서 3부터 차례대로 나오도록 설정
	    for (int i = 3; i > 0; i--) {
	    	backgroundPanel.setVisible(true);
	        JLabel lblCurrent = new JLabel();
	        
	        switch (i) {
	            case 3:
	                lblCurrent = lblCount3;
	                break;
	            case 2:
	                lblCurrent = lblCount2;
	                break;
	            case 1:
	                lblCurrent = lblCount1;
	                break;
	  
	        }

	        lblCurrent.setVisible(true);

	        try {
	            Thread.sleep(1000); // 1초의 지연
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	        
	        lblCurrent.setVisible(false);
	    }
	    
	    lblFight.setVisible(true);
	    try {
            Thread.sleep(1000); // 1초의 지연
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
	    
	    // countDown 이전의 설정으로 돌아가기 위해 보여짐의 여부 재설정
	    lblFight.setVisible(false);
	    backgroundPanel.setVisible(false);
	  
	    
	    stop(); // 스레드 중지
	    setVisible(false); // 실행이 종료된 후에 setVisible(false) 호출
	}

		
}
	
