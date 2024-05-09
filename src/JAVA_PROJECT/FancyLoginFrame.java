package JAVA_PROJECT;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FancyLoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    public FancyLoginFrame() {
        super("로그인");

        // 배경 이미지 설정
        ImageIcon backgroundImage = new ImageIcon("src\\JAVA_PROJECT\\IMG\\LOGIN.jpeg");
        JLabel backgroundLabel = new JLabel(backgroundImage);

        // 로고 이미지 설정
        ImageIcon logoImage = new ImageIcon("logo.png");
        JLabel logoLabel = new JLabel(logoImage);

        // UI 요소 초기화
        JLabel usernameLabel = new JLabel("ID :");
        JLabel passwordLabel = new JLabel("PASS :");
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        JButton loginButton = new JButton("LOGIN");
        JButton joinButton = new JButton("SIGN");

        // 로그인 버튼 스타일 설정
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(new Color(59, 89, 182));
        loginButton.setFocusPainted(false);

        // 회원 가입 버튼 스타일 설정
        joinButton.setFont(new Font("Arial", Font.BOLD, 14));
        joinButton.setForeground(Color.WHITE);
        joinButton.setBackground(new Color(59, 89, 182));
        joinButton.setFocusPainted(false);

        // 로그인 버튼 이벤트 처리
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                char[] passwordChars = passwordField.getPassword();
                String password = new String(passwordChars);

                // 콘솔에 로그인 정보 출력
                System.out.println("아이디: " + username);
                System.out.println("비밀번호: " + password);

                // 방문을 환영합니다 알림창 표시
                JOptionPane.showMessageDialog(FancyLoginFrame.this, "방문을 환영합니다!");
            }
        });


        // 회원가입 버튼 이벤트 처리
        joinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 회원가입 페이지 열기
                openSignUpPage();
            }
        });

        // 폼 디자인
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(logoLabel, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        panel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        panel.add(loginButton, gbc);

        gbc.gridy = 4;
        panel.add(joinButton, gbc);

        // JFrame 설정
        getContentPane().add(backgroundLabel, BorderLayout.CENTER);
        getContentPane().add(panel, BorderLayout.SOUTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void openSignUpPage() {
        // 회원가입 페이지 열기
        new SignUpFrame();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FancyLoginFrame::new);
    }
    
    
}
