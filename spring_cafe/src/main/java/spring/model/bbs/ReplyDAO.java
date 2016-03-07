package spring.model.bbs;
 
import java.util.List;
import java.util.Map;
 
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
 
@Repository
public class ReplyDAO {
 
@Autowired
private SqlSessionTemplate sqlSessionTemplate ; 
 

/** ReplyDAO클래스의 내용을 테스트하기위해서 사용**/
public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate){
this.sqlSessionTemplate = sqlSessionTemplate;
}
 
public boolean create(ReplyDTO dto){
boolean flag = false;
 
int cnt = (Integer)sqlSessionTemplate.insert("reply.create", dto);
if(cnt>0) flag = true;
 
return flag;
}
 
public ReplyDTO read(int rnum){
 
return(ReplyDTO)sqlSessionTemplate.selectOne("reply.read", rnum);
 
}
 
public boolean update(ReplyDTO dto){
boolean flag = false;
 
int cnt = sqlSessionTemplate.update("reply.update", dto);
if(cnt>0)flag = true;
 
return flag;
}
 
public List<ReplyDTO> list(Map map){
 
return sqlSessionTemplate.selectList("reply.list", map);
}
 
 
public int total(int bbsno){
return (Integer)sqlSessionTemplate.selectOne("reply.total",bbsno);
}
/** 댓글 하나만 삭제 **/
public boolean delete(int rnum){
boolean flag = false;
int cnt = sqlSessionTemplate.delete("reply.delete", rnum);
if(cnt>0) flag = true;
 
return flag;
}

/** 하나의 글의 여러 댓글들 삭제 */
public int bdelete(int bbsno) throws Exception{
   return sqlSessionTemplate.delete("reply.bdelete", bbsno);   
}

public int rcount(int bbsno){
    return sqlSessionTemplate.selectOne("reply.rcount", bbsno);
}
}