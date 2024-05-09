package JAVA_PROJECT;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WritePageFrame extends JFrame {

    private JTextField titleField;
    private JTextArea contentArea;
    private JButton submitButton;

    public WritePageFrame() {
        super("글쓰기");

        // 배경 이미지 설정
        ImageIcon backgroundImage = new ImageIcon("src\\JAVA_PROJECT\\IMG\\LOGIN.jpeg"); // 배경 이미지 파일 경로를 입력하세요
        BackgroundPanel backgroundPanel = new BackgroundPanel(backgroundImage);
        setContentPane(backgroundPanel);

        // UI 요소 초기화
        JLabel titleLabel = new JLabel("제목:");
        titleField = new JTextField(30);
        JLabel contentLabel = new JLabel("내용:");
        contentArea = new JTextArea(10, 30);
        JScrollPane contentScrollPane = new JScrollPane(contentArea);
        submitButton = new JButton("WRITE");

        // 작성 완료 버튼 스타일 설정
        submitButton.setFont(new Font("Arial", Font.BOLD, 14));
        submitButton.setForeground(Color.WHITE);
        submitButton.setBackground(new Color(59, 89, 182));
        submitButton.setFocusPainted(false);

        // 작성 완료 버튼 이벤트 처리
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 작성한 글 정보 가져오기
                String title = titleField.getText();
                String content = contentArea.getText();

                // 콘솔에 글 정보 출력 (실제로는 여기서 데이터를 저장하거나 처리해야 합니다)
                System.out.println("제목: " + title);
                System.out.println("내용: " + content);

                // 데이터베이스에 게시글 삽입
                submitPostToDatabase(title, content);

                // 작성 페이지 닫기
                dispose();
            }
        });

        // 폼 디자인
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(titleLabel, gbc);

        gbc.gridy = 1;
        panel.add(titleField, gbc);

        gbc.gridy = 2;
        panel.add(contentLabel, gbc);

        gbc.gridy = 3;
        panel.add(contentScrollPane, gbc);

        gbc.gridy = 4;
        panel.add(submitButton, gbc);

        // JFrame 설정
        backgroundPanel.setLayout(new GridBagLayout());
        GridBagConstraints bgc = new GridBagConstraints();
        bgc.gridx = 0;
        bgc.gridy = 0;
        backgroundPanel.add(panel, bgc);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setSize(500, 620);
    }

    private void submitPostToDatabase(String title, String content) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // 데이터베이스 연결
            connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "SCOTT", "TIGER");

            // SQL 쿼리 작성
            String sql = "INSERT INTO posts (title, content) VALUES (?, ?)";

            // PreparedStatement 생성
            statement = connection.prepareStatement(sql);

            // 파라미터 설정
            statement.setString(1, title);
            statement.setString(2, content);

            // 쿼리 실행
            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "게시글이 성공적으로 등록되었습니다.");
            } else {
                JOptionPane.showMessageDialog(this, "게시글 등록에 실패했습니다.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "데이터베이스 연결에 문제가 있습니다.");
            ex.printStackTrace();
        } finally {
            // 리소스 해제
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

    static class BackgroundPanel extends JPanel {
        private final Image backgroundImage;

        public BackgroundPanel(ImageIcon backgroundImage) {
            this.backgroundImage = backgroundImage.getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(WritePageFrame::new);
    }
}
