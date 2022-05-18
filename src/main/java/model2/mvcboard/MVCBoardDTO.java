package model2.mvcboard;

public class MVCBoardDTO {
	//DB의 컬럼의 값을 저장하고 전송해주는 역할
	
	private String idx;
	private String name;
	private String title;
	private String content;
	private java.sql.Date postdate;
	private String ofile;
	private String sfile;
	private int downcount;
	private String pass;
	private int visitcount;
	String getIdx() {
		return idx;
	}
	void setIdx(String idx) {
		this.idx = idx;
	}
	String getName() {
		return name;
	}
	void setName(String name) {
		this.name = name;
	}
	String getTitle() {
		return title;
	}
	void setTitle(String title) {
		this.title = title;
	}
	String getContent() {
		return content;
	}
	void setContent(String content) {
		this.content = content;
	}
	java.sql.Date getPostdate() {
		return postdate;
	}
	void setPostdate(java.sql.Date postdate) {
		this.postdate = postdate;
	}
	String getOfile() {
		return ofile;
	}
	void setOfile(String ofile) {
		this.ofile = ofile;
	}
	String getSfile() {
		return sfile;
	}
	void setSfile(String sfile) {
		this.sfile = sfile;
	}
	int getDowncount() {
		return downcount;
	}
	void setDowncount(int downcount) {
		this.downcount = downcount;
	}
	String getPass() {
		return pass;
	}
	void setPass(String pass) {
		this.pass = pass;
	}
	int getVisitcount() {
		return visitcount;
	}
	void setVisitcount(int visitcount) {
		this.visitcount = visitcount;
	}
	
	//getter, setter 생성
	
	
}
