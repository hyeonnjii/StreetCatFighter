import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class BeginningPanel extends JPanel {

   // Frame에 대한 접근을 위한 레퍼런스
   private GameFrame parent;

   // 각 버튼들에 대한 이미지
   private ImageIcon imgStartBtn, imgStartBtnEntered;
   private ImageIcon imgVLogo;
   private Image background;
   private JLabel lblStartBtn;
   private JLabel lblLogoImage;

   
   public BeginningPanel(GameFrame parent) {
      this.parent = parent; // 상위 Frame의 함수를 이용하기 위한 변수

      setSize(1000,700);
      setBackground(Color.white);
      setLayout(null);
      


      // 게임 시작 버튼 이미지
      imgStartBtn = new ImageIcon("./img/startBtn.png");
      imgStartBtnEntered = new ImageIcon("./img/player1Fight.png");
      
      // 로고 이미지
      imgVLogo = new ImageIcon("./img/logo.png");
      // 배경 이미지
      background = new ImageIcon("./img/background.png").getImage();

      // 원하는 크기로 이미지 크기 조정
      imgStartBtn = new ImageIcon(
            imgStartBtn.getImage().getScaledInstance(200, 100, java.awt.Image.SCALE_SMOOTH));
      imgStartBtnEntered = new ImageIcon(imgStartBtnEntered.getImage().getScaledInstance(200,
       100, java.awt.Image.SCALE_SMOOTH));
      imgVLogo = new ImageIcon(imgVLogo.getImage().getScaledInstance(500, 350,
       java.awt.Image.SCALE_SMOOTH));
       background = background.getScaledInstance(1000, 700,
       java.awt.Image.SCALE_SMOOTH);

      lblStartBtn = new JLabel(imgStartBtn);
      lblLogoImage = new JLabel(imgVLogo);
      lblStartBtn.setBounds(400, 500, 200, 100);
      lblLogoImage.setBounds(250, 115, 500, 400);


      // 버튼을 누를 경우 GamePanel로 이동하기 위한 이벤트 리스너 생성 및 추가
      lblStartBtn.addMouseListener(new ButtonClickedEvent(parent, 0, imgStartBtnEntered, imgStartBtn));
      add(lblStartBtn);
      add(lblLogoImage);

      lblStartBtn.setVisible(true);

   }

   @Override
   public void paintComponent(Graphics g) {
      super.paintComponent(g);

      //배경이미지
      g.drawImage(background, 0, 0, getWidth(), getHeight(),this);
 

   }
}
