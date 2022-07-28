package com.mnsoft.upmu.common.dao;

import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.mnsoft.upmu.common.vo.Approval;
import com.mnsoft.upmu.common.vo.ApprovalAw;
import com.mnsoft.upmu.common.vo.Common;
import com.mnsoft.upmu.common.vo.CommonAppl;
import com.mnsoft.upmu.common.vo.DateInfo;
import com.mnsoft.upmu.common.vo.DeptInfo;
import com.mnsoft.upmu.common.vo.GroupInfo;
import com.mnsoft.upmu.common.vo.UserInfo;
import com.mnsoft.upmu.education.vo.Education;
import com.mnsoft.upmu.holiday.vo.Holiday;
import com.mnsoft.upmu.officialDocument.vo.OfficialDocument;
import com.mnsoft.upmu.personalWelfare.vo.HealthCheck;
import com.mnsoft.upmu.personalWelfare.vo.IncentiveInfo;
import com.mnsoft.upmu.personalWelfare.vo.IncomeInfo;
import com.mnsoft.upmu.personalWelfare.vo.InsuranceInfo;
import com.mnsoft.upmu.personalWelfare.vo.PromotionPoint;
import com.mnsoft.upmu.personalWelfare.vo.ViewDateInfo;
import com.mnsoft.upmu.system.vo.Application;
import com.mnsoft.upmu.system.vo.Code;
import com.mnsoft.upmu.system.vo.FileInfo;
import com.mnsoft.upmu.system.vo.Guide;
import com.mnsoft.upmu.system.vo.Role;
import com.mnsoft.upmu.system.vo.TaskNote;
import com.mnsoft.upmu.yearEducation.vo.YearEducation;

@Repository("commonDAO")
public class CommonDAO {
	
	@Resource(name = "sqlSessionTemplate") 
    private SqlSessionTemplate sqlSession;

	
	public List<Code> selectCodeList(Code code) throws Exception {
		return sqlSession.selectList("selectCodeList",code);
	}

	public List<Code> getCommonComboEtc_1(Code code) throws Exception {
		return sqlSession.selectList("getCommonComboEtc_1",code);
	}
	
	public List<Code> getCommonComboEtc_2(Code code) throws Exception {
		return sqlSession.selectList("getCommonComboEtc_2",code);
	}

	public List<Code> selectWorkCodeList(Code code) throws Exception {
		return sqlSession.selectList("selectWorkCodeList",code);
	}
	public List<Code> selectWorkComboType(Code code) throws Exception {
		return sqlSession.selectList("selectWorkComboType",code);
	}

	public List<Code> selectComonMenuList(Code code) throws Exception {
		return sqlSession.selectList("selectComonMenuList",code);
	}

	public UserInfo selectUserInfo(UserInfo userInfo) throws Exception {
		return sqlSession.selectOne("selectUserInfo", userInfo);
	}

	public List<Code> selectCommonDeptList(Code code) throws Exception {
		return sqlSession.selectList("selectCommonDeptList",code);
	}
	
	public List<Code> selectPatentDeptList(Code code) throws Exception {
		return sqlSession.selectList("selectPatentDeptList",code);
	}

	public List<UserInfo> selectCommonUserList(UserInfo searchParam) {
		return sqlSession.selectList("selectCommonUserList",searchParam);
	}
	
	public List<String> selecttestList(){
		return sqlSession.selectList("selecttestList");
	}
	
	public List<DeptInfo> selectOrgListForTree(DeptInfo searchParam) {
		return sqlSession.selectList("selectOrgListForTree",searchParam);
	}

	public List<DeptInfo> selectOrgListForTreeLeader(UserInfo searchParam) {
		return sqlSession.selectList("selectOrgListForTreeLeader",searchParam);
	}
	
	public List<UserInfo> selectOrgListForUser(List<String> list) {
		return sqlSession.selectList("selectOrgListForUser",list);
	}

	public List<UserInfo> selectOrgListForDept(List<String> list) {
		return sqlSession.selectList("selectOrgListForDept",list);
	}

	public List<UserInfo> selectUserListForDept(UserInfo searchParam) {
		return sqlSession.selectList("selectUserListForDept",searchParam);
	}
	public List<UserInfo> selectHeadUserListForDept(UserInfo searchParam) {
		return sqlSession.selectList("selectHeadUserListForDept",searchParam);
	}

