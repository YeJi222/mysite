package com.poscodx.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.mysite.vo.PageVo;

@Repository
public class BoardRepository {
	@Autowired
	private SqlSession sqlSession;
	
	@Autowired
	private DataSource dataSource;
 
	public Boolean insert(BoardVo vo) {
		boolean result = false;

		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		PreparedStatement pstmt4 = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;

		try {
			conn = dataSource.getConnection();
			
			String sql1 = "select count(*) from board;";
			
			pstmt1 = conn.prepareStatement(sql1);
			rs1 = pstmt1.executeQuery();
			Long next_gNo = (long) 0;
			Long next_oNo = (long) 1;
			Long next_depth = (long) 0;
			
			int rowCount = 0;
			if(rs1.next()) {
				rowCount = rs1.getInt(1);
			}	
			
			if(vo.getNo() == null) { // 새 글인 경우 
				if(rowCount == 0) { // board 테이블의 row가 하나도 없을 때 	
					next_gNo = (long) 0;
				} else { // board data가 하나 이상 있을 때 
					String sql2 = "select max(g_no)+1 from board;";
					pstmt2 = conn.prepareStatement(sql2);
					rs2 = pstmt2.executeQuery();
					
					if(rs2.next()) {
						next_gNo = rs2.getLong(1);
					}
				}
			} else { // 답글인 경우 
				// view 기준 
				next_gNo = vo.getG_no();
				next_oNo = vo.getO_no() + 1; // 기준 view의 order_no + 1
				next_depth = vo.getDepth() + 1;
				
				// 기준 view의 g_no, o_no, depth 그대로
				// 새로 추가할 o_no 는 기준 view의 o_no + 1
				// 계층에서 그 이하(o_no 숫자가 기준 view보다 큰 경우) o_no + 1 
				String sql3 = "update board set o_no=o_no+1 where g_no=? and o_no>=?;";
				pstmt3 = conn.prepareStatement(sql3);
				pstmt3.setLong(1, next_gNo);
				pstmt3.setLong(2, next_oNo);
				pstmt3.executeQuery();
			}
			
			String setTime = "SET time_zone='+09:00';";
			pstmt = conn.prepareStatement(setTime);
			pstmt.executeQuery();
			
			String sql4 = "insert into board values(null, ?, ?, 0, now(), ?, ?, ?, ?)";
			pstmt4 = conn.prepareStatement(sql4);
			pstmt4.setString(1, vo.getTitle());
			pstmt4.setString(2, vo.getContents());
			pstmt4.setLong(3, next_gNo);
			pstmt4.setLong(4, next_oNo);
			pstmt4.setLong(5, next_depth);
			pstmt4.setLong(6, vo.getUser_no());

			int count = pstmt4.executeUpdate();

			result = count == 1;
		} catch (SQLException e) {
			System.out.println("Error:" + e);
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(pstmt1 != null) {
					pstmt1.close();
				}
				if(pstmt2 != null) {
					pstmt2.close();
				}
				if(pstmt3 != null) {
					pstmt3.close();
				}
				if(pstmt4 != null) {
					pstmt4.close();
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
	
	public BoardVo getInfoByNo(long no) {
		return sqlSession.selectOne("board.getInfoByNo", no);
	}
	
	public void update(BoardVo vo) {
		sqlSession.update("board.update", vo);
	}
	
	public void addHit(long no) {
		sqlSession.update("board.addHit", no);
	}
	
	public boolean deleteByNoAndPassword(Long no, String password) {
		boolean result = false;

		Connection conn = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;

		try {
			conn = dataSource.getConnection();
			
			String sql1 = "select count(*) from user join board"
					+ " where user.no = board.user_no and"
					+ " board.no=? and password=password(?)";
			
			pstmt1 = conn.prepareStatement(sql1);
			pstmt1.setLong(1, no);
			pstmt1.setString(2, password);
			
			rs = pstmt1.executeQuery();
			
			int successCount = 0;
			if(rs.next()) {
				successCount = rs.getInt(1);
			}
			
			if(successCount == 0) { // 패스워드 불일치 
				System.out.println("패스워드 불일치(삭제 불가)");
 			} else { // 패스워드 일치 - delete 가능 
 				String sql2 = "delete from board where no=?";
 				pstmt2 = conn.prepareStatement(sql2);
 				pstmt2.setLong(1, no);
 	
 				int count = pstmt2.executeUpdate();
 	
 				result = count == 1;
			}	
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
				if(rs != null) {
					rs.close();
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
	
	public int getTotalPost() {
		return sqlSession.selectOne("board.getTotalPost");
	}
	
	public List<BoardVo> pagePostList(PageVo pageVo) {
		List<BoardVo> result = new ArrayList<>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = dataSource.getConnection();

			String sql =
				"SELECT t1.*, t2.name"
				+ " FROM board t1"
				+ " JOIN user t2 ON t1.user_no = t2.no"
				+ " ORDER BY g_no DESC, o_no ASC"
				+ " LIMIT ?, ?;";
			pstmt = conn.prepareStatement(sql);
			
			Long startIdx = (long) ((pageVo.getCurPageNo() - 1) * pageVo.getPostSize());
			Long size = (long) pageVo.getPostSize();
			
			pstmt.setLong(1, startIdx);
			pstmt.setLong(2, size);

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
				String writer = rs.getString(10);
				
				BoardVo vo = new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setHit(hit);
				vo.setRegDate(regDate);
				vo.setG_no(g_no);
				vo.setO_no(o_no);
				vo.setDepth(depth);
				vo.setWriter(writer);

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
}
