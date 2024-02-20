package sec01.ex01;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class MemberDAO {

	//필
//	private final static String driver="oracle.jdbc.OracleDriver";
//	private final static String url="jdbc:oracle:thin:@localhost:1521:orcl";
//	private final static String user = "scott";
//	private final static String pwd = "12341234";
	
	
	private Connection conn;
//	private Statement stmt;
	private PreparedStatement pstmt;
	//PreparedStatement : 미리 컴파일된 SQL 문을 나타내는 개체 //Statement : SQL 문을 나타내는 개체
	// Statement 인터페이스가 DBMS에 전달되는 SQL문은 단순한 문자열이므로 DBMS는 이 문자열을 DBMS가 이해할 수 있도록 컴파일하고 실행함
	// 반면에 PreparedStatement 인터페이스는 컴파일된 SQL문을 DBMS에 전달하여 성능을 향상시킴
	// 또한 실행하려는 SQL문에 "?"을 넣을 수 있습니다. Statement보다 SQL문 작성하기가 더 간단하다. 
	
	private DataSource dataFactory;
	
	//생
	
	public MemberDAO() {
		System.out.println("MemberDAO 객체 생성");
		
		try {
			//1. 연결을 하기 위한 컨텍스트(pro07) 인식을 위한 Context객체
			Context ctx = new InitialContext();
			Context envContext = (Context)ctx.lookup("java:/comp/env");
//			envContext.lookup("jdbc/oracle");
			dataFactory=(DataSource)envContext.lookup("jdbc/oracle");
			//이 코드는 JNDI를 사용하여 "java:/comp/env" 컨텍스트에서 "jdbc/oracle"라는 이름으로 등록된
			//데이터 소스를 얻어와서 dataFactory 변수에 할당하는 것입니다.
			//이렇게 얻어온 데이터 소스를 사용하여 데이터베이스 연결을 설정하고 관리할 수 있습니다.
			
			
		}catch(Exception e) {
			System.out.println("MemberDAO 객체에서 DB 연결 관련 에러");
		}
		
		
	}
	
	//메
	
	// 연결 후 회원 목록을 가져오라는 메소드 (서블릿에서 실행되고 연결된 곳)
	public List<MemberVO> listMembers(){
		
		
		List<MemberVO> list = new ArrayList<MemberVO>();	//회원들의 정보를 기록하기 위한 List컬렉션
		
		try {
			
			
			
//		connDB();
			//2. DB연결 우선
			conn=dataFactory.getConnection();
		
		//3. 연결 객체가(conn) sql을 돌려야 함, sql을 돌리기 위해서는 sql 관련 문구를 처리하는 PreparedStatement Interface사용.
		
		//4. SQL 작성
		String query = "select * from T_MEMBER";	//테이블, 가져올 데이터
		System.out.println("실행한 sql : " + query);
//		ResultSet rs = stmt.executeQuery(query);	//ResultSet : DB에서 가져온 데이터를 읽음	
		//executeQuery : Executes the given SQL statement, which returns a single ResultSet object. (하나의 ResultSet객체를 반환하는 SQL statement를 받아 실행)
		
		pstmt=conn.prepareStatement(query);
		
		ResultSet rs = pstmt.executeQuery();
		
		//rs.next() : Moves the cursor forward one row from its current position
		//테이블 첫째 행부터 한 행씩 다음행으로 이동
		
		while(rs.next()) {	//참고할 행이 있는한 반복
			// 결과테이블(ResultSet)의 칼럼 인식 후 결과값 가져오기
			String id=rs.getString("id");
			System.out.println("나온 id " + id);
			String pwd=rs.getString("pwd");
			String name=rs.getString("name");
			String email=rs.getString("email");
			Date joindate = rs.getDate("joindate");	//getdate는 Date클래스로 타입맞추기
			
			//MemberVO 객체를 만들어서 그 객체에 ResultSet에서 나온 결과를 set해야함 //"MemberVo 객체 필드에 값을 지정 -> list에 저장" 반복
			MemberVO vo = new MemberVO();
			vo.setId(id);
			vo.setPwd(pwd);
			vo.setName(name);
			vo.setEmail(email);
			vo.setJoindate(joindate);
			
			list.add(vo);
			
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		
		}catch(Exception e){
			System.out.println("연결시 에러");
		}
		
		return list;	//DB에서 가져온 데이터들을 list에 저장 후 반환 및 메소드가 끝났으므로 다시 서블릿으로 이동
	}
	
	
	//회원을 추가하라는 메소드
	public void addMember(MemberVO memberVO) {
		try {
			conn=dataFactory.getConnection();
			
			String id=memberVO.getId();
			String pwd = memberVO.getPwd();
			String name = memberVO.getName();
			String email = memberVO.getEmail();
			
			String query="insert into t_member(id,pwd,name,email) VALUES(?,?,?,?)";
			System.out.println("회원 추가 sql문 : " + query);
			
			pstmt=conn.prepareStatement(query);
			
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			pstmt.setString(3, name);
			pstmt.setString(4, email);
			
			
			pstmt.executeUpdate();  // 추가시 executeUpdate
			
			pstmt.close();
		} catch (Exception e) {
			System.out.println("회원추가시 에러");
		}
	}

	
	//DB 연결
//	void connDB(){
//		try {
//			
//			//1. 드라이버 로딩
//			Class.forName(driver);
//			
//			System.out.println("Oracle 드라이버 로딩 성공");
//			
//			//2. 연결, 연결을 위한 DriverManager 클래스 : JDBC Driver를 관리하며 DB와 연결해서 Connection 구현 객체를 생성
//			//The basic service for managing a set of JDBC drivers.
//			
//			conn = DriverManager.getConnection(url, user, pwd);	//DB연결, 아이디, 패스워드 (계정 연결 - 테이블을 가져오기 위함)
//			
//			System.out.println("Connection 생성 성공");
//			
////			pstmt = conn.createStatement();	//SQL의 DDL(테이블 구조)과 DML(테이블 데이터) 실행 시 사용 
//			
//			System.out.println("Statement 생성 성공");
//			
//		} catch (Exception e) {							//DB연동 에러 확인
//			System.out.println("DB 연동 관련 없음");
//			System.out.println("에러 원인 : " + e.getCause().toString());
//		}
//	}
	
}