	public List<Code> selectCommonLinkList(Code code) {
		return sqlSession.selectList("selectCommonLinkList",code);
	}

	public void insertFileMgmt(FileInfo fileVo) {
		sqlSession.insert("insertFileMgmt", fileVo);
	}

	public List<FileInfo> selectFileList(FileInfo fVo) {
		return sqlSession.selectList("selectFileList",fVo);
	}

	public int deleteFileMgmt(FileInfo fileVo) {
		return sqlSession.insert("deleteFileMgmt", fileVo);
	}

	public DateInfo selectCheckHoliday(DateInfo vo) {
		return sqlSession.selectOne("selectCheckHoliday", vo);
	}

	public void insertApprMaster(Approval apprInfo) {
		sqlSession.insert("insertApprMaster", apprInfo);
		
	}
	public void insertApprDetail(Approval apprInfo) {
		sqlSession.insert("insertApprDetail", apprInfo);
	}
	
	public Application selectApprTaskInfo(Approval vo) {
		return sqlSession.selectOne("selectApprTaskInfo", vo);
	}
	public String selectApprTaskNameInfo(Approval vo) {
		return sqlSession.selectOne("selectApprTaskNameInfo", vo);
	}
	
	public String selectApprCommonCodeInfo(Approval vo) {
		return sqlSession.selectOne("selectApprCommonCodeInfo", vo);
	}

	public String selectApprNo(Approval vo) {
		return sqlSession.selectOne("selectApprNo", vo);
	}

	public List<Approval> selectApprovalSignList(Approval vo) {
		return sqlSession.selectList("selectApprovalSignList",vo);
	}
	
	public List<Approval> selectApprovalDueList(Approval vo) {
		return sqlSession.selectList("selectApprovalDueList",vo);
	}

	public List<Approval> selectApprovalProgressList(Approval vo) {
		return sqlSession.selectList("selectApprovalProgressList",vo);
	}

	public List<Approval> selectApprovalSaveList(Approval vo) {
		return sqlSession.selectList("selectApprovalSaveList",vo);
	}

	public List<Approval> selectApprovalRejectList(Approval vo) {
		return sqlSession.selectList("selectApprovalRejectList",vo);
	}
	
	public List<Approval> selectApprovalComplateListForReq(Approval vo) {
		return sqlSession.selectList("selectApprovalComplateListForReq",vo);
	}
	public List<Approval> selectApprovalComplateListForAppr(Approval vo) {
		return sqlSession.selectList("selectApprovalComplateListForAppr",vo);
	}
	public List<Approval> selectApprovalComplateListForAll(Approval vo) {
		return sqlSession.selectList("selectApprovalComplateListForAll",vo);
	}

	public void insertFileMgmt2(Guide fileVo) {
		sqlSession.insert("insertFileMgmt2", fileVo);
		
	}

	public List<Approval> selectApprList(Approval approvalVo) {
		return sqlSession.selectList("selectApprList",approvalVo);
	}

	public Approval selectApprInfo(Approval approvalVo) {
		return sqlSession.selectOne("selectApprInfo", approvalVo);
	}

	public void updateApprConfirm(Approval vo) {
		sqlSession.update("updateApprConfirm", vo);
	}

	public Approval selectApprNextInfo(Approval vo) {
		return sqlSession.selectOne("selectApprNextInfo", vo);
	}
	public Approval selectApprCoopNextInfo(Approval vo) {
		return sqlSession.selectOne("selectApprCoopNextInfo", vo);
	}

	public void updateApprMasterConfirm(Approval vo) {
		sqlSession.update("updateApprMasterConfirm", vo);
	}

	public void updateApprProcessStatus(Approval vo) {
		sqlSession.update("updateApprProcessStatus", vo);
	}

	public void updateApprMasterReject(Approval vo) {
		sqlSession.update("updateApprMasterReject", vo);
	}

	public void updateApprReject(Approval vo) {
		sqlSession.update("updateApprReject", vo);
	}

	public List<UserInfo> selectUserListFoName(UserInfo vo) {
		return sqlSession.selectList("selectUserListFoName",vo);
	}

	public List<DeptInfo> selectDeptListFoName(DeptInfo vo) {
		return sqlSession.selectList("selectDeptListFoName",vo);
	}

