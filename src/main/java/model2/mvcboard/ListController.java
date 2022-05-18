package model2.mvcboard;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.BoardPage;		//페이징 처리하는 객체

public class ListController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//Get 방식으로 요청이 왔을 때 서버에서 처리
		//1. DAO 객체 생성 (Model : 비즈니스 로직 처리)
		MVCBoardDAO dao = new MVCBoardDAO();
		
		//2. 뷰에 전달할 매개변수 저장용 맵 생성
		Map<String, Object> map = new HashMap<String, Object>();
		
		String searchField = req.getParameter("searchField");
		String searchWord = req.getParameter("searchWord");
		
		if(searchWord != null) {	//검색어가 있다면 map 에 값을 저장
			map.put("searchField", searchField);
			map.put("searchWord", searchWord);
		}
		
		// 게시물 갯수 알아오기 (DAO에 selectCount(map))
		int totalCount = dao.selectCount(map);
		System.out.println("전체 레코드 수 : " + totalCount);
		System.out.println("List.do 요청시 Controller 가 응답함");
		
	/*페이징 처리 시작*/
		//xml 에 세팅된 변수값 불러오기
		ServletContext application = getServletContext();
		int pageSize = Integer.parseInt(application.getInitParameter("POSTS_PER_PAGE"));
		int blockPage = Integer.parseInt(application.getInitParameter("PAGES_PER_BLOCK"));
		
		System.out.println(pageSize);
		System.out.println(blockPage);
		
		//현재 페이지 확인
		int pageNum = 1;
		String pageTemp = req.getParameter("pageNum");	//파라미터는 String 으로 값이 넘어옴
		if(pageTemp != null && !pageTemp.equals("")) {
			pageNum = Integer.parseInt(pageTemp);	//값이 있을 때 넘어온 페이지 변수를 정수로 변환 
		}
		
		//목록에 출력할 게시물 ㅂ범위 계산
		int start = (pageNum -1) * pageSize + 1;	//첫 게시물 번호
		int end = pageNum * pageSize;				//마지막 게시물 번호
		
		map.put("start", start);
		map.put("end", end);
		
	/* 페이징 처리 끝 */
	
		//게시물 목록을 받아오기 (DAO 객체에 작업을 전달)
			//boardList 는 DB의 레코드를 담은 DTO객체를 담고 있음
		List<MVCBoardDTO> boardLists = dao.selectListPage(map);
		dao.close();
		
		//뷰 페이지에 전달할 매개변수들을 추가
		String pagingImg = BoardPage.pagingStr(totalCount, pageSize, blockPage, pageNum, "../mvcboard/list.do");
		map.put("pagingImg", pagingImg);
		map.put("totalCount", totalCount);
		map.put("pageSize", pageSize);
		map.put("pageNum", pageNum);
		
		//뷰페이지로 데이터 전달, request 영엑에전달할 데이터를 저장하고 List.jsp(뷰페이지) 로 포워드
		req.setAttribute("boardLists", boardLists);
		req.setAttribute("map", map);
		req.getRequestDispatcher("/mvcboard/List.jsp").forward(req, resp);
		
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Post 방식으로 요청이 왔을 때 서버에서 처리
	
	}
}
