package com.mnsoft.upmu.common.dao;

import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.mnsoft.upmu.common.vo.Approval;
import com.mnsoft.upmu.common.vo.ErpCarPurchase;
import com.mnsoft.upmu.common.vo.ErpDispatch;
import com.mnsoft.upmu.common.vo.ErpEducation;
import com.mnsoft.upmu.common.vo.ErpEducationalExpenses;
import com.mnsoft.upmu.common.vo.ErpExpenditureResolution;
import com.mnsoft.upmu.common.vo.ErpHoliday;
import com.mnsoft.upmu.common.vo.ErpHolidayWork;
import com.mnsoft.upmu.common.vo.ErpLeave;
import com.mnsoft.upmu.common.vo.ErpMedical;
import com.mnsoft.upmu.expenditureResolution.vo.ExpenditureResolution;
import com.mnsoft.upmu.holiday.vo.Holiday;

@Repository("commonDAOERP")
public class CommonDAOERP {

	@Resource(name = "sqlSessionTemplateErp") 
    private SqlSessionTemplate sqlSessionErp;

	
	public Holiday selectCodeList() throws Exception {
		return sqlSessionErp.selectOne("selectCodeListErp");
	}


	public void insertHolidayInfoForErp(ErpHoliday erpHolidayVo) {
		sqlSessionErp.insert("insertHolidayInfoForErp", erpHolidayVo);
	}


	public String selectHuanHeadMaxNo(ErpLeave lvVo) {
		return sqlSessionErp.selectOne("selectHuanHeadMaxNo", lvVo);
	}

	public void insertLeaveInfoHeadForErp(ErpLeave vo) {
		sqlSessionErp.insert("insertLeaveInfoHeadForErp", vo);
	}
	
	public void insertLeaveInfoDetailForErp(ErpLeave vo) {
		sqlSessionErp.insert("insertLeaveInfoDetailForErp", vo);
		
	}

	public void insertHolidayWorkInfoForErp(ErpHolidayWork vo) {
		sqlSessionErp.insert("insertHolidayWorkInfoForErp", vo);
	}
	
	public void insertCarPurchaseForErp(ErpCarPurchase vo) {
		sqlSessionErp.insert("insertCarPurchaseForErp", vo);
	}

	public void insertDispatchForErp(ErpDispatch vo) {
		sqlSessionErp.insert("insertDispatchForErp", vo);
	}
	public void insertLeaveInfoInsaForErp(ErpLeave vo) {
		sqlSessionErp.insert("insertLeaveInfoInsaForErp", vo);
		
	}
	
	public void insertEducationInfoDucodeForErp(ErpEducation vo) {
		sqlSessionErp.insert("insertEducationInfoDucodeForErp", vo);
	}
	
	public void insertEducationInfoPcodeForErp(ErpEducation vo) {
		sqlSessionErp.insert("insertEducationInfoPcodeForErp", vo);
		
	}

	public void insertEducationInfoDuperForErp(ErpEducation vo) {
		sqlSessionErp.insert("insertEducationInfoDuperForErp", vo);
	}
	
	public void insertEdExpensesInfoPfluctForErp(ErpEducationalExpenses vo) {
		sqlSessionErp.insert("insertEdExpensesInfoPfluctForErp", vo);
	}
	
	public void insertMedicalInfoPfluctForErp(ErpMedical vo) {
		sqlSessionErp.insert("insertMedicalInfoPfluctForErp", vo);
	}
	
	public void insertChulsanInfoInsaForErp(ErpLeave vo) {
		sqlSessionErp.insert("insertChulsanInfoInsaForErp", vo);
		
	}
	
	public void updateErInfoFiForErp(ErpExpenditureResolution vo) {
		sqlSessionErp.update("updateErInfoFiForErp", vo);
	}


	public List<Approval> doSelectApprovalLinkListErp(Approval vo) {
		return sqlSessionErp.selectList("doSelectApprovalLinkListErp",vo);
	}


	public ExpenditureResolution selectErContentInfo(ExpenditureResolution vo) {
		return sqlSessionErp.selectOne("selectErContentInfo", vo);
	}
	
	
	

}
