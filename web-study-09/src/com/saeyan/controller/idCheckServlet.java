package com.saeyan.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.saeyan.dao.MemberDAO;

@WebServlet("/idCheck.do")
public class idCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userid = request.getParameter("userid");
		
		MemberDAO mDao = MemberDAO.getInstance();
		
		int result = mDao.confirmID(userid);
		// MemberDAO클래스에서 아이디중복체크하는메소드 confirm을 들고옴 해당아이디 있으면 리턴값 1, 없으면 -1을 리턴.
		
		request.setAttribute("userid", userid);
		request.setAttribute("result", result);
		
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("member/idcheck.jsp");
		dispatcher.forward(request, response);
	}
}