	public List<CommonAppl> selectApplicationAppl(CommonAppl commonAppl) {
		return sqlSession.selectList("selectApplicationAppl",commonAppl);
	}

	public List<CommonAppl> doPopupListView(CommonAppl commonAppl) {
		return sqlSession.selectList("doPopupListView",commonAppl);
	}

	public List<Code> getCommonYhCombo(Code code) {
		return sqlSession.selectList("getCommonYhCombo",code);
	}

	public CommonAppl selectCommonApplInfo(CommonAppl commonAppl) {
		return sqlSession.selectOne("selectCommonApplInfo",commonAppl);
	}

	public CommonAppl searchApplInfoMenu(CommonAppl commonAppl) {
		return sqlSession.selectOne("searchApplInfoMenu",commonAppl);
	}

	public void insertCommonAppl(CommonAppl commonAppl) {
		sqlSession.update("insertCommonAppl", commonAppl);
	}
	public void updateCommonAppl(CommonAppl commonAppl) {
		sqlSession.update("updateCommonAppl", commonAppl);
	}

	public void insertApprLineHeader(Approval vo) {
		sqlSession.insert("insertApprLineHeader", vo);
	}

	public void insertApprLineDetail(Approval vo) {
		sqlSession.insert("insertApprLineDetail", vo);
	}

	public void deleteApprLineHeader(Approval vo) {
		sqlSession.insert("deleteApprLineHeader", vo);
	}

	public void deleteApprLineDetail(Approval vo) {
		sqlSession.insert("deleteApprLineDetail", vo);
	}

	public List<Approval> selectApprLine(Approval vo) {
		return sqlSession.selectList("selectApprLine",vo);
	}

	public List<Approval> selectApprLineDetail(Approval vo) {
		return sqlSession.selectList("selectApprLineDetail",vo);
	}

	public List<CommonAppl> selectCommonAppl(CommonAppl commonAppl) {
		return sqlSession.selectList("selectCommonAppl",commonAppl);
	}

	public List<GroupInfo> selectSubjectGroup(GroupInfo vo) {
		return sqlSession.selectList("selectSubjectGroup",vo);
	}

	public List<GroupInfo> selectSubjectGroupDetail(GroupInfo vo) {
		return sqlSession.selectList("selectSubjectGroupDetail",vo);
	}
	
	public void insertSubjectGroupHeader(GroupInfo vo) {
		sqlSession.insert("insertSubjectGroupHeader", vo);
	}

	public void insertSubjectGroupDetail(GroupInfo vo) {
		sqlSession.insert("insertSubjectGroupDetail", vo);
	}

	public void deleteSubjectGroupHeader(GroupInfo vo) {
		sqlSession.delete("deleteSubjectGroupHeader", vo);
	}
	
	public void deleteSubjectGroupDetail(GroupInfo vo) {
		sqlSession.delete("deleteSubjectGroupDetail", vo);
	}
	

	public List<Code> selectCommonCodeMgmtList(Code vo) {
		return sqlSession.selectList("selectCommonCodeMgmtList",vo);
	}

	public void updateCommonCodeMgmt(Code vo) {
		sqlSession.update("updateCommonCodeMgmt", vo);
	}

	public void insertCommonCodeMgmt(Code vo) {
		sqlSession.insert("insertCommonCodeMgmt", vo);
	}

	public void deleteCommonCodeMgmt(Code vo) {
		sqlSession.delete("deleteCommonCodeMgmt", vo);
	}

	public void updateApprViewTime(Approval vo) {
		sqlSession.update("updateApprViewTime", vo);
	}

	public List<Approval> selectApprDetailList(Approval vo) {
		return sqlSession.selectList("selectApprDetailList",vo);
	}

	public List<Approval> selectApprovalRefDocList(Approval vo) {
		return sqlSession.selectList("selectApprovalRefDocList",vo);
	}

	public void insertApprRefDoc(Approval vo) {
		sqlSession.update("insertApprRefDoc", vo);
	}

	public void insertApprRefUsers(Approval vo) {
		sqlSession.update("insertApprRefUsers", vo);
	}
	
	public TaskNote selectCommonAdminInfo(TaskNote vo) {
		return sqlSession.selectOne("selectCommonAdminInfo",vo);
	}

	public List<Approval> selectApprRefDocList(Approval vo) {
		return sqlSession.selectList("selectApprRefDocList",vo);
	}

