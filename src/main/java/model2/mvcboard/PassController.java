package model2.mvcboard;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import utils.JSFunction;

@WebServlet("/mvcboard/pass.do")
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
		// Post 요청시 처리
		
		//pass.jsp(뷰) 에서 전송한 변수 3 개
		String idx = req.getParameter("idx");
		String mode = req.getParameter("mode");
		String pass = req.getParameter("pass");
		
		//비밀번호 확인 (DAO에 작업을 시킴)
		MVCBoardDAO dao = new MVCBoardDAO();
		boolean confirmed = dao.confirmPassword(pass, idx);
		dao.close();
		
		if(confirmed) {
			if(mode.equals("edit")) {	//수정 페이지
				HttpSession session = req.getSession();
				session.setAttribute("pass", pass);
				resp.sendRedirect("../mvcboard/edit.do?idx="+idx);
			}else if(mode.equals("delete")) {
				dao = new MVCBoardDAO();
				MVCBoardDTO dto = dao.selectView(idx); // r게시물 삭제
				int result = dao.deletePost(idx);
				dao.close();
			}
				
				//게시물 삭제시 첨부파일도 같이 삭제
				
				//삭제 이후 페이지 이동(JavaScript) : JSFunction.java
				JSFunction.alertLocation(resp, "삭제되었습니다.", "../mvcboard/list.do");
		}else {		//비밀번호가 일치하지 않을 때 (Java Script 실행시 이전페이지로 돌아가도록)
			//이전 페이지로 이동(JavaScript)
			JSFunction.alertBack(resp, "비밀번호 검증에 실패했습니다.");
			
		}
	}

	
	
	
}
