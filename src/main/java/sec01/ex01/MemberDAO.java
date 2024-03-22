package sec01.ex01;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO {

	private final static String driver="oracle.jdbc.OracleDriver";
	private Statement stmt;	
	private Connection con;
	String url = "jdbc:oracle:thin:@localhost:1521:orcl";
	String user = "scott";
	String pw = "12341234";
//	
//	
	public List<MemberVO> listMembers(){
		
		List<MemberVO> list = new ArrayList<MemberVO>();
		
		try {
			connDB();
			String query = "select * from t_member";
			System.out.println(query);
			ResultSet rs = stmt.executeQuery(query);
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
			stmt.close();
			con.close();
			
		} catch (Exception e) {
			e.getMessage();
		}
		
		return list;
	}
	
	private void connDB() {
		try {
			Class.forName(driver);	
			System.out.println("Oracle 드라이버 로딩 성공");
			con = DriverManager.getConnection(url, user, pw);	
			System.out.println("Connection 생성 성공");
			stmt = con.createStatement();
			System.out.println("Statement 생성 성공");
			
		} catch (Exception e) {
			e.getMessage();
		}
	}
	
	
	
}
