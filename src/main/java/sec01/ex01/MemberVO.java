package sec01.ex01;

import java.sql.Date;

<<<<<<< HEAD
import lombok.Data;

@Data
public class MemberVO {

=======

import lombok.Data;


@Data
public class MemberVO {


>>>>>>> 2d3df45dc9bbe51bb370515b263c88b13c645f44
	private String id;
	private String pwd;
	private String name;
	private String email;
<<<<<<< HEAD
	private Date joinDate;
	
	MemberVO(){
		System.out.println("MemberVO 생성자 실행");
	}
	
=======
	private Date joindate;
	
	MemberVO(){
		System.out.println("MemberVO 기본 생성자 호출됨");
	}
	
	
	
	
>>>>>>> 2d3df45dc9bbe51bb370515b263c88b13c645f44
}
