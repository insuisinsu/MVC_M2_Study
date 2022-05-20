package model2.mvcboard;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.oreilly.servlet.MultipartRequest;

import fileupload.FileUtil;
import utils.JSFunction;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/mvcboard/edit.do")
public class EditController extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//비밀번호 검증 후(검증이 완료되면) idx 에 해당하는 레코드를 dto 에 담아서 Edit.jsp 로 출력
		//Get 방식으로 요청을 받아서(Client 가 넘기는 변수를 할당 받아서) 뷰 페이지로 넘김
		String idx = req.getParameter("idx");
		MVCBoardDAO dao = new MVCBoardDAO();
		MVCBoardDTO dto = dao.selectView(idx);	//idx 에 해당하는 레코드를 넘겨받아 DTO에 저장
		
		req.setAttribute("dto", dto);
		req.getRequestDispatcher("/mvcboard/Edit.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Edit.jsp 에서 수정될 내용을 받아서 DB에 적용
		
		//1. 파일 업로드 처리
			//업로드 디렉토리의 물리적인 경로를 확인해야 함(서버의 파일을 업로드할 물리적인 절대경로)
		String saveDirectory = req.getServletContext().getRealPath("/Uploads");
		
			//업로드할 파일의 최대용량 확인(web.xml 의 설정을 가져옴 / 1MB)
		ServletContext application = this.getServletContext();
		int maxPostSize = Integer.parseInt(application.getInitParameter("maxPostSize"));
		
			//파일 업로드
		MultipartRequest mr = FileUtil.uploadFile(req, saveDirectory, maxPostSize);
		
		if(mr == null) {
			//파일 업로드 실패
			JSFunction.alertBack(resp, "첨부파일 용량이 초과되었습니다.");
			return;
		}
		
		//2. 파일 업로드 외 처리
			//Request 객체가 아니라 MultipartRequest 객체에서 Form 의 변수값을 받음
			// - 업로드 라이브러리 마다 form 의 변수값을 받는 메소드 이름이 다를 수 있음
		String idx = mr.getParameter("idx");
		String prevOfile = mr.getParameter("prevOfile");
		String prevSfile = mr.getParameter("prevSfile");
		
		String name = mr.getParameter("name");
		String title = mr.getParameter("title");
		String content = mr.getParameter("content");
		
		//비밀 번호는 Session 변수의 값에서 가져온다.
		HttpSession session = req.getSession();
		String pass = (String) session.getAttribute("pass");
		
		//DTO 에서 넘겨받은 변수값을 저장(Client Form -> DTO -> DAO 에 전달)
		MVCBoardDTO dto = new MVCBoardDTO();
		dto.setIdx(idx);
		dto.setName(name);
		dto.setTitle(title);
		dto.setContent(content);
		dto.setPass(pass);
		
		//dto 객체의 Ofile, Sfile 은 업로드 경로에 해당 파일명이 존재하는 경우 처리 필요
		
		//원본 파일명과 저장될 파일 이름 설정
		String fileName = mr.getFilesystemName("ofile");
		if(fileName != null) {		//첨부 파일이 Uploads 폴더에 존재하는 경우, 파일 이름을 수정해서 저장
			//새로운 파일명 생성
			String now = new SimpleDateFormat("yyyyMMdd_HmsS").format(new Date());	//날짜
			String ext = fileName.substring(fileName.lastIndexOf("."));
			String newFilename = now + ext;
			
			System.out.println("now : " + now);
			System.out.println("ext : " + ext);
			System.out.println("newFilename : " + newFilename);
			
			//파일명 변경
			File oldFile = new File(saveDirectory + File.separator + fileName);
			File newFile = new File(saveDirectory + File.separator + newFilename);
			
			oldFile.renameTo(newFile);
			
			//변경된 내용을 DTO 에 저장
			dto.setOfile(fileName);		//원본 파일이름
			dto.setSfile(newFilename);	//새로운 파일이름(서버에 저장될 파일이름)
			
			//기존 파일 삭제
			FileUtil.deletefile(req, "/Uploads", prevSfile);

		}else { 	//첨부파일이 존재하지 않으면 기존의 이름 유지
			dto.setOfile(prevOfile);
			dto.setSfile(prevSfile);
		}
		
		//DB에 수정 내용을 반영 (DTO 에 저장된 값을 DAO 의 메소드의 매개변수로 전달)
		MVCBoardDAO dao = new MVCBoardDAO();
		int result = dao.updatePost(dto);		//result 가 1이면 update 성공, 0이면 update 실패
		dao.close();
		
		//수정 성공 or 실패
		if(result==1) {
			session.removeAttribute("pass");
			resp.sendRedirect("../mvcboard/view.do?idx=" + idx);
		}else {
			JSFunction.alertLocation(resp, "비밀번호 검증을 다시 해주세요.",
					"../mvcboard/view.do?idx=" + idx);
		}
		
		
	}

}
