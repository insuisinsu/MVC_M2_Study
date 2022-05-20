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

	//���Ͼ��ε� (multipart/form-data ��û) ó��
	//MultipartRequest : cos.jar ���̺귯�� ���� / ���� ���ε� ó��
	
	public static MultipartRequest uploadFile(HttpServletRequest req,
			String saveDirectory, int maxPostSize) {
		try {
			//���ε� ����
			return new MultipartRequest(req, saveDirectory, maxPostSize, "UTF-8");

		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("���� ���ε� ����");
			return null;
		}
		
	}
	
	//�� ������ ������ ���ε�� ������ ���ϵ� �Բ� ����
	public static void deletefile(HttpServletRequest req, String directory, String filename) {
		//�Ű����� directory .. ������ ���ε�� ������ ������
		String sDirectory = req.getServletContext().getRealPath(directory);
		File file = new File(sDirectory + File.separator + filename);
		if(file.exists()) {	//������ �����Ѵٸ�
			file.delete();
		}
	}
	
	//���� �ٿ�ε� ó�� �޼ҵ� : ������ ������ ã�� �ٿ�ε� �մϴ�.
	public static void download(HttpServletRequest req, HttpServletResponse resp,
			String directory, String sfileName, String ofileName) {
		//upload ������ ������ �������� �����θ� ���� 
		String sDirectory = req.getServletContext().getRealPath(directory);
		
		try {
			//������ ã�� �Է� ��Ʈ�� ����
			File file = new File(sDirectory, sfileName);
			InputStream iStream = new FileInputStream(file);
			
			//�ѱ� ���� ����ó��
			String client = req.getHeader("User-Agent");
			if(client.indexOf("WOW64") == -1) {
				ofileName = new String(ofileName.getBytes("UTF-8"), "ISO-8895-1"); 
			}else {
				ofileName = new String(ofileName.getBytes("KSC5601"), "ISO-8895-1");
			}
		
			//���� �ٿ�� ���� ��� ����
			resp.reset();
			resp.setContentType("application/octet-steam");
			resp.setHeader("Content-Disposition", 
					"attachment; filename\"" + ofileName + "\"");
			resp.setHeader("Content-Length",  "" + file.length());
			
			
			//respose ���� ��ü�� ���� ���ο� ��� ��Ʈ�� ����
			OutputStream oStream = resp.getOutputStream();
			
			//��� ��Ʈ���� ���� ���� ���
			byte b[] = new byte [(int)file.length()];
			int readBuffer = 0;
			while((readBuffer - iStream.read(b) > 0)) {
				oStream.write(b, 0, readBuffer);
			}
			
			//����� ��Ʈ�� ��ü ����
			iStream.close();
			oStream.close();
			
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("���� ó���� ���� �߻�");
		}
		
		
	}
	
	
}