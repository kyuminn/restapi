package com.mnsoft.upmu.system.holiday.dao;

import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.mnsoft.upmu.holiday.vo.Holiday;
import com.mnsoft.upmu.holiday.vo.HolidayCode;

@Repository("HolidayDao")
public class HolidayDao {

	@Resource(name = "sqlSessionTemplate")
    private SqlSessionTemplate sqlSession;
	
	public int saveHolidayAppl(Holiday vo) {
		return sqlSession.insert("saveHolidayAppl", vo);
	}
	public int deleteHolidayApplDetail(Holiday vo) {
		return sqlSession.delete("deleteHolidayApplDetail", vo);
	}
	public int insertHolidayApplDetail(Holiday vo) {
		return sqlSession.insert("insertHolidayApplDetail", vo);
	}
	public List<Holiday> selectHolidayList(Holiday vo) {
		return sqlSession.selectList("selectHolidayList",vo);
	}
	public List<HolidayCode> selectHolidayCodeList(HolidayCode vo) {
		return sqlSession.selectList("selectHolidayCodeList",vo);
	}
	public int saveHolidayCodeList(HolidayCode list) {
		return sqlSession.insert("saveHolidayCodeList", list);
	}
	public List<HolidayCode> selectWorkgListForHolidayAppl(HolidayCode vo) {
		return sqlSession.selectList("selectWorkgListForHolidayAppl", vo);
	}
	public Holiday selectHolidayApplInfo(Holiday vo) {
		return sqlSession.selectOne("selectHolidayApplInfo", vo);
	}
	public List<Holiday> selectHolidayApprList(Holiday vo) {
		return sqlSession.selectList("selectHolidayApprList", vo);
	}
	public Holiday selectHoliDayType(Holiday vo) {
		return sqlSession.selectOne("selectHoliDayType", vo);
	}
	public List<Holiday> selectHolidayApplInfoForErp(Holiday vo) {
		return sqlSession.selectList("selectHolidayApplInfoForErp", vo);
	}
	public int selectHolidayRequestCheck(Holiday vo) {
		return sqlSession.selectOne("selectHolidayRequestCheck", vo);
	}
	
	public Holiday doSelectDiffDayExceptHoliday(Holiday vo) {
		return sqlSession.selectOne("doSelectDiffDayExceptHoliday", vo);
	}

}
