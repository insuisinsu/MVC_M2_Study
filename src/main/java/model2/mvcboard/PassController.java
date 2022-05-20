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
		// Get ��û�� ó��
		
		//mode : edit <-- �� ����, mode : delete <-- �� ����
		req.setAttribute("mod", req.getParameter("mod"));
		req.getRequestDispatcher("/mvcboard/Pass.jsp").forward(req, resp);
		
		System.out.println("PassController �۵���");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Post ��û�� ó��
		
		//pass.jsp(��) ���� ������ ���� 3 ��
		String idx = req.getParameter("idx");
		String mode = req.getParameter("mode");
		String pass = req.getParameter("pass");
		
		//��й�ȣ Ȯ�� (DAO�� �۾��� ��Ŵ)
		MVCBoardDAO dao = new MVCBoardDAO();
		boolean confirmed = dao.confirmPassword(pass, idx);
		dao.close();
		
		if(confirmed) {
			if(mode.equals("edit")) {	//���� ������
				HttpSession session = req.getSession();
				session.setAttribute("pass", pass);
				resp.sendRedirect("../mvcboard/edit.do?idx="+idx);
			}else if(mode.equals("delete")) {
				dao = new MVCBoardDAO();
				MVCBoardDTO dto = dao.selectView(idx); // r�Խù� ����
				int result = dao.deletePost(idx);
				dao.close();
			}
				
				//�Խù� ������ ÷�����ϵ� ���� ����
				
				//���� ���� ������ �̵�(JavaScript) : JSFunction.java
				JSFunction.alertLocation(resp, "�����Ǿ����ϴ�.", "../mvcboard/list.do");
		}else {		//��й�ȣ�� ��ġ���� ���� �� (Java Script ����� ������������ ���ư�����)
			//���� �������� �̵�(JavaScript)
			JSFunction.alertBack(resp, "��й�ȣ ������ �����߽��ϴ�.");
			
		}
	}

	
	
	
}
