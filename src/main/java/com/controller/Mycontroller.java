package com.controller;

import java.io.IOException;

import com.model.LoginBean;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Mycontroller extends HttpServlet {  //Controller 세팅

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Client 에서 Get 방식으로 요청할 경우 처리하는 블락
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Client 에서 Post 방식으로 요청할 경우 처리하는 블락
		
		//client 의 뷰 페이지로 전송할 contentType 을 정의
		response.setContentType("text.html; charset = utf-8");
		
		String id = request.getParameter("id");
		String password = request.getParameter("passwd");
		
		LoginBean bean = new LoginBean();
		bean.setId(id);
		bean.setPassword(password);
		
		request.setAttribute("bean", bean);
		
		//password 가 "admin" 이면, status 가 true
		boolean status = bean.validate();
		
		if(status) {
			RequestDispatcher rd = request.getRequestDispatcher("mvc_success.jsp");
			rd.forward(request, response);
		}else {
			RequestDispatcher rd = request.getRequestDispatcher("mvc_error.jsp");
			rd.forward(request, response);			
		}
		
		
	}	
	


}
