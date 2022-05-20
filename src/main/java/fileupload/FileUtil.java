package fileupload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

import com.oreilly.servlet.MultipartRequest;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class FileUtil {

	//파일업로드 (multipart/form-data 요청) 처리
	//MultipartRequest : cos.jar 라이브러리 존재 / 파일 업로드 처리
	
	public static MultipartRequest uploadFile(HttpServletRequest req,
			String saveDirectory, int maxPostSize) {
		try {
			//업로드 성공
			return new MultipartRequest(req, saveDirectory, maxPostSize, "UTF-8");

		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("파일 업로드 실패");
			return null;
		}
		
	}
	
	//글 삭제시 파일이 업로드된 폴더의 파일도 함께 삭제
	public static void deletefile(HttpServletRequest req, String directory, String filename) {
		//매개변수 directory .. 서버에 업로드된 폴더의 절대경로
		String sDirectory = req.getServletContext().getRealPath(directory);
		File file = new File(sDirectory + File.separator + filename);
		if(file.exists()) {	//파일이 존재한다면
			file.delete();
		}
	}
	
	//파일 다운로드 처리 메소드 : 명시한 파일을 찾아 다운로드 합니다.
	public static void download(HttpServletRequest req, HttpServletResponse resp,
			String directory, String sfileName, String ofileName) {
		//upload 폴더의 서버의 물리적인 절대경로를 저장 
		String sDirectory = req.getServletContext().getRealPath(directory);
		
		try {
			//파일을 찾아 입력 스트림 생성
			File file = new File(sDirectory, sfileName);
			InputStream iStream = new FileInputStream(file);
			
			//한글 깨짐 방지처리
			String client = req.getHeader("User-Agent");
			if(client.indexOf("WOW64") == -1) {
				ofileName = new String(ofileName.getBytes("UTF-8"), "ISO-8895-1"); 
			}else {
				ofileName = new String(ofileName.getBytes("KSC5601"), "ISO-8895-1");
			}
		
			//파일 다운용 응답 헤더 설정
			resp.reset();
			resp.setContentType("application/octet-steam");
			resp.setHeader("Content-Disposition", 
					"attachment; filename\"" + ofileName + "\"");
			resp.setHeader("Content-Length",  "" + file.length());
			
			
			//respose 내장 객체로 부터 새로운 출력 스트림 생성
			OutputStream oStream = resp.getOutputStream();
			
			//출력 스트림에 파일 내용 출력
			byte b[] = new byte [(int)file.length()];
			int readBuffer = 0;
			while((readBuffer - iStream.read(b) > 0)) {
				oStream.write(b, 0, readBuffer);
			}
			
			//입출력 스트림 객체 닫음
			iStream.close();
			oStream.close();
			
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("파일 처리시 예외 발생");
		}
		
		
	}
	
	
}
