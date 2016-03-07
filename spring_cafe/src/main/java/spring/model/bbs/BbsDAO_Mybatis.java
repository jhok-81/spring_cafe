package spring.model.bbs;
 
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class BbsDAO_Mybatis {
 
@Autowired
SqlSessionTemplate sqlSessionTemplate ; 
 
public List<BbsDTO> list(Map map){
 
   return sqlSessionTemplate.selectList("bbs.list", map);
   
}
 
public BbsDTO read(int bbsno){
 
   return sqlSessionTemplate.selectOne("bbs.read", bbsno);
}

public void increaseViewcnt(int bbsno) {
	
	sqlSessionTemplate.update("bbs.upViewcnt", bbsno);
	
}

public int total(String col, String word) {
    Map map = new HashMap();
    map.put("col", col);
    map.put("word", word);

	return sqlSessionTemplate.selectOne("bbs.total", map);
}

public int create(BbsDTO dto) {
	// TODO Auto-generated method stub
	return sqlSessionTemplate.insert("bbs.create", dto);
}

public int checkPasswd(int bbsno, String passwd) {
	Map map = new HashMap();
	map.put("bbsno", bbsno);
	map.put("passwd", passwd);
	
	return sqlSessionTemplate.selectOne("bbs.checkPasswd", map);
}

public int update(BbsDTO dto) {
	// TODO Auto-generated method stub
	return sqlSessionTemplate.update("bbs.update", dto);
}

public int delete(int bbsno) {
	// TODO Auto-generated method stub
	return sqlSessionTemplate.delete("bbs.delete", bbsno);
}

public void addAnsnum(int grpno, int ansnum) {
	Map map = new HashMap();
	map.put("grpno", grpno);
	map.put("ansnum", ansnum);
	sqlSessionTemplate.update("bbs.addAnsnum", map);
	
}

public int reply(BbsDTO dto) {
	// TODO Auto-generated method stub
	return sqlSessionTemplate.insert("bbs.reply", dto);
}                                                                                                                                                                                 

public BbsDTO readReply(int bbsno) {
	// TODO Auto-generated method stub
	return sqlSessionTemplate.selectOne("bbs.readReply", bbsno);
}
 
}