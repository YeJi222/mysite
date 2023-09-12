package com.poscodx.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.poscodx.mysite.vo.BoardVo;

public class BoardDao {

	public Boolean insert(BoardVo vo) {
		boolean result = false;

		Connection conn = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;

		try {
			conn = getConnection();
			
			String sql1 = "select count(*) from board;";
			
			pstmt1 = conn.prepareStatement(sql1);
			rs1 = pstmt1.executeQuery();
			int rowCount = 0;
			if(rs1.next()) {
				rowCount = rs1.getInt(1);
			}
			// System.out.println(rowCount);
			
			// 새 글인 경우 
			int next_gNo = 0;
			if(rowCount == 0) { // board 테이블의 row가 하나도 없을 때 	
				next_gNo = 1;
			} else { // board data가 하나 이상 있을 때 
				String sql2 = "select max(g_no)+1 from board;";
				pstmt2 = conn.prepareStatement(sql2);
				rs2 = pstmt2.executeQuery();
				
				if(rs2.next()) {
					next_gNo = rs2.getInt(1);
				}
			}
			// System.out.println(next_gNo);

			String sql3 = "insert into board values(null, ?, ?, 0, now(), ?, 1, 0)";
			pstmt3 = conn.prepareStatement(sql3);
			pstmt3.setString(1, vo.getTitle());
			pstmt3.setString(2, vo.getContents());
			pstmt3.setInt(3, next_gNo);

			int count = pstmt3.executeUpdate();

			result = count == 1;

		} catch (SQLException e) {
			System.out.println("Error:" + e);
		} finally {
			try {
				if(pstmt1 != null) {
					pstmt1.close();
				}
				if(pstmt2 != null) {
					pstmt2.close();
				}
				if(pstmt3 != null) {
					pstmt3.close();
				}
				if(rs1 != null) {
					rs1.close();
				}
				if(rs2 != null) {
					rs2.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}
	
	public List<BoardVo> findAll() {
		List<BoardVo> result = new ArrayList<>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			String sql =
				"select no, title, contents, hit," +
				" date_format(reg_date, '%Y-%m-%d %H:%i:%s')," + 
				" g_no, o_no, depth" + 
				" from board" + 
				" order by g_no desc, o_no asc;";
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();
			while(rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				Long hit = rs.getLong(4);
				String regDate = rs.getString(5);
				Long g_no = rs.getLong(6);
				Long o_no = rs.getLong(7);
				Long depth = rs.getLong(8);

				BoardVo vo = new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setHit(hit);
				vo.setRegDate(regDate);
				vo.setG_no(g_no);
				vo.setO_no(o_no);
				vo.setDepth(depth);

				result.add(vo);
			}

		} catch (SQLException e) {
			System.out.println("Error:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}

				if(pstmt != null) {
					pstmt.close();
				}

				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		

		return result;
	}
	
	private Connection getConnection() throws SQLException {
		Connection conn = null;

		try {
			Class.forName("org.mariadb.jdbc.Driver");

			String url = "jdbc:mariadb://192.168.64.9:3307/webdb?charset=utf8";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} 

		return conn;
	}
}
