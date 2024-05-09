package JAVA_PROJECT;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FancyLoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe"; // Oracle 데이터베이스 URL
    private static final String USER = "SCOTT"; // 데이터베이스 사용자 이름
    private static final String PASS = "TIGER"; // 데이터베이스 암호

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
                String password = new String(passwordField.getPassword());

                // 데이터베이스와의 연결을 시도합니다.
                try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
                    // SQL 쿼리를 준비합니다.
                    String sql = "SELECT * FROM users WHERE username=? AND password=?";
                    PreparedStatement statement = conn.prepareStatement(sql);
                    statement.setString(1, username);
                    statement.setString(2, password);

                    // 쿼리를 실행하고 결과를 가져옵니다.
                    ResultSet rs = statement.executeQuery();

                    // 결과가 존재하는 경우 로그인 성공
                    if (rs.next()) {
                        JOptionPane.showMessageDialog(FancyLoginFrame.this,
                                "방문을 환영합니다!", "환영합니다!", JOptionPane.INFORMATION_MESSAGE);
                        openMainPage();
                    } else {
                        JOptionPane.showMessageDialog(FancyLoginFrame.this,
                                "로그인에 실패했습니다. 다시 시도해주세요.", "로그인 실패", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(FancyLoginFrame.this,
                            "데이터베이스 연결에 문제가 발생했습니다.", "에러", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // 회원가입 버튼 이벤트 처리
        joinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 회원가입 페이지 열기
                openSignUpPage();
                dispose();
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
    
    private void openMainPage() {
        new MainPageFrame();
   }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FancyLoginFrame::new);
    }
}
