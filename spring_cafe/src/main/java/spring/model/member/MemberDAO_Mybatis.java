package spring.model.member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDAO_Mybatis {
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;                                                                       

	public int total(Map map) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne("member.total", map);
	}

	public List<MemberDTO> list(Map map) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList("member.list", map);
	}

	public MemberDTO read(String id) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne("member.read", id);
	}

	public List<ZipcodeDTO> zipcodeList(String dongli) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList("member.zipcode", dongli);
	}

	public int duplicateId(String id) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne("member.checkId", id);
	}

	public int duplicateEmail(String email) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne("member.checkEmail", email);
	}

	public int create(MemberDTO dto) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.insert("member.create", dto);
	}

	public int update(MemberDTO dto) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.update("member.update", dto);
	}

	public String getFname(String id) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne("member.getFname", id);
	}

	public int delete(String id) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.delete("member.delete", id);
	}

	public int updatePasswd(String id, String passwd) {
		Map map = new HashMap();
		map.put("id", id);
		map.put("passwd", passwd);
		
		return sqlSessionTemplate.update("member.updatePasswd", map);
	}

	public int updateFile(String id, String filename) {
		Map map = new HashMap();
		map.put("id", id);
		map.put("filename", filename);

		return sqlSessionTemplate.update("member.updateFile", map);
	}

	public int loginCheck(String id, String passwd) {
		Map map = new HashMap();
		map.put("id", id);
		map.put("passwd", passwd);
		
		return sqlSessionTemplate.selectOne("member.loginCheck", map);
	}

	public String getGrade(String id) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne("member.getGrade", id);
	}
}
