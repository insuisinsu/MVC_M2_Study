package model2.mvcboard;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import common.DBConnPool;

public class MVCBoardDAO extends DBConnPool{
	
	//기본 생성자 호출시 부모 클래스를 호출
	public MVCBoardDAO() {
		super();			//DBConnPool 객체의 기본생성자 호출, DBCP 에서 con 객체 홮성화
	}
	
	//검색 조건에 맞는 게시물 갯수 반환
	public int selectCount(Map<String, Object> map) {
		int totalCount = 0;
		String query = "SELECT COUNT(*) FROM mvcboard";		//레코드의 총갯수 반환
		if(map.get("searchWord") != null) {				//검색기능을 사용했을시 Where
			query += " Where " + map.get("searchField") + " " + "like '%" + map.get("searchWord") + "%'"; 
		}
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			rs.next();
			totalCount = rs.getInt(1);
		}catch(Exception e) {
			System.out.println("게시물 카운트 중 예외 발생");
			e.printStackTrace();
		}
		return totalCount;
	}
	
	//검색 조건에 맞는 게시물 목록
	//DataBase 에서 Select 한 결과값을 DTO 에 담아서 리턴
	
	public List<MVCBoardDTO> selectListPage(Map<String, Object> map){
		List<MVCBoardDTO> board = new Vector<MVCBoardDTO>();
		String query = " "
				+ "SELECT * FROM ( "
				+ " SELECT Tb.*, ROWNUM, rNUM FROM ( "
				+ "    SELECT * FROM mvcboard 	";
		
		if(map.get("searchWord") != null) {	//검색기능을 사용한다면
			query += " WHERE " + map.get("searchField")
					+ " LIKE '%" + map.get("searchWord") + "%' ";
		}
		
		query += " ORDER BY "
				+ " ) Tb "
				+ " ) "
				+ " WHERE rNUM BETWEEN ? AND ? ";
		
		try {	//psmt 객체 생성후 쿼리 실행
			psmt  = con.prepareStatement(query);	//PreparedStatement 객체 생성
			psmt.setString(1, map.get("start").toString());
			psmt.setString(2, map.get("end").toString());
			rs = psmt.executeQuery();		//DataBase 에서 Select 한 결과값을 rs 에 저장
			
			//rs 에 저장된 값을 DTO 에 저장 -> DTO 객체를 List 에 add
			while(rs.next()) {
				MVCBoardDTO dto = new MVCBoardDTO();
				dto.setIdx(rs.getString(1));
				dto.setName(rs.getString(2));
				dto.setTitle(rs.getString(3));
				dto.setContent(rs.getString(4));
				dto.setPostdate(rs.getDate(5));
				dto.setOfile(rs.getString(6));
				dto.setSfile(rs.getString(7));
				dto.setDowncount(rs.getInt(8));
				dto.setPass(rs.getString(9));
				dto.setVisitcount(rs.getInt(10));
				
				board.add(dto);		//List 에 DB의 rs의 하나의 레코드와 값을 dto 에 저장하고
									//dto 를 List 에 추가
			}		
		}catch(Exception e){
			e.printStackTrace();
		}
		return board;		//board 는 DTO 객체를 저장하고 있음
	}
	
	
	//목록 검색(Select)
	//주어진 일련번호에 해당하는 값을 DTO 에 담아 반환
	
	//데이터 삽입(Insert)
	public int insertWrite(MVCBoardDTO dto) {
		int result =0;
		try {
			String query = "INSERT INTO mvcboard ( "
					+ " idx, name, title, content, ofile, sfile,pass) "
					+ " VALUES ( "
					+ " seq_board_num.nextval, ?, ?, ?, ?, ?, ?)";
			
			psmt = con.prepareStatement(query);
			psmt.setString(1, dto.getName());
			psmt.setString(2, dto.getTitle());
			psmt.setString(3, dto.getContent());
			psmt.setString(4, dto.getOfile());
			psmt.setString(5, dto.getSfile());
			psmt.setString(6, dto.getPass());
			
			result = psmt.executeUpdate();		//DB 에 값을 저장
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;		//insert 가 성공하면 result > 0 / 실패하면 0
	}
	
	
	//데이터 수정(Update)
	
	//데이터 삭제(Delete)
	
	//데이터 검색(Select + 특정조건)
	
	
}
