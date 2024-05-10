package JAVA_PROJECT;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class MainPageFrame extends JFrame {

    private DefaultTableModel tableModel;

    public MainPageFrame() {
        super("게시판");

        // 배경 이미지 설정
        ImageIcon backgroundImage = new ImageIcon("src\\JAVA_PROJECT\\IMG\\LOGIN.jpeg"); // 배경 이미지 파일의 경로를 설정하세요
        JLabel backgroundLabel = new JLabel(backgroundImage);

        // 게시물 목록을 표시하는 테이블 생성
        String[] columnNames = {"번호", "제목", "내용", "작성일"};
        Object[][] data = {};
        tableModel = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(tableModel);
        table.setPreferredScrollableViewportSize(new Dimension(500, 200));
        JScrollPane scrollPane = new JScrollPane(table);

        // 글쓰기 버튼
        JButton writeButton = new JButton("글쓰기");
        writeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 글쓰기 페이지로 넘어감
            	dispose();
                openWritePage();
            }
        });

        // 삭제 버튼
        JButton deleteButton = new JButton("삭제");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 선택된 행 삭제
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    // 테이블에서 선택된 행 삭제
                    tableModel.removeRow(selectedRow);

                    // 선택된 행의 ID 가져오기
                    int postId = (int) table.getValueAt(selectedRow, 0);

                    // 내부 DB에서 해당 행 삭제
                    deletePostFromDatabase(postId);
                } else {
                    JOptionPane.showMessageDialog(MainPageFrame.this, "삭제할 행을 선택해주세요.", "알림", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // 수정 버튼
        JButton modifyButton = new JButton("수정");
        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 선택된 행 수정 페이지로 넘어감
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    // 선택된 행의 데이터 가져오기
                    int postId = (int) table.getValueAt(selectedRow, 0);
                    String title = (String) table.getValueAt(selectedRow, 1);
                    String content = (String) table.getValueAt(selectedRow, 2);

                    // 수정 페이지 열기
                    openModifyPage(postId, title, content);

                    // 메인 페이지 닫기
                    dispose(); // 현재 프레임을 닫음
                } else {
                    JOptionPane.showMessageDialog(MainPageFrame.this, "수정할 행을 선택해주세요.", "알림", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // JFrame 설정
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setSize(500, 620);

        // 배경 이미지를 최상단에 추가
        getContentPane().setLayout(null);
        backgroundLabel.setBounds(0, 0, backgroundImage.getIconWidth(), backgroundImage.getIconHeight());
        getContentPane().add(backgroundLabel);

        // 게시물 목록 테이블 위치 설정
        scrollPane.setBounds(50, 50, 400, 410);
        backgroundLabel.add(scrollPane);

        // 버튼 위치 설정
        writeButton.setBounds(60, 520, 100, 50);
        deleteButton.setBounds(200, 520, 100, 50);
        modifyButton.setBounds(340, 520, 100, 50);
        getContentPane().add(writeButton);
        getContentPane().add(deleteButton);
        getContentPane().add(modifyButton);

        // 게시물 조회
        retrievePostsFromDatabase();

        // 프레임을 보이도록 설정
        setVisible(true);
    }

    private void openWritePage() {
        // 글쓰기 페이지 열기
        new WritePageFrame().setVisible(true);
    }

    private void openModifyPage(int postId, String title, String content) {
        // 수정 페이지 열기
        new ModifyPageFrame(postId, title, content).setVisible(true);
    }

    private void deletePostFromDatabase(int postId) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // Oracle JDBC 드라이버 로드
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // 데이터베이스 연결 설정
            String url = "jdbc:oracle:thin:@localhost:1521:XE"; // 데이터베이스 URL
            String username = "SCOTT"; // 사용자명
            String password = "TIGER"; // 비밀번호
            connection = DriverManager.getConnection(url, username, password);

            // SQL 쿼리 작성 및 실행 (해당 postId에 해당하는 행 삭제)
            String sql = "DELETE FROM posts WHERE post_id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, postId);
            statement.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            // 오류 처리
        } finally {
            // 리소스 해제
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void retrievePostsFromDatabase() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // Oracle JDBC 드라이버 로드
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // 데이터베이스 연결 설정
            String url = "jdbc:oracle:thin:@localhost:1521:XE"; // 데이터베이스 URL
            String username = "SCOTT"; // 사용자명
            String password = "TIGER"; // 비밀번호
            connection = DriverManager.getConnection(url, username, password);

            // SQL 쿼리 작성 및 실행
            String sql = "SELECT post_id, title, content, created_at FROM posts";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            // 결과 처리
            while (resultSet.next()) {
                int postId = resultSet.getInt("post_id");
                String title = resultSet.getString("title");
                String content = resultSet.getString("content");
                String createdAt = resultSet.getString("created_at");

                // 테이블에 추가
                tableModel.addRow(new Object[]{postId, title, content, createdAt});
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            // 오류 처리
        } finally {
            // 리소스 해제
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainPageFrame::new);
    }
}