	public List<Approval> selectApprRefUSerList(Approval vo) {
		return sqlSession.selectList("selectApprRefUSerList",vo);
	}

	public void deleteApprMaster(Approval vo) {
		sqlSession.delete("deleteApprMaster", vo);
	}

	public void deleteApprDetail(Approval vo) {
		sqlSession.delete("deleteApprDetail", vo);
	}

	public void deleteApprRefDoc(Approval vo) {
		sqlSession.delete("deleteApprRefDoc", vo);
	}

	public void deleteApprRefUsers(Approval vo) {
		sqlSession.delete("deleteApprRefUsers", vo);
	}

	public Approval selectApprLineTypeByTask(Approval vo) {
		return sqlSession.selectOne("selectApprLineTypeByTask",vo);
	}

	public List<Approval> selectApprLineInfoByTask(Approval vo) {
		return sqlSession.selectList("selectApprLineInfoByTask",vo);
	}
	public List<Approval> selectApprLineInfoByTaskForChrg(Approval vo) {
		return sqlSession.selectList("selectApprLineInfoByTaskForChrg",vo);
	}

	public List<Approval> selectApprCoopLineInfoByTask(Approval vo) {
		return sqlSession.selectList("selectApprCoopLineInfoByTask",vo);
	}
	
	public List<Approval> selectApprLineInfoByTaskOB(Approval vo) {
		return sqlSession.selectList("selectApprLineInfoByTaskOB",vo);
	}
	public List<Approval> selectApprLineInfoByTaskForChrgOB(Approval vo) {
		return sqlSession.selectList("selectApprLineInfoByTaskForChrgOB",vo);
	}

	public void updateApplInfoConfirm(Approval vo) {
		sqlSession.update("updateApplInfoConfirm", vo);
	}

	public void updateApplInfoReject(Approval vo) {
		sqlSession.update("updateApplInfoReject", vo);
	}

	public void deleteApprLineChange(Approval vo) {
		sqlSession.delete("deleteApprLineChange", vo);
	}

	public void insertApprLineChange(Approval vo) {
		sqlSession.insert("insertApprLineChange", vo);
	}

	public int selectApprViewCnt(Approval vo) {
		return sqlSession.selectOne("selectApprViewCnt",vo);
	}

	public void updateApprCancel(Approval vo) {
		sqlSession.update("updateApprCancel", vo);
	}

	public void updateApplInfoCancel(Approval vo) {
		sqlSession.update("updateApplInfoCancel", vo);		
	}
	
	public List<Approval> doSelectApprovalLinkList(Approval vo) {
		return sqlSession.selectList("doSelectApprovalLinkList",vo);
	}
	
	public Holiday selectYearHoliInfo(Holiday vo) {
		return sqlSession.selectOne("selectYearHoliInfo", vo);
	}

	public String selectCarPurchaseDateInfo(UserInfo vo) {
		return sqlSession.selectOne("selectCarPurchaseDateInfo", vo);
	}

	public List<Code> getCommonComboEtc(Code code) {
		return sqlSession.selectList("getCommonComboEtc",code);
	}

	public CommonAppl selectTaskApplInfo(CommonAppl commonApplVo) {
		return sqlSession.selectOne("selectTaskApplInfo", commonApplVo);
	}

	public List<Approval> selectApprovalSignListForMain(Approval apprVo) {
		return sqlSession.selectList("selectApprovalSignListForMain",apprVo);
	}

	public List<Approval> selectApprovalProgressListForMain(Approval apprVo) {
		return sqlSession.selectList("selectApprovalProgressListForMain",apprVo);
	}

	public void insertApprUpdateHistory(Approval vo) {
		sqlSession.insert("insertApprUpdateHistory", vo);		
	}

	public List<Approval> selectApprUpdateResnList(Approval approvalVo) {
		return sqlSession.selectList("selectApprUpdateResnList",approvalVo);
	}

	public List<Approval> selectApprChargeList(Approval approvalVo) {
		return sqlSession.selectList("selectApprChargeList",approvalVo);
	}

	public String selectApprManagerUser(Approval approvalVo) {
		return sqlSession.selectOne("selectApprManagerUser", approvalVo);
	}

	public Approval selectApprChargeCnt(Approval approvalVo) {
		return sqlSession.selectOne("selectApprChargeCnt",approvalVo);
	}

