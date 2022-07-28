package com.mnsoft.upmu.bizcard.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

import com.mnsoft.upmu.bizcard.vo.BizCard;
import com.mnsoft.upmu.common.vo.UserInfo;
import com.mnsoft.upmu.retire.vo.Retire;
import com.mnsoft.upmu.system.vo.Code;
import com.mnsoft.upmu.system.vo.FileInfo;
import com.mnsoft.upmu.system.vo.Link;
import com.mnsoft.upmu.system.vo.Role;
import com.mnsoft.upmu.system.vo.TaskNote;
import com.mnsoft.upmu.system.vo.Time;
import com.mnsoft.upmu.system.vo.Tree;
import com.mnsoft.upmu.vo.Menu;
import com.mnsoft.upmu.work.vo.Personal;


@Repository("BizCardDao")
public class BizCardDao {

	@Resource(name = "sqlSessionTemplate")
    private SqlSessionTemplate sqlSession;
			
	public int saveBizCard(BizCard bizCard)throws Exception {
		return sqlSession.update("saveBizCard", bizCard);
	}
	public List<UserInfo> userInfo(UserInfo userInfo)throws Exception  {
		// TODO Auto-generated method stub
		return sqlSession.selectList("selectUserInfo",userInfo);
	}
	public List<BizCard> selectBizCardList(BizCard bizCard)throws Exception  {
		// TODO Auto-generated method stub
		return sqlSession.selectList("selectBizCardList",bizCard);
	}
	public List<BizCard> bizCardInfo(BizCard bizCard)throws Exception  {
		// TODO Auto-generated method stub
		return sqlSession.selectList("selectBizCardInfo",bizCard);
	}
	
	public int updateCdEmail(Code code)throws Exception {
		return sqlSession.update("updateCdEmail", code);
	}
	public int saveAddr(Code code)throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.update("saveAddr", code);
		
	}
	public int saveCnt(Code code)throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.update("saveCnt", code);
		
	}	
	
	public  List<BizCard>  selectToMailB() {
		// TODO Auto-generated method stub
		return sqlSession.selectList("selectToMailB");
	}

	public BizCard selectFromMailB(BizCard bizCard) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("selectFromMailB",bizCard);
	}
	public List<BizCard> bizCardInfoToday(BizCard bizCard) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("selectBizCardInfoToday",bizCard);
	}
}
