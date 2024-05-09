package JAVA_PROJECT;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class SignUpFrame extends JFrame {

    private JTextField nameField;
    private JTextField idField;
    private JPasswordField passwordField;
    private JButton signUpButton;

    public SignUpFrame() {
        super("회원가입");
        
        // UI 요소 초기화
        JLabel nameLabel = new JLabel("이름:");
        JLabel idLabel = new JLabel("아이디:");
        JLabel passwordLabel = new JLabel("비밀번호:");
        nameField = new JTextField(20);
        idField = new JTextField(20);
        passwordField = new JPasswordField(20);
        signUpButton = new JButton("SIGN");

        // 회원가입 버튼 스타일 설정
        signUpButton.setFont(new Font("Arial", Font.BOLD, 14));
        signUpButton.setForeground(Color.WHITE);
        signUpButton.setBackground(new Color(59, 89, 182));
        signUpButton.setFocusPainted(false);

        // 회원가입 버튼 이벤트 처리
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String id = idField.getText();
                char[] passwordChars = passwordField.getPassword();
                String password = new String(passwordChars);

                // 콘솔에 회원가입 정보 출력
                System.out.println("이름: " + name);
                System.out.println("아이디: " + id);
                System.out.println("비밀번호: " + password);

                // 실제로는 여기서 회원가입 처리를 수행해야 합니다.
                // 예를 들어, 입력된 정보를 데이터베이스에 저장합니다.
            }
        });

        // 폼 디자인
        BackgroundPanel panel = new BackgroundPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackgroundImage(new ImageIcon("src\\\\JAVA_PROJECT\\\\IMG\\\\LOGIN.jpeg")); // 이미지 경로를 수정하세요
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(nameLabel, gbc);

        gbc.gridy = 1;
        panel.add(nameField, gbc);

        gbc.gridy = 2;
        panel.add(idLabel, gbc);

        gbc.gridy = 3;
        panel.add(idField, gbc);

        gbc.gridy = 4;
        panel.add(passwordLabel, gbc);

        gbc.gridy = 5;
        panel.add(passwordField, gbc);

        gbc.gridy = 6;
        panel.add(signUpButton, gbc);

        // JFrame 설정
        getContentPane().add(panel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        setSize(500, 600);
    }

    class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel() {
            super();
        }

        public void setBackgroundImage(ImageIcon backgroundImage) {
            this.backgroundImage = backgroundImage.getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
}
