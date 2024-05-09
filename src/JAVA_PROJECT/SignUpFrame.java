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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class SignUpFrame extends JFrame {

    private JTextField nameField;
    private JTextField idField;
    private JPasswordField passwordField;
    private JButton signUpButton;

    private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String USERNAME = "SCOTT";
    private static final String PASSWORD = "TIGER";

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

                // 사용자 정보를 데이터베이스에 삽입
                if (insertUser(name, id, password)) {
                    JOptionPane.showMessageDialog(SignUpFrame.this, "회원가입 성공: " + name + "환영합니다!!!");
                    openLoginPage();
                } else {
                    JOptionPane.showMessageDialog(SignUpFrame.this, "회원가입 실패", "경고", JOptionPane.WARNING_MESSAGE);
                }	
            }
        });

        // 폼 디자인
        BackgroundPanel panel = new BackgroundPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackgroundImage(new ImageIcon("src\\JAVA_PROJECT\\IMG\\LOGIN.jpeg")); // 이미지 경로를 수정하세요
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

    static class BackgroundPanel extends JPanel {
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

    private boolean insertUser(String name, String id, String password) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String query = "INSERT INTO users (id, username, password) VALUES (?, ?, ?)";
            statement = connection.prepareStatement(query);
            statement.setString(1, id); // id 값 설정
            statement.setString(2, name); // username 값 설정
            statement.setString(3, password); // password 값 설정
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException ex) {
            System.out.println("회원가입 실패: " + ex.getMessage());
            return false;
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    private void openLoginPage() {
        new FancyLoginFrame();
        dispose(); // 회원가입 창 닫기
    }
}
