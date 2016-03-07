package spring.model.picture;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import spring.utility.blog.DBClose;
import spring.utility.blog.DBOpen;

public class ImgDAO {
	public List getImgList(int imgno) {
		List v = new ArrayList();
		Connection con = DBOpen.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();

		sql.append(" SELECT * FROM ");
		sql.append(" ( ");
		sql.append("    select imgno, filename,  ");
		sql.append("            lag(imgno,2)     over (order by imgno) pre_imgno2,     "); //이전
		sql.append("            lag(filename,2)  over (order by imgno) pre_file2,      "); 
		sql.append("            lag(imgno,1)     over (order by imgno ) pre_imgno1,     ");//이전전
		sql.append("            lag(filename,1)  over (order by imgno ) pre_file1,     "); 
		sql.append("            lead(imgno,1)    over (order by imgno) nex_imgno1,     "); //다음
		sql.append("            lead(filename,1) over (order by imgno) nex_file1,   ");  
		sql.append("            lead(imgno,2)    over (order by imgno) nex_imgno2,     "); //다음다음
		sql.append("            lead(filename,2) over (order by imgno) nex_file2    "); 
		sql.append("            from ( ");
		sql.append("                  SELECT imgno, filename  ");
		sql.append("                  FROM img");
		sql.append("                  ORDER BY imgno DESC ");
		sql.append("             ) ");
		sql.append("    ) ");
		sql.append(" WHERE imgno = ? ");

		 
		try {
		pstmt = con.prepareStatement(sql.toString());
		pstmt.setInt(1, imgno);

		rs = pstmt.executeQuery();
		if(rs.next()){
		String[] filename = { rs.getString("pre_file2"),rs.getString("pre_file1"),
		              rs.getString("filename"),rs.getString("nex_file1"),rs.getString("nex_file2")};
		int[] numArr = {rs.getInt("pre_imgno2"),rs.getInt("pre_imgno1"),rs.getInt("imgno"),rs.getInt("nex_imgno1"),rs.getInt("nex_imgno2")};
		v.add(filename);
		v.add(numArr);
		}
		} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		} finally{

		DBClose.close(con, pstmt, rs);
		}


		return v;
		}

	
	/**
	 * 이미지 생성
	 * @param dto
	 * @return
	 */
	public int create(ImgDTO dto){
		int cnt = 0;
		
		return cnt;
	}
	/**
	 * 한건의 이미지와 관련있는 이미지 5개가져오기
	 * @param imgno
	 * @return
	 */
	public ImgDTO read(int imgno){
		ImgDTO dto = null;
		Connection con = DBOpen.getConnection();
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    StringBuffer sql = null; // String 보다 처리 속도가 수만배 빠름.
	    
	    sql = new StringBuffer();
	    sql.append(" SELECT imgno, wname, title, content, viewcnt, wdate,filename");
	    sql.append(" FROM img");
	    sql.append(" WHERE imgno = ?");
	   
	    try{
	      pstmt = con.prepareStatement(sql.toString());
	      pstmt.setInt(1, imgno);
	      
	      rs = pstmt.executeQuery();
	      
	      if(rs.next() == true){
	        String title = rs.getString("title");
	        String content = rs.getString("content");
	        String wname = rs.getString("wname");
	        int viewcnt = rs.getInt("viewcnt");
	        String wdate = rs.getString("wdate");
	        String filename = rs.getString("filename");
	        
	        dto = new ImgDTO();         // 하나의 레코드를 하나의 객체로 변환
	        dto.setImgno(imgno);
	        dto.setWname(wname);
	        dto.setTitle(title);
	        dto.setContent(content);
	        dto.setViewcnt(viewcnt);
	        dto.setWdate(wdate);
	        dto.setFilename(filename);
	      
	      }
	    }catch(Exception e){
	      e.printStackTrace();
	    }finally{
	      DBClose.close(con, pstmt, rs);
	    }
	    
		
		return dto;
	}
	/**
	 * 이미지게시물 수정
	 * @param dto
	 * @return
	 */
	public int update(ImgDTO dto){
		int cnt = 0;
		
		return cnt;
	}
	/**
	 * 한건의 이미지삭제
	 * @param imgno
	 * @return
	 */
	public int delete(int imgno){
		int cnt = 0;
		
		return cnt;
	}
	/**
	 * 이미지 게시물 목록 가져오기
	 * @param map -col,word,sno,eno
	 * @return
	 */
	public List<ImgDTO> list(Map map){
		List<ImgDTO> list = new ArrayList<ImgDTO>();
		
		return list;
	}

	/**
	 * 레코드갯수 가져오기
	 * @param map col,word
	 * @return
	 */
	public int total(Map map){
		int total = 0;
		
		
		return total;
	}
	/**
	 * 패스워드 검증
	 * @param imgno
	 * @param passwd
	 * @return
	 */
	public int passwdCheck(int imgno, String passwd){
		int cnt =0;
		
		return cnt;
	}

	/**
	 * 답변쓰기에서 부모의 정보 가져오기
	 * @param imgno
	 * @return ImgDTO - imgno, grpno, indent, ansnum, title
	 */
	public ImgDTO readReply(int imgno){
		ImgDTO dto = null;
		
		return dto;
	}
	/**
	 * 답변쓰기에서 답변순서 정하기
	 * @param grpno
	 * @param ansnum
	 */
	public void addAnsnum(int grpno, int ansnum){
		
	}
	/**
	 * 답변쓰기
	 * @param dto
	 * @return
	 */
	public int reply(ImgDTO dto){
		int cnt =0;
		
		return cnt;
	}
	/**
	 * 조회수 증가(viewcnt에 1증가)
	 * @param imgno
	 */
	public void addViewcnt(int imgno){
		
	}
	
}
