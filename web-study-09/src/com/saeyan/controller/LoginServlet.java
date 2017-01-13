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

@WebServlet("/login.do")
public class LoginServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String url="member/login.jsp";
		
		HttpSession session = request.getSession();
		
		if(session.getAttribute("loginUser") != null){ //이미 로그인 된 사용자이면
			url="main.jsp";//메인페이지로 이동한다.
		}
	
		//login.jsp 페이지로 forward 방식으로 이동!
		RequestDispatcher dispatcher = request
				.getRequestDispatcher(url);
		dispatcher.forward(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//회원 인증을 위한 서블릿 클래스 post로 처리 
		String url ="member/login.jsp";
		
		String userid= request.getParameter("userid");
		String pwd = request.getParameter("pwd");
		
		MemberDAO mDao = MemberDAO.getInstance();
		int result = mDao.userCheck(userid, pwd); //MemberDAO 클래스 에서 userCheck()메소드를 가져와 리턴값을 result에 넣어준다.
		
		if(result == 1 ){ //로그인 처리 완료할 경우 1리턴
			MemberVO mVo=mDao.getMember(userid);
			HttpSession session = request.getSession(); //세션에다가 회원정보를 저장해두고 어느페이지에서든 사용한다.
			session.setAttribute("loginUser", mVo);
			request.setAttribute("message", "회원 가입에 성공했습니다.");
			url="main.jsp";//로그인성공하면 main.jsp로 이동
		}else if(result==0){ //비밀번호가 맞지 않을 경우 0리턴
			request.setAttribute("message", "비밀번호가 맞지 않습니다.");
		}else if(result==-1){ //아이디가 존재하지 않을 경우 -1리턴
			request.setAttribute("message","존재하지 않는 회원입니다.");
		}
		
		RequestDispatcher dispatcher = request
				.getRequestDispatcher(url);
		dispatcher.forward(request, response);
		
	}

}
