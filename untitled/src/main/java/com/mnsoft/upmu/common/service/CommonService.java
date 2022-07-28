package com.mnsoft.upmu.common.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.mnsoft.upmu.campaign.vo.Campaign;
import com.mnsoft.upmu.common.util.CommonMessage;
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
import com.mnsoft.upmu.system.vo.Code;
import com.mnsoft.upmu.system.vo.FileInfo;
import com.mnsoft.upmu.system.vo.Guide;
import com.mnsoft.upmu.system.vo.TaskNote;
import com.mnsoft.upmu.yearEducation.vo.YearEducation;

public interface CommonService {
	//공통코드 조회
	public List<Code> selectCodeList(Code code) throws Exception;
	
	//공통코드 조회
	public List<Code> getCommonComboEtc_1(Code code) throws Exception;
	
	public List<Code> getCommonComboEtc_2(Code code) throws Exception;

	//업무코드 조회
	public List<Code> selectWorkCodeList(Code code) throws Exception;
	public List<Code> selectWorkComboType(Code code) throws Exception;

	public List<Code> selectComonMenuList(Code code) throws Exception;

	//사용자 조회
	public UserInfo selectUserInfo(UserInfo userInfo) throws Exception;
	//사용자 세션 생성
	public void setSessionInfo(HttpServletRequest req, UserInfo userInfo);

	//부서코드 조회
	public List<Code> selectCommonDeptList(Code code, HttpServletRequest req) throws Exception;
	
	// 특허 페이지에서 부서코드 조회
	public List<Code> selectPatentDeptList(Code code, HttpServletRequest req) throws Exception;

	//사용자 리스트 조회
	public List<UserInfo> selectCommonUserList(UserInfo searchParam);

	//조직도 조회
	public List<DeptInfo> selectOrgListForTree(DeptInfo searchParam);

	//리더기준 조직도 조회
	public List<DeptInfo> selectOrgListForTreeLeader(UserInfo searchParam) throws Exception;

	public List<UserInfo> selectOrgListForUser(UserInfo searchParam);

	public List<UserInfo> selectOrgListForDept(UserInfo searchParam);

	//부서인원리스트 조회
	public List<UserInfo> selectUserListForDept(UserInfo searchParam);
	
	//링크코드 조회
	public List<Code> selectCommonLinkList(Code code) throws Exception;

	public CommonMessage insertFileMgmt(HttpServletRequest req, String string, String string2);

	public CommonMessage deleteFileMgmt(HttpServletRequest req, FileInfo param);

	public DateInfo selectCheckHoliday(DateInfo vo);

	public List<Approval> selectApprovalSignList(Approval vo);
	
	public List<Approval> selectApprovalDueList(Approval vo);

	public List<Approval> selectApprovalProgressList(Approval vo);

	public List<Approval> selectApprovalSaveList(Approval vo);

	public List<Approval> selectApprovalRejectList(Approval vo);

	public List<Approval> selectApprovalComplateList(Approval vo);

	CommonMessage insertFileMgmt2(HttpServletRequest req, String foldNm, String fileId, Guide vo);

	public List<Approval> selectApprList(Approval approvalVo);

	public Approval selectApprInfo(Approval approvalVo) throws Exception;

	public CommonMessage updateApprConfirm(Approval vo, HttpServletRequest req);

	public CommonMessage updateApprReject(Approval vo, HttpServletRequest req);
	
	public List<Code> getCommonYhCombo(Code code) throws Exception;

	public List<UserInfo> selectUserListFoName(UserInfo vo);

	public List<DeptInfo> selectDeptListFoName(DeptInfo vo);

	public List<CommonAppl> selectApplicationAppl(CommonAppl commonAppl);

	public List<CommonAppl> doPopupListView(CommonAppl commonAppl);

	public CommonMessage saveCommonAppl(HttpServletRequest req, CommonAppl vo, Approval apprVo, List<Approval> apprList);

	public CommonAppl searchApplInfoMenu(CommonAppl commonAppl);
	public CommonAppl selectCommonApplInfo(CommonAppl commonAppl);

	public CommonMessage approveCommonAppl(HttpServletRequest req, CommonAppl vo, Approval apprVo, List<Approval> apprList);
	public CommonMessage updateCommonAppl(HttpServletRequest req, CommonAppl vo, Approval apprVo, List<Approval> apprList);

	public CommonMessage insertApprLine(Approval vo, List<Approval> list);

	public CommonMessage deleteApprLine(Approval vo);

	public List<Approval> selectApprLine(Approval vo);

	public List<Approval> selectApprLineDetail(Approval vo);

	public List<GroupInfo> selectSubjectGroup(GroupInfo vo);
	public List<GroupInfo> selectSubjectGroupDetail(GroupInfo vo);
	public CommonMessage insertSubjectGroup(GroupInfo vo, List<GroupInfo> list)throws Exception;
	public CommonMessage deleteSubjectGroup(GroupInfo vo);

