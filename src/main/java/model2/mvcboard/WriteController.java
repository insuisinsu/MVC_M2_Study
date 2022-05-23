package model2.mvcboard;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;

import fileupload.FileUtil;
import utils.JSFunction;


@WebServlet("/mvcboard/write.do")
public class WriteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get 방식 요청 처리
		// List.jsp (view 페이지) 에서 글쓰기를 클릭했을 때 -> 글쓰기 뷰페이지(Write.jsp) 로 이동
		
		//뷰페이지로 이동
		request.getRequestDispatcher("/mvcboard/Write.jsp").forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Post 방식 요청 처리
		//Write.jsp 페이지에서 전송을 클릭했을 때 form 에서 넘어오는 변수의 값을 처리
		
		//form 에서 파일을 전송하므로 cos.jar 라이브러리의 객체 생성후 변수의 값을 받아야한다.
		
		//1. 파일 업로드 처리
			//saveDirectory 변수에 업로드할 파일을 저장할 서버의 물리적인 경로를 저장
			String saveDirectory = request.getServletContext().getRealPath("/Uploads");
			
			//maxPostSize : 업로드할 최대 용량 (web.xml 에 1MB 로 잡혀있음) 그걸 불러올 거임
			ServletContext application = getServletContext();
			int maxPostSize = Integer.parseInt(application.getInitParameter("maxPostSize"));
		
			//객체 생성
			MultipartRequest mr = FileUtil.uploadFile(request, saveDirectory, maxPostSize);
			
			//객체 생성 실패시 처리할 내용
			if(mr==null) { // 객체 생성 실패 (1MB 이상인 파일 전송시)
				JSFunction.alertLocation(response, "첨부용량 초과", "../mvcboard/write.do");
				return;
			}
		
		//2. 파일 업로드 외 처리(Form 의 변수값 처리)
			//폼에서 넘겨받은 값을 받아서 DTO(VO)에 Setter 주입을 하고 DAO 에 Insert 메소드에 던져줌
			MVCBoardDTO dto = new MVCBoardDTO();
			dto.setName(mr.getParameter("name"));
			dto.setTitle(mr.getParameter("title"));
			dto.setContent(mr.getParameter("content"));
			dto.setPass(mr.getParameter("pass"));
			
			//원본파일 이름과 저장파일 이름 설정
			String fileName = mr.getFilesystemName("ofile");	//client의 첨부파일의 물리적 주소
			System.out.println(fileName);
			if(fileName != null) { 		//첨부파일이 비어있지 않다면
				
				//새로운 파일 이름으로 변경해서 서버에 저장함
				//첨부하는 파일의 이름이 이미 서버에 있을 수 있음 -> 날짜를 통해 중복 제거
				String now = new SimpleDateFormat("yyyyMMdd_HmsS").format(new Date());
				System.out.println("now : " + now);
				//확장자를 잘라내고 저장 - " . " 부터 가져옴ㄷ
				String ext = fileName.substring(fileName.lastIndexOf("."));
				System.out.println("ext : " + ext);
				//서버에 저장할 파일이름
				String newFileName = now + ext;
				System.out.println("newFileName : " + newFileName);
				
				//파일명 변경
				File oldFile = new File(saveDirectory + File.separator + fileName);		//File.separator = \ (역슬래시)
				File newFile = new File(saveDirectory + File.separator + newFileName);
				System.out.println("oldFile : " + oldFile);
				System.out.println("newFile : " + newFile);
				//oldFile 의 이름을 newFile 로 변경
				oldFile.renameTo(newFile);
				
				dto.setOfile(fileName);		//원래 파일 이름
				dto.setSfile(newFileName);	//서버에 저장될 파일 이름
			}
			
			//DTO의 객체를 DAO 의 insertWrite(dto) 메소드를 호출해서 DB에 저장
			MVCBoardDAO dao = new MVCBoardDAO();
			int result = dao.insertWrite(dto);
			//객체 종료 메소드 호출(rs, stmt, psmt, con 모두 종료)
			dao.close();
			
			//글쓰기가 성공하면 list 페이지로 이동
			if(result == 1) {
				response.sendRedirect("../mvcboard/list.do");
			}
			//글쓰기가 실패하면 이동하는 페이지
			if(result == 0 ) {
				response.sendRedirect("../mvcboard/write.do");
			}
			
			
	}

}









