package utils;

import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;

public class JSFunction {
	//�޼��� �˸�â ���� ����� url �� �̵�
	public static void alertLocation (String msg, String url, JspWriter out) {
		try {
			String script = ""
						+ "<scipt>"
						+ "    alert('" + msg + "';"
						+ "    location.herf = '" + url +"';"
						+ "</script>";
			
			out.println(script);	//�ڹ� ��ũ��Ʈ �ڵ带 out ���� ��ü�� ���
		}catch(Exception e) {
			
		}
	}
	
	
	//�޽��� �˸�â�� ���� ���� �������� ���ư�
	public static void alertBack(String msg, JspWriter out) {
		try {
			String script = ""
						+ "<script>"
						+ "		alert('" + msg + "');"
						+ "		history.back();"
						+ "</script>";
			out.println(script);
		}catch(Exception e) {
			
		}
	}
	
	
	//�޽��� �˸�â�� ���� ����� url �� �̵�
	public static void alertLocation (HttpServletResponse resp, String msg, String url) {
		try {
			resp.setContentType("text/html;charset=UTF-8");
			PrintWriter writer = resp.getWriter();
			String script = ""
						+ "<script>"
						+ "		alert('" + msg + "');"
						+ "		location.href = '" + url + "';"
						+ "</script>";
			writer.print(script);
		}catch(Exception e){
			
		}
		
	}

	//�޽��� �˸�â�� ����� ���� �������� ���ư��ϴ�.
	public static void alertBack(HttpServletResponse resp, String msg) {
		try {
			resp.setContentType("text/html;cahrset=UTF-8");
			PrintWriter writer = resp.getWriter();
			String script = ""
						+ "<script>"
						+ "		alert('" + msg + "');"
						+ "		history.back();"
						+ "</script>";
			writer.print(script);
		}catch(Exception e){
			
		}
	}
	
	
}