	public void deleteApprHeaderInfo(Approval vo) {
		sqlSession.delete("deleteApprHeaderInfo", vo);
	}
	
	public void deleteApprDetailInfo(Approval vo) {
		sqlSession.delete("deleteApprDetailInfo", vo);
	}

	public void deleteApplInfo(Approval vo) {
		sqlSession.delete("deleteApplInfo", vo);
	}

	public UserInfo selectCommonEmailInfo(String apprUserId) {
		return sqlSession.selectOne("selectCommonEmailInfo", apprUserId);
	}

	public void saveAppointInfo(Approval vo) {
		sqlSession.insert("saveAppointInfo", vo);
	}

	public void addApprChargePic(Approval vo) {
		sqlSession.insert("addApprChargePic", vo);
	}

	public void deleteApprChargePic(Approval vo) {
		sqlSession.delete("deleteApprChargePic", vo);
	}

	public void saveChargeOpinionConfirm(Approval vo) {
		sqlSession.update("saveChargeOpinionConfirm", vo);
	}

	public List<Approval> selectChargeChangeList(Approval approvalVo) {
		return sqlSession.selectList("selectChargeChangeList",approvalVo);
	}

	public Approval selectChargeManager(Approval approvalVo) {
		return sqlSession.selectOne("selectChargeManager",approvalVo);
	}

	public void deleteAppointInfo(Approval vo) {
		sqlSession.delete("deleteAppointInfo", vo);
	}

	public Approval selectAppointInfo(Approval vo) {
		return sqlSession.selectOne("selectAppointInfo", vo);
	}

	public void updateApprAppointInfo(Approval approvalVo) {
		sqlSession.update("updateApprAppointInfo", approvalVo);
	}
	
	public void updateApprAppointInfoClear(Approval approvalVo) {
		sqlSession.update("updateApprAppointInfoClear", approvalVo);
	}
	
	public List<Code> getCommonComboCd(Code code) {
		return sqlSession.selectList("getCommonComboCd",code);
	}

	public HealthCheck selectYearHealthCheckInfo(HealthCheck vo) {
		return sqlSession.selectOne("selectYearHealthCheckInfo", vo);
	}

	public String selectCheckPromotionView(ViewDateInfo vo) {
		return sqlSession.selectOne("selectCheckPromotionView", vo);
	}

	public PromotionPoint selectPromotionPointInfo(PromotionPoint vo) {
		return sqlSession.selectOne("selectPromotionPointInfo", vo);
	}

	public String selectCheckIncomeView(ViewDateInfo vo) {
		return sqlSession.selectOne("selectCheckIncomeView", vo);
	}

	public IncomeInfo selectIncomeInfo(IncomeInfo vo) {
		return sqlSession.selectOne("selectIncomeInfo", vo);
	}

	public Approval selectOrgApprIdInfo(Approval vo) {
		return sqlSession.selectOne("selectOrgApprIdInfo", vo);
	}

	public List<Code> selectCommonTaskList(Code vo) {
		return sqlSession.selectList("selectCommonTaskList",vo);
	}

	public UserInfo selectUserDetail(UserInfo vo) {
		return sqlSession.selectOne("selectUserDetail",vo);
	}

	public List<ApprovalAw> selectApprovalSignListForAW(Approval vo) {
		return sqlSession.selectList("selectApprovalSignListForAW",vo);
	}

	public List<ApprovalAw> selectApprovalProgressListForAW(Approval vo) {
		return sqlSession.selectList("selectApprovalProgressListForAW",vo);
	}

	public void updateApprProcessStatusCoop(Approval apprProc) {
		sqlSession.update("updateApprProcessStatusCoop", apprProc);
	}

	public int selectApprCoopCnt(Approval vo) {
		return sqlSession.selectOne("selectApprCoopCnt",vo);
	}

	public void updateLocaleInfo(Common apprProc) {
		sqlSession.update("updateLocaleInfo", apprProc);
	}

	public Approval selectApplApprInfo(Approval vo) {
		return sqlSession.selectOne("selectApplApprInfo",vo);
	}

	public String selectApprInfoReqDate(Approval vo) {
		return sqlSession.selectOne("selectApprInfoReqDate",vo);
	}

	public List<Approval> selectAppointList(Approval vo) {
		return sqlSession.selectList("selectAppointList",vo);
	}