	public List<CommonAppl> selectCommonAppl(CommonAppl commonAppl);

	public List<Code> selectCommonCodeMgmtList(Code vo);

	public CommonMessage saveCommonCodeMgmtList(List<Code> list);

	public CommonMessage deleteCommonCodeMgmtList(List<Code> list);

	public List<Approval> selectApprDetailList(Approval vo);

	public List<Approval> selectApprovalRefDocList(Approval vo);
	
	public TaskNote selectAdminInfo(TaskNote vo) throws Exception;

	public List<Approval> selectApprRefDocList(Approval approvalVo);

	public List<Approval> selectApprRefUSerList(Approval approvalVo);

	public List<Approval> selectApprLineInfoByTask(HttpServletRequest req, Approval vo);
	
	public List<Approval> selectApprCoopLineInfoByTask(HttpServletRequest req, Approval vo);

	public CommonMessage updateApprLineChange(Approval vo, List<Approval> list, HttpServletRequest req);

	public CommonMessage updateApprCancel(Approval vo, HttpServletRequest req);
	
	public CommonMessage deleteApprInfo(Approval vo, HttpServletRequest req);
	
	public List<Approval> doSelectApprovalLinkList(Approval vo) throws Exception;

	public Holiday selectYearHoliInfo(Holiday vo);

	public String selectCarPurchaseDateInfo(UserInfo userInfo);

	public List<Code> getCommonComboEtc(Code code) throws Exception;

	public CommonAppl selectTaskApplInfo(CommonAppl commonApplVo);

	public List<Approval> selectApprovalSignListForMain(Approval apprVo);

	public List<Approval> selectApprovalProgressListForMain(Approval apprVo);

	public List<Approval> selectApprUpdateResnList(Approval approvalVo);

	public List<Approval> selectApprChargeList(Approval approvalVo);

	public Approval selectApprChargeCnt(Approval approvalVo);

	public CommonMessage saveAppointInfo(Approval vo, HttpServletRequest req);

	public CommonMessage deleteAppointInfo(Approval vo, HttpServletRequest req);


	public Approval selectAppointInfo(Approval vo);

	public CommonMessage addApprChargePic(Approval vo, HttpServletRequest req);

	public CommonMessage deleteApprChargePic(List<Approval> list, HttpServletRequest req);

	public CommonMessage saveApprChargePic(Approval vo, HttpServletRequest req);

	public CommonMessage saveChargeOpinionConfirm(Approval vo, HttpServletRequest req);

	public CommonMessage sendChargeMail(Approval vo, HttpServletRequest req) throws Exception;
	
	public List<Approval> selectChargeChangeList(Approval approvalVo);

	public List<Code> getCommonComboCd(Code code) throws Exception;

	public HealthCheck selectYearHealthCheckInfo(HealthCheck holiVo);

	public String selectCheckPromotionView(ViewDateInfo viewPr);

	public PromotionPoint selectPromotionPointInfo(PromotionPoint prVo);

	public String selectCheckIncomeView(ViewDateInfo viewPr);

	public IncomeInfo selectIncomeInfo(IncomeInfo prVo);

	public List<Code> selectCommonTaskList(Code vo);
	
	public UserInfo selectUserDetail(UserInfo vo);

	public List<ApprovalAw> selectApprovalSignListForAW(Approval vo);

	public List<ApprovalAw> selectApprovalProgressListForAW(Approval vo);

	public CommonMessage updateLocaleInfo(Common commonVo);

	public Approval selectApplApprInfo(HttpServletRequest req, Approval apprVo);

	public String selectApprDocUrl(Approval apprVo);

	public CommonMessage insertFileMgmt3(HttpServletRequest req, String string, String file_id, OfficialDocument vo);

	public List<Code> selectInpoproCombo();

	public UserInfo selectParentAuth(UserInfo patentVo);

	public CommonMessage doSelectHelpDesk(UserInfo vo);

	public List<Approval> selectObDefaultRefUser(Approval approvalVo);

	public List<String> selecttestList();

	public String selectCheckIncentiveView(ViewDateInfo viewPr);

	public IncentiveInfo selectIncentiveInfo(IncentiveInfo ictInfo);

	public IncomeInfo selectIncomeInfo2(IncomeInfo icVo);

	public List<YearEducation> selectYearEduInfo(YearEducation yEduVo);
	
	public String selectCheckInsuranceView(ViewDateInfo viewPr);

	public InsuranceInfo selectInsuranceInfo(InsuranceInfo isInfo);

	List<Approval> selectApprCoopLineInfoByIP(HttpServletRequest req, Approval vo);

	public List<UserInfo> selectUserListForSabun(UserInfo vo);
	
}
