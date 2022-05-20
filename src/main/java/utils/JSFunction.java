package utils;

import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;

public class JSFunction {
	//메세지 알림창 띄우고 명시한 url 로 이동
	public static void alertLocation (String msg, String url, JspWriter out) {
		try {
			String script = ""
						+ "<scipt>"
						+ "    alert('" + msg + "';"
						+ "    location.herf = '" + url +"';"
						+ "</script>";
			
			out.println(script);	//자바 스크립트 코드를 out 내장 객체로 출력
		}catch(Exception e) {
			
		}
	}
	
	
	//메시지 알림창을 띄우고 이전 페이지로 돌아감
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
	
	
	//메시지 알림창을 띄우고 명시한 url 로 이동
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

	//메시지 알림창을 띄운후 이전 페이지로 돌아갑니다.
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
