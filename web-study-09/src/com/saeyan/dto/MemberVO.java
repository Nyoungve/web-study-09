package com.saeyan.dto;
// Value Object: VO(자바빈 값만을 저장해서...)  회원테이블의 정보를 자바에서 얻어오기 전에 회원 정보를 저장할 공간을 위한 준비과정이다.
// Data Transfer Object : DTO 라고 부르기도 한다. (데이터 전달 목적)
// 자바빈 == VO == DTO 
//회원정보를 하나의 묶음으로 관리하기 위해 나온 자바빈이 데이터 베이스와 접목할 경우에는 VO 라고 한다. 

public class MemberVO {
	//숨겨진 데이터(필드)
	private String name;
	private String userid;
	private String pwd;
	private String email;
	private String phone;
	private int admin;
	
	//공개된 메소드를 통해서 외부에서 들어온 정보를 클래스에 저장시키고, 클래스의 정보를 외부에서 조회할 수 있다. 
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getAdmin() {
		return admin;
	}
	public void setAdmin(int admin) {
		this.admin = admin;
	}
	
	//자동으로 오버라이딩된 toString()
	@Override
	public String toString() {
		return "MemberVO [name="+name+", userid="+userid+", pwd="+pwd
				+", email="+email+", phone="+phone+", admin="+admin+"]";
	}
}
