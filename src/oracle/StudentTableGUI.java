package oracle;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class StudentTableGUI {
    // 오라클 DB에서 데이터를 가져와 DefaultTableModel로 변환
    private static DefaultTableModel getStudentData() {
        // 테이블 모델 생성 (열 이름 설정)
        DefaultTableModel model = new DefaultTableModel(new String[]{"학번", "이름", "나이", "전공", "연락처", "등록일"}, 0);

        Connection conn = OracleDatabaseConnection.connect();
        if (conn != null) {
            String query = "SELECT * FROM 학생";
            try {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                // ResultSet에서 데이터 가져와 모델에 추가
                while (rs.next()) {
                    int 학번 = rs.getInt("학번");
                    String 이름 = rs.getString("이름");
                    int 나이 = rs.getInt("나이");
                    String 전공 = rs.getString("전공");
                    String 연락처 = rs.getString("연락처");
                    Date 등록일 = rs.getDate("등록일");

                    // 모델에 행 추가
                    model.addRow(new Object[]{학번, 이름, 나이, 전공, 연락처, 등록일});
                }

                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return model;
    }

    public static void main(String[] args) {
        // GUI 프레임 생성
        JFrame frame = new JFrame("학생 테이블");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);

        // JTable에 데이터 설정
        JTable table = new JTable(getStudentData());

        // JScrollPane으로 JTable 감싸기 (스크롤 지원)
        JScrollPane scrollPane = new JScrollPane(table);

        // 프레임에 JScrollPane 추가
        frame.add(scrollPane, BorderLayout.CENTER);

        // 프레임 표시
        frame.setVisible(true);
    }
}

