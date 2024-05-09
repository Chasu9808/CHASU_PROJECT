package JAVA_PROJECT;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class ModifyPageFrame extends JFrame {

    private JTextField titleField;
    private JTextArea contentArea;
    private JButton submitButton;
    private int postId;

    public ModifyPageFrame(int postId, String title, String content) {
        super("글 수정");

        this.postId = postId;

        // 배경 이미지 설정
        ImageIcon backgroundImage = new ImageIcon("src/JAVA_PROJECT/IMG/background.jpeg");
        BackgroundPanel backgroundPanel = new BackgroundPanel(backgroundImage);
        setContentPane(backgroundPanel);

        // 텍스트 추가
        JLabel textLabel = new JLabel("글 수정");
        textLabel.setFont(new Font("Arial", Font.BOLD, 24));
        textLabel.setForeground(Color.WHITE);
        textLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // UI 요소 초기화
        JLabel titleLabel = new JLabel("제목:");
        titleField = new JTextField(title, 30);
        JLabel contentLabel = new JLabel("내용:");
        contentArea = new JTextArea(content, 10, 30);
        JScrollPane contentScrollPane = new JScrollPane(contentArea);
        submitButton = new JButton("EDIT");

        // 수정 완료 버튼 스타일 설정
        submitButton.setFont(new Font("Arial", Font.BOLD, 14));
        submitButton.setForeground(Color.WHITE);
        submitButton.setBackground(new Color(59, 89, 182));
        submitButton.setFocusPainted(false);

        // 수정 완료 버튼 이벤트 처리
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 수정한 글 정보 가져오기
                String modifiedTitle = titleField.getText();
                String modifiedContent = contentArea.getText();

                // 데이터베이스에 수정된 내용 업데이트
                updatePostInDatabase(modifiedTitle, modifiedContent);

                // 수정 페이지 닫기
                dispose();

                // 알림 창 표시
                JOptionPane.showMessageDialog(null, "글 수정이 완료되었습니다.");

                // 게시판 화면으로 이동
                openMainPage();
            }
        });

        // 폼 디자인
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(textLabel, gbc);

        gbc.gridy = 1;
        panel.add(titleLabel, gbc);

        gbc.gridy = 2;
        panel.add(titleField, gbc);

        gbc.gridy = 3;
        panel.add(contentLabel, gbc);

        gbc.gridy = 4;
        panel.add(contentScrollPane, gbc);

        gbc.gridy = 5;
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
    }

    private void updatePostInDatabase(String modifiedTitle, String modifiedContent) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // 데이터베이스 연결
            connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "SCOTT", "TIGER");

            // SQL 쿼리 작성
            String sql = "UPDATE posts SET title = ?, content = ? WHERE post_id = ?";

            // PreparedStatement 생성
            statement = connection.prepareStatement(sql);

            // 파라미터 설정
            statement.setString(1, modifiedTitle);
            statement.setString(2, modifiedContent);
            statement.setInt(3, postId);

            // 쿼리 실행
            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "게시글이 성공적으로 업데이트되었습니다.");
            } else {
                JOptionPane.showMessageDialog(this, "게시글 업데이트에 실패했습니다.");
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

    private void openMainPage() {
        new MainPageFrame().setVisible(true);
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
        SwingUtilities.invokeLater(() -> new ModifyPageFrame(1, "새로운 제목", "새로운 내용"));
    }
    private void openLoginPage() {
        new MainPageFrame();
    }
    
}
