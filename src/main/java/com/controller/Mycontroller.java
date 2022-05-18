package com.controller;

import java.io.IOException;

import com.model.LoginBean;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Mycontroller extends HttpServlet {  //Controller ����

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Client ���� Get ������� ��û�� ��� ó���ϴ� ���
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Client ���� Post ������� ��û�� ��� ó���ϴ� ���
		
		//client �� �� �������� ������ contentType �� ����
		response.setContentType("text.html; charset = utf-8");
		
		String id = request.getParameter("id");
		String password = request.getParameter("passwd");
		
		LoginBean bean = new LoginBean();
		bean.setId(id);
		bean.setPassword(password);
		
		request.setAttribute("bean", bean);
		
		//password �� "admin" �̸�, status �� true
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
