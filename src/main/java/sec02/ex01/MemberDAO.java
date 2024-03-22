package sec02.ex01;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class MemberDAO {

//	private final static String driver="oracle.jdbc.OracleDriver";
	private PreparedStatement pstmt;	
	private Connection con;
//	String url = "jdbc:oracle:thin:@localhost:1521:orcl";
//	String user = "scott";
//	String pw = "12341234";
	private DataSource dataFactory;
	
	public MemberDAO() {
		System.out.println("MemberDAO 생성자 실행");
		//MemberDAO 객체 초기화(생성자)시에 커넥션 풀 정보를 불러오게 해라!!! - JNDI
		try {
			Context ctx = new InitialContext();
			Context envContext = (Context)ctx.lookup("java:/comp/env");
			dataFactory = (DataSource) envContext.lookup("jdbc/oracle");
			System.out.println("JNDI. 커넥션 풀 연결");
			
		} catch (Exception e) {
			System.out.println("DB 연결을 위한 MemberDAO 객체 생성시 에러");
			e.printStackTrace();
		}
		
	}
	
	//회원목록 조회
	public List<MemberVO> listMembers(){
		
		List<MemberVO> list = new ArrayList<MemberVO>();
		
		try {
			System.out.println("listMembers 메소드 실행");
//			connDB();
			con = dataFactory.getConnection();
			String query = "select * from t_member";
			System.out.println("prepareStatement: " + query);
			pstmt = con.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String id = rs.getString("id");
				String pwd = rs.getString("pwd");
				String name = rs.getString("name");
				String email = rs.getString("email");
				Date joinDate = rs.getDate("joindate");
				
				MemberVO vo = new MemberVO();
				vo.setId(id);
				vo.setPwd(pwd);
				vo.setName(name);
				vo.setEmail(email);
				vo.setJoinDate(joinDate);
				
				list.add(vo);
			}
			rs.close();
			pstmt.close();
			con.close();
			
		} catch (Exception e) {
			System.out.println("SQL 실행시 에러");
			e.printStackTrace();
		}
		
		return list;
	}
	
//	private void connDB() {
//		try {
//			Class.forName(driver);	
//			System.out.println("Oracle 드라이버 로딩 성공");
//			con = DriverManager.getConnection(url, user, pw);	
//			System.out.println("Connection 생성 성공");
//			
////			stmt = con.createStatement();	//SQL의 DDL(테이블 구조)과 DML(테이블 데이터) 실행 시 사용 
////			System.out.println("Statement 생성 성공");
//			
//		} catch (Exception e) {
//			e.getMessage();
//		}
//	}
	
	
	
}
