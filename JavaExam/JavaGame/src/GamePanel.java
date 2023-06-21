import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

public class GamePanel extends JPanel {

	//Frame에 대한 접근을 위한 레퍼런스
	private GameFrame parent;
	
	private Player player1, player2;
	private int leftBoxWidth;
	private int addCount;
	private boolean finished;
	private String fontFile;

	private JLabel lblName1, lblName2;
	private JLabel leftBox; // 게임 박스
	private JLabel scoreLbl; // 스코어 label
	private ImageIcon imgIntroBg, img1, img2, imgFight1, imgFight2, imgBar1, imgBar2, imgBarFrame,
			imgProfile1, imgProfile2, imgVs;
	private Image background;
	private JLabel lblImg1, lblImg2, lblImgFight1, lblImgFight2, lblBarFrame, lblBar2, lblProfile1,
			lblProfile2, lblVs; // 이미지 labels
	private JLabel lblResult1, lblResult2; // 승패 labels
	private int imgWidth, imgHeight;
	private int nWin, nLose, nTie;
	
	private Timer timer;
	
    private ImageIcon imgVLogo;
    private ImageIcon imgBackBtn;
    private JLabel lblLogoImage;

	// 게임룰 설명
	private JLabel overLayScreen;
	private boolean showOverlay; // 오버레이 표시 여부
	private JButton btnClose; // 화면 닫는 버튼
	private JTextArea ruleBox; // 게임 룰 설명 박스(JTextArea 긴글)

	// 카운트 다운
	private CountDown countDown;

	private KeyInputListener keyL;
	private ClickListener clickL;

	String name1 = JOptionPane.showInputDialog(null, "Enter Player 1 Name:");
	String name2 = JOptionPane.showInputDialog(null, "Enter Player 2 Name:");
	public GameCount gameCount;

	public GamePanel(GameFrame parent) {
		this.parent = parent; 
		
		// 페이지 설정
	    setSize(1000,700);
		setBackground(Color.WHITE);
		setLayout(null);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false); // key focus를 위해 필요

		finished = false;
		addCount = 0;

		// 색상표
		Color subText = new Color(0xFFFDBB);
		Color normText = new Color(0xEEEEEE);

		// 폰트
		fontFile = "./font/DungGeunMo.ttf";

		// Player 객체 생성
		player1 = new Player(name1, KeyEvent.VK_A, subText);
		player2 = new Player(name2, KeyEvent.VK_UP, subText);


		// 오버레이변수 초기화
		clickL = new ClickListener();

		// 닫는 버튼
		showOverlay = true;
		btnClose = new JButton("START THE GAME!!");
		btnClose.setBounds(300, 500, 400, 42);
		btnClose.setHorizontalAlignment(SwingConstants.CENTER);
		btnClose.setBorder(new LineBorder(Color.BLACK, 3));
		btnClose.addMouseListener(clickL);
		add(btnClose);

		// 게임 룰 설명
		ruleBox = new JTextArea();
		ruleBox.setText("안녕하세요," + player1.name + " 님과 " + player2.name + " 님:)" + "\n 게임룰을 설명해드릴게요:)" + "\n\n"
				+ player1.name + " 님의 키버튼은 'a' 버튼\n" + player2.name + " 님의 키버튼은 'up' 버튼입니다." + "\n\n10초 동안 자신의 키버튼을 "
				+ "\n더 많이 누르는 사람이 승리하게 됩니다." + "\n잊지마세요! " + "꾸욱 버튼을 누르는 반칙은 \n *절대* 통하지 않아요"
				+ "\n\n이제 시작할 준비를 완벽히 마치셨군요 후후" + "\n그럼 준비하시고.. 시작합니다!!!!!!");
		ruleBox.setOpaque(false);
		ruleBox.setBounds(320, 210, 500, 300);
		ruleBox.setForeground(Color.white);
		add(ruleBox);