	public String selectApprAppointIdInfo(Approval vo) {
		return sqlSession.selectOne("selectApprAppointIdInfo",vo);
	}

	public String selectApprDocUrl(Approval apprVo) {
		return sqlSession.selectOne("selectApprDocUrl", apprVo);
	}

	public void updateWorkAppointInfo(Approval vo) {
		sqlSession.update("updateWorkAppointInfo", vo);
	}
	
	public void updateWorkAppointInfoClear(Approval vo) {
		sqlSession.update("updateWorkAppointInfoClear", vo);
	}

	public List<Approval> selectApprCoopList(Approval vo) {
		return sqlSession.selectList("selectApprCoopList",vo);
	}

	public int selectMenuUrlCount(String reqUrl) {
		return sqlSession.selectOne("selectMenuUrlCount", reqUrl);
	}

	public int selectRoleMenuAuthCount(Role role) {
		return sqlSession.selectOne("selectRoleMenuAuthCount", role);
	}

	public String selectEdType(Approval vo) {
		return sqlSession.selectOne("selectEdType", vo);
	}

	public String selectUserLangInfo(UserInfo vo) {
		return sqlSession.selectOne("selectUserLangInfo", vo);
	}

	public void insertFileMgmt3(OfficialDocument fileVo) {
		sqlSession.insert("insertFileMgmt3", fileVo);
	}

	public List<Approval> selectApprConformUserList(Approval vo) {
		return sqlSession.selectList("selectApprConformUserList",vo);
	}
	
	public List<Approval> selectApprRefDocAppr(Approval vo)throws Exception  {
		return sqlSession.selectList("selectApprRefDocAppr",vo);
	}

	public List<Approval> selectApprRefUsersAppr(Approval vo)throws Exception  {
		return sqlSession.selectList("selectApprRefUsersAppr",vo);
	}
	
	public void insertApplFileInfo(FileInfo vo) {
		sqlSession.insert("insertApplFileInfo", vo);
	}

	public List<Code> selectInpoproCombo() {
		return sqlSession.selectList("selectInpoproCombo");
	}

	public void insertApprDetailOb(Approval vo) {
		sqlSession.insert("insertApprDetailOb", vo);
	}

	public UserInfo selectParentAuth(UserInfo vo) {
		return sqlSession.selectOne("selectParentAuth", vo);
	}

	public UserInfo doSelectHelpDesk(UserInfo vo) {
		return sqlSession.selectOne("doSelectHelpDesk", vo);
	}

	public List<Approval> selectObDefaultRefUser(Approval vo) {
		return sqlSession.selectList("selectObDefaultRefUser",vo);
	}

	public int selectApprRefDocCnt(Approval vo) {
		return sqlSession.selectOne("selectApprRefDocCnt", vo);
	}

	public String selectHdApprNo(Approval vo) {
		return sqlSession.selectOne("selectHdApprNo", vo);
	}

	public String selectEdApprName(String edType) {
		return sqlSession.selectOne("selectEdApprName", edType);
	}
	
	public String selectCheckIncentiveView(ViewDateInfo vo) {
		return sqlSession.selectOne("selectCheckIncentiveView", vo);
	}

	public IncentiveInfo selectIncentiveInfo(IncentiveInfo vo) {
		return sqlSession.selectOne("selectIncentiveInfo", vo);
	}

	public IncomeInfo selectIncomeInfo2(IncomeInfo vo) {
		return sqlSession.selectOne("selectIncomeInfo2", vo);
	}

	public List<YearEducation> selectYearEduInfo(YearEducation yEduVo) {
		return sqlSession.selectList("selectYearEduInfo", yEduVo);
	}
	
	public String selectCheckInsuranceView(ViewDateInfo vo) {
		return sqlSession.selectOne("selectCheckInsuranceView", vo);
	}

	public InsuranceInfo selectInsuranceInfo(InsuranceInfo vo) {
		return sqlSession.selectOne("selectInsuranceInfo", vo);
	}
	
	public List<Approval> selectApprCoopLineInfoByIP(Approval vo) {
		return sqlSession.selectList("selectApprCoopLineInfoByIP",vo);
	}

	public List<UserInfo> selectUserListForSabun(UserInfo vo) {
		return sqlSession.selectList("selectUserListForSabun",vo);
	}
		
}