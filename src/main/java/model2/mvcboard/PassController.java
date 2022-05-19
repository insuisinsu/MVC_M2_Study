package model2.mvcboard;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PassController extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Get 요청시 처리
		
		//mode : edit <-- 글 수정, mode : delete <-- 글 삭제
		req.setAttribute("mod", req.getParameter("mod"));
		req.getRequestDispatcher("/mvcboard/Pass.jsp").forward(req, resp);
		
		System.out.println("PassController 작동됨");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 
	}

	
	
	
}