		// 오버레이 스크린 frame
		imgIntroBg = new ImageIcon("./img/introbg.png");
		overLayScreen = new JLabel(imgIntroBg);
		overLayScreen.setBounds(0, 0, 1000, 700);
		add(overLayScreen);

		// countDown 생성
		countDown = new CountDown();
		countDown.setBounds(0, 0, 1000, 700);
		
		// gameCount 생성
		gameCount = new GameCount();
		gameCount.setBounds(0, 0, 1000, 700);
		add(gameCount);

		// Player Images
		img1 = new ImageIcon("./img/player1.png");
		img2 = new ImageIcon("./img/player2.png");
		imgFight1 = new ImageIcon("./img/player1Fight.png");
		imgFight2 = new ImageIcon("./img/player2Fight.png");
		imgBarFrame = new ImageIcon("./img/barframe.png");
		imgProfile1 = new ImageIcon("./img/p1profile.png");
		imgProfile2 = new ImageIcon("./img/p2profile.png");
		imgVs = new ImageIcon("./img/vs.png");
		background = new ImageIcon("./img/background2.png").getImage();
        imgVLogo = new ImageIcon("./img/logo.png");
        imgBackBtn = new ImageIcon("./img/backBtn.png");

		// 원하는 크기로 이미지 크기 조정
		imgWidth = 450;
		imgHeight = 300;
		img1 = new ImageIcon(img1.getImage().getScaledInstance(imgWidth, imgHeight, java.awt.Image.SCALE_SMOOTH));
		img2 = new ImageIcon(img2.getImage().getScaledInstance(imgWidth, imgHeight, java.awt.Image.SCALE_SMOOTH));
		imgFight1 = new ImageIcon(
				imgFight1.getImage().getScaledInstance(imgWidth, imgHeight, java.awt.Image.SCALE_SMOOTH));
		imgFight2 = new ImageIcon(
				imgFight2.getImage().getScaledInstance(imgWidth, imgHeight, java.awt.Image.SCALE_SMOOTH));
		imgVs = new ImageIcon(imgVs.getImage().getScaledInstance(70, 35, java.awt.Image.SCALE_SMOOTH));
		background = background.getScaledInstance(1000, 700, java.awt.Image.SCALE_SMOOTH);
        imgVLogo = new ImageIcon(imgVLogo.getImage().getScaledInstance(150, 100, java.awt.Image.SCALE_SMOOTH));
        imgBackBtn = new ImageIcon(imgBackBtn.getImage().getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH));
    
        // 이미지 아이콘을 label로 생성
		lblImg1 = new JLabel(img1);
		lblImg2 = new JLabel(img2);
		lblImgFight1 = new JLabel(imgFight1);
		lblImgFight2 = new JLabel(imgFight2);
		lblBarFrame = new JLabel(imgBarFrame);
		lblProfile1 = new JLabel(imgProfile1);
		lblProfile2 = new JLabel(imgProfile2);
		lblVs = new JLabel(imgVs);
        lblLogoImage = new JLabel(imgBackBtn);
        
        
        // 특정 위치에 배치
		lblImg1.setBounds(100, 295, 450, 300);
		lblImg2.setBounds(450, 295, 450, 300);
		lblImgFight1.setBounds(100, 295, 450, 300);
		lblImgFight2.setBounds(450, 295, 450, 300);
		lblBarFrame.setBounds(95, 40, 810, 40);
		lblProfile1.setBounds(95, 85, 70, 80);
		lblProfile2.setBounds(835, 85, 70, 80);
		lblVs.setBounds(455, 105, 87, 45);
        lblLogoImage.setBounds(10, 550, 150, 100);

		lblImgFight1.setVisible(false);
		lblImgFight2.setVisible(false);

		add(countDown); // countDown add!!!
		add(lblImg1);
		add(lblImg2);
		add(lblImgFight1);
		add(lblImgFight2);
		add(lblBarFrame);
		add(lblProfile1);
		add(lblProfile2);
		add(lblVs);
		
		// 뒤로 가기 버튼 > 시작 패널로 이동됨
      	lblLogoImage.addMouseListener(new ButtonClickedEvent(parent, -1, imgVLogo, imgBackBtn));
        add(lblLogoImage);  

		// 상태창
		imgBar2 = new ImageIcon("./img/p2bar.png");
		lblBar2 = new JLabel(imgBar2);
		lblBar2.setBounds(100, 45, 800, 30);
		lblBar2.setLayout(null);
		add(lblBar2);

		leftBoxWidth = 400;

		imgBar1 = new ImageIcon("./img/p1bar.png");
		leftBox = new JLabel(imgBar1);
		leftBox.setBounds(100, 45, leftBoxWidth, 30);
		leftBox.setLayout(null);
		leftBox.addKeyListener(new KeyInputListener());
		add(leftBox);

		setComponentZOrder(leftBox, getComponentCount() - 2);

		// key 입력을 탐지 하기 위한 리스너 생성
		keyL = new KeyInputListener();
		
		// GamePanel에 대해서 이벤트 감지할 수 있도록 설정
		addKeyListener(keyL);

		// player 이름 labels
		lblName1 = new JLabel(player1.name);
		lblName2 = new JLabel(player2.name);
		lblName1.setBounds(180, 92, 300, 32);
		lblName2.setBounds(520, 92, 300, 32);
		lblName1.setHorizontalAlignment(SwingConstants.LEFT);
		lblName2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblName1.setForeground(normText);
		lblName2.setForeground(normText);
		add(lblName1);
		add(lblName2);

		// 승패를 보여주는 labels
		nWin = nLose = nTie = 0;
		lblResult1 = new JLabel("0승 0패 0무");
		lblResult2 = new JLabel("0승 0패 0무");
		lblResult1.setBounds(180, 120, 300, 50);
		lblResult2.setBounds(520, 120, 300, 50);
		lblResult1.setHorizontalAlignment(SwingConstants.LEFT);
		lblResult2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblResult1.setForeground(subText);
		lblResult2.setForeground(subText);
		add(lblResult1);
		add(lblResult2);

		// 플레이어 점수 레이블 생성
		scoreLbl = new JLabel(player1.name + ": 0   " + player2.name + ": 0");
		scoreLbl.setBounds(0, 600, 1000, 35);
		scoreLbl.setHorizontalAlignment(SwingConstants.CENTER);
		scoreLbl.setForeground(normText);
		add(scoreLbl);

		// 0.01초 마다 GameListener 이벤트가 실행되도록 설정하기 위해 Timer 객체 생성
		timer = new Timer(10, new GameListener());
		timer.start();

		// font 적용 try-catch 문
		try {
			InputStream inputStream = new BufferedInputStream(new FileInputStream(fontFile));

			Font customFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);

			// FONT 스타일 및 크기 설정
			Font customFontRegular = customFont.deriveFont(Font.PLAIN, 16);
			Font customFontBold = customFont.deriveFont(Font.BOLD, 32);
			Font customFontRegular2 = customFont.deriveFont(Font.PLAIN, 20);

			// 게임 룰 버튼
			btnClose.setFont(customFontBold);

			// 플레이어 이름 레이블
			lblName1.setFont(customFontBold);
			lblName2.setFont(customFontBold);

			// 플레이어 결과 레이블
			lblResult1.setFont(customFontRegular);
			lblResult2.setFont(customFontRegular);

			// 게임룰 설명 레이블
			ruleBox.setFont(customFont.deriveFont(Font.PLAIN, 18));
			// 승패 결과 레이블
			scoreLbl.setFont(customFontRegular2);

		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
	}

	public void setPlayers(Player p1, Player p2) {
		player1 = p1;
		player2 = p2;

	}
	
	
	// gameCount 패널 정의
	public class GameCount extends JPanel {
		private int count = 15;
		private boolean visible = false;

		public GameCount() {
			setOpaque(false); // GameCount Panel 불투명
			setPreferredSize(new Dimension(1000, 700));
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			// 카운트다운 숫자를 그림
			if (visible) {
				g.setColor(Color.WHITE);
				try {
					InputStream inputStream = new BufferedInputStream(new FileInputStream(fontFile));

					Font customFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);

					// FONT 스타일 및 크기 설정
					Font customFontBold = customFont.deriveFont(Font.BOLD, 100);
					g.setFont(customFontBold);
				} catch (FontFormatException | IOException e) {
					e.printStackTrace();
				}
				g.drawString(Integer.toString(count), 460, 300);
			}
		}
		
		
		// gamecount를 초기화하기 위한 함수
		public void reset() {
			count = 15; //약간의 시간 텀이 생기기 때문에 15로 설정 > 10초부터 보이게 설정
			start();
		}
		
		
		// gamecount 시작 함수
		public void start() {
			timer = new Timer(1000, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					count--;
					if (count == 10) { // count 가 10일 경우부터 보이게 설정
						visible = true;
					} else if (count == 0 || finished == true) {
						timer.stop();
						// 10초가 경과하면 게임을 종료합니다.
						visible = false;
						finished = true;
					}
					repaint();
				}
			});
			// 0.5 초 딜레이를 위해 설정
			timer.setInitialDelay(500);
			timer.start();
		}
		
		// gamecount 정지 함수
		public void stop() {
			if (timer != null && timer.isRunning()) {
				timer.stop();
			}
		}
	}
	
	
	// 게임이 끝난는지 확인 여부 반환 함수
	public boolean getFinished() {
		return finished;
	}
	
	
	// leftBoxWidth를 업데이트하는 함수
	public void update() {
        requestFocusInWindow(); // 박스가 움직이기 위해서는 focus를 가져와야 함
		
		leftBoxWidth = leftBoxWidth + addCount; // addCount에 대한 변화는 keylistener에서 감지하고 있음
		//계속해서 update함수는 gameListener 안에서 동작되고 있기 때문에 0.01 초마다 실행되지만
		//keyListener에서 특정 key event 가 감지되었을 경우에만 addCount 가 leftBoxWidth를 변화시킬 수 있는 값으로 할당됨
		leftBox.setBounds(100, 45, leftBoxWidth, 30);
		
		
		// 상자가 끝까지 이동한 경우 게임종료 반환
		if (leftBoxWidth <= 0 || leftBoxWidth == 800) {
			leftBoxWidth = 0;
			finished = true;
		}

		// 플레이어 점수 업데이트
		scoreLbl.setText(player1.name + ": " + player1.score + "    " + player2.name + ": " + player2.score);

		repaint();
	}
	
	
	// 우승 게임 플레이어 반환값을 얻기 위한 함수
	public String getWinner() {
		// 더 높은 점수를 가진 플레이어의 이름을 반환
		if (player1.score > player2.score) {
			return player1.name;
			
		} else if (player2.score > player1.score) {
			return player2.name;
			
		} else {
			return "It's a tie!"; // 무승부일 경우
		}
	}
	
	
	// background 이미지 
	@Override 
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.drawImage(background, 0, 0, null);
	}
	
	
	// key event 를 감지하기 위한 리스너
	public class KeyInputListener implements KeyListener {
		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if (!countDown.isCountDown) { // countdown이 실행되는 경우에는 해당 key event 감지 못하도록 설정
				if (addCount == 0) { // 특정 변화가 없는 경우에만 실행
					if (e.getKeyCode() == KeyEvent.VK_A) { //a 버튼을 누른 경우
						addCount = 1; //1로 값 변환
						player1.score += 1;
						lblImgFight1.setVisible(true);
						lblImg1.setVisible(false);

					} else if (e.getKeyCode() == KeyEvent.VK_UP) { // up 버튼을 누른 경우
						addCount = -1;
						player2.score += 1;
						lblImgFight2.setVisible(true);
						lblImg2.setVisible(false);
					}
				}
			}
			
		}

		@Override
		public void keyReleased(KeyEvent e) { // 해당 함수를 설정해준 이유는 키보드를 눌렀을 때만 박스가 움직이도록 하기 위해 설정
			if (!countDown.isCountDown) {
				if (e.getKeyCode() == KeyEvent.VK_A) {
					addCount = 0; //0으로 바꿔 박스의 width의 변화가 없도록 함
					lblImgFight1.setVisible(false);
					lblImg1.setVisible(true);
				} else if (e.getKeyCode() == KeyEvent.VK_UP) {
					addCount = 0; //0으로 바꿔 박스의 width의 변화가 없도록 함
					lblImgFight2.setVisible(false);
					lblImg2.setVisible(true);
				}
			}
		}

	}// keyInputListener class
	
	// 초기 상태로 리셋하기 위한 함수
	public void reset() {
		gameCount.visible = false;
		leftBoxWidth = 400;
		finished = false;
		// leftBox와 rightBox 초기 상태로 복원

		leftBox.setBounds(100, 400, leftBoxWidth, 50);

		lblImgFight1.setVisible(false);
		lblImg1.setVisible(true);
		lblImgFight2.setVisible(false);
		lblImg2.setVisible(true);
		
		addCount = 0;
		player1.score = 0;
		player2.score = 0;
		scoreLbl.setText(
				player1.name + ": " + player1.score + "    " + player2.name + ": " + player2.score);

		countDown.reset();
		gameCount.reset();
	}

	public class GameListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			update();

			// 게임 종료 조건
			if (getFinished()) {
				// 게임 종료 메시지 출력
				gameCount.visible = false;
				String winner = getWinner();

				int result;
				
				// 무승부 일 경우
				if (winner == "It's a tie!") {
					result = JOptionPane.showConfirmDialog(null, "---------Time's Up---------\n" 
							+ winner  // wins! 문자 제외시킴
							+ "\n--------------------------------\n"
							+"Keep the game with this player?");
				} else {
					result = JOptionPane.showConfirmDialog(null, "---------Time's Up---------\n" 
							+ winner + " wins!\n"
							+ "--------------------------------\n"
							+"Keep the game with this player?");
				}

				// 사용자가 예 버튼을 클릭했을 경우
				if (result == JOptionPane.YES_OPTION) {

					// 승패 result 재설정
					if (winner == player1.name) {
						nWin += 1;
					} else if (winner == player2.name) {
						nLose += 1;
					} else {
						nTie += 1;
					}
					
					
					lblResult1.setText(nWin + "승 " + nLose + "패 " + nTie + "무 ");
					lblResult2.setText(nLose + "승 " + nWin + "패 " + nTie + "무 ");
					reset();
					
					
				} else if (result == JOptionPane.NO_OPTION){ // 아니오 버튼을 클릭했을 경우
					
					parent.swapPanel(-1); // 시작화면으로 초기화
					
				} else if (result == JOptionPane.CANCEL_OPTION) { // 취소 버튼을 클릭했을 경우
					
					System.exit(0); // 시스템 강제 종료
				}

			}

		}

	}

	// 게임 룰 설명 부분에서 btnClose 버튼 클릭 시 countDown thread가 시작되도록 하는 mouse listener
	public class ClickListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			JButton btn = (JButton) e.getSource();

			if (btn == btnClose) {
				// 오버레이 닫기
				showOverlay = false;
				overLayScreen.setVisible(false);
				btnClose.setVisible(false);
				ruleBox.setVisible(false);
				repaint();
				
				
				//countdown 과 gameCount 시작할 수 있도록 설정
				countDown.start();
				gameCount.start();

			}

		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

	}

}