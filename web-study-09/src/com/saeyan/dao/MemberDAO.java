package com.saeyan.dao;
//member 테이블과 연동해서 작업하는 회원 테이블에서 정보를 조회하거나 추가, 수정, 삭제 작업을 하는 클래스 DAO
//DAO의 주된 역할은 데이터베이스 데이터를 VO 객체로 얻어오거나 VO 객체에 저장된 값을 데이터 베이스에 추가한다. 
//매번 이런작업을 해야하기에 객체 생성보다는 싱글톤 패턴(Singletone pattern)을 사용해서 클래스를 설계하도록 한다.
//싱글톤 패턴은 인스턴스가 오로지 단 하나만 존재할 수 있도록 클래스를 설계하는 것을 말함. 
//싱글톤은 객체를 메모리에 단 한 번만 올려놓고 시스템 전반에 걸쳐서 특정한 자원(Object, Module, Component)를 공유할 때 사용한다. 
//메모리의 낭비를 막기위해서 사용하는 singletone 패턴!

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.saeyan.dto.MemberVO;


public class MemberDAO {
	//회원정보를 처리하는 DAO인 MemberDAO를 오로지 한개의 인스턴스만 생성해서 사용하는 싱글톤 패턴으로 정의함.
	//자기자신만 인스턴스를 생성 할 수 있도록 private접근자로 생성자 선언!!
	
	private MemberDAO(){
		
	}
	private static MemberDAO instance= new MemberDAO();
	
	//외부에서 값을 수정할 수 없고, 얻어 올 수만 있도록 getter만 만든다.
	public static MemberDAO getInstance(){
		return instance;
	}
	
	//DBCP 커넥션을 얻어오는 메소드 
	public Connection getConnection() throws Exception{
		Connection conn= null;
		Context initContext = new InitialContext();
		Context envContext = (Context) initContext.lookup("java:/comp/env");
		DataSource ds = (DataSource) envContext.lookup("jdbc/myoracle");
		conn = ds.getConnection();
		return conn;
	}
	
	//데이터 접근이 목적인 클래스가 MemberDAO 
	//데이터 베이스에 들어있는 데이터를 어떻게 이용할지에 초점을 맞추어 설계하는 클래스. 
	//데이터 베이스에 저장된 정보를 얻어오거나 전달하기 위해서는 테이블에 저장된 데이터를 VO 객체 단위로 저장해서 사용한다. 
	//데이터베이스에 저장된 회원 정보는 새롭게 추가되거나 조회되고 수정되어야하는데, 이런 작업은 모두 DAO클래스에서 한다. 
	//위의 작업들이 원활하게 이루어지기 위한 메소드.
	
	
	//사용자 인증시 사용하는 메소드. 
	//테이블에서 아이디와 암호를 비교해서 해당 아이디가 존재하지 않으면 -1, 
	//아이디만 일치하고 암호가 다르면 0, 
	//모두 일치하면 1 리턴함.
	public int userCheck(String userid, String pwd){
		int result = -1;
		String sql = "select pwd from member where userid=?";
		Connection conn = null; 
		PreparedStatement pstmt = null; 
		ResultSet rs = null; 
		
		try{
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			rs = pstmt.executeQuery();
			if(rs.next()){
				if(rs.getString("pwd") != null && rs.getString("pwd").equals(pwd)){
					result = 1;
				}else{
					result = 0; 
				}
			}else{
				result = -1;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(Exception e){
				e.printStackTrace();
			}
		}
		return result;
	}
	
	//테이블에서 아이디로 해당 회원을 찾아 회원 정보를 가져온다. 
	public MemberVO getMember(String userid){
		
		MemberVO mVo = null;
		
		String sql = "select * from member where userid=?";
		Connection conn = null;
		PreparedStatement pstmt = null; 
		ResultSet rs = null; 
		
		try{
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			rs = pstmt.executeQuery();
			if(rs.next()){
				mVo = new MemberVO();
				mVo.setName(rs.getString("name"));
				mVo.setUserid(rs.getString("userid"));
				mVo.setPwd(rs.getString("pwd"));
				mVo.setEmail(rs.getString("email"));
				mVo.setPhone(rs.getString("phone"));
				mVo.setAdmin(rs.getInt("admin"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return mVo;
	}
	
	
	
	//회원가입 시 아이디 중복을 확인할 때 사용. 
	//해당 아이디가 있으면 1, 없으면 -1리턴.
	public int confirmID(String userid){
		int result = -1; 
		String sql="select userid from member where userid=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			rs = pstmt.executeQuery();
			if(rs.next()){
				result = 1; 
			}else{
				result =-1;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return result;
	}
	
	
	
	//매개 변수로 받은 VO 객체를 member 테이블에 삽입한다. 
	//회원정보를 DB에 추가하기 위한 메소드 추가하기.
	public int insertMember(MemberVO mVo){
		int result = -1; 
		String sql="insert into member values(?,?,?,?,?,?)";
		Connection conn = null; 
		PreparedStatement pstmt = null; 
		try{
			conn = getConnection(); 
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mVo.getName());
			pstmt.setString(2, mVo.getUserid());
			pstmt.setString(3, mVo.getPwd());
			pstmt.setString(4, mVo.getEmail());
			pstmt.setString(5, mVo.getPhone());
			pstmt.setInt(6, mVo.getAdmin());
			result = pstmt.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return result;
	}
	
	
	//매개 변수로 받은 VO 객체 내의 아이디로 member 테이블에서 검색해서
	//VO 객체에 저장된 정보로 회원 정보를 수정한다. 
	public int updateMember(MemberVO mVo){
		int result = -1; 
		String sql = "update member set pwd=?, email=?"
				+"phone=?, admin=? where userid=?";
		
		Connection conn = null; 
		PreparedStatement pstmt = null; 
		try{
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mVo.getPwd());
			pstmt.setString(2, mVo.getEmail());
			pstmt.setString(3, mVo.getPhone());
			pstmt.setInt(4, mVo.getAdmin());
			pstmt.setString(5, mVo.getUserid());
			result = pstmt.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return result;
	}
}
