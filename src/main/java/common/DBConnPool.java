package common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DBConnPool {
	public Connection con;
	public Statement stmt;
	public PreparedStatement psmt;
	public ResultSet rs;

	// �⺻ ������
	public DBConnPool() {
		System.out.println("DBCP ��ü ���");
		try {
			//��Ĺ�� ������ Ŀ�ؼ� Ǯ(DBCP) ���� ������
			Context initCtx = new InitialContext();
			Context ctx = (Context) initCtx.lookup("java:comp/env");
			DataSource source = (DataSource)ctx.lookup("dbcp_myoracle");
			
			//Ŀ�ؼ� Ǯ�� ���ؼ� ���� ���
			con = source.getConnection();
			System.out.println("DBConnPool(Ŀ�ؼ�Ǯ(DBCP)) ���� ����");
					
		}catch(Exception e) {
			System.out.println("DBConnPool(Ŀ�ؼ�Ǯ(DBCP)) ���� ����");
			e.printStackTrace(); 	//���� �޽��� ��� : �ڼ��� ���� ���
			System.out.println(e.getMessage()); //���� �޽��� ��� : ������ ���� ���
		}	
	}
	
	//�ڿ� ���� ���� (�ڿ� �ݳ�) : close() ȣ��� �ڿ��� �ݳ��ϵ��� ����
	public void close() {
		try {
			if(rs != null) rs.close();
			if(stmt != null) stmt.close();
			if(psmt != null) psmt.close();
			if(con != null) con.close(); 	//�ڿ��� Ŀ�ؼ� Ǯ�� �ݳ�
			
			System.out.println("DBCP �ڿ� �ݳ�");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
}