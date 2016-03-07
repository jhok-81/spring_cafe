package spring.model.member;
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import spring.utility.blog.DBClose;
import spring.utility.blog.DBOpen;
 
@Repository
public class MemberDAO {
	/**
	  * 로그인처리를 위한 등급을 가져옴
	  * @param id 등급을 가져올 아이디
	  * @return 등급(grade)
	  */
	 public String getGrade(String id){
	 String grade = null;
	 Connection con= DBOpen.getConnection();
	 PreparedStatement pstmt = null;
	 ResultSet rs = null;
	 
	 StringBuffer sql = new StringBuffer();
	 sql.append(" SELECT grade FROM member ");
	 sql.append(" WHERE id = ? ");
	 
	 try {
	pstmt = con.prepareStatement(sql.toString());
	pstmt.setString(1, id);
	 
	rs = pstmt.executeQuery();
	 
	if(rs.next()){
	grade = rs.getString("grade");
	}
	 
	} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
	}
	 
	 
	 return grade;
	 }
	/** 
	   * 로그인 처리 
	   * @param id 아이디 
	   * @param passwd 패스워드 
	   * @return 1: 일치, 0: 일치하지 않음 
	   */ 
	  public int loginCheck(String id, String passwd){  
	    Connection con = DBOpen.getConnection();  // DBMS 연결 
	    PreparedStatement pstmt = null; 
	    ResultSet rs = null; 
	    int cnt = 0; 

	    try{ 
	      StringBuffer sql = new StringBuffer(); 
	      sql.append(" SELECT COUNT(id) as cnt"); 
	      sql.append(" FROM member "); 
	      sql.append(" WHERE id = ? AND passwd = ?"); 

	      pstmt = con.prepareStatement(sql.toString()); 
	      pstmt.setString(1, id); 
	      pstmt.setString(2, passwd); 

	      rs = pstmt.executeQuery(); 
	       
	      if(rs.next() == true){ //첫번째 레코드로 이동, 다음부터 다음레코드로 이동 
	        cnt = rs.getInt("cnt");    // DBMS -> JAVA 
	      } 
	       
	    }catch(Exception e){ 
	      System.out.println(e.toString()); 
	    }finally{ 
	      DBClose.close(con, pstmt, rs); 
	    } 

	    return cnt; 
	     
	  } 
	
	/**
	 * id의 사진(fname)정보를 가져옴
	 * @param id 
	 * @return fname
	 */
	public String getFname(String id){
		Connection con = DBOpen.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT fname FROM member ");
		sql.append(" WHERE id = ? ");
		String fname = null;
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				fname = rs.getString("fname");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			DBClose.close(con, pstmt, rs);
		}	
		return fname;
	}
	
	/** 
	   * 회원을 삭제합니다. 
	   * @param id 삭제할 회원 아이디 
	   * @return 삭제 결과 1, 0 
	   */ 
	  public int delete(String id){  
	    Connection con = DBOpen.getConnection(); 
	    PreparedStatement pstmt = null; 
	    StringBuffer sql = new StringBuffer(); 
	    int cnt = 0; 
	     
	    try{
	      sql.append(" DELETE FROM member");  
	      sql.append(" WHERE id=?"); 
	      
	      pstmt = con.prepareStatement(sql.toString());
	      pstmt.setString(1, id);
	      
	      cnt = pstmt.executeUpdate(); 
	       
	    }catch(Exception e){ 
	      System.out.println(e.toString()); 
	    }finally{ 
	      DBClose.close(con, pstmt); 
	    } 
	    return cnt; 
	  } 
	/** 
	   * 회원 정보 수정 
	   * @param dto 
	   * @return 가입된 회원의 수, 1 또는 0 
	   */ 
	  public int update(MemberDTO dto) { 
	    Connection con = DBOpen.getConnection(); 
	    PreparedStatement pstmt = null; // SQL 실행 
	    ResultSet rs = null; // SELECT 결과 저장 
	    StringBuffer sql = null; 
	    int cnt = 0; 

	    try { 
	      sql = new StringBuffer(); 
	      sql.append(" UPDATE member"); 
	      sql.append(" SET mname=?, tel=?, email=?, zipcode=?, address1=?, address2=?, job=?"); 
	      sql.append(" WHERE id=?"); 

	      pstmt = con.prepareStatement(sql.toString()); 

	      pstmt.setString(1, dto.getMname()); 
	      pstmt.setString(2, dto.getTel()); 
	      pstmt.setString(3,  dto.getEmail()); 
	      pstmt.setString(4, dto.getZipcode()); 
	      pstmt.setString(5, dto.getAddress1()); 
	      pstmt.setString(6, dto.getAddress2()); 
	      pstmt.setString(7, dto.getJob()); 
	      pstmt.setString(8, dto.getId()); 

	      cnt = pstmt.executeUpdate(); 
	    } catch (Exception e) { 
	      e.printStackTrace(); 
	    } finally { 
	      DBClose.close(con, pstmt); 
	    } 

	    return cnt; 
	  } 

	/** 
	   * 회원 비번을 변경합니다. 
	   * @param id 회원 아이디 
	   * @param passwd 패스워드 
	   * @return 변경된 레코드 수 1, 0 
	   */ 
	  public int updatePasswd(String id, String passwd){  
	    Connection con = DBOpen.getConnection(); 
	    PreparedStatement pstmt = null; 
	    StringBuffer sql = new StringBuffer(); 
	    int cnt = 0; 
	   
	    sql.append(" UPDATE member ");
	    sql.append(" SET passwd = ? ");
	    sql.append(" WHERE id = ? ");
	    
	    try{ 
	      pstmt = con.prepareStatement(sql.toString());
	      pstmt.setString(1, passwd);
	      pstmt.setString(2, id);
	      
	      cnt = pstmt.executeUpdate(); 
	       
	    }catch(Exception e){ 
	      System.out.println(e.toString()); 
	    }finally{ 
	      DBClose.close(con, pstmt); 
	    } 
	    return cnt; 
	  } 

	/** 
	   * 회원 사진을 변경합니다. 
	   * @param id 회원 아이디 
	   * @param fname 파일명 
	   * @return 변경된 레코드 수 1, 0 
	   */ 
	  public int updateFile(String id, String fname){  
	    Connection con = DBOpen.getConnection(); 
	    PreparedStatement pstmt = null; 
	    StringBuffer sql = new StringBuffer(); 
	    int cnt = 0; 
	     
	    try{ 
	      sql.append(" UPDATE member"); 
	      sql.append(" SET fname=?"); 
	      sql.append(" WHERE id=?"); 
	      
	      pstmt =con.prepareStatement(sql.toString());
	      pstmt.setString(1, fname);
	      pstmt.setString(2, id);
	      
	      cnt = pstmt.executeUpdate(); 
	       
	    }catch(Exception e){ 
	      System.out.println(e.toString()); 
	    }finally{ 
	      DBClose.close(con, pstmt); 
	    } 
	    return cnt; 
	  } 
	
	/** 
	   * 회원 상세 정보 
	   *  
	   * @return 
	   */ 
	  public MemberDTO read(String id) { 
	    Connection con = DBOpen.getConnection(); 
	    PreparedStatement pstmt = null; // SQL 실행 
	    ResultSet rs = null; // SELECT 결과 저장 
	    StringBuffer sql = null; 
	    int cnt = 0; 
	    MemberDTO dto = null; 

	    try { 
	      sql = new StringBuffer(); 
	      sql.append(" SELECT id, passwd, mname, tel, email, zipcode, address1, address2, job, mdate, fname"); 
	      sql.append(" FROM member"); 
	      sql.append(" WHERE id=?"); 

	      pstmt = con.prepareStatement(sql.toString()); 
	      pstmt.setString(1, id); 

	      rs = pstmt.executeQuery(); 
	      
	      if(rs.next()){

		      dto = new MemberDTO(); 
	
		      dto.setId(rs.getString("id")); 
		      dto.setPasswd(rs.getString("passwd")); 
		      dto.setMname(rs.getString("mname")); 
		      dto.setTel(rs.getString("tel")); 
		      dto.setEmail(rs.getString("email")); 
		      dto.setZipcode(rs.getString("zipcode")); 
		      dto.setAddress1(rs.getString("address1")); 
		      dto.setAddress2(rs.getString("address2")); 
		      dto.setJob(rs.getString("job")); 
		      dto.setMdate(rs.getString("mdate")); 
		      dto.setFname(rs.getString("fname")); 
	      }

	    } catch (Exception e) { 
	      e.printStackTrace(); 
	    } finally { 
	      DBClose.close(con, pstmt, rs); 
	    } 

	    return dto; 
	  } 
	/**
	 * 전체레코드 갯수 가져오기
	 * @param map col-검색컬럼, word-검색어
	 * @return int 전체 레코드 갯수
	 */
	public int total(Map map){
	int total = 0;
	Connection con = DBOpen.getConnection();
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String col = (String)map.get("col");
	String word = (String)map.get("word");
	 
	StringBuffer sql = new StringBuffer();
	sql.append(" SELECT COUNT(*) FROM member ");
	if(word.trim().length()>0){
		sql.append(" WHERE "+col+" LIKE '%'||?||'%' ");
	}
	 
	try {
	pstmt = con.prepareStatement(sql.toString());
	if(word.trim().length()>0){
		pstmt.setString(1, word);
	}
	 
	rs = pstmt.executeQuery();
	 
	if(rs.next()){
	total = rs.getInt(1);
	}
	 
	} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
	} finally{
	DBClose.close(con, pstmt, rs);
	}
	 
	return total;
	}
	/** 
	  * 회원 목록 출력 
	  *  
	  * @return 
	  */ 
	 public List<MemberDTO> list(Map map) { 
	   Connection con = DBOpen.getConnection(); 
	   PreparedStatement pstmt = null; // SQL 실행 
	   ResultSet rs = null; // SELECT 결과 저장 
	   StringBuffer sql = null; 
	   List<MemberDTO> list = null; 
	   
	   int sno = (Integer)map.get("sno"); 
	   int eno = (Integer)map.get("eno"); 
	   String col = (String)map.get("col");
	   String word = (String)map.get("word");
	   
	   try { 
	     sql = new StringBuffer(); 
	     sql.append(" SELECT id, mname, tel, email, address1, address2, fname, r  "); 
	     sql.append(" FROM(  "); 
	     sql.append("     SELECT id, mname, tel, email, address1, address2, fname, rownum r  "); 
	     sql.append("     FROM( "); 
	     sql.append("      SELECT id, mname, tel, email, address1, address2, fname"); 
	     sql.append("      FROM member ");
	     if(word.trim().length()>0){
	      sql.append("     WHERE "+col+" LIKE '%'||?||'%'");
	     }
	     sql.append("      ORDER BY id ASC  "); 
	     sql.append("     )  "); 
	     sql.append(" )WHERE r >= ? AND r <= ?"); 
	 
	     pstmt = con.prepareStatement(sql.toString());
	     int i=0;
	     if(word.trim().length()>0){
	      pstmt.setString(++i, word);
	     }
	     pstmt.setInt(++i, sno);
	     pstmt.setInt(++i, eno);
	 
	     rs = pstmt.executeQuery(); 
	 
	     list = new ArrayList<MemberDTO>(); 
	     while (rs.next() == true) { // 첫 번째 레코드, 두번째 레코드 
	       MemberDTO dto = new MemberDTO(); 
	       dto.setId(rs.getString("id")); 
	       dto.setMname(rs.getString("mname")); 
	       dto.setTel(rs.getString("tel")); 
	       dto.setEmail(rs.getString("email")); 
	       dto.setAddress1(rs.getString("address1")); 
	       dto.setAddress2(rs.getString("address2")); 
	       dto.setFname(rs.getString("fname")); 
	 
	       list.add(dto); 
	     } 
	   } catch (Exception e) { 
	     e.printStackTrace(); 
	   } finally { 
	     DBClose.close(con, pstmt, rs); 
	   } 
	 
	   return list; 
	} 
	public int create(MemberDTO dto){ 
	    Connection con = DBOpen.getConnection(); 
	    PreparedStatement pstmt = null; 
	    StringBuffer sql = new StringBuffer(); 
	    int cnt = 0; 
	     
	    try{ 
	      sql.append(" INSERT INTO member(id, passwd, mname, tel, email, zipcode, "); 
	      sql.append("                    address1, address2, job, mdate, fname)"); 
	      sql.append(" VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, sysdate, ?)"); 
	       
	      pstmt = con.prepareStatement(sql.toString()); 
	      pstmt.setString(1, dto.getId()); 
	      pstmt.setString(2, dto.getPasswd()); 
	      pstmt.setString(3, dto.getMname()); 
	      pstmt.setString(4, dto.getTel()); 
	      pstmt.setString(5, dto.getEmail()); 
	      pstmt.setString(6, dto.getZipcode()); 
	      pstmt.setString(7, dto.getAddress1()); 
	      pstmt.setString(8, dto.getAddress2()); 
	      pstmt.setString(9, dto.getJob()); 
	      pstmt.setString(10, dto.getFname()); 
	      
	      cnt = pstmt.executeUpdate(); 
	       
	    }catch(Exception e){ 
	      System.out.println(e.toString()); 
	    }finally{ 
	      DBClose.close(con, pstmt); 
	    } 
	    return cnt; 
	  } 
	/** 
	   * 우편번호 목록 출력 
	   *  
	   * @return 
	   */ 
	  public List<ZipcodeDTO> zipcodeList(String dongli) { 
	    Connection con = DBOpen.getConnection(); 
	    PreparedStatement pstmt = null; // SQL 실행 
	    ResultSet rs = null; // SELECT 결과 저장 
	    StringBuffer sql = null; 
	    int cnt = 0; 
	    List<ZipcodeDTO> list = null; 

	    try { 
	      sql = new StringBuffer(); 
	      sql.append(" SELECT zipcode, sido, gugun, dongli, etc"); 
	      sql.append(" FROM zipcode"); 
	      sql.append(" WHERE (dongli LIKE '%' || ? || '%')"); 
	      sql.append(" ORDER BY sido, gugun, dongli"); 
	       
	      // ASC 오름차순 정렬임, 생략되어 있음 
	      // sql.append(" ORDER BY sido ASC, gugun ASC, dongli ASC"); 

	      pstmt = con.prepareStatement(sql.toString()); 
	      pstmt.setString(1, dongli); 

	      rs = pstmt.executeQuery(); 

	      list = new ArrayList<ZipcodeDTO>(); 
	      while (rs.next() == true) { 
	        ZipcodeDTO dto = new ZipcodeDTO(); 
	        // zipcode, sido, gugun, dongli, etc 
	        dto.setZipcode(rs.getString("zipcode")); 
	        dto.setSido(rs.getString("sido")); 
	        dto.setGugun(rs.getString("gugun")); 
	        dto.setDongli(rs.getString("dongli")); 
	        dto.setEtc(rs.getString("etc")); 

	        list.add(dto); 
	      } 
	    } catch (Exception e) { 
	      e.printStackTrace(); // 에러원인이 자세하게 출력되나 해킹의 대상 
	    } finally { 
	      DBClose.close(con, pstmt, rs); 
	    } 

	    return list; 
	  } 
	/**
	   * 중복 Email 검사
	   * @param email
	   * @return 0: 중복 아님, 1: 중복
	   */
	  public int duplicateEmail(String email){
	    Connection con = DBOpen.getConnection(); // DBMS 연결 
	    PreparedStatement pstmt = null; 
	    ResultSet rs = null; 
	    int cnt = 0; 
	     
	    try { 
	      StringBuffer sql = new StringBuffer(); // 문자열 편집 지원 
	       
	      sql.append(" SELECT COUNT(*) as cnt"); 
	      sql.append(" FROM member "); 
	      sql.append(" WHERE email=?"); 
	 
	      pstmt = con.prepareStatement(sql.toString());
	      pstmt.setString(1, email);
	      
	      rs = pstmt.executeQuery(); 
	 
	      if (rs.next() == true) { // 첫번째 레코드로 이동, 다음부터 다음레코드로 이동 
	        cnt = rs.getInt("cnt"); 
	      } 
	 
	    } catch (Exception e) { 
	      System.out.println(e.toString()); 
	    } finally { 
	      DBClose.close(con, pstmt, rs); 
	    } 
	 
	    return cnt; 
	  }
 
	/**
	   * 중복 아이디 검사
	   * @param id
	   * @return 0: 중복 아님, 1: 중복
	   */
	  public int duplicateId(String id){
	    Connection con = DBOpen.getConnection(); // DBMS 연결 
	    PreparedStatement pstmt = null; 
	    ResultSet rs = null; 
	    int cnt = 0; 
	     
	    try { 
	      StringBuffer sql = new StringBuffer(); // 문자열 편집 지원 
	       
	      sql.append(" SELECT COUNT(*) as cnt"); 
	      sql.append(" FROM member "); 
	      sql.append(" WHERE id=?"); 
	 
	      pstmt = con.prepareStatement(sql.toString());
	      pstmt.setString(1, id);
	      
	      rs = pstmt.executeQuery(); 
	 
	      if (rs.next() == true) { // 첫번째 레코드로 이동, 다음부터 다음레코드로 이동 
	        cnt = rs.getInt("cnt"); 
	      } 
	 
	    } catch (Exception e) { 
	      System.out.println(e.toString()); 
	    } finally { 
	      DBClose.close(con, pstmt, rs); 
	    } 
	 
	    return cnt; 
	  }
	 
}