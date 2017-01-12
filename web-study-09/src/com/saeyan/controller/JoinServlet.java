package com.saeyan.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.saeyan.dao.MemberDAO;
import com.saeyan.dto.MemberVO;

//로그인 페이지의 <회원가입> 버튼이 클릭되면 join.do가 요청되도록 서블릿을 정의해 주어야 한다. 
@WebServlet("/join.do")
public class JoinServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//join.do가 get 방식으로 요청되면 회원가입을 위한 폼인 join.jsp를 띄운다.
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("member/join.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//회원정보를 데이터 베이스에 추가하는 서블릿 클래스 
		request.setCharacterEncoding("UTF-8");
		
		String name=request.getParameter("name");
		String userid=request.getParameter("userid");
		String pwd=request.getParameter("pwd");
		String email=request.getParameter("email");
		String phone=request.getParameter("phone");
		String admin=request.getParameter("admin");
		
		MemberVO mVo= new MemberVO();
		mVo.setName(name);
		mVo.setUserid(userid);
		mVo.setPwd(pwd);
		mVo.setEmail(email);
		mVo.setPhone(phone);
		mVo.setAdmin(Integer.parseInt(admin));
		
		
		MemberDAO mDao = MemberDAO.getInstance();
		int result=mDao.insertMember(mVo);
		
		HttpSession session= request.getSession();
		
		if(result==1){
			session.setAttribute("userid", mVo.getUserid());
			request.setAttribute("message","회원 가입에 성공했습니다.");
		}else{
			request.setAttribute("message","회원 가입에 실패했습니다.");
		}
		
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("member/login.jsp");
		dispatcher.forward(request, response);
	
	}
	
	

}
