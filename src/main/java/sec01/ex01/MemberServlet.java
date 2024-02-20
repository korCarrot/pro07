package sec01.ex01;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/member")
public class MemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void init(ServletConfig config) throws ServletException {
		System.out.println("MemberServlet 초기화됨");		//실행 - 서블릿 초기화
	}

	public void destroy() {
		System.out.println("MemberServlet 소멸됨");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	doHandle(request, response);	
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);	
		}
	
	protected void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw = response.getWriter();
		// MemberServlet 객체가 연결하기 위한 MemberDAO 객체를 포함 관계로 가지고 있어야 함
		
		String command = request.getParameter("command");
		System.out.println("커맨드" + command);
		
		MemberDAO dao = new MemberDAO();
		if(command!=null && command.equals("addMember")) {
			//회원가입하는 코드
			//DAO에서 가입하는 코드 호출
			
			//memberForm.html에서 파라미터이름을 통한 값 가져오기
			
			String id = request.getParameter("id");
			String pwd = request.getParameter("pwd");
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			
			System.out.println("가입정보" + id+pwd+name+email);
			
			//멤버 객체에 저장
			MemberVO vo = new MemberVO();
			vo.setId(id);
			vo.setPwd(pwd);
			vo.setName(name);
			vo.setEmail(email);

			dao.addMember(vo);
			
		}else if(command!=null && command.equals("delMember")) {
			System.out.println("여기는 삭제시 코드");
			String id=request.getParameter("id");
			System.out.println("삭제 id" + id);
			dao.delMember(id);
		}
		
		
//		MemberDAO dao = new MemberDAO();	//MemberDAO 생성자 - 객체 연결된 것 확인
		List<MemberVO> list = dao.listMembers();	//MemberDAO객체의 listMebers()메소드 실행한 리턴값을 받아옴.
		System.out.println(list);
		
		pw.write("<!DOCTYPE html>\r\n"	//html의 th부분과 css작성
				+ "<html>\r\n"
				+ "<head>\r\n"
				+ "	<meta charset=\"utf-8\">	\r\n"
				+ "	<title></title>\r\n"
				+ "    <style>\r\n"
				+ "        table, tr,  th, td {\r\n"
				+ "            border: solid 2px black;\r\n"
				+ "            border-collapse: collapse;\r\n"
				+ "            padding: 8px;\r\n"
				+ "\r\n"
				+ "        }\r\n"
				+ "th {\r\n"
				+ " background-color: rgb(142, 238, 142);\r\n}"
				+ "    </style>\r\n"
				+ "</head>\r\n"
				+ "<body>\r\n"
				+ "<table>\r\n"
				+ "    <tr><th>아이디</th><th>비밀번호</th><th>이름</th><th>이메일</th><th>가입일</th></tr>\r\n");
		
		for(int i=0; i < list.size(); i++) {
		String id = list.get(i).getId();
		String pwd = list.get(i).getPwd();
		String name = list.get(i).getName();
		String email = list.get(i).getEmail();
		Date joinDate = list.get(i).getJoindate();
		
		//for문으로 반복하며 인덱스를 통해 list의 값들을 저장
		
		pw.write("<tr><td>" + id + "</td><td>"+ pwd +"</td><td>"+ name +"</td><td>"+ email +"</td><td>"
				+ joinDate +"</td>"
						+ "<td><a href='http://localhost:8090/pro07/member?command=delMember&id="+ id + "'>삭제</a></td></tr>");
		
		}
		pw.write("</table>\r\n"
				+ "</body>\r\n"
				+ "</html>");	//html(테이블) 서식마무리
		

//		pw.write(list.get(0).getId());
//		pw.write(list.toString());
		pw.close();
	}
	}

