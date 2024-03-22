package sec02.ex02;

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

//@WebServlet("/member")
public class MemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void init(ServletConfig config) throws ServletException {
		System.out.println("init메소드 호출");
	}

	public void destroy() {
		System.out.println("destroy 메소드 호출");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	doHandle(request, response);
	}
	

	void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		MemberDAO dao = new MemberDAO();
		PrintWriter out = response.getWriter();
		
		String command = request.getParameter("command");
		
		if(command!=null && command.equals("addMember")) {
			String id = request.getParameter("id");
			String pwd = request.getParameter("pwd");
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			
			MemberVO vo = new MemberVO();
			vo.setId(id);
			vo.setPwd(pwd);
			vo.setName(name);
			vo.setEmail(email);
			
			dao.addMember(vo);
		}
		else if(command!=null && command.equals("delMember")) {
			String id = request.getParameter("id");
			dao.delMember(id);
		}
		
	
		List<MemberVO> list = dao.listMembers();
		System.out.println("MemberVO 리스트 사이즈" + list.size());
		
		out.print("<html><body>"
				+ "<table border=1><tr align='center' bgcolor='lightgreen'>"
				+ "<td>아이디</td><td>비밀번호</td><td>이름</td><td>이메일</td><td>가입일</td></tr>");
		
		for(int i=0; i < list.size(); i++) {	//MemberVO vo = list.get(i); 로 받아서 vo.getId()를 하니 적용이 안되었음. 리스트의 모든 내용을 MemberVO vo에 담아서 뽑아내는게 안되는듯.
			String id = list.get(i).getId();
			String pwd = list.get(i).getPwd();
			String name = list.get(i).getName();
			String email = list.get(i).getEmail();
			Date joinDate = list.get(i).getJoinDate();
			
			out.print("<tr><td>" + id + "</td>" + "<td>" + pwd + "</td>" + "<td>" + name + "</td>" + "<td>" + email + "</td>" + "<td>" + joinDate + "</td>"
					+ "<td><a href='http://localhost:8080/pro07/member?command=delMember&id="+ id +"'>삭제</a></td></tr>");
		}
		
		out.print("</table></body></html>"
				+ "<a href='http://localhost:8080/pro07/memberForm.html'>새 회원 등록하기</a>");
		
		out.close();
		
		}
	
}
