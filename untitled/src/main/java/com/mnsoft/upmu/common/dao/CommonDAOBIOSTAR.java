package com.mnsoft.upmu.common.dao;

import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.mnsoft.upmu.meal.vo.Meal;

@Repository("commonDAOBIOSTAR")
public class CommonDAOBIOSTAR {

	@Resource(name = "sqlSessionTemplateBiostar") 
    private SqlSessionTemplate sqlSessionBiostar;
	
	public void biostarSaveMealTimeMgmt(Meal vo) {
		sqlSessionBiostar.update("biostarSaveMealTimeMgmt", vo);
	}
	
	public List<Meal> selectMealTimeMgmt(Meal meal) {
		return sqlSessionBiostar.selectList("selectMealTimeMgmt",meal);
	}
	
	public List<Meal> selectMealMonth(Meal meal) {
		return sqlSessionBiostar.selectList("selectMealMonth",meal);
	}
	
	public Meal selectMealMgmt(Meal meal) {
		return sqlSessionBiostar.selectOne("selectMealMgmt",meal);
	}
	
	public void saveMealMgmt(Meal meal) {
		sqlSessionBiostar.insert("saveMealMgmt", meal);
	}
}
