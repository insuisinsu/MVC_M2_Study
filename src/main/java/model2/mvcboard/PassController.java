package model2.mvcboard;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PassController extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Get ��û�� ó��
		
		//mode : edit <-- �� ����, mode : delete <-- �� ����
		req.setAttribute("mod", req.getParameter("mod"));
		req.getRequestDispatcher("/mvcboard/Pass.jsp").forward(req, resp);
		
		System.out.println("PassController �۵���");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 
	}

	
	
	
}